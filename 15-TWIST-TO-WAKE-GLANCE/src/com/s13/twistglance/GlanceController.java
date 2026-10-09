package com.s13.twistglance;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.BatteryManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/** Cosmetic wrist-glance overlay, NOT an authentication/security lock screen. */
final class GlanceController {
    private static final long GLANCE_MS = 5000L;
    private final Context context;
    private final PowerManager power;
    private final Handler main = new Handler(Looper.getMainLooper());
    private final WindowManager windows;
    private View overlay;
    private Runnable timeout;

    GlanceController(Context ctx, PowerManager power) {
        this.context = ctx;
        this.power = power;
        this.windows = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
    }

    void showOnMainThread() { main.post(this::show); }

    void onScreenOff() { main.post(this::dismiss); }

    private void show() {
        if (overlay != null || windows == null) return;
        try {
            // Check the host WIITE process, not the standalone LSPosed module.
            if (!Settings.canDrawOverlays(context)) {
                XposedBridge.log("S13GlanceV53: WIITE overlay permission unavailable");
                return;
            }
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            int sidePx = Math.min(dm.widthPixels, dm.heightPixels);
            if (sidePx < 200) sidePx = Math.max(dm.widthPixels, dm.heightPixels);

            LinearLayout panel = new LinearLayout(context);
            panel.setGravity(Gravity.CENTER);
            panel.setOrientation(LinearLayout.VERTICAL);
            panel.setBackgroundColor(Color.BLACK);
            panel.setPadding(Math.round(sidePx * .015f), 0, Math.round(sidePx * .015f), 0);

            Typeface face = Typeface.create("sans-serif-condensed", Typeface.BOLD);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setTypeface(face);
            paint.setTextSize(100f);
            // Size from true pixel width, avoiding TextClock autosize's wrap-content shrink.
            // '23:59' is intentionally a wide, conservative five-character reference.
            float sampleWidth = Math.max(paint.measureText("23:59"), paint.measureText("12:59"));
            float clockPx = sampleWidth > 0 ? (sidePx * .91f * 100f / sampleWidth) : (sidePx * .32f);
            clockPx = Math.min(clockPx, sidePx * .41f);
            clockPx = Math.max(clockPx, sidePx * .24f);

            TextClock time = new TextClock(context);
            time.setFormat12Hour("h:mm");
            time.setFormat24Hour("HH:mm");
            time.setTextColor(Color.WHITE);
            time.setTypeface(face);
            time.setTextSize(TypedValue.COMPLEX_UNIT_PX, clockPx);
            time.setIncludeFontPadding(false);
            time.setSingleLine(true);
            time.setGravity(Gravity.CENTER);
            panel.addView(time, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            TextClock date = new TextClock(context);
            date.setFormat12Hour("EEEE, MMM d");
            date.setFormat24Hour("EEEE, MMM d");
            date.setTextColor(0xffb8c3d0);
            date.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            date.setTextSize(TypedValue.COMPLEX_UNIT_PX, sidePx * .071f);
            date.setSingleLine(true);
            date.setGravity(Gravity.CENTER);
            panel.addView(date);

            // Single capacity read per glance. No battery polling, receiver, or wakelock.
            // BatteryManager returns a percentage or MIN_VALUE if unsupported.
            try {
                BatteryManager battery = (BatteryManager)
                        context.getSystemService(Context.BATTERY_SERVICE);
                if (battery != null) {
                    int pct = battery.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                    if (pct >= 0 && pct <= 100) {
                        TextView batteryText = new TextView(context);
                        batteryText.setText("BATTERY  " + pct + "%");
                        batteryText.setTextColor(0xffb8c3d0);
                        batteryText.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                        batteryText.setTextSize(TypedValue.COMPLEX_UNIT_PX, sidePx * .065f);
                        batteryText.setGravity(Gravity.CENTER);
                        batteryText.setPadding(0, Math.round(sidePx * .020f), 0, 0);
                        panel.addView(batteryText);
                    } else {
                        XposedBridge.log("S13GlanceV53: capacity unavailable");
                    }
                }
            } catch (Throwable t) {
                // The clock must still work if the battery service behaves differently.
                XposedBridge.log("S13GlanceV53: battery read skipped");
            }

            TextView hint = new TextView(context);
            hint.setText("TAP TO OPEN");
            hint.setTextColor(0xff8f98a4);
            hint.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            hint.setTextSize(TypedValue.COMPLEX_UNIT_PX, sidePx * .045f);
            hint.setGravity(Gravity.CENTER);
            hint.setPadding(0, Math.round(sidePx * .045f), 0, 0);
            panel.addView(hint);

            panel.setClickable(true);
            panel.setOnClickListener(v -> {
                dismiss();
                XposedBridge.log("S13GlanceV53: single tap -> CloX");
            });

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                            | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                    PixelFormat.OPAQUE);
            params.gravity = Gravity.CENTER;
            params.setTitle("S13 Glance v5.3");
            windows.addView(panel, params);
            overlay = panel;
            timeout = () -> {
                if (overlay != panel) return;
                // The old code dismissed BEFORE requesting sleep, revealing CloX.
                // Keep the black overlay visible through the sleep transition.
                WakeEngine.pauseAfterScreenOff();
                if (power != null && power.isInteractive()) {
                    try {
                        XposedHelpers.callMethod(power, "goToSleep", SystemClock.uptimeMillis());
                        XposedBridge.log("S13GlanceV53: five-second sleep requested");
                    } catch (Throwable t) {
                        XposedBridge.log("S13GlanceV53: sleep rejected; normal 30s system timeout applies");
                        XposedBridge.log(t);
                    }
                }
                // SCREEN_OFF receiver also dismisses; this is a safety fallback
                // if the firmware doesn't deliver the system broadcast.
                main.postDelayed(() -> { if (overlay == panel) dismiss(); }, 900L);
            };
            main.postDelayed(timeout, GLANCE_MS);
            XposedBridge.log("S13GlanceV53: overlay displayed; sizePx=" + Math.round(clockPx)
                    + "; 5s timer; cooldown 5s after screen-off");
        } catch (Throwable t) {
            XposedBridge.log("S13GlanceV53: overlay failed; vendor wake retained");
            XposedBridge.log(t);
            dismiss();
        }
    }

    private void dismiss() {
        if (timeout != null) main.removeCallbacks(timeout);
        timeout = null;
        View prior = overlay;
        overlay = null;
        if (prior != null && windows != null) {
            try { windows.removeViewImmediate(prior); }
            catch (Throwable t) { XposedBridge.log(t); }
        }
    }
}
