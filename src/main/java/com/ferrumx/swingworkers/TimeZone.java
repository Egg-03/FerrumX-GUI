package com.ferrumx.swingworkers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.tinylog.Logger;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.operating_system.Win32_TimeZone;
import com.ferrumx.ui.secondary.ExceptionUI;

public class TimeZone extends SwingWorker<Map<String, String>, Void> {
	
	private final List<JTextField> timeZoneFields;
	
	public TimeZone (List<JTextField> timeZoneFields) {
		this.timeZoneFields = timeZoneFields;
	}

	@Override
	protected Map<String, String> doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		return Win32_TimeZone.getOSTimeZone();
	}
	
	@Override
	protected void done() {
		try {
			Map<String, String> timeZone = get();
			
			timeZoneFields.get(0).setText(timeZone.get("StandardName"));
			timeZoneFields.get(1).setText(timeZone.get("Caption"));
		} catch (ExecutionException e) {
			new ExceptionUI("Time-zone Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
		} catch (InterruptedException e) {
			new ExceptionUI("Time-zone Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
			Thread.currentThread().interrupt();
		}
	}

}
