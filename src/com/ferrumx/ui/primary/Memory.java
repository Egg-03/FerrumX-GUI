package com.ferrumx.ui.primary;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_PhysicalMemory;
import com.ferrumx.ui.secondary.ExceptionUI;

final class Memory {

	private Memory() {
		throw new IllegalStateException("Utility Class");
	}

	protected static boolean initializeMemory(JComboBox<String> memoryChoice, JTextField... memoryFields) {
		try {
			List<String> memorySlots = Win32_PhysicalMemory.getTag();

			if (memorySlots.isEmpty()) {
				new ExceptionUI("Memory Initialization Error", "FATAL ERROR: No Memory hardware found")
						.setVisible(true);
				return false;
			}

			for (String currentSlot : memorySlots) {
				memoryChoice.addItem(currentSlot);
			}

			Map<String, String> memoryProperties = Win32_PhysicalMemory
					.getMemory(memoryChoice.getItemAt(memoryChoice.getSelectedIndex()));
			memoryFields[0].setText(memoryProperties.get("Name"));
			memoryFields[1].setText(memoryProperties.get("Manufacturer"));
			memoryFields[2].setText(memoryProperties.get("Model"));
			memoryFields[3].setText(memoryProperties.get("OtherIdentifyingInfo"));
			memoryFields[4].setText(memoryProperties.get("PartNumber"));
			memoryFields[5].setText(memoryProperties.get("FormFactor"));
			memoryFields[6].setText(memoryProperties.get("BankLabel"));

			memoryFields[8].setText(memoryProperties.get("DataWidth") + " Bit");
			memoryFields[9].setText(memoryProperties.get("Speed") + " MT/s");
			memoryFields[10].setText(memoryProperties.get("ConfiguredClockSpeed") + " MT/s");
			memoryFields[11].setText(memoryProperties.get("DeviceLocator"));
			memoryFields[12].setText(memoryProperties.get("SerialNumber"));

			Long memoryCapacity = Long.valueOf(memoryProperties.get("Capacity")) / (1024 * 1024);
			memoryFields[7].setText((String.valueOf(memoryCapacity) + " MB"));

		} catch (IndexOutOfBoundsException | IOException | ShellException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Memory Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			return false;
		} catch (NumberFormatException e1) {
			memoryFields[7].setText("N/A"); // sets RAM capacity field to N/A in case the adapterRAM property cannot be
											// parsed into a Long value
		} catch (InterruptedException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Memory Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			Thread.currentThread().interrupt();
			return false;
		}
		addMemoryChoiceActionListner(memoryChoice, memoryFields);
		return true;
	}

	private static void addMemoryChoiceActionListner(JComboBox<String> memoryChoice, JTextField... memoryFields) {
		memoryChoice.addActionListener(e ->
			new Thread(()->{
				try {
					Map<String, String> memoryProperties = Win32_PhysicalMemory
							.getMemory(memoryChoice.getItemAt(memoryChoice.getSelectedIndex()));
					memoryFields[0].setText(memoryProperties.get("Name"));
					memoryFields[1].setText(memoryProperties.get("Manufacturer"));
					memoryFields[2].setText(memoryProperties.get("Model"));
					memoryFields[3].setText(memoryProperties.get("OtherIdentifyingInfo"));
					memoryFields[4].setText(memoryProperties.get("PartNumber"));
					memoryFields[5].setText(memoryProperties.get("FormFactor"));
					memoryFields[6].setText(memoryProperties.get("BankLabel"));

					memoryFields[8].setText(memoryProperties.get("DataWidth") + " Bit");
					memoryFields[9].setText(memoryProperties.get("Speed") + " MT/s");
					memoryFields[10].setText(memoryProperties.get("ConfiguredClockSpeed") + " MT/s");
					memoryFields[11].setText(memoryProperties.get("DeviceLocator"));
					memoryFields[12].setText(memoryProperties.get("SerialNumber"));

					Long memoryCapacity = Long.valueOf(memoryProperties.get("Capacity")) / (1024 * 1024);
					memoryFields[7].setText((String.valueOf(memoryCapacity) + " MB"));

				} catch (IndexOutOfBoundsException | IOException | ShellException e2) {
					String errorMessage = e2.getMessage();
					String stackTrace = Arrays.toString(e2.getStackTrace());
					new ExceptionUI("Memory Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
				} catch (NumberFormatException e1) {
					memoryFields[7].setText("N/A"); // sets RAM capacity field to N/A in case the adapterRAM property cannot
													// be parsed into a Long value
				} catch (InterruptedException e1) {
					String errorMessage = e1.getMessage();
					String stackTrace = Arrays.toString(e1.getStackTrace());
					new ExceptionUI("Memory Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
					Thread.currentThread().interrupt();
				}
			}).start()
		 );
	}
}
