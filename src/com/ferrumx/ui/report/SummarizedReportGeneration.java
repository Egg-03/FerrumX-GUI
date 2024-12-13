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
import com.ferrumx.formatter.cim.CIM_ML;
import com.ferrumx.system.currentuser.User;
import com.ferrumx.system.hardware.HardwareID;
import com.ferrumx.system.hardware.Win32_AssociatedProcessorMemory;
import com.ferrumx.system.hardware.Win32_DesktopMonitor;
import com.ferrumx.system.hardware.Win32_DiskDrive;
import com.ferrumx.system.hardware.Win32_NetworkAdapter;
import com.ferrumx.system.hardware.Win32_PhysicalMemory;
import com.ferrumx.system.hardware.Win32_PortConnector;
import com.ferrumx.system.hardware.Win32_Printer;
import com.ferrumx.system.hardware.Win32_Processor;
import com.ferrumx.system.hardware.Win32_SoundDevice;
import com.ferrumx.system.hardware.Win32_VideoController;
import com.ferrumx.system.networking.Win32_NetworkAdapterSetting;
import com.ferrumx.system.operating_system.Win32_DiskDriveToDiskPartition;
import com.ferrumx.system.operating_system.Win32_LogicalDiskToPartition;
import com.ferrumx.system.operating_system.Win32_OperatingSystem;
import com.ferrumx.system.operating_system.Win32_TimeZone;

public class SummarizedReportGeneration {
	
