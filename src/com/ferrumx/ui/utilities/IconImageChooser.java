package com.ferrumx.ui.utilities;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JLabel;

import com.ferrumx.ui.primary.FerrumX;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class IconImageChooser {
	
	private IconImageChooser() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static void cpuImageChooser(JLabel icon, String cpuName) {
		Map<String, String> logoMap = new LinkedHashMap<>();
		// Intel
		logoMap.put("GenuineIntel", "/resources/cpu_manufactuer_icons/intel.svg");
		logoMap.put("GenuineIotel", "/resources/cpu_manufactuer_icons/intel.svg");
		// AMD
		logoMap.put("AuthenticAMD", "/resources/cpu_manufactuer_icons/amd.svg");
		logoMap.put("AMD ISBETTER", "/resources/cpu_manufactuer_icons/amd.svg");
		logoMap.put("HygonGenuine", "/resources/cpu_manufactuer_icons/amd.svg"); // AMD-Hygon 2016 Zen1 Partnership
		// Centaur Technology
		logoMap.put("CentaurHauls", "/resources/cpu_manufactuer_icons/centaur.svg");
		// Cyrix
		logoMap.put("CyrixInstead", "/resources/cpu_manufactuer_icons/cyrix.svg");
		// Transmeta Corporation
		logoMap.put("TransmetaCPU", "/resources/cpu_manufactuer_icons/transmeta.svg");
		logoMap.put("GenuineTMx86", "/resources/cpu_manufactuer_icons/transmeta.svg");
		// National Semiconductor
		logoMap.put("Geode by NSC", "/resources/cpu_manufactuer_icons/national_semiconductor.svg");
		// VIA
		logoMap.put("VIA VIA VIA ", "/resources/cpu_manufactuer_icons/VIA.svg");
		
		if(logoMap.containsKey(cpuName))
			icon.setIcon(new FlatSVGIcon(FerrumX.class.getResource(logoMap.get(cpuName))));
	}
	
	public static void gpuImageChooser(JLabel icon, String gpuName) {
		Map<String, String> logoMap = new LinkedHashMap<>();
		logoMap.put("AMD", "/resources/gpu_manufacturer_icons/amd.svg");
		logoMap.put("ATI", "/resources/gpu_manufacturer_icons/ati.svg");
		logoMap.put("NVIDIA", "/resources/gpu_manufacturer_icons/nvidia.svg");
		logoMap.put("Intel", "/resources/gpu_manufacturer_icons/intel.svg");
		logoMap.put("Matrox", "/resources/gpu_manufacturer_icons/matrox.svg");
		logoMap.put("3dfx", "/resources/gpu_manufacturer_icons/3dfx.svg");
		
		for(Map.Entry<String, String> logos : logoMap.entrySet()) {
			if(gpuName.contains(logos.getKey()))
				icon.setIcon(new FlatSVGIcon(FerrumX.class.getResource(logos.getValue())));
		}
	}
	
	public static void osImageChooser(JLabel icon, String osName) {
		Map<String, String> logoMap = new LinkedHashMap<>();
		logoMap.put("Windows 8", "/resources/windows_logos/windows8.svg");
		logoMap.put("Windows 8.1", "/resources/windows_logos/windows8.svg");
		logoMap.put("Windows 10", "/resources/windows_logos/windows10.svg");
		logoMap.put("Windows 11", "/resources/windows_logos/windows11.svg");
		logoMap.put("Windows Server 2012", "/resources/windows_logos/Windows_Server_2012.svg");
		logoMap.put("Windows Server 2016", "/resources/windows_logos/Windows_Server_2016.svg");
		logoMap.put("Windows Server 2019", "/resources/windows_logos/Windows_Server_2019.svg");
		logoMap.put("Windows Server 2022", "/resources/windows_logos/Windows_Server_2022.svg");
		
		for(Map.Entry<String, String> logos : logoMap.entrySet()) {
			if(osName.contains(logos.getKey()))
				icon.setIcon(new FlatSVGIcon(FerrumX.class.getResource(logos.getValue())));
		}
	}
	
}
