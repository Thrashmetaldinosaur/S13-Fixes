# Crown rotation → volume

The current working watch has all three components enabled:

1. `S13 Crown Volume` APK v1.3 (`com.s13.crownvolume`)
2. `S13 Crown Scroll Block` APK v1.0 (`com.s13.crownscrollblock`)
3. `S13 Crown Volume Service` Magisk module v1.0

The APK modules hook the Android System Framework through LSPosed:
- Crown Volume v1.3 handles rotary events and root volume adjustment.
- Crown Scroll Block prevents the same crown events from producing unwanted framework scrolling.

The Magisk service listens to the S13 rotary input directly (`rotary`, fallback `/dev/input/event12`) and is also enabled on the archived working watch.

For an exact clone of the current watch, keep all three. Do not remove one as "duplicate" unless you retest one-detent/one-step behavior.

LSPosed:
- Install both APKs.
- Enable both as LSPosed modules.
- Scope them to Android System Framework (`android`).
- Reboot.

Magisk:
- Install `S13_Crown_Volume_Service_LIVE_v1.0.zip`.
- Reboot.
