package com.ferrumx.swingworkers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import org.tinylog.Logger;

import com.ferrumx.exceptions.ShellException;
import com.ferrumx.system.hardware.Win32_Battery;
import com.ferrumx.system.hardware.Win32_PortableBattery;
import com.ferrumx.ui.secondary.ExceptionUI;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class Battery extends SwingWorker<List<Map<String, String>>, Void> {
	
	private final JLabel batteryChargePercentage;
	private final JLabel batteryChargeIcon;
	final List<JTextField> batteryFields;
	
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
			
			if(!battery.isEmpty()) {
				String batteryCharge = battery.get("EstimatedChargeRemaining");
				batteryFields.get(0).setText(battery.get("Caption"));
				batteryFields.get(1).setText(battery.get("Status"));
				batteryFields.get(2).setText(batteryStatusInterpreter(battery.get("BatteryStatus")));
				batteryFields.get(3).setText(batteryChemistryInterpreter(battery.get("Chemistry")));
				batteryFields.get(4).setText(batteryCharge+"%");
				batteryFields.get(5).setText(battery.get("EstimatedRunTime")+" min.");
				
				batteryImageUpdateByCharge(batteryCharge, batteryChargeIcon);
				batteryChargePercentage.setText("Current Charge Level: "+batteryCharge+"%");
			}
			
			if (!portableBattery.isEmpty()) {
				batteryFields.get(6).setText(portableBattery.get("Name"));
				batteryFields.get(7).setText(portableBattery.get("DeviceID"));
				batteryFields.get(8).setText(portableBattery.get("DesignCapacity")+"mWh");
				batteryFields.get(9).setText(portableBattery.get("DesignVoltage")+ "mV");
			}
			
		} catch (ExecutionException e) {
			new ExceptionUI("Battery Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
		} catch (InterruptedException e) {
			new ExceptionUI("Battery Error", e.getMessage()+"\nPlease refer to the logs for more information.");
			Logger.error(e);
			Thread.currentThread().interrupt();
		}
	}
	
	private String batteryStatusInterpreter (String charge) {
        return switch (charge) {
            case "1" -> "Discharging";
            case "2" -> "Plugged In";
            case "3" -> "Fully Charged";
            case "4" -> "Low";
            case "5" -> "Critical";
            case "6" -> "Charging";
            case "7" -> "Charging and High";
            case "8" -> "Charging and Low";
            case "9" -> "Charging and Critical";
            case "10" -> "Undefined";
            case "11" -> "Partially Charged";
            default -> charge;
        };
	}
	
	private String batteryChemistryInterpreter (String chemistry) {
        return switch (chemistry) {
            case "1" -> "Other";
            case "2" -> "Unknown";
            case "3" -> "Lead Acid";
            case "4" -> "Nickel Cadmium";
            case "5" -> "Nickel MetalHydride";
            case "6" -> "Lithium-ion";
            case "7" -> "Zinc Air";
            case "8" -> "Lithium Polymer";
            default -> chemistry;
        };
	}
	
	private void batteryImageUpdateByCharge(String currentCharge, JLabel batteryChargeIcon) {
		int charge = Integer.parseInt(currentCharge);
		Integer ceilCharge = Math.ceilDiv(charge,10)*10; // Round to the ceiling value of the nearest multiple of 10
		String currentBatteryIndicator = String.valueOf(ceilCharge);
		
		batteryChargeIcon.setIcon(new FlatSVGIcon(Battery.class.getResource("/icons/battery_level_images/Battery-"+currentBatteryIndicator+".svg")));
	}

}
