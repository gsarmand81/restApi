const int TX_PIN = 5;   //

typedef struct  {
    boolean power;
    int duration;   // micro seconds
} signal;

const signal SHORT_ON { HIGH, 146 };
const signal SHORT_OFF { LOW, 210 };
const signal LONG_ON { HIGH, 495 };  //495
const signal LONG_OFF { LOW, 560 };
const int REST = 5520;
const int SLEEP = 2000;

signal sig_light_on [] {
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON
};

signal sig_light_off [] {
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON, LONG_OFF,
    LONG_ON, SHORT_OFF,
    LONG_ON, SHORT_OFF,
    LONG_ON, SHORT_OFF,
    LONG_ON, SHORT_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON, LONG_OFF,
    SHORT_ON
};

void setup() {
    pinMode(TX_PIN, OUTPUT);
    Serial.println ( "Initializing" );

}

void doTransmission() {
 
 Serial.println("Doing transmission...");

 Serial.println("On ... ");

    for (int burst = 1; burst <= 6; burst++) {
        for (int pre = 0; pre < 49; ++pre) {
            digitalWrite(TX_PIN, sig_light_on[pre].power);
            delayMicroseconds (sig_light_on[pre].duration);                          
        }
        
        digitalWrite(TX_PIN, LOW);
        delayMicroseconds(REST);
    }  

  delay(5000);

  Serial.println("Off ... ");
  
  for (int burst = 1; burst <= 6; burst++) {
        for (int pre = 0; pre < 49; ++pre) {
            digitalWrite(TX_PIN, sig_light_off[pre].power);
            delayMicroseconds (sig_light_off[pre].duration);                          
        }
        
        digitalWrite(TX_PIN, LOW);
        delayMicroseconds(REST);
    }  

  delay(5000);

}

void doOn() {
 
 Serial.println("On ... ");

    for (int burst = 1; burst <= 6; burst++) {
        for (int pre = 0; pre < 49; ++pre) {
            digitalWrite(TX_PIN, sig_light_on[pre].power);
            delayMicroseconds (sig_light_on[pre].duration);                          
        }
        
        digitalWrite(TX_PIN, LOW);
        delayMicroseconds(REST);
    }  
}


void doOff() {
 
 Serial.println("Off ... ");

    for (int burst = 1; burst <= 6; burst++) {
        for (int pre = 0; pre < 49; ++pre) {
            digitalWrite(TX_PIN, sig_light_off[pre].power);
            delayMicroseconds (sig_light_off[pre].duration);                          
        }
        
        digitalWrite(TX_PIN, LOW);
        delayMicroseconds(REST);
    }  
 
}


void loop()  {
  
  doOn();
  delay(15000);
  doOff();
  delay(15000);
  
}
