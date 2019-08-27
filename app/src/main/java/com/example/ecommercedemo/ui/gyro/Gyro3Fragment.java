package com.example.ecommercedemo.ui.gyro;


import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ecommercedemo.R;

import static android.content.Context.SENSOR_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Gyro3Fragment extends Fragment {
    Sensor accelerometer;
    Sensor magnetometer;
    Sensor vectorSensor;
    DeviceOrientation deviceOrientation;
    private SensorManager mSensorManager;

    public Gyro3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gyro3, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        deviceOrientation = new DeviceOrientation();
    }

    @Override
    public void onResume() {
        super.onResume();

        mSensorManager.registerListener(deviceOrientation.getEventListener(), accelerometer, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(deviceOrientation.getEventListener(), magnetometer, SensorManager.SENSOR_DELAY_UI);

        int orientation = deviceOrientation.getOrientation();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(deviceOrientation.getEventListener());

    }
}
