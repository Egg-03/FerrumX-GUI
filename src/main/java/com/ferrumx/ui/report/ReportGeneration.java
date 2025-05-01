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
import javax.swing.JEditorPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import org.tinylog.Logger;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.associatedclasses.Win32_AssociatedProcessorMemory;
import com.ferrumx.system.associatedclasses.Win32_DiskDriveToDiskPartition;
import com.ferrumx.system.associatedclasses.Win32_LogicalDiskToPartition;
import com.ferrumx.system.associatedclasses.Win32_NetworkAdapterSetting;
import com.ferrumx.system.hardware.HardwareID;
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
import com.ferrumx.system.operating_system.Win32_OperatingSystem;
import com.ferrumx.ui.secondary.ExceptionUI;
import com.ferrumx.ui.utilities.MarkdownToHtml;

public class ReportGeneration  {
	
	private JEditorPane reportDisplay;
	private JButton reportButton;
	private JProgressBar progressBar;
	private static final String NEWLINE = System.lineSeparator();
	private static final String MARKDOWN_LINE = NEWLINE+NEWLINE+"---"+NEWLINE+NEWLINE;
	private static final String MARKDOWN_TABLE_HEADING = NEWLINE+"Properties|Values"+NEWLINE+":---|:---"+NEWLINE;
	
	public ReportGeneration (JEditorPane reportDisplay, JButton detailedReportButton, JProgressBar progressBar) {
		this.reportDisplay = reportDisplay;
		this.reportButton = detailedReportButton;
		this.progressBar = progressBar;
		
		clearComponentStatuses();
		new Thread(this::compute).start();
	}
	
