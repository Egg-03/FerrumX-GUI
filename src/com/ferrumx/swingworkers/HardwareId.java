package com.ferrumx.swingworkers;

import java.util.concurrent.ExecutionException;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.ferrumx.system.hardware.HardwareID;
import com.ferrumx.ui.secondary.StatusUI;

public class HardwareId extends SwingWorker<String, Void> {
	
	private JTextField id;
	private StatusUI status;
	
	public HardwareId(JTextField id, StatusUI status) {
		this.id = id;
		this.status = status;
	}

	@Override
	protected String doInBackground() throws ExecutionException, InterruptedException {
		return HardwareID.getHardwareID();
	}
	
	@Override
	protected void done() {
		try {
			id.setText(get());
			status.setHardwareLabel(true);
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
