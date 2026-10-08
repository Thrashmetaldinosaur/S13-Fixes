# Real RAM / storage reporting

The working watch currently has BOTH modules enabled:

- `s13_real_ram_storage` v1.0
- `s13_real_storage` v1.0

Both force the vendor memory-profile node to NORMAL (`0`) so the firmware stops presenting the fake 8 GB / ~200 GB capacity profile.

Expected real hardware values from the original work:
- roughly 3 GB physical RAM
- roughly 26–27 GB usable userdata

For exact replication of the current watch, install both live module ZIPs and reboot.

These modules do not repartition or format the watch.
