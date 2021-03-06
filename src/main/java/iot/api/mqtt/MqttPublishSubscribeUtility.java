package iot.api.mqtt;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import iot.api.ApplicationContextProvider;
import iot.api.model.entities.SensorEvent;
import iot.api.model.entities.repositories.SensorEventRepository;
import iot.api.utility.ExecuteHelper;


/**
 * This is the MQTT Callback class which overrides the MQTT Call back methods 
 *
 */
class SimpleCallback implements MqttCallback {

	private static final Logger logger = LoggerFactory.getLogger("sys.out.log");

	//Called when the client lost the connection to the broker
	public void connectionLost(Throwable arg0) {
		logger.info("Connection lost to the broker tcp://192.168.1.2:1883");
	}

	//Called when a new message has arrived
	public void messageArrived(String topic, MqttMessage message) throws Exception {

		logger.info("Message Arrived: Topic: " + topic + " Message: " + new String(message.getPayload()));

		try {

			ExecuteHelper executeHelper = (ExecuteHelper)ApplicationContextProvider.getApplicationContext().
					getBean("executeHelper");

			SensorEventRepository sensorEventRepository = (SensorEventRepository)ApplicationContextProvider.getApplicationContext().
					getBean("sensorEventRepository");

			SensorEvent event = sensorEventRepository.findTop1ByFridgeIdAndSensorIdOrderByTimestampDesc(0l, 3l);

			boolean flag = false;
			if (event !=  null) {
				flag = event.getValue().equals("0");
			}		

			if (!(topic.contains("A2") && (new String(message.getPayload())).equals("0") &&  flag)) {
				executeHelper.execute(topic, message);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deliveryComplete(IMqttDeliveryToken token) {
		logger.info("Delivery is Complete");

	}

}

/**
 * This class holds the MQTT methods to Connect, Publish & Subscribe to Broker
 *
 */
public class MqttPublishSubscribeUtility {

	private static final Logger logger = LoggerFactory.getLogger("sys.out.log");

	private final static String PROPERTIES_FILE_NAME = "/mqtt.properties";
	Properties props = new Properties();

	private MqttClient sampleClient;
	private String topicName;

	public MqttPublishSubscribeUtility (String topicName) {

		try {

			this.topicName = topicName;

			MemoryPersistence persistence = new MemoryPersistence();

			/**
			 * Load device properties
			 */
			try {
				props.load(MqttPublishSubscribeUtility.class.getResourceAsStream(PROPERTIES_FILE_NAME));
			} catch (IOException e) {
				logger.error("Not able to read the properties file, exiting..");
				System.exit(-1);
			}

			String clientId = props.getProperty("CLIENT_ID") + "-" + topicName;
			logger.info("About to connect to MQTT broker with the following parameters: BROKER_URL: " +  props.getProperty("BROKER_URL") + 
					" CLIENT_ID: " + clientId + " TOPIC_NAME: " + topicName);

			MqttClient sampleClient;

			sampleClient = new MqttClient(props.getProperty("BROKER_URL"), clientId, persistence);

			MqttConnectOptions connOpts = new MqttConnectOptions();
			connOpts.setCleanSession(true);
			sampleClient.connect(connOpts);

			logger.info("Connected");

			sampleClient.subscribe(topicName, 1);
			sampleClient.setCallback(new SimpleCallback());

			this.sampleClient = sampleClient;

		} catch (MqttException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}

	}


	public void mqttConnectNPublishNSubscribe(String payload){
		
		//TODO Modulo para validar datos.
		try {

			logger.info("Publish message="+payload.toString());	        
			MqttMessage message = new MqttMessage(payload.getBytes(Charset.forName("UTF-8")));
			if(props.getProperty("QOS")!=null){
				message.setQos(Integer.parseInt(props.getProperty("QOS")));
			}	    
			sampleClient.publish(topicName, message);

			logger.info("Message published");	        
			//TODO Poner esta accion en un metodo destroy
			//	        sampleClient.disconnect();

		} catch(MqttException me){
			logger.error("reason " + me.getReasonCode());
			logger.error("msg " + me.getMessage());
			logger.error("loc " + me.getLocalizedMessage());
			logger.error("cause " + me.getCause());
			logger.error("except " + me);
			me.printStackTrace();
		}
	}


}