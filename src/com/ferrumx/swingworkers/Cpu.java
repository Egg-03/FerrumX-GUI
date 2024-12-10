package com.ferrumx.swingworkers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_AssociatedProcessorMemory;
import com.ferrumx.system.hardware.Win32_CacheMemory;
import com.ferrumx.system.hardware.Win32_Processor;
import com.ferrumx.ui.utilities.IconImageChooser;

public class Cpu extends SwingWorker<Map<String, String>, List<String>> {
	
	private JLabel cpuLogo;
	private JComboBox<String> cpuChoice;
	private JTextArea cacheArea;
	private List<JTextField> cpuFields;
	
	public Cpu(JLabel cpuLogo, JComboBox<String> cpuChoice, JTextArea cacheArea, List<JTextField> cpuFields) {
		this.cpuLogo = cpuLogo;
		this.cpuChoice = cpuChoice;
		this.cacheArea = cacheArea;
		this.cpuFields = cpuFields;
	}

	@Override
	protected Map<String, String> doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		
		List<String> cpuList = Win32_Processor.getProcessorList();
		publish(cpuList);
		
		new CpuCache(cpuList.getFirst(), cacheArea).execute();
		return Win32_Processor.getCurrentProcessor(cpuList.getFirst());
	}
	
	@Override
	protected void process(List<List<String>> chunks) {
		for(List<String> cpuList: chunks) {
			for(String cpu: cpuList) {
				cpuChoice.addItem(cpu);
			}
		}
	}
	
	@Override
	protected void done() {
		try {
			Map<String, String> cpuProperties = get();
			
			cpuFields.get(0).setText(cpuProperties.get("Name"));
			cpuFields.get(1).setText(cpuProperties.get("NumberOfCores"));
			cpuFields.get(2).setText(cpuProperties.get("ThreadCount"));
			cpuFields.get(3).setText(cpuProperties.get("NumberOfLogicalProcessors"));
			
			cpuFields.get(5).setText(cpuProperties.get("AddressWidth") + " bit");
			cpuFields.get(6).setText(cpuProperties.get("SocketDesignation"));
			cpuFields.get(7).setText(cpuProperties.get("ExtClock") + "MHz");
			cpuFields.get(9).setText(cpuProperties.get("MaxClockSpeed") + "MHz");
			cpuFields.get(10).setText(cpuProperties.get("Version"));
			cpuFields.get(11).setText(cpuProperties.get("Caption"));
			cpuFields.get(12).setText(cpuProperties.get("Family"));
			cpuFields.get(13).setText(cpuProperties.get("Stepping"));
			cpuFields.get(14).setText(cpuProperties.get("VirtualizationFirmwareEnabled"));
			cpuFields.get(15).setText(cpuProperties.get("ProcessorID"));
			cpuFields.get(16).setText(cpuProperties.get("L2CacheSize") + " KB");
			cpuFields.get(17).setText(cpuProperties.get("L3CacheSize") + " KB");

			cpuFields.get(8).setText(String.valueOf((Float.valueOf(cpuProperties.get("MaxClockSpeed"))
					/ Float.valueOf(cpuProperties.get("ExtClock")))));
			
			// set cpu logo img based on manufacturer
			String manufacturer = cpuProperties.get("Manufacturer");
			cpuFields.get(4).setText(manufacturer);
			IconImageChooser.cpuImageChooser(cpuLogo, manufacturer);
			
			//add action listener to the cpu choice combo box
			cpuChoice.addActionListener(e-> new CpuActionListener(cpuLogo, cpuChoice, cacheArea, cpuFields).execute());
			
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e3) {
			cpuFields.get(8).setText("N/A");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
}

class CpuActionListener extends SwingWorker<Map<String, String>, Void>{
	
	private JLabel cpuLogo;
	private JComboBox<String> cpuChoice;
	private JTextArea cacheArea;
	private List<JTextField> cpuFields;
	
	public CpuActionListener (JLabel cpuLogo, JComboBox<String> cpuChoice, JTextArea cacheArea, List<JTextField> cpuFields) {
		this.cpuLogo = cpuLogo;
		this.cpuChoice = cpuChoice;
		this.cacheArea = cacheArea;
		this.cpuFields = cpuFields;
	}

	@Override
	protected Map<String, String> doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException  {
		String currentCPU = this.cpuChoice.getItemAt(this.cpuChoice.getSelectedIndex());
		new CpuCache(currentCPU, cacheArea).execute();
		return Win32_Processor.getCurrentProcessor(currentCPU);
	}
	
	@Override
	protected void done() {
		try {
			Map<String, String> cpuProperties = get();
			
			cpuFields.get(0).setText(cpuProperties.get("Name"));
			cpuFields.get(1).setText(cpuProperties.get("NumberOfCores"));
			cpuFields.get(2).setText(cpuProperties.get("ThreadCount"));
			cpuFields.get(3).setText(cpuProperties.get("NumberOfLogicalProcessors"));
			
			cpuFields.get(5).setText(cpuProperties.get("AddressWidth") + " bit");
			cpuFields.get(6).setText(cpuProperties.get("SocketDesignation"));
			cpuFields.get(7).setText(cpuProperties.get("ExtClock") + "MHz");
			cpuFields.get(9).setText(cpuProperties.get("MaxClockSpeed") + "MHz");
			cpuFields.get(10).setText(cpuProperties.get("Version"));
			cpuFields.get(11).setText(cpuProperties.get("Caption"));
			cpuFields.get(12).setText(cpuProperties.get("Family"));
			cpuFields.get(13).setText(cpuProperties.get("Stepping"));
			cpuFields.get(14).setText(cpuProperties.get("VirtualizationFirmwareEnabled"));
			cpuFields.get(15).setText(cpuProperties.get("ProcessorID"));
			cpuFields.get(16).setText(cpuProperties.get("L2CacheSize") + " KB");
			cpuFields.get(17).setText(cpuProperties.get("L3CacheSize") + " KB");

			cpuFields.get(8).setText(String.valueOf((Float.valueOf(cpuProperties.get("MaxClockSpeed"))
					/ Float.valueOf(cpuProperties.get("ExtClock")))));
			
			// set cpu logo img based on manufacturer
			String manufacturer = cpuProperties.get("Manufacturer");
			cpuFields.get(4).setText(manufacturer);
			IconImageChooser.cpuImageChooser(cpuLogo, manufacturer);
			
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e3) {
			cpuFields.get(8).setText("N/A");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}	
}

class CpuCache extends SwingWorker<Void, Map<String, String>> {
	
	private String currentCpu;
	private JTextArea cacheArea;
	
	public CpuCache(String cpuChoice, JTextArea cacheArea) {
		this.currentCpu = cpuChoice;
		this.cacheArea = cacheArea;
	}

	@Override
	protected Void doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		List<String> cpuCacheList = Win32_AssociatedProcessorMemory.getCacheID(currentCpu);
		for (String currentCacheId : cpuCacheList) {
			publish(Win32_CacheMemory.getCPUCache(currentCacheId));		
		}
		return null;
	}
	
	@Override
	protected void process(List<Map<String, String>> chunks) {
		for(Map<String, String> cpuCacheProperties: chunks) {
			cacheArea.append(cpuCacheProperties.get("Purpose") + ": " + cpuCacheProperties.get("InstalledSize")
			+ " KB - " + cpuCacheProperties.get("Associativity") + " way\n");
		}
	}
	
	@Override
	protected void done() {
		try {
			get();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			cacheArea.setText("N/A");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			cacheArea.setText("N/A");
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

}

