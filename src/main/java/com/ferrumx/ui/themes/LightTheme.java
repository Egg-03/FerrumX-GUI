package com.ferrumx.ui.themes;

import com.formdev.flatlaf.FlatLightLaf;

import java.io.Serial;

public class LightTheme extends FlatLightLaf {
	
	@Serial
	private static final long serialVersionUID = -8289157648820041582L;

	@SuppressWarnings("UnusedReturnValue")
    public static boolean setup() {
		return setup(new LightTheme());
	}
	
	@Override
    public String getName() {
        return "LightTheme";
    }

}
