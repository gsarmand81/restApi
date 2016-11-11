-- Nombre del topico: topic_ + fridge_id + pin

--
--Identifico el refrigerador: test_topic_1
--Identifico el sendor: test_topic_1/A2/value/get
--
-- INPUT
-- A3 presence sensor 
-- A0 internal temperature sensor 
-- A1 external temperature sensor 
-- A2 weight sensor
--
-- OUTPUT
-- D13 thermostat actuator
-- D12 light actuator
--

INSERT INTO stores values('0','Concepcion Beistegui 1401','Narvarte','AAAAA');
INSERT INTO stores values('1','Anaxagoras 616','Del Valle','BBBBB');
INSERT INTO stores values('2','Cuauhtemoc 165','Roma','CCCCC');
INSERT INTO stores values('3','Marques de Montes Claros 109','Queretaro','DDDDD');
INSERT INTO stores values('4','Marques de Mancera 106','Lomas','EEEEE');
INSERT INTO stores values('5','Kiliwas 806','Cerrito','FFFFF');

INSERT INTO fridges values('0','AABBB',0);
INSERT INTO fridges values('1','AACCC',0);
INSERT INTO fridges values('2','BBAAA',1);
INSERT INTO fridges values('3','BBBBB',1);
INSERT INTO fridges values('4','CCAAA',2);
INSERT INTO fridges values('5','CCBBB',2);
INSERT INTO fridges values('6','DDAAA',3);
INSERT INTO fridges values('7','DDBBB',4);

INSERT INTO  IOTDB.sensors values (0,'Presencia','Sensor');
INSERT INTO  IOTDB.sensors values (1,'Temperatura Interna','Sensor');
INSERT INTO  IOTDB.sensors values (2,'Temperatura Externa','Sensor');
INSERT INTO  IOTDB.sensors values (3,'Peso','Sensor');
INSERT INTO  IOTDB.sensors values (4,'Termostato','Actuador');
INSERT INTO  IOTDB.sensors values (5,'Luz','Actuador');
