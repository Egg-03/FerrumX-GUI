package com.ferrumx.swingworkers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.tinylog.Logger;

import com.ferrumx.system.operating_system.Win32_OperatingSystem;
import com.ferrumx.ui.secondary.ExceptionUI;
import com.ferrumx.ui.utilities.IconImageChooser;

public class OperatingSystem extends SwingWorker<Map<String, String>, List<String>> {
	
	private final JLabel osLogo;
	private final JComboBox<String> osChoice;
	private final List<JTextField> osFields;
	
	public OperatingSystem(JLabel osLogo, JComboBox<String> osChoice, List<JTextField> osFields) {
		this.osLogo = osLogo;
		this.osChoice = osChoice;
		this.osFields = osFields;
	}

	@Override
	protected Map<String, String> doInBackground() throws Exception {
		List<String> osList = Win32_OperatingSystem.getOSList();
		publish(osList);
		
		return  Win32_OperatingSystem.getOSInfo(osList.getFirst());
	}
	
	@Override
	protected void process(List<List<String>> chunks) {
		for(List<String> osList:chunks) {
			for(String currentOS:osList) {
				osChoice.addItem(currentOS);
			}
		}
	}
	
	@Override
	protected void done() {
		try {
			Map<String, String> osProperties = get();
			
			String caption = osProperties.get("Caption");
			osFields.get(0).setText(caption);
			IconImageChooser.osImageChooser(osLogo, caption);
			
			osFields.get(1).setText(osProperties.get("Version"));
			osFields.get(2).setText(osProperties.get("Manufacturer"));
			osFields.get(3).setText(osProperties.get("OSArchitecture"));
			osFields.get(4).setText(osProperties.get("BuildNumber"));
			osFields.get(5).setText(osProperties.get("InstallDate"));
			osFields.get(6).setText(osProperties.get("LastBootUpTime"));
			osFields.get(7).setText(osProperties.get("SerialNumber"));
			osFields.get(8).setText(osProperties.get("Primary"));
			osFields.get(9).setText(osProperties.get("Distributed"));
			osFields.get(10).setText(osProperties.get("PortableOperatingSystem"));
			osFields.get(11).setText(osProperties.get("CSName"));
			osFields.get(12).setText(osProperties.get("NumberOfUsers"));
			osFields.get(13).setText(osProperties.get("RegisteredUser"));
			osFields.get(14).setText(osProperties.get("MUILanguages"));
			osFields.get(15).setText(osProperties.get("SystemDrive"));
			osFields.get(16).setText(osProperties.get("WindowsDirectory"));
			osFields.get(17).setText(osProperties.get("SystemDirectory"));
			
			osChoice.addActionListener(e-> new OsActionListener(osLogo, osChoice, osFields).execute());
			
		} catch (ExecutionException e) {
			new ExceptionUI("OS Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
		} catch (InterruptedException e) {
			new ExceptionUI("OS Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
			Thread.currentThread().interrupt();
		}
	}

}

class OsActionListener extends SwingWorker<Map<String, String>, Void>{
	
	private final JLabel osLogo;
	private final JComboBox<String> osChoice;
	private final List<JTextField> osFields;
	
	public OsActionListener(JLabel osLogo, JComboBox<String> osChoice, List<JTextField> osFields) {
		this.osLogo = osLogo;
		this.osChoice = osChoice;
		this.osFields = osFields;
	}

	@Override
	protected Map<String, String> doInBackground() throws Exception {
		return  Win32_OperatingSystem.getOSInfo(osChoice.getItemAt(osChoice.getSelectedIndex()));
	}
	
	@Override
	protected void done() {
		try {
			Map<String, String> osProperties = get();
			
			String caption = osProperties.get("Caption");
			osFields.get(0).setText(caption);
			IconImageChooser.osImageChooser(osLogo, caption);
			
			osFields.get(1).setText(osProperties.get("Version"));
			osFields.get(2).setText(osProperties.get("Manufacturer"));
			osFields.get(3).setText(osProperties.get("OSArchitecture"));
			osFields.get(4).setText(osProperties.get("BuildNumber"));
			osFields.get(5).setText(osProperties.get("InstallDate"));
			osFields.get(6).setText(osProperties.get("LastBootUpTime"));
			osFields.get(7).setText(osProperties.get("SerialNumber"));
			osFields.get(8).setText(osProperties.get("Primary"));
			osFields.get(9).setText(osProperties.get("Distributed"));
			osFields.get(10).setText(osProperties.get("PortableOperatingSystem"));
			osFields.get(11).setText(osProperties.get("CSName"));
			osFields.get(12).setText(osProperties.get("NumberOfUsers"));
			osFields.get(13).setText(osProperties.get("RegisteredUser"));
			osFields.get(14).setText(osProperties.get("MUILanguages"));
			osFields.get(15).setText(osProperties.get("SystemDrive"));
			osFields.get(16).setText(osProperties.get("WindowsDirectory"));
			osFields.get(17).setText(osProperties.get("SystemDirectory"));
			
		} catch (ExecutionException e) {
			new ExceptionUI("OS Action Listener Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
		} catch (InterruptedException e) {
			new ExceptionUI("OS Action Listener Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
			Thread.currentThread().interrupt();
		}
	}
	
}