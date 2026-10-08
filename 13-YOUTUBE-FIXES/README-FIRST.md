# S13 YouTube Fix Stack — Community Implementation Guide

This is a self-contained installer pack for the custom S13 YouTube fixes.

Tested baseline:
- S13 / Core 5
- Android 13
- firmware `S13_C29_EN_V1.6_20251121`
- YouTube `21.39.523`
- Magisk + Zygisk-LSPosed

Final custom modules included:
1. Fullscreen Fix v1.6 — `com.s13.youtubefullscreenfix`
2. Square-Mode Tab Fix v9 — `com.s13.youtubesquaremodefix`
3. Queue Fit v1 — `com.s13.youtubequeuefitfixv1`

## Recommended installation

Read `00-START-HERE.md`.

For ADB installation, run:
`INSTALL-YOUTUBE-FIXES.ps1`

Then manually configure LSPosed:

- enable all three modules;
- scope all three to `com.google.android.youtube` ONLY;
- reboot.

Run `VERIFY-YOUTUBE-FIXES.ps1` afterward.

## Important

The known-good YouTube version is `21.39.523`.

Google's YouTube APK is not included in this community archive. The three custom S13 fix APKs are included and installable directly.

Do not enable old S13 YouTube Magisk/RRO experiments alongside this stack.
