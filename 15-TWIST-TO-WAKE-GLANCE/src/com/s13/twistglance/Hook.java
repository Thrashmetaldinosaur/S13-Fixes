package com.s13.twistglance;

import android.app.Application;
import android.content.Context;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public final class Hook implements IXposedHookLoadPackage {
    private static final String TARGET = "com.wiite.wearhealthuart";

    @Override public void handleLoadPackage(XC_LoadPackage.LoadPackageParam p) {
        if (!TARGET.equals(p.packageName) || !TARGET.equals(p.processName)) return;
        // Avoid system_server. Stay within the known-working WIITE process.
        try {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class,
                new XC_MethodHook() {
                    @Override protected void afterHookedMethod(MethodHookParam param) {
                        try {
                            WakeEngine.install((Context) param.args[0]);
                        } catch (Throwable t) {
                            XposedBridge.log("S13GlanceV5: init failed; no other apps affected");
                            XposedBridge.log(t);
                        }
                    }
                });
            XposedBridge.log("S13GlanceV5: WIITE host hook attached");
        } catch (Throwable t) {
            XposedBridge.log(t);
        }
    }
}
