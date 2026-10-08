$ErrorActionPreference = "Stop"

$adb = ".\adb.exe"
$here = Split-Path -Parent $MyInvocation.MyCommand.Path

$device = (& $adb devices |
    Select-String "\sdevice$" |
    ForEach-Object { ($_ -split "\s+")[0] } |
    Select-Object -First 1)

if (-not $device) {
    throw "No ADB device found. Connect the S13 and authorize ADB first."
}

$apks = @(
    (Join-Path $here "01-FULLSCREEN-FIX\S13-YouTube-Fullscreen-Fix-v1.6.apk"),
    (Join-Path $here "02-SQUARE-TAB-FIX\S13-YouTube-SquareMode-TabFix-v9.apk"),
    (Join-Path $here "03-QUEUE-FIT\S13-YouTube-Queue-Fit-v1.apk")
)

Write-Host "Using S13: $device" -ForegroundColor Cyan

Write-Host "`n=== YOUTUBE VERSION ===" -ForegroundColor Cyan
& $adb -s $device shell "dumpsys package com.google.android.youtube" |
    Select-String "versionName=|versionCode="

foreach ($apk in $apks) {
    if (-not (Test-Path $apk)) {
        throw "Missing APK: $apk"
    }

    Write-Host "`nInstalling $(Split-Path $apk -Leaf)" -ForegroundColor Cyan
    & $adb -s $device install -r $apk

    if ($LASTEXITCODE -ne 0) {
        throw "Install failed: $apk"
    }
}

Write-Host "`n=== INSTALLED CUSTOM MODULES ===" -ForegroundColor Green

$packages = @(
    "com.s13.youtubefullscreenfix",
    "com.s13.youtubesquaremodefix",
    "com.s13.youtubequeuefitfixv1"
)

foreach ($pkg in $packages) {
    Write-Host "`n$pkg"
    & $adb -s $device shell "dumpsys package $pkg" |
        Select-String "versionName=|versionCode="
}

Write-Host ""
Write-Host "APK installation complete." -ForegroundColor Green
Write-Host "NEXT: In LSPosed, enable all three modules and scope ALL THREE to com.google.android.youtube ONLY, then reboot." -ForegroundColor Yellow
