package github.com.mobileappdevhm20.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var bluetoothAdapter: BluetoothAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()


        val rollButton: Button = findViewById(R.id.button5)
        rollButton.text = "Let's Roll"
        rollButton.setOnClickListener {
            Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
            if (bluetoothAdapter == null) {
                // Device doesn't support Bluetooth
                Toast.makeText(this, "Bluetooth not support", Toast.LENGTH_SHORT).show()

            } else {
                if (!bluetoothAdapter?.isEnabled) {
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    startActivityForResult(enableBtIntent, 10)
                    Toast.makeText(this, "Turned on", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "Already on", Toast.LENGTH_LONG).show();

                }
            }
        }
        val rollButton1: Button = findViewById(R.id.button6)
        rollButton1.setOnClickListener {
            Toast.makeText(this, "button chool", Toast.LENGTH_SHORT).show()
            if (bluetoothAdapter == null) {
                // Device doesn't support Bluetooth
                Toast.makeText(this, "Bluetooth not support", Toast.LENGTH_SHORT).show()

            } else {
                bluetoothAdapter.disable()
                Toast.makeText(this, "Turned off", Toast.LENGTH_LONG).show();

            }
        }


        val rollButton2: Button = findViewById(R.id.button7)
        rollButton2.setOnClickListener {
            Toast.makeText(this, "button discover", Toast.LENGTH_SHORT).show()
            val discoverableIntent: Intent =
                Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                    putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
                }
            startActivity(discoverableIntent)
        }

        val rollButton60: Button = findViewById(R.id.button)
        rollButton60.setOnClickListener {
            Toast.makeText(this, "bframgment", Toast.LENGTH_SHORT).show()
            //s  val ft: FragmentTransaction = supportFragmentManager.beginTransaction()


            // Launch the DeviceListActivity to see devices and do scan
            val serverIntent = Intent(this, DeviceListActivity::class.java)
            startActivityForResult(
                serverIntent,
11
            )



        }


        var pairedDevices1: MutableList<String> = ArrayList()
        var listView: ListView = findViewById(R.id.listView)
        val rollButton3: Button = findViewById(R.id.button2)
        rollButton3.setOnClickListener {
            Toast.makeText(this, "button discover", Toast.LENGTH_SHORT).show()
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            pairedDevices?.forEach { device ->
                val deviceName = device.name
                val deviceHardwareAddress = device.address // MAC address
                Toast.makeText(
                    this,
                    "button discover $deviceName $deviceHardwareAddress",
                    Toast.LENGTH_SHORT
                ).show()
                pairedDevices1.add(deviceName)

            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, pairedDevices1)
            listView.adapter = adapter

        }


        // Register for broadcasts when a device is discovered.
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)
        val rollButton4: Button = findViewById(R.id.button3)
        rollButton4.setOnClickListener {
            Toast.makeText(this, "button discover", Toast.LENGTH_SHORT).show()

            if (bluetoothAdapter != null) {
                bluetoothAdapter.startDiscovery()
            }
        }

    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device.name
                    val deviceHardwareAddress = device.address // MAC address
                    val text: TextView = findViewById(R.id.textView)

                    text.text = "deviceName"
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        if (bluetoothAdapter == null) {
            return
        }
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!bluetoothAdapter.isEnabled()) {
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, 10)
            // Otherwise, setup the chat session
        }
    }

    override fun onDestroy() {
        super.onDestroy()


        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val text: TextView = findViewById(R.id.label)
        when (requestCode) {
            10 -> {
                if (resultCode == RESULT_OK) {
                    text.text = "on"
                } else {
                    text.text = "off"
                }
            }
            11->{

                // Get the device MAC address
                val extras = data!!.extras ?: return
                val address =
                    extras.getString("device_address")
                Toast.makeText(this, "button clicked ${address}", Toast.LENGTH_SHORT).show()
            }

        }


    }
}
