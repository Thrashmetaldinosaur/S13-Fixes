package de.robv.android.xposed;
public abstract class XC_MethodHook {
  protected void afterHookedMethod(MethodHookParam p) throws Throwable { }
  public static class Unhook { }
  public static class MethodHookParam { public Object thisObject; public Object[] args; }
}
