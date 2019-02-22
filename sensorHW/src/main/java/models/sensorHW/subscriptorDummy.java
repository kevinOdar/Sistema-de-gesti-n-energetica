package models.sensorHW;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public final class subscriptorDummy {

    public static void main(String[] args) throws MqttException, InterruptedException {

        System.out.println("== START SUBSCRIBER ==");

        MqttClient client = new MqttClient("tcp://localhost:1883", "serverClientId");
        
        client.setCallback(new MqttCallback() {
            public void connectionLost(Throwable throwable) {
                System.out.println("Connection to MQTT broker lost!");
            }

            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                System.out.println("Message received:\t" + new String(mqttMessage.getPayload()));
            }

            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            }
        });
        
        client.connect();

        client.subscribe("sensorHW");
        System.out.println("ok!");


    }
	
}
