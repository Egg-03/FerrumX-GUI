package com.ferrumx.swingworkers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_NetworkAdapter;
import com.ferrumx.system.networking.Win32_NetworkAdapterConfiguration;
import com.ferrumx.system.networking.Win32_NetworkAdapterSetting;

public class Network extends SwingWorker<List<Map<String, String>>, List<String>> {
	
	private JComboBox<String> networkChoice;
	private List<JTextField> networkFields;
	
	public Network(JComboBox<String> networkChoice, List<JTextField> networkFields) {
		this.networkChoice = networkChoice;
		this.networkFields = networkFields;
	}

	@Override
	protected List<Map<String, String>> doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		List<String> networkAdapters = Win32_NetworkAdapter.getDeviceIDList();
		publish(networkAdapters);
		
		Map<String, String> networkAdapterProperties = Win32_NetworkAdapter.getNetworkAdapters(networkAdapters.getFirst());
		
		String index = Win32_NetworkAdapterSetting.getIndex(networkAdapters.getFirst());
		Map<String, String> networkAdapterConfiguration = Win32_NetworkAdapterConfiguration.getAdapterConfiguration(index);
		
		return List.of(networkAdapterProperties, networkAdapterConfiguration);
	}

	@Override
	protected void process(List<List<String>> chunks) {
		for(List<String> networkAdapters:chunks) {
			for(String currentAdapter:networkAdapters) {
				networkChoice.addItem(currentAdapter);
			}
		}
	}
	
	@Override
	protected void done() {
		try {
			List<Map<String, String>> networkPropertyMaps = get();
			Map<String, String> networkAdapterProperties = networkPropertyMaps.getFirst();
			Map<String, String> networkAdapterConfiguration = networkPropertyMaps.getLast();
			
			networkFields.get(0).setText(networkAdapterProperties.get("Name"));
			networkFields.get(1).setText(networkAdapterProperties.get("PNPDeviceID"));
			networkFields.get(2).setText(networkAdapterProperties.get("MACAddress"));
			networkFields.get(3).setText(networkAdapterProperties.get("NetConnectionID"));
			
			networkFields.get(4).setText(networkAdapterConfiguration.get("IPEnabled"));
			networkFields.get(5).setText(networkAdapterConfiguration.get("IPAddress"));
			networkFields.get(6).setText(networkAdapterConfiguration.get("IPSubnet"));
			networkFields.get(7).setText(networkAdapterConfiguration.get("DefaultIPGateway"));
			networkFields.get(8).setText(networkAdapterConfiguration.get("DHCPEnabled"));
			networkFields.get(9).setText(networkAdapterConfiguration.get("DHCPServer"));
			networkFields.get(10).setText(networkAdapterConfiguration.get("DNSHostName"));
			networkFields.get(11).setText(networkAdapterConfiguration.get("DNSServerSearchOrder"));
			
			networkChoice.addActionListener(e-> new NetworkAdapterListener(networkChoice, networkFields).execute());
			
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
}

class NetworkAdapterListener extends SwingWorker<List<Map<String, String>>, Void>{
	
	private JComboBox<String> networkChoice;
	private List<JTextField> networkFields;
	
	public NetworkAdapterListener(JComboBox<String> networkChoice, List<JTextField> networkFields) {
		this.networkChoice = networkChoice;
		this.networkFields = networkFields;
	}

	@Override
	protected List<Map<String, String>> doInBackground() throws Exception {
		String networkAdapter = networkChoice.getItemAt(networkChoice.getSelectedIndex());
		
		Map<String, String> networkAdapterProperties = Win32_NetworkAdapter.getNetworkAdapters(networkAdapter);
		
		String index = Win32_NetworkAdapterSetting.getIndex(networkAdapter);
		Map<String, String> networkAdapterConfiguration = Win32_NetworkAdapterConfiguration.getAdapterConfiguration(index);
		
		return List.of(networkAdapterProperties, networkAdapterConfiguration);
	}
	
	@Override
	protected void done() {
		try {
			List<Map<String, String>> networkPropertyMaps = get();
			Map<String, String> networkAdapterProperties = networkPropertyMaps.getFirst();
			Map<String, String> networkAdapterConfiguration = networkPropertyMaps.getLast();
			
			networkFields.get(0).setText(networkAdapterProperties.get("Name"));
			networkFields.get(1).setText(networkAdapterProperties.get("PNPDeviceID"));
			networkFields.get(2).setText(networkAdapterProperties.get("MACAddress"));
			networkFields.get(3).setText(networkAdapterProperties.get("NetConnectionID"));
			
			networkFields.get(4).setText(networkAdapterConfiguration.get("IPEnabled"));
			networkFields.get(5).setText(networkAdapterConfiguration.get("IPAddress"));
			networkFields.get(6).setText(networkAdapterConfiguration.get("IPSubnet"));
			networkFields.get(7).setText(networkAdapterConfiguration.get("DefaultIPGateway"));
			networkFields.get(8).setText(networkAdapterConfiguration.get("DHCPEnabled"));
			networkFields.get(9).setText(networkAdapterConfiguration.get("DHCPServer"));
			networkFields.get(10).setText(networkAdapterConfiguration.get("DNSHostName"));
			networkFields.get(11).setText(networkAdapterConfiguration.get("DNSServerSearchOrder"));
			
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
}
