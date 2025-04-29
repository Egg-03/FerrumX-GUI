package com.ferrumx.ui.themes;

import com.formdev.flatlaf.FlatLightLaf;

public class LightTheme extends FlatLightLaf {
	
	private static final long serialVersionUID = -8289157648820041582L;

	public static boolean setup() {
		return setup(new LightTheme());
	}
	
	@Override
    public String getName() {
        return "LightTheme";
    }

}
