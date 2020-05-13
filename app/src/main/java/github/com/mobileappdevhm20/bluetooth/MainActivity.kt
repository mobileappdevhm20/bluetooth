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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()


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

                }
            }
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
        10-> {
            if(resultCode==  RESULT_OK){
                text.text= "on"
            }else{
                text.text="off"
            }
        }

        }


  }
}
