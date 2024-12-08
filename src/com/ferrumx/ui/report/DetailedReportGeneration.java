package com.ferrumx.ui.report;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.currentuser.User;
import com.ferrumx.system.hardware.HardwareID;
import com.ferrumx.system.hardware.Win32_AssociatedProcessorMemory;
import com.ferrumx.system.hardware.Win32_BIOS;
import com.ferrumx.system.hardware.Win32_Baseboard;
import com.ferrumx.system.hardware.Win32_CacheMemory;
import com.ferrumx.system.hardware.Win32_DesktopMonitor;
import com.ferrumx.system.hardware.Win32_DiskDrive;
import com.ferrumx.system.hardware.Win32_NetworkAdapter;
import com.ferrumx.system.hardware.Win32_PhysicalMemory;
import com.ferrumx.system.hardware.Win32_PortConnector;
import com.ferrumx.system.hardware.Win32_Printer;
import com.ferrumx.system.hardware.Win32_Processor;
import com.ferrumx.system.hardware.Win32_SoundDevice;
import com.ferrumx.system.hardware.Win32_VideoController;
import com.ferrumx.system.networking.Win32_NetworkAdapterConfiguration;
import com.ferrumx.system.networking.Win32_NetworkAdapterSetting;
import com.ferrumx.system.operating_system.Win32_DiskDriveToDiskPartition;
import com.ferrumx.system.operating_system.Win32_LogicalDiskToPartition;
import com.ferrumx.system.operating_system.Win32_OperatingSystem;
import com.ferrumx.system.operating_system.Win32_TimeZone;

public class DetailedReportGeneration {
	
	private DetailedReportGeneration() {
		throw new IllegalStateException("Utility Class");
	}

	public static void generate(JTextArea reportDisplay, JTextArea errorDisplay, JButton detailedReportButton,
			JButton summarizedReportButton, JProgressBar progress) {
		// set progress bar value to 0
		progress.setValue(0);
		// disable button once computation starts
		detailedReportButton.setEnabled(false);
		summarizedReportButton.setEnabled(false);
		// clear the displays first
		reportDisplay.selectAll();
		reportDisplay.replaceSelection("");

		errorDisplay.selectAll();
		errorDisplay.replaceSelection("");

		// Start the computation in a separate thread to prevent blocking the main
		// thread and allow live update of the results
		// Since this class' methods are invoked only after the GUI is drawn in the main
		// thread, the two text areas
		// passed as parameters wont suffer from NullPointerExceptions
		// regardless of when this thread starts
		new Thread(() -> {
			reportDisplayHardwareID(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(2));

			reportDisplayCPU(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(6));

			reportDisplayCPUCache(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(13));

			reportDisplayGPU(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(20));

			reportDisplayRAM(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(28));

			reportDisplayMotherboard(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(35));

			reportDisplayBIOS(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(42));

			reportDisplayNetwork(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(50));

			reportDisplayDisk(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(57));
			
			reportDisplayMonitor(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(60));
			
			reportDisplayOS(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(64));

			reportDisplayUser(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(70));

			reportDisplayPrinter(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(77));

			reportDisplaySound(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(85));

			reportDisplayTimeZone(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(90));

			reportDisplayIO(reportDisplay, errorDisplay);
			SwingUtilities.invokeLater(() -> progress.setValue(100));

			// re-enable report buttons
			detailedReportButton.setEnabled(true);
			summarizedReportButton.setEnabled(true);
		}).start();
	}

