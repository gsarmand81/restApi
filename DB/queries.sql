# Catalog
# 0 presence sensor
# 1 internal temperature sensor
# 2 external temperature sensor
# 3 weight sensor
# 4 thermostat actuator
# 5 light actuator


SELECT * FROM rules;

-- Actualizar init_hour and end_hour
UPDATE `IOTDB`.`rules` SET `init_hour`='02:00',`end_hour`='23:59' WHERE `rule_id` in ('2','3','4','5','6','8');

-- Activar Regla
UPDATE `IOTDB`.`rules` SET `enable`=false WHERE `rule_id` in ('2','3');

SELECT * from sensor_events where sensor_id = 3 order by timestamp desc limit 1 ;

INSERT INTO `IOTDB`.`rules` (`enable`, `name`, `operator`, `param_id`, `param_id_action`, `value_action`, `value_compare`, `end_hour`, `init_hour`)  
VALUES (true, 'Mantiene temperatura por medio del termostato', '<', '1', '4', '1', '40', '00:10', '05:00');

