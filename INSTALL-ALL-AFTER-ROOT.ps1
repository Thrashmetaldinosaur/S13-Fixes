param(
    [switch]$SkipBuildCheck
)

$ErrorActionPreference = "Stop"
$adb = ".\adb.exe"
$pack = Split-Path -Parent $MyInvocation.MyCommand.Path
$expectedBuild = "S13_C29_EN_V1.6_20251121"

$cloxRequired = Join-Path $pack "12-DEPENDENCIES\CloX_v6.3.apk"
if (-not (Test-Path $cloxRequired)) {
    Write-Host ""
    Write-Host "MISSING LARGE DEPENDENCY: CloX_v6.3.apk" -ForegroundColor Red
    Write-Host "Download CloX_v6.3.apk from this repository's GitHub Releases page." -ForegroundColor Yellow
    Write-Host "Then place it here:" -ForegroundColor Yellow
    Write-Host $cloxRequired -ForegroundColor Yellow
    Write-Host ""
    throw "CloX_v6.3.apk is required before installation."
}


function Run-Adb {
    param([Parameter(ValueFromRemainingArguments=$true)][string[]]$Args)
    & $adb @Args
    if ($LASTEXITCODE -ne 0) {
        throw "ADB command failed: $($Args -join ' ')"
    }
}

Write-Host "=== S13 COMMUNITY FIX INSTALLER ===" -ForegroundColor Cyan

$device = (& $adb devices |
    Select-String "\sdevice$" |
    ForEach-Object { ($_ -split "\s+")[0] } |
    Select-Object -First 1)

if (-not $device) {
    throw "No ADB device found. Connect/authorize the S13 first."
}

Write-Host "Device: $device"

$build = (& $adb -s $device shell getprop ro.build.display.id).Trim()
$android = (& $adb -s $device shell getprop ro.build.version.release).Trim()

Write-Host "Android: $android"
Write-Host "Build:   $build"

if (-not $SkipBuildCheck -and $build -ne $expectedBuild) {
    throw "Build mismatch. Expected $expectedBuild. Re-run with -SkipBuildCheck only if you understand the compatibility risk."
}

$rootCheck = (& $adb -s $device shell "su -c 'id'" 2>$null) -join "`n"
if ($rootCheck -notmatch "uid=0") {
    throw "Root is not working. Finish the root/Magisk setup before running this installer."
}

Write-Host "Root: OK" -ForegroundColor Green

$modules = @(
    "02-USB-MTP-ADB\S13_MTP_ADB_Fix_LIVE_v1.0.zip",
    "03-REAL-RAM-STORAGE\S13_Real_RAM_Storage_LIVE_v1.0.zip",
    "03-REAL-RAM-STORAGE\S13_Real_Storage_LIVE_v1.0.zip",
    "05-CROWN-ROTATION-VOLUME\S13_Crown_Volume_Service_LIVE_v1.0.zip",
    "08-ASR5803-WIFI-POWER-SAVE\S13_ASR5803_Power_Save_Fix_LIVE_v1.1.zip"
)

Write-Host "`n=== INSTALL MAGISK MODULES ===" -ForegroundColor Cyan

foreach ($rel in $modules) {
    $local = Join-Path $pack $rel
    if (-not (Test-Path $local)) {
        throw "Missing file: $local"
    }

    $name = Split-Path $local -Leaf
    $remote = "/data/local/tmp/$name"

    Write-Host "Installing $name"
    Run-Adb -s $device push $local $remote
    Run-Adb -s $device shell "su -c 'magisk --install-module `"$remote`"'"
    Run-Adb -s $device shell "rm -f `"$remote`""
}

$apks = @(
    "12-DEPENDENCIES\Breezy_Weather_v6.2.2.apk",
    "12-DEPENDENCIES\CloX_v6.3.apk",
    "04-BREEZY-CLOX-BRIDGE-AND-CROWN\S13_Breezy_CloX_Bridge_v1.0.apk",
    "05-CROWN-ROTATION-VOLUME\S13_Crown_Volume_v1.3.apk",
    "05-CROWN-ROTATION-VOLUME\S13_Crown_Scroll_Block_v1.0.apk",
    "06-CIRCLE-MODE-SCALE\S13_Circle_Scale_v1.1.apk",
    "07-CLOX-WAKE-FIX\S13_CloX_Wake_Fix_v2.0.apk",
    "13-YOUTUBE-FIXES\01-FULLSCREEN-FIX\S13-YouTube-Fullscreen-Fix-v1.6.apk",
    "13-YOUTUBE-FIXES\02-SQUARE-TAB-FIX\S13-YouTube-SquareMode-TabFix-v9.apk",
    "13-YOUTUBE-FIXES\03-QUEUE-FIT\S13-YouTube-Queue-Fit-v1.apk"
)

Write-Host "`n=== INSTALL APKs ===" -ForegroundColor Cyan

foreach ($rel in $apks) {
    $local = Join-Path $pack $rel
    if (-not (Test-Path $local)) {
        throw "Missing file: $local"
    }

    Write-Host "Installing $(Split-Path $local -Leaf)"
    Run-Adb -s $device install -r $local
}

Write-Host "`n=== APPLY BACKGROUND POWER SETTINGS ===" -ForegroundColor Cyan

Run-Adb -s $device shell settings put global mobile_data_always_on 0
Run-Adb -s $device shell settings put global wifi_scan_always_enabled 0
Run-Adb -s $device shell settings put global ble_scan_always_enabled 0

Write-Host "`n=== ENABLE BREEZY/CLOX CROWN ACCESSIBILITY SERVICE ===" -ForegroundColor Cyan

$svc = "com.s13.breezycloxbridge/com.s13.breezycloxbridge.CrownKeyService"
$current = (& $adb -s $device shell settings get secure enabled_accessibility_services).Trim()

if ($current -eq "null" -or [string]::IsNullOrWhiteSpace($current)) {
    $new = $svc
}
elseif ($current -notlike "*$svc*") {
    $new = "$current`:$svc"
}
else {
    $new = $current
}

Run-Adb -s $device shell settings put secure enabled_accessibility_services $new
Run-Adb -s $device shell settings put secure accessibility_enabled 1

Write-Host "`n==================================================" -ForegroundColor Green
Write-Host "FILES INSTALLED SUCCESSFULLY" -ForegroundColor Green
Write-Host "==================================================" -ForegroundColor Green
Write-Host ""
Write-Host "YOU ARE NOT FINISHED YET." -ForegroundColor Yellow
Write-Host ""
Write-Host "Open LSPosed and configure these scopes:" -ForegroundColor Yellow
Write-Host ""
Write-Host "Android System Framework (android):"
Write-Host "  - S13 Crown Volume v1.3"
Write-Host "  - S13 Crown Scroll Block v1.0"
Write-Host "  - S13 Circle Scale v1.1"
Write-Host "  - S13 CloX Wake Fix v2.0"
Write-Host ""
Write-Host "YouTube ONLY (com.google.android.youtube):"
Write-Host "  - S13 YouTube Fullscreen Fix v1.6"
Write-Host "  - S13 YouTube Square-Mode Tab Fix v9"
Write-Host "  - S13 YouTube Queue Fit v1"
Write-Host ""
Write-Host "Then REBOOT the watch." -ForegroundColor Yellow
Write-Host "After reboot, follow 00-START-HERE-EASY-GUIDE.md for testing."
