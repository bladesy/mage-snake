package uk.ac.qub.dblades01.mage;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class CompassHandler implements SensorEventListener {
    private float yaw, pitch, roll;
    private float[] accelValues = new float[3],
            magnetValues = new float[3],
            rotationValues = new float[9],
            orientationValues = new float[3];
    private boolean accelValuesSet = false, magnetValuesSet = false;
    private Sensor accelerometer, magnetometer;

    public CompassHandler(Context context) {
        SensorManager sensorManager;

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0)
            accelerometer = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        if(sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).size() > 0)
            magnetometer = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).get(0);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor == accelerometer) {
            System.arraycopy(sensorEvent.values, 0, accelValues, 0, sensorEvent.values.length);
            accelValuesSet = true;
        }
        else if(sensorEvent.sensor == magnetometer) {
            System.arraycopy(sensorEvent.values, 0, magnetValues, 0, sensorEvent.values.length);
            magnetValuesSet = true;
        }

        if(accelValuesSet && magnetValuesSet) {
            SensorManager.getRotationMatrix(rotationValues, null, accelValues, magnetValues);
            SensorManager.getOrientation(rotationValues, orientationValues);

            yaw = orientationValues[0];
            pitch = orientationValues[1];
            roll = orientationValues[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getRoll() {
        return roll;
    }
}
