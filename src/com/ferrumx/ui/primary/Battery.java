package com.ferrumx.ui.primary;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_Battery;
import com.ferrumx.system.hardware.Win32_PortableBattery;
import com.ferrumx.ui.secondary.ExceptionUI;
import com.formdev.flatlaf.extras.FlatSVGIcon;

final class Battery {
	private Battery() {
		throw new IllegalStateException("Utility Class");
	}
	
	protected static void initializeBattery(JLabel batteryChargePercentage, JLabel batteryChargeIcon, JTextField... batteryFields) {
		try {
			Map<String, String> battery = Win32_Battery.getBattery();
			Map<String, String> portableBattery = Win32_PortableBattery.getPortableBattery();
			
			if(!battery.isEmpty()) {
				String batteryCharge = battery.get("EstimatedChargeRemaining");
				
				batteryFields[0].setText(battery.get("Caption"));
				batteryFields[1].setText(battery.get("Status"));
				batteryFields[2].setText(batteryStatusInterpreter(battery.get("BatteryStatus")));
				batteryFields[3].setText(batteryChemistryInterpreter(battery.get("Chemistry")));
				batteryFields[4].setText(batteryCharge+"%");
				batteryFields[5].setText(battery.get("EstimatedRunTime")+" min.");
				
				batteryImageUpdateByCharge(batteryCharge, batteryChargeIcon);
				batteryChargePercentage.setText("Current Charge Level: "+batteryCharge+"%");
			}
			
			if(!portableBattery.isEmpty()) {
				batteryFields[6].setText(portableBattery.get("Name"));
				batteryFields[7].setText(portableBattery.get("DeviceID"));
				batteryFields[8].setText(portableBattery.get("DesignCapacity")+"mWh");
				batteryFields[9].setText(portableBattery.get("DesignVoltage")+ "mV");
			}
		} catch (IndexOutOfBoundsException | IOException | ShellException e) {
			String message = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Battery Initialization Error", "Error: "+message+"\nStackTrace: \n"+stackTrace).setVisible(true);
		} catch (InterruptedException e) {
			String message = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Battery Initialization Error", "Error: "+message+"\nStackTrace: \n"+stackTrace).setVisible(true);
			Thread.currentThread().interrupt();
		}
		
	}
	
	private static String batteryStatusInterpreter (String charge) {
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
	
	private static String batteryChemistryInterpreter (String chemistry) {
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
	
	private static void batteryImageUpdateByCharge(String currentCharge, JLabel batteryChargeIcon) {
		Integer charge = Integer.valueOf(currentCharge);
		Integer ceilCharge = Math.ceilDiv(charge,10)*10; // Round to the ceiling value of the nearest multiple of 10
		String currentBatteryIndicator = String.valueOf(ceilCharge);
		
		batteryChargeIcon.setIcon(new FlatSVGIcon(Battery.class.getResource("/resources/battery_level_images/Battery-"+currentBatteryIndicator+".svg")));
	}
}
