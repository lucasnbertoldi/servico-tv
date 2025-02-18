#include <IRremote.h>

#define RECV_PIN 2
#define LED_REMOTE_CONTROL 3
#define LIGHT_RELAY 8
#define LIGHT_CODE "light"
#define START_RECEIVING_MESSAGE '_'
#define END_RECEIVING_MESSAGE '-'
#define CONTROL_MESSAGE_INDICATOR '_'

IRrecv irrecv(RECV_PIN);

decode_results results;

int DELAY_CONTROL = 150;
boolean lightState = true;
char charReceived;
String stringReceived = "";
boolean receivingMessage = false;

void setup()
{
  pinMode(LED_REMOTE_CONTROL, OUTPUT);
  pinMode(LIGHT_RELAY, OUTPUT);
  digitalWrite(LED_REMOTE_CONTROL, LOW);
  digitalWrite(LIGHT_RELAY, HIGH);
  Serial.begin(9600);
  irrecv.enableIRIn(); // Start the receiver
}


void loop() {
  if (irrecv.decode(&results)) {
    String message = CONTROL_MESSAGE_INDICATOR + String(results.value,DEC) + CONTROL_MESSAGE_INDICATOR;
    Serial.print(message);
    digitalWrite(LED_REMOTE_CONTROL, HIGH);
    delay(DELAY_CONTROL);
    irrecv.resume();
  }
  digitalWrite(LED_REMOTE_CONTROL, LOW);

  if (Serial.available() > 0) {
    charReceived = Serial.read();
    if (charReceived == START_RECEIVING_MESSAGE) {
      stringReceived = "";
      receivingMessage = true;
    } else {
      if (receivingMessage) {
        stringReceived += charReceived;
        if (stringReceived == LIGHT_CODE) {
          lightState = !lightState;
          digitalWrite(LIGHT_RELAY, lightState);
          delay(1000);
        }
        if (stringReceived.length() >= 20 || charReceived == END_RECEIVING_MESSAGE) {
          stringReceived = "";
          receivingMessage = false;
        }        
      }
    }
  }
}
