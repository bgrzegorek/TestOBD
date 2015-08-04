package com.testobd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Bartek on 2015-07-30.
 */
public class BluetoothConnection {

    private Context context;

    private String device_address;
    private BluetoothDevice device;
    private BluetoothAdapter adapter;
    private BluetoothSocket socket;

    private final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final int REQUEST_ENABLE_BT = 1;


    public BluetoothConnection(Context context, String device_address){
        this.device_address = device_address;
        this.context = context;
        //connect();
    }

    private void connect() {
        adapter = BluetoothAdapter.getDefaultAdapter();
        device = adapter.getRemoteDevice(device_address);

        try {
            socket = device.createInsecureRfcommSocketToServiceRecord(uuid);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            socket = null;
        }
    }

    public BluetoothSocket getSocket(){
        connect();
        return socket;
    }
}
