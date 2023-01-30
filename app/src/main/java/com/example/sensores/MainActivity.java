package com.example.sensores;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView salida;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        salida = (TextView) findViewById(R.id.salida);

        // Solicitamos al sistema servicios específicos; en este caso SENSORES
        // que almacenaremos en un objeto SensorManager
        SensorManager sensorManager = (SensorManager)
                getSystemService(SENSOR_SERVICE);

        //1ª PARTE EJERCICIO SENSORES -- Obtener los nombres de todos los sensores
        /*
        List<Sensor> listaSensores = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: listaSensores) {
            log(sensor.getName());
        }
        */

        //2ª PARTE EJERCICIO SENSORES -- Obtener valores de los diferentes sensores
        List<Sensor> listaSensores;

        //Vamos preguntando tipo a tipo si hay sensor de un tipo determinado y obtenemos su valor
        listaSensores = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if (!listaSensores.isEmpty()) {
            Sensor orientationSensor = listaSensores.get(0);
            //El método "registerListener" toma como 1er parámetro un objeto que implemente
            //el interface SensorEventListener (que la hemos tenido que IMPLEMENTAR en la
            //declaración de la clase), como 2º el sensor que estamos registrando
            //y como 3º con qué frecuencia nos gustaría recibir actualizaciones del sensor
            sensorManager.registerListener((SensorEventListener) this, orientationSensor, SensorManager.SENSOR_DELAY_UI);
        }

        listaSensores = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (!listaSensores.isEmpty()) {
            Sensor acelerometerSensor = listaSensores.get(0);
            sensorManager.registerListener((SensorEventListener) this, acelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }

        listaSensores = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
        if (!listaSensores.isEmpty()) {
            Sensor magneticSensor = listaSensores.get(0);
            sensorManager.registerListener((SensorEventListener) this, magneticSensor, SensorManager.SENSOR_DELAY_UI);
        }

        listaSensores = sensorManager.getSensorList(Sensor.TYPE_PROXIMITY);
        if (!listaSensores.isEmpty()) {
            Sensor temperatureSensor = listaSensores.get(0);
            sensorManager.registerListener((SensorEventListener) this, temperatureSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    private void log(String string) {
        salida.append(string + "\n");
    }

    //Al implementar la interfaz SensorEventListener, hay que sobreescribir
    //sus 2 métodos: onSensorChanged y onAccuracyChanged (aunque éste no nos interese)
    @Override public void onSensorChanged(SensorEvent evento) {
        //Cada sensor puede provocar que un thread principal pase por aquí
        //así que sincronizamos el acceso (se verá más adelante)
        switch(evento.sensor.getType()) {
            case Sensor.TYPE_ORIENTATION:
                for (int i=0 ; i<3 ; i++) {
                    log("Orientación "+i+": "+evento.values[i]);
                }
                break;
            case Sensor.TYPE_ACCELEROMETER:
                for (int i=0 ; i<3 ; i++) {
                    log("Acelerómetro "+i+": "+evento.values[i]);
                }
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                for (int i=0 ; i<3 ; i++) {
                    log("Magnetismo "+i+": "+evento.values[i]);
                }
                break;
            default:
                for (int i=0 ; i<evento.values.length ; i++) {
                    log("Proximidad " + i + ": " + evento.values[i]);
                }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int precision) {
    }
}
