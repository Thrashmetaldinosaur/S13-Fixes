$adb = ".\adb.exe"
$device = (& $adb devices | Select-String "\sdevice$" | ForEach-Object { ($_ -split "\s+")[0] } | Select-Object -First 1)
if (-not $device) { throw "No ADB device found." }

& $adb -s $device shell settings put global mobile_data_always_on 0
& $adb -s $device shell settings put global wifi_scan_always_enabled 0
& $adb -s $device shell settings put global ble_scan_always_enabled 0

Write-Host "S13 background scan settings restored." -ForegroundColor Green
