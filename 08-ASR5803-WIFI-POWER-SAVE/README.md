# S13 ASR5803 Wi-Fi Power Save Fix v1.1

Live module ID: `s13_asr5803_powersave`

This is the exact module pulled from the working watch.

Target driver parameters:
- `ps_on=Y`
- `PS_mode=1`

The module waits for boot completion, preserves current Wi-Fi/Bluetooth state, cleanly disables the shared ASR radio hardware, reloads `asr5803.ko` with power saving enabled, and restores the prior radio state.

The live module log shows successful activations and later boots where the fix was already active.

Install:
1. Magisk → Modules → Install from storage.
2. Select `S13_ASR5803_Power_Save_Fix_LIVE_v1.1.zip`.
3. Reboot.

Verify:
    .\adb shell "su -c 'cat /sys/module/asr5803/parameters/ps_on'"
    .\adb shell "su -c 'cat /sys/module/asr5803/parameters/PS_mode'"

Expected:
    Y
    1

The included `wlan_power_control.conf` is the live configuration pulled from the watch and contains `ps_on=1` and `PS_mode=1`.

Do not manually experiment with unloading/reloading the Wi-Fi driver outside this tested module unless you are prepared to recover the watch.
