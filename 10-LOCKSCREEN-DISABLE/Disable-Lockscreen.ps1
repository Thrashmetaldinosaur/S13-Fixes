$adb = ".\adb.exe"
$device = (& $adb devices | Select-String "\sdevice$" | ForEach-Object { ($_ -split "\s+")[0] } | Select-Object -First 1)
if (-not $device) { throw "No ADB device found." }

& $adb -s $device shell "su -c 'locksettings set-disabled true'"
& $adb -s $device shell "su -c 'settings put secure lockscreen.disabled 1'"

& $adb -s $device shell locksettings get-disabled
& $adb -s $device shell settings get secure lockscreen.disabled
