# S13 / Core 5 Community Fix Pack — EASY START GUIDE

If you just downloaded this ZIP and want to make your S13 behave like the fully fixed reference watch, start here.

## 1. First: make sure you have the correct watch/firmware

This pack was built and tested on:

- Watch: S13 / Core 5
- Android: 13
- Firmware/build: `S13_C29_EN_V1.6_20251121`
- SoC: ASR8601

On the watch, check Android/build information in Settings, or connect ADB and run:

```powershell
.\adb shell getprop ro.build.display.id
.\adb shell getprop ro.build.version.release
```

Expected build:

```text
S13_C29_EN_V1.6_20251121
```

If your build is different, DO NOT flash the included root/AVB images blindly. The app-level fixes may still be useful, but the root images are firmware-specific.

---


## Before running the automatic installer: download CloX

`CloX_v6.3.apk` is about 59.1 MiB, so it is not committed directly to the Git repository.

Open this repository's **Releases** page, download:

`CloX_v6.3.apk`

and place it in:

`12-DEPENDENCIES/CloX_v6.3.apk`

SHA-256:

`da7cf30e9d81aa1d096519d3e0697a77bc43d171242ec361b8625fb51408ce7f`

Then continue with the installer below.

---

# Which path are you on?

## A. My watch is already rooted with Magisk + LSPosed

Great. This is the easy path.

You can run:

```powershell
.\INSTALL-ALL-AFTER-ROOT.ps1
```

from this folder while the watch is connected through ADB.

The script will:

- verify the S13 build;
- confirm root;
- install the current Magisk modules;
- install CloX + Breezy Weather;
- install the custom S13 APKs;
- install all three YouTube fix APKs;
- apply the safe background power/scan settings;
- enable the Breezy/CloX crown Accessibility service.

It DOES NOT automatically change LSPosed scopes. You do that manually afterward.

Then skip to **Section 3 — LSPosed setup** below.

## B. My watch is not rooted

Root is required for most of the fixes.

Go to:

`01-ROOT\ROOT_GUIDE_ORIGINAL.md`

That is the full tested rooting procedure for this firmware.

The important warning is simple:

**Do not flash the included root images unless your S13 is on the exact tested build.**

Once Magisk root and LSPosed are working, come back here and continue with the easy install.

---

# 2. What the automatic installer adds

## Magisk modules

The script installs:

### USB / MTP / ADB
`02-USB-MTP-ADB\S13_MTP_ADB_Fix_LIVE_v1.0.zip`

Fixes the S13 USB behavior so MTP file transfer and ADB work properly.

### Real RAM / storage reporting
`03-REAL-RAM-STORAGE\S13_Real_RAM_Storage_LIVE_v1.0.zip`

`03-REAL-RAM-STORAGE\S13_Real_Storage_LIVE_v1.0.zip`

Stops the firmware from presenting the fake advertised memory/storage profile.

### Crown volume service
`05-CROWN-ROTATION-VOLUME\S13_Crown_Volume_Service_LIVE_v1.0.zip`

Provides the low-level crown rotation service used by the working setup.

### ASR5803 Wi-Fi power saving
`08-ASR5803-WIFI-POWER-SAVE\S13_ASR5803_Power_Save_Fix_LIVE_v1.1.zip`

Enables the tested ASR5803 Wi-Fi power-saving configuration.

---

# 3. LSPosed setup — IMPORTANT

After the installer finishes, open **LSPosed** on the watch.

You need to enable these modules and set the correct scope.

## Scope to Android System Framework ONLY

Enable:

- `S13 Crown Volume` v1.3
- `S13 Crown Scroll Block` v1.0
- `S13 Circle Scale` v1.1
- `S13 CloX Wake Fix` v2.0

For each one, select:

```text
Android System Framework
```

Usually shown as package:

```text
android
```

Do not broadly scope these modules to every app.

## Scope to YouTube ONLY

Enable:

- `S13 YouTube Fullscreen Fix` v1.6
- `S13 YouTube Square-Mode Tab Fix` v9
- `S13 YouTube Queue Fit` v1

Scope all three ONLY to:

```text
com.google.android.youtube
```

The known-good YouTube version is:

```text
21.39.523
```

The Google YouTube APK itself is not included in this archive.

After setting the scopes, REBOOT the watch.

---

# 4. Crown button controls

The included Breezy/CloX Bridge APK also handles the crown button.

Final behavior:

- Single crown press = Back
- Double crown press = switch circle/square mode
- Hold crown for about 0.7 seconds = rotate portrait/landscape

