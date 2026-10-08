$adb = ".\adb.exe"
$pkg = "com.s13.breezycloxbridge"
$svc = "com.s13.breezycloxbridge/com.s13.breezycloxbridge.CrownKeyService"

$device = (& $adb devices | Select-String "\sdevice$" | ForEach-Object { ($_ -split "\s+")[0] } | Select-Object -First 1)
if (-not $device) { throw "No ADB device found." }

& $adb -s $device shell appops set $pkg WRITE_SETTINGS allow

$current = (& $adb -s $device shell settings get secure enabled_accessibility_services).Trim()
if ($current -eq "null" -or [string]::IsNullOrWhiteSpace($current)) {
    $new = $svc
} elseif ($current -notlike "*$svc*") {
    $new = "$current`:$svc"
} else {
    $new = $current
}

& $adb -s $device shell settings put secure enabled_accessibility_services $new
& $adb -s $device shell settings put secure accessibility_enabled 1

Write-Host "Bridge accessibility service enabled." -ForegroundColor Green
