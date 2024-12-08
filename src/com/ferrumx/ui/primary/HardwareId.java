package com.ferrumx.ui.primary;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import javax.swing.JTextField;

import com.ferrumx.system.hardware.HardwareID;
import com.ferrumx.ui.secondary.ExceptionUI;

final class HardwareId {

	private HardwareId() {
		throw new IllegalStateException("Class method to be used only in the main frame class");
	}

	protected static boolean initializeHardwareId(JTextField hardwareIdTextField) {
		try {
			hardwareIdTextField.setText(HardwareID.getHardwareID());
			hardwareIdTextField.setCaretPosition(0);
			return true;
		} catch (ExecutionException e) {
			String errorMessage = e.getCause().getMessage();
			String stackTrace = Arrays.toString(e.getCause().getStackTrace());
			new ExceptionUI("HWID Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			return false;
		} catch (InterruptedException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Video Controller Initialization Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			Thread.currentThread().interrupt();
			return false;
		}
	}
}