#!/usr/bin/python3
import cgi
import cgitb
import RPi.GPIO as GPIO
import time
cgitb.enable()
print('Start')
n=.2
GPIO.setmode(GPIO.BCM)

GPIO.setup(17,GPIO.OUT)
print('Start')

for i in range (0,10):
    time.sleep(n)
    GPIO.output(17,0)
    time.sleep(n)
    GPIO.output(17,1)

print('Stop')
GPIO.cleanup()
