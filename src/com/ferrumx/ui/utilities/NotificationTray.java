package com.ferrumx.ui.utilities;

import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import org.tinylog.Logger;

import com.ferrumx.ui.secondary.ExceptionUI;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class NotificationTray {
	private TrayIcon trayIcon = null;
	private SystemTray sysTray = null;
	
	public NotificationTray() {
		if(SystemTray.isSupported()) {
			sysTray = SystemTray.getSystemTray();
			trayIcon = new TrayIcon(new FlatSVGIcon(NotificationTray.class.getResource("/resources/notif_icons/notif.svg")).getImage());
			trayIcon.setImageAutoSize(true);
		}
		else {
			new ExceptionUI("System Tray Notification Error", "System Tray Notification is not supported");
		}
	}

    public SystemTray getSysTray() {
		return sysTray;
	}

	public TrayIcon getTrayIcon() {
		return trayIcon;
	}

	public void displayTray(String message, MessageType type) {
        try {
			sysTray.add(trayIcon);
			trayIcon.displayMessage("FerrumX", message, type);
			//an action listener should be added which will close this notification on click
			//this should be done after retrieving an instance of TrayIcon using the getNotification() function
		} catch (AWTException e) {
			new ExceptionUI("System Tray Notification Error", "Error: "+e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
			sysTray.remove(trayIcon);
		}
    }
	
	public void displayTrayAutoRemove(String message, MessageType type) {
		 try {
				sysTray.add(trayIcon);
				trayIcon.displayMessage("FerrumX", message, type);
				sysTray.remove(trayIcon);
			} catch (AWTException e) {
				new ExceptionUI("System Tray Notification Error", "Error: "+e.getMessage()+"\nPlease refer to the logs for more information.");
				Logger.error(e);
				sysTray.remove(trayIcon);
			}
	}
}
