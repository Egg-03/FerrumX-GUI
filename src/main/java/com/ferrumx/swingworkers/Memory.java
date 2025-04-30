package com.ferrumx.swingworkers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.tinylog.Logger;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_PhysicalMemory;
import com.ferrumx.ui.secondary.ExceptionUI;

public class Memory extends SwingWorker<Map<String, String>, List<String>> {
	
	private JComboBox<String> memoryChoice;
	private List<JTextField> memoryFields;
	
	public Memory(JComboBox<String> memoryChoice, List<JTextField> memoryFields) {
		this.memoryChoice = memoryChoice;
		this.memoryFields = memoryFields;
	}

	@Override
	protected Map<String, String> doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		List<String> memorySlots = Win32_PhysicalMemory.getTag();
		publish(memorySlots);
		
		return Win32_PhysicalMemory.getMemory(memorySlots.getFirst());
	}
	
	@Override
	protected void process(List<List<String>> chunks) {
		for(List<String> tags:chunks) {
			for(String tag:tags) {
				memoryChoice.addItem(tag);
			}
		}
	}
	
	@Override
	protected void done() {
		try {
			Map<String, String> memoryProperties = get();
			
			memoryFields.get(0).setText(memoryProperties.get("Name"));
			memoryFields.get(1).setText(memoryProperties.get("Manufacturer"));
			memoryFields.get(2).setText(memoryProperties.get("Model"));
			memoryFields.get(3).setText(memoryProperties.get("OtherIdentifyingInfo"));
			memoryFields.get(5).setText(memoryProperties.get("FormFactor"));
			memoryFields.get(6).setText(memoryProperties.get("BankLabel"));

			memoryFields.get(8).setText(memoryProperties.get("DataWidth") + " Bit");
			memoryFields.get(9).setText(memoryProperties.get("Speed") + " MT/s");
			memoryFields.get(10).setText(memoryProperties.get("ConfiguredClockSpeed") + " MT/s");
			memoryFields.get(11).setText(memoryProperties.get("DeviceLocator"));
			memoryFields.get(12).setText(memoryProperties.get("SerialNumber"));

			Long memoryCapacity = Long.valueOf(memoryProperties.get("Capacity")) / (1024 * 1024);
			memoryFields.get(7).setText((String.valueOf(memoryCapacity) + " MB"));
			
			memoryChoice.addActionListener(e-> new MemoryActionListener(memoryChoice, memoryFields).execute());
		} catch (ExecutionException e) {
			new ExceptionUI("Memory Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
		} catch (NumberFormatException e1) {
			memoryFields.get(7).setText("N/A"); // sets RAM capacity field to N/A in case the adapterRAM property cannot be
			// parsed into a Long value
		} catch (InterruptedException e) {
			new ExceptionUI("Memory Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
			Thread.currentThread().interrupt();
		}
	}

}

class MemoryActionListener extends SwingWorker<Map<String, String>, Void> {
	
	private JComboBox<String> memoryChoice;
	private List<JTextField> memoryFields;
	
	public MemoryActionListener(JComboBox<String> memoryChoice, List<JTextField> memoryFields) {
		this.memoryChoice = memoryChoice;
		this.memoryFields = memoryFields;
	}

	@Override
	protected Map<String, String> doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		return Win32_PhysicalMemory.getMemory(memoryChoice.getItemAt(memoryChoice.getSelectedIndex()));
	}
	
	@Override
	protected void done() {
		try {
			Map<String, String> memoryProperties = get();
			
			memoryFields.get(0).setText(memoryProperties.get("Name"));
			memoryFields.get(1).setText(memoryProperties.get("Manufacturer"));
			memoryFields.get(2).setText(memoryProperties.get("Model"));
			memoryFields.get(3).setText(memoryProperties.get("OtherIdentifyingInfo"));
			memoryFields.get(5).setText(memoryProperties.get("FormFactor"));
			memoryFields.get(6).setText(memoryProperties.get("BankLabel"));

			memoryFields.get(8).setText(memoryProperties.get("DataWidth") + " Bit");
			memoryFields.get(9).setText(memoryProperties.get("Speed") + " MT/s");
			memoryFields.get(10).setText(memoryProperties.get("ConfiguredClockSpeed") + " MT/s");
			memoryFields.get(11).setText(memoryProperties.get("DeviceLocator"));
			memoryFields.get(12).setText(memoryProperties.get("SerialNumber"));

			Long memoryCapacity = Long.valueOf(memoryProperties.get("Capacity")) / (1024 * 1024);
			memoryFields.get(7).setText((String.valueOf(memoryCapacity) + " MB"));
		} catch (ExecutionException e) {
			new ExceptionUI("Memory Action Listener Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
		} catch (NumberFormatException e1) {
			memoryFields.get(7).setText("N/A"); // sets RAM capacity field to N/A in case the adapterRAM property cannot be
			// parsed into a Long value
		} catch (InterruptedException e) {
			new ExceptionUI("Memory Action Listener Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
			Thread.currentThread().interrupt();
		}
	}
	
}
