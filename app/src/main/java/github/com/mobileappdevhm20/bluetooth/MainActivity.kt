package github.com.mobileappdevhm20.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    lateinit var bluetoothAdapter: BluetoothAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter != null) {
            val text: TextView = findViewById(R.id.label)
            if (!bluetoothAdapter?.isEnabled) {
                text.text = "on"
            }else{
                text.text = "off"
            }
        }


    }
//Turn on Bluetooth
    fun on(view: View) {
        if (!bluetoothAdapter?.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 10)
            Toast.makeText(this, "Turned on", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Already on", Toast.LENGTH_LONG).show();
        }
    }
//Turn off Bluetooth
    fun off(view: View) {
        bluetoothAdapter.disable()
        Toast.makeText(this, "Turned off", Toast.LENGTH_LONG).show();

    }
//Make the Device Discoverable. Other devices can find the device and can connect
    fun makeDiscoverable(view: View) {
        Toast.makeText(this, "button discover", Toast.LENGTH_SHORT).show()
        val discoverableIntent: Intent =
            Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE).apply {
                putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
            }
        startActivity(discoverableIntent)
    }
//Show all Paired Devices or scan for new Devices
    fun select(view: View) {
        Toast.makeText(this, "bframgment", Toast.LENGTH_SHORT).show()

        // Launch the DeviceListActivity to see devices and do scan
        val serverIntent = Intent(this, DeviceListActivity::class.java)
        startActivityForResult(
            serverIntent,
            11
        )
    }


    override fun onStart() {
        super.onStart()

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!bluetoothAdapter.isEnabled()) {
            val enableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableIntent, 10)
            // Otherwise, setup the chat session
        }
    }

    override fun onResume() {
        super.onResume()
        var BluetoothService = MyBluetoothService(mHandler)
    }

// Handles Return Values from Intents
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
            11 -> {

                // Get the device MAC address
                val extras = data!!.extras ?: return
                val address =
                    extras.getString("device_address")
                Toast.makeText(this, "button clicked ${address}", Toast.LENGTH_SHORT).show()
            }

        }


    }

    private fun changeTextfield(m: String) {
        Log.i("changeTextfield", "Message  $m")

        val text: TextView = findViewById(R.id.label1)

        text.text = "$m"
        m.substring(0, 10)
        Toast.makeText(this, "changeTextfield ${m.substring(0, 10)}", Toast.LENGTH_SHORT)

    }
//Different handlers called from the bluetooth -Thread when receiving or sending messages
    private val mHandler = object : Handler() {
        val MESSAGE_READ: Int = 0
        val MESSAGE_WRITE: Int = 1
        val MESSAGE_TOAST: Int = 2
        override fun handleMessage(msg: Message?) {
            Log.i("handler", "handle message $msg")
            when (msg?.what) {
                MESSAGE_READ -> {
                    val writeBuf = msg.obj as ByteArray
                    // construct a string from the buffer
                    // construct a string from the buffer
                    val writeMessage = String(writeBuf)
                    Log.i("handler", "receive Message  $writeMessage")
                    changeTextfield(writeMessage)
                }
                MESSAGE_WRITE -> {
                }
                MESSAGE_TOAST -> {
                }
            }
        }
    }

}
