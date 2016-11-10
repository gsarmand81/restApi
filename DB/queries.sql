SELECT * FROM rules;

-- Actualizar init_hour and end_hour
UPDATE `IOTDB`.`rules` SET `init_hour`='22:00',`end_hour`='23:59' WHERE `rule_id` in ('2','3');

-- Activar Regla
UPDATE `IOTDB`.`rules` SET `enable`=true WHERE `rule_id` in ('2','3');

SELECT * from sensor_events where sensor_id = 3 order by timestamp desc limit 1 


