# S13 active CloX watch face — backup reference

**Confirmed from the user's live CloX backup dated 2026-10-08.**

Active face name: `S13 Minimal Weather XL Breezy Cache Zoomed Out 95`.

The XML definition is in [watch.xml](watch.xml), copied from the active directory:

`/data/user/0/com.ailife.clox/files/wf_active/watch.xml`

It is the **actual active face definition**, not a reconstruction. It uses a black background, large bitmap-font time, weekday graphic, seconds, battery percentage, Breezy Weather condition and Fahrenheit temperature.

**This public folder is not a complete restorable watch face.** The face also requires several supporting assets from `wf_active/` (including image sprites, bitmap fonts and a bundled font). These are **not** published here. No private CloX preferences are published.

## Full private backup (do not publish)

`S13-CloX-Private-20261008-155243.tar`

Created on the working watch with:

```sh
cd /data/user/0
tar -cf /data/local/tmp/s13-clox-private-backup.tar com.ailife.clox/files com.ailife.clox/shared_prefs
```

The private TAR contains the complete active face under `com.ailife.clox/files/wf_active/` and the app's preferences, which can include private location details.

**Do not extract and replace live app data blindly.** Before restoration, verify the device firmware, CloX version, Android user/UID ownership, and target path; back up the destination and stop CloX. Restoring user-0 app data with the wrong ownership can break the launcher. The existing live data is the source of truth.

Reference app: CloX `com.ailife.clox` 6.3; companion Breezy Weather and custom bridge are documented under `04-BREEZY-CLOX-BRIDGE-AND-CROWN` and `12-DEPENDENCIES`.

## Glance v5

Experimental twist-to-wake Glance v5 uses a separate temporary clock overlay and **does not replace** this active CloX face. Retain the private TAR until v5 has been tested and a second backup exists.
