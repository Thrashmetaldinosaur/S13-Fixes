# Large Files and External Downloads

To keep every file committed to the Git repository under 25 MB, the required oversized CloX APK is distributed from **GitHub Releases**. The optional firmware backup is separately hosted on **MEGA**.

## Required large file

### CloX v6.3

Filename:

`CloX_v6.3.apk`

Size:

`61,937,774 bytes` (~59.1 MiB)

SHA-256:

`da7cf30e9d81aa1d096519d3e0697a77bc43d171242ec361b8625fb51408ce7f`

After downloading it from Releases, place it here:

`12-DEPENDENCIES/CloX_v6.3.apk`

The automatic installer checks for this file before starting.

## Optional S13 firmware backup — MEGA

**[Download S13_WT_EN_V1.1_20260309 (MEGA)](https://mega.nz/file/9uRgALhY#ZJstwhSb1AQV3osDAIYPg4tMg15jr39jgXYGzp6lKzo)**

This backup is **hosted on MEGA**, not bundled in the Git repository or GitHub Releases. It is not needed for normal installation of the S13 fixes.

The archived build (`S13_WT_EN_V1.1_20260309`) differs from the build tested for these fixes (`S13_C29_EN_V1.6_20251121`). Do not flash partitions from one onto the other without independently checking hardware, partitions and AVB compatibility.

The external backup's internal contents, size and SHA-256 have not yet been inspected or verified here. See [`Firmware Backup/README.md`](Firmware%20Backup/README.md) for precautions.

## YouTube

Google's YouTube APK is **not redistributed** in this project.

The known-good version used during testing was:

`YouTube 21.39.523`

Package:

`com.google.android.youtube`

Users should obtain that version themselves from a source they trust.
