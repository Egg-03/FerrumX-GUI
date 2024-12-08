package com.ferrumx.ui.primary;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.operating_system.Win32_OperatingSystem;
import com.ferrumx.ui.secondary.ExceptionUI;
import com.ferrumx.ui.utilities.IconImageChooser;

final class OperatingSystem {

	private OperatingSystem() {
		throw new IllegalStateException("Utility Class");
	}

	protected static boolean getOsProperties(JLabel osLogo, JComboBox<String> osChoice, JTextField... osFields) {
		try {
			List<String> osList = Win32_OperatingSystem.getOSList();

			if (osList.isEmpty()) {
				new ExceptionUI("OS Initialization Error", "FATAL ERROR: No valid Operating System found")
						.setVisible(true);
				return false;
			}
			for (String os : osList) {
				osChoice.addItem(os);
			}

			Map<String, String> osProperties = Win32_OperatingSystem.getOSInfo(osChoice.getItemAt(osChoice.getSelectedIndex()));
			
			String caption = osProperties.get("Caption");
			osFields[0].setText(caption);
			IconImageChooser.osImageChooser(osLogo, caption);
			
			osFields[1].setText(osProperties.get("Version"));
			osFields[2].setText(osProperties.get("Manufacturer"));
			osFields[3].setText(osProperties.get("OSArchitecture"));
			osFields[4].setText(osProperties.get("BuildNumber"));
			osFields[5].setText(osProperties.get("InstallDate"));
			osFields[6].setText(osProperties.get("LastBootUpTime"));
			osFields[7].setText(osProperties.get("SerialNumber"));
			osFields[8].setText(osProperties.get("Primary"));
			osFields[9].setText(osProperties.get("Distributed"));
			osFields[10].setText(osProperties.get("PortableOperatingSystem"));
			osFields[11].setText(osProperties.get("CSName"));
			osFields[12].setText(osProperties.get("NumberOfUsers"));
			osFields[13].setText(osProperties.get("RegisteredUser"));
			osFields[14].setText(osProperties.get("MUILanguages"));
			osFields[15].setText(osProperties.get("SystemDrive"));
			osFields[16].setText(osProperties.get("WindowsDirectory"));
			osFields[17].setText(osProperties.get("SystemDirectory"));

		} catch (IndexOutOfBoundsException | IOException | ShellException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("OS Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			return false;
		} catch (InterruptedException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("OS Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			Thread.currentThread().interrupt();
			return false;
		}
		addOsChoiceActionListener(osLogo, osChoice, osFields);
		return true;
	}

	private static void addOsChoiceActionListener(JLabel osLogo, JComboBox<String> osChoice, JTextField[] osFields) {
		osChoice.addActionListener(e ->
			new Thread(()->{
				try {
					Map<String, String> osProperties = Win32_OperatingSystem.getOSInfo(osChoice.getItemAt(osChoice.getSelectedIndex()));
					
					String caption = osProperties.get("Caption");
					osFields[0].setText(caption);
					IconImageChooser.osImageChooser(osLogo, caption);
					
					osFields[1].setText(osProperties.get("Version"));
					osFields[2].setText(osProperties.get("Manufacturer"));
					osFields[3].setText(osProperties.get("OSArchitecture"));
					osFields[4].setText(osProperties.get("BuildNumber"));
					osFields[5].setText(osProperties.get("InstallDate"));
					osFields[6].setText(osProperties.get("LastBootUpTime"));
					osFields[7].setText(osProperties.get("SerialNumber"));
					osFields[8].setText(osProperties.get("Primary"));
					osFields[9].setText(osProperties.get("Distributed"));
					osFields[10].setText(osProperties.get("PortableOperatingSystem"));
					osFields[11].setText(osProperties.get("CSName"));
					osFields[12].setText(osProperties.get("NumberOfUsers"));
					osFields[13].setText(osProperties.get("RegisteredUser"));
					osFields[14].setText(osProperties.get("MUILanguages"));
					osFields[15].setText(osProperties.get("SystemDrive"));
					osFields[16].setText(osProperties.get("WindowsDirectory"));
					osFields[17].setText(osProperties.get("SystemDirectory"));
				} catch (IndexOutOfBoundsException | IOException | ShellException e1) {
					String errorMessage = e1.getMessage();
					String stackTrace = Arrays.toString(e1.getStackTrace());
					new ExceptionUI("OS Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
				} catch (InterruptedException e1) {
					String errorMessage = e1.getMessage();
					String stackTrace = Arrays.toString(e1.getStackTrace());
					new ExceptionUI("OS Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
					Thread.currentThread().interrupt();
				}

			}).start()
		);
	}
}
