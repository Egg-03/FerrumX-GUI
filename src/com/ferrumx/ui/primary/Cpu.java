package com.ferrumx.ui.primary;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_AssociatedProcessorMemory;
import com.ferrumx.system.hardware.Win32_CacheMemory;
import com.ferrumx.system.hardware.Win32_Processor;
import com.ferrumx.ui.secondary.ExceptionUI;
import com.ferrumx.ui.utilities.IconImageChooser;

final class Cpu {

	private Cpu() {
		throw new IllegalStateException("Utility Class");
	}

	protected static boolean initializeCpu(JLabel cpuLogo, JTextArea cacheTa, JComboBox<String> cpuChoice,
			JTextField... cpuFields) {
		try {
			List<String> cpuList = Win32_Processor.getProcessorList();

			if (cpuList.isEmpty()) {
				new ExceptionUI("CPU Initialization Error", "FATAL ERROR: No CPU devices found").setVisible(true);
				return false;
			}

			for (String cpu : cpuList) {
				cpuChoice.addItem(cpu);
			}

			String currentCpu = cpuChoice.getItemAt(cpuChoice.getSelectedIndex());
			Map<String, String> cpuProperties = Win32_Processor.getCurrentProcessor(currentCpu);

			cpuFields[0].setText(cpuProperties.get("Name"));
			cpuFields[1].setText(cpuProperties.get("NumberOfCores"));
			cpuFields[2].setText(cpuProperties.get("ThreadCount"));
			cpuFields[3].setText(cpuProperties.get("NumberOfLogicalProcessors"));
			
			cpuFields[5].setText(cpuProperties.get("AddressWidth") + " bit");
			cpuFields[6].setText(cpuProperties.get("SocketDesignation"));
			cpuFields[7].setText(cpuProperties.get("ExtClock") + "MHz");
			cpuFields[9].setText(cpuProperties.get("MaxClockSpeed") + "MHz");
			cpuFields[10].setText(cpuProperties.get("Version"));
			cpuFields[11].setText(cpuProperties.get("Caption"));
			cpuFields[12].setText(cpuProperties.get("Family"));
			cpuFields[13].setText(cpuProperties.get("Stepping"));
			cpuFields[14].setText(cpuProperties.get("VirtualizationFirmwareEnabled"));
			cpuFields[15].setText(cpuProperties.get("ProcessorID"));
			cpuFields[16].setText(cpuProperties.get("L2CacheSize") + " KB");
			cpuFields[17].setText(cpuProperties.get("L3CacheSize") + " KB");

			cpuFields[8].setText(String.valueOf((Float.valueOf(cpuProperties.get("MaxClockSpeed"))
					/ Float.valueOf(cpuProperties.get("ExtClock")))));
			
			// set cpu logo img based on manufacturer
			String manufacturer = cpuProperties.get("Manufacturer");
			cpuFields[4].setText(manufacturer);
			IconImageChooser.cpuImageChooser(cpuLogo, manufacturer);
			
			List<String> cpuCacheList = Win32_AssociatedProcessorMemory.getCacheID(currentCpu);
			for (String currentCacheId : cpuCacheList) {
				Map<String, String> cpuCacheProperties = Win32_CacheMemory.getCPUCache(currentCacheId);
				cacheTa.append(cpuCacheProperties.get("Purpose") + ": " + cpuCacheProperties.get("InstalledSize")
						+ " KB - " + cpuCacheProperties.get("Associativity") + " way\n");
			}


		} catch (IndexOutOfBoundsException | IOException | ShellException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("CPU Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			return false;
		} catch (NumberFormatException e2) {
			cpuFields[8].setText("N/A");
			return true;
		} catch (InterruptedException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("CPU Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			Thread.currentThread().interrupt();
			return false;
		}

		addCpuChoiceActionEvent(cpuLogo, cpuChoice, cacheTa, cpuFields);
		return true;
	}

	private static void addCpuChoiceActionEvent(JLabel cpuLogo, JComboBox<String> cpuChoice, JTextArea cacheTa,
			JTextField... cpuFields) {
		cpuChoice.addActionListener(e -> 
			new Thread(()->{
				try {
					String currentCpu = cpuChoice.getItemAt(cpuChoice.getSelectedIndex());
					Map<String, String> cpuProperties = Win32_Processor.getCurrentProcessor(currentCpu);
					cpuFields[0].setText(cpuProperties.get("Name"));
					cpuFields[1].setText(cpuProperties.get("NumberOfCores"));
					cpuFields[2].setText(cpuProperties.get("ThreadCount"));
					cpuFields[3].setText(cpuProperties.get("NumberOfLogicalProcessors"));
					
					cpuFields[5].setText(cpuProperties.get("AddressWidth") + " bit");
					cpuFields[6].setText(cpuProperties.get("SocketDesignation"));
					cpuFields[7].setText(cpuProperties.get("ExtClock") + "MHz");
					cpuFields[9].setText(cpuProperties.get("MaxClockSpeed") + "MHz");
					cpuFields[10].setText(cpuProperties.get("Version"));
					cpuFields[11].setText(cpuProperties.get("Caption"));
					cpuFields[12].setText(cpuProperties.get("Family"));
					cpuFields[13].setText(cpuProperties.get("Stepping"));
					cpuFields[14].setText(cpuProperties.get("VirtualizationFirmwareEnabled"));
					cpuFields[15].setText(cpuProperties.get("ProcessorID"));
					cpuFields[16].setText(cpuProperties.get("L2CacheSize") + " KB");
					cpuFields[17].setText(cpuProperties.get("L3CacheSize") + " KB");

					cpuFields[8].setText(String.valueOf((Float.valueOf(cpuProperties.get("MaxClockSpeed"))
							/ Float.valueOf(cpuProperties.get("ExtClock")))));
					
					// set cpu logo img based on manufacturer
					String manufacturer = cpuProperties.get("Manufacturer");
					cpuFields[4].setText(manufacturer);
					IconImageChooser.cpuImageChooser(cpuLogo, manufacturer);
					
					cacheTa.selectAll();
					cacheTa.replaceSelection("");
					List<String> cpuCacheList = Win32_AssociatedProcessorMemory.getCacheID(currentCpu);
					for (String currentCacheId : cpuCacheList) {
						Map<String, String> cpuCacheProperties = Win32_CacheMemory.getCPUCache(currentCacheId);
						cacheTa.append(cpuCacheProperties.get("Purpose") + ": " + cpuCacheProperties.get("InstalledSize")
								+ " KB - " + cpuCacheProperties.get("Associativity") + " way\n");
					}
				} catch (IndexOutOfBoundsException | IOException | ShellException e2) {
					String errorMessage = e2.getMessage();
					String stackTrace = Arrays.toString(e2.getStackTrace());
					new ExceptionUI("CPU Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
				} catch (NumberFormatException e3) {
					cpuFields[8].setText("N/A");
				} catch (InterruptedException e1) {
					String errorMessage = e1.getMessage();
					String stackTrace = Arrays.toString(e1.getStackTrace());
					new ExceptionUI("CPU Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
					Thread.currentThread().interrupt();
				}
			}).start()
		);
	}
}
