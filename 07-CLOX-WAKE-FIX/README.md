# S13 CloX Wake Fix v2.0

Package: `com.s13.cloxwakefix`

Final LSPosed module pulled from the working watch.

It hooks the System Framework and blocks the specific CloX wake request:
`clox:notif_wake`

It is intentionally narrow: it stops CloX notification-related random wakes without broadly disabling normal/manual wake behavior.

Install:
1. Install the APK.
2. Enable it in LSPosed.
3. Scope it to Android System Framework (`android`).
4. Reboot.
