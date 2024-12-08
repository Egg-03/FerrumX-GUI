package com.ferrumx.ui.primary;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.swing.JTextField;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_BIOS;
import com.ferrumx.system.hardware.Win32_Baseboard;
import com.ferrumx.ui.secondary.ExceptionUI;

final class Mainboard {
	private Mainboard() {
		throw new IllegalStateException("Utility Class");
	}

	protected static boolean initializeMainboard(JTextField... mainboardFields) {
		try {
			Map<String, String> bios = Win32_BIOS.getPrimaryBIOS();
			Map<String, String> mainboard = Win32_Baseboard.getMotherboard();

			if (mainboard.isEmpty()) {
				new ExceptionUI("Mainboard Initialization Error", "FATAL ERROR: No Mainboard device found")
						.setVisible(true);
				return false;
			}

			if (bios.isEmpty()) {
				new ExceptionUI("BIOS Error", "FATAL ERROR: No Primary BIOS found").setVisible(true);
				return false;
			}

			// mainboard property fill
			mainboardFields[0].setText(mainboard.get("Manufacturer"));
			mainboardFields[1].setText(mainboard.get("Model"));
			mainboardFields[2].setText(mainboard.get("Product"));
			mainboardFields[3].setText(mainboard.get("SerialNumber"));
			mainboardFields[4].setText(mainboard.get("Version"));
			mainboardFields[5].setText(mainboard.get("PNPDeviceID"));
			// bios property fill
			mainboardFields[6].setText(bios.get("Name"));
			mainboardFields[7].setText(bios.get("Caption"));
			mainboardFields[8].setText(bios.get("Manufacturer"));
			mainboardFields[9].setText(bios.get("ReleaseDate"));
			mainboardFields[10].setText(bios.get("Version"));
			mainboardFields[11].setText(bios.get("Status"));
			mainboardFields[12].setText(bios.get("SMBIOSPResent"));
			mainboardFields[13].setText(bios.get("SMBIOSBIOSVersion"));
			mainboardFields[14].setText(bios.get("CurrentLanguage"));
		} catch (IndexOutOfBoundsException | IOException | ShellException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Mainboard/BIOS Initialization Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			return false;
		} catch (InterruptedException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Mainboard/BIOS Initialization Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			Thread.currentThread().interrupt();
			return false;
		}
		return true;
	}
}
