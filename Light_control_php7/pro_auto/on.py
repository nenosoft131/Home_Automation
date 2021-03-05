#!/usr/bin/python3
import RPi.GPIO as GPIO
import sys

if str(sys.argv[1])=='str':
	GPIO.setmode(GPIO.BCM)
	GPIO.setwarnings(False)
	GPIO.setup(17,GPIO.OUT)
	GPIO.output(17,True)
elif str(sys.argv[1])=='st':	
	GPIO.setmode(GPIO.BCM)
	GPIO.setwarnings(False)
	GPIO.setup(17,GPIO.OUT)
	GPIO.output(17,False)
	GPIO.cleanup()
elif str(sys.argv[1])=='Ctr_21':
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(18,GPIO.OUT)
	GPIO.output(18,True)
elif str(sys.argv[1])=='Ctr_20':	
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(18,GPIO.OUT)
	GPIO.output(18,False)
	GPIO.cleanup()
elif str(sys.argv[1])=='Ctr_31':
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(19,GPIO.OUT)
	GPIO.output(19,True)
elif str(sys.argv[1])=='Ctr_30':	
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(19,GPIO.OUT)
	GPIO.output(19,False)
	GPIO.cleanup()