# S13 Smart Performance v2.3 — moderate foreground optimization

**Optional Magisk module.** Tested on rooted S13 / Core 5 (ASR8601), firmware `S13_C29_EN_V1.6_20251121`, Android 13.

## Download

[Download S13-Smart-Performance-v2.3-Foreground-Uclamp-20-Magisk.zip](S13-Smart-Performance-v2.3-Foreground-Uclamp-20-Magisk.zip)

SHA-256: `7984763d1f2789493d85cbe0b2003c6ea492c1015f1845db7fe54297f0389a9b`

## What it does

- Sets `/dev/cpuctl/top-app/cpu.uclamp.min` to **20.00**, compared with the tested watch's stock `10.00`, once after boot.
- Prioritizes tasks in Android's *top-app* cgroup. **20% utilization clamp does not mean 20% faster apps**, and is not a 20% increase in clock frequency.
- Leaves `schedutil`, native ASR Power HAL, CPU frequency floor (stock 819 MHz), maximum (1.5 GHz), GPU and thermal settings unchanged.
- No resident polling/watchdog; the firmware may later rewrite `20.00`.
- Magisk ID: `s13_adaptive_perf`. Upgrades/replaces previous v2.1.x/v2.2.x attempts; **do not run another CPU-floor controller alongside it**.
- A different firmware revision or missing sysfs control causes the installer to abort.

## Tested status

On the reference watch after reboot, `profile.sh status` returned `version=2.3.0`, `top_app_uclamp_min=20.00`, `stock_top_app_uclamp_min=10.00`, and `controller=one-shot-on-boot`. This verifies persistent configuration at the time of inspection, **not a measured improvement in app frame rates, battery life, or long-run persistence**.

## Manual Magisk installation (not in one-click installer)

Use Magisk to install the ZIP, or with ADB:

```powershell
.\adb -s DEVICE push ".\S13-Smart-Performance-v2.3-Foreground-Uclamp-20-Magisk.zip" /data/local/tmp/s13_performance.zip
.\adb -s DEVICE shell "su -c 'magisk --install-module /data/local/tmp/s13_performance.zip'"
.\adb -s DEVICE reboot
```

Replace `DEVICE` with the active ADB serial/IP:port. Download the ZIP first; run from the directory containing it and `adb`, or use full file paths.

Verify:
```powershell
.\adb -s DEVICE shell "su -c 'sh /data/adb/modules/s13_adaptive_perf/profile.sh status; cat /dev/cpuctl/top-app/cpu.uclamp.min'"
```

## Restore stock / roll back

Immediately return the measured setting to **10.00** and save that choice for future boots:

```powershell
.\adb -s DEVICE shell "su -c 'sh /data/adb/modules/s13_adaptive_perf/profile.sh observe'"
```

`profile.sh balanced` reapplies 20.00. To remove the module entirely, switch to `observe` first, then disable or uninstall in Magisk and reboot.

This fix is **optional**, not a prerequisite for the core S13 fixes. Higher utilization may increase heat and battery consumption. Keep normal thermal safeguards intact.
