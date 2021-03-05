#!/usr/bin/python3
import RPi.GPIO as GPIO
import sys

if str(sys.argv[1])=='1Ctr_1':
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(17,GPIO.OUT)
	GPIO.output(17,True)
elif str(sys.argv[1])=='0Ctr_1':	
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(17,GPIO.OUT)
	GPIO.output(17,False)
	GPIO.cleanup()
elif str(sys.argv[1])=='1Ctr_2':
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(18,GPIO.OUT)
	GPIO.output(18,True)
elif str(sys.argv[1])=='0Ctr_2':	
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(18,GPIO.OUT)
	GPIO.output(18,False)
	GPIO.cleanup()
elif str(sys.argv[1])=='1Ctr_3':
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(19,GPIO.OUT)
	GPIO.output(19,True)
elif str(sys.argv[1])=='0Ctr_3':	
	GPIO.setmode(GPIO.BCM)
	GPIO.setup(19,GPIO.OUT)
	GPIO.output(19,False)
	GPIO.cleanup()