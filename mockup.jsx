import React, { useState, useEffect, useRef } from 'react';
import { Play, Pause, Plus, ArrowLeft, Radio, BarChart3, Wifi, WifiOff, Music, Volume2, Search, MapPin, Globe, Trash2, PlayCircle, Timer, Clock, Save, X, ChevronUp, ChevronDown, Check, ChevronRight } from 'lucide-react';

// --- MOCK API DATA & REPOSITORY (Radio Garden Simulation) ---

const RADIO_GARDEN_DATA = {
    places: [
        { id: "JktId01", title: "Jakarta", country: "Indonesia", size: 45, geo: [-6.2088, 106.8456] },
        { id: "Aq7xeIiB", title: "Austin TX", country: "United States", size: 21, geo: [30.2672, -97.7431] },
        { id: "LdnUk02", title: "London", country: "United Kingdom", size: 120, geo: [51.5074, -0.1278] },
        { id: "TkyJp03", title: "Tokyo", country: "Japan", size: 80, geo: [35.6762, 139.6503] },
    ],
    channels: {
        "JktId01": [
            { id: "Prambors01", title: "Prambors FM 102.2", subtitle: "Jakarta, ID", href: "/listen/prambors/ID01", stream: "https://masima.rastream.com/masima-pramborsjakarta" },
            { id: "Delta01", title: "Delta FM", subtitle: "Jakarta, ID", href: "/listen/delta/ID02", stream: "https://masima.rastream.com/masima-deltajakarta" },
            { id: "HardRock01", title: "Hard Rock FM", subtitle: "Jakarta, ID", href: "/listen/hardrock/ID03", stream: "https://masima.rastream.com/masima-hardrockjakarta" }
        ],
        "Aq7xeIiB": [
            { id: "vbFsCngB", title: "KUTX FM 98.9", subtitle: "Austin TX", href: "/listen/kutx-98-9/vbFsCngB", stream: "https://kut.streamguys1.com/kutx-free" },
            { id: "KVRX01", title: "KVRX 91.7", subtitle: "Austin TX", href: "/listen/kvrx/US02", stream: "https://kvrx.org/stream" }
        ],
        "LdnUk02": [
            { id: "BBCWorld", title: "BBC World Service", subtitle: "London, UK", href: "/listen/bbc-world/UK01", stream: "https://stream.live.vc.bbcmedia.co.uk/bbc_world_service" },
            { id: "JazzFM", title: "Jazz FM", subtitle: "London, UK", href: "/listen/jazz-fm/UK02", stream: "https://media-ssl.musicradio.com/JazzFM" }
        ],
        "TkyJp03": [
            { id: "JPopPower", title: "J-Pop Powerplay", subtitle: "Tokyo, JP", href: "/listen/jpop/JP01", stream: "https://kathy.torontocast.com:3060/" }
        ]
    }
};

const RadioGardenRepo = {
    getPlaces: async () => new Promise(resolve => setTimeout(() => resolve(RADIO_GARDEN_DATA.places), 600)),
    getPlaceChannels: async (placeId) => new Promise(resolve => setTimeout(() => resolve(RADIO_GARDEN_DATA.channels[placeId] || []), 500)),
    search: async (query) => new Promise(resolve => {
        setTimeout(() => {
            const q = query.toLowerCase();
            const results = [];
            RADIO_GARDEN_DATA.places.forEach(p => {
                if (p.title.toLowerCase().includes(q) || p.country.toLowerCase().includes(q)) results.push({ ...p, type: 'place' });
            });
            Object.values(RADIO_GARDEN_DATA.channels).flat().forEach(c => {
                if (c.title.toLowerCase().includes(q)) results.push({ ...c, type: 'channel' });
            });
            resolve(results);
        }, 400);
    })
};

// --- Components ---

const Visualizer = ({ isPlaying }) => {
    return (
        <div className="flex justify-center items-end gap-1 h-16 w-full mb-6">
            {[...Array(12)].map((_, i) => (
                <div key={i} className={`w-2 bg-[#C5A059] rounded-t-sm transition-all duration-300 ease-in-out ${isPlaying ? 'animate-pulse' : 'h-1 opacity-20'}`}
                    style={{ height: isPlaying ? `${Math.random() * 100}%` : '4px', animationDuration: `${0.5 + Math.random()}s`, animationDelay: `${Math.random() * 0.2}s` }}
                />
            ))}
        </div>
    );
};

