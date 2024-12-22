package com.ferrumx.swingworkers;

import java.util.concurrent.ExecutionException;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.tinylog.Logger;

import com.ferrumx.system.hardware.HardwareID;
import com.ferrumx.ui.secondary.ExceptionUI;

public class HardwareId extends SwingWorker<String, Void> {
	
	private JTextField id;
	
	public HardwareId(JTextField id) {
		this.id = id;
	}

	@Override
	protected String doInBackground() throws ExecutionException, InterruptedException {
		return HardwareID.getHardwareID();
	}
	
	@Override
	protected void done() {
		try {
			id.setText(get());
		} catch (ExecutionException e) {
			id.setText("N/A");
			new ExceptionUI("HWID Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
		} catch (InterruptedException e) {
			id.setText("N/A");
			new ExceptionUI("HWID Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
			Thread.currentThread().interrupt();
		}
	}

}
