package com.ferrumx.ui.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.tinylog.Logger;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.HardwareID;
import com.ferrumx.system.hardware.Win32_AssociatedProcessorMemory;
import com.ferrumx.system.hardware.Win32_BIOS;
import com.ferrumx.system.hardware.Win32_Baseboard;
import com.ferrumx.system.hardware.Win32_CacheMemory;
import com.ferrumx.system.hardware.Win32_DiskDrive;
import com.ferrumx.system.hardware.Win32_NetworkAdapter;
import com.ferrumx.system.hardware.Win32_PhysicalMemory;
import com.ferrumx.system.hardware.Win32_PortConnector;
import com.ferrumx.system.hardware.Win32_Processor;
import com.ferrumx.system.hardware.Win32_VideoController;
import com.ferrumx.system.networking.Win32_NetworkAdapterConfiguration;
import com.ferrumx.system.networking.Win32_NetworkAdapterSetting;
import com.ferrumx.system.operating_system.Win32_DiskDriveToDiskPartition;
import com.ferrumx.system.operating_system.Win32_LogicalDiskToPartition;
import com.ferrumx.system.operating_system.Win32_OperatingSystem;

public class DetailedReportGenerationMultiThreaded  {
	
	private JTextArea reportDisplay;
	private JTextArea progressDisplay;
	private JButton detailedReportButton;
	private JProgressBar progress;
	
	public DetailedReportGenerationMultiThreaded (JTextArea reportDisplay, JTextArea errorDisplay, JButton detailedReportButton, JProgressBar progress) {
		this.reportDisplay = reportDisplay;
		this.progressDisplay = errorDisplay;
		this.detailedReportButton = detailedReportButton;
		this.progress = progress;
		
		clearComponentStatuses();
		new Thread(this::compute).start();
	}
	
	private void clearComponentStatuses() {
		SwingUtilities.invokeLater(()->{
			reportDisplay.setText(null);
			progressDisplay.setText(null);
			progress.setIndeterminate(true);
			detailedReportButton.setEnabled(false);
		});
	}
	
	private void compute() {
		
		StringBuilder sb = new StringBuilder();
		
		try(ExecutorService exec = Executors.newCachedThreadPool()) {
			
			List<Future<String>> futureCompute = new ArrayList<>();
			
			futureCompute.add(exec.submit(this::hwid));
	        futureCompute.add(exec.submit(this::cpu));
	        futureCompute.add(exec.submit(this::cpuCache));
	        futureCompute.add(exec.submit(this::physicalMemory));
	        futureCompute.add(exec.submit(this::videoController));
	        futureCompute.add(exec.submit(this::mainBoard));
	        futureCompute.add(exec.submit(this::bios));
	        futureCompute.add(exec.submit(this::mainboardPorts));
	        futureCompute.add(exec.submit(this::networkAdapters));
	        futureCompute.add(exec.submit(this::diskDetails));
	        futureCompute.add(exec.submit(this::osDetails));
	        
	        for(Future<String> task:futureCompute) {
	        	sb.append(task.get());
	        }
	        
	        SwingUtilities.invokeLater(()-> {
	        	reportDisplay.setText(sb.toString());
	        	progress.setIndeterminate(false);
	        	progress.setValue(100);
	        	detailedReportButton.setEnabled(true);
	        });
	        
		} catch (ExecutionException e) {
			Logger.error(e);
			SwingUtilities.invokeLater(()-> progressDisplay.setText(e.getMessage()));
		} catch (InterruptedException e) {
			Logger.error(e);
			SwingUtilities.invokeLater(()-> {
				detailedReportButton.setEnabled(true);
				progressDisplay.setText(e.getMessage());
			});
			Thread.currentThread().interrupt();
		}
	}
	
	private String hwid() throws ExecutionException, InterruptedException {
		StringBuilder hwid = new StringBuilder();
		hwid.append("--------------------HARDWARE ID--------------------\n"+HardwareID.getHardwareID()+"\n");
		return hwid.toString();
	}
	
