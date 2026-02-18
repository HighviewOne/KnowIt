# KnowIt

A text-based Android trivia game built with Kotlin and Jetpack Compose.

## Gameplay

- **20 questions** across 5 categories: 🔬 Science · 📜 History · 🌍 Geography · 🎬 Pop Culture · 💻 Tech
- Questions alternate between **Multiple Choice** and **Type-In** formats
- **+10 pts** per correct answer
- **+5 pt streak bonus** for consecutive correct answers
- Max possible score: **295 pts**

## Screens

| Screen | Description |
|---|---|
| Home | Animated title, high score display, Play button |
| Game | 20 questions with live score, streak counter, progress bar |
| Result | Final score, accuracy %, letter grade (A+ → F), high score tracking |

## Animations

All animations are pure Jetpack Compose — no third-party libraries.

- Title scale-pulse on Home screen
- Staggered emoji entrance
- Question card **shake** on wrong answer
- **Green glow** flash on correct answer
- **Canvas confetti** (30 particles) on correct answer
- Animated score counter
- Category-colored progress bar

## Tech Stack

| Component | Version |
|---|---|
| Kotlin | 2.0.21 |
| Android Gradle Plugin | 8.6.1 |
| Gradle | 8.9 |
| Compose BOM | 2024.11.00 |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 34 |
| JVM Target | 17 |

## Getting Started

1. Clone the repo
   ```bash
   git clone https://github.com/HighviewOne/KnowIt.git
   ```
2. Open the project in **Android Studio Hedgehog or newer** — it will generate `gradle-wrapper.jar` automatically
3. Click **Sync Now** to resolve dependencies
4. Run on an API 26+ emulator or physical device

### Physical device (alternative)
Enable **USB Debugging** on your phone, plug it in, and select it as the run target in Android Studio.

### Build an APK from the terminal
```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```

## Project Structure

```
app/src/main/
├── kotlin/com/knowit/
│   ├── MainActivity.kt
│   ├── model/Question.kt          # Data models & enums
│   ├── data/QuestionBank.kt       # 20 trivia questions
│   ├── viewmodel/GameViewModel.kt # Game state & scoring logic
│   └── ui/
│       ├── theme/                 # Color, Type, Theme
│       ├── screens/HomeScreen.kt
│       ├── screens/GameScreen.kt
│       └── screens/ResultScreen.kt
└── res/values/
```

## License

MIT
