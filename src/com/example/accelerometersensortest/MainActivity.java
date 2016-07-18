package com.example.accelerometersensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;

public class MainActivity extends Activity {

	private SensorManager sensorManager;
	private TextView accValue0,accValue1,accValue2,magValue0,magValue1,magValue2;
	 
    private float[] accelerometerValues = new float[3];
    private float[] magneticValues = new float[3];
    private float[] mValues = new float[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor sensorM = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorManager.registerListener(listener, sensor,SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(listener, sensorM,SensorManager.SENSOR_DELAY_NORMAL);
		
		 	accValue0 = (TextView) findViewById(R.id.accValue0);
	        accValue1 = (TextView) findViewById(R.id.accValue1);
	        accValue2 = (TextView) findViewById(R.id.accValue2);
	        
	        magValue0 = (TextView) findViewById(R.id.magValue0);
	        magValue1 = (TextView) findViewById(R.id.magValue1);
	        magValue2 = (TextView) findViewById(R.id.magValue2);
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (sensorManager != null) {
			sensorManager.unregisterListener(listener);
		}
	}

	private SensorEventListener listener = new SensorEventListener() {

		@Override
		public void onSensorChanged(SensorEvent event) {
			 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
				 accelerometerValues[0] = event.values[0];
				 accelerometerValues[1] = event.values[1];
				 accelerometerValues[2] = event.values[2];
	            }
	            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
	            	magneticValues = event.values;
	            	 float[] values = new float[3];
	                 float[] R = new float[9];
	                 SensorManager.getRotationMatrix(R, null, accelerometerValues,magneticValues);
	                 SensorManager.getOrientation(R, values);
	                 mValues[0] = (float) Math.toDegrees(values[0]);
	                 mValues[1] = (float) Math.toDegrees(values[1]);
	                 mValues[2] = (float) Math.toDegrees(values[2]);
	            }
			
	            accValue0.setText("accValue0:"+accelerometerValues[0]);
	            accValue1.setText("accValue1:"+accelerometerValues[1]);
	            accValue2.setText("accValue2:"+accelerometerValues[2]);
	            
	            magValue0.setText("magValue0:"+mValues[0]);
	            magValue1.setText("magValue1:"+mValues[1]);
	            magValue2.setText("magValue2:"+mValues[2]);
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {

		}
	};

}
