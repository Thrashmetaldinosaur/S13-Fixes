# S13 / Core 5 Community Fixes

A community fix pack for the **S13 / Core 5 full-Android smartwatch**.

This repository contains the final working fixes developed and tested on the reference S13, including crown controls, USB/MTP, real memory reporting, Wi-Fi power saving, CloX/Breezy integration, screen-wake fixes, and the final YouTube fixes.

## Start here

If you are new to this project, read:

**[`00-START-HERE-EASY-GUIDE.md`](00-START-HERE-EASY-GUIDE.md)**

If your S13 is already rooted with **Magisk + Zygisk + LSPosed**, the short version is:

1. Download this repository.
2. Go to the repository's **Releases** page and download `CloX_v6.3.apk`.
3. Put it in `12-DEPENDENCIES/`.
4. Connect the watch with ADB.
5. Run `INSTALL-ALL-AFTER-ROOT.ps1`.
6. Configure the LSPosed scopes shown below.
7. Reboot.
8. Test the fixes.

---

## Tested reference watch

- Model: **S13 / Core 5**
- SoC: **ASR8601**
- Android: **13**
- SDK: **33**
- Tested build: **`S13_C29_EN_V1.6_20251121`**
- Product family: **YBD_C29_OLED**

If your firmware is different, do **not** blindly flash the included boot/root images.

---

## What this fixes

- Magisk root/recovery reference
- USB MTP + ADB
- Fake RAM/storage reporting
- Crown press controls
- Crown rotation → Android volume
- Unwanted crown scrolling
- Circle-mode scaling
- Breezy Weather → CloX integration
- CloX random screen wakes
- ASR5803 Wi-Fi power saving
- Excess background scanning
- Optional lockscreen removal
- SIM seating/detection note
- YouTube fullscreen
- YouTube bottom navigation in square mode
- YouTube queue scaling

### Crown controls

| Crown action | Result |
|---|---|
| Single press | Back |
| Double press | Circle / Square mode |
| Hold ~0.7 sec | Portrait / Landscape |
| Rotate | Android media volume |

---

# Easy install

## If the watch is already rooted

You need:

- Magisk
- Zygisk
- LSPosed
- ADB working from your PC

### Step 1 — Get the large dependency

`CloX_v6.3.apk` is larger than the 25 MB repo-file target, so it is kept in **GitHub Releases**.

Download:

`CloX_v6.3.apk`

Then place it here:

`12-DEPENDENCIES/CloX_v6.3.apk`

Expected SHA-256:

`da7cf30e9d81aa1d096519d3e0697a77bc43d171242ec361b8625fb51408ce7f`

See [`LARGE-FILES.md`](LARGE-FILES.md) for details.

### Step 2 — Connect the S13

From Android Platform Tools:

```powershell
.\adb devices
```

Your watch should appear as `device`.

### Step 3 — Run the installer

From the root of this repository:

```powershell
.\INSTALL-ALL-AFTER-ROOT.ps1
```

The installer:

- checks the watch/build;
- verifies root;
- installs the Magisk modules;
- installs Breezy Weather and CloX;
- installs the S13 custom APKs;
- installs all three final YouTube fix APKs;
- applies the safe background power settings;
- enables the Breezy/CloX crown Accessibility service.

The installer does **not** configure LSPosed scopes automatically.

### Step 4 — Configure LSPosed

Enable these modules and scope them to **Android System Framework (`android`) only**:

- S13 Crown Volume v1.3
- S13 Crown Scroll Block v1.0
- S13 Circle Scale v1.1
- S13 CloX Wake Fix v2.0

Enable these modules and scope them to **YouTube (`com.google.android.youtube`) only**:

- S13 YouTube Fullscreen Fix v1.6
- S13 YouTube Square-Mode Tab Fix v9
- S13 YouTube Queue Fit v1

Then reboot the watch.

### Step 5 — Test it

Check:

- Crown single press = Back
- Crown double press = circle/square toggle
- Crown hold = portrait/landscape
- Crown rotation = one volume step per detent
- Crown rotation does not also scroll
- YouTube fullscreen works
- YouTube tabs work in square mode
- queued YouTube videos do not shrink

For full verification, follow:

[`00-START-HERE-EASY-GUIDE.md`](00-START-HERE-EASY-GUIDE.md)

---

# If the watch is not rooted

Start with:

[`01-ROOT/ROOT_GUIDE_ORIGINAL.md`](01-ROOT/ROOT_GUIDE_ORIGINAL.md)

**Important:** the supplied boot/AVB images are specific to the tested firmware. Do not flash them onto another firmware revision unless you have independently verified compatibility.

Back up your own partitions before making boot-chain changes whenever possible.

