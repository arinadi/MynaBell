# Phase 1: Foundation & DevOps

**Objective:**
Establish a robust Android project skeleton using **Kotlin DSL**, set up **CI/CD** with GitHub Actions, and ensure a stable local build environment. This phase lays the groundwork for all future development.

## 1. Project Initialization

### 1.1 Android Studio Project Setup
- **Template:** Empty Activity (Compose).
- **Package Name:** `com.myna.bell`
- **Minimum SDK:** API 26 (Android 8.0).
- **Language:** Kotlin.
- **Build Configuration Language:** Kotlin DSL (`build.gradle.kts`).

### 1.2 Gradle Configuration (Reference: CyperBot)
We will replicate the robust Gradle setup from `CyperBot`.

**Project-Level `build.gradle.kts`:**
- Define plugins and clean task.
- Use version catalogs if applicable (or consistent version variables).

**App-Level `build.gradle.kts`:**
- **Versioning Strategy**: Implement Git-based versioning (commit count for `versionCode`, hash for `versionName`).
- **Dependencies**:
  - `androidx.core:core-ktx`
  - `androidx.lifecycle:lifecycle-runtime-ktx`
  - `androidx.activity:activity-compose`
  - `androidx.compose.ui:ui` (and related Compose libs)
  - `com.google.dagger:hilt-android` (Dependency Injection setup early)
  - `androidx.room:room-runtime` (Database setup early)

**`settings.gradle.kts`:**
- Define plugin management and dependency resolution repositories (Google, MavenCentral).

## 2. CI/CD Implementation

### 2.1 GitHub Actions Workflow (`.github/workflows/ci.yml`)
- **Trigger**: Push to `main`, Pull Requests.
- **Environment**: `ubuntu-latest`.
- **JDK**: Java 17 (Zulu or Temurin).
- **Steps**:
  1.  Checkout Code.
  2.  Set up JDK 17.
  3.  Grant Execute Permission to `gradlew`.
  4.  **Lint Check**: `./gradlew lintDebug`.
  5.  **Build Debug APK**: `./gradlew assembleDebug`.
  6.  **Unit Tests**: `./gradlew testDebugUnitTest`.
  7.  Upload Artifacts (Lint Report, APK).

## 3. Dummy Implementation (Proof of Concept)

### 3.1 Initial UI (Compose)
Create a basic "Hello World" screen using Material 3 to verify Compose setup.
- `MainActivity.kt`: Simple `Scaffold` with a "Myna Bell Initialized" text.

### 3.2 Documentation
- **`README.md`**:
  - Project Overview.
  - Setup Instructions (Local & CI).
  - Architecture Diagram (Mermaid).
- **`agent.md`**:
  - Context for future AI sessions (Project rules, architecture decisions).

## 4. Verification Steps

### 4.1 Local Build Verification
- [ ] Run `./gradlew clean build`.
- [ ] Verify `app-debug.apk` is generated in `app/build/outputs/apk/debug/`.

### 4.2 CI Verification
- [ ] Push changes to GitHub.
- [ ] Verify Action run is green.
- [ ] Inspect uploaded artifacts.

### 4.3 Documentation Check
- [ ] Ensure `README.md` is clear and renders correctly on GitHub.

---
*Status: Planned*
*Next Step: Execute Project Initialization*
