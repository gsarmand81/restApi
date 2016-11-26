# Catalog
# 0 presence sensor
# 1 internal temperature sensor
# 2 external temperature sensor
# 3 weight sensor
# 4 thermostat actuator
# 5 light actuator


SELECT * FROM rules;

-- Actualizar init_hour and end_hour
UPDATE `IOTDB`.`rules` SET `init_hour`='09:00',`end_hour`='19:00' WHERE `rule_id` in ('2','3','4','5','6','8');

-- Activar Regla
UPDATE `IOTDB`.`rules` SET `enable`=true WHERE `rule_id` in ('2','3');

SELECT * from sensor_events where sensor_id = 3 order by timestamp desc limit 1 ;

INSERT INTO `IOTDB`.`rules` (`enable`, `name`, `operator`, `param_id`, `param_id_action`, `value_action`, `value_compare`, `end_hour`, `init_hour`)  
VALUES (true, 'Por Presencia se prende la luz', '=', '0', '5', '1', '1', '05:00', '0:10');
INSERT INTO `IOTDB`.`rules` (`enable`, `name`, `operator`, `param_id`, `param_id_action`, `value_action`, `value_compare`, `end_hour`, `init_hour`)  
VALUES (true, 'Por Presencia se apaga la luz', '=', '0', '5', '0', '0', '05:00', '0:10');
INSERT INTO `IOTDB`.`rules` (`enable`, `name`, `operator`, `param_id`, `param_id_action`, `value_action`, `value_compare`, `end_hour`, `init_hour`)  
VALUES (true, 'Por Peso se prende el termostato', '>', '3', '4', '1', '500', '05:00', '0:10');
INSERT INTO `IOTDB`.`rules` (`enable`, `name`, `operator`, `param_id`, `param_id_action`, `value_action`, `value_compare`, `end_hour`, `init_hour`)  
VALUES (true, 'Por Peso se apage el termostato', '<', '3', '4', '0', '500', '05:00', '0:10');
INSERT INTO `IOTDB`.`rules` (`enable`, `name`, `operator`, `param_id`, `param_id_action`, `value_action`, `value_compare`, `end_hour`, `init_hour`)  
VALUES (true, 'Prende termostato si la temperatura es mayo a  25', '>', '1', '4', '1', '25', '05:00', '0:10');
INSERT INTO `IOTDB`.`rules` (`enable`, `name`, `operator`, `param_id`, `param_id_action`, `value_action`, `value_compare`, `end_hour`, `init_hour`)  
VALUES (true, 'Apaga termostato si la temperatura es mayo a  25', '<', '1', '4', '0', '25', '05:00', '0:10');

UPDATE `IOTDB`.`rules` SET `enable`='1' WHERE `rule_id` in ('1','2','3','4','5','6');

UPDATE `IOTDB`.`rules` SET `init_hour`='02:00',`end_hour`='23:59' WHERE `rule_id` in ('1','2','3','4','5','6');


