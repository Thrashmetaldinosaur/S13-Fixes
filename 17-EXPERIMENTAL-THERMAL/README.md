# S13 Virtual-Skin Thermal Calibration v1.2 — experimental

**Optional and high-risk. Not part of the one-click installer.**

[Download S13-Virtual-Skin-51C-56C-Magisk-v1.2.zip](S13-Virtual-Skin-51C-56C-Magisk-v1.2.zip)

SHA-256: `2b0d81f3653452ddbc38d21d4ff1cd161dc18fcc2cfc6b35ba675ee5d43af5a8`

For *exactly* `S13_C29_EN_V1.6_20251121` on the rooted S13 / Core 5.

## What is changed

Magisk ID `s13_virtual_skin_tune`. Overlays the firmware's normal and charging thermal JSON configurations. In the VIRTUAL-SKIN sensor's levels only:

| Level | Earlier custom v1.1 | Experimental v1.2 |
|---|---:|---:|
| Critical | 49°C | **51°C** |
| Emergency | 54°C | **56°C** |

Other thermal thresholds, including the final shutdown level, are not intentionally modified. The **charging profile's final threshold is 57°C**, leaving just **1°C** between emergency and final level. The VIRTUAL-SKIN sensor is a weighted virtual reading, **not the measured watch-case or wrist-contact temperature**. Other subsystems and hardware safeguards may still apply, but do not treat these modifications as safe simply because shutdown values are unchanged.

## Strong warning

This raises the temperature at which the watch may intervene during heavy use and charging. There is **no demonstrated safety margin for skin contact, charging, battery aging, or prolonged operation** at these values. Increased heat can cause discomfort, injury, battery damage or instability. Do not wear the watch while charging; if it becomes hot to touch, stop the load and restore stock. **For general users, retaining the original thermal settings is preferable.**

## Installation / restoration

Install manually through Magisk, then reboot. The installer checks the exact firmware build. Do not combine with other thermal overlays.

To restore stock vendor thermal configurations, **disable or uninstall the `s13_virtual_skin_tune` Magisk module and reboot**. The ZIP changes files systemlessly; the original vendor partition is not overwritten.

The reference watch confirmed Magisk v1.2 installation after a reboot; the actual on-device effect of all thermal actions at high temperature **has not been exhaustively tested**.
