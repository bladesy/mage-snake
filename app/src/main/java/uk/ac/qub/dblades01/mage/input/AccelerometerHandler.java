package uk.ac.qub.dblades01.mage.input;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/* Provides polling access to the acceleration values of the device. */
public class AccelerometerHandler implements SensorEventListener {
    private float accelX, accelY, accelZ;

    /* context is used to obtain the SensorManager instance. */
    public AccelerometerHandler(Context context) {
        SensorManager sensorManager;
        Sensor accelerometer;

        /* Casting to SensorManager is required, as system services take the form of different
        objects. */
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        /* If the device has at least one accelerometer, take the first available and set its
        listener to this handler. */
        if(sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0) {
            accelerometer = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    /* Called when the accelerometer values change so that the acceleration values in this handler
    are kept updated. */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        accelX = sensorEvent.values[0];
        accelY = sensorEvent.values[1];
        accelZ = sensorEvent.values[2];
    }

    /* Required to fulfil the requirements of implementing SensorEventListener. */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /* Return the acceleration on the x-axis of this device. */
    public float getAccelX() {
        return accelX;
    }

    /* Return the acceleration on the y-axis of this device. */
    public float getAccelY() {
        return accelY;
    }

    /* Return the acceleration on the z-axis of this device. */
    public float getAccelZ() {
        return accelZ;
    }
}
