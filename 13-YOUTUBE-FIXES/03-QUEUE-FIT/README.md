# YouTube Queue Fit v1

Package: `com.s13.youtubequeuefitfixv1`

Purpose:
- Fixes queued/auto-advanced videos that render smaller than normal fullscreen.
- Detects the undersized video layer and applies a uniform aspect-ratio-preserving correction.
- During testing the bad queue geometry was ~294×221 versus ~466×280, requiring about a 1.59× correction.

Install:
1. Install the APK.
2. Enable it in LSPosed.
3. Scope ONLY to `com.google.android.youtube`.
4. Reboot.

Keep this separate from Fullscreen Fix v1.6.
