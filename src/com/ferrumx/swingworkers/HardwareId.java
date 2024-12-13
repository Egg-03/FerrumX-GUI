package com.ferrumx.swingworkers;

import java.util.concurrent.ExecutionException;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.ferrumx.system.hardware.HardwareID;

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
			// TODO Auto-generated catch block
			id.setText("N/A");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			id.setText("N/A");
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

}
