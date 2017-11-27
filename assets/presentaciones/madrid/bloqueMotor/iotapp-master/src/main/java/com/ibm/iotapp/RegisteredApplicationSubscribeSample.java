/**
 *****************************************************************************
 * Copyright (c) 2015-16 IBM Corporation and other Contributors.

 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Sathiskumar Palaniappan - Initial Contribution
 * Amit M Mangalvedkar - Initial Contribution
 *****************************************************************************
 */

package com.ibm.iotapp;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.awt.*;
import java.awt.event.KeyEvent;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.ibm.iotf.client.app.ApplicationClient;
import com.ibm.iotf.client.app.ApplicationStatus;
import com.ibm.iotf.client.app.Command;
import com.ibm.iotf.client.app.DeviceStatus;
import com.ibm.iotf.client.app.Event;
import com.ibm.iotf.client.app.EventCallback;
import com.ibm.iotf.client.app.StatusCallback;

/**
 * This sample shows how an application subscribe to device status 
 * (like connect & disconnect from IBM Watson IoT Platform) and device events. 
 * 
 */
public class RegisteredApplicationSubscribeSample {
	
	private final static String PROPERTIES_FILE_NAME = "/application.properties";

	private ApplicationClient myClient = null;	
	
	public RegisteredApplicationSubscribeSample() throws MqttException {

		/**
		  * Load device properties
		  */
		Properties props = new Properties();
		try {
			props.load(RegisteredApplicationSubscribeSample.class.getResourceAsStream(PROPERTIES_FILE_NAME));
		} catch (IOException e1) {
			System.err.println("Not able to read the properties file, exiting..");
			System.exit(-1);
		}
		
		try {
			myClient = new ApplicationClient(props);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		myClient.connect();
		
		/**
		 * Get the Device Type and Device Id for which the application will listen for the events
		 */
		String deviceType = trimedValue(props.getProperty("Device-Type"));
		String deviceId = trimedValue(props.getProperty("Device-ID"));
		
		MyEventCallback evtBack = new MyEventCallback();
		Thread t = new Thread(evtBack);
		t.start();
		myClient.setEventCallback(evtBack);
		
		myClient.setStatusCallback(new MyStatusCallback());
		
		System.out.println("Subscribing to load events from device " + deviceId + " of type "+deviceType);
		// Subscribe to particular device type, ID, event and format
		myClient.subscribeToDeviceEvents(deviceType, deviceId, "Spotify", "json", 0);
		
		myClient.subscribeToDeviceStatus(deviceType, deviceId);
		
	}

	public static void main(String[] args) throws MqttException {
		new RegisteredApplicationSubscribeSample();
	}
	
	/**
	 * A sample Event callback class that processes the device events in separate thread.
	 *
	 */
	private static class MyEventCallback implements EventCallback, Runnable {

		// A queue to hold & process the Events for smooth handling of MQTT messages
		private BlockingQueue<Event> evtQueue = new LinkedBlockingQueue<Event>();
		
		public void processEvent(Event e) {
			try {
				evtQueue.put(e);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		public void processCommand(Command cmd) {
			System.out.println("Command received:: " + cmd);			
		}

		public void run() {
			while(true) {
				Event e = null;
				try {
					e = evtQueue.take();
					// In this example, we just output the event
					System.out.println("Event:: " + e.getDeviceId() + ":" + e.getEvent() + ":" + e.getPayload());
					String raw = e.getPayload();
					String[] parts = raw.split("\"");
					String action = parts[5];
					System.out.println("Command:" + action);
					Robot robot = new Robot();

					switch (action){
						case "pausa":
							robot.keyPress(KeyEvent.VK_SPACE);
							robot.keyRelease(KeyEvent.VK_SPACE);
							break;

						case "+5":
							//robot.keyPress(KeyEvent.VK_RIGHT);
							//robot.keyRelease(KeyEvent.VK_RIGHT);
							robot.keyPress(KeyEvent.VK_META);
							robot.keyPress(KeyEvent.VK_RIGHT);
							robot.keyRelease(KeyEvent.VK_RIGHT);
							robot.keyRelease(KeyEvent.VK_META);
							break;

						case "-5":
							//robot.keyPress(KeyEvent.VK_LEFT);
							//robot.keyRelease(KeyEvent.VK_LEFT);
							robot.keyPress(KeyEvent.VK_META);
							robot.keyPress(KeyEvent.VK_LEFT);
							robot.keyRelease(KeyEvent.VK_LEFT);
							robot.keyRelease( KeyEvent.VK_META);
							break;

						case "m":
							robot.keyPress(KeyEvent.VK_M);
							robot.keyRelease(KeyEvent.VK_M);
							robot.keyPress(KeyEvent.VK_ALT_GRAPH);
							robot.keyPress(KeyEvent.VK_SHIFT);
							robot.keyPress(KeyEvent.VK_DOWN);
							robot.keyRelease(KeyEvent.VK_DOWN);
							robot.keyRelease(KeyEvent.VK_SHIFT);
							robot.keyRelease(KeyEvent.VK_ALT_GRAPH);
							break;

						case "f":
							robot.keyPress(KeyEvent.VK_F);
							robot.keyRelease(KeyEvent.VK_F);
							robot.keyPress(KeyEvent.VK_CONTROL);
							robot.keyPress(KeyEvent.VK_F);
							robot.keyRelease(KeyEvent.VK_F);
							robot.keyRelease(KeyEvent.VK_CONTROL);
							break;

						case "up":
							robot.keyPress(KeyEvent.VK_UP);
							robot.keyRelease(KeyEvent.VK_UP);
							robot.keyPress(KeyEvent.VK_CONTROL);
							robot.keyPress(KeyEvent.VK_UP);
							robot.keyRelease(KeyEvent.VK_UP);
							robot.keyRelease(KeyEvent.VK_CONTROL);
							break;

						case "down":
							robot.keyPress(KeyEvent.VK_DOWN);
							robot.keyRelease(KeyEvent.VK_DOWN);
							robot.keyPress(KeyEvent.VK_CONTROL);
							robot.keyPress(KeyEvent.VK_DOWN);
							robot.keyRelease(KeyEvent.VK_DOWN);
							robot.keyRelease(KeyEvent.VK_CONTROL);
							break;

						case "0":
							robot.keyPress(KeyEvent.VK_0);
							robot.keyRelease(KeyEvent.VK_0);
							break;
						/**case "nueva lista":
							robot.keyPress(0x8);
							robot.keyPress(KeyEvent.VK_N);
							robot.keyRelease(KeyEvent.VK_N);
							robot.keyRelease(0x8);

							/**robot.keyPress(KeyEvent.VK_A);
							 robot.keyRelease(KeyEvent.VK_A);
							 robot.keyPress(KeyEvent.VK_N);
							 robot.keyRelease(KeyEvent.VK_N);**/

					}

				} catch (InterruptedException e1) {
						// Ignore the Interuppted exception, retry
						continue;
				} catch (AWTException e1) {
					e1.printStackTrace();
				}

			}
		}

	}

	private static class MyStatusCallback implements StatusCallback {

		public void processApplicationStatus(ApplicationStatus status) {
			System.out.println("Application Status = " + status.getPayload());
		}

		public void processDeviceStatus(DeviceStatus status) {
			if(status.getAction() == "Disconnect") {
				System.out.println("device: "+status.getDeviceId()
						+ "  time: "+ status.getTime()
						+ "  action: " + status.getAction()
						+ "  reason: " + status.getReason());
			} else {
				System.out.println("device: "+status.getDeviceId()
						+ "  time: "+ status.getTime()
						+ "  action: " + status.getAction());
			}
		}
	}

	private static String trimedValue(String value) {
		if(value != null) {
			return value.trim();
		}
		return value;
	}
}
