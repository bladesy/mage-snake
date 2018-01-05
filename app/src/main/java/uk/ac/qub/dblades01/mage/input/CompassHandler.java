package uk.ac.qub.dblades01.mage.input;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/* Provides polling access to the orientation of the device. */
public class CompassHandler implements SensorEventListener {
    private float yaw, pitch, roll;
    private float[] accelValues = new float[3],
            magnetValues = new float[3],
            rotationValues = new float[9],
            orientationValues = new float[3];
    private boolean accelValuesSet = false, magnetValuesSet = false;
    private Sensor accelerometer, magnetometer;

    /* context is used to obtain the SensorManager instance. */
    public CompassHandler(Context context) {
        SensorManager sensorManager;

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0) {
            accelerometer = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
        if(sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).size() > 0) {
            magnetometer = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).get(0);
            sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    /* Called when either the accelerometer or magnetometer values change - the internal values are
    then updated and used to calculate the orientation of the device and update it. */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        /* If accelerometer values have changed, update the internal values. */
        if(sensorEvent.sensor == accelerometer) {
            System.arraycopy(sensorEvent.values, 0, accelValues, 0, sensorEvent.values.length);
            accelValuesSet = true;
        }
        /* If magnetometer values have changed, update the internal values. */
        else if(sensorEvent.sensor == magnetometer) {
            System.arraycopy(sensorEvent.values, 0, magnetValues, 0, sensorEvent.values.length);
            magnetValuesSet = true;
        }

        /* Orientation is only calculated if acceleration and magnetometer values have both been
        obtained at least once before. */
        if(accelValuesSet && magnetValuesSet) {
            /* Calculate rotational values from the acceleration and magnetometer values. */
            SensorManager.getRotationMatrix(rotationValues, null, accelValues, magnetValues);
            /* Update the internal orientation values based on the rotation calculated. */
            SensorManager.getOrientation(rotationValues, orientationValues);

            /* Extract useful values from the internal orientation values. */
            yaw = orientationValues[0];
            pitch = orientationValues[1];
            roll = orientationValues[2];
        }
    }

    /* Required to fulfil the requirements of implementing SensorEventListener. */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /* Return the rotation of the vertical axis of the device. */
    public float getYaw() {
        return yaw;
    }

    /* Return the rotation of the lateral axis of the device. */
    public float getPitch() {
        return pitch;
    }

    /* Return the rotation of the longitudinal axis of the device. */
    public float getRoll() {
        return roll;
    }
}