	private void clearComponentStatuses() {
		SwingUtilities.invokeLater(()->{
			reportDisplay.setText(null);
			progressBar.setIndeterminate(true);
			reportButton.setEnabled(false);
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
	        	reportDisplay.setText(MarkdownToHtml.parse(sb.toString()));
	        	progressBar.setIndeterminate(false);
	        	progressBar.setValue(100);
	        	reportButton.setEnabled(true);
	        	Logger.info(MarkdownToHtml.parse(sb.toString()));
	        });
	        
		} catch (ExecutionException e) {
			Logger.error(e);
			new ExceptionUI("Report Error", "The Report Generation Module has encountered one or more errors. Please check the logs for more details.\n"+e.getMessage());
		} catch (InterruptedException e) {
			Logger.error(e);
			new ExceptionUI("Report Error", "The Report Generation Module has encountered an interruption error. Please check the logs for more details.\n"+e.getMessage());
			SwingUtilities.invokeLater(()-> reportButton.setEnabled(true));
			Thread.currentThread().interrupt();
		}
	}
	
	private String hwid() throws ExecutionException, InterruptedException {
		StringBuilder hwid = new StringBuilder();
		hwid.append("## HARDWARE ID"+NEWLINE+"*"+HardwareID.getHardwareID()+"*");
		return hwid.toString();
	}
	
	private String cpu() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder cpuDetails = new StringBuilder();
		cpuDetails.append(MARKDOWN_LINE+"## CPU"+MARKDOWN_TABLE_HEADING);
		List<String> cpuList = Win32_Processor.getProcessorList();
		for(String cpu:cpuList) {
			Map<String, String> cpuProperties = Win32_Processor.getCurrentProcessor(cpu);
			for(Map.Entry<String, String> entries: cpuProperties.entrySet()) {
				cpuDetails.append(" | *"+entries.getKey()+"* | "+entries.getValue()+" | "+NEWLINE);
			}
			if(!cpuList.getLast().equals(cpu))
				cpuDetails.append(MARKDOWN_TABLE_HEADING);
		}
		return cpuDetails.toString();
	}
	
	private String cpuCache() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		StringBuilder cpuCacheDetails = new StringBuilder();
		cpuCacheDetails.append(MARKDOWN_LINE+"## CPU CACHE"+MARKDOWN_TABLE_HEADING);
		List<String> cpuID = Win32_Processor.getProcessorList();
		for (String id : cpuID) {
			List<String> cacheID = Win32_AssociatedProcessorMemory.getCacheID(id);
			for (String currentCacheID : cacheID) {
				Map<String, String> cache = Win32_CacheMemory.getCPUCache(currentCacheID);
				for (Map.Entry<String, String> currentCache : cache.entrySet()) {
					cpuCacheDetails.append(" | *"+currentCache.getKey() + "* | " + currentCache.getValue()+" | "+NEWLINE);
				}
				if(!cacheID.getLast().equals(currentCacheID))
					cpuCacheDetails.append(MARKDOWN_TABLE_HEADING);
			}
		}
		return cpuCacheDetails.toString();
	}
	
	private String physicalMemory() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		StringBuilder memoryDetails = new StringBuilder();
		memoryDetails.append(MARKDOWN_LINE+"## PHYSICAL MEMORY"+MARKDOWN_TABLE_HEADING);
		List<String> memoryID = Win32_PhysicalMemory.getTag();
		
		for (String id : memoryID) {
			Map<String, String> memory = Win32_PhysicalMemory.getMemory(id);
			for (Map.Entry<String, String> entry : memory.entrySet()) {
				memoryDetails.append(" | *"+entry.getKey()+"* | "+entry.getValue()+" | "+NEWLINE);
			}
			
			if(!memoryID.getLast().equals(id))
				memoryDetails.append(MARKDOWN_TABLE_HEADING);
		}
		return memoryDetails.toString();
	}
	
	private String videoController() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		StringBuilder videoControllerDetails = new StringBuilder();
		videoControllerDetails.append(MARKDOWN_LINE+"## VIDEO CONTROLLER"+MARKDOWN_TABLE_HEADING);
		List<String> gpuIDs = Win32_VideoController.getGPUID();
	
		for (String currentID : gpuIDs) {
			Map<String, String> currentGPU = Win32_VideoController.getGPU(currentID);
			for (Map.Entry<String, String> entry : currentGPU.entrySet()) {
				 videoControllerDetails.append(" | *"+entry.getKey()+"* | "+entry.getValue()+" | "+NEWLINE);
			}
			if(!gpuIDs.getLast().equals(currentID))
				videoControllerDetails.append(MARKDOWN_TABLE_HEADING);
		}
		return videoControllerDetails.toString();
	}
	
	private String mainBoard() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		StringBuilder mainboardDetails = new StringBuilder();
		mainboardDetails.append(MARKDOWN_LINE+"## MAINBOARD"+MARKDOWN_TABLE_HEADING);
		Map<String, String> motherboard = Win32_Baseboard.getMotherboard();
		for (Map.Entry<String, String> entry : motherboard.entrySet()) {
			mainboardDetails.append(" | *"+entry.getKey()+"* | "+entry.getValue()+" | "+NEWLINE);
		}
		
		return mainboardDetails.toString();
	}
	
	private String bios() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder biosDetails = new StringBuilder();
		biosDetails.append(MARKDOWN_LINE+"## BIOS"+MARKDOWN_TABLE_HEADING);
		Map<String, String> BIOS = Win32_BIOS.getPrimaryBIOS();
		for (Map.Entry<String, String> entry : BIOS.entrySet()) {
			biosDetails.append(" | *"+entry.getKey()+"* | "+entry.getValue()+" | "+NEWLINE);
		}
		
		return biosDetails.toString();
	}
	
	private String mainboardPorts() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		StringBuilder mainboardPortDetails = new StringBuilder();
		mainboardPortDetails.append(MARKDOWN_LINE+"## IO PORTS"+MARKDOWN_TABLE_HEADING);
		List<String> portID = Win32_PortConnector.getBaseboardPortID();
		for (String id : portID) {
			Map<String, String> ports = Win32_PortConnector.getBaseboardPorts(id);
			for (Map.Entry<String, String> port : ports.entrySet()) {
				mainboardPortDetails.append(" | *"+port.getKey()+"* | "+port.getValue()+" | "+NEWLINE);
			}
		}
		return mainboardPortDetails.toString();
	}
	
	private String networkAdapters() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder networkDetails = new StringBuilder();
		networkDetails.append(MARKDOWN_LINE+"## NETWORK ADAPTERS"+MARKDOWN_TABLE_HEADING);
		List<String> deviceIDs = Win32_NetworkAdapter.getDeviceIDList();
		
		for (String currentID : deviceIDs) {
			
			Map<String, String> networkAdapter = Win32_NetworkAdapter.getNetworkAdapters(currentID);
			String index = Win32_NetworkAdapterSetting.getIndex(currentID);
			Map<String, String> networkAdapterConfiguration = Win32_NetworkAdapterConfiguration.getAdapterConfiguration(index);
			
			for (Map.Entry<String, String> entry : networkAdapter.entrySet()) {
				networkDetails.append(" | *"+entry.getKey()+"* | "+entry.getValue()+" | "+NEWLINE);
			}
			
			for (Map.Entry<String, String> entry : networkAdapterConfiguration.entrySet()) {
				networkDetails.append(" | *"+entry.getKey()+"* | "+entry.getValue()+" | "+NEWLINE);
			}
			if(!deviceIDs.getLast().equals(currentID))
				networkDetails.append(MARKDOWN_TABLE_HEADING);
		}
		return networkDetails.toString();
	}
	
	private String diskDetails() throws IOException, IndexOutOfBoundsException, ShellException, InterruptedException {
		StringBuilder diskDetails = new StringBuilder();
		diskDetails.append(MARKDOWN_LINE+"## STORAGE"+MARKDOWN_TABLE_HEADING);
		List<String> diskID = Win32_DiskDrive.getDriveID();

		for (String id : diskID) {
			Map<String, String> disk = Win32_DiskDrive.getDrive(id);
			
			List<String> diskPartition = Win32_DiskDriveToDiskPartition.getPartitionList(id);
			for (Map.Entry<String, String> entry : disk.entrySet()) {
				diskDetails.append(" | *"+entry.getKey()+"* | "+entry.getValue()+" |\n");
			}
			
			diskDetails.append(NEWLINE+"Partition|Drive Letter"+NEWLINE+":---|:---"+NEWLINE);
			for (String currentPartition : diskPartition) {
				diskDetails.append(" | " + currentPartition + " | "+ Win32_LogicalDiskToPartition.getDriveLetter(currentPartition)+" |\n");
			}
			if(!diskID.getLast().equals(id))
				diskDetails.append(MARKDOWN_TABLE_HEADING);
		}
		return diskDetails.toString();
	}
	
	private String osDetails() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		StringBuilder osDetails = new StringBuilder();
		osDetails.append(MARKDOWN_LINE+"## OPERATING SYSTEM"+MARKDOWN_TABLE_HEADING);
		List<String> oslist = Win32_OperatingSystem.getOSList();
		for (String currentOS : oslist) {
			Map<String, String> osinfo = Win32_OperatingSystem.getOSInfo(currentOS);

			for (Map.Entry<String, String> entry : osinfo.entrySet()) {
				osDetails.append(" | *"+entry.getKey()+"* | "+entry.getValue()+" | "+NEWLINE);
			}
			if(!oslist.getLast().equals(currentOS))
				osDetails.append(MARKDOWN_TABLE_HEADING);
		}
		return osDetails.toString();
	}
}