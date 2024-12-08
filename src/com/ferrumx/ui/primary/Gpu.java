package com.ferrumx.ui.primary;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_VideoController;
import com.ferrumx.ui.secondary.ExceptionUI;
import com.ferrumx.ui.utilities.IconImageChooser;

final class Gpu {
	private Gpu() {
		throw new IllegalStateException("Utility Class");
	}

	protected static boolean initializeGpu(JLabel gpuIcon, JComboBox<String> gpuChoice, JTextField... gpuFields) {
		try {
			List<String> gpuList = Win32_VideoController.getGPUID();

			if (gpuList.isEmpty()) {
				new ExceptionUI("GPU Initialization Error", "FATAL ERROR: No GPU devices found").setVisible(true);
				return false;
			}

			for (String gpu : gpuList) {
				gpuChoice.addItem(gpu);
			}

			Map<String, String> gpuProperties = Win32_VideoController.getGPU(gpuChoice.getItemAt(gpuChoice.getSelectedIndex()));
			String gpuName = gpuProperties.get("Name");
			gpuFields[0].setText(gpuName);
			IconImageChooser.gpuImageChooser(gpuIcon, gpuName);
			
			gpuFields[1].setText(gpuProperties.get("PNPDeviceID"));
			gpuFields[2].setText(gpuProperties.get("CurrentHorizontalResolution"));
			gpuFields[3].setText(gpuProperties.get("CurrentVerticalResolution"));
			gpuFields[4].setText(gpuProperties.get("CurrentBitsPerPixel"));
			gpuFields[5].setText(gpuProperties.get("MinRefreshRate") + " Hz");
			gpuFields[6].setText(gpuProperties.get("MaxRefreshRate") + " Hz");
			gpuFields[7].setText(gpuProperties.get("CurrentRefreshRate") + " Hz");
			gpuFields[8].setText(gpuProperties.get("AdapterDACType"));

			gpuFields[10].setText(gpuProperties.get("DriverVersion"));
			gpuFields[11].setText(gpuProperties.get("DriverDate"));
			gpuFields[12].setText(gpuProperties.get("VideoProcessor"));

			Long adapterRAM = Long.valueOf(gpuProperties.get("AdapterRAM")) / (1024 * 1024);
			gpuFields[9].setText(String.valueOf(adapterRAM) + " MB");

		} catch (IndexOutOfBoundsException | IOException | ShellException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Video Controller Initialization Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			return false;
		} catch (NumberFormatException e1) {
			gpuFields[9].setText("N/A"); // sets VRAM field to N/A in case the adapterRAM property cannot be parsed into
											// a Long value
			return true;
		} catch (InterruptedException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Video Controller Initialization Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			Thread.currentThread().interrupt();
			return false;
		}
		addGpuChoiceActionListener(gpuIcon, gpuChoice, gpuFields);
		return true;
	}

	private static void addGpuChoiceActionListener(JLabel gpuIcon, JComboBox<String> gpuChoice, JTextField... gpuFields) {
		gpuChoice.addActionListener(e ->
			new Thread(()->{
				Map<String, String> gpuProperties;
				try {
					gpuProperties = Win32_VideoController.getGPU(gpuChoice.getItemAt(gpuChoice.getSelectedIndex()));
					
					String gpuName = gpuProperties.get("Name");
					gpuFields[0].setText(gpuName);
					IconImageChooser.gpuImageChooser(gpuIcon, gpuName);
					
					gpuFields[1].setText(gpuProperties.get("PNPDeviceID"));
					gpuFields[2].setText(gpuProperties.get("CurrentHorizontalResolution"));
					gpuFields[3].setText(gpuProperties.get("CurrentVerticalResolution"));
					gpuFields[4].setText(gpuProperties.get("CurrentBitsPerPixel"));
					gpuFields[5].setText(gpuProperties.get("MinRefreshRate") + " Hz");
					gpuFields[6].setText(gpuProperties.get("MaxRefreshRate") + " Hz");
					gpuFields[7].setText(gpuProperties.get("CurrentRefreshRate") + " Hz");
					gpuFields[8].setText(gpuProperties.get("AdapterDACType"));

					gpuFields[10].setText(gpuProperties.get("DriverVersion"));
					gpuFields[11].setText(gpuProperties.get("DriverDate"));
					gpuFields[12].setText(gpuProperties.get("VideoProcessor"));

					Long adapterRAM = Long.valueOf(gpuProperties.get("AdapterRAM")) / (1024 * 1024);
					gpuFields[9].setText(String.valueOf(adapterRAM) + " MB");

				} catch (IndexOutOfBoundsException | IOException | ShellException e1) {
					String errorMessage = e1.getMessage();
					String stackTrace = Arrays.toString(e1.getStackTrace());
					new ExceptionUI("Video Controller Initialization Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
				} catch (NumberFormatException e1) {
					gpuFields[9].setText("N/A"); // sets VRAM field to N/A in case the adapterRAM property cannot be parsed
													// into a Long value
				} catch (InterruptedException e1) {
					String errorMessage = e1.getMessage();
					String stackTrace = Arrays.toString(e1.getStackTrace());
					new ExceptionUI("Video Controller Initialization Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
					Thread.currentThread().interrupt();
				}
			}).start()
		);
	}
}
