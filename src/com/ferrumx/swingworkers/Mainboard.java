package com.ferrumx.swingworkers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.tinylog.Logger;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_BIOS;
import com.ferrumx.system.hardware.Win32_Baseboard;
import com.ferrumx.ui.secondary.ExceptionUI;

public class Mainboard extends SwingWorker<List<Map<String, String>>, Void> {
	
	private List<JTextField> mainboardFields;
	
	public Mainboard(List<JTextField> mainboardFields) {
		this.mainboardFields=mainboardFields;
	}

	@Override
	protected List<Map<String, String>> doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException  {
		Map<String, String> bios = Win32_BIOS.getPrimaryBIOS();
		Map<String, String> mainboard = Win32_Baseboard.getMotherboard();
		
		return List.of(mainboard, bios);
	}
	
	@Override
	protected void done() {
		try {
			List<Map<String, String>> mainboardClasses = get();
			Map<String, String> mainboard = mainboardClasses.getFirst();
			Map<String, String> bios = mainboardClasses.getLast();
			
			// mainboard property fill
			mainboardFields.get(0).setText(mainboard.get("Manufacturer"));
			mainboardFields.get(1).setText(mainboard.get("Model"));
			mainboardFields.get(2).setText(mainboard.get("Product"));
			mainboardFields.get(3).setText(mainboard.get("SerialNumber"));
			mainboardFields.get(4).setText(mainboard.get("Version"));
			mainboardFields.get(5).setText(mainboard.get("PNPDeviceID"));
			// bios property fill
			mainboardFields.get(6).setText(bios.get("Name"));
			mainboardFields.get(7).setText(bios.get("Caption"));
			mainboardFields.get(8).setText(bios.get("Manufacturer"));
			mainboardFields.get(9).setText(bios.get("ReleaseDate"));
			mainboardFields.get(10).setText(bios.get("Version"));
			mainboardFields.get(11).setText(bios.get("Status"));
			mainboardFields.get(12).setText(bios.get("SMBIOSPResent"));
			mainboardFields.get(13).setText(bios.get("SMBIOSBIOSVersion"));
			mainboardFields.get(14).setText(bios.get("CurrentLanguage"));
		} catch (ExecutionException e) {
			new ExceptionUI("Mainboard Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
		} catch (InterruptedException e) {
			new ExceptionUI("Mainboard Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
			Thread.currentThread().interrupt();
		}
	}

}