	private SummarizedReportGeneration() {
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
		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------BIOS INFO------------------------\n"));
		try {
			Map<String, String> BIOS = CIM_ML.getWhere("Win32_BIOS", "PrimaryBIOS", "True",
					"Name, Manufacturer, ReleaseDate");
			for (Map.Entry<String, String> entry : BIOS.entrySet()) {
				SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
			}
			if (BIOS.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("BIOS Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("BIOS Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("BIOS ERROR: Unable to fetch BIOS Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("BIOS ERROR: Unable to fetch BIOS Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayCPU(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> currentCPU = Collections.emptyMap();

		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------CPU INFO------------------------\n"));
		try {
			deviceIDs = Win32_Processor.getProcessorList();
			for (String currentID : deviceIDs) {
				currentCPU = CIM_ML.getWhere("Win32_Processor", "DeviceID", currentID,
						"Name, NumberOfCores, ThreadCount, NumberOfLogicalProcessors, Manufacturer");
				for (Map.Entry<String, String> entry : currentCPU.entrySet()) {
					SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
				}
				SwingUtilities.invokeLater(()->reportDisplay.append("\n"));
			}
			if (currentCPU.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("CPU Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("CPU Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("CPU ERROR: Unable to fetch CPU Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("CPU ERROR: Unable to fetch CPU Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayCPUCache(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> cpuID;
		List<String> cacheID;

		Map<String, String> cache = Collections.emptyMap();
		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------CPU CACHE------------------------\n"));
		try {
			cpuID = Win32_Processor.getProcessorList();
			for (String id : cpuID) {
				cacheID = Win32_AssociatedProcessorMemory.getCacheID(id);
				for (String currentCacheID : cacheID) {
					cache = CIM_ML.getWhere("Win32_CacheMemory", "DeviceID", currentCacheID, "Purpose, InstalledSize");
					for (Map.Entry<String, String> currentCache : cache.entrySet()) {
						SwingUtilities.invokeLater(()->reportDisplay.append(currentCache.getKey() + ": " + currentCache.getValue()+"\n"));
					}
					SwingUtilities.invokeLater(()->reportDisplay.append("\n"));
				}
			}
			if (cache.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("CPU Cache Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("CPU Cache Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("CPU CACHE ERROR: Unable to fetch CPU Cache Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("CPU CACHE ERROR: Unable to fetch CPU Cache Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayDisk(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> diskID;
		List<String> diskPartition;
		Map<String, String> disk = Collections.emptyMap();

		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------STORAGE INFO------------------------\n"));
		try {
			diskID = Win32_DiskDrive.getDriveID();
			for (String id : diskID) {
				disk = CIM_ML.getWhere("Win32_DiskDrive", "DeviceID", id, "Model, Size, Status");
				diskPartition = Win32_DiskDriveToDiskPartition.getPartitionList(id);
				for (Map.Entry<String, String> entry : disk.entrySet()) {
					SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
				}

				for (String currentPartition : diskPartition) {
					reportDisplay.append("Partition: " + currentPartition + ", Drive Letter: "
							+ Win32_LogicalDiskToPartition.getDriveLetter(currentPartition) + "\n");
				}
				SwingUtilities.invokeLater(()->reportDisplay.append("\n"));
			}
			if (disk.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("Storage Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("Storage Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("STORAGE ERROR: Unable to fetch Storage Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("STORAGE ERROR: Unable to fetch Storage Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayGPU(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> gpuIDs;
		Map<String, String> currentGPU = Collections.emptyMap();

		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------VIDEO CONTROLLER------------------------\n"));
		try {
			gpuIDs = Win32_VideoController.getGPUID();
			for (String currentID : gpuIDs) {
				currentGPU = CIM_ML.getWhere("Win32_VideoController", "DeviceID", currentID,
						"Name, VideoProcessor, DriverVersion, AdapterRAM, CurrentHorizontalResolution, CurrentVerticalResolution");
				for (Map.Entry<String, String> entry : currentGPU.entrySet()) {
					SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
				}
			}
			if (currentGPU.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("GPU Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("GPU Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("GPU ERROR: Unable to fetch VideoCard Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("GPU ERROR: Unable to fetch VideoCard Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayHardwareID(JTextArea reportDisplay, JTextArea errorDisplay) {
		SwingUtilities.invokeLater(()->reportDisplay.append("-------------------HARDWARE ID----------------------\n"));
		try {
			String hwid = HardwareID.getHardwareID();
			SwingUtilities.invokeLater(()->reportDisplay.append(hwid + "\n"));
			if (hwid.isBlank() || hwid.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("HWID Generation: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("HWID Generation: Success\n"));
			}
		} catch (InterruptedException  e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("HWID ERROR: Unable to fetch HWID Info\n" + e.getMessage() + "\n"));
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("HWID ERROR: Unable to fetch HWID Info\n" + e.getCause().getMessage() + "\n"));
		}
	}

	private static void reportDisplayIO(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> portID;
		Map<String, String> ports = Collections.emptyMap();

		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------MAINBOARD I/O INFO------------------------\n"));
		try {
			portID = Win32_PortConnector.getBaseboardPortID();
			for (String id : portID) {
				ports = CIM_ML.getWhere("Win32_PortConnector", "Tag", id, "ExternalReferenceDesignator");
				for (Map.Entry<String, String> port : ports.entrySet()) {
					SwingUtilities.invokeLater(()->reportDisplay.append(port.getValue() + "\n"));
				}
			}
			if (ports.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("I/O Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("I/O Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("I/O ERROR: Unable to fetch Motherboard Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("I/O ERROR: Unable to fetch Motherboard Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayMonitor(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> monitorProperties = Collections.emptyMap();

		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------DISPLAY------------------------\n"));
		try {
			deviceIDs = Win32_DesktopMonitor.getMonitorID();
			for (String currentID : deviceIDs) {
				monitorProperties = Win32_DesktopMonitor.getMonitorProperties(currentID);
				for (Map.Entry<String, String> entry : monitorProperties.entrySet()) {
					SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
				}
			}
			if (monitorProperties.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("Display Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("Display Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("DISPLAY ERROR: Unable to fetch Display Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("DISPLAY ERROR: Unable to fetch Display Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayMotherboard(JTextArea reportDisplay, JTextArea errorDisplay) {
		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------MAINBOARD------------------------\n"));
		try {
			Map<String, String> motherboard = CIM_ML.get("Win32_Baseboard", "Manufacturer, Model, Product");
			for (Map.Entry<String, String> entry : motherboard.entrySet()) {
				SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
			}

			if (motherboard.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("Mainboard Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("Mainboard Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("MAINBOARD ERROR: Unable to fetch Motherboard Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("MAINBOARD ERROR: Unable to fetch Motherboard Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayNetwork(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> networkAdapter = Collections.emptyMap();
		Map<String, String> networkAdapterConfiguration = Collections.emptyMap();
		String index = "";

		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------NETWORK INFO------------------------\n"));
		try {
			deviceIDs = Win32_NetworkAdapter.getDeviceIDList();
			for (String currentID : deviceIDs) {
				networkAdapter = CIM_ML.getWhere("Win32_NetworkAdapter", "DeviceID", currentID,
						"Name, MACAddress, NetConnectionID");
				index = Win32_NetworkAdapterSetting.getIndex(currentID);
				networkAdapterConfiguration = CIM_ML.getWhere("Win32_NetworkAdapterConfiguration", "Index", index,
						"IPAddress, IPSubnet, DefaultIPGateway, DHCPServer, DNSServerSearchOrder");
				for (Map.Entry<String, String> entry : networkAdapter.entrySet()) {
					SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
				}
				SwingUtilities.invokeLater(()->reportDisplay.append("\n"));
				for (Map.Entry<String, String> entry : networkAdapterConfiguration.entrySet()) {
					SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
				}
				SwingUtilities.invokeLater(()->reportDisplay.append("\n"));
			}
			if (networkAdapter.isEmpty() || networkAdapterConfiguration.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("Network Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("Network Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("NETWORK ERROR: Unable to fetch Network Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("NETWORK ERROR: Unable to fetch Network Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayOS(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> oslist;
		Map<String, String> osinfo = Collections.emptyMap();

		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------OS INFO------------------------\n"));
		try {
			oslist = Win32_OperatingSystem.getOSList();
			for (String currentOS : oslist) {
				osinfo = CIM_ML.getWhere("Win32_OperatingSystem", "Name", currentOS,
						"Caption, InstallDate, CSName, BuildNumber, OSArchitecture, WindowsDirectory");
				for (Map.Entry<String, String> entry : osinfo.entrySet()) {
					SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
				}
			}
			if (osinfo.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("OS Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("OS Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("OS ERROR: Unable to fetch Operating System Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("OS ERROR: Unable to fetch Operating System Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayPrinter(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> currentPrinter = Collections.emptyMap();

		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------PRINTER INFO------------------------\n"));
		try {
			deviceIDs = Win32_Printer.getDeviceIDList();
			for (String currentID : deviceIDs) {
				currentPrinter = CIM_ML.getWhere("Win32_Printer", "DeviceID", currentID,
						"Name, HorizontalResolution, VerticalResolution, DriverName, Local, Network");
				for (Map.Entry<String, String> entry : currentPrinter.entrySet()) {
					SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
				}
				SwingUtilities.invokeLater(()->reportDisplay.append("\n"));
			}
			if (currentPrinter.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("Printer Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("Printer Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("PRINTER ERROR: Unable to fetch Printer Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("PRINTER ERROR: Unable to fetch Printer Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayRAM(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> memoryID;
		Map<String, String> memory = Collections.emptyMap();

		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------SPD------------------------\n"));
		try {
			memoryID = Win32_PhysicalMemory.getTag();
			for (String id : memoryID) {
				memory = CIM_ML.getWhere("Win32_PhysicalMemory", "Tag", id,
						"Manufacturer, Model, PartNumber, Capacity, Speed");
				for (Map.Entry<String, String> entry : memory.entrySet()) {
					SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
				}
				SwingUtilities.invokeLater(()->reportDisplay.append("\n"));
			}
			if (memory.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("Memory Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("Memory Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("MEMORY ERROR: Unable to fetch Memory Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("MEMORY ERROR: Unable to fetch Memory Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplaySound(JTextArea reportDisplay, JTextArea errorDisplay) {
		List<String> deviceIDs;
		Map<String, String> currentAudio = Collections.emptyMap(); // this prevents null pointer exception

		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------AUDIO INFO------------------------\n"));
		try {
			deviceIDs = Win32_SoundDevice.getSoundDeviceID();
			for (String currentID : deviceIDs) {
				currentAudio = CIM_ML.getWhere("Win32_SoundDevice", "DeviceID", currentID,
						"Caption, Manufacturer, Status");
				for (Map.Entry<String, String> entry : currentAudio.entrySet()) {
					SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
				}
				SwingUtilities.invokeLater(()->reportDisplay.append("\n"));
			}
			if (currentAudio.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("Audio Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("Audio Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("AUDIO ERROR: Unable to fetch Audio Info\n" + e));
		} catch (InterruptedException e) {
			errorDisplay.append("AUDIO ERROR: Unable to fetch Audio Info\n" + e);
			Thread.currentThread().interrupt();
		}
	}

	private static void reportDisplayTimeZone(JTextArea reportDisplay, JTextArea errorDisplay) {
		Map<String, String> currentTimeZone = Collections.emptyMap();

		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------TIMEZONE------------------------\n"));
		try {
			currentTimeZone = Win32_TimeZone.getOSTimeZone();
			for (Map.Entry<String, String> entry : currentTimeZone.entrySet()) {
				SwingUtilities.invokeLater(()->reportDisplay.append(entry.getKey() + ": " + entry.getValue() + "\n"));
			}

			if (currentTimeZone.isEmpty()) {
				SwingUtilities.invokeLater(()->errorDisplay.append("Time-zone Info: Unavailable\n"));
			} else {
				SwingUtilities.invokeLater(()->errorDisplay.append("Time-zone Info: Success\n"));
			}
		} catch (IOException | IndexOutOfBoundsException | ShellException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("TIMEZONE ERROR: Unable to fetch TimeZone Info\n" + e + "\n"));
		} catch (InterruptedException e) {
			SwingUtilities.invokeLater(()->errorDisplay.append("TIMEZONE ERROR: Unable to fetch TimeZone Info\n" + e + "\n"));
			Thread.currentThread().interrupt();
		}
	}
	
	private static void reportDisplayUser(JTextArea reportDisplay, JTextArea errorDisplay) {
		SwingUtilities.invokeLater(()->reportDisplay.append("----------------------USER INFO------------------------\n"));
		SwingUtilities.invokeLater(()->reportDisplay.append("Current Username: " + User.getUsername() + "\n"));
		SwingUtilities.invokeLater(()->reportDisplay.append("User Home Directory: " + User.getHome() + "\n"));
		SwingUtilities.invokeLater(()->errorDisplay.append("User Info: Success\n"));
	}
}
