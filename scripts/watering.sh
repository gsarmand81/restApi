#!/bin/bash

mosquitto_pub -h 127.0.0.1 -i testPublish -t test_topic/D8/value/set -m 'false'
sleep 15
mosquitto_pub -h 127.0.0.1 -i testPublish -t test_topic/D8/value/set -m 'true'

