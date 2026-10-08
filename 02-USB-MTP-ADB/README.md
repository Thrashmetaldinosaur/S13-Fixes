# S13 MTP + ADB Fix v1.0

Live Magisk module ID: `s13_mtp`

Purpose:
- Replaces the stock S13/ASR USB behavior with working MTP + ADB.
- Includes the patched `libmtp.so` currently used by the watch.
- Handles initial boot setup and MTP restart after USB reconnects.

Install:
1. Open Magisk.
2. Modules → Install from storage.
3. Select `S13_MTP_ADB_Fix_LIVE_v1.0.zip`.
4. Reboot.
5. Connect the USB puck/cable and verify both ADB and Windows file transfer.

The live module is the source of truth. Its `module.prop` reports v1.0 even though an older development archive had a v1.1 filename.
