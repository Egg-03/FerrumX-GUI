package com.ferrumx.swingworkers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.tinylog.Logger;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_VideoController;
import com.ferrumx.ui.secondary.ExceptionUI;
import com.ferrumx.ui.utilities.IconImageChooser;

public class Gpu extends SwingWorker<Map<String, String>, List<String>> {
	
	private JLabel gpuIcon;
	private JComboBox<String> gpuChoice;
	private List<JTextField> gpuFields;
	
	public Gpu(JLabel gpuIcon, JComboBox<String> gpuChoice, List<JTextField> gpuFields) {
		this.gpuIcon = gpuIcon;
		this.gpuChoice = gpuChoice;
		this.gpuFields = gpuFields;
	}

	@Override
	protected Map<String, String> doInBackground() throws Exception {
		List<String> gpuList = Win32_VideoController.getGPUID();
		publish(gpuList);
		
		return Win32_VideoController.getGPU(gpuList.getFirst());
	}
	
	@Override
	protected void process(List<List<String>> chunks) {
		for(List<String> gpus:chunks) {
			for(String gpu:gpus) {
				gpuChoice.addItem(gpu);
			}
		}
	}
	
	@Override
	protected void done() {
		try {
			Map<String, String> gpuProperties = get();
			
			String gpuName = gpuProperties.get("Name");
			gpuFields.get(0).setText(gpuName);
			IconImageChooser.gpuImageChooser(gpuIcon, gpuName);
			
			gpuFields.get(1).setText(gpuProperties.get("PNPDeviceID"));
			gpuFields.get(2).setText(gpuProperties.get("CurrentHorizontalResolution"));
			gpuFields.get(3).setText(gpuProperties.get("CurrentVerticalResolution"));
			gpuFields.get(4).setText(gpuProperties.get("CurrentBitsPerPixel"));
			gpuFields.get(5).setText(gpuProperties.get("MinRefreshRate") + " Hz");
			gpuFields.get(6).setText(gpuProperties.get("MaxRefreshRate") + " Hz");
			gpuFields.get(7).setText(gpuProperties.get("CurrentRefreshRate") + " Hz");
			gpuFields.get(8).setText(gpuProperties.get("AdapterDACType"));

			gpuFields.get(10).setText(gpuProperties.get("DriverVersion"));
			gpuFields.get(11).setText(gpuProperties.get("DriverDate"));
			gpuFields.get(12).setText(gpuProperties.get("VideoProcessor"));

			Long adapterRAM = Long.valueOf(gpuProperties.get("AdapterRAM")) / (1024 * 1024);
			gpuFields.get(9).setText(String.valueOf(adapterRAM) + " MB");
			
			gpuChoice.addActionListener(e-> new GpuActionListener(gpuIcon, gpuChoice, gpuFields).execute());
		} catch (ExecutionException e) {
			new ExceptionUI("GPU Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
		} catch (NumberFormatException e1) {
			gpuFields.get(9).setText("N/A"); // sets VRAM field to N/A in case the adapterRAM property cannot be parsed into a Long value
		}  catch (InterruptedException e) {
			new ExceptionUI("GPU Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
			Thread.currentThread().interrupt();
		}
	}

}

class GpuActionListener extends SwingWorker<Map<String, String>, Void> {
	
	private JLabel gpuIcon;
	private JComboBox<String> gpuChoice;
	private List<JTextField> gpuFields;
	
	public GpuActionListener(JLabel gpuIcon, JComboBox<String> gpuChoice, List<JTextField> gpuFields) {
		this.gpuIcon = gpuIcon;
		this.gpuChoice = gpuChoice;
		this.gpuFields = gpuFields;
	}

	@Override
	protected Map<String, String> doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		return Win32_VideoController.getGPU(gpuChoice.getItemAt(gpuChoice.getSelectedIndex()));
	}
	
	@Override
	protected void done() {
		try {
			Map<String, String> gpuProperties = get();
			
			String gpuName = gpuProperties.get("Name");
			gpuFields.get(0).setText(gpuName);
			IconImageChooser.gpuImageChooser(gpuIcon, gpuName);
			
			gpuFields.get(1).setText(gpuProperties.get("PNPDeviceID"));
			gpuFields.get(2).setText(gpuProperties.get("CurrentHorizontalResolution"));
			gpuFields.get(3).setText(gpuProperties.get("CurrentVerticalResolution"));
			gpuFields.get(4).setText(gpuProperties.get("CurrentBitsPerPixel"));
			gpuFields.get(5).setText(gpuProperties.get("MinRefreshRate") + " Hz");
			gpuFields.get(6).setText(gpuProperties.get("MaxRefreshRate") + " Hz");
			gpuFields.get(7).setText(gpuProperties.get("CurrentRefreshRate") + " Hz");
			gpuFields.get(8).setText(gpuProperties.get("AdapterDACType"));

			gpuFields.get(10).setText(gpuProperties.get("DriverVersion"));
			gpuFields.get(11).setText(gpuProperties.get("DriverDate"));
			gpuFields.get(12).setText(gpuProperties.get("VideoProcessor"));

			Long adapterRAM = Long.valueOf(gpuProperties.get("AdapterRAM")) / (1024 * 1024);
			gpuFields.get(9).setText(String.valueOf(adapterRAM) + " MB");
		} catch (ExecutionException e) {
			new ExceptionUI("GPU Action Listener Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
			e.printStackTrace();
		} catch (NumberFormatException e1) {
			gpuFields.get(9).setText("N/A"); // sets VRAM field to N/A in case the adapterRAM property cannot be parsed into a Long value
		}  catch (InterruptedException e) {
			new ExceptionUI("GPU Action Listener Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
			Thread.currentThread().interrupt();
		}
	}
}