const NumpadButton = ({ value, onClick }) => (
    <button onClick={() => onClick(value)} className="w-16 h-16 rounded-full border border-gray-700 bg-white/5 text-2xl font-medium text-white hover:bg-[#C5A059] hover:text-black hover:border-[#C5A059] active:scale-95 transition-all flex items-center justify-center backdrop-blur-sm">
        {value}
    </button>
);

const TimePickerColumn = ({ value, onIncrement, onDecrement, label }) => (
    <div className="flex flex-col items-center gap-2">
        <button onClick={(e) => { e.stopPropagation(); onIncrement(); }} className="p-2 text-gray-500 hover:text-[#C5A059] transition-colors"><ChevronUp size={24} /></button>
        <div className="text-5xl font-sans font-bold text-white tracking-tighter w-20 text-center">{value}</div>
        <button onClick={(e) => { e.stopPropagation(); onDecrement(); }} className="p-2 text-gray-500 hover:text-[#C5A059] transition-colors"><ChevronDown size={24} /></button>
        {label && <span className="text-xs text-gray-500 font-medium tracking-widest">{label}</span>}
    </div>
);

export default function App() {
    const [currentScreen, setCurrentScreen] = useState('list'); // list, wakeup, select, edit
    const [isPlaying, setIsPlaying] = useState(false);
    const [totpCode, setTotpCode] = useState("825109");
    const [inputCode, setInputCode] = useState("");
    const [timeLeft, setTimeLeft] = useState(30);
    const [shake, setShake] = useState(false);
    const [isOffline, setIsOffline] = useState(false);

    // Alarm State
    const [alarms, setAlarms] = useState([
        { id: 1, time: "07", minute: "00", ampm: "AM", days: ["MON", "TUE", "WED", "THU", "FRI"], active: true, station: "Jazz FM - London", countdown: "8h 20m" },
        { id: 2, time: "09", minute: "30", ampm: "AM", days: ["SAT", "SUN"], active: false, station: "BBC World Service", countdown: "1d 2h" }
    ]);

    // Edit State
    const [editingAlarm, setEditingAlarm] = useState(null); // Holds the draft alarm being edited
    const [isSelectingForEdit, setIsSelectingForEdit] = useState(false); // Flag to know if we are in 'select' screen for editing

    // Selector State
    const [searchQuery, setSearchQuery] = useState("");
    const [viewMode, setViewMode] = useState('places');
    const [currentPlace, setCurrentPlace] = useState(null);
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(false);
    const [previewStation, setPreviewStation] = useState(null);

    // --- Logic for Wake Up Screen ---
    useEffect(() => {
        let timer;
        if (currentScreen === 'wakeup') {
            setIsPlaying(true);
            timer = setInterval(() => {
                setTimeLeft((prev) => {
                    if (prev <= 1) {
                        setShake(true);
                        setTimeout(() => setShake(false), 500);
                        setTotpCode(Math.floor(100000 + Math.random() * 900000).toString());
                        setInputCode("");
                        return 30;
                    }
                    return prev - 1;
                });
            }, 1000);
        } else {
            setIsPlaying(false);
            setInputCode("");
            setTimeLeft(30);
        }
        return () => clearInterval(timer);
    }, [currentScreen]);

    // --- Logic for Radio Selector ---
    useEffect(() => {
        if (currentScreen === 'select' && viewMode === 'places') {
            loadPlaces();
        }
    }, [currentScreen, viewMode]);

    useEffect(() => {
        if (searchQuery.length > 2) {
            setViewMode('search');
            performSearch(searchQuery);
        } else if (searchQuery.length === 0 && viewMode === 'search') {
            setViewMode('places');
            loadPlaces();
        }
    }, [searchQuery]);

    const loadPlaces = async () => {
        setLoading(true);
        const places = await RadioGardenRepo.getPlaces();
        setItems(places);
        setLoading(false);
    };

    const loadChannels = async (place) => {
        setLoading(true);
        setCurrentPlace(place);
        const channels = await RadioGardenRepo.getPlaceChannels(place.id);
        setItems(channels);
        setViewMode('channels');
        setLoading(false);
    };

    const performSearch = async (q) => {
        setLoading(true);
        const results = await RadioGardenRepo.search(q);
        setItems(results);
        setLoading(false);
    }

    // --- Alarm Actions ---
    const toggleAlarm = (id) => {
        setAlarms(alarms.map(alarm => alarm.id === id ? { ...alarm, active: !alarm.active } : alarm));
    };

    const deleteAlarm = (id) => {
        if (confirm("Delete this alarm?")) setAlarms(alarms.filter(alarm => alarm.id !== id));
    };

    const testAlarm = (alarm) => {
        setPreviewStation({ title: alarm.station });
        setCurrentScreen('wakeup');
    };

    const openEditAlarm = (alarm) => {
        setEditingAlarm({ ...alarm }); // Create deep copy
        setCurrentScreen('edit');
    };

    const saveEditedAlarm = () => {
        setAlarms(alarms.map(a => a.id === editingAlarm.id ? editingAlarm : a));
        setEditingAlarm(null);
        setCurrentScreen('list');
    };

    const cancelEdit = () => {
        setEditingAlarm(null);
        setCurrentScreen('list');
    };

    const handleStationSelectForEdit = (station) => {
        setEditingAlarm({ ...editingAlarm, station: station.title });
        setIsSelectingForEdit(false);
        setCurrentScreen('edit');
        setPreviewStation(null); // Stop preview
    };

    const handleNumpad = (num) => {
        if (inputCode.length < 6) {
            const newVal = inputCode + num;
            setInputCode(newVal);
            if (newVal.length === 6) {
                if (newVal === totpCode) {
                    setTimeout(() => {
                        setCurrentScreen('list');
                        alert("Alarm Dimatikan! Selamat Pagi.");
                    }, 200);
                } else {
                    setShake(true);
                    setTimeout(() => setShake(false), 500);
                    setInputCode("");
                }
            }
        }
    };

    const handleDelete = () => setInputCode(inputCode.slice(0, -1));

    // --- Helper for Edit Time ---
    const adjustTime = (field, delta) => {
        if (!editingAlarm) return;
        let val = parseInt(editingAlarm[field]);

        if (field === 'time') { // Hours
            val += delta;
            if (val > 12) val = 1;
            if (val < 1) val = 12;
        } else if (field === 'minute') {
            val += delta;
            if (val > 59) val = 0;
            if (val < 0) val = 59;
        }

        setEditingAlarm({
            ...editingAlarm,
            [field]: val.toString().padStart(2, '0')
        });
    };

    const toggleDay = (day) => {
        if (!editingAlarm) return;
        const days = editingAlarm.days.includes(day)
            ? editingAlarm.days.filter(d => d !== day)
            : [...editingAlarm.days, day];
        setEditingAlarm({ ...editingAlarm, days });
    };

    // --- Render Screens ---

    const renderAlarmList = () => (
        <div className="flex flex-col h-full bg-[#121212] text-[#E0E0E0] p-6 relative">
            <div className="mt-8 mb-6">
                <h1 className="text-xl font-bold tracking-widest text-[#C5A059]">MYNABELL</h1>
            </div>

            <div className="mb-8">
                <h2 className="text-3xl font-light">Good Morning!</h2>
                <p className="text-gray-500">You have {alarms.filter(a => a.active).length} active alarms</p>
            </div>

            <div className="space-y-4 overflow-y-auto pb-24 no-scrollbar h-[500px]">
                {alarms.length === 0 && (
                    <div className="text-center text-gray-600 mt-10">
                        <Clock size={48} className="mx-auto mb-2 opacity-50" />
                        <p>No alarms set</p>
                    </div>
                )}

                {alarms.map((alarm) => (
                    <div
                        key={alarm.id}
                        onClick={() => openEditAlarm(alarm)}
                        className={`bg-[#1E1E1E] p-6 rounded-3xl transition-all duration-300 relative overflow-hidden group cursor-pointer active:scale-[0.98] ${alarm.active ? 'border-l-4 border-[#C5A059] shadow-lg opacity-100' : 'border-l-4 border-gray-700 opacity-60'
                            }`}
                    >
                        <div className="absolute top-0 right-0 p-4 opacity-5 group-hover:opacity-10 transition-opacity">
                            <Radio size={48} />
                        </div>

                        <div className="flex justify-between items-start">
                            <div>
                                <h3 className={`text-5xl font-sans font-medium tracking-tighter mb-1 ${alarm.active ? 'text-white' : 'text-gray-500'}`}>
                                    {alarm.time}:{alarm.minute}<span className="text-xl text-gray-500 ml-1">{alarm.ampm}</span>
                                </h3>

                                {alarm.active && (
                                    <div className="flex items-center gap-1 text-xs text-[#C5A059] font-medium tracking-wide mb-3 animate-pulse">
                                        <Timer size={12} />
                                        <span>Rings in {alarm.countdown}</span>
                                    </div>
                                )}

                                <div className="flex gap-2 text-xs font-bold mb-3">
                                    {["MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"].map((d) => (
                                        <span key={d} className={alarm.days.includes(d) ? (alarm.active ? 'text-[#C5A059]' : 'text-gray-500') : 'text-gray-800'}>{d}</span>
                                    ))}
                                </div>

                                <div className="flex items-center gap-2 text-gray-400 text-sm">
                                    <Music size={14} />
                                    <span className="truncate max-w-[150px]">Radio: {alarm.station}</span>
                                </div>
                            </div>

                            <div className="flex flex-col items-end gap-4 mt-2 z-10">
                                <div
                                    onClick={(e) => { e.stopPropagation(); toggleAlarm(alarm.id); }}
                                    className={`w-12 h-7 rounded-full flex items-center px-1 cursor-pointer transition-colors ${alarm.active ? 'bg-[#C5A059] justify-end' : 'bg-gray-700 justify-start'
                                        }`}
                                >
                                    <div className="w-5 h-5 bg-white rounded-full shadow-md"></div>
                                </div>

                                <div className="flex gap-3 mt-2">
                                    <button
                                        onClick={(e) => { e.stopPropagation(); testAlarm(alarm); }}
                                        className="p-2 bg-gray-800 rounded-full text-gray-300 hover:bg-[#C5A059] hover:text-black transition-colors"
                                    >
                                        <PlayCircle size={18} />
                                    </button>

                                    <button
                                        onClick={(e) => { e.stopPropagation(); deleteAlarm(alarm.id); }}
                                        className="p-2 bg-gray-800 rounded-full text-gray-400 hover:bg-red-900/50 hover:text-red-400 transition-colors"
                                    >
                                        <Trash2 size={18} />
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>

            <button
                onClick={() => setCurrentScreen('select')}
                className="absolute bottom-8 right-6 w-16 h-16 bg-[#C5A059] rounded-2xl shadow-xl flex items-center justify-center text-[#121212] hover:bg-[#d4b06a] transition-colors z-20"
            >
                <Plus size={32} />
            </button>
        </div>
    );

    const renderEditAlarm = () => (
        <div className="flex flex-col h-full bg-[#121212] text-[#E0E0E0] p-6 relative">
            <div className="flex items-center justify-between mt-8 mb-8">
                <button onClick={cancelEdit} className="p-2 rounded-full bg-gray-800 text-gray-400 hover:text-white"><X size={24} /></button>
                <h2 className="text-xl font-bold tracking-widest text-white">EDIT ALARM</h2>
                <button onClick={saveEditedAlarm} className="p-2 rounded-full bg-[#C5A059] text-black hover:bg-white"><Check size={24} /></button>
            </div>

            <div className="flex justify-center items-center gap-4 mb-12">
                <TimePickerColumn
                    value={editingAlarm.time}
                    onIncrement={() => adjustTime('time', 1)}
                    onDecrement={() => adjustTime('time', -1)}
                    label="HOUR"
                />
                <div className="text-5xl font-sans font-bold text-gray-600 mb-6">:</div>
                <TimePickerColumn
                    value={editingAlarm.minute}
                    onIncrement={() => adjustTime('minute', 1)}
                    onDecrement={() => adjustTime('minute', -1)}
                    label="MIN"
                />
                <div className="flex flex-col gap-2 ml-4 mb-6">
                    <button onClick={() => setEditingAlarm({ ...editingAlarm, ampm: 'AM' })} className={`px-3 py-1 rounded-lg font-bold text-sm ${editingAlarm.ampm === 'AM' ? 'bg-[#C5A059] text-black' : 'bg-gray-800 text-gray-500'}`}>AM</button>
                    <button onClick={() => setEditingAlarm({ ...editingAlarm, ampm: 'PM' })} className={`px-3 py-1 rounded-lg font-bold text-sm ${editingAlarm.ampm === 'PM' ? 'bg-[#C5A059] text-black' : 'bg-gray-800 text-gray-500'}`}>PM</button>
                </div>
            </div>

            <div className="space-y-6">
                <div>
                    <label className="text-xs text-gray-500 font-bold tracking-widest block mb-3">REPEAT DAYS</label>
                    <div className="flex justify-between bg-[#1E1E1E] p-4 rounded-2xl">
                        {["MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"].map(day => (
                            <button
                                key={day}
                                onClick={() => toggleDay(day)}
                                className={`w-8 h-8 rounded-full text-[10px] font-bold flex items-center justify-center transition-all ${editingAlarm.days.includes(day) ? 'bg-[#C5A059] text-black scale-110' : 'bg-gray-800 text-gray-500'
                                    }`}
                            >
                                {day.charAt(0)}
                            </button>
                        ))}
                    </div>
                </div>

                <div>
                    <label className="text-xs text-gray-500 font-bold tracking-widest block mb-3">WAKE UP SOUND</label>
                    <div
                        onClick={() => { setIsSelectingForEdit(true); setCurrentScreen('select'); }}
                        className="bg-[#1E1E1E] p-4 rounded-2xl flex items-center justify-between cursor-pointer border border-transparent hover:border-[#C5A059] transition-all group"
                    >
                        <div className="flex items-center gap-4">
                            <div className="w-12 h-12 rounded-xl bg-blue-900/20 text-blue-400 flex items-center justify-center group-hover:bg-[#C5A059] group-hover:text-black transition-colors">
                                <Radio size={24} />
                            </div>
                            <div>
                                <p className="text-white font-medium">{editingAlarm.station}</p>
                                <p className="text-xs text-gray-500">Radio Stream</p>
                            </div>
                        </div>
                        <ChevronRight size={20} className="text-gray-600 group-hover:text-white" />
                    </div>
                </div>
            </div>
        </div>
    );

    const renderWakeUp = () => (
        <div className={`flex flex-col h-full ${isOffline ? 'bg-yellow-900/20' : 'bg-[#121212]'} text-[#E0E0E0] relative transition-colors duration-1000`}>
            <div className="absolute inset-0 bg-gradient-to-b from-[#C5A059]/10 to-transparent pointer-events-none" />

            <div className="absolute top-6 left-6 z-20">
                <button onClick={() => setCurrentScreen('list')} className="flex items-center gap-2 text-gray-500 hover:text-white bg-black/20 px-3 py-1 rounded-full backdrop-blur-md">
                    <ArrowLeft size={14} />
                    <span className="text-xs">Dismiss Test</span>
                </button>
            </div>

            <div className="flex-1 flex flex-col items-center pt-12 px-6 z-10">
                <div className="text-center mb-6">
                    <h2 className="text-6xl font-sans font-bold tracking-tighter text-white mb-2">07:00</h2>
                    <div className="flex items-center justify-center gap-2 text-[#C5A059]">
                        {isOffline ? <WifiOff size={16} /> : <Wifi size={16} />}
                        <span className="text-sm font-medium tracking-wide">
                            {isOffline ? "OFFLINE MODE - LOCAL ALARM" : `LIVE • ${previewStation ? previewStation.title.toUpperCase() : "JAZZ FM LONDON"}`}
                        </span>
                    </div>
                </div>

                <Visualizer isPlaying={isPlaying} />

                <div className={`w-full max-w-xs bg-[#1E1E1E] rounded-3xl p-6 border border-gray-800 shadow-2xl ${shake ? 'animate-shake' : ''}`}>
                    <div className="text-center mb-4">
                        <p className="text-xs text-gray-500 uppercase tracking-widest mb-1">Enter Verification Code</p>
                        <div className="text-4xl font-mono font-bold text-[#C5A059] tracking-widest">{totpCode}</div>
                        <div className="w-full h-1 bg-gray-800 mt-2 rounded-full overflow-hidden">
                            <div
                                className="h-full bg-[#C5A059] transition-all duration-1000 ease-linear"
                                style={{ width: `${(timeLeft / 30) * 100}%` }}
                            />
                        </div>
                    </div>

                    <div className="flex justify-center gap-3 mb-6">
                        {[...Array(6)].map((_, i) => (
                            <div key={i} className={`w-3 h-3 rounded-full transition-all duration-200 ${i < inputCode.length ? 'bg-white scale-110' : 'bg-gray-700'}`} />
                        ))}
                    </div>

                    <div className="grid grid-cols-3 gap-4 justify-items-center">
                        {[1, 2, 3, 4, 5, 6, 7, 8, 9].map(num => (<NumpadButton key={num} value={num} onClick={handleNumpad} />))}
                        <button onClick={() => setIsOffline(!isOffline)} className="w-16 h-16 flex items-center justify-center text-gray-600 active:text-white"><WifiOff size={20} /></button>
                        <NumpadButton value={0} onClick={handleNumpad} />
                        <button onClick={handleDelete} className="w-16 h-16 flex items-center justify-center text-gray-400 hover:text-white transition-colors">Delete</button>
                    </div>
                </div>
            </div>
        </div>
    );

    const renderSelector = () => (
        <div className="flex flex-col h-full bg-[#121212] text-[#E0E0E0]">
            <div className="p-6 pb-2">
                <div className="flex items-center gap-2 mb-4">
                    <button
                        onClick={() => {
                            if (viewMode === 'channels' && !searchQuery) {
                                setViewMode('places');
                                loadPlaces();
                            } else {
                                if (isSelectingForEdit) {
                                    setCurrentScreen('edit');
                                    setIsSelectingForEdit(false);
                                } else {
                                    setCurrentScreen('list');
                                }
                            }
                        }}
                        className="p-2 -ml-2 text-gray-400 hover:text-white rounded-full hover:bg-white/10"
                    >
                        <ArrowLeft size={20} />
                    </button>
                    <h2 className="text-xl font-bold text-white">
                        {isSelectingForEdit ? 'Pick Station' : (viewMode === 'places' ? 'Select Location' : viewMode === 'channels' ? currentPlace?.title : 'Search Results')}
                    </h2>
                </div>

                <div className="relative mb-4">
                    <Search className="absolute left-4 top-1/2 -translate-y-1/2 text-gray-500" size={18} />
                    <input type="text" placeholder="Search stations, places..." value={searchQuery} onChange={(e) => setSearchQuery(e.target.value)} className="w-full bg-[#1E1E1E] border border-gray-800 rounded-2xl py-3 pl-12 pr-4 text-white placeholder-gray-500 focus:outline-none focus:border-[#C5A059] transition-colors" />
                </div>

                {viewMode === 'places' && <div className="text-xs text-[#C5A059] font-medium tracking-wide mb-2">EXPLORE WORLDWIDE</div>}
            </div>

            <div className="flex-1 overflow-y-auto px-6 space-y-3 pb-6">
                {loading ? (
                    <div className="flex flex-col items-center justify-center py-12 text-gray-600">
                        <div className="w-8 h-8 border-2 border-[#C5A059] border-t-transparent rounded-full animate-spin mb-2"></div>
                        <p className="text-xs">Connecting to Radio Garden...</p>
                    </div>
                ) : (
                    <>
                        {items.length === 0 && <div className="text-center text-gray-500 py-10">No results found</div>}
                        {items.map((item) => (
                            <div
                                key={item.id}
                                onClick={() => {
                                    if (viewMode === 'places' || item.type === 'place') {
                                        loadChannels(item);
                                    } else {
                                        if (isSelectingForEdit) {
                                            handleStationSelectForEdit(item); // Direct select
                                        } else {
                                            setPreviewStation(item); // Preview
                                        }
                                    }
                                }}
                                className={`bg-[#1E1E1E] p-4 rounded-xl border ${previewStation?.id === item.id ? 'border-[#C5A059] bg-[#C5A059]/10' : 'border-gray-800'} flex items-center gap-4 hover:border-[#C5A059]/50 transition-colors group cursor-pointer active:scale-[0.98]`}
                            >
                                <div className={`w-12 h-12 rounded-lg flex items-center justify-center ${(viewMode === 'places' || item.type === 'place') ? 'bg-blue-900/30 text-blue-400' : 'bg-gray-800 text-gray-500'} group-hover:text-[#C5A059]`}>
                                    {(viewMode === 'places' || item.type === 'place') ? <MapPin size={24} /> : <Radio size={24} />}
                                </div>
                                <div className="flex-1">
                                    <h4 className="font-medium text-white">{item.title}</h4>
                                    <div className="flex items-center gap-2 text-xs text-gray-400">
                                        {item.country && <span className="flex items-center gap-1"><Globe size={10} /> {item.country}</span>}
                                        {item.subtitle && <span>{item.subtitle}</span>}
                                    </div>
                                </div>
                                {(viewMode === 'places' || item.type === 'place') ? (
                                    <div className="w-8 h-8 flex items-center justify-center text-gray-600"><ArrowLeft size={16} className="rotate-180" /></div>
                                ) : (
                                    isSelectingForEdit ? <div className="w-8 h-8 flex items-center justify-center text-[#C5A059]"><Check size={20} /></div> :
                                        <button className={`w-10 h-10 rounded-full flex items-center justify-center transition-all ${previewStation?.id === item.id ? 'bg-[#C5A059] text-black' : 'bg-white/10 text-white hover:bg-[#C5A059] hover:text-black'}`}>
                                            {previewStation?.id === item.id ? <BarChart3 size={16} className="animate-pulse" /> : <Play size={16} fill="currentColor" />}
                                        </button>
                                )}
                            </div>
                        ))}
                    </>
                )}
            </div>

            {previewStation && !isSelectingForEdit && (
                <div className="bg-[#1E1E1E] border-t border-gray-800 p-4 pb-8 flex items-center justify-between">
                    <div className="flex items-center gap-3">
                        <div className="w-10 h-10 bg-[#C5A059] rounded flex items-center justify-center overflow-hidden relative">
                            <div className="absolute inset-0 bg-black/20" />
                            <BarChart3 size={20} className="text-black relative z-10" />
                        </div>
                        <div>
                            <p className="text-xs text-[#C5A059] uppercase tracking-wider">Previewing</p>
                            <p className="text-sm font-bold text-white truncate max-w-[150px]">{previewStation.title}</p>
                        </div>
                    </div>
                    <div className="flex gap-4">
                        <Volume2 size={20} className="text-gray-400" />
                        <button onClick={() => setPreviewStation(null)} className="w-8 h-8 bg-white rounded-full flex items-center justify-center text-black hover:scale-105 transition-transform">
                            <Pause size={14} fill="currentColor" />
                        </button>
                    </div>
                </div>
            )}
        </div>
    );

    return (
        <div className="w-full h-[800px] max-w-md mx-auto overflow-hidden bg-black font-sans shadow-2xl rounded-[40px] border-[8px] border-gray-900 relative">
            <div className="absolute top-0 left-1/2 -translate-x-1/2 w-32 h-6 bg-black rounded-b-xl z-50"></div>
            {currentScreen === 'list' && renderAlarmList()}
            {currentScreen === 'wakeup' && renderWakeUp()}
            {currentScreen === 'select' && renderSelector()}
            {currentScreen === 'edit' && editingAlarm && renderEditAlarm()}

            <style>{`
         .no-scrollbar::-webkit-scrollbar { display: none; }
         .no-scrollbar { -ms-overflow-style: none; scrollbar-width: none; }
         @keyframes shake { 0%, 100% { transform: translateX(0); } 25% { transform: translateX(-10px); } 75% { transform: translateX(10px); } }
         .animate-shake { animation: shake 0.4s ease-in-out; }
       `}</style>
        </div>
    );
}