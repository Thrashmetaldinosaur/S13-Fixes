# S13 YouTube Community Install — Start Here

This folder contains the complete installable custom fix stack used on the known-good S13.

## Requirements

- S13 / Core 5
- Android 13
- tested firmware: `S13_C29_EN_V1.6_20251121`
- Magisk root
- Zygisk + LSPosed
- YouTube package: `com.google.android.youtube`
- known-good YouTube version: `21.39.523`

Google's YouTube APK is not redistributed in this archive.

## Included custom APKs

1. `01-FULLSCREEN-FIX/S13-YouTube-Fullscreen-Fix-v1.6.apk`
2. `02-SQUARE-TAB-FIX/S13-YouTube-SquareMode-TabFix-v9.apk`
3. `03-QUEUE-FIT/S13-YouTube-Queue-Fit-v1.apk`

These are the final working builds, not diagnostic/intermediate versions.

## Fast installation

From Windows PowerShell in Android platform-tools:

1. Connect the rooted S13 and verify `.\adb devices`.
2. Run:
   `.\13-YOUTUBE-FIXES\INSTALL-YOUTUBE-FIXES.ps1`
   if you extracted this archive under platform-tools, or run the script by its full path.
3. Open LSPosed on the watch.
4. Enable all three modules.
5. Scope ALL THREE modules to `com.google.android.youtube` ONLY.
6. Reboot.
7. Run `VERIFY-YOUTUBE-FIXES.ps1`.
8. Perform the three functional tests below.

## Functional tests

### Normal fullscreen
Open an ordinary video and enter fullscreen.
Expected: video fills the usable display and stays centered/stable.

### Square mode
Switch the S13 into square mode.
Expected: YouTube bottom navigation remains visible/usable.

### Queue playback
Create/play a queue and advance to another video.
Expected: the video stays properly fitted instead of shrinking.

## Why there are three modules

The modules deliberately have separate responsibilities:

- **Fullscreen Fix v1.6**: normal fullscreen geometry and stability.
- **Square-Mode Tab Fix v9**: bottom navigation in square mode.
- **Queue Fit v1**: queue-specific undersized renderer correction.

During development, combining responsibilities caused regressions. In particular, the v8 square-tab approach restored navigation but interfered with fullscreen. v9 removed the broad click hook and is the final version.
