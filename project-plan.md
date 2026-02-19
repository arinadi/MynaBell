# Myna Bell - Project Plan

**Myna Bell** is an advanced **Online Radio Alarm Clock** designed to mitigate **auditory habituation** through dynamic stimuli (global radio stations) and cognitive verification (TOTP challenges).

## 1. Technical Architecture

### 1.1 Tech Stack
- **Language**: Kotlin (1.9.24+)
- **Minimum SDK**: 26 (Android 8.0) | **Target SDK**: 34 (Android 14)
- **Build System**: Gradle (AGP 8.5.1) with Kotlin DSL
- **UI Toolkit**: Jetpack Compose (Material 3)
- **Architecture Pattern**: MVVM (Model-View-ViewModel) + Clean Architecture
- **Asynchronous Processing**: Coroutines + Flow

### 1.2 Key Libraries (Based on CyperBot Reference & Research)
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp + Gson/Moshi
- **Local Database**: Room (for favorites, alarm schedules, history)
- **Media Playback**: Jetpack Media3 (ExoPlayer)
- **Background Work**: AlarmManager (Exact Alarms) + WorkManager (Maintenance)
- **Navigation**: Jetpack Compose Navigation

### 1.3 Project Structure
The project will follow a modular feature-based or clean architecture structure, similar to `D:\CyperBot` but optimized for Compose.

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/myna/bell/
│   │   │   ├── data/           # Repositories, Data Sources, Database
│   │   │   ├── domain/         # Use Cases, Models
│   │   │   ├── ui/             # Compose Screens, ViewModels, Theme
│   │   │   ├── di/             # Hilt Modules
│   │   │   ├── service/        # MediaService, AlarmReceiver
│   │   │   └── util/           # Extensions, Constants
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   └── test/                   # Unit Tests
├── build.gradle.kts
└── ...
```

## 2. Phase Breakdown

### Phase 1: Foundation & DevOps (Active)
**Goal:** Establish a robust project skeleton with CI/CD and basic local build ability.
- [ ] Initialize Android Project with Kotlin DSL.
- [ ] Setup GitHub Actions for CI (Build & Lint).
- [ ] Configure `build.gradle.kts` (incorporating best practices from CyperBot).
- [ ] Implement robust `README.md` and Documentation structure.
- [ ] Verify build locally and on CI.

### Phase 2: Core Radio Engine
**Goal:** Enable streaming of global radio stations.
- [ ] Integrate Radio Garden API (Retrofit implementation).
- [ ] Implement Jetpack Media3 (ExoPlayer) Service.
- [ ] Create basic "Now Playing" UI.
- [ ] Handle Audio Focus and Network Failures (Fallback logic).

### Phase 3: Alarm & Scheduling System
**Goal:** Reliable alarm triggering.
- [ ] Implement `AlarmManager` with `setExactAndAllowWhileIdle`.
- [ ] Handle `BOOT_COMPLETED` for alarm persistence.
- [ ] Create Database schema for Alarm schedules.
- [ ] Implement `WakeLock` and Foreground Service management.

### Phase 4: UI/UX & Cognitive Verification
**Goal:** Polish the interface and implement the "Wake Up" challenge.
- [ ] Implement Material 3 Dark Theme (Deep Charcoal).
- [ ] Build the TOTP (Time-based One-Time Password) Logic.
- [ ] Create the "Wake Up" screen with Numpad and Visualizer.
- [ ] Integrate Haptic Feedback.

### Phase 5: Optimization & Release
**Goal:** Production readiness.
- [ ] Battery Optimization Audits (Doze mode testing).
- [ ] Crash Reporting & Analytics (privacy-focused).
- [ ] Final UI Polish (Animations, Transitions).
- [ ] Release Build Signing & ProGuard/R8 configuration.

## 3. Reference Analysis (CyperBot)
We will leverage `D:\CyperBot` for:
- **Gradle Configuration**: Replicating the robust Git-based versioning and dependency management.
- **CI Workflow**: Adapting `.github/workflows/ci.yml`.
- **Code Style**: Ensuring consistency in Kotlin conventions.

## 4. Documentation Strategy
All documentation will be:
- Written in **English**.
- Optimized for **AI Agents** (Context-rich, clear, structured).
- Located in `docs/` or root as `*.md` files.

---
*Created by Antigravity Agent - 2026-02-19*
