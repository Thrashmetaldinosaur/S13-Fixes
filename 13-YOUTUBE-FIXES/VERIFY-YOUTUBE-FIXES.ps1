$adb = ".\adb.exe"

$device = (& $adb devices |
    Select-String "\sdevice$" |
    ForEach-Object { ($_ -split "\s+")[0] } |
    Select-Object -First 1)

if (-not $device) {
    throw "No ADB device found."
}

$pkgs = @(
    "com.s13.youtubefullscreenfix",
    "com.s13.youtubesquaremodefix",
    "com.s13.youtubequeuefitfixv1",
    "com.google.android.youtube"
)

foreach ($pkg in $pkgs) {
    Write-Host "`n=== $pkg ===" -ForegroundColor Cyan

    $path = & $adb -s $device shell "pm path $pkg"

    if (-not $path) {
        Write-Host "NOT INSTALLED" -ForegroundColor Red
        continue
    }

    & $adb -s $device shell "dumpsys package $pkg" |
        Select-String "versionName=|versionCode="
}

Write-Host "`n=== LSPOSED LOG REFERENCES ===" -ForegroundColor Cyan

& $adb -s $device shell "su -c 'for f in /data/adb/lspd/log/modules_*.log; do grep -iE `"youtubefullscreenfix|youtubesquaremodefix|youtubequeuefitfixv1|S13YT`" `$f 2>/dev/null; done'"

Write-Host "`nManual LSPosed check:" -ForegroundColor Yellow
Write-Host "Fullscreen Fix, Square Tab Fix and Queue Fit must all be enabled and scoped ONLY to com.google.android.youtube."
