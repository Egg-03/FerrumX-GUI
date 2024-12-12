package com.ferrumx.swingworkers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_Battery;
import com.ferrumx.system.hardware.Win32_PortableBattery;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class Battery extends SwingWorker<List<Map<String, String>>, Void> {
	
	private JLabel batteryChargePercentage;
	private JLabel batteryChargeIcon;
	List<JTextField> batteryFields;
	
	public Battery(JLabel batteryChargePercentage, JLabel batteryChargeIcon, List<JTextField> batteryFields) {
		this.batteryChargePercentage = batteryChargePercentage;
		this.batteryChargeIcon = batteryChargeIcon;
		this.batteryFields = batteryFields;
	}

	@Override
	protected List<Map<String, String>> doInBackground() throws IndexOutOfBoundsException, IOException, ShellException, InterruptedException {
		
		Map<String, String> battery = Win32_Battery.getBattery();
		Map<String, String> portableBattery = Win32_PortableBattery.getPortableBattery();
		
		return List.of(battery, portableBattery);
	}
	
	@Override
	protected void done() {
		try {
			List<Map<String, String>> batteryClasses = get();
			
			Map<String, String> battery = batteryClasses.getFirst();
			Map<String, String> portableBattery = batteryClasses.getLast();
			
			String batteryCharge = battery.get("EstimatedChargeRemaining");
			batteryFields.get(0).setText(battery.get("Caption"));
			batteryFields.get(1).setText(battery.get("Status"));
			batteryFields.get(2).setText(batteryStatusInterpreter(battery.get("BatteryStatus")));
			batteryFields.get(3).setText(batteryChemistryInterpreter(battery.get("Chemistry")));
			batteryFields.get(4).setText(batteryCharge+"%");
			batteryFields.get(5).setText(battery.get("EstimatedRunTime")+" min.");
			
			batteryFields.get(6).setText(portableBattery.get("Name"));
			batteryFields.get(7).setText(portableBattery.get("DeviceID"));
			batteryFields.get(8).setText(portableBattery.get("DesignCapacity")+"mWh");
			batteryFields.get(9).setText(portableBattery.get("DesignVoltage")+ "mV");
			
			batteryImageUpdateByCharge(batteryCharge, batteryChargeIcon);
			batteryChargePercentage.setText("Current Charge Level: "+batteryCharge+"%");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	private String batteryStatusInterpreter (String charge) {
		switch (charge) {
		case "1":
			return "Discharging";
		case "2":
			return "Plugged In";
		case "3":
			return "Fully Charged";
		case "4":
			return "Low";
		case "5":
			return "Critical";
		case "6":
			return "Charging";
		case "7":
			return "Charging and High";
		case "8":
			return "Charging and Low";
		case "9":
			return "Charging and Critical";
		case "10":
			return "Undefined";
		case "11":
			return "Partially Charged";
		default:
			return charge;
		}
	}
	
	private String batteryChemistryInterpreter (String chemistry) {
		switch (chemistry) {
		case "1":
			return "Other";
		case "2":
			return "Unknown";
		case "3":
			return "Lead Acid";
		case "4":
			return "Nickel Cadmium";
		case "5":
			return "Nickel MetalHydride";
		case "6":
			return "Lithium-ion";
		case "7":
			return "Zinc Air";
		case "8":
			return "Lithium Polymer";
		default:
			return chemistry;
		}
	}
	
	private void batteryImageUpdateByCharge(String currentCharge, JLabel batteryChargeIcon) {
		Integer charge = Integer.valueOf(currentCharge);
		Integer ceilCharge = Math.ceilDiv(charge,10)*10; // Round to the ceiling value of the nearest multiple of 10
		String currentBatteryIndicator = String.valueOf(ceilCharge);
		
		batteryChargeIcon.setIcon(new FlatSVGIcon(Battery.class.getResource("/resources/battery_level_images/Battery-"+currentBatteryIndicator+".svg")));
	}

}
