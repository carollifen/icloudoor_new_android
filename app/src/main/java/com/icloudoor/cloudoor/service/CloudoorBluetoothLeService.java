package com.icloudoor.cloudoor.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.icloudoor.cloudoor.icdcrypto.ICDCrypto;

import java.util.UUID;

/**
 * Created by flying on 2015/8/15.  api >= 18, version 4.3
 */
@SuppressWarnings("NewApi")
public class CloudoorBluetoothLeService extends Service {
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private LocalBinder mBinder = new LocalBinder();
    //蓝牙设备MAC地址，用于建立连接
    private String mBluetoothDeviceAddress;

    //app与BLE断开设备连接
    public static final String ACTION_GATT_DISCONNECTED = "com.cloudoor.bluetooth.le.ACTION_GATT_DISCONNECTED";
    //自定义开门事件
    public static final String ACTION_DO_OPEN_DOOR = "com.cloudoor.bluetooth.le.ACTION_DO_OPEN_DOOR";
    //BundleKey，对应BLE设备传递给app的值，据此判断是否成功开门
    public static final String EXTRA_DATA = "com.cloudoor.bluetooth.le.ACTION_EXTRA_DATA";

    //云门设备的通用唯一定位符
    private static final UUID UUID_CLOUDOOR_SERVICE = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
    //云门客户端属性通用唯一定位符
    private static final UUID UUID_CLOUDOOR_CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    //云门设备的接受消息属性通用唯一定位符
    private static final UUID UUID_CLOUDOOR_RECEIVE_CHARACTERISTIC = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
    //云门设备的发送消息属性通用唯一定位符
    private static final UUID UUID_CLOUDOOR_SEND_CHARACTERISTIC = UUID.fromString("0000fff4-0000-1000-8000-00805f9b34fb");


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    /**
     * 初始化mBluetoothAdapter
     *
     * @return
     */
    public boolean initialize() {
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        return mBluetoothAdapter != null;
    }

    /**
     * 连接BLE设备
     *
     * @param address
     * @return
     */
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            return false;
        }

        //先前连接设备，尝试重连
        //TODO 可能会连续发信号，需要测试
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress) && mBluetoothGatt != null) {
            return mBluetoothGatt.connect();
        }

        BluetoothDevice device;
        if (BluetoothAdapter.checkBluetoothAddress(address)) {
            device = mBluetoothAdapter.getRemoteDevice(address);
        } else {
            return false;
        }
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        mBluetoothDeviceAddress = address;
        return true;
    }

    /**
     * 断开与BLE设备的连接或者取消将要的连接，此方法是异步的，由mGattCallback的onConnectionStateChange方法回调
     */
    private void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.disconnect();
    }

    /**
     * 请求读取指定的属性，此方法是异步的，由mGattCallback的onCharacteristicRead方法回调
     *
     * @param characteristic
     */
    private void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    /**
     * 启动云门设备发送消息属性
     */
    private void setCharacteristicNotification() {
        if (mBluetoothGatt == null) {
            disconnect();
            return;
        }

        BluetoothGattService cloudoorService = mBluetoothGatt.getService(UUID_CLOUDOOR_SERVICE);
        if (cloudoorService == null) {
            disconnect();
            return;
        }

        BluetoothGattCharacteristic cloudoorSendCharacteristic = cloudoorService.getCharacteristic(UUID_CLOUDOOR_SEND_CHARACTERISTIC);
        if (cloudoorSendCharacteristic == null) {
            disconnect();
            return;
        }

        mBluetoothGatt.setCharacteristicNotification(cloudoorSendCharacteristic, true);

        BluetoothGattDescriptor descriptor = cloudoorSendCharacteristic.getDescriptor(UUID_CLOUDOOR_CLIENT_CHARACTERISTIC_CONFIG);
        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        mBluetoothGatt.writeDescriptor(descriptor);
    }

    /**
     * 从云门设备读数据
     */
    private void readReceiveCharacteristic() {
        BluetoothGattService cloudoorService = mBluetoothGatt.getService(UUID_CLOUDOOR_SERVICE);
        if (cloudoorService == null) {
            disconnect();
            return;
        }

        BluetoothGattCharacteristic cloudoorReceiveCharacteristic = cloudoorService.getCharacteristic(UUID_CLOUDOOR_RECEIVE_CHARACTERISTIC);
        if (cloudoorReceiveCharacteristic == null) {
            disconnect();
            return;
        }
        readCharacteristic(cloudoorReceiveCharacteristic);
    }

    /**
     * 写收到的数据写到设备
     */
    private void writeReceiveCharacteristic() {
        BluetoothGattService cloudoorService = mBluetoothGatt.getService(UUID_CLOUDOOR_SERVICE);
        if (cloudoorService == null) {
            disconnect();
            return;
        }

        BluetoothGattCharacteristic cloudoorReceiveCharacteristic = cloudoorService.getCharacteristic(UUID_CLOUDOOR_RECEIVE_CHARACTERISTIC);
        if (cloudoorReceiveCharacteristic == null) {
            disconnect();
            return;
        }
        cloudoorReceiveCharacteristic.setValue(ICDCrypto.encodeDoorOpenSignal(cloudoorReceiveCharacteristic.getValue()));
        mBluetoothGatt.writeCharacteristic(cloudoorReceiveCharacteristic);
    }


    /**
     * 使用完BLE设备后，调用此方法释放BLE设备资源
     */
    private void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    /**
     * 发送广播通知app有新的交互事件
     *
     * @param action
     * @param characteristic
     */
    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);
        if (UUID_CLOUDOOR_SEND_CHARACTERISTIC.equals(characteristic.getUuid())) {
            intent.putExtra(EXTRA_DATA, characteristic.getValue());
        }
        sendBroadcast(intent);
    }

    //app与BLE设备连接后的回调方法
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                broadcastUpdate(ACTION_GATT_DISCONNECTED);
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                readReceiveCharacteristic();
                setCharacteristicNotification();
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                writeReceiveCharacteristic();
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DO_OPEN_DOOR, characteristic);
        }
    };

    public class LocalBinder extends Binder {
        CloudoorBluetoothLeService getService() {
            return CloudoorBluetoothLeService.this;
        }
    }
}
