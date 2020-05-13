# [Using Bluetooth in an app](https://developer.android.com/guide/topics/connectivity/bluetooth) (19.06.2020) Nicole Gertz, David Haupenthal
## 1. Motivation and introduction to the topic
## 2. Technical background
## 3. Implementation/application on a mobile platform
## 4. Short demonstration (live programming or similar)
## 5. Summary


Präsentatition: https://1drv.ms/p/s!AhncPad3R28fmP5yfWNLoO2TMXua0g?e=TAz7i9

https://www.youtube.com/watch?v=jzxZUJmOu3o


Folie 1:
Problem mit Kabel

Folie 2:
Lösung wireless

Folie3
8 Geräte
2.4 ghz
Folie4: 
harald bluetooth 

folie 5 
live programming
permissions: android manifest. uses Bluetooth
find device, paring 
security
austausch daten


# 1. Setting up Bluetooth
```xml
<manifest ... >
  <uses-permission android:name="android.permission.BLUETOOTH" />
  <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />

  <!-- If your app targets Android 9 or lower, you can declare
       ACCESS_COARSE_LOCATION instead. -->
  <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
  ...
</manifest>
```
Bluetooth support
```kotlin
        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
   if (bluetoothAdapter == null) {
                // Device doesn't support Bluetooth

            } else {
            // Device support Bluetooth
            }
```
Bluetooth turn on
```kotlin
                if (!bluetoothAdapter?.isEnabled) {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(enableBtIntent, 0)
                    Toast.makeText(this, "Turned on", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "Already on", Toast.LENGTH_LONG).show();

                }
```
Bluetooth turn off
```kotlin
    bluetoothAdapter.disable()
```
Make Device avaible
```kotlin
          val discoverableIntent: Intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
            }
            startActivity(discoverableIntent)
```

Paired Devices
```kotlin
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            pairedDevices?.forEach { device ->
                val deviceName = device.name
                val deviceHardwareAddress = device.address // MAC address
                Toast.makeText(this, "button discover $deviceName $deviceHardwareAddress", Toast.LENGTH_SHORT).show()

            }
```

# 2. Scanning for other Bluetooth devices
# 3. Querying the local Bluetooth adapter for paired Bluetooth devices
# 4. Establishing RFCOMM channels/sockets
# 5. Connecting to a remote device
# 6. Transfering data over Bluetooth