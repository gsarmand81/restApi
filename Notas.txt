Buscar Libros.
A
Kendall, K. y Kendall, J. (2005). Análisis y diseño de sistemas. Prentice Hall, 6a. ed.
Levine G. (1987). Introducción a la ingeniería en computación. McGraw-Hill. México.
B
Muñiz Luis ERP. Guía práctica para la selección e implantación. Erp: Enterprise
Resource. Plannning o sistema de planificación de recursos empresariales. 2004, Plaza
edición: BARCELONA, ISBN: 9788480883597.
Clemente, James P. (2007). Administración exitosa de Proyectos. Thomson. 462p., ISBN
970-686-713-9
Pressman, Roger S. (2005). Ingeniería de software. Un enfoque práctico. McGraw-Hill-
Interamericana. 6a. ed. México.


Redes 
Politica FCFS

Imprimir pagina de la 12 a la 19


www.academia.edu
http://biblioteca.universia.net/

sgmario81@gmail.com
Guia practica para la seleccion e implantacion


http://www.slideshare.net/rafaelchachabejarano/redes-computadores-enfoque-descendente-kurose-ross
http://alelopj.weebly.com/uploads/9/3/6/4/936494/roger_pressman-ingeniera_del_software-v_ed-cap1.pdf

##########################################################################################################3

Proyecto

-------------
RaspBerry
-------------

## Install SO
	
Copiar archivos a FAT32 berryboot-20160930-pi2-pi3
E iniciar Raspberry y seguir el Wizard

ssh pi@192.168.1.72

## Install Ardulink

sudo apt-get update && sudo apt-get install oracle-java7-jdk
sudo apt-get install librxtx-java

cd /usr/lib/jvm/jdk-7-oracle-armhf/jre/lib/arm
ln -s /usr/lib/jni/librxtxSerial.so

cd /usr/lib/jvm/jdk-7-oracle-armhf/jre/lib/arm
 /usr/lib/jvm/java-8-oracle

Crear enlace simbolico
ln -s /dev/ttyACM0 /dev/ttyS80


## Install Mosquitto	
sudo apt-get install mosquitto
sudo apt-get install mosquitto-clients

--Pruebas

mosquitto -v

mosquitto_sub -h 127.0.0.1 -i testPresence -t test_topic/A3/value/get
mosquitto_sub -h 127.0.0.1 -i testTemperature2 -t test_topic/A0/value/get
mosquitto_sub -h 127.0.0.1 -i testTemperature1 -t test_topic/A1/value/get
mosquitto_sub -h 127.0.0.1 -i testPressure -t test_topic/A2/value/get
mosquitto_pub -h 127.0.0.1 -i testPublish -t test_topic/D12/value/set -m 'true'
mosquitto_sub -h 127.0.0.1 -i testListener -t test_topic/D12/value/get
mosquitto_sub -h 127.0.0.1 -i testListener -t test_topic/D2/value/get


sudo java -jar lib/ardulink-mqtt-0.6.1.jar -brokerHost 127.0.0.1 -brokerPort 1883 -brokerTopic test_topic -control --analog 0 --analog 1 --analog 2 --digital 2 -athms 1000 

Sensor DHT11
	Revisar como integrarlo con Arduolink. Digital y necesita preprocesamiento.

Sensor PIR
	Es un solo Puerto Digital

Sensor Peso Resistivo
	Es un solo Puerto Analogico

Sensor FotoResistencia
	Es un solo Puerto Analogico
	
Sensor LM35
	Es un solo Puerto Analogico
Comprar cables hembras largos para acomodarlo en la maqueta.

Checar con Lucy sobre el refrigerador


http://www.arduinoecia.com.br/2013/10/sensor-optico-reflexivo-tcrt5000.html
https://learn.adafruit.com/force-sensitive-resistor-fsr/using-an-fsr


Calibracion
http://www.minitronica.com/blog/uso-del-sensor-movimiento-hc-sr501-arduino/

Marron, Negro, Naranja 10K
Naranja,Naranja,Marron 330
http://www.inventable.eu/paginas/ResCalculatorSp/ResCalculatorSp.html


########################################################################
https://eclipse.org/paho/clients/java/
https://gist.github.com/m2mIO-gister

https://spring.io/guides/gs/spring-boot/
https://spring.io/guides/gs/serving-web-content/



TODO 
Completar parte de persistencia.
Comunicacion con los topicos.
Vistas HTML
Estadisticos.


/home/gsarmand/Ceneval/Notas.txt

http://localhost

reset && ./gradlew build && java -jar build/libs/rest-api-0.1.0.jar
curl http://localhost:8080/mqtt?topic=test_topic_3

select sensor_id,count(1) from sensor_events group by sensor_id;

curl 'http://localhost:8080/mqtt?topic=test_topic/D12/value/set&message=false'


#
#Identifico el refrigerador: test_topic_1
#Identifico el sendor: test_topic_1/A2/value/get
#
# INPUT
# D2 presence sensor 
# A0 internal temperature sensor 
# A1 external temperature sensor 
# A2 weight sensor
#
# OUTPUT
# D13 thermostat actuator *
# D12 light actuator *
#
# Catalog
# 0 presence sensor
# 1 internal temperature sensor
# 2 external temperature sensor
# 3 weight sensor
# 4 thermostat actuator
# 5 light actuator

# INSERT INTO stores values('0','Concepcion Beistegui 1401','Narvarte','AAAAA');

# INSERT INTO fridges values('0','AAAAA',0);

# INSERT INTO  IOTDB.sensors values (0,'presence sensor');
# INSERT INTO  IOTDB.sensors values (1,'internal temperature sensor');
# INSERT INTO  IOTDB.sensors values (2,'external temperature sensor');
# INSERT INTO  IOTDB.sensors values (3,'weight sensor');
# INSERT INTO  IOTDB.sensors values (4,'thermostat actuator');
# INSERT INTO  IOTDB.sensors values (5,'light actuator');

# INSERT INTO sensor_events values(1,current_date(),'0',0,1);

# delete from sensor_events;

# select * from sensor_events;

I'm not an expert, but try to put 0.1uF capacitors, from A0 to GND and from A1 to GND, it might help.
http://panamahitek.com/herramienta-de-control-para-arduino-el-triac-conmutador-para-corriente-alterna/

