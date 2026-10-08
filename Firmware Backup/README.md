# Optional S13 Firmware Backup

## Download

**[Download S13_WT_EN_V1.1_20260309 on MEGA](https://mega.nz/file/9uRgALhY#ZJstwhSb1AQV3osDAIYPg4tMg15jr39jgXYGzp6lKzo)**

This is an **optional, externally hosted firmware backup**, intended for recovery research or cases where someone specifically needs this firmware revision. It is **not required** to install the fixes in this repository.

The backup is hosted on MEGA rather than being stored in the Git repository. Its exact archive filename, partition contents, file size and SHA-256 checksum have **not been independently verified** here.

## Firmware compatibility — read before flashing

| Purpose | Build |
|---|---|
| Fix-pack development and testing | `S13_C29_EN_V1.6_20251121` |
| Optional external backup | `S13_WT_EN_V1.1_20260309` |

**These are not the same build.** Do not assume `boot`, `init_boot`, `vbmeta`, `vendor_boot`, firmware or other partition images can be swapped between them.

Before attempting restoration, identify your exact watch hardware revision and build, preserve original partitions, compare partition layout and sizes, determine the current A/B slot and AVB configuration, and confirm a recovery method is available. Flashing incompatible firmware can permanently impair or brick the watch.

If you only want to make your already-rooted S13 more usable, use [the easy installation guide](../00-START-HERE-EASY-GUIDE.md) instead. You do **not** need this firmware backup for that process.
