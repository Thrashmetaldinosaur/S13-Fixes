# Firmware Backup

The firmware backup is intentionally **not stored directly in the Git repository** because it is expected to exceed the repository's 25 MB per-file sharing target.

## Archived firmware

Expected release asset:

`S13_WT_EN_V1.1_20260309.zip`

Download it from this repository's **Releases** page under the release assets.

> The firmware backup is not the same firmware revision as the primary development watch.

Primary tested fix-pack firmware:

`S13_C29_EN_V1.6_20251121`

Archived firmware:

`S13_WT_EN_V1.1_20260309`

Do not assume boot-chain or partition images are interchangeable between these builds.

Before flashing firmware or individual partitions, verify the exact hardware revision, partition layout, image size, slot, and AVB state.

Once the firmware backup has been uploaded to Releases, this README should be updated with its exact file size and SHA-256 checksum.
