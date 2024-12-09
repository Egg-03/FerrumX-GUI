package com.ferrumx.swingworkers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_AssociatedProcessorMemory;
import com.ferrumx.system.hardware.Win32_CacheMemory;

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
