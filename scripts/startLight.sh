#!/bin/bash

mosquitto_pub -h 127.0.0.1 -i testPublish -t test_topic/D9/value/set -m 'false'

