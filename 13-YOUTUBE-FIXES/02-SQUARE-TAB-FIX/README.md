# YouTube Square-Mode Tab Fix v9

Package: `com.s13.youtubesquaremodefix`

Purpose:
- Restores YouTube's bottom navigation tabs in S13 square mode.
- v9 is the final version.
- v8 restored navigation but broke/interfered with fullscreen.
- v9 removed the broad `View.performClick()` hook and kept the safer YouTube-specific fallback.

Install:
1. Install the APK.
2. Enable it in LSPosed.
3. Scope ONLY to `com.google.android.youtube`.
4. Reboot.

Do not use v7/v8/diagnostic builds.
