package com.ferrumx.ui.secondary;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class AboutUI extends JFrame {
	private static final long serialVersionUID = 4517185644185035860L;
	private static String appLink = "https://github.com/Egg-03/FerrumX";
	private static String devLink = "https://github.com/Egg-03";
	private static String iconLink = "https://github.com/Soumil-Biswas";
	private static String themeLink = "https://github.com/JFormDesigner/FlatLaf";
	private static String svgLink = "https://github.com/weisJ/jsvg";
	private static String iniLink = "https://ini4j.sourceforge.net/";
	private static String extraIconLink = "https://github.com/google/material-design-icons";

	public AboutUI() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutUI.class.getResource("/resources/icon_main.png")));
		setBounds(100, 100, 570, 420);
		setTitle("About FerrumX");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(mainPanel);
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_mainPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_mainPanel.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_mainPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		mainPanel.setLayout(gbl_mainPanel);

		setVersionPanel(mainPanel);
		setContributionPanel(mainPanel);
		setThirdPartyPanel(mainPanel);
	}

	private void setThirdPartyPanel(JPanel mainPanel) {
		JPanel thirdPartyPanel = new JPanel();
		thirdPartyPanel.setBorder(new TitledBorder(null, "Third Party Libraries", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_thirdPartyPanel = new GridBagConstraints();
		gbc_thirdPartyPanel.gridwidth = 3;
		gbc_thirdPartyPanel.insets = new Insets(0, 0, 0, 5);
		gbc_thirdPartyPanel.fill = GridBagConstraints.BOTH;
		gbc_thirdPartyPanel.gridx = 0;
		gbc_thirdPartyPanel.gridy = 2;
		mainPanel.add(thirdPartyPanel, gbc_thirdPartyPanel);
		GridBagLayout gbl_thirdPartyPanel = new GridBagLayout();
		gbl_thirdPartyPanel.columnWidths = new int[]{0, 0, 0};
		gbl_thirdPartyPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_thirdPartyPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_thirdPartyPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		thirdPartyPanel.setLayout(gbl_thirdPartyPanel);

		JLabel themeSupport = new JLabel("Theme Support");
		themeSupport.setIcon(new FlatSVGIcon(AboutUI.class.getResource("/resources/extra_icons/theme.svg")));
		GridBagConstraints gbc_themeSupport = new GridBagConstraints();
		gbc_themeSupport.insets = new Insets(0, 0, 5, 5);
		gbc_themeSupport.anchor = GridBagConstraints.WEST;
		gbc_themeSupport.gridx = 0;
		gbc_themeSupport.gridy = 0;
		thirdPartyPanel.add(themeSupport, gbc_themeSupport);

		JTextField themeSupportLink = new JTextField();
		themeSupportLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		themeSupportLink.setText(themeLink);
		themeSupportLink.setForeground(new Color(0, 191, 255));
		themeSupportLink.setEditable(false);
		themeSupportLink.setColumns(10);
		themeSupportLink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ConfirmationUI confirm = new ConfirmationUI("Contributor Profile","This will open a new browser window. Continue ?");
				confirm.getBtnYes().addActionListener(e1-> {
					try {
						Desktop.getDesktop().browse(new URI(themeLink));
						confirm.dispose();
						themeSupportLink.setForeground(new Color(225, 0, 225)); //this will mark as visited
					} catch (URISyntaxException | IOException ex) {
						String errorMessage = ex.getMessage();
						String stackTrace = Arrays.toString(ex.getStackTrace());
						new ExceptionUI("Link Visit Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
						confirm.dispose();
					}
				});

				confirm.getBtnNo().addActionListener(e1->confirm.dispose());
			}
		});
		GridBagConstraints gbc_themeSupportLink = new GridBagConstraints();
		gbc_themeSupportLink.insets = new Insets(0, 0, 5, 0);
		gbc_themeSupportLink.fill = GridBagConstraints.HORIZONTAL;
		gbc_themeSupportLink.gridx = 1;
		gbc_themeSupportLink.gridy = 0;
		thirdPartyPanel.add(themeSupportLink, gbc_themeSupportLink);

		JLabel svgSupport = new JLabel("SVG Support");
		svgSupport.setIcon(new FlatSVGIcon(AboutUI.class.getResource("/resources/extra_icons/library.svg")));
		GridBagConstraints gbc_svgSupport = new GridBagConstraints();
		gbc_svgSupport.anchor = GridBagConstraints.WEST;
		gbc_svgSupport.insets = new Insets(0, 0, 5, 5);
		gbc_svgSupport.gridx = 0;
		gbc_svgSupport.gridy = 1;
		thirdPartyPanel.add(svgSupport, gbc_svgSupport);

		JTextField svgSupportLink = new JTextField();
		svgSupportLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		svgSupportLink.setForeground(new Color(0, 191, 255));
		svgSupportLink.setText(svgLink);
		svgSupportLink.setEditable(false);
		GridBagConstraints gbc_svgSupportLink = new GridBagConstraints();
		gbc_svgSupportLink.insets = new Insets(0, 0, 5, 0);
		gbc_svgSupportLink.fill = GridBagConstraints.HORIZONTAL;
		gbc_svgSupportLink.gridx = 1;
		gbc_svgSupportLink.gridy = 1;
		thirdPartyPanel.add(svgSupportLink, gbc_svgSupportLink);
		svgSupportLink.setColumns(10);
		svgSupportLink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ConfirmationUI confirm = new ConfirmationUI("Contributor Profile","This will open a new browser window. Continue ?");
				confirm.getBtnYes().addActionListener(e1-> {
					try {
						Desktop.getDesktop().browse(new URI(svgLink));
						confirm.dispose();
						svgSupportLink.setForeground(new Color(225, 0, 225)); //this will mark as visited
					} catch (URISyntaxException | IOException ex) {
						String errorMessage = ex.getMessage();
						String stackTrace = Arrays.toString(ex.getStackTrace());
						new ExceptionUI("Link Visit Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
						confirm.dispose();
					}
				});

				confirm.getBtnNo().addActionListener(e1->confirm.dispose());
			}
		});

		JLabel iniSupport = new JLabel("INI Support");
		iniSupport.setIcon(new FlatSVGIcon(AboutUI.class.getResource("/resources/extra_icons/library.svg")));
		GridBagConstraints gbc_iniSupport = new GridBagConstraints();
		gbc_iniSupport.anchor = GridBagConstraints.WEST;
		gbc_iniSupport.insets = new Insets(0, 0, 5, 5);
		gbc_iniSupport.gridx = 0;
		gbc_iniSupport.gridy = 2;
		thirdPartyPanel.add(iniSupport, gbc_iniSupport);

		JTextField iniSupportLink = new JTextField();
		iniSupportLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		iniSupportLink.setForeground(new Color(0, 191, 255));
		iniSupportLink.setText(iniLink);
		iniSupportLink.setEditable(false);
		GridBagConstraints gbc_iniSupportLink = new GridBagConstraints();
		gbc_iniSupportLink.insets = new Insets(0, 0, 5, 0);
		gbc_iniSupportLink.fill = GridBagConstraints.HORIZONTAL;
		gbc_iniSupportLink.gridx = 1;
		gbc_iniSupportLink.gridy = 2;
		thirdPartyPanel.add(iniSupportLink, gbc_iniSupportLink);
		iniSupportLink.setColumns(10);
		iniSupportLink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ConfirmationUI confirm = new ConfirmationUI("Contributor Profile","This will open a new browser window. Continue ?");
				confirm.getBtnYes().addActionListener(e1-> {
					try {
						Desktop.getDesktop().browse(new URI(iniLink));
						confirm.dispose();
						iniSupportLink.setForeground(new Color(225, 0, 225)); //this will mark as visited
					} catch (URISyntaxException | IOException ex) {
						String errorMessage = ex.getMessage();
						String stackTrace = Arrays.toString(ex.getStackTrace());
						new ExceptionUI("Link Visit Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
						confirm.dispose();
					}
				});

				confirm.getBtnNo().addActionListener(e1->confirm.dispose());
			}
		});

		JLabel extraIcons = new JLabel("Extra Icons");
		extraIcons.setIcon(new FlatSVGIcon(AboutUI.class.getResource("/resources/extra_icons/extension.svg")));
		GridBagConstraints gbc_extraIcons = new GridBagConstraints();
		gbc_extraIcons.anchor = GridBagConstraints.WEST;
		gbc_extraIcons.insets = new Insets(0, 0, 5, 5);
		gbc_extraIcons.gridx = 0;
		gbc_extraIcons.gridy = 3;
		thirdPartyPanel.add(extraIcons, gbc_extraIcons);

		JTextField extraIconsLink = new JTextField();
		extraIconsLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		extraIconsLink.setForeground(new Color(0, 191, 255));
		extraIconsLink.setText(extraIconLink);
		extraIconsLink.setEditable(false);
		GridBagConstraints gbc_extraIconsLink = new GridBagConstraints();
		gbc_extraIconsLink.insets = new Insets(0, 0, 5, 0);
		gbc_extraIconsLink.fill = GridBagConstraints.HORIZONTAL;
		gbc_extraIconsLink.gridx = 1;
		gbc_extraIconsLink.gridy = 3;
		thirdPartyPanel.add(extraIconsLink, gbc_extraIconsLink);
		extraIconsLink.setColumns(10);

		JScrollPane unsignedNoticeScrollPane = new JScrollPane();
		GridBagConstraints gbc_unsignedNoticeScrollPane = new GridBagConstraints();
		gbc_unsignedNoticeScrollPane.fill = GridBagConstraints.BOTH;
		gbc_unsignedNoticeScrollPane.gridwidth = 2;
		gbc_unsignedNoticeScrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_unsignedNoticeScrollPane.gridx = 0;
		gbc_unsignedNoticeScrollPane.gridy = 4;
		thirdPartyPanel.add(unsignedNoticeScrollPane, gbc_unsignedNoticeScrollPane);

		JTextArea unsignedNotice = new JTextArea();
		unsignedNotice.setFont(new Font("Segoe UI", Font.ITALIC, 11));
		unsignedNoticeScrollPane.setViewportView(unsignedNotice);
		unsignedNotice.setEditable(false);
		unsignedNotice.setWrapStyleWord(true);
		unsignedNotice.setLineWrap(true);
		unsignedNotice.setText("WARNING: The executable in this release has been wrapped using launch4j and is currently unsigned (I'm too poor to afford signing). As of now, the project's github repository is the only official distribution page for the program. Be careful if you've downloaded the program from other sources.");
		extraIconsLink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ConfirmationUI confirm = new ConfirmationUI("Contributor Profile","This will open a new browser window. Continue ?");
				confirm.getBtnYes().addActionListener(e1-> {
					try {
						Desktop.getDesktop().browse(new URI(extraIconLink));
						confirm.dispose();
						extraIconsLink.setForeground(new Color(225, 0, 225)); //this will mark as visited
					} catch (URISyntaxException | IOException ex) {
						String errorMessage = ex.getMessage();
						String stackTrace = Arrays.toString(ex.getStackTrace());
						new ExceptionUI("Link Visit Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
						confirm.dispose();
					}
				});

				confirm.getBtnNo().addActionListener(e1->confirm.dispose());
			}
		});
	}

	private void setContributionPanel(JPanel mainPanel) {
		JPanel contributionPanel = new JPanel();
		contributionPanel.setBorder(new TitledBorder(null, "Contribution", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_contributionPanel = new GridBagConstraints();
		gbc_contributionPanel.insets = new Insets(0, 0, 5, 0);
		gbc_contributionPanel.gridwidth = 3;
		gbc_contributionPanel.fill = GridBagConstraints.BOTH;
		gbc_contributionPanel.gridx = 0;
		gbc_contributionPanel.gridy = 1;
		mainPanel.add(contributionPanel, gbc_contributionPanel);
		GridBagLayout gbl_contributionPanel = new GridBagLayout();
		gbl_contributionPanel.columnWidths = new int[]{88, 412, 0};
		gbl_contributionPanel.rowHeights = new int[]{20, 20, 0};
		gbl_contributionPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contributionPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contributionPanel.setLayout(gbl_contributionPanel);

		JTextField developerLink = new JTextField();
		developerLink.setForeground(new Color(0, 191, 255));
		developerLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		developerLink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ConfirmationUI confirm = new ConfirmationUI("Developer Profile","This will open a new browser window. Continue ?");
				confirm.getBtnYes().addActionListener(e1-> {
					try {
						Desktop.getDesktop().browse(new URI(devLink));
						confirm.dispose();
						developerLink.setForeground(new Color(225, 0, 225)); //this will mark as visited
					} catch (URISyntaxException | IOException ex) {
						String errorMessage = ex.getMessage();
						String stackTrace = Arrays.toString(ex.getStackTrace());
						new ExceptionUI("Link Visit Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
						confirm.dispose();
					}
				});

				confirm.getBtnNo().addActionListener(e1->confirm.dispose());
			}
		});

		JLabel developer = new JLabel("Developer");
		developer.setToolTipText("Wrapper and UI Developer");
		developer.setIcon(new FlatSVGIcon(AboutUI.class.getResource("/resources/extra_icons/developer.svg")));
		GridBagConstraints gbc_developer = new GridBagConstraints();
		gbc_developer.anchor = GridBagConstraints.WEST;
		gbc_developer.insets = new Insets(0, 0, 5, 5);
		gbc_developer.gridx = 0;
		gbc_developer.gridy = 0;
		contributionPanel.add(developer, gbc_developer);
		developerLink.setText(devLink);
		developerLink.setEditable(false);
		GridBagConstraints gbc_developerLink = new GridBagConstraints();
		gbc_developerLink.fill = GridBagConstraints.HORIZONTAL;
		gbc_developerLink.insets = new Insets(0, 0, 5, 0);
		gbc_developerLink.gridx = 1;
		gbc_developerLink.gridy = 0;
		contributionPanel.add(developerLink, gbc_developerLink);
		developerLink.setColumns(10);

		JLabel iconMaker = new JLabel("Designer");
		iconMaker.setToolTipText("Logo and Icon Designer");
		iconMaker.setIcon(new FlatSVGIcon(AboutUI.class.getResource("/resources/extra_icons/designer.svg")));
		GridBagConstraints gbc_iconMaker = new GridBagConstraints();
		gbc_iconMaker.anchor = GridBagConstraints.WEST;
		gbc_iconMaker.insets = new Insets(0, 0, 0, 5);
		gbc_iconMaker.gridx = 0;
		gbc_iconMaker.gridy = 1;
		contributionPanel.add(iconMaker, gbc_iconMaker);

		JTextField iconMakerLink = new JTextField();
		iconMakerLink.setForeground(new Color(0, 191, 255));
		iconMakerLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		iconMakerLink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ConfirmationUI confirm = new ConfirmationUI("Contributor Profile","This will open a new browser window. Continue ?");
				confirm.getBtnYes().addActionListener(e1-> {
					try {
						Desktop.getDesktop().browse(new URI(iconLink));
						confirm.dispose();
						iconMakerLink.setForeground(new Color(225, 0, 225)); //this will mark as visited
					} catch (URISyntaxException | IOException ex) {
						String errorMessage = ex.getMessage();
						String stackTrace = Arrays.toString(ex.getStackTrace());
						new ExceptionUI("Link Visit Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
						confirm.dispose();
					}
				});

				confirm.getBtnNo().addActionListener(e1->confirm.dispose());
			}
		});
		iconMakerLink.setText(iconLink);
		iconMakerLink.setEditable(false);
		GridBagConstraints gbc_iconMakerLink = new GridBagConstraints();
		gbc_iconMakerLink.fill = GridBagConstraints.HORIZONTAL;
		gbc_iconMakerLink.gridx = 1;
		gbc_iconMakerLink.gridy = 1;
		contributionPanel.add(iconMakerLink, gbc_iconMakerLink);
		iconMakerLink.setColumns(10);
	}

	private void setVersionPanel(JPanel mainPanel) {
		JPanel wrapperVersionPanel = new JPanel();
		wrapperVersionPanel.setBorder(new TitledBorder(null, "Wrapper Version", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_wrapperVersionPanel = new GridBagConstraints();
		gbc_wrapperVersionPanel.fill = GridBagConstraints.BOTH;
		gbc_wrapperVersionPanel.insets = new Insets(0, 0, 5, 5);
		gbc_wrapperVersionPanel.gridx = 0;
		gbc_wrapperVersionPanel.gridy = 0;
		mainPanel.add(wrapperVersionPanel, gbc_wrapperVersionPanel);
		wrapperVersionPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel wrapperVersion = new JLabel("v1.3.0");
		wrapperVersion.setHorizontalAlignment(SwingConstants.CENTER);
		wrapperVersionPanel.add(wrapperVersion);
		wrapperVersion.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 12));
		wrapperVersion.setIcon(new FlatSVGIcon(AboutUI.class.getResource("/resources/extra_icons/api.svg")));

		JPanel logoPanel = new JPanel();
		logoPanel.setBorder(new TitledBorder(null, "Click to open the github page", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagConstraints gbc_logoPanel = new GridBagConstraints();
		gbc_logoPanel.fill = GridBagConstraints.BOTH;
		gbc_logoPanel.insets = new Insets(0, 0, 5, 5);
		gbc_logoPanel.gridx = 1;
		gbc_logoPanel.gridy = 0;
		mainPanel.add(logoPanel, gbc_logoPanel);
		GridBagLayout gbl_logoPanel = new GridBagLayout();
		gbl_logoPanel.columnWidths = new int[] { 0, 0 };
		gbl_logoPanel.rowHeights = new int[] { 0, 0 };
		gbl_logoPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_logoPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		logoPanel.setLayout(gbl_logoPanel);

		JLabel logo = new JLabel("");
		logo.setHorizontalAlignment(SwingConstants.CENTER);
		logo.setIcon(new FlatSVGIcon(AboutUI.class.getResource("/resources/logo_main_broad.svg")));
		logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(appLink));
				} catch (URISyntaxException | IOException ex) {
					String errorMessage = ex.getMessage();
					String stackTrace = Arrays.toString(ex.getStackTrace());
					new ExceptionUI("Link Visit Error", "Error: "+errorMessage+"\nStackTrace: \n"+stackTrace).setVisible(true);
				}
			}
		});
		GridBagConstraints gbc_logo = new GridBagConstraints();
		gbc_logo.gridx = 0;
		gbc_logo.gridy = 0;
		logoPanel.add(logo, gbc_logo);

		JPanel guiVersionPanel = new JPanel();
		guiVersionPanel.setBorder(new TitledBorder(null, "GUI Version", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_guiVersionPanel = new GridBagConstraints();
		gbc_guiVersionPanel.insets = new Insets(0, 0, 5, 0);
		gbc_guiVersionPanel.fill = GridBagConstraints.BOTH;
		gbc_guiVersionPanel.gridx = 2;
		gbc_guiVersionPanel.gridy = 0;
		mainPanel.add(guiVersionPanel, gbc_guiVersionPanel);
		guiVersionPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel guiVersion = new JLabel("v1.1.0");
		guiVersion.setHorizontalAlignment(SwingConstants.CENTER);
		guiVersionPanel.add(guiVersion);
		guiVersion.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 12));
		guiVersion.setIcon(new FlatSVGIcon(AboutUI.class.getResource("/resources/extra_icons/ui.svg")));
		
	}
}
