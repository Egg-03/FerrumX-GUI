package com.ferrumx.ui.utilities;

import java.awt.TrayIcon.MessageType;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JRadioButtonMenuItem;

import org.ini4j.Wini;

import com.ferrumx.ui.secondary.ExceptionUI;
import com.formdev.flatlaf.extras.FlatSVGIcon;

public class ThemeLoader {
	private ThemeLoader() {
		throw new IllegalStateException("Utility Class");
	}

	public static void store(String themeClass) {

		try {
			// if the settings folder or the theme.ini file does not exist, create one
			if (!new File("settings").isDirectory()) {
				new File("settings").mkdir();
				if (new File("settings/theme.ini").createNewFile()) {
					new NotificationTray().displayTrayAutoRemove(
							"Created the theme.ini file. All selected themes will be stored and loaded from this file",
							MessageType.INFO);
				}

			} else if (!new File("settings/theme.ini").isFile() && new File("settings/theme.ini").createNewFile()) {
				new NotificationTray().displayTrayAutoRemove(
						"Created the theme.ini file. All selected themes will be stored and loaded from this file",
						MessageType.INFO);
			}

			// if the theme file exists, store the currently selected theme class
			Wini ini = new Wini(new File("settings/theme.ini"));
			ini.put("Theme", "CurrentTheme", themeClass);
			ini.store();
		} catch (IOException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Theme Save Error", "Error: " + errorMessage + "\nStackTrace: \n" + stackTrace)
					.setVisible(true);
		}
	}

	// load the stored theme from the ini file
	public static String load() {
		String defaultTheme = "com.formdev.flatlaf.themes.FlatMacDarkLaf";
		try {
			// if the settings folder or the theme.ini file does not exist, create one
			// and store it with the default black theme
			if (!new File("settings").isDirectory()) {
				new File("settings").mkdir();
				if (new File("settings/theme.ini").createNewFile()) {
					// if a new theme.ini file was created, load with the default black theme
					Wini ini = new Wini(new File("settings/theme.ini"));
					ini.put("Theme", "CurrentTheme", defaultTheme);
					ini.store();
				}
				return defaultTheme;
			} else if (!new File("settings/theme.ini").isFile()) {
				if (new File("settings/theme.ini").createNewFile()) {
					// if a new theme.ini file was created, load with the default black theme
					Wini ini = new Wini(new File("settings/theme.ini"));
					ini.put("Theme", "CurrentTheme", defaultTheme);
					ini.store();
				}
				return defaultTheme;
			}

			// load theme from the saved ini file
			Wini ini = new Wini(new File("settings/theme.ini"));
			return ini.get("Theme", "CurrentTheme");

		} catch (IOException e) {
			String errorMessage = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Theme Load Error", "Error: " + errorMessage + "\nStackTrace: \n" + stackTrace)
					.setVisible(true);
			return defaultTheme;
		}
	}

	// will read the current loaded theme class name and select the appropriate
	// theme button
	public static void notifyCurrentThemeUsage(JRadioButtonMenuItem... themeButtons) {
		switch (ThemeLoader.load()) {
		case "com.formdev.flatlaf.themes.FlatMacDarkLaf":
			themeButtons[0].setSelected(true);
			break;
		case "com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkHardIJTheme":
			themeButtons[1].setSelected(true);
			break;
		case "com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMoonlightIJTheme":
			themeButtons[2].setSelected(true);
			break;
		case "com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme":
			themeButtons[3].setSelected(true);
			break;
		case "com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme":
			themeButtons[4].setSelected(true);
			break;
		case "com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme":
			themeButtons[5].setSelected(true);
			break;
		case "com.egg.flatlafcustomthemes.SamLaf":
			themeButtons[6].setSelected(true);
			FlatSVGIcon.ColorFilter.getInstance().setMapper(Color -> java.awt.Color.decode("#fe4956"));
			break;
			/* Since this function is called during the initializeMenu() function in FerrumX.java, which is
			 * called during the UI component loading phase, the pink color filter gets applied before the
			 * components are visible */
		case "com.egg.flatlafcustomthemes.BellLaf":
			themeButtons[7].setSelected(true);
			FlatSVGIcon.ColorFilter.getInstance().setMapper(Color -> java.awt.Color.PINK);
			break;
		case "com.egg.flatlafcustomthemes.NoirLightLaf":
			themeButtons[8].setSelected(true);
			break;
		case "com.egg.flatlafcustomthemes.NoirDarkLaf":
			themeButtons[9].setSelected(true);
			break;
		default:
			themeButtons[0].setSelected(true);
		}
	}
}
