# S13 Breezy-CloX Bridge v1.0

Package: `com.s13.breezycloxbridge`

This is the LIVE APK pulled from the working watch.

It contains both:
- Breezy Weather → CloX weather bridging
- crown-button controls

Confirmed crown behavior in the final build:
- single press → Back
- double press → toggle circle/square mode
- hold ~700 ms → toggle rotation

The square/circle toggle uses:
`settings put system watch_small_screen_stat`

Setup:
1. Install the APK.
2. Allow its root request when prompted.
3. Enable `S13 Breezy-CloX Bridge / CrownKeyService` as an Accessibility service.
4. In Breezy Weather, configure the bridge as the weather-sharing/provider target used by the app.
5. Keep CloX installed.

The APK contains the final `CrownKeyService`, `WeatherReceiver`, `CloXBridge`, and rotation-toggle implementation.
