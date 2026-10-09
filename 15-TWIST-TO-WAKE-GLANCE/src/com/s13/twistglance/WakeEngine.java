package com.s13.twistglance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;
import android.os.SystemClock;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * WIITE tilt wake-up: event-driven only, one listener, no polling or held wakelock.
 * v5.2: at least five seconds of quiet AFTER screen-off / glance completion.
 */
final class WakeEngine implements SensorEventListener {
    private static final long QUIET_AFTER_SCREEN_OFF_MS = 5000L;
    private static boolean registered;
    private static long nextEligibleWakeElapsed;

    private final PowerManager power;
    private final GlanceController glance;

    private WakeEngine(Context ctx) {
        power = (PowerManager) ctx.getSystemService(Context.POWER_SERVICE);
        glance = new GlanceController(ctx, power);

        // Covers both early glance sleep and the user's manual screen-off.
        // System broadcast is dynamically registered; the receiver lives only
        // in the WIITE process, where this class is scoped by LSPosed.
        try {
            ctx.registerReceiver(new BroadcastReceiver() {
                @Override public void onReceive(Context context, Intent intent) {
                    if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
                        pauseAfterScreenOff();
                        glance.onScreenOff();
                    }
                }
            }, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        } catch (Throwable t) {
            XposedBridge.log("S13GlanceV52: screen-off receiver unavailable; 5s glance-timeout cooldown still active");
            XposedBridge.log(t);
        }
    }

    static synchronized void pauseAfterScreenOff() {
        long until = SystemClock.elapsedRealtime() + QUIET_AFTER_SCREEN_OFF_MS;
        if (until > nextEligibleWakeElapsed) nextEligibleWakeElapsed = until;
    }

    private static synchronized boolean reserveWake() {
        long now = SystemClock.elapsedRealtime();
        if (now < nextEligibleWakeElapsed) return false;
        // Also suppress duplicate callbacks while Android is waking.
        nextEligibleWakeElapsed = now + QUIET_AFTER_SCREEN_OFF_MS;
        return true;
    }

    static synchronized void install(Context ctx) {
        if (registered) return;
        SensorManager sensors = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        if (sensors == null) {
            XposedBridge.log("S13GlanceV52: no SensorManager");
            return;
        }
        // TYPE_TILT_DETECTOR = 22; hidden Android constant not exposed by SDK.
        Sensor tilt = sensors.getDefaultSensor(22, true);
        if (tilt == null || !tilt.isWakeUpSensor()) {
            XposedBridge.log("S13GlanceV52: wake-up tilt sensor missing");
            return;
        }
        WakeEngine engine = new WakeEngine(ctx);
        boolean ok = sensors.registerListener(engine, tilt, SensorManager.SENSOR_DELAY_NORMAL);
        if (ok) {
            registered = true;
            XposedBridge.log("S13GlanceV52: registered=true; sensor=" + tilt.getName());
        } else {
            XposedBridge.log("S13GlanceV52: registered=false");
        }
    }

    @Override public void onSensorChanged(SensorEvent event) {
        try {
            if (power == null || power.isInteractive() || !reserveWake()) return;
            // Preserve the verified WIITE-vendor-process wake mechanism.
            XposedHelpers.callMethod(power, "wakeUp", SystemClock.uptimeMillis());
            XposedBridge.log("S13GlanceV52: wrist wake requested");
            glance.showOnMainThread();
        } catch (Throwable t) {
            XposedBridge.log("S13GlanceV52: wake error");
            XposedBridge.log(t);
        }
    }

    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) { }
}
