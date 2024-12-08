package com.ferrumx.ui.primary;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_DiskDrive;
import com.ferrumx.system.operating_system.Win32_DiskDriveToDiskPartition;
import com.ferrumx.system.operating_system.Win32_LogicalDiskToPartition;
import com.ferrumx.ui.secondary.ExceptionUI;

final class Storage {

	private Storage() {
		throw new IllegalStateException("Utility Class");
	}

	protected static boolean initializeStorage(JComboBox<String> diskChoice, JTextArea partitionDetails,
			JTextField... storageFields) {
		try {
			List<String> diskId = Win32_DiskDrive.getDriveID();
			if (diskId.isEmpty()) {
				new ExceptionUI("Storage Initialization Error", "FATAL ERROR: No valid storage devices found")
						.setVisible(true);
				return false;
			}
			for (String id : diskId) {
				diskChoice.addItem(id);
			}

			String currentDiskId = diskChoice.getItemAt(diskChoice.getSelectedIndex());
			// get disk properties for the current id
			Map<String, String> diskProperties = Win32_DiskDrive.getDrive(currentDiskId);
			storageFields[0].setText(diskProperties.get("Caption"));
			storageFields[1].setText(diskProperties.get("Model"));

			storageFields[3].setText(diskProperties.get("FirmwareRevision"));
			storageFields[4].setText(diskProperties.get("SerialNumber"));
			storageFields[5].setText(diskProperties.get("Partitions"));
			storageFields[6].setText(diskProperties.get("Status"));
			storageFields[7].setText(diskProperties.get("InterfaceType"));

			Long storageCap = Long.valueOf(diskProperties.get("Size")) / (1024 * 1024 * 1024);
			storageFields[2].setText((String.valueOf(storageCap) + " GB"));
			// get disk partitions for the current id
			List<String> diskPartitions = Win32_DiskDriveToDiskPartition.getPartitionList(currentDiskId);
			for (String currentPartition : diskPartitions) {
				partitionDetails.append("Partition: " + currentPartition + ", Drive Letter: "
						+ Win32_LogicalDiskToPartition.getDriveLetter(currentPartition) + "\n");
			}
		} catch (IndexOutOfBoundsException | IOException | ShellException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Storage Initialization Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			return false;
		} catch (NumberFormatException e) {
			storageFields[2].setText("N/A");
		} catch (InterruptedException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Storage Initialization Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
			Thread.currentThread().interrupt();
			return false;
		}
		addStorageChoiceActionListener(diskChoice, partitionDetails, storageFields);
		return true;
	}

	private static void addStorageChoiceActionListener(JComboBox<String> diskChoice, JTextArea partitionDetails,
			JTextField... storageFields) {
		diskChoice.addActionListener(e -> 
			new Thread(()->{
				try {
					String currentDiskId = diskChoice.getItemAt(diskChoice.getSelectedIndex());
					// get disk properties for the current id
					Map<String, String> diskProperties = Win32_DiskDrive.getDrive(currentDiskId);
					storageFields[0].setText(diskProperties.get("Caption"));
					storageFields[1].setText(diskProperties.get("Model"));

					storageFields[3].setText(diskProperties.get("FirmwareRevision"));
					storageFields[4].setText(diskProperties.get("SerialNumber"));
					storageFields[5].setText(diskProperties.get("Partitions"));
					storageFields[6].setText(diskProperties.get("Status"));
					storageFields[7].setText(diskProperties.get("InterfaceType"));

					Long storageCap = Long.valueOf(diskProperties.get("Size")) / (1024 * 1024 * 1024);
					storageFields[2].setText((String.valueOf(storageCap) + " GB"));

					// get disk partitions for the current id
					List<String> diskPartitions = Win32_DiskDriveToDiskPartition.getPartitionList(currentDiskId);
					// remove the contents in the text area before updating with new data
					partitionDetails.selectAll();
					partitionDetails.replaceSelection("");
					for (String currentPartition : diskPartitions) {
						partitionDetails.append("Partition: " + currentPartition + ", Drive Letter: "
								+ Win32_LogicalDiskToPartition.getDriveLetter(currentPartition) + "\n");
					}
				} catch (IndexOutOfBoundsException | IOException | ShellException e1) {
					String errorMessage = e1.getMessage();
					String stackTrace = Arrays.toString(e1.getStackTrace());
					new ExceptionUI("Storage Initialization Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
				} catch (NumberFormatException e1) {
					storageFields[2].setText("N/A");
				} catch (InterruptedException e1) {
					String errorMessage = e1.getMessage();
					String stackTrace = Arrays.toString(e1.getStackTrace());
					new ExceptionUI("Storage Initialization Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
					Thread.currentThread().interrupt();
				}
			}).start()
		);
	}
}
