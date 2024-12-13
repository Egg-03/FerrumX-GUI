package com.ferrumx.swingworkers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_DiskDrive;
import com.ferrumx.system.operating_system.Win32_DiskDriveToDiskPartition;

public class Storage extends SwingWorker<Map<String, String>, List<String>> {
	
	private JComboBox<String> storageChoice;
	private JTextArea partitionArea;
	private List<JTextField> storageFields;
	
	public Storage(JComboBox<String> storageChoice, JTextArea partitionArea, List<JTextField> storageFields) {
		this.storageChoice = storageChoice;
		this.storageFields = storageFields;
		this.partitionArea = partitionArea;
	}

	@Override
	protected Map<String, String> doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		List<String> storageDisks = Win32_DiskDrive.getDriveID();
		publish(storageDisks);
		
		new StoragePartitions(storageDisks.getFirst(), partitionArea).execute();
		return Win32_DiskDrive.getDrive(storageDisks.getFirst());
	}
	
	@Override
	protected void process(List<List<String>> chunks) {
		for(List<String> drives:chunks) {
			for(String drive:drives) {
				storageChoice.addItem(drive);
			}
		}
	}
	
	@Override
	protected void done() {
		try {
			Map<String, String> storageProperties = get();
			
			storageFields.get(0).setText(storageProperties.get("Caption"));
			storageFields.get(1).setText(storageProperties.get("Model"));

			storageFields.get(3).setText(storageProperties.get("FirmwareRevision"));
			storageFields.get(4).setText(storageProperties.get("SerialNumber"));
			storageFields.get(5).setText(storageProperties.get("Partitions"));
			storageFields.get(6).setText(storageProperties.get("Status"));
			storageFields.get(7).setText(storageProperties.get("InterfaceType"));
			
			Long storageCap = Long.valueOf(storageProperties.get("Size")) / (1024 * 1024 * 1024);
			storageFields.get(2).setText((String.valueOf(storageCap) + " GB"));

			storageChoice.addActionListener(e-> new StorageActionListener(storageChoice, partitionArea, storageFields).execute());
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e1) {
			storageFields.get(2).setText("N/A"); // sets Storage capacity field to N/A in case the Size property cannot be parsed into a Long value
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

}

class StorageActionListener extends SwingWorker<Map<String, String>, JTextArea> {
	
	private JComboBox<String> storageChoice;
	private JTextArea partitionArea;
	private List<JTextField> storageFields;
	
	public StorageActionListener(JComboBox<String> storageChoice, JTextArea partitionArea, List<JTextField> storageFields) {
		this.storageChoice = storageChoice;
		this.partitionArea = partitionArea;
		this.storageFields = storageFields;
	}

	@Override
	protected Map<String, String> doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		String selectedDrive = storageChoice.getItemAt(storageChoice.getSelectedIndex());
		publish(partitionArea);
		
		new StoragePartitions(selectedDrive, partitionArea).execute();
		return Win32_DiskDrive.getDrive(selectedDrive);
	}
	
	@Override
	protected void process(List<JTextArea> chunks) {
		for(JTextArea ta: chunks) {
			// refresh the partitionArea every time a new Storage is selected
			ta.selectAll();
			ta.replaceSelection(null);
		}
	}
	
	@Override
	protected void done() {
		try {
			Map<String, String> storageProperties = get();
			
			storageFields.get(0).setText(storageProperties.get("Caption"));
			storageFields.get(1).setText(storageProperties.get("Model"));

			storageFields.get(3).setText(storageProperties.get("FirmwareRevision"));
			storageFields.get(4).setText(storageProperties.get("SerialNumber"));
			storageFields.get(5).setText(storageProperties.get("Partitions"));
			storageFields.get(6).setText(storageProperties.get("Status"));
			storageFields.get(7).setText(storageProperties.get("InterfaceType"));
			
			Long storageCap = Long.valueOf(storageProperties.get("Size")) / (1024 * 1024 * 1024);
			storageFields.get(2).setText((String.valueOf(storageCap) + " GB"));

		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e1) {
			storageFields.get(2).setText("N/A"); // sets Storage capacity field to N/A in case the Size property cannot be parsed into a Long value
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
}

class StoragePartitions extends SwingWorker<Void, List<String>> {
	
	private String currentDisk;
	private JTextArea partitionArea;
	
	public StoragePartitions(String currentDisk, JTextArea partitionArea) {
		this.currentDisk = currentDisk;
		this.partitionArea = partitionArea;
	}

	@Override
	protected Void doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		publish(Win32_DiskDriveToDiskPartition.getPartitionList(currentDisk));
		return null;
	}
	
	@Override
	protected void process(List<List<String>> chunks) {
		for(List<String> partitions:chunks) {
			for(String currentPartition:partitions) {
				partitionArea.append("Partition: " + currentPartition + "\n"); //TODO Win32_LogicalDiskToPartition(currentPartition) could not be implemented as process() is executed in the AWT Event Thread
			}
		}
	}
	
	@Override
	protected void done() {
		try {
			get();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			partitionArea.setText("N/A");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			partitionArea.setText("N/A");
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}

}