	private static void reportDisplayBIOS(JTextArea reportDisplay, JTextArea errorDisplay) {
		reportDisplay.append("----------------------BIOS INFO------------------------\n");
		try {
			Map<String, String> BIOS = Win32_BIOS.getPrimaryBIOS();
			for (Map.Entry<String, String> entry : BIOS.entrySet()) {
				reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
			}
			if (BIOS.isEmpty()) {
				errorDisplay.append("BIOS Info: Unavailable\n");
			} else {
				errorDisplay.append("BIOS Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("BIOS ERROR: Unable to fetch BIOS Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("BIOS ERROR: Unable to fetch BIOS Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayCPU(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> currentCPU = Collections.emptyMap();

		reportDisplay.append("----------------------CPU INFO------------------------\n");
		try {
			deviceIDs = Win32_Processor.getProcessorList();
			for (String currentID : deviceIDs) {
				currentCPU = Win32_Processor.getCurrentProcessor(currentID);
				for (Map.Entry<String, String> entry : currentCPU.entrySet()) {
					reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
				}
				reportDisplay.append("\n");
			}
			if (currentCPU.isEmpty()) {
				errorDisplay.append("CPU Info: Unavailable\n");
			} else {
				errorDisplay.append("CPU Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("CPU ERROR: Unable to fetch CPU Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("CPU ERROR: Unable to fetch CPU Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayCPUCache(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> cpuID;
		List<String> cacheID;

		Map<String, String> cache = Collections.emptyMap();
		reportDisplay.append("----------------------CPU CACHE------------------------\n");
		try {
			cpuID = Win32_Processor.getProcessorList();
			for (String id : cpuID) {
				cacheID = Win32_AssociatedProcessorMemory.getCacheID(id);
				for (String currentCacheID : cacheID) {
					cache = Win32_CacheMemory.getCPUCache(currentCacheID);
					for (Map.Entry<String, String> currentCache : cache.entrySet()) {
						reportDisplay.append(currentCache.getKey() + ": " + currentCache.getValue() + "\n");
					}
					reportDisplay.append("\n");
				}
			}
			if (cache.isEmpty()) {
				errorDisplay.append("CPU Cache Info: Unavailable\n");
			} else {
				errorDisplay.append("CPU Cache Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("CPU CACHE ERROR: Unable to fetch CPU Cache Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("CPU CACHE ERROR: Unable to fetch CPU Cache Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayDisk(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> diskID;
		List<String> diskPartition;
		Map<String, String> disk = Collections.emptyMap();

		reportDisplay.append("----------------------STORAGE INFO------------------------\n");
		try {
			diskID = Win32_DiskDrive.getDriveID();
			for (String id : diskID) {
				disk = Win32_DiskDrive.getDrive(id);
				diskPartition = Win32_DiskDriveToDiskPartition.getPartitionList(id);
				for (Map.Entry<String, String> entry : disk.entrySet()) {
					reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
				}

				for (String currentPartition : diskPartition) {
					reportDisplay.append("Partition: " + currentPartition + ", Drive Letter: "
							+ Win32_LogicalDiskToPartition.getDriveLetter(currentPartition) + "\n");
				}
				reportDisplay.append("\n");
			}
			if (disk.isEmpty()) {
				errorDisplay.append("Storage Info: Unavailable\n");
			} else {
				errorDisplay.append("Storage Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("STORAGE ERROR: Unable to fetch Storage Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("STORAGE ERROR: Unable to fetch Storage Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayGPU(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> gpuIDs;
		Map<String, String> currentGPU = Collections.emptyMap();

		reportDisplay.append("----------------------VIDEO CONTROLLER------------------------\n");
		try {
			gpuIDs = Win32_VideoController.getGPUID();
			for (String currentID : gpuIDs) {
				currentGPU = Win32_VideoController.getGPU(currentID);
				for (Map.Entry<String, String> entry : currentGPU.entrySet()) {
					reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
				}
			}
			if (currentGPU.isEmpty()) {
				errorDisplay.append("GPU Info: Unavailable\n");
			} else {
				errorDisplay.append("GPU Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("GPU ERROR: Unable to fetch VideoCard Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("GPU ERROR: Unable to fetch VideoCard Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayHardwareID(JTextArea reportDisplay, JTextArea errorDisplay) {
		reportDisplay.append("-------------------HARDWARE ID----------------------\n");
		try {
			String hwid = HardwareID.getHardwareID();
			reportDisplay.append(hwid + "\n");
			if (hwid.isBlank() || hwid.isEmpty()) {
				errorDisplay.append("HWID Generation: Unavailable\n");
			} else {
				errorDisplay.append("HWID Generation: Success\n");
			}
		} catch (InterruptedException  e) {
			errorDisplay.append("HWID ERROR: Unable to fetch HWID Info\n" + e.getMessage() + "\n");
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			errorDisplay.append("HWID ERROR: Unable to fetch HWID Info\n" + e.getCause().getMessage() + "\n");
		}
	}

	private static void reportDisplayIO(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> portID;
		Map<String, String> ports = Collections.emptyMap();

		reportDisplay.append("----------------------MAINBOARD I/O INFO------------------------\n");
		try {
			portID = Win32_PortConnector.getBaseboardPortID();
			for (String id : portID) {
				ports = Win32_PortConnector.getBaseboardPorts(id);
				for (Map.Entry<String, String> port : ports.entrySet()) {
					reportDisplay.append(port.getKey() + ": " + port.getValue() + "\n");
				}
				reportDisplay.append("\n");
			}
			if (ports.isEmpty()) {
				errorDisplay.append("I/O Info: Unavailable\n");
			} else {
				errorDisplay.append("I/O Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("I/O ERROR: Unable to fetch Motherboard Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("I/O ERROR: Unable to fetch Motherboard Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayMonitor(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> monitorProperties = Collections.emptyMap();

		reportDisplay.append("----------------------DISPLAY------------------------\n");
		try {
			deviceIDs = Win32_DesktopMonitor.getMonitorID();
			for (String currentID : deviceIDs) {
				monitorProperties = Win32_DesktopMonitor.getMonitorProperties(currentID);
				for (Map.Entry<String, String> entry : monitorProperties.entrySet()) {
					reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
				}
			}
			if (monitorProperties.isEmpty()) {
				errorDisplay.append("Display Info: Unavailable\n");
			} else {
				errorDisplay.append("Display Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("DISPLAY ERROR: Unable to fetch Display Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("DISPLAY ERROR: Unable to fetch Display Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayMotherboard(JTextArea reportDisplay, JTextArea errorDisplay) {
		reportDisplay.append("----------------------MAINBOARD------------------------\n");
		try {
			Map<String, String> motherboard = Win32_Baseboard.getMotherboard();
			for (Map.Entry<String, String> entry : motherboard.entrySet()) {
				reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
			}

			if (motherboard.isEmpty()) {
				errorDisplay.append("Mainboard Info: Unavailable\n");
			} else {
				errorDisplay.append("Mainboard Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("MAINBOARD ERROR: Unable to fetch Motherboard Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("MAINBOARD ERROR: Unable to fetch Motherboard Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayNetwork(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> networkAdapter = Collections.emptyMap();
		Map<String, String> networkAdapterConfiguration = Collections.emptyMap();
		String index = "";

		reportDisplay.append("----------------------NETWORK INFO------------------------\n");
		try {
			deviceIDs = Win32_NetworkAdapter.getDeviceIDList();
			for (String currentID : deviceIDs) {
				networkAdapter = Win32_NetworkAdapter.getNetworkAdapters(currentID);
				index = Win32_NetworkAdapterSetting.getIndex(currentID);
				networkAdapterConfiguration = Win32_NetworkAdapterConfiguration.getAdapterConfiguration(index);
				for (Map.Entry<String, String> entry : networkAdapter.entrySet()) {
					reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
				}
				reportDisplay.append("\n");
				for (Map.Entry<String, String> entry : networkAdapterConfiguration.entrySet()) {
					reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
				}
				reportDisplay.append("\n");
			}
			if (networkAdapter.isEmpty() || networkAdapterConfiguration.isEmpty()) {
				errorDisplay.append("Network Info: Unavailable\n");
			} else {
				errorDisplay.append("Network Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("NETWORK ERROR: Unable to fetch Network Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("NETWORK ERROR: Unable to fetch Network Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayOS(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> oslist;
		Map<String, String> osinfo = Collections.emptyMap();

		reportDisplay.append("----------------------OS INFO------------------------\n");
		try {
			oslist = Win32_OperatingSystem.getOSList();
			for (String currentOS : oslist) {
				osinfo = Win32_OperatingSystem.getOSInfo(currentOS);
				for (Map.Entry<String, String> entry : osinfo.entrySet()) {
					reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
				}
			}
			if (osinfo.isEmpty()) {
				errorDisplay.append("OS Info: Unavailable\n");
			} else {
				errorDisplay.append("OS Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("OS ERROR: Unable to fetch Operating System Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("OS ERROR: Unable to fetch Operating System Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayPrinter(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> currentPrinter = Collections.emptyMap();

		reportDisplay.append("----------------------PRINTER INFO------------------------\n");
		try {
			deviceIDs = Win32_Printer.getDeviceIDList();
			for (String currentID : deviceIDs) {
				currentPrinter = Win32_Printer.getCurrentPrinter(currentID);
				for (Map.Entry<String, String> entry : currentPrinter.entrySet()) {
					reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
				}
				reportDisplay.append("\n");
			}
			if (currentPrinter.isEmpty()) {
				errorDisplay.append("Printer Info: Unavailable\n");
			} else {
				errorDisplay.append("Printer Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("PRINTER ERROR: Unable to fetch Printer Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("PRINTER ERROR: Unable to fetch Printer Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayRAM(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> memoryID;
		Map<String, String> memory = Collections.emptyMap();

		reportDisplay.append("----------------------SPD------------------------\n");
		try {
			memoryID = Win32_PhysicalMemory.getTag();
			for (String id : memoryID) {
				memory = Win32_PhysicalMemory.getMemory(id);
				for (Map.Entry<String, String> entry : memory.entrySet()) {
					reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
				}
				reportDisplay.append("\n");
			}
			if (memory.isEmpty()) {
				errorDisplay.append("Memory Info: Unavailable\n");
			} else {
				errorDisplay.append("Memory Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("MEMORY ERROR: Unable to fetch Memory Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("MEMORY ERROR: Unable to fetch Memory Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplaySound(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> currentAudio = Collections.emptyMap(); // this prevents null pointer exception

		reportDisplay.append("----------------------AUDIO INFO------------------------\n");
		try {
			deviceIDs = Win32_SoundDevice.getSoundDeviceID();
			for (String currentID : deviceIDs) {
				currentAudio = Win32_SoundDevice.getCurrentAudioDevice(currentID);
				for (Map.Entry<String, String> entry : currentAudio.entrySet()) {
					reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
				}
				reportDisplay.append("\n");
			}
			if (currentAudio.isEmpty()) {
				errorDisplay.append("Audio Info: Unavailable\n");
			} else {
				errorDisplay.append("Audio Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("AUDIO ERROR: Unable to fetch Audio Info\n" + e);
		} catch (InterruptedException e) {
			errorDisplay.append("AUDIO ERROR: Unable to fetch Audio Info\n" + e);
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayTimeZone(JTextArea reportDisplay, JTextArea errorDisplay) {
		Map<String, String> currentTimeZone = Collections.emptyMap();

		reportDisplay.append("----------------------TIMEZONE------------------------\n");
		try {
			currentTimeZone = Win32_TimeZone.getOSTimeZone();
			for (Map.Entry<String, String> entry : currentTimeZone.entrySet()) {
				reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n");
			}

			if (currentTimeZone.isEmpty()) {
				errorDisplay.append("Time-zone Info: Unavailable\n");
			} else {
				errorDisplay.append("Time-zone Info: Success\n");
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			errorDisplay.append("TIMEZONE ERROR: Unable to fetch TimeZone Info\n" + e + "\n");
		} catch (InterruptedException e) {
			errorDisplay.append("TIMEZONE ERROR: Unable to fetch TimeZone Info\n" + e + "\n");
			Thread.currentThread().interrupt();
		}
	}
	
	private static void reportDisplayUser(JTextArea reportDisplay, JTextArea errorDisplay) {
		reportDisplay.append("----------------------USER INFO------------------------\n");
		reportDisplay.append("Current Username: " + User.getUsername() + "\n");
		reportDisplay.append("User Home Directory: " + User.getHome() + "\n");
		errorDisplay.append("User Info: Success\n");
	}
}