The automatic installer enables its Accessibility service.

If you install manually instead, run:

`04-BREEZY-CLOX-BRIDGE-AND-CROWN\Enable-Bridge-Accessibility.ps1`

---

# 5. Weather / CloX setup

The reference setup uses:

- CloX v6.3
- Breezy Weather v6.2.2
- S13 Breezy-CloX Bridge v1.0

The installer installs all three.

Open Breezy Weather and CloX normally after reboot and make sure both have the permissions they request.

The bridge is what passes Breezy weather information into CloX.

---

# 6. YouTube fixes

The complete working YouTube stack is:

1. Fullscreen Fix v1.6
2. Square-Mode Tab Fix v9
3. Queue Fit v1

Do not replace v9 with v8. v8 restored the tabs but interfered with fullscreen.

After reboot, test:

### Normal video
Open a video and enter fullscreen.

Expected:
- player fills the usable display;
- video remains centered;
- fullscreen controls work.

### Square mode
Double-press the crown to switch the watch into square mode.

Expected:
- YouTube bottom navigation remains usable.

### Queue
Add videos to a queue and let the next video start.

Expected:
- queued videos remain correctly fitted;
- video should not suddenly shrink.

You can also run:

`13-YOUTUBE-FIXES\VERIFY-YOUTUBE-FIXES.ps1`

---

# 7. Test the other fixes

## Crown rotation

Rotate the crown.

Expected:
- one crown detent changes Android volume by one step;
- the page should NOT also scroll.

## Circle / square toggle

Double-press crown.

Expected:
- watch changes between circle and square UI mode.

## Rotation

Hold crown for about 0.7 seconds.

Expected:
- portrait/landscape toggles.

## CloX wake fix

Leave the watch idle with CloX running.

Expected:
- CloX notification updates should no longer randomly wake the screen.

## Wi-Fi power saving

Run:

```powershell
.\adb shell "su -c 'cat /sys/module/asr5803/parameters/ps_on'"
.\adb shell "su -c 'cat /sys/module/asr5803/parameters/PS_mode'"
```

Expected:

```text
Y
1
```

## Background scanning

The installer sets:

```text
mobile_data_always_on = 0
wifi_scan_always_enabled = 0
ble_scan_always_enabled = 0
```

These reduce unnecessary background radio activity.

---

# 8. Optional fixes

## Remove the lockscreen/pattern

Folder:

`10-LOCKSCREEN-DISABLE`

Only do this if you actually want no lockscreen.

Run:

`Disable-Lockscreen.ps1`

This is OPTIONAL and reduces device security.

## SIM not detected

There is no software patch.

If the watch reports no SIM / SIM removed, physically reseat the nano-SIM carefully.

See:

`11-SIM-SEATING\README.md`

---

# 9. If something goes wrong

Do not start installing random older versions from the development history.

The versions in this archive are the final reference versions.

For each fix, open that fix's folder and read its `README.md`.

If a Magisk module causes a boot problem, disable/remove that module from Magisk recovery before changing unrelated system files.

For YouTube problems, confirm first:

- YouTube is the expected package: `com.google.android.youtube`
- tested version is 21.39.523
- all three YouTube fix modules are enabled;
- all three are scoped ONLY to YouTube.

---

# 10. Quick checklist

Before calling the installation finished:

- [ ] Correct S13 build confirmed
- [ ] Magisk root works
- [ ] Zygisk enabled
- [ ] LSPosed works
- [ ] Five Magisk fix modules installed
- [ ] Breezy Weather installed
- [ ] CloX installed
- [ ] Breezy/CloX Bridge installed + Accessibility enabled
- [ ] Crown Volume enabled in LSPosed → Android System Framework
- [ ] Crown Scroll Block enabled in LSPosed → Android System Framework
- [ ] Circle Scale enabled in LSPosed → Android System Framework
- [ ] CloX Wake Fix enabled in LSPosed → Android System Framework
- [ ] YouTube Fullscreen v1.6 enabled in LSPosed → YouTube only
- [ ] YouTube Square Tab v9 enabled in LSPosed → YouTube only
- [ ] YouTube Queue Fit v1 enabled in LSPosed → YouTube only
- [ ] Rebooted after LSPosed setup
- [ ] Crown single/double/hold tested
- [ ] Crown rotation volume tested
- [ ] YouTube fullscreen tested
- [ ] YouTube square-mode tabs tested
- [ ] YouTube queue playback tested
- [ ] ASR5803 reports `Y / 1`

If all of those pass, your S13 should be very close to the fully fixed reference setup.
