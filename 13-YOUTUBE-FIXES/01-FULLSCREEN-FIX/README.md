# YouTube Fullscreen Fix v1.6

Package: `com.s13.youtubefullscreenfix`

Included APK:
`S13-YouTube-Fullscreen-Fix-v1.6.apk`

SHA-256:
`9edf99317bfb9cbb954786a821ad36f450e4b59c9a9d0242a6b4cff9b282c5b2`

This is the exact final APK pulled from the known-good working S13.

## What it fixes

The stock YouTube watch layout does not use the S13 display correctly. This module:

- expands the usable player geometry;
- suppresses watch-layout elements that interfere with fullscreen;
- preserves the normal fullscreen controls and time bar;
- recenters YouTube's fitted video layer;
- resets the renderer surface scale and translation so fullscreen remains stable.

Queue playback can expose a separate undersized-renderer bug. That is intentionally handled by the separate Queue Fit v1 module.

## Install

1. Install `S13-YouTube-Fullscreen-Fix-v1.6.apk`.
2. Open LSPosed.
3. Enable **S13 YouTube Fullscreen Fix**.
4. Scope it to `com.google.android.youtube` ONLY.
5. Reboot after the complete three-module YouTube stack is configured.

Known-good YouTube version:
`21.39.523`

Do not substitute v1.4 or v1.5; those were intermediate development builds.