	private String cpu() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder cpuDetails = new StringBuilder();
		cpuDetails.append("--------------------CPU--------------------\n");
		List<String> cpuList = Win32_Processor.getProcessorList();
		for(String cpu:cpuList) {
			Map<String, String> cpuProperties = Win32_Processor.getCurrentProcessor(cpu);
			for(Map.Entry<String, String> entries: cpuProperties.entrySet()) {
				cpuDetails.append(entries.getKey()+": "+entries.getValue()+"\n");
			}
			cpuDetails.append("\n");
		}
		return cpuDetails.toString();
	}
	
	private String cpuCache() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		StringBuilder cpuCacheDetails = new StringBuilder();
		cpuCacheDetails.append("--------------------CPU CACHE--------------------\n");
		List<String> cpuID = Win32_Processor.getProcessorList();
		for (String id : cpuID) {
			List<String> cacheID = Win32_AssociatedProcessorMemory.getCacheID(id);
			for (String currentCacheID : cacheID) {
				Map<String, String> cache = Win32_CacheMemory.getCPUCache(currentCacheID);
				for (Map.Entry<String, String> currentCache : cache.entrySet()) {
					cpuCacheDetails.append(currentCache.getKey() + ": " + currentCache.getValue()+"\n");
				}
				cpuCacheDetails.append("\n");
			}
		}
		return cpuCacheDetails.toString();
	}
	
	private String physicalMemory() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		StringBuilder memoryDetails = new StringBuilder();
		memoryDetails.append("--------------------PHYSICAL MEMORY--------------------\n");
		List<String> memoryID = Win32_PhysicalMemory.getTag();
		
		for (String id : memoryID) {
			Map<String, String> memory = Win32_PhysicalMemory.getMemory(id);
			for (Map.Entry<String, String> entry : memory.entrySet()) {
				memoryDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
			}
			memoryDetails.append("\n");
		}
		return memoryDetails.toString();
	}
	
	private String videoController() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		StringBuilder videoControllerDetails = new StringBuilder();
		videoControllerDetails.append("--------------------VIDEO CONTROLLER--------------------\n");
		List<String> gpuIDs = Win32_VideoController.getGPUID();
	
		for (String currentID : gpuIDs) {
			Map<String, String> currentGPU = Win32_VideoController.getGPU(currentID);
			for (Map.Entry<String, String> entry : currentGPU.entrySet()) {
				 videoControllerDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
			}
			 videoControllerDetails.append("\n");
		}
		return videoControllerDetails.toString();
	}
	
	private String mainBoard() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		StringBuilder mainboardDetails = new StringBuilder();
		mainboardDetails.append("--------------------MAINBOARD--------------------\n");
		Map<String, String> motherboard = Win32_Baseboard.getMotherboard();
		for (Map.Entry<String, String> entry : motherboard.entrySet()) {
			mainboardDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
		}
		
		return mainboardDetails.toString();
	}
	
	private String bios() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder biosDetails = new StringBuilder();
		biosDetails.append("--------------------BIOS DETAILS--------------------\n");
		Map<String, String> BIOS = Win32_BIOS.getPrimaryBIOS();
		for (Map.Entry<String, String> entry : BIOS.entrySet()) {
			biosDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
		}
		
		return biosDetails.toString();
	}
	
	private String mainboardPorts() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		StringBuilder mainboardPortDetails = new StringBuilder();
		mainboardPortDetails.append("--------------------MAINBOARD--------------------\n");
		List<String> portID = Win32_PortConnector.getBaseboardPortID();
		for (String id : portID) {
			Map<String, String> ports = Win32_PortConnector.getBaseboardPorts(id);
			for (Map.Entry<String, String> port : ports.entrySet()) {
				mainboardPortDetails.append(port.getKey() + ": " + port.getValue()+"\n");
			}
			mainboardPortDetails.append("\n");
		}
		return mainboardPortDetails.toString();
	}
	
	private String networkAdapters() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder networkDetails = new StringBuilder();
		networkDetails.append("--------------------NETWORK ADAPTERS--------------------\n");
		List<String> deviceIDs = Win32_NetworkAdapter.getDeviceIDList();
		
		for (String currentID : deviceIDs) {
			
			Map<String, String> networkAdapter = Win32_NetworkAdapter.getNetworkAdapters(currentID);
			String index = Win32_NetworkAdapterSetting.getIndex(currentID);
			Map<String, String> networkAdapterConfiguration = Win32_NetworkAdapterConfiguration.getAdapterConfiguration(index);
			
			for (Map.Entry<String, String> entry : networkAdapter.entrySet()) {
				networkDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
			}
			networkDetails.append("\n");
			for (Map.Entry<String, String> entry : networkAdapterConfiguration.entrySet()) {
				networkDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
			}
			networkDetails.append("\n");
		}
		return networkDetails.toString();
	}
	
	private String diskDetails() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		StringBuilder diskDetails = new StringBuilder();
		diskDetails.append("--------------------STORAGE--------------------\n");
		List<String> diskID = Win32_DiskDrive.getDriveID();

		for (String id : diskID) {
			Map<String, String> disk = Win32_DiskDrive.getDrive(id);
			
			List<String> diskPartition = Win32_DiskDriveToDiskPartition.getPartitionList(id);
			for (Map.Entry<String, String> entry : disk.entrySet()) {
				diskDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
			}

			for (String currentPartition : diskPartition) {
				diskDetails.append("Partition: " + currentPartition + ", Drive Letter: "+ Win32_LogicalDiskToPartition.getDriveLetter(currentPartition)+"\n");
			}
			diskDetails.append("\n");
		}
		return diskDetails.toString();
	}
	
	private String osDetails() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder osDetails = new StringBuilder();
		osDetails.append("--------------------OPERATING SYSTEM--------------------\n");
		List<String> oslist = Win32_OperatingSystem.getOSList();
		for (String currentOS : oslist) {
			Map<String, String> osinfo = Win32_OperatingSystem.getOSInfo(currentOS);

			for (Map.Entry<String, String> entry : osinfo.entrySet()) {
				osDetails.append(entry.getKey() + ": " + entry.getValue()+"\n");
			}
			osDetails.append("\n");
		}
		return osDetails.toString();
	}
}