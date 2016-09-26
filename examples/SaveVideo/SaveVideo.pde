import com.magicandlove.*;
import processing.video.*;

Capture cap;
Recorder rec;
boolean recording;

void setup() {
  size(640, 480);
  background(0);
  cap = new Capture(this, width, height);
  int fRate = 25;
  rec = new Recorder(this, "testing.mp4", fRate);
  recording = false;
  frameRate(fRate);
  cap.start();
}

void draw() {
  image(cap, 0, 0);
  if (recording) 
    rec.addFrame();
}

void captureEvent(Capture c) {
  c.read();
}

void mousePressed() {
  recording = !recording;
}

void keyPressed() {
  if (keyCode == 32) {
    rec.finish();
  }
}