# S13 Wrist Twist-to-Wake + Glance v5.3

**Tested on the owner's rooted Android 13 S13 / Core 5** (`S13_C29_EN_V1.6_20251121`). The owner confirmed reliable wrist wake, large clock and battery percentage with the five-second glance.

## What is included

The known-good version is **S13 Twist + Glance v5 TEST**, versionCode **8**, versionName `5.3-5s-battery`, package `com.s13.twistglance`. It is an LSPosed module, not a traditional Android security lock screen.

- Wrist twist wakes using the WIITE wake-up tilt sensor (Android type **22**); event-driven, one sensor listener, no periodic accelerometer polling or held CPU wakelock.
- AMOLED-black temporary clock overlay with large condensed time, date and battery percentage (one battery reading per glance).
- **Single tap** dismisses clock and reveals the already-running Android/CloX interface.
- **5 seconds** of clock visibility before requesting screen-off, plus a separate **5-second cooldown after screen-off**.
- Preserves the usual Android screen timeout, CloX theme/watch face, ASR5803 power settings and other modules.
- **Known acceptable limitation:** Android briefly displays the last app/CloX before the overlay draws.
- Does **not** provide PIN/password authentication; do not mistake this for a secure lock screen.

## Reproduce v5.3

See [the complete standalone source archive](S13-Lift-to-Wake-Glance-v5.3-Standalone-Source.tar.xz). This archive is **not a prebuilt APK**. Extract it with `tar -xf` on Windows 11/Windows 10 with tar installed, then open PowerShell in the extracted directory and run:

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\Build-And-Install-v5.ps1
```

The helper downloads JDK 17, Android SDK 35 and Gradle 8.9 if needed. Use `-BuildOnly` to compile without installing. The builder checks the exact tested watch firmware before installation. The resulting APK is at `app\build\outputs\apk\debug\app-debug.apk`.

**Debug signing:** Builds made on different computers can have different APK signing certificates and may not upgrade an installed v5.3. Do not uninstall your working module to bypass `INSTALL_FAILED_UPDATE_INCOMPATIBLE` without a saved copy of the original APK and LSPosed settings. The owner's known working APK (not uploaded here) had SHA-256 `9F3E10F189B4BB6D00BF646565E5178EDFEBF440492509F6C42C3C89167742A8`.

## LSPosed setup

- Enable `S13 Twist + Glance v5 TEST` (`com.s13.twistglance`) scoped to **WIITE Watch Health** (`com.wiite.wearhealthuart`) **only**.
- **Uncheck** Android Settings (`com.android.settings`) and **Android System Framework** (`android`).
- Disable earlier v4 module `com.s13.tiltwakeprobetest001`. Do not uninstall it, as it provides rollback.
- Reboot after changing scopes.
- Confirm sensor listener with `adb shell dumpsys sensorservice` (`WIITE TILT Sensor`; normally connections=1).
- Event logs use `S13GlanceV53` for the overlay and retain `S13GlanceV52` for the unchanged wake engine.

## Rollback

Disable v5 in LSPosed, re-enable v4 with WIITE-only scope, and reboot. Never enable v4 and v5 simultaneously. This module does not alter the underlying CloX custom watch face.

**Safety and scope:** Only the owner's firmware was tested; battery improvements are plausible but not quantitatively benchmarked. Do not install blindly on unrelated firmware, and keep a current watch backup.