---

# YouTube

The final working stack is:

| Fix | Package | Version |
|---|---|---|
| Fullscreen Fix | `com.s13.youtubefullscreenfix` | 1.6 |
| Square-Mode Tab Fix | `com.s13.youtubesquaremodefix` | v9 |
| Queue Fit | `com.s13.youtubequeuefitfixv1` | v1 |

All three custom APKs are included directly in this repository.

They should all be scoped in LSPosed **only** to:

`com.google.android.youtube`

The known-good YouTube version is:

`21.39.523`

Google's YouTube APK itself is not redistributed here.

More details:

[`13-YOUTUBE-FIXES/00-START-HERE.md`](13-YOUTUBE-FIXES/00-START-HERE.md)

---

# Large files

Files over the repo's 25 MB sharing target are distributed through **GitHub Releases**.

Large downloads:

- **CloX v6.3** — required for CloX/weather integration. Download the APK from [GitHub Releases](https://github.com/Thrashmetaldinosaur/S13-Fixes/releases) and place it in `12-DEPENDENCIES/`.
- **Optional firmware backup:** [S13_WT_EN_V1.1_20260309 (MEGA)](https://mega.nz/file/9uRgALhY#ZJstwhSb1AQV3osDAIYPg4tMg15jr39jgXYGzp6lKzo) — for recovery and firmware research only; **not required** to install these fixes.

See:

[`LARGE-FILES.md`](LARGE-FILES.md)

---

# Optional firmware backup

The following separate S13 firmware backup is available **for recovery or research if you need that particular firmware revision**:

**[Download S13_WT_EN_V1.1_20260309 on MEGA](https://mega.nz/file/9uRgALhY#ZJstwhSb1AQV3osDAIYPg4tMg15jr39jgXYGzp6lKzo)**

- **Archived backup:** `S13_WT_EN_V1.1_20260309`
- **Build tested for this fix pack:** `S13_C29_EN_V1.6_20251121`
- **Not required** for ordinary installation of the community fixes.

These are **different firmware revisions**. Do not assume that `init_boot`, `vbmeta`, `boot` or other partitions can be flashed interchangeably. The contents and checksum of the externally hosted backup have **not yet been independently verified**.

Read [`Firmware Backup/README.md`](Firmware%20Backup/README.md) before considering any recovery operation.

---

# Repository layout

```text
00-START-HERE-EASY-GUIDE.md
INSTALL-ALL-AFTER-ROOT.ps1
LARGE-FILES.md

01-ROOT/
02-USB-MTP-ADB/
03-REAL-RAM-STORAGE/
04-BREEZY-CLOX-BRIDGE-AND-CROWN/
05-CROWN-ROTATION-VOLUME/
06-CIRCLE-MODE-SCALE/
07-CLOX-WAKE-FIX/
08-ASR5803-WIFI-POWER-SAVE/
09-POWER-AND-SCAN-SETTINGS/
10-LOCKSCREEN-DISABLE/
11-SIM-SEATING/
12-DEPENDENCIES/
13-YOUTUBE-FIXES/

Firmware Backup/
SHA256SUMS.txt
```

Each fix directory contains its own README.

---

# Wi-Fi power-save verification

After installation and reboot:

```powershell
.\adb shell "su -c 'cat /sys/module/asr5803/parameters/ps_on'"
.\adb shell "su -c 'cat /sys/module/asr5803/parameters/PS_mode'"
```

Expected:

```text
Y
1
```

Do not manually unload/reload the ASR5803 driver unless you understand the recovery implications.

---

# Safety / compatibility

This is an unofficial community project.

Rooting and flashing Android devices can cause:

- boot loops
- data loss
- failed OTA updates
- broken radios
- an unbootable watch

Record your exact firmware before changing boot-chain partitions:

```powershell
.\adb shell getprop ro.build.display.id
.\adb shell getprop ro.product.device
.\adb shell getprop ro.boot.slot_suffix
```

The primary tested build is:

`S13_C29_EN_V1.6_20251121`

---

# Privacy

The public community package has been sanitized to remove known personal identifiers, local Windows usernames, email addresses, and private session logs.

If you discover private information that should not be in the repository, open an issue.

---

# Contributing / compatibility reports

If a fix works or fails on another S13 firmware revision, please include:

```text
Watch/model:
Android version:
Build number:
ro.product.device:
ro.product.name:
Active slot:
Magisk version:
LSPosed version:
Fix/module:
Expected behavior:
Actual behavior:
```

Please do not post passwords, SIM identifiers, account information, or other private data.

---

## Recommended first file

**[`00-START-HERE-EASY-GUIDE.md`](00-START-HERE-EASY-GUIDE.md)**
