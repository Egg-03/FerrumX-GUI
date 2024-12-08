package com.ferrumx.ui.utilities;

import java.io.File;
import java.io.IOException;

public class Elevation {
	private Elevation() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static String elevationStatus() {
		try {
			File testFile = new File(System.getenv("programfiles")+"/FerrumX.tfile");
			if(testFile.createNewFile()) {
				testFile.deleteOnExit();
				return "Elevated";
			}	
		} catch (IOException | SecurityException e) {
			return "Non-elevated";
		}
		return "Undetermined";
	}
}