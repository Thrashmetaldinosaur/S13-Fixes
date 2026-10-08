# Root backup

These images are from the S13/rooting work for build `S13_C29_EN_V1.6_20251121`.

Included:
- `S13_CURRENT_ROOTED_init_boot_a.img` — live rooted `init_boot_a` pulled from the watch.
- `S13_CURRENT_vbmeta_a.img` — live vbmeta_a.
- `S13_CURRENT_vbmeta_system_a.img` — live vbmeta_system_a.
- `S13_STOCK_init_boot_a.img` — original stock init_boot backup, when available.
- `ROOT_GUIDE_ORIGINAL.md` — original detailed ASR8601 root procedure.

The successful root path used the ASR8601 low-level USB interface rather than ordinary Android/Recovery reads. Keep these images tied to this exact firmware unless the AVB chain is revalidated.

Do not use the rooted image as a generic S13 image for other firmware builds.
