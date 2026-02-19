# Agent Context: Myna Bell

## Project Identity
**Myna Bell** is a Kotlin-first Android application designed to wake up users effectively using dynamic radio streams.
- **Goal**: Combat auditory habituation.
- **Core Mechanism**: Radio Garden API Streams + TOTP Verification.

## Technical Rules
1.  **Language**: Kotlin (Strictly).
2.  **UI**: Jetpack Compose (No XML Layouts for new screens).
3.  **Architecture**: MVVM + Clean Architecture.
4.  **Dependency Injection**: Hilt.
5.  **Build System**: Gradle Kotlin DSL (`build.gradle.kts`).

## Coding Standards
- **English Only**: Comments, variable names, and documentation.
- **Conciseness**: Prefer readable, idiomatic Kotlin (`val`, `when`, `apply/also`).
- **Safety**: Use Kotlin's Null Safety features. Avoid `!!` operator.

## Phase Strategy
Refer to `project-plan.md` for the roadmap.
- **Phase 1**: Infrastructure (Current).
- **Phase 2**: Radio Logic.
- **Phase 3**: Alarm Logic.
- **Phase 4**: UI/UX.
- **Phase 5**: Polish.

## Key Files
- `d:\Myna-Bell\project-plan.md`: Master roadmap.
- `d:\Myna-Bell\mockup.jsx`: UI Reference (Source of Truth for Design).
- `d:\Myna-Bell\RADIO_GARDEN_API.md`: API Documentation.

## Important CLI Commands
- Build Debug: `./gradlew assembleDebug`
- Lint: `./gradlew lintDebug`
- Unit Test: `./gradlew testDebugUnitTest`

---
*Last Updated: 2026-02-19*
