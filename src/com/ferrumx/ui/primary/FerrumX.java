package com.ferrumx.ui.primary;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultCaret;

import com.ferrumx.ui.report.DetailedReportGeneration;
import com.ferrumx.ui.report.SummarizedReportGeneration;
import com.ferrumx.ui.secondary.AboutUI;
import com.ferrumx.ui.secondary.ConfirmationUI;
import com.ferrumx.ui.secondary.ExceptionUI;
import com.ferrumx.ui.secondary.StatusUI;
import com.ferrumx.ui.utilities.ComponentImageCapture;
import com.ferrumx.ui.utilities.DateTime;
import com.ferrumx.ui.utilities.Elevation;
import com.ferrumx.ui.utilities.ThemeLoader;
import com.ferrumx.ui.utilities.UIManagerConfigurations;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.border.LineBorder;

public class FerrumX {

	private JFrame mainFrame;
	// HWID
	private JTextField hwidTf;
	// CPU
	private JLabel cpuLogo; // contains logo or img
	private JComboBox<String> cpuChoice;
	private JTextField cpuCoreTf;
	private JTextField cpuThreadTf;
	private JTextField cpuLogicProcessorTf;
	private JTextField cpuNameTf;
	private JTextField addressWidthTf;
	private JTextField cpuSocketTf;
	private JTextField cpuManufacturerTf;
	private JTextField cpuBaseClockTf;
	private JTextField multiplierTf;
	private JTextField effectiveClockTf;
	private JTextField cpuVersionTf;
	private JTextField cpuCaptionTf;
	private JTextField cpuFamilyTf;
	private JTextField cpuSteppingTf;
	private JTextField cpuVirtStatusTf;
	private JTextField cpuIdTf;
	private JTextField cpuL2Tf;
	private JTextField cpuL3Tf;
	private JTextArea cacheTa;
	// Memory
	private JComboBox<String> memorySlotChoice;
	private JTextField memoryNameTf;
	private JTextField memoryManufacturerTf;
	private JTextField memoryModelTf;
	private JTextField memoryOtherInfoTf;
	private JTextField memoryPartNumberTf;
	private JTextField memoryFormFactorTf;
	private JTextField memoryBankLabelTf;
	private JTextField memoryCapacityTf;
	private JTextField memoryWidthTf;
	private JTextField memorySpeedTf;
	private JTextField memoryConfigClockSpeedTf;
	private JTextField memoryLocatorTf;
	private JTextField memorySerialNumberTf;
	// Mainboard
	private JTextField mbManufacturerTf;
	private JTextField mbModelTf;
	private JTextField mbProductTf;
	private JTextField mbSerialNumberTf;
	private JTextField mbVersionTf;
	private JTextField mbPnpTf;
	private JTextField biosNameTf;
	private JTextField biosCaptionTf;
	private JTextField biosManufacturerTf;
	private JTextField biosReleaseDateTf;
	private JTextField biosVersionTf;
	private JTextField biosStatusTf;
	private JTextField smbiosPresenceTf;
	private JTextField smbiosVersionTf;
	private JTextField biosLanguageTf;
	// GPU
	private JLabel gpuLogo; // contains logo or image
	private JComboBox<String> gpuChoiceComboBox;
	private JTextField gpuNameTf;
	private JTextField gpuPnpTf;
	private JTextField gpuHorizontalResTf;
	private JTextField gpuVerticalResTf;
	private JTextField gpuBitsTf;
	private JTextField gpuMinRefTf;
	private JTextField gpuMaxRefTf;
	private JTextField gpuCurrentRefTf;
	private JTextField gpuDACTf;
	private JTextField gpuVramTf;
	private JTextField gpuDriverVersionTf;
	private JTextField gpuDriverDateTf;
	private JTextField gpuVideoProcessorTf;
	// Network
	private JComboBox<String> netConnectionChoiceBox;
	private JTextField netNameTf;
	private JTextField netPnpTf;
	private JTextField netMacTf;
	private JTextField netConnectIdTf;
	private JTextField netIpStatusTf;
	private JTextField netIPAddressTf;
	private JTextField netIPSubnetTf;
	private JTextField netGatewayTf;
	private JTextField netDHCPStatusTf;
	private JTextField netDHCPServerTf;
	private JTextField netDNSHostTf;
	private JTextField netDNSServerTf;
	// Disk
	private JComboBox<String> diskIndexChoiceBox;
	private JTextField diskCaptionTf;
	private JTextField diskModelTf;
	private JTextField diskSizeTf;
	private JTextField firmwareRevisionTf;
	private JTextField diskSerialNumberTf;
	private JTextField diskPartitionsTf;
	private JTextField diskStatusTf;
	private JTextField diskInterfaceTf;
	private JTextArea diskPartTa;
	// OS
	private JLabel osCoverImg; // contains logo or img
	private JComboBox<String> currentOsChoiceBox;
	private JTextField osCaptionTf;
	private JTextField osVersionTf;
	private JTextField osManufacturerTf;
	private JTextField osArchitectureTf;
	private JTextField osBuildNumberTf;
	private JTextField osInstallDateTf;
	private JTextField osLastBootTf;
	private JTextField osSerialTf;
	private JTextField osPrimaryTf;
	private JTextField osDistributedTf;
	private JTextField osPortTf;
	private JTextField osDeviceNameTf;
	private JTextField osUserCountTf;
	private JTextField osRegUserTf;
	private JTextField osLangTf;
	private JTextField osSysDriveTf;
	private JTextField osWinDirTf;
	private JTextField osSysDirTf;
	// User and Time zone
	private JTextField userTf;
	private JTextField userHomeTf;
	private JTextField timeZoneNameTf;
	private JTextField timeZoneCaptionTf;
	// Battery
	private JLabel batteryChargeView; //the icon representing the current charge level on the battery
	private JLabel batteryChargePercentage; // the charge percentage on the battery icon panel
	private JTextField batteryCaptionTf;
	private JTextField batteryStatusTf;
	private JTextField batteryStatusTwoTf;
	private JTextField batteryChemistryTf;
	private JTextField batteryChargeTf;
	private JTextField batteryRuntimeTf;
	private JTextField batteryNameTf;
	private JTextField batteryDeviceIDTf;
	private JTextField batteryCapcityTf;
	private JTextField batteryVoltageTf;

	// Links
	private String appLatestReleasePage = "https://github.com/Egg-03/FerrumX/releases/latest";
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(ThemeLoader.load());
			UIManagerConfigurations.enableRoundComponents();
			UIManagerConfigurations.enableTabSeparators(true);
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException| UnsupportedLookAndFeelException e) {
			String message = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("Theme Error", "Error: "+message+"\nStackTrace: \n"+stackTrace).setVisible(true);
		}

		try {
			FerrumX window = new FerrumX();
			window.mainFrame.setVisible(true);
		} catch (Exception e) {
			String message = e.getMessage();
			String stackTrace = Arrays.toString(e.getStackTrace());
			new ExceptionUI("FerrumX Application Window Launch Error", "Error: "+message+"\nStackTrace: \n"+stackTrace).setVisible(true);
		}
	}

	/**
	 * Create the application.
	 */
	public FerrumX() {
		initializeComponents();
		initializeSystemInfo(new StatusUI("Booting Up", "Please wait till FerrumX gathers information about your system"));
	}

	// changes theme on the fly with application open
	private void changeTheme(String lnfName, JFrame mainframe) {
		try {
			UIManager.setLookAndFeel(lnfName);
			SwingUtilities.updateComponentTreeUI(mainframe);
			ThemeLoader.store(lnfName);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			new ExceptionUI("Theme Change Error", e.getMessage()).setVisible(true);
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeComponents() {
		mainFrame = new JFrame();
		mainFrame.setTitle("FerrumX");
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(FerrumX.class.getResource("/resources/icon_main.png")));
		mainFrame.setBounds(100, 100, 860, 630);
		mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		tabbedPane.setFont(new Font("Trebuchet MS", Font.BOLD | Font.ITALIC, 11));
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		mainFrame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

		// Initialize the CPU Panel
		JPanel hwidCpuPanel = new JPanel();
		initializeCpuPanel(tabbedPane, hwidCpuPanel);

		// Initialize the Memory Panel
		JPanel memoryPanel = new JPanel();
		initializeMemoryPanel(tabbedPane, memoryPanel);

		// Initialize the mainboard and the bios panels
		JPanel mainBoardPanel = new JPanel();
		mainBoardPanel.setBorder(null);
		initializeMainboardPanel(tabbedPane, mainBoardPanel);

		// Initialize the gpu panel
		JPanel gpuPanel = new JPanel();
		initializeGpuPanel(tabbedPane, gpuPanel);

		// Initialize the network panel
		JPanel networkPanel = new JPanel();
		initializeNetworkPanel(tabbedPane, networkPanel);

		// Initialize the storage panel
		JPanel storagePanel = new JPanel();
		initializeStoragePanel(tabbedPane, storagePanel);

		// Initialize the os and the user panels
		JPanel osAndUserPanel = new JPanel();
		initializeOsAndUserPanel(tabbedPane, osAndUserPanel);
		
		// Initialize the battery panel
		JPanel batteryPanel = new JPanel();
		initializeBatteryPanel(tabbedPane, batteryPanel);

		// Initialize the report panel
		initializeReportPanel(tabbedPane);

		// Initialize the Menu Bar
		JMenuBar menuBar = new JMenuBar();
		initializeMenu(mainFrame, tabbedPane, menuBar);
		mainFrame.getContentPane().add(menuBar, BorderLayout.NORTH);
		
		//show the date of last app startup and operation mode
		JLabel timeAndOperationModeField = new JLabel();
		timeAndOperationModeField.setHorizontalAlignment(SwingConstants.CENTER);
		timeAndOperationModeField.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 12));
		timeAndOperationModeField.setText(DateTime.getDate()+"   |   Operation Mode: "+Elevation.elevationStatus()+"   |   "+System.getProperty("java.runtime.name")+" "+System.getProperty("java.runtime.version"));
		mainFrame.getContentPane().add(timeAndOperationModeField, BorderLayout.SOUTH);
		
	}

	private void initializeMenu(JFrame mainFrame, JTabbedPane tabbedPane, JMenuBar menuBar) {
		//theme menu
		JMenu themeMenu = new JMenu("Appearance");
		menuBar.add(themeMenu);

		JRadioButtonMenuItem darkThemeChoice = new JRadioButtonMenuItem("Mac Dark");
		darkThemeChoice.addActionListener(e -> {
			if (darkThemeChoice.isSelected()) {
				changeTheme("com.formdev.flatlaf.themes.FlatMacDarkLaf", mainFrame);
				FlatSVGIcon.ColorFilter.getInstance().setMapper(null);
			}
		});
		themeMenu.add(darkThemeChoice);

		JRadioButtonMenuItem gruvboxThemeChoice = new JRadioButtonMenuItem("Gruvbox Dark");
		gruvboxThemeChoice.addActionListener(e -> {
			if (gruvboxThemeChoice.isSelected()) {
				changeTheme("com.formdev.flatlaf.intellijthemes.FlatGruvboxDarkHardIJTheme", mainFrame);
				FlatSVGIcon.ColorFilter.getInstance().setMapper(null);
			}
		});
		themeMenu.add(gruvboxThemeChoice);

		JRadioButtonMenuItem moonlightThemeChoice = new JRadioButtonMenuItem("Moonlight Purple");
		moonlightThemeChoice.addActionListener(e -> {
			if (moonlightThemeChoice.isSelected()) {
				changeTheme("com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMoonlightIJTheme", mainFrame);
				FlatSVGIcon.ColorFilter.getInstance().setMapper(null);
			}
		});
		themeMenu.add(moonlightThemeChoice);

		JRadioButtonMenuItem monokaiproThemeChoice = new JRadioButtonMenuItem("Monokai Pro");
		monokaiproThemeChoice.addActionListener(e -> {
			if (monokaiproThemeChoice.isSelected()) {
				changeTheme("com.formdev.flatlaf.intellijthemes.FlatMonokaiProIJTheme", mainFrame);
				FlatSVGIcon.ColorFilter.getInstance().setMapper(null);
			}
		});
		themeMenu.add(monokaiproThemeChoice);

		JRadioButtonMenuItem purpleThemeChoice = new JRadioButtonMenuItem("Dark Purple");
		purpleThemeChoice.addActionListener(e -> {
			if (purpleThemeChoice.isSelected()) {
				changeTheme("com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme", mainFrame);
				FlatSVGIcon.ColorFilter.getInstance().setMapper(null);
			}
		});
		themeMenu.add(purpleThemeChoice);

		JRadioButtonMenuItem carbonThemeChoice = new JRadioButtonMenuItem("Carbon");
		carbonThemeChoice.addActionListener(e -> {
			if (carbonThemeChoice.isSelected()) {
				changeTheme("com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme", mainFrame);
				FlatSVGIcon.ColorFilter.getInstance().setMapper(null);
			}
		});
		themeMenu.add(carbonThemeChoice);
		
		JRadioButtonMenuItem samLaf = new JRadioButtonMenuItem("Sam's Blood Moon");
		samLaf.addActionListener(e -> {
			if (samLaf.isSelected()) {
				changeTheme("com.egg.flatlafcustomthemes.SamLaf", mainFrame);
				FlatSVGIcon.ColorFilter.getInstance().setMapper(Color -> java.awt.Color.decode("#fe4956"));
			}
		});
		themeMenu.add(samLaf);
		
		JRadioButtonMenuItem bellLaf = new JRadioButtonMenuItem("Bell's Pink World");
		bellLaf.addActionListener(e -> {
			if (bellLaf.isSelected()) {
				changeTheme("com.egg.flatlafcustomthemes.BellLaf", mainFrame);
				FlatSVGIcon.ColorFilter.getInstance().setMapper(Color -> java.awt.Color.PINK);
			}
		});
		themeMenu.add(bellLaf);
		
		JRadioButtonMenuItem noirLightLaf = new JRadioButtonMenuItem("Noir Light");
		noirLightLaf.addActionListener(e -> {
			if (noirLightLaf.isSelected()) {
				changeTheme("com.egg.flatlafcustomthemes.NoirLightLaf", mainFrame);
				FlatSVGIcon.ColorFilter.getInstance().setMapper(null);
			}
		});
		themeMenu.add(noirLightLaf);
		
		JRadioButtonMenuItem noirDarkLaf = new JRadioButtonMenuItem("Noir Dark");
		noirDarkLaf.addActionListener(e -> {
			if (noirDarkLaf.isSelected()) {
				changeTheme("com.egg.flatlafcustomthemes.NoirDarkLaf", mainFrame);
				FlatSVGIcon.ColorFilter.getInstance().setMapper(null);
			}
		});
		themeMenu.add(noirDarkLaf);

		ButtonGroup themeButtonGroup = new ButtonGroup();
		themeButtonGroup.add(gruvboxThemeChoice);
		themeButtonGroup.add(moonlightThemeChoice);
		themeButtonGroup.add(darkThemeChoice);
		themeButtonGroup.add(monokaiproThemeChoice);
		themeButtonGroup.add(purpleThemeChoice);
		themeButtonGroup.add(carbonThemeChoice);
		themeButtonGroup.add(samLaf);
		themeButtonGroup.add(bellLaf);
		themeButtonGroup.add(noirLightLaf);
		themeButtonGroup.add(noirDarkLaf);
		
		//this will load the currently applied theme from the ini file and will invoke the setSelected method for the currently applied JRadioButtonMenuItem theme button
		ThemeLoader.notifyCurrentThemeUsage(darkThemeChoice, gruvboxThemeChoice, moonlightThemeChoice, monokaiproThemeChoice, purpleThemeChoice, carbonThemeChoice, samLaf, bellLaf, noirLightLaf, noirDarkLaf);
		//options menu
		JMenu optionsMenu = new JMenu("Options");
		menuBar.add(optionsMenu);
		
		JMenuItem Screenshot = new JMenuItem("Screenshot");
		Screenshot.setIcon(new FlatSVGIcon(FerrumX.class.getResource("/resources/menu_icons/screenshot.svg")));
		Screenshot.addActionListener(e->ComponentImageCapture.getScreenshot(mainFrame, tabbedPane.getTitleAt(tabbedPane.getSelectedIndex())));
		optionsMenu.add(Screenshot);

		//help menu
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);

		JMenuItem about = new JMenuItem("About");
		about.setIcon(new FlatSVGIcon(FerrumX.class.getResource("/resources/menu_icons/about.svg")));
		about.addActionListener(e -> new AboutUI().setVisible(true));
		helpMenu.add(about);

		JMenuItem updateCheck = new JMenuItem("Check For New Releases");
		updateCheck.setIcon(new FlatSVGIcon(FerrumX.class.getResource("/resources/menu_icons/release.svg")));
		updateCheck.addActionListener(e -> {
			ConfirmationUI confirm = new ConfirmationUI("Github Releases","This will open a new browser window. Continue ?");
			confirm.getBtnYes().addActionListener(e1->{
				try {
					Desktop.getDesktop().browse(new URI(appLatestReleasePage));
					confirm.dispose();
				} catch (URISyntaxException | IOException ex) {
					new ExceptionUI("Github Release Page Visit Error", ex.getMessage()).setVisible(true);
					confirm.dispose();
				}
			});
			
			confirm.getBtnNo().addActionListener(e1->confirm.dispose());
		});
		helpMenu.add(updateCheck);
	}

	private void initializeCpuPanel(JTabbedPane tabbedPane, JPanel hwidCpuPanel) {
		
		tabbedPane.addTab("CPU", new FlatSVGIcon(FerrumX.class.getResource("/resources/tab_icons_small/CPU.svg")),
				hwidCpuPanel, "Displays CPU Information");
		tabbedPane.setEnabledAt(0, true);
		GridBagLayout gbl_hwidCpuPanel = new GridBagLayout();
		gbl_hwidCpuPanel.columnWidths = new int[] { 0, 0 };
		gbl_hwidCpuPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_hwidCpuPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_hwidCpuPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		hwidCpuPanel.setLayout(gbl_hwidCpuPanel);

		JPanel hwidPanel = new JPanel();
		hwidPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "HardwareID",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbchwidPanel = new GridBagConstraints();
		gbchwidPanel.insets = new Insets(0, 0, 5, 0);
		gbchwidPanel.fill = GridBagConstraints.BOTH;
		gbchwidPanel.gridx = 0;
		gbchwidPanel.gridy = 0;
		hwidCpuPanel.add(hwidPanel, gbchwidPanel);
		GridBagLayout gbl_hwidPanel = new GridBagLayout();
		gbl_hwidPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_hwidPanel.rowHeights = new int[] { 0, 0 };
		gbl_hwidPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_hwidPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		hwidPanel.setLayout(gbl_hwidPanel);

		hwidTf = new JTextField();
		hwidTf.setEditable(false);
		GridBagConstraints gbc_hwidTf = new GridBagConstraints();
		gbc_hwidTf.gridwidth = 2;
		gbc_hwidTf.insets = new Insets(0, 0, 0, 5);
		gbc_hwidTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_hwidTf.gridx = 0;
		gbc_hwidTf.gridy = 0;
		hwidPanel.add(hwidTf, gbc_hwidTf);
		hwidTf.setColumns(10);

		JButton copyHwidButton = new JButton();
		copyHwidButton.setToolTipText("Copy HWID to Clipboard");
		copyHwidButton.setIcon(new FlatSVGIcon(FerrumX.class.getResource("/resources/extra_icons/copy.svg")));
		copyHwidButton.addActionListener(e -> {
			hwidTf.selectAll();
			hwidTf.copy();
		});
		GridBagConstraints gbc_copyHwidButton = new GridBagConstraints();
		gbc_copyHwidButton.gridx = 2;
		gbc_copyHwidButton.gridy = 0;
		hwidPanel.add(copyHwidButton, gbc_copyHwidButton);

		JPanel cpuPanel = new JPanel();
		cpuPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "CPU",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbccpuPanel = new GridBagConstraints();
		gbccpuPanel.fill = GridBagConstraints.BOTH;
		gbccpuPanel.gridx = 0;
		gbccpuPanel.gridy = 1;
		hwidCpuPanel.add(cpuPanel, gbccpuPanel);
		GridBagLayout gbl_cpuPanel = new GridBagLayout();
		gbl_cpuPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_cpuPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_cpuPanel.columnWeights = new double[] { 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_cpuPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		cpuPanel.setLayout(gbl_cpuPanel);

		JPanel essentialInfoPanel = new JPanel();
		essentialInfoPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Primary Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_essentialInfoPanel = new GridBagConstraints();
		gbc_essentialInfoPanel.fill = GridBagConstraints.BOTH;
		gbc_essentialInfoPanel.gridwidth = 3;
		gbc_essentialInfoPanel.insets = new Insets(0, 0, 5, 0);
		gbc_essentialInfoPanel.gridx = 0;
		gbc_essentialInfoPanel.gridy = 0;
		cpuPanel.add(essentialInfoPanel, gbc_essentialInfoPanel);
		GridBagLayout gbl_essentialInfoPanel = new GridBagLayout();
		gbl_essentialInfoPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_essentialInfoPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_essentialInfoPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_essentialInfoPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		essentialInfoPanel.setLayout(gbl_essentialInfoPanel);

		JLabel cpuCount = new JLabel("CPU#");
		GridBagConstraints gbc_cpuCount = new GridBagConstraints();
		gbc_cpuCount.insets = new Insets(0, 0, 5, 5);
		gbc_cpuCount.gridx = 0;
		gbc_cpuCount.gridy = 0;
		essentialInfoPanel.add(cpuCount, gbc_cpuCount);

		cpuChoice = new JComboBox<>();
		GridBagConstraints gbc_cpuChoice = new GridBagConstraints();
		gbc_cpuChoice.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuChoice.insets = new Insets(0, 0, 5, 5);
		gbc_cpuChoice.gridx = 1;
		gbc_cpuChoice.gridy = 0;
		essentialInfoPanel.add(cpuChoice, gbc_cpuChoice);
		cpuChoice.setEditable(false);

		JLabel cpuName = new JLabel("Name");
		GridBagConstraints gbc_cpuName = new GridBagConstraints();
		gbc_cpuName.insets = new Insets(0, 0, 5, 5);
		gbc_cpuName.gridx = 2;
		gbc_cpuName.gridy = 0;
		essentialInfoPanel.add(cpuName, gbc_cpuName);

		cpuNameTf = new JTextField();
		GridBagConstraints gbc_cpuNameTf = new GridBagConstraints();
		gbc_cpuNameTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuNameTf.gridwidth = 3;
		gbc_cpuNameTf.insets = new Insets(0, 0, 5, 0);
		gbc_cpuNameTf.gridx = 3;
		gbc_cpuNameTf.gridy = 0;
		essentialInfoPanel.add(cpuNameTf, gbc_cpuNameTf);
		cpuNameTf.setEditable(false);
		cpuNameTf.setColumns(10);

		JLabel cpuCores = new JLabel("Cores");
		GridBagConstraints gbc_cpuCores = new GridBagConstraints();
		gbc_cpuCores.insets = new Insets(0, 0, 5, 5);
		gbc_cpuCores.gridx = 0;
		gbc_cpuCores.gridy = 1;
		essentialInfoPanel.add(cpuCores, gbc_cpuCores);

		cpuCoreTf = new JTextField();
		GridBagConstraints gbc_cpuCoreTf = new GridBagConstraints();
		gbc_cpuCoreTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuCoreTf.insets = new Insets(0, 0, 5, 5);
		gbc_cpuCoreTf.gridx = 1;
		gbc_cpuCoreTf.gridy = 1;
		essentialInfoPanel.add(cpuCoreTf, gbc_cpuCoreTf);
		cpuCoreTf.setEditable(false);
		cpuCoreTf.setColumns(10);

		JLabel cpuThreads = new JLabel("Threads");
		GridBagConstraints gbc_cpuThreads = new GridBagConstraints();
		gbc_cpuThreads.insets = new Insets(0, 0, 5, 5);
		gbc_cpuThreads.gridx = 2;
		gbc_cpuThreads.gridy = 1;
		essentialInfoPanel.add(cpuThreads, gbc_cpuThreads);

		cpuThreadTf = new JTextField();
		GridBagConstraints gbc_cpuThreadTf = new GridBagConstraints();
		gbc_cpuThreadTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuThreadTf.insets = new Insets(0, 0, 5, 5);
		gbc_cpuThreadTf.gridx = 3;
		gbc_cpuThreadTf.gridy = 1;
		essentialInfoPanel.add(cpuThreadTf, gbc_cpuThreadTf);
		cpuThreadTf.setEditable(false);
		cpuThreadTf.setColumns(10);

		JLabel cpuLogicalProcessors = new JLabel("Logical Processors");
		GridBagConstraints gbc_cpuLogicalProcessors = new GridBagConstraints();
		gbc_cpuLogicalProcessors.insets = new Insets(0, 0, 5, 5);
		gbc_cpuLogicalProcessors.gridx = 4;
		gbc_cpuLogicalProcessors.gridy = 1;
		essentialInfoPanel.add(cpuLogicalProcessors, gbc_cpuLogicalProcessors);

		cpuLogicProcessorTf = new JTextField();
		GridBagConstraints gbc_cpuLogicProcessorTf = new GridBagConstraints();
		gbc_cpuLogicProcessorTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuLogicProcessorTf.insets = new Insets(0, 0, 5, 0);
		gbc_cpuLogicProcessorTf.gridx = 5;
		gbc_cpuLogicProcessorTf.gridy = 1;
		essentialInfoPanel.add(cpuLogicProcessorTf, gbc_cpuLogicProcessorTf);
		cpuLogicProcessorTf.setEditable(false);
		cpuLogicProcessorTf.setColumns(10);

		JLabel addressWidth = new JLabel("Address Width");
		GridBagConstraints gbc_addressWidth = new GridBagConstraints();
		gbc_addressWidth.insets = new Insets(0, 0, 0, 5);
		gbc_addressWidth.gridx = 0;
		gbc_addressWidth.gridy = 2;
		essentialInfoPanel.add(addressWidth, gbc_addressWidth);

		addressWidthTf = new JTextField();
		GridBagConstraints gbc_addressWidthTf = new GridBagConstraints();
		gbc_addressWidthTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_addressWidthTf.insets = new Insets(0, 0, 0, 5);
		gbc_addressWidthTf.gridx = 1;
		gbc_addressWidthTf.gridy = 2;
		essentialInfoPanel.add(addressWidthTf, gbc_addressWidthTf);
		addressWidthTf.setEditable(false);
		addressWidthTf.setColumns(10);

		JLabel cpuSocket = new JLabel("Socket");
		GridBagConstraints gbc_cpuSocket = new GridBagConstraints();
		gbc_cpuSocket.insets = new Insets(0, 0, 0, 5);
		gbc_cpuSocket.gridx = 2;
		gbc_cpuSocket.gridy = 2;
		essentialInfoPanel.add(cpuSocket, gbc_cpuSocket);

		cpuSocketTf = new JTextField();
		GridBagConstraints gbc_cpuSocketTf = new GridBagConstraints();
		gbc_cpuSocketTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuSocketTf.insets = new Insets(0, 0, 0, 5);
		gbc_cpuSocketTf.gridx = 3;
		gbc_cpuSocketTf.gridy = 2;
		essentialInfoPanel.add(cpuSocketTf, gbc_cpuSocketTf);
		cpuSocketTf.setEditable(false);
		cpuSocketTf.setColumns(10);

		JLabel cpuManufacturer = new JLabel("Manufacturer");
		GridBagConstraints gbc_cpuManufacturer = new GridBagConstraints();
		gbc_cpuManufacturer.insets = new Insets(0, 0, 0, 5);
		gbc_cpuManufacturer.gridx = 4;
		gbc_cpuManufacturer.gridy = 2;
		essentialInfoPanel.add(cpuManufacturer, gbc_cpuManufacturer);

		cpuManufacturerTf = new JTextField();
		GridBagConstraints gbc_cpuManufacturerTf = new GridBagConstraints();
		gbc_cpuManufacturerTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuManufacturerTf.gridx = 5;
		gbc_cpuManufacturerTf.gridy = 2;
		essentialInfoPanel.add(cpuManufacturerTf, gbc_cpuManufacturerTf);
		cpuManufacturerTf.setEditable(false);
		cpuManufacturerTf.setColumns(10);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridwidth = 3;
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		cpuPanel.add(separator, gbc_separator);

		JPanel clockPanel = new JPanel();
		clockPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Clock Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_clockPanel = new GridBagConstraints();
		gbc_clockPanel.fill = GridBagConstraints.BOTH;
		gbc_clockPanel.gridwidth = 3;
		gbc_clockPanel.insets = new Insets(0, 0, 5, 0);
		gbc_clockPanel.gridx = 0;
		gbc_clockPanel.gridy = 2;
		cpuPanel.add(clockPanel, gbc_clockPanel);
		GridBagLayout gbl_clockPanel = new GridBagLayout();
		gbl_clockPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_clockPanel.rowHeights = new int[] { 0, 0 };
		gbl_clockPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_clockPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		clockPanel.setLayout(gbl_clockPanel);

		JLabel cpuExtClock = new JLabel("BCLK");
		GridBagConstraints gbc_cpuExtClock = new GridBagConstraints();
		gbc_cpuExtClock.insets = new Insets(0, 0, 0, 5);
		gbc_cpuExtClock.gridx = 0;
		gbc_cpuExtClock.gridy = 0;
		clockPanel.add(cpuExtClock, gbc_cpuExtClock);

		cpuBaseClockTf = new JTextField();
		GridBagConstraints gbc_cpuBaseClockTf = new GridBagConstraints();
		gbc_cpuBaseClockTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuBaseClockTf.insets = new Insets(0, 0, 0, 5);
		gbc_cpuBaseClockTf.gridx = 1;
		gbc_cpuBaseClockTf.gridy = 0;
		clockPanel.add(cpuBaseClockTf, gbc_cpuBaseClockTf);
		cpuBaseClockTf.setEditable(false);
		cpuBaseClockTf.setColumns(10);

		JLabel cpuMultiplier = new JLabel("Multiplier");
		GridBagConstraints gbc_cpuMultiplier = new GridBagConstraints();
		gbc_cpuMultiplier.insets = new Insets(0, 0, 0, 5);
		gbc_cpuMultiplier.gridx = 2;
		gbc_cpuMultiplier.gridy = 0;
		clockPanel.add(cpuMultiplier, gbc_cpuMultiplier);

		multiplierTf = new JTextField();
		GridBagConstraints gbc_multiplierTf = new GridBagConstraints();
		gbc_multiplierTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_multiplierTf.insets = new Insets(0, 0, 0, 5);
		gbc_multiplierTf.gridx = 3;
		gbc_multiplierTf.gridy = 0;
		clockPanel.add(multiplierTf, gbc_multiplierTf);
		multiplierTf.setEditable(false);
		multiplierTf.setColumns(10);

		JLabel cpuEffectiveClock = new JLabel("Effective Base Clock");
		GridBagConstraints gbc_cpuEffectiveClock = new GridBagConstraints();
		gbc_cpuEffectiveClock.insets = new Insets(0, 0, 0, 5);
		gbc_cpuEffectiveClock.gridx = 4;
		gbc_cpuEffectiveClock.gridy = 0;
		clockPanel.add(cpuEffectiveClock, gbc_cpuEffectiveClock);

		effectiveClockTf = new JTextField();
		GridBagConstraints gbc_effectiveClockTf = new GridBagConstraints();
		gbc_effectiveClockTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_effectiveClockTf.gridx = 5;
		gbc_effectiveClockTf.gridy = 0;
		clockPanel.add(effectiveClockTf, gbc_effectiveClockTf);
		effectiveClockTf.setEditable(false);
		effectiveClockTf.setColumns(10);

		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.gridwidth = 3;
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 3;
		cpuPanel.add(separator_1, gbc_separator_1);

		JPanel familyPanel = new JPanel();
		familyPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Family Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_familyPanel = new GridBagConstraints();
		gbc_familyPanel.fill = GridBagConstraints.BOTH;
		gbc_familyPanel.gridwidth = 3;
		gbc_familyPanel.insets = new Insets(0, 0, 5, 0);
		gbc_familyPanel.gridx = 0;
		gbc_familyPanel.gridy = 4;
		cpuPanel.add(familyPanel, gbc_familyPanel);
		GridBagLayout gbl_familyPanel = new GridBagLayout();
		gbl_familyPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_familyPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_familyPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_familyPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		familyPanel.setLayout(gbl_familyPanel);

		JLabel cpuVersion = new JLabel("Version");
		GridBagConstraints gbc_cpuVersion = new GridBagConstraints();
		gbc_cpuVersion.insets = new Insets(0, 0, 5, 5);
		gbc_cpuVersion.gridx = 0;
		gbc_cpuVersion.gridy = 0;
		familyPanel.add(cpuVersion, gbc_cpuVersion);

		cpuVersionTf = new JTextField();
		GridBagConstraints gbc_cpuVersionTf = new GridBagConstraints();
		gbc_cpuVersionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuVersionTf.insets = new Insets(0, 0, 5, 5);
		gbc_cpuVersionTf.gridx = 1;
		gbc_cpuVersionTf.gridy = 0;
		familyPanel.add(cpuVersionTf, gbc_cpuVersionTf);
		cpuVersionTf.setEditable(false);
		cpuVersionTf.setColumns(10);

		JLabel cpuFamily = new JLabel("Family");
		GridBagConstraints gbc_cpuFamily = new GridBagConstraints();
		gbc_cpuFamily.insets = new Insets(0, 0, 5, 5);
		gbc_cpuFamily.gridx = 2;
		gbc_cpuFamily.gridy = 0;
		familyPanel.add(cpuFamily, gbc_cpuFamily);

		cpuFamilyTf = new JTextField();
		GridBagConstraints gbc_cpuFamilyTf = new GridBagConstraints();
		gbc_cpuFamilyTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuFamilyTf.insets = new Insets(0, 0, 5, 5);
		gbc_cpuFamilyTf.gridx = 3;
		gbc_cpuFamilyTf.gridy = 0;
		familyPanel.add(cpuFamilyTf, gbc_cpuFamilyTf);
		cpuFamilyTf.setEditable(false);
		cpuFamilyTf.setColumns(10);

		JLabel cpuVirtStatus = new JLabel("Virtualization");
		GridBagConstraints gbc_cpuVirtStatus = new GridBagConstraints();
		gbc_cpuVirtStatus.insets = new Insets(0, 0, 5, 5);
		gbc_cpuVirtStatus.gridx = 4;
		gbc_cpuVirtStatus.gridy = 0;
		familyPanel.add(cpuVirtStatus, gbc_cpuVirtStatus);

		cpuVirtStatusTf = new JTextField();
		GridBagConstraints gbc_cpuVirtStatusTf = new GridBagConstraints();
		gbc_cpuVirtStatusTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuVirtStatusTf.insets = new Insets(0, 0, 5, 0);
		gbc_cpuVirtStatusTf.gridx = 5;
		gbc_cpuVirtStatusTf.gridy = 0;
		familyPanel.add(cpuVirtStatusTf, gbc_cpuVirtStatusTf);
		cpuVirtStatusTf.setEditable(false);
		cpuVirtStatusTf.setColumns(10);

		JLabel cpuCaption = new JLabel("Caption");
		GridBagConstraints gbc_cpuCaption = new GridBagConstraints();
		gbc_cpuCaption.insets = new Insets(0, 0, 0, 5);
		gbc_cpuCaption.gridx = 0;
		gbc_cpuCaption.gridy = 1;
		familyPanel.add(cpuCaption, gbc_cpuCaption);

		cpuCaptionTf = new JTextField();
		GridBagConstraints gbc_cpuCaptionTf = new GridBagConstraints();
		gbc_cpuCaptionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuCaptionTf.insets = new Insets(0, 0, 0, 5);
		gbc_cpuCaptionTf.gridx = 1;
		gbc_cpuCaptionTf.gridy = 1;
		familyPanel.add(cpuCaptionTf, gbc_cpuCaptionTf);
		cpuCaptionTf.setEditable(false);
		cpuCaptionTf.setColumns(10);

		JLabel cpuStepping = new JLabel("Stepping");
		GridBagConstraints gbc_cpuStepping = new GridBagConstraints();
		gbc_cpuStepping.insets = new Insets(0, 0, 0, 5);
		gbc_cpuStepping.gridx = 2;
		gbc_cpuStepping.gridy = 1;
		familyPanel.add(cpuStepping, gbc_cpuStepping);

		cpuSteppingTf = new JTextField();
		GridBagConstraints gbc_cpuSteppingTf = new GridBagConstraints();
		gbc_cpuSteppingTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuSteppingTf.insets = new Insets(0, 0, 0, 5);
		gbc_cpuSteppingTf.gridx = 3;
		gbc_cpuSteppingTf.gridy = 1;
		familyPanel.add(cpuSteppingTf, gbc_cpuSteppingTf);
		cpuSteppingTf.setEditable(false);
		cpuSteppingTf.setColumns(10);

		JLabel cpuId = new JLabel("ProcessorID");
		GridBagConstraints gbc_cpuId = new GridBagConstraints();
		gbc_cpuId.insets = new Insets(0, 0, 0, 5);
		gbc_cpuId.gridx = 4;
		gbc_cpuId.gridy = 1;
		familyPanel.add(cpuId, gbc_cpuId);

		cpuIdTf = new JTextField();
		GridBagConstraints gbc_cpuIdTf = new GridBagConstraints();
		gbc_cpuIdTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuIdTf.gridx = 5;
		gbc_cpuIdTf.gridy = 1;
		familyPanel.add(cpuIdTf, gbc_cpuIdTf);
		cpuIdTf.setEditable(false);
		cpuIdTf.setColumns(10);

		JSeparator separator_2 = new JSeparator();
		GridBagConstraints gbc_separator_2 = new GridBagConstraints();
		gbc_separator_2.insets = new Insets(0, 0, 5, 0);
		gbc_separator_2.gridwidth = 3;
		gbc_separator_2.gridx = 0;
		gbc_separator_2.gridy = 5;
		cpuPanel.add(separator_2, gbc_separator_2);

		JPanel miniCachePanel = new JPanel();
		miniCachePanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Cache",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_miniCachePanel = new GridBagConstraints();
		gbc_miniCachePanel.fill = GridBagConstraints.BOTH;
		gbc_miniCachePanel.insets = new Insets(0, 0, 0, 5);
		gbc_miniCachePanel.gridx = 0;
		gbc_miniCachePanel.gridy = 6;
		cpuPanel.add(miniCachePanel, gbc_miniCachePanel);
		GridBagLayout gbl_miniCachePanel = new GridBagLayout();
		gbl_miniCachePanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_miniCachePanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_miniCachePanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_miniCachePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		miniCachePanel.setLayout(gbl_miniCachePanel);

		JLabel cpuLevelTwoCache = new JLabel("L2 Cache");
		GridBagConstraints gbc_cpuLevelTwoCache = new GridBagConstraints();
		gbc_cpuLevelTwoCache.anchor = GridBagConstraints.EAST;
		gbc_cpuLevelTwoCache.insets = new Insets(0, 0, 5, 5);
		gbc_cpuLevelTwoCache.gridx = 0;
		gbc_cpuLevelTwoCache.gridy = 0;
		miniCachePanel.add(cpuLevelTwoCache, gbc_cpuLevelTwoCache);

		cpuL2Tf = new JTextField();
		GridBagConstraints gbc_cpuL2Tf = new GridBagConstraints();
		gbc_cpuL2Tf.anchor = GridBagConstraints.SOUTH;
		gbc_cpuL2Tf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuL2Tf.insets = new Insets(0, 0, 5, 0);
		gbc_cpuL2Tf.gridx = 1;
		gbc_cpuL2Tf.gridy = 0;
		miniCachePanel.add(cpuL2Tf, gbc_cpuL2Tf);
		cpuL2Tf.setEditable(false);
		cpuL2Tf.setColumns(10);

		JLabel cpuLevelThreeCache = new JLabel("L3 Cache");
		GridBagConstraints gbc_cpuLevelThreeCache = new GridBagConstraints();
		gbc_cpuLevelThreeCache.anchor = GridBagConstraints.EAST;
		gbc_cpuLevelThreeCache.fill = GridBagConstraints.VERTICAL;
		gbc_cpuLevelThreeCache.insets = new Insets(0, 0, 5, 5);
		gbc_cpuLevelThreeCache.gridx = 0;
		gbc_cpuLevelThreeCache.gridy = 2;
		miniCachePanel.add(cpuLevelThreeCache, gbc_cpuLevelThreeCache);
		cpuLevelThreeCache.setHorizontalAlignment(SwingConstants.CENTER);

		cpuL3Tf = new JTextField();
		GridBagConstraints gbc_cpuL3Tf = new GridBagConstraints();
		gbc_cpuL3Tf.fill = GridBagConstraints.HORIZONTAL;
		gbc_cpuL3Tf.insets = new Insets(0, 0, 5, 0);
		gbc_cpuL3Tf.gridx = 1;
		gbc_cpuL3Tf.gridy = 2;
		miniCachePanel.add(cpuL3Tf, gbc_cpuL3Tf);
		cpuL3Tf.setEditable(false);
		cpuL3Tf.setColumns(10);

		JPanel cpuLogoPanel = new JPanel();
		cpuLogoPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"CPU Manufacturer Logo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_cpuLogoPanel = new GridBagConstraints();
		gbc_cpuLogoPanel.insets = new Insets(0, 0, 0, 5);
		gbc_cpuLogoPanel.fill = GridBagConstraints.BOTH;
		gbc_cpuLogoPanel.gridx = 1;
		gbc_cpuLogoPanel.gridy = 6;
		cpuPanel.add(cpuLogoPanel, gbc_cpuLogoPanel);
		cpuLogoPanel.setLayout(new GridLayout(0, 1, 0, 0));

		cpuLogo = new JLabel("");
		cpuLogo.setHorizontalAlignment(SwingConstants.CENTER);
		cpuLogoPanel.add(cpuLogo);

		JPanel detailedCachePanel = new JPanel();
		detailedCachePanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Extra Cache Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_detailedCachePanel = new GridBagConstraints();
		gbc_detailedCachePanel.fill = GridBagConstraints.BOTH;
		gbc_detailedCachePanel.gridx = 2;
		gbc_detailedCachePanel.gridy = 6;
		cpuPanel.add(detailedCachePanel, gbc_detailedCachePanel);
		detailedCachePanel.setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane cacheScrollPane = new JScrollPane();
		detailedCachePanel.add(cacheScrollPane);

		cacheTa = new JTextArea();
		cacheScrollPane.setViewportView(cacheTa);
		cacheTa.setEditable(false);
	}

	private void initializeMemoryPanel(JTabbedPane tabbedPane, JPanel memoryPanel) {
		memoryPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Memory",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab("Memory", new FlatSVGIcon(FerrumX.class.getResource("/resources/tab_icons_small/RAM.svg")),
				memoryPanel, "Displays Memory Information");
		GridBagLayout gbl_memoryPanel = new GridBagLayout();
		gbl_memoryPanel.columnWidths = new int[] { 0, 0 };
		gbl_memoryPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_memoryPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_memoryPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		memoryPanel.setLayout(gbl_memoryPanel);

		JPanel memoryIdentifierPanel = new JPanel();
		memoryIdentifierPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Identifier", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_memoryIdentifierPanel = new GridBagConstraints();
		gbc_memoryIdentifierPanel.fill = GridBagConstraints.BOTH;
		gbc_memoryIdentifierPanel.insets = new Insets(0, 0, 5, 0);
		gbc_memoryIdentifierPanel.gridx = 0;
		gbc_memoryIdentifierPanel.gridy = 0;
		memoryPanel.add(memoryIdentifierPanel, gbc_memoryIdentifierPanel);
		GridBagLayout gbl_memoryIdentifierPanel = new GridBagLayout();
		gbl_memoryIdentifierPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_memoryIdentifierPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_memoryIdentifierPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_memoryIdentifierPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		memoryIdentifierPanel.setLayout(gbl_memoryIdentifierPanel);

		JLabel memorySlot = new JLabel("Slot#");
		GridBagConstraints gbc_memorySlot = new GridBagConstraints();
		gbc_memorySlot.anchor = GridBagConstraints.EAST;
		gbc_memorySlot.insets = new Insets(0, 0, 5, 5);
		gbc_memorySlot.gridx = 0;
		gbc_memorySlot.gridy = 0;
		memoryIdentifierPanel.add(memorySlot, gbc_memorySlot);

		memorySlotChoice = new JComboBox<>();
		GridBagConstraints gbc_memorySlotChoice = new GridBagConstraints();
		gbc_memorySlotChoice.fill = GridBagConstraints.HORIZONTAL;
		gbc_memorySlotChoice.insets = new Insets(0, 0, 5, 0);
		gbc_memorySlotChoice.gridx = 1;
		gbc_memorySlotChoice.gridy = 0;
		memoryIdentifierPanel.add(memorySlotChoice, gbc_memorySlotChoice);

		JLabel memoryName = new JLabel("Name");
		GridBagConstraints gbc_memoryName = new GridBagConstraints();
		gbc_memoryName.anchor = GridBagConstraints.EAST;
		gbc_memoryName.insets = new Insets(0, 0, 5, 5);
		gbc_memoryName.gridx = 0;
		gbc_memoryName.gridy = 1;
		memoryIdentifierPanel.add(memoryName, gbc_memoryName);

		memoryNameTf = new JTextField();
		GridBagConstraints gbc_memoryNameTf = new GridBagConstraints();
		gbc_memoryNameTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memoryNameTf.insets = new Insets(0, 0, 5, 0);
		gbc_memoryNameTf.gridx = 1;
		gbc_memoryNameTf.gridy = 1;
		memoryIdentifierPanel.add(memoryNameTf, gbc_memoryNameTf);
		memoryNameTf.setEditable(false);
		memoryNameTf.setColumns(10);

		JLabel memoryManufacturer = new JLabel("Manufacturer");
		GridBagConstraints gbc_memoryManufacturer = new GridBagConstraints();
		gbc_memoryManufacturer.anchor = GridBagConstraints.EAST;
		gbc_memoryManufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_memoryManufacturer.gridx = 0;
		gbc_memoryManufacturer.gridy = 2;
		memoryIdentifierPanel.add(memoryManufacturer, gbc_memoryManufacturer);

		memoryManufacturerTf = new JTextField();
		GridBagConstraints gbc_memoryManufacturerTf = new GridBagConstraints();
		gbc_memoryManufacturerTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memoryManufacturerTf.insets = new Insets(0, 0, 5, 0);
		gbc_memoryManufacturerTf.gridx = 1;
		gbc_memoryManufacturerTf.gridy = 2;
		memoryIdentifierPanel.add(memoryManufacturerTf, gbc_memoryManufacturerTf);
		memoryManufacturerTf.setEditable(false);
		memoryManufacturerTf.setColumns(10);

		JLabel memoryModel = new JLabel("Model");
		GridBagConstraints gbc_memoryModel = new GridBagConstraints();
		gbc_memoryModel.anchor = GridBagConstraints.EAST;
		gbc_memoryModel.insets = new Insets(0, 0, 5, 5);
		gbc_memoryModel.gridx = 0;
		gbc_memoryModel.gridy = 3;
		memoryIdentifierPanel.add(memoryModel, gbc_memoryModel);

		memoryModelTf = new JTextField();
		GridBagConstraints gbc_memoryModelTf = new GridBagConstraints();
		gbc_memoryModelTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memoryModelTf.insets = new Insets(0, 0, 5, 0);
		gbc_memoryModelTf.gridx = 1;
		gbc_memoryModelTf.gridy = 3;
		memoryIdentifierPanel.add(memoryModelTf, gbc_memoryModelTf);
		memoryModelTf.setEditable(false);
		memoryModelTf.setColumns(10);

		JLabel memoryOtherInfo = new JLabel("Other Identifying Info");
		GridBagConstraints gbc_memoryOtherInfo = new GridBagConstraints();
		gbc_memoryOtherInfo.anchor = GridBagConstraints.EAST;
		gbc_memoryOtherInfo.insets = new Insets(0, 0, 5, 5);
		gbc_memoryOtherInfo.gridx = 0;
		gbc_memoryOtherInfo.gridy = 4;
		memoryIdentifierPanel.add(memoryOtherInfo, gbc_memoryOtherInfo);

		memoryOtherInfoTf = new JTextField();
		GridBagConstraints gbc_memoryOtherInfoTf = new GridBagConstraints();
		gbc_memoryOtherInfoTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memoryOtherInfoTf.insets = new Insets(0, 0, 5, 0);
		gbc_memoryOtherInfoTf.gridx = 1;
		gbc_memoryOtherInfoTf.gridy = 4;
		memoryIdentifierPanel.add(memoryOtherInfoTf, gbc_memoryOtherInfoTf);
		memoryOtherInfoTf.setEditable(false);
		memoryOtherInfoTf.setColumns(10);

		JLabel memoryPartNumber = new JLabel("Part Number");
		GridBagConstraints gbc_memoryPartNumber = new GridBagConstraints();
		gbc_memoryPartNumber.anchor = GridBagConstraints.EAST;
		gbc_memoryPartNumber.insets = new Insets(0, 0, 5, 5);
		gbc_memoryPartNumber.gridx = 0;
		gbc_memoryPartNumber.gridy = 5;
		memoryIdentifierPanel.add(memoryPartNumber, gbc_memoryPartNumber);

		memoryPartNumberTf = new JTextField();
		GridBagConstraints gbc_memoryPartNumberTf = new GridBagConstraints();
		gbc_memoryPartNumberTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memoryPartNumberTf.insets = new Insets(0, 0, 5, 0);
		gbc_memoryPartNumberTf.gridx = 1;
		gbc_memoryPartNumberTf.gridy = 5;
		memoryIdentifierPanel.add(memoryPartNumberTf, gbc_memoryPartNumberTf);
		memoryPartNumberTf.setEditable(false);
		memoryPartNumberTf.setColumns(10);

		JLabel memorySerialNumber = new JLabel("Serial Number");
		GridBagConstraints gbc_memorySerialNumber = new GridBagConstraints();
		gbc_memorySerialNumber.anchor = GridBagConstraints.EAST;
		gbc_memorySerialNumber.insets = new Insets(0, 0, 5, 5);
		gbc_memorySerialNumber.gridx = 0;
		gbc_memorySerialNumber.gridy = 6;
		memoryIdentifierPanel.add(memorySerialNumber, gbc_memorySerialNumber);

		memorySerialNumberTf = new JTextField();
		GridBagConstraints gbc_memorySerialNumberTf = new GridBagConstraints();
		gbc_memorySerialNumberTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memorySerialNumberTf.insets = new Insets(0, 0, 5, 0);
		gbc_memorySerialNumberTf.gridx = 1;
		gbc_memorySerialNumberTf.gridy = 6;
		memoryIdentifierPanel.add(memorySerialNumberTf, gbc_memorySerialNumberTf);
		memorySerialNumberTf.setEditable(false);
		memorySerialNumberTf.setColumns(10);

		JLabel memoryFormFactor = new JLabel("Form Factor");
		GridBagConstraints gbc_memoryFormFactor = new GridBagConstraints();
		gbc_memoryFormFactor.anchor = GridBagConstraints.EAST;
		gbc_memoryFormFactor.insets = new Insets(0, 0, 0, 5);
		gbc_memoryFormFactor.gridx = 0;
		gbc_memoryFormFactor.gridy = 7;
		memoryIdentifierPanel.add(memoryFormFactor, gbc_memoryFormFactor);

		memoryFormFactorTf = new JTextField();
		GridBagConstraints gbc_memoryFormFactorTf = new GridBagConstraints();
		gbc_memoryFormFactorTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memoryFormFactorTf.gridx = 1;
		gbc_memoryFormFactorTf.gridy = 7;
		memoryIdentifierPanel.add(memoryFormFactorTf, gbc_memoryFormFactorTf);
		memoryFormFactorTf.setEditable(false);
		memoryFormFactorTf.setColumns(10);

		JPanel memoryPropertyPanel = new JPanel();
		memoryPropertyPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Location, Capacity and Speed", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_memoryPropertyPanel = new GridBagConstraints();
		gbc_memoryPropertyPanel.fill = GridBagConstraints.BOTH;
		gbc_memoryPropertyPanel.gridx = 0;
		gbc_memoryPropertyPanel.gridy = 1;
		memoryPanel.add(memoryPropertyPanel, gbc_memoryPropertyPanel);
		GridBagLayout gbl_memoryPropertyPanel = new GridBagLayout();
		gbl_memoryPropertyPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_memoryPropertyPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_memoryPropertyPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_memoryPropertyPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		memoryPropertyPanel.setLayout(gbl_memoryPropertyPanel);

		JLabel memoryBankLabel = new JLabel("Bank Label");
		GridBagConstraints gbc_memoryBankLabel = new GridBagConstraints();
		gbc_memoryBankLabel.anchor = GridBagConstraints.EAST;
		gbc_memoryBankLabel.insets = new Insets(0, 0, 5, 5);
		gbc_memoryBankLabel.gridx = 0;
		gbc_memoryBankLabel.gridy = 0;
		memoryPropertyPanel.add(memoryBankLabel, gbc_memoryBankLabel);

		memoryBankLabelTf = new JTextField();
		GridBagConstraints gbc_memoryBankLabelTf = new GridBagConstraints();
		gbc_memoryBankLabelTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memoryBankLabelTf.insets = new Insets(0, 0, 5, 0);
		gbc_memoryBankLabelTf.gridx = 1;
		gbc_memoryBankLabelTf.gridy = 0;
		memoryPropertyPanel.add(memoryBankLabelTf, gbc_memoryBankLabelTf);
		memoryBankLabelTf.setEditable(false);
		memoryBankLabelTf.setColumns(10);

		JLabel memoryCapacity = new JLabel("Capacity");
		GridBagConstraints gbc_memoryCapacity = new GridBagConstraints();
		gbc_memoryCapacity.anchor = GridBagConstraints.EAST;
		gbc_memoryCapacity.insets = new Insets(0, 0, 5, 5);
		gbc_memoryCapacity.gridx = 0;
		gbc_memoryCapacity.gridy = 1;
		memoryPropertyPanel.add(memoryCapacity, gbc_memoryCapacity);

		memoryCapacityTf = new JTextField();
		GridBagConstraints gbc_memoryCapacityTf = new GridBagConstraints();
		gbc_memoryCapacityTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memoryCapacityTf.insets = new Insets(0, 0, 5, 0);
		gbc_memoryCapacityTf.gridx = 1;
		gbc_memoryCapacityTf.gridy = 1;
		memoryPropertyPanel.add(memoryCapacityTf, gbc_memoryCapacityTf);
		memoryCapacityTf.setEditable(false);
		memoryCapacityTf.setColumns(10);

		JLabel memoryWidth = new JLabel("Data Width");
		GridBagConstraints gbc_memoryWidth = new GridBagConstraints();
		gbc_memoryWidth.anchor = GridBagConstraints.EAST;
		gbc_memoryWidth.insets = new Insets(0, 0, 5, 5);
		gbc_memoryWidth.gridx = 0;
		gbc_memoryWidth.gridy = 2;
		memoryPropertyPanel.add(memoryWidth, gbc_memoryWidth);

		memoryWidthTf = new JTextField();
		GridBagConstraints gbc_memoryWidthTf = new GridBagConstraints();
		gbc_memoryWidthTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memoryWidthTf.insets = new Insets(0, 0, 5, 0);
		gbc_memoryWidthTf.gridx = 1;
		gbc_memoryWidthTf.gridy = 2;
		memoryPropertyPanel.add(memoryWidthTf, gbc_memoryWidthTf);
		memoryWidthTf.setEditable(false);
		memoryWidthTf.setColumns(10);

		JLabel memorySpeed = new JLabel("Speed");
		GridBagConstraints gbc_memorySpeed = new GridBagConstraints();
		gbc_memorySpeed.anchor = GridBagConstraints.EAST;
		gbc_memorySpeed.insets = new Insets(0, 0, 5, 5);
		gbc_memorySpeed.gridx = 0;
		gbc_memorySpeed.gridy = 3;
		memoryPropertyPanel.add(memorySpeed, gbc_memorySpeed);

		memorySpeedTf = new JTextField();
		GridBagConstraints gbc_memorySpeedTf = new GridBagConstraints();
		gbc_memorySpeedTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memorySpeedTf.insets = new Insets(0, 0, 5, 0);
		gbc_memorySpeedTf.gridx = 1;
		gbc_memorySpeedTf.gridy = 3;
		memoryPropertyPanel.add(memorySpeedTf, gbc_memorySpeedTf);
		memorySpeedTf.setEditable(false);
		memorySpeedTf.setColumns(10);

		JLabel memoryConfigClockSpeed = new JLabel("Configured Clock Speed");
		GridBagConstraints gbc_memoryConfigClockSpeed = new GridBagConstraints();
		gbc_memoryConfigClockSpeed.anchor = GridBagConstraints.EAST;
		gbc_memoryConfigClockSpeed.insets = new Insets(0, 0, 5, 5);
		gbc_memoryConfigClockSpeed.gridx = 0;
		gbc_memoryConfigClockSpeed.gridy = 4;
		memoryPropertyPanel.add(memoryConfigClockSpeed, gbc_memoryConfigClockSpeed);

		memoryConfigClockSpeedTf = new JTextField();
		GridBagConstraints gbc_memoryConfigClockSpeedTf = new GridBagConstraints();
		gbc_memoryConfigClockSpeedTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memoryConfigClockSpeedTf.insets = new Insets(0, 0, 5, 0);
		gbc_memoryConfigClockSpeedTf.gridx = 1;
		gbc_memoryConfigClockSpeedTf.gridy = 4;
		memoryPropertyPanel.add(memoryConfigClockSpeedTf, gbc_memoryConfigClockSpeedTf);
		memoryConfigClockSpeedTf.setEditable(false);
		memoryConfigClockSpeedTf.setColumns(10);

		JLabel memoryLocator = new JLabel("Device Locator");
		GridBagConstraints gbc_memoryLocator = new GridBagConstraints();
		gbc_memoryLocator.anchor = GridBagConstraints.EAST;
		gbc_memoryLocator.insets = new Insets(0, 0, 0, 5);
		gbc_memoryLocator.gridx = 0;
		gbc_memoryLocator.gridy = 5;
		memoryPropertyPanel.add(memoryLocator, gbc_memoryLocator);

		memoryLocatorTf = new JTextField();
		GridBagConstraints gbc_memoryLocatorTf = new GridBagConstraints();
		gbc_memoryLocatorTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_memoryLocatorTf.gridx = 1;
		gbc_memoryLocatorTf.gridy = 5;
		memoryPropertyPanel.add(memoryLocatorTf, gbc_memoryLocatorTf);
		memoryLocatorTf.setEditable(false);
		memoryLocatorTf.setColumns(10);
	}

	private void initializeMainboardPanel(JTabbedPane tabbedPane, JPanel mainBoardPanel) {
		tabbedPane.addTab("Mainboard",
				new FlatSVGIcon(FerrumX.class.getResource("/resources/tab_icons_small/MainBoard.svg")), mainBoardPanel,
				"Displays Motherboard and BIOS Info");
		tabbedPane.setEnabledAt(2, true);
		GridBagLayout gbl_mainBoardPanel = new GridBagLayout();
		gbl_mainBoardPanel.columnWidths = new int[] { 0, 0 };
		gbl_mainBoardPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_mainBoardPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_mainBoardPanel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		mainBoardPanel.setLayout(gbl_mainBoardPanel);

		JPanel mainboardPanel = new JPanel();
		mainboardPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Mainboard", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_mainboardPanel = new GridBagConstraints();
		gbc_mainboardPanel.insets = new Insets(0, 0, 5, 0);
		gbc_mainboardPanel.fill = GridBagConstraints.BOTH;
		gbc_mainboardPanel.gridx = 0;
		gbc_mainboardPanel.gridy = 0;
		mainBoardPanel.add(mainboardPanel, gbc_mainboardPanel);
		GridBagLayout gbl_mainboardPanel = new GridBagLayout();
		gbl_mainboardPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_mainboardPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_mainboardPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_mainboardPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		mainboardPanel.setLayout(gbl_mainboardPanel);

		JLabel mbManufacturer = new JLabel("Manufacturer");
		GridBagConstraints gbc_mbManufacturer = new GridBagConstraints();
		gbc_mbManufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_mbManufacturer.anchor = GridBagConstraints.EAST;
		gbc_mbManufacturer.gridx = 0;
		gbc_mbManufacturer.gridy = 0;
		mainboardPanel.add(mbManufacturer, gbc_mbManufacturer);

		mbManufacturerTf = new JTextField();
		mbManufacturerTf.setEditable(false);
		GridBagConstraints gbc_mbManufacturerTf = new GridBagConstraints();
		gbc_mbManufacturerTf.insets = new Insets(0, 0, 5, 0);
		gbc_mbManufacturerTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_mbManufacturerTf.gridx = 1;
		gbc_mbManufacturerTf.gridy = 0;
		mainboardPanel.add(mbManufacturerTf, gbc_mbManufacturerTf);
		mbManufacturerTf.setColumns(10);

		JLabel mbModel = new JLabel("Model");
		GridBagConstraints gbc_mbModel = new GridBagConstraints();
		gbc_mbModel.anchor = GridBagConstraints.EAST;
		gbc_mbModel.insets = new Insets(0, 0, 5, 5);
		gbc_mbModel.gridx = 0;
		gbc_mbModel.gridy = 1;
		mainboardPanel.add(mbModel, gbc_mbModel);

		mbModelTf = new JTextField();
		mbModelTf.setEditable(false);
		GridBagConstraints gbc_mbModelTf = new GridBagConstraints();
		gbc_mbModelTf.insets = new Insets(0, 0, 5, 0);
		gbc_mbModelTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_mbModelTf.gridx = 1;
		gbc_mbModelTf.gridy = 1;
		mainboardPanel.add(mbModelTf, gbc_mbModelTf);
		mbModelTf.setColumns(10);

		JLabel mbProduct = new JLabel("Product");
		GridBagConstraints gbc_mbProduct = new GridBagConstraints();
		gbc_mbProduct.insets = new Insets(0, 0, 5, 5);
		gbc_mbProduct.anchor = GridBagConstraints.EAST;
		gbc_mbProduct.gridx = 0;
		gbc_mbProduct.gridy = 2;
		mainboardPanel.add(mbProduct, gbc_mbProduct);

		mbProductTf = new JTextField();
		mbProductTf.setEditable(false);
		GridBagConstraints gbc_mbProductTf = new GridBagConstraints();
		gbc_mbProductTf.insets = new Insets(0, 0, 5, 0);
		gbc_mbProductTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_mbProductTf.gridx = 1;
		gbc_mbProductTf.gridy = 2;
		mainboardPanel.add(mbProductTf, gbc_mbProductTf);
		mbProductTf.setColumns(10);

		JLabel mbSerialNumber = new JLabel("Serial Number");
		GridBagConstraints gbc_mbSerialNumber = new GridBagConstraints();
		gbc_mbSerialNumber.anchor = GridBagConstraints.EAST;
		gbc_mbSerialNumber.insets = new Insets(0, 0, 5, 5);
		gbc_mbSerialNumber.gridx = 0;
		gbc_mbSerialNumber.gridy = 3;
		mainboardPanel.add(mbSerialNumber, gbc_mbSerialNumber);

		mbSerialNumberTf = new JTextField();
		mbSerialNumberTf.setEditable(false);
		GridBagConstraints gbc_mbSerialNumberTf = new GridBagConstraints();
		gbc_mbSerialNumberTf.insets = new Insets(0, 0, 5, 0);
		gbc_mbSerialNumberTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_mbSerialNumberTf.gridx = 1;
		gbc_mbSerialNumberTf.gridy = 3;
		mainboardPanel.add(mbSerialNumberTf, gbc_mbSerialNumberTf);
		mbSerialNumberTf.setColumns(10);

		JLabel mbVersion = new JLabel("Version");
		GridBagConstraints gbc_mbVersion = new GridBagConstraints();
		gbc_mbVersion.anchor = GridBagConstraints.EAST;
		gbc_mbVersion.insets = new Insets(0, 0, 5, 5);
		gbc_mbVersion.gridx = 0;
		gbc_mbVersion.gridy = 4;
		mainboardPanel.add(mbVersion, gbc_mbVersion);

		mbVersionTf = new JTextField();
		mbVersionTf.setEditable(false);
		GridBagConstraints gbc_mbVersionTf = new GridBagConstraints();
		gbc_mbVersionTf.insets = new Insets(0, 0, 5, 0);
		gbc_mbVersionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_mbVersionTf.gridx = 1;
		gbc_mbVersionTf.gridy = 4;
		mainboardPanel.add(mbVersionTf, gbc_mbVersionTf);
		mbVersionTf.setColumns(10);

		JLabel mbPnp = new JLabel("PNP Device ID");
		GridBagConstraints gbc_mbPnp = new GridBagConstraints();
		gbc_mbPnp.anchor = GridBagConstraints.EAST;
		gbc_mbPnp.insets = new Insets(0, 0, 0, 5);
		gbc_mbPnp.gridx = 0;
		gbc_mbPnp.gridy = 5;
		mainboardPanel.add(mbPnp, gbc_mbPnp);

		mbPnpTf = new JTextField();
		mbPnpTf.setEditable(false);
		GridBagConstraints gbc_mbPnpTf = new GridBagConstraints();
		gbc_mbPnpTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_mbPnpTf.gridx = 1;
		gbc_mbPnpTf.gridy = 5;
		mainboardPanel.add(mbPnpTf, gbc_mbPnpTf);
		mbPnpTf.setColumns(10);

		JPanel biosPanel = new JPanel();
		biosPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Mainboard BIOS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_biosPanel = new GridBagConstraints();
		gbc_biosPanel.fill = GridBagConstraints.BOTH;
		gbc_biosPanel.gridx = 0;
		gbc_biosPanel.gridy = 1;
		mainBoardPanel.add(biosPanel, gbc_biosPanel);
		GridBagLayout gbl_biosPanel = new GridBagLayout();
		gbl_biosPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_biosPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_biosPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_biosPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		biosPanel.setLayout(gbl_biosPanel);

		JLabel biosName = new JLabel("Name");
		GridBagConstraints gbc_biosName = new GridBagConstraints();
		gbc_biosName.anchor = GridBagConstraints.EAST;
		gbc_biosName.insets = new Insets(0, 0, 5, 5);
		gbc_biosName.gridx = 0;
		gbc_biosName.gridy = 0;
		biosPanel.add(biosName, gbc_biosName);

		biosNameTf = new JTextField();
		biosNameTf.setEditable(false);
		GridBagConstraints gbc_biosNameTf = new GridBagConstraints();
		gbc_biosNameTf.insets = new Insets(0, 0, 5, 0);
		gbc_biosNameTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_biosNameTf.gridx = 1;
		gbc_biosNameTf.gridy = 0;
		biosPanel.add(biosNameTf, gbc_biosNameTf);
		biosNameTf.setColumns(10);

		JLabel biosCaption = new JLabel("Caption");
		GridBagConstraints gbc_biosCaption = new GridBagConstraints();
		gbc_biosCaption.anchor = GridBagConstraints.EAST;
		gbc_biosCaption.insets = new Insets(0, 0, 5, 5);
		gbc_biosCaption.gridx = 0;
		gbc_biosCaption.gridy = 1;
		biosPanel.add(biosCaption, gbc_biosCaption);

		biosCaptionTf = new JTextField();
		biosCaptionTf.setEditable(false);
		GridBagConstraints gbc_biosCaptionTf = new GridBagConstraints();
		gbc_biosCaptionTf.insets = new Insets(0, 0, 5, 0);
		gbc_biosCaptionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_biosCaptionTf.gridx = 1;
		gbc_biosCaptionTf.gridy = 1;
		biosPanel.add(biosCaptionTf, gbc_biosCaptionTf);
		biosCaptionTf.setColumns(10);

		JLabel biosManufacturer = new JLabel("Manufacturer");
		GridBagConstraints gbc_biosManufacturer = new GridBagConstraints();
		gbc_biosManufacturer.anchor = GridBagConstraints.EAST;
		gbc_biosManufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_biosManufacturer.gridx = 0;
		gbc_biosManufacturer.gridy = 2;
		biosPanel.add(biosManufacturer, gbc_biosManufacturer);

		biosManufacturerTf = new JTextField();
		biosManufacturerTf.setEditable(false);
		GridBagConstraints gbc_biosManufacturerTf = new GridBagConstraints();
		gbc_biosManufacturerTf.insets = new Insets(0, 0, 5, 0);
		gbc_biosManufacturerTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_biosManufacturerTf.gridx = 1;
		gbc_biosManufacturerTf.gridy = 2;
		biosPanel.add(biosManufacturerTf, gbc_biosManufacturerTf);
		biosManufacturerTf.setColumns(10);

		JLabel biosRelease = new JLabel("Release Date");
		GridBagConstraints gbc_biosRelease = new GridBagConstraints();
		gbc_biosRelease.anchor = GridBagConstraints.EAST;
		gbc_biosRelease.insets = new Insets(0, 0, 5, 5);
		gbc_biosRelease.gridx = 0;
		gbc_biosRelease.gridy = 3;
		biosPanel.add(biosRelease, gbc_biosRelease);

		biosReleaseDateTf = new JTextField();
		biosReleaseDateTf.setEditable(false);
		GridBagConstraints gbc_biosReleaseDateTf = new GridBagConstraints();
		gbc_biosReleaseDateTf.insets = new Insets(0, 0, 5, 0);
		gbc_biosReleaseDateTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_biosReleaseDateTf.gridx = 1;
		gbc_biosReleaseDateTf.gridy = 3;
		biosPanel.add(biosReleaseDateTf, gbc_biosReleaseDateTf);
		biosReleaseDateTf.setColumns(10);

		JLabel biosVersion = new JLabel("Version");
		GridBagConstraints gbc_biosVersion = new GridBagConstraints();
		gbc_biosVersion.anchor = GridBagConstraints.EAST;
		gbc_biosVersion.insets = new Insets(0, 0, 5, 5);
		gbc_biosVersion.gridx = 0;
		gbc_biosVersion.gridy = 4;
		biosPanel.add(biosVersion, gbc_biosVersion);

		biosVersionTf = new JTextField();
		biosVersionTf.setEditable(false);
		GridBagConstraints gbc_biosVersionTf = new GridBagConstraints();
		gbc_biosVersionTf.insets = new Insets(0, 0, 5, 0);
		gbc_biosVersionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_biosVersionTf.gridx = 1;
		gbc_biosVersionTf.gridy = 4;
		biosPanel.add(biosVersionTf, gbc_biosVersionTf);
		biosVersionTf.setColumns(10);

		JLabel biosStatus = new JLabel("Status");
		GridBagConstraints gbc_biosStatus = new GridBagConstraints();
		gbc_biosStatus.anchor = GridBagConstraints.EAST;
		gbc_biosStatus.insets = new Insets(0, 0, 5, 5);
		gbc_biosStatus.gridx = 0;
		gbc_biosStatus.gridy = 5;
		biosPanel.add(biosStatus, gbc_biosStatus);

		biosStatusTf = new JTextField();
		biosStatusTf.setEditable(false);
		GridBagConstraints gbc_biosStatusTf = new GridBagConstraints();
		gbc_biosStatusTf.insets = new Insets(0, 0, 5, 0);
		gbc_biosStatusTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_biosStatusTf.gridx = 1;
		gbc_biosStatusTf.gridy = 5;
		biosPanel.add(biosStatusTf, gbc_biosStatusTf);
		biosStatusTf.setColumns(10);

		JLabel smbiosPresence = new JLabel("SMBIOS Present");
		GridBagConstraints gbc_smbiosPresence = new GridBagConstraints();
		gbc_smbiosPresence.anchor = GridBagConstraints.EAST;
		gbc_smbiosPresence.insets = new Insets(0, 0, 5, 5);
		gbc_smbiosPresence.gridx = 0;
		gbc_smbiosPresence.gridy = 6;
		biosPanel.add(smbiosPresence, gbc_smbiosPresence);

		smbiosPresenceTf = new JTextField();
		smbiosPresenceTf.setEditable(false);
		GridBagConstraints gbc_smbiosPresenceTf = new GridBagConstraints();
		gbc_smbiosPresenceTf.insets = new Insets(0, 0, 5, 0);
		gbc_smbiosPresenceTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_smbiosPresenceTf.gridx = 1;
		gbc_smbiosPresenceTf.gridy = 6;
		biosPanel.add(smbiosPresenceTf, gbc_smbiosPresenceTf);
		smbiosPresenceTf.setColumns(10);

		JLabel smbiosVersion = new JLabel("SMBIOS Version");
		GridBagConstraints gbc_smbiosVersion = new GridBagConstraints();
		gbc_smbiosVersion.anchor = GridBagConstraints.EAST;
		gbc_smbiosVersion.insets = new Insets(0, 0, 5, 5);
		gbc_smbiosVersion.gridx = 0;
		gbc_smbiosVersion.gridy = 7;
		biosPanel.add(smbiosVersion, gbc_smbiosVersion);

		smbiosVersionTf = new JTextField();
		smbiosVersionTf.setEditable(false);
		GridBagConstraints gbc_smbiosVersionTf = new GridBagConstraints();
		gbc_smbiosVersionTf.insets = new Insets(0, 0, 5, 0);
		gbc_smbiosVersionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_smbiosVersionTf.gridx = 1;
		gbc_smbiosVersionTf.gridy = 7;
		biosPanel.add(smbiosVersionTf, gbc_smbiosVersionTf);
		smbiosVersionTf.setColumns(10);

		JLabel biosLanguage = new JLabel("Current Language");
		GridBagConstraints gbc_biosLanguage = new GridBagConstraints();
		gbc_biosLanguage.anchor = GridBagConstraints.EAST;
		gbc_biosLanguage.insets = new Insets(0, 0, 0, 5);
		gbc_biosLanguage.gridx = 0;
		gbc_biosLanguage.gridy = 8;
		biosPanel.add(biosLanguage, gbc_biosLanguage);

		biosLanguageTf = new JTextField();
		biosLanguageTf.setEditable(false);
		GridBagConstraints gbc_biosLanguageTf = new GridBagConstraints();
		gbc_biosLanguageTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_biosLanguageTf.gridx = 1;
		gbc_biosLanguageTf.gridy = 8;
		biosPanel.add(biosLanguageTf, gbc_biosLanguageTf);
		biosLanguageTf.setColumns(10);
	}

	private void initializeGpuPanel(JTabbedPane tabbedPane, JPanel gpuPanel) {
		gpuPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Video Controller", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab("GPU", new FlatSVGIcon(FerrumX.class.getResource("/resources/tab_icons_medium/GPU.svg")),
				gpuPanel, "Displays basic information about your GPUs");
		GridBagLayout gbl_gpuPanel = new GridBagLayout();
		gbl_gpuPanel.columnWidths = new int[] { 0, 0 };
		gbl_gpuPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_gpuPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_gpuPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gpuPanel.setLayout(gbl_gpuPanel);

		JPanel gpuIdentifierPanel = new JPanel();
		gpuIdentifierPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Identifier", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_gpuIdentifierPanel = new GridBagConstraints();
		gbc_gpuIdentifierPanel.fill = GridBagConstraints.BOTH;
		gbc_gpuIdentifierPanel.insets = new Insets(0, 0, 5, 0);
		gbc_gpuIdentifierPanel.gridx = 0;
		gbc_gpuIdentifierPanel.gridy = 0;
		gpuPanel.add(gpuIdentifierPanel, gbc_gpuIdentifierPanel);
		GridBagLayout gbl_gpuIdentifierPanel = new GridBagLayout();
		gbl_gpuIdentifierPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_gpuIdentifierPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_gpuIdentifierPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		gbl_gpuIdentifierPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gpuIdentifierPanel.setLayout(gbl_gpuIdentifierPanel);

		JLabel gpuChoice = new JLabel("GPU#");
		GridBagConstraints gbc_gpuChoice = new GridBagConstraints();
		gbc_gpuChoice.anchor = GridBagConstraints.EAST;
		gbc_gpuChoice.insets = new Insets(0, 0, 5, 5);
		gbc_gpuChoice.gridx = 0;
		gbc_gpuChoice.gridy = 0;
		gpuIdentifierPanel.add(gpuChoice, gbc_gpuChoice);

		gpuChoiceComboBox = new JComboBox<>();
		GridBagConstraints gbc_gpuChoiceComboBox = new GridBagConstraints();
		gbc_gpuChoiceComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuChoiceComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_gpuChoiceComboBox.gridx = 1;
		gbc_gpuChoiceComboBox.gridy = 0;
		gpuIdentifierPanel.add(gpuChoiceComboBox, gbc_gpuChoiceComboBox);

		JLabel gpuName = new JLabel("Name");
		GridBagConstraints gbc_gpuName = new GridBagConstraints();
		gbc_gpuName.anchor = GridBagConstraints.EAST;
		gbc_gpuName.insets = new Insets(0, 0, 5, 5);
		gbc_gpuName.gridx = 0;
		gbc_gpuName.gridy = 1;
		gpuIdentifierPanel.add(gpuName, gbc_gpuName);

		gpuNameTf = new JTextField();
		GridBagConstraints gbc_gpuNameTf = new GridBagConstraints();
		gbc_gpuNameTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuNameTf.insets = new Insets(0, 0, 5, 5);
		gbc_gpuNameTf.gridx = 1;
		gbc_gpuNameTf.gridy = 1;
		gpuIdentifierPanel.add(gpuNameTf, gbc_gpuNameTf);
		gpuNameTf.setEditable(false);
		gpuNameTf.setColumns(10);
		
		JPanel gpuManufacturerLogoPanel = new JPanel();
		gpuManufacturerLogoPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "GPU Manufacturer", TitledBorder.RIGHT, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_gpuManufacturerLogoPanel = new GridBagConstraints();
		gbc_gpuManufacturerLogoPanel.gridheight = 3;
		gbc_gpuManufacturerLogoPanel.insets = new Insets(0, 0, 5, 0);
		gbc_gpuManufacturerLogoPanel.fill = GridBagConstraints.BOTH;
		gbc_gpuManufacturerLogoPanel.gridx = 2;
		gbc_gpuManufacturerLogoPanel.gridy = 0;
		gpuIdentifierPanel.add(gpuManufacturerLogoPanel, gbc_gpuManufacturerLogoPanel);
		gpuManufacturerLogoPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		gpuLogo = new JLabel("");
		gpuLogo.setHorizontalAlignment(SwingConstants.CENTER);
		gpuManufacturerLogoPanel.add(gpuLogo);

		JLabel gpuPnpId = new JLabel("PNP Device ID");
		GridBagConstraints gbc_gpuPnpId = new GridBagConstraints();
		gbc_gpuPnpId.anchor = GridBagConstraints.EAST;
		gbc_gpuPnpId.insets = new Insets(0, 0, 0, 5);
		gbc_gpuPnpId.gridx = 0;
		gbc_gpuPnpId.gridy = 2;
		gpuIdentifierPanel.add(gpuPnpId, gbc_gpuPnpId);

		gpuPnpTf = new JTextField();
		GridBagConstraints gbc_gpuPnpTf = new GridBagConstraints();
		gbc_gpuPnpTf.insets = new Insets(0, 0, 0, 5);
		gbc_gpuPnpTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuPnpTf.gridx = 1;
		gbc_gpuPnpTf.gridy = 2;
		gpuIdentifierPanel.add(gpuPnpTf, gbc_gpuPnpTf);
		gpuPnpTf.setEditable(false);
		gpuPnpTf.setColumns(10);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		gpuPanel.add(separator, gbc_separator);

		JPanel displayInfoPanel = new JPanel();
		displayInfoPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Display Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_displayInfoPanel = new GridBagConstraints();
		gbc_displayInfoPanel.fill = GridBagConstraints.BOTH;
		gbc_displayInfoPanel.insets = new Insets(0, 0, 5, 0);
		gbc_displayInfoPanel.gridx = 0;
		gbc_displayInfoPanel.gridy = 2;
		gpuPanel.add(displayInfoPanel, gbc_displayInfoPanel);
		GridBagLayout gbl_displayInfoPanel = new GridBagLayout();
		gbl_displayInfoPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_displayInfoPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_displayInfoPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_displayInfoPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		displayInfoPanel.setLayout(gbl_displayInfoPanel);

		JLabel gpuHorizontal = new JLabel("Horizontal Res");
		GridBagConstraints gbc_gpuHorizontal = new GridBagConstraints();
		gbc_gpuHorizontal.anchor = GridBagConstraints.EAST;
		gbc_gpuHorizontal.insets = new Insets(0, 0, 5, 5);
		gbc_gpuHorizontal.gridx = 0;
		gbc_gpuHorizontal.gridy = 0;
		displayInfoPanel.add(gpuHorizontal, gbc_gpuHorizontal);

		gpuHorizontalResTf = new JTextField();
		GridBagConstraints gbc_gpuHorizontalResTf = new GridBagConstraints();
		gbc_gpuHorizontalResTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuHorizontalResTf.insets = new Insets(0, 0, 5, 0);
		gbc_gpuHorizontalResTf.gridx = 1;
		gbc_gpuHorizontalResTf.gridy = 0;
		displayInfoPanel.add(gpuHorizontalResTf, gbc_gpuHorizontalResTf);
		gpuHorizontalResTf.setEditable(false);
		gpuHorizontalResTf.setColumns(10);

		JLabel gpuVertical = new JLabel("Vertical Res");
		GridBagConstraints gbc_gpuVertical = new GridBagConstraints();
		gbc_gpuVertical.anchor = GridBagConstraints.EAST;
		gbc_gpuVertical.insets = new Insets(0, 0, 5, 5);
		gbc_gpuVertical.gridx = 0;
		gbc_gpuVertical.gridy = 1;
		displayInfoPanel.add(gpuVertical, gbc_gpuVertical);

		gpuVerticalResTf = new JTextField();
		GridBagConstraints gbc_gpuVerticalResTf = new GridBagConstraints();
		gbc_gpuVerticalResTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuVerticalResTf.insets = new Insets(0, 0, 5, 0);
		gbc_gpuVerticalResTf.gridx = 1;
		gbc_gpuVerticalResTf.gridy = 1;
		displayInfoPanel.add(gpuVerticalResTf, gbc_gpuVerticalResTf);
		gpuVerticalResTf.setEditable(false);
		gpuVerticalResTf.setColumns(10);

		JLabel gpuBitsPerPixel = new JLabel("Bits Per Pixel");
		GridBagConstraints gbc_gpuBitsPerPixel = new GridBagConstraints();
		gbc_gpuBitsPerPixel.anchor = GridBagConstraints.EAST;
		gbc_gpuBitsPerPixel.insets = new Insets(0, 0, 5, 5);
		gbc_gpuBitsPerPixel.gridx = 0;
		gbc_gpuBitsPerPixel.gridy = 2;
		displayInfoPanel.add(gpuBitsPerPixel, gbc_gpuBitsPerPixel);

		gpuBitsTf = new JTextField();
		GridBagConstraints gbc_gpuBitsTf = new GridBagConstraints();
		gbc_gpuBitsTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuBitsTf.insets = new Insets(0, 0, 5, 0);
		gbc_gpuBitsTf.gridx = 1;
		gbc_gpuBitsTf.gridy = 2;
		displayInfoPanel.add(gpuBitsTf, gbc_gpuBitsTf);
		gpuBitsTf.setEditable(false);
		gpuBitsTf.setColumns(10);

		JLabel gpuMinRef = new JLabel("Min Refresh Rate");
		GridBagConstraints gbc_gpuMinRef = new GridBagConstraints();
		gbc_gpuMinRef.anchor = GridBagConstraints.EAST;
		gbc_gpuMinRef.insets = new Insets(0, 0, 5, 5);
		gbc_gpuMinRef.gridx = 0;
		gbc_gpuMinRef.gridy = 3;
		displayInfoPanel.add(gpuMinRef, gbc_gpuMinRef);

		gpuMinRefTf = new JTextField();
		GridBagConstraints gbc_gpuMinRefTf = new GridBagConstraints();
		gbc_gpuMinRefTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuMinRefTf.insets = new Insets(0, 0, 5, 0);
		gbc_gpuMinRefTf.gridx = 1;
		gbc_gpuMinRefTf.gridy = 3;
		displayInfoPanel.add(gpuMinRefTf, gbc_gpuMinRefTf);
		gpuMinRefTf.setEditable(false);
		gpuMinRefTf.setColumns(10);

		JLabel gpuMaxRef = new JLabel("Max Refresh Rate");
		GridBagConstraints gbc_gpuMaxRef = new GridBagConstraints();
		gbc_gpuMaxRef.anchor = GridBagConstraints.EAST;
		gbc_gpuMaxRef.insets = new Insets(0, 0, 5, 5);
		gbc_gpuMaxRef.gridx = 0;
		gbc_gpuMaxRef.gridy = 4;
		displayInfoPanel.add(gpuMaxRef, gbc_gpuMaxRef);

		gpuMaxRefTf = new JTextField();
		GridBagConstraints gbc_gpuMaxRefTf = new GridBagConstraints();
		gbc_gpuMaxRefTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuMaxRefTf.insets = new Insets(0, 0, 5, 0);
		gbc_gpuMaxRefTf.gridx = 1;
		gbc_gpuMaxRefTf.gridy = 4;
		displayInfoPanel.add(gpuMaxRefTf, gbc_gpuMaxRefTf);
		gpuMaxRefTf.setEditable(false);
		gpuMaxRefTf.setColumns(10);

		JLabel gpuCurrentRef = new JLabel("Current Refresh Rate");
		GridBagConstraints gbc_gpuCurrentRef = new GridBagConstraints();
		gbc_gpuCurrentRef.anchor = GridBagConstraints.EAST;
		gbc_gpuCurrentRef.insets = new Insets(0, 0, 0, 5);
		gbc_gpuCurrentRef.gridx = 0;
		gbc_gpuCurrentRef.gridy = 5;
		displayInfoPanel.add(gpuCurrentRef, gbc_gpuCurrentRef);

		gpuCurrentRefTf = new JTextField();
		GridBagConstraints gbc_gpuCurrentRefTf = new GridBagConstraints();
		gbc_gpuCurrentRefTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuCurrentRefTf.gridx = 1;
		gbc_gpuCurrentRefTf.gridy = 5;
		displayInfoPanel.add(gpuCurrentRefTf, gbc_gpuCurrentRefTf);
		gpuCurrentRefTf.setEditable(false);
		gpuCurrentRefTf.setColumns(10);

		JSeparator separator_1 = new JSeparator();
		GridBagConstraints gbc_separator_1 = new GridBagConstraints();
		gbc_separator_1.insets = new Insets(0, 0, 5, 0);
		gbc_separator_1.gridx = 0;
		gbc_separator_1.gridy = 3;
		gpuPanel.add(separator_1, gbc_separator_1);

		JPanel gpuVramAndDriverPanel = new JPanel();
		gpuVramAndDriverPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"VRAM and Driver Info", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_gpuVramAndDriverPanel = new GridBagConstraints();
		gbc_gpuVramAndDriverPanel.fill = GridBagConstraints.BOTH;
		gbc_gpuVramAndDriverPanel.gridx = 0;
		gbc_gpuVramAndDriverPanel.gridy = 4;
		gpuPanel.add(gpuVramAndDriverPanel, gbc_gpuVramAndDriverPanel);
		GridBagLayout gbl_gpuVramAndDriverPanel = new GridBagLayout();
		gbl_gpuVramAndDriverPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_gpuVramAndDriverPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_gpuVramAndDriverPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_gpuVramAndDriverPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gpuVramAndDriverPanel.setLayout(gbl_gpuVramAndDriverPanel);

		JLabel gpuAdapterDACType = new JLabel("Adapter DAC Type");
		GridBagConstraints gbc_gpuAdapterDACType = new GridBagConstraints();
		gbc_gpuAdapterDACType.anchor = GridBagConstraints.EAST;
		gbc_gpuAdapterDACType.insets = new Insets(0, 0, 5, 5);
		gbc_gpuAdapterDACType.gridx = 0;
		gbc_gpuAdapterDACType.gridy = 0;
		gpuVramAndDriverPanel.add(gpuAdapterDACType, gbc_gpuAdapterDACType);

		gpuDACTf = new JTextField();
		GridBagConstraints gbc_gpuDACTf = new GridBagConstraints();
		gbc_gpuDACTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuDACTf.insets = new Insets(0, 0, 5, 0);
		gbc_gpuDACTf.gridx = 1;
		gbc_gpuDACTf.gridy = 0;
		gpuVramAndDriverPanel.add(gpuDACTf, gbc_gpuDACTf);
		gpuDACTf.setEditable(false);
		gpuDACTf.setColumns(10);

		JLabel gpuVram = new JLabel("VRAM");
		GridBagConstraints gbc_gpuVram = new GridBagConstraints();
		gbc_gpuVram.anchor = GridBagConstraints.EAST;
		gbc_gpuVram.insets = new Insets(0, 0, 5, 5);
		gbc_gpuVram.gridx = 0;
		gbc_gpuVram.gridy = 1;
		gpuVramAndDriverPanel.add(gpuVram, gbc_gpuVram);

		gpuVramTf = new JTextField();
		GridBagConstraints gbc_gpuVramTf = new GridBagConstraints();
		gbc_gpuVramTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuVramTf.insets = new Insets(0, 0, 5, 0);
		gbc_gpuVramTf.gridx = 1;
		gbc_gpuVramTf.gridy = 1;
		gpuVramAndDriverPanel.add(gpuVramTf, gbc_gpuVramTf);
		gpuVramTf.setEditable(false);
		gpuVramTf.setColumns(10);

		JLabel gpuDriverVersion = new JLabel("Driver Version");
		GridBagConstraints gbc_gpuDriverVersion = new GridBagConstraints();
		gbc_gpuDriverVersion.anchor = GridBagConstraints.EAST;
		gbc_gpuDriverVersion.insets = new Insets(0, 0, 5, 5);
		gbc_gpuDriverVersion.gridx = 0;
		gbc_gpuDriverVersion.gridy = 2;
		gpuVramAndDriverPanel.add(gpuDriverVersion, gbc_gpuDriverVersion);

		gpuDriverVersionTf = new JTextField();
		GridBagConstraints gbc_gpuDriverVersionTf = new GridBagConstraints();
		gbc_gpuDriverVersionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuDriverVersionTf.insets = new Insets(0, 0, 5, 0);
		gbc_gpuDriverVersionTf.gridx = 1;
		gbc_gpuDriverVersionTf.gridy = 2;
		gpuVramAndDriverPanel.add(gpuDriverVersionTf, gbc_gpuDriverVersionTf);
		gpuDriverVersionTf.setEditable(false);
		gpuDriverVersionTf.setColumns(10);

		JLabel gpuDriverDate = new JLabel("Driver Date");
		GridBagConstraints gbc_gpuDriverDate = new GridBagConstraints();
		gbc_gpuDriverDate.anchor = GridBagConstraints.EAST;
		gbc_gpuDriverDate.insets = new Insets(0, 0, 5, 5);
		gbc_gpuDriverDate.gridx = 0;
		gbc_gpuDriverDate.gridy = 3;
		gpuVramAndDriverPanel.add(gpuDriverDate, gbc_gpuDriverDate);

		gpuDriverDateTf = new JTextField();
		GridBagConstraints gbc_gpuDriverDateTf = new GridBagConstraints();
		gbc_gpuDriverDateTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuDriverDateTf.insets = new Insets(0, 0, 5, 0);
		gbc_gpuDriverDateTf.gridx = 1;
		gbc_gpuDriverDateTf.gridy = 3;
		gpuVramAndDriverPanel.add(gpuDriverDateTf, gbc_gpuDriverDateTf);
		gpuDriverDateTf.setEditable(false);
		gpuDriverDateTf.setColumns(10);

		JLabel gpuProcessor = new JLabel("Video Processor");
		GridBagConstraints gbc_gpuProcessor = new GridBagConstraints();
		gbc_gpuProcessor.anchor = GridBagConstraints.EAST;
		gbc_gpuProcessor.insets = new Insets(0, 0, 0, 5);
		gbc_gpuProcessor.gridx = 0;
		gbc_gpuProcessor.gridy = 4;
		gpuVramAndDriverPanel.add(gpuProcessor, gbc_gpuProcessor);

		gpuVideoProcessorTf = new JTextField();
		GridBagConstraints gbc_gpuVideoProcessorTf = new GridBagConstraints();
		gbc_gpuVideoProcessorTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_gpuVideoProcessorTf.gridx = 1;
		gbc_gpuVideoProcessorTf.gridy = 4;
		gpuVramAndDriverPanel.add(gpuVideoProcessorTf, gbc_gpuVideoProcessorTf);
		gpuVideoProcessorTf.setEditable(false);
		gpuVideoProcessorTf.setColumns(10);
	}

	private void initializeNetworkPanel(JTabbedPane tabbedPane, JPanel networkPanel) {
		tabbedPane.addTab("Network", new FlatSVGIcon(FerrumX.class.getResource("/resources/tab_icons_small/Network.svg")),
				networkPanel, "Displays network information");
		GridBagLayout gbl_networkPanel = new GridBagLayout();
		gbl_networkPanel.columnWidths = new int[] { 0, 0 };
		gbl_networkPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_networkPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_networkPanel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		networkPanel.setLayout(gbl_networkPanel);

		JPanel adapterPanel = new JPanel();
		adapterPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Adapter Properties", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_adapterPanel = new GridBagConstraints();
		gbc_adapterPanel.insets = new Insets(0, 0, 5, 0);
		gbc_adapterPanel.fill = GridBagConstraints.BOTH;
		gbc_adapterPanel.gridx = 0;
		gbc_adapterPanel.gridy = 0;
		networkPanel.add(adapterPanel, gbc_adapterPanel);
		GridBagLayout gbl_adapterPanel = new GridBagLayout();
		gbl_adapterPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_adapterPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_adapterPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_adapterPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		adapterPanel.setLayout(gbl_adapterPanel);

		JLabel netConnectionChoice = new JLabel("Connection ID");
		GridBagConstraints gbc_netConnectionChoice = new GridBagConstraints();
		gbc_netConnectionChoice.anchor = GridBagConstraints.EAST;
		gbc_netConnectionChoice.insets = new Insets(0, 0, 5, 5);
		gbc_netConnectionChoice.gridx = 0;
		gbc_netConnectionChoice.gridy = 0;
		adapterPanel.add(netConnectionChoice, gbc_netConnectionChoice);

		netConnectionChoiceBox = new JComboBox<>();
		GridBagConstraints gbc_netConnectionChoiceBox = new GridBagConstraints();
		gbc_netConnectionChoiceBox.gridwidth = 2;
		gbc_netConnectionChoiceBox.insets = new Insets(0, 0, 5, 0);
		gbc_netConnectionChoiceBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_netConnectionChoiceBox.gridx = 1;
		gbc_netConnectionChoiceBox.gridy = 0;
		adapterPanel.add(netConnectionChoiceBox, gbc_netConnectionChoiceBox);

		JLabel netName = new JLabel("Name");
		GridBagConstraints gbc_netName = new GridBagConstraints();
		gbc_netName.anchor = GridBagConstraints.EAST;
		gbc_netName.insets = new Insets(0, 0, 5, 5);
		gbc_netName.gridx = 0;
		gbc_netName.gridy = 1;
		adapterPanel.add(netName, gbc_netName);

		netNameTf = new JTextField();
		netNameTf.setEditable(false);
		GridBagConstraints gbc_netNameTf = new GridBagConstraints();
		gbc_netNameTf.gridwidth = 2;
		gbc_netNameTf.insets = new Insets(0, 0, 5, 0);
		gbc_netNameTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_netNameTf.gridx = 1;
		gbc_netNameTf.gridy = 1;
		adapterPanel.add(netNameTf, gbc_netNameTf);
		netNameTf.setColumns(10);

		JLabel netPnp = new JLabel("PNP Device ID");
		GridBagConstraints gbc_netPnp = new GridBagConstraints();
		gbc_netPnp.anchor = GridBagConstraints.EAST;
		gbc_netPnp.insets = new Insets(0, 0, 5, 5);
		gbc_netPnp.gridx = 0;
		gbc_netPnp.gridy = 2;
		adapterPanel.add(netPnp, gbc_netPnp);

		netPnpTf = new JTextField();
		netPnpTf.setEditable(false);
		GridBagConstraints gbc_netPnpTf = new GridBagConstraints();
		gbc_netPnpTf.gridwidth = 2;
		gbc_netPnpTf.insets = new Insets(0, 0, 5, 5);
		gbc_netPnpTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_netPnpTf.gridx = 1;
		gbc_netPnpTf.gridy = 2;
		adapterPanel.add(netPnpTf, gbc_netPnpTf);
		netPnpTf.setColumns(10);

		JLabel netMac = new JLabel("MAC Address");
		GridBagConstraints gbc_netMac = new GridBagConstraints();
		gbc_netMac.anchor = GridBagConstraints.EAST;
		gbc_netMac.insets = new Insets(0, 0, 5, 5);
		gbc_netMac.gridx = 0;
		gbc_netMac.gridy = 3;
		adapterPanel.add(netMac, gbc_netMac);

		netMacTf = new JTextField();
		netMacTf.setEditable(false);
		GridBagConstraints gbc_netMacTf = new GridBagConstraints();
		gbc_netMacTf.gridwidth = 2;
		gbc_netMacTf.insets = new Insets(0, 0, 5, 5);
		gbc_netMacTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_netMacTf.gridx = 1;
		gbc_netMacTf.gridy = 3;
		adapterPanel.add(netMacTf, gbc_netMacTf);
		netMacTf.setColumns(10);

		JLabel netConnectionId = new JLabel("Net Connection ID");
		GridBagConstraints gbc_netConnectionId = new GridBagConstraints();
		gbc_netConnectionId.anchor = GridBagConstraints.EAST;
		gbc_netConnectionId.insets = new Insets(0, 0, 5, 5);
		gbc_netConnectionId.gridx = 0;
		gbc_netConnectionId.gridy = 4;
		adapterPanel.add(netConnectionId, gbc_netConnectionId);

		netConnectIdTf = new JTextField();
		netConnectIdTf.setEditable(false);
		GridBagConstraints gbc_netConnectIdTf = new GridBagConstraints();
		gbc_netConnectIdTf.gridwidth = 2;
		gbc_netConnectIdTf.insets = new Insets(0, 0, 5, 5);
		gbc_netConnectIdTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_netConnectIdTf.gridx = 1;
		gbc_netConnectIdTf.gridy = 4;
		adapterPanel.add(netConnectIdTf, gbc_netConnectIdTf);
		netConnectIdTf.setColumns(10);

		JPanel connectionPanel = new JPanel();
		connectionPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Connection Properties", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_connectionPanel = new GridBagConstraints();
		gbc_connectionPanel.fill = GridBagConstraints.BOTH;
		gbc_connectionPanel.gridx = 0;
		gbc_connectionPanel.gridy = 1;
		networkPanel.add(connectionPanel, gbc_connectionPanel);
		GridBagLayout gbl_connectionPanel = new GridBagLayout();
		gbl_connectionPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_connectionPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_connectionPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_connectionPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		connectionPanel.setLayout(gbl_connectionPanel);

		JLabel netIPStatus = new JLabel("IP Enabled");
		GridBagConstraints gbc_netIPStatus = new GridBagConstraints();
		gbc_netIPStatus.anchor = GridBagConstraints.EAST;
		gbc_netIPStatus.insets = new Insets(0, 0, 5, 5);
		gbc_netIPStatus.gridx = 0;
		gbc_netIPStatus.gridy = 0;
		connectionPanel.add(netIPStatus, gbc_netIPStatus);

		netIpStatusTf = new JTextField();
		netIpStatusTf.setEditable(false);
		GridBagConstraints gbc_netIpStatusTf = new GridBagConstraints();
		gbc_netIpStatusTf.insets = new Insets(0, 0, 5, 5);
		gbc_netIpStatusTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_netIpStatusTf.gridx = 1;
		gbc_netIpStatusTf.gridy = 0;
		connectionPanel.add(netIpStatusTf, gbc_netIpStatusTf);
		netIpStatusTf.setColumns(10);

		JLabel netIPAddress = new JLabel("IP Address");
		GridBagConstraints gbc_netIPAddress = new GridBagConstraints();
		gbc_netIPAddress.anchor = GridBagConstraints.EAST;
		gbc_netIPAddress.insets = new Insets(0, 0, 5, 5);
		gbc_netIPAddress.gridx = 0;
		gbc_netIPAddress.gridy = 1;
		connectionPanel.add(netIPAddress, gbc_netIPAddress);

		netIPAddressTf = new JTextField();
		netIPAddressTf.setEditable(false);
		GridBagConstraints gbc_netIPAddressTf = new GridBagConstraints();
		gbc_netIPAddressTf.insets = new Insets(0, 0, 5, 5);
		gbc_netIPAddressTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_netIPAddressTf.gridx = 1;
		gbc_netIPAddressTf.gridy = 1;
		connectionPanel.add(netIPAddressTf, gbc_netIPAddressTf);
		netIPAddressTf.setColumns(10);

		JLabel netIPSubnet = new JLabel("IP Subnet");
		GridBagConstraints gbc_netIPSubnet = new GridBagConstraints();
		gbc_netIPSubnet.anchor = GridBagConstraints.EAST;
		gbc_netIPSubnet.insets = new Insets(0, 0, 5, 5);
		gbc_netIPSubnet.gridx = 0;
		gbc_netIPSubnet.gridy = 2;
		connectionPanel.add(netIPSubnet, gbc_netIPSubnet);

		netIPSubnetTf = new JTextField();
		netIPSubnetTf.setEditable(false);
		GridBagConstraints gbc_netIPSubnetTf = new GridBagConstraints();
		gbc_netIPSubnetTf.insets = new Insets(0, 0, 5, 5);
		gbc_netIPSubnetTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_netIPSubnetTf.gridx = 1;
		gbc_netIPSubnetTf.gridy = 2;
		connectionPanel.add(netIPSubnetTf, gbc_netIPSubnetTf);
		netIPSubnetTf.setColumns(10);

		JLabel netGateway = new JLabel("Default Gateway");
		GridBagConstraints gbc_netGateway = new GridBagConstraints();
		gbc_netGateway.anchor = GridBagConstraints.EAST;
		gbc_netGateway.insets = new Insets(0, 0, 5, 5);
		gbc_netGateway.gridx = 0;
		gbc_netGateway.gridy = 3;
		connectionPanel.add(netGateway, gbc_netGateway);

		netGatewayTf = new JTextField();
		netGatewayTf.setEditable(false);
		GridBagConstraints gbc_netGatewayTf = new GridBagConstraints();
		gbc_netGatewayTf.insets = new Insets(0, 0, 5, 5);
		gbc_netGatewayTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_netGatewayTf.gridx = 1;
		gbc_netGatewayTf.gridy = 3;
		connectionPanel.add(netGatewayTf, gbc_netGatewayTf);
		netGatewayTf.setColumns(10);

		JLabel netDHCPStatus = new JLabel("DHCP Enabled");
		GridBagConstraints gbc_netDHCPStatus = new GridBagConstraints();
		gbc_netDHCPStatus.anchor = GridBagConstraints.EAST;
		gbc_netDHCPStatus.insets = new Insets(0, 0, 5, 5);
		gbc_netDHCPStatus.gridx = 0;
		gbc_netDHCPStatus.gridy = 4;
		connectionPanel.add(netDHCPStatus, gbc_netDHCPStatus);

		netDHCPStatusTf = new JTextField();
		netDHCPStatusTf.setEditable(false);
		GridBagConstraints gbc_netDHCPStatusTf = new GridBagConstraints();
		gbc_netDHCPStatusTf.insets = new Insets(0, 0, 5, 5);
		gbc_netDHCPStatusTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_netDHCPStatusTf.gridx = 1;
		gbc_netDHCPStatusTf.gridy = 4;
		connectionPanel.add(netDHCPStatusTf, gbc_netDHCPStatusTf);
		netDHCPStatusTf.setColumns(10);

		JLabel networkLogo = new JLabel("");
		GridBagConstraints gbc_networkLogo = new GridBagConstraints();
		gbc_networkLogo.insets = new Insets(0, 0, 5, 0);
		gbc_networkLogo.gridx = 2;
		gbc_networkLogo.gridy = 4;
		connectionPanel.add(networkLogo, gbc_networkLogo);

		JLabel netDHCPServer = new JLabel("DHCP Server");
		GridBagConstraints gbc_netDHCPServer = new GridBagConstraints();
		gbc_netDHCPServer.anchor = GridBagConstraints.EAST;
		gbc_netDHCPServer.insets = new Insets(0, 0, 5, 5);
		gbc_netDHCPServer.gridx = 0;
		gbc_netDHCPServer.gridy = 5;
		connectionPanel.add(netDHCPServer, gbc_netDHCPServer);

		netDHCPServerTf = new JTextField();
		netDHCPServerTf.setEditable(false);
		GridBagConstraints gbc_netDHCPServerTf = new GridBagConstraints();
		gbc_netDHCPServerTf.insets = new Insets(0, 0, 5, 5);
		gbc_netDHCPServerTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_netDHCPServerTf.gridx = 1;
		gbc_netDHCPServerTf.gridy = 5;
		connectionPanel.add(netDHCPServerTf, gbc_netDHCPServerTf);
		netDHCPServerTf.setColumns(10);

		JLabel netDNSHost = new JLabel("DNS Host");
		GridBagConstraints gbc_netDNSHost = new GridBagConstraints();
		gbc_netDNSHost.anchor = GridBagConstraints.EAST;
		gbc_netDNSHost.insets = new Insets(0, 0, 5, 5);
		gbc_netDNSHost.gridx = 0;
		gbc_netDNSHost.gridy = 6;
		connectionPanel.add(netDNSHost, gbc_netDNSHost);

		netDNSHostTf = new JTextField();
		netDNSHostTf.setEditable(false);
		GridBagConstraints gbc_netDNSHostTf = new GridBagConstraints();
		gbc_netDNSHostTf.insets = new Insets(0, 0, 5, 5);
		gbc_netDNSHostTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_netDNSHostTf.gridx = 1;
		gbc_netDNSHostTf.gridy = 6;
		connectionPanel.add(netDNSHostTf, gbc_netDNSHostTf);
		netDNSHostTf.setColumns(10);

		JLabel netDNSServer = new JLabel("DNS Server");
		GridBagConstraints gbc_netDNSServer = new GridBagConstraints();
		gbc_netDNSServer.anchor = GridBagConstraints.EAST;
		gbc_netDNSServer.insets = new Insets(0, 0, 5, 5);
		gbc_netDNSServer.gridx = 0;
		gbc_netDNSServer.gridy = 7;
		connectionPanel.add(netDNSServer, gbc_netDNSServer);

		netDNSServerTf = new JTextField();
		netDNSServerTf.setEditable(false);
		GridBagConstraints gbc_netDNSServerTf = new GridBagConstraints();
		gbc_netDNSServerTf.insets = new Insets(0, 0, 5, 5);
		gbc_netDNSServerTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_netDNSServerTf.gridx = 1;
		gbc_netDNSServerTf.gridy = 7;
		connectionPanel.add(netDNSServerTf, gbc_netDNSServerTf);
		netDNSServerTf.setColumns(10);
	}

	private void initializeStoragePanel(JTabbedPane tabbedPane, JPanel storagePanel) {

		storagePanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Storage",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabbedPane.addTab("Storage", new FlatSVGIcon(FerrumX.class.getResource("/resources/tab_icons_small/Storage.svg")),
				storagePanel, "Displays disk information");
		GridBagLayout gbl_storagePanel = new GridBagLayout();
		gbl_storagePanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_storagePanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_storagePanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_storagePanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		storagePanel.setLayout(gbl_storagePanel);

		JLabel diskIndex = new JLabel("Index");
		GridBagConstraints gbc_diskIndex = new GridBagConstraints();
		gbc_diskIndex.insets = new Insets(0, 0, 5, 5);
		gbc_diskIndex.anchor = GridBagConstraints.EAST;
		gbc_diskIndex.gridx = 0;
		gbc_diskIndex.gridy = 0;
		storagePanel.add(diskIndex, gbc_diskIndex);

		diskIndexChoiceBox = new JComboBox<>();
		GridBagConstraints gbc_diskIndexChoiceBox = new GridBagConstraints();
		gbc_diskIndexChoiceBox.insets = new Insets(0, 0, 5, 0);
		gbc_diskIndexChoiceBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_diskIndexChoiceBox.gridx = 1;
		gbc_diskIndexChoiceBox.gridy = 0;
		storagePanel.add(diskIndexChoiceBox, gbc_diskIndexChoiceBox);

		JLabel diskCaption = new JLabel("Caption");
		GridBagConstraints gbc_diskCaption = new GridBagConstraints();
		gbc_diskCaption.anchor = GridBagConstraints.EAST;
		gbc_diskCaption.insets = new Insets(0, 0, 5, 5);
		gbc_diskCaption.gridx = 0;
		gbc_diskCaption.gridy = 1;
		storagePanel.add(diskCaption, gbc_diskCaption);

		diskCaptionTf = new JTextField();
		diskCaptionTf.setEditable(false);
		GridBagConstraints gbc_diskCaptionTf = new GridBagConstraints();
		gbc_diskCaptionTf.insets = new Insets(0, 0, 5, 0);
		gbc_diskCaptionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_diskCaptionTf.gridx = 1;
		gbc_diskCaptionTf.gridy = 1;
		storagePanel.add(diskCaptionTf, gbc_diskCaptionTf);
		diskCaptionTf.setColumns(10);

		JLabel diskModel = new JLabel("Model");
		GridBagConstraints gbc_diskModel = new GridBagConstraints();
		gbc_diskModel.anchor = GridBagConstraints.EAST;
		gbc_diskModel.insets = new Insets(0, 0, 5, 5);
		gbc_diskModel.gridx = 0;
		gbc_diskModel.gridy = 2;
		storagePanel.add(diskModel, gbc_diskModel);

		diskModelTf = new JTextField();
		diskModelTf.setEditable(false);
		GridBagConstraints gbc_diskModelTf = new GridBagConstraints();
		gbc_diskModelTf.insets = new Insets(0, 0, 5, 0);
		gbc_diskModelTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_diskModelTf.gridx = 1;
		gbc_diskModelTf.gridy = 2;
		storagePanel.add(diskModelTf, gbc_diskModelTf);
		diskModelTf.setColumns(10);

		JLabel diskSize = new JLabel("Size");
		GridBagConstraints gbc_diskSize = new GridBagConstraints();
		gbc_diskSize.anchor = GridBagConstraints.EAST;
		gbc_diskSize.insets = new Insets(0, 0, 5, 5);
		gbc_diskSize.gridx = 0;
		gbc_diskSize.gridy = 3;
		storagePanel.add(diskSize, gbc_diskSize);

		diskSizeTf = new JTextField();
		diskSizeTf.setEditable(false);
		GridBagConstraints gbc_diskSizeTf = new GridBagConstraints();
		gbc_diskSizeTf.insets = new Insets(0, 0, 5, 0);
		gbc_diskSizeTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_diskSizeTf.gridx = 1;
		gbc_diskSizeTf.gridy = 3;
		storagePanel.add(diskSizeTf, gbc_diskSizeTf);
		diskSizeTf.setColumns(10);

		JLabel diskFirmwareRev = new JLabel("Firmware Revision");
		GridBagConstraints gbc_diskFirmwareRev = new GridBagConstraints();
		gbc_diskFirmwareRev.anchor = GridBagConstraints.EAST;
		gbc_diskFirmwareRev.insets = new Insets(0, 0, 5, 5);
		gbc_diskFirmwareRev.gridx = 0;
		gbc_diskFirmwareRev.gridy = 4;
		storagePanel.add(diskFirmwareRev, gbc_diskFirmwareRev);

		firmwareRevisionTf = new JTextField();
		firmwareRevisionTf.setEditable(false);
		GridBagConstraints gbc_firmwareRevisionTf = new GridBagConstraints();
		gbc_firmwareRevisionTf.insets = new Insets(0, 0, 5, 0);
		gbc_firmwareRevisionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_firmwareRevisionTf.gridx = 1;
		gbc_firmwareRevisionTf.gridy = 4;
		storagePanel.add(firmwareRevisionTf, gbc_firmwareRevisionTf);
		firmwareRevisionTf.setColumns(10);

		JLabel diskSerialNumber = new JLabel("Serial Number");
		GridBagConstraints gbc_diskSerialNumber = new GridBagConstraints();
		gbc_diskSerialNumber.anchor = GridBagConstraints.EAST;
		gbc_diskSerialNumber.insets = new Insets(0, 0, 5, 5);
		gbc_diskSerialNumber.gridx = 0;
		gbc_diskSerialNumber.gridy = 5;
		storagePanel.add(diskSerialNumber, gbc_diskSerialNumber);

		diskSerialNumberTf = new JTextField();
		diskSerialNumberTf.setEditable(false);
		GridBagConstraints gbc_diskSerialNumberTf = new GridBagConstraints();
		gbc_diskSerialNumberTf.insets = new Insets(0, 0, 5, 0);
		gbc_diskSerialNumberTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_diskSerialNumberTf.gridx = 1;
		gbc_diskSerialNumberTf.gridy = 5;
		storagePanel.add(diskSerialNumberTf, gbc_diskSerialNumberTf);
		diskSerialNumberTf.setColumns(10);

		JLabel diskPartitionCount = new JLabel("Partitions");
		GridBagConstraints gbc_diskPartitionCount = new GridBagConstraints();
		gbc_diskPartitionCount.anchor = GridBagConstraints.EAST;
		gbc_diskPartitionCount.insets = new Insets(0, 0, 5, 5);
		gbc_diskPartitionCount.gridx = 0;
		gbc_diskPartitionCount.gridy = 6;
		storagePanel.add(diskPartitionCount, gbc_diskPartitionCount);

		diskPartitionsTf = new JTextField();
		diskPartitionsTf.setEditable(false);
		GridBagConstraints gbc_diskPartitionsTf = new GridBagConstraints();
		gbc_diskPartitionsTf.insets = new Insets(0, 0, 5, 0);
		gbc_diskPartitionsTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_diskPartitionsTf.gridx = 1;
		gbc_diskPartitionsTf.gridy = 6;
		storagePanel.add(diskPartitionsTf, gbc_diskPartitionsTf);
		diskPartitionsTf.setColumns(10);

		JLabel diskStatus = new JLabel("Status");
		GridBagConstraints gbc_diskStatus = new GridBagConstraints();
		gbc_diskStatus.anchor = GridBagConstraints.EAST;
		gbc_diskStatus.insets = new Insets(0, 0, 5, 5);
		gbc_diskStatus.gridx = 0;
		gbc_diskStatus.gridy = 7;
		storagePanel.add(diskStatus, gbc_diskStatus);

		diskStatusTf = new JTextField();
		diskStatusTf.setEditable(false);
		GridBagConstraints gbc_diskStatusTf = new GridBagConstraints();
		gbc_diskStatusTf.insets = new Insets(0, 0, 5, 0);
		gbc_diskStatusTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_diskStatusTf.gridx = 1;
		gbc_diskStatusTf.gridy = 7;
		storagePanel.add(diskStatusTf, gbc_diskStatusTf);
		diskStatusTf.setColumns(10);

		JLabel diskInterface = new JLabel("Interface Type");
		GridBagConstraints gbc_diskInterface = new GridBagConstraints();
		gbc_diskInterface.anchor = GridBagConstraints.EAST;
		gbc_diskInterface.insets = new Insets(0, 0, 5, 5);
		gbc_diskInterface.gridx = 0;
		gbc_diskInterface.gridy = 8;
		storagePanel.add(diskInterface, gbc_diskInterface);

		diskInterfaceTf = new JTextField();
		diskInterfaceTf.setEditable(false);
		GridBagConstraints gbc_diskInterfaceTf = new GridBagConstraints();
		gbc_diskInterfaceTf.insets = new Insets(0, 0, 5, 0);
		gbc_diskInterfaceTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_diskInterfaceTf.gridx = 1;
		gbc_diskInterfaceTf.gridy = 8;
		storagePanel.add(diskInterfaceTf, gbc_diskInterfaceTf);
		diskInterfaceTf.setColumns(10);

		JPanel diskPartitionPanel = new JPanel();
		diskPartitionPanel.setBorder(new TitledBorder(new LineBorder(null), "Drive Partition Layout", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_diskPartitionPanel = new GridBagConstraints();
		gbc_diskPartitionPanel.gridwidth = 2;
		gbc_diskPartitionPanel.fill = GridBagConstraints.BOTH;
		gbc_diskPartitionPanel.gridx = 0;
		gbc_diskPartitionPanel.gridy = 9;
		storagePanel.add(diskPartitionPanel, gbc_diskPartitionPanel);
		diskPartitionPanel.setLayout(new GridLayout(1, 0, 0, 0));

		diskPartTa = new JTextArea();
		diskPartTa.setEditable(false);
		JScrollPane scroll = new JScrollPane(diskPartTa);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		diskPartitionPanel.add(scroll);
	}

	private void initializeOsAndUserPanel(JTabbedPane tabbedPane, JPanel osAndUserPanel) {
		osAndUserPanel.setBorder(null);
		tabbedPane.addTab("OS", new FlatSVGIcon(FerrumX.class.getResource("/resources/tab_icons_small/OS.svg")),
				osAndUserPanel, "Displays OS, User and Timezone information");
		GridBagLayout gbl_osAndUserPanel = new GridBagLayout();
		gbl_osAndUserPanel.columnWidths = new int[] { 0, 0 };
		gbl_osAndUserPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_osAndUserPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_osAndUserPanel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		osAndUserPanel.setLayout(gbl_osAndUserPanel);

		JPanel osPanel = new JPanel();
		osPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Operating System", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_osPanel = new GridBagConstraints();
		gbc_osPanel.insets = new Insets(0, 0, 5, 0);
		gbc_osPanel.fill = GridBagConstraints.BOTH;
		gbc_osPanel.gridx = 0;
		gbc_osPanel.gridy = 0;
		osAndUserPanel.add(osPanel, gbc_osPanel);
		GridBagLayout gbl_osPanel = new GridBagLayout();
		gbl_osPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_osPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_osPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_osPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		osPanel.setLayout(gbl_osPanel);

		JLabel currentOs = new JLabel("Current OS");
		GridBagConstraints gbc_currentOs = new GridBagConstraints();
		gbc_currentOs.insets = new Insets(0, 0, 5, 5);
		gbc_currentOs.anchor = GridBagConstraints.EAST;
		gbc_currentOs.gridx = 0;
		gbc_currentOs.gridy = 0;
		osPanel.add(currentOs, gbc_currentOs);

		currentOsChoiceBox = new JComboBox<>();
		GridBagConstraints gbc_currentOsChoiceBox = new GridBagConstraints();
		gbc_currentOsChoiceBox.gridwidth = 5;
		gbc_currentOsChoiceBox.insets = new Insets(0, 0, 5, 0);
		gbc_currentOsChoiceBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_currentOsChoiceBox.gridx = 1;
		gbc_currentOsChoiceBox.gridy = 0;
		osPanel.add(currentOsChoiceBox, gbc_currentOsChoiceBox);

		JLabel osCaption = new JLabel("Caption");
		GridBagConstraints gbc_osCaption = new GridBagConstraints();
		gbc_osCaption.anchor = GridBagConstraints.EAST;
		gbc_osCaption.insets = new Insets(0, 0, 5, 5);
		gbc_osCaption.gridx = 0;
		gbc_osCaption.gridy = 1;
		osPanel.add(osCaption, gbc_osCaption);

		osCaptionTf = new JTextField();
		osCaptionTf.setEditable(false);
		GridBagConstraints gbc_osCaptionTf = new GridBagConstraints();
		gbc_osCaptionTf.gridwidth = 3;
		gbc_osCaptionTf.insets = new Insets(0, 0, 5, 5);
		gbc_osCaptionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osCaptionTf.gridx = 1;
		gbc_osCaptionTf.gridy = 1;
		osPanel.add(osCaptionTf, gbc_osCaptionTf);
		osCaptionTf.setColumns(10);

		JPanel osCoverImagePanel = new JPanel();
		osCoverImagePanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"OS Cover", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_osCoverImagePanel = new GridBagConstraints();
		gbc_osCoverImagePanel.gridwidth = 2;
		gbc_osCoverImagePanel.insets = new Insets(0, 0, 5, 0);
		gbc_osCoverImagePanel.fill = GridBagConstraints.BOTH;
		gbc_osCoverImagePanel.gridheight = 4;
		gbc_osCoverImagePanel.gridx = 4;
		gbc_osCoverImagePanel.gridy = 1;
		osPanel.add(osCoverImagePanel, gbc_osCoverImagePanel);
		osCoverImagePanel.setLayout(new GridLayout(0, 1, 0, 0));

		osCoverImg = new JLabel("");
		osCoverImagePanel.add(osCoverImg);
		osCoverImg.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel osVersion = new JLabel("Version");
		GridBagConstraints gbc_osVersion = new GridBagConstraints();
		gbc_osVersion.anchor = GridBagConstraints.EAST;
		gbc_osVersion.insets = new Insets(0, 0, 5, 5);
		gbc_osVersion.gridx = 0;
		gbc_osVersion.gridy = 2;
		osPanel.add(osVersion, gbc_osVersion);

		osVersionTf = new JTextField();
		osVersionTf.setEditable(false);
		GridBagConstraints gbc_osVersionTf = new GridBagConstraints();
		gbc_osVersionTf.insets = new Insets(0, 0, 5, 5);
		gbc_osVersionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osVersionTf.gridx = 1;
		gbc_osVersionTf.gridy = 2;
		osPanel.add(osVersionTf, gbc_osVersionTf);
		osVersionTf.setColumns(10);

		JLabel osBuildNumber = new JLabel("Build Number");
		GridBagConstraints gbc_osBuildNumber = new GridBagConstraints();
		gbc_osBuildNumber.anchor = GridBagConstraints.EAST;
		gbc_osBuildNumber.insets = new Insets(0, 0, 5, 5);
		gbc_osBuildNumber.gridx = 2;
		gbc_osBuildNumber.gridy = 2;
		osPanel.add(osBuildNumber, gbc_osBuildNumber);

		osBuildNumberTf = new JTextField();
		osBuildNumberTf.setEditable(false);
		GridBagConstraints gbc_osBuildNumberTf = new GridBagConstraints();
		gbc_osBuildNumberTf.insets = new Insets(0, 0, 5, 5);
		gbc_osBuildNumberTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osBuildNumberTf.gridx = 3;
		gbc_osBuildNumberTf.gridy = 2;
		osPanel.add(osBuildNumberTf, gbc_osBuildNumberTf);
		osBuildNumberTf.setColumns(10);

		JLabel osManufacturer = new JLabel("Manufacturer");
		GridBagConstraints gbc_osManufacturer = new GridBagConstraints();
		gbc_osManufacturer.anchor = GridBagConstraints.EAST;
		gbc_osManufacturer.insets = new Insets(0, 0, 5, 5);
		gbc_osManufacturer.gridx = 0;
		gbc_osManufacturer.gridy = 3;
		osPanel.add(osManufacturer, gbc_osManufacturer);

		osManufacturerTf = new JTextField();
		osManufacturerTf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		osManufacturerTf.setEditable(false);
		GridBagConstraints gbc_osManufacturerTf = new GridBagConstraints();
		gbc_osManufacturerTf.insets = new Insets(0, 0, 5, 5);
		gbc_osManufacturerTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osManufacturerTf.gridx = 1;
		gbc_osManufacturerTf.gridy = 3;
		osPanel.add(osManufacturerTf, gbc_osManufacturerTf);
		osManufacturerTf.setColumns(10);

		JLabel osArch = new JLabel("Architecture");
		GridBagConstraints gbc_osArch = new GridBagConstraints();
		gbc_osArch.anchor = GridBagConstraints.EAST;
		gbc_osArch.insets = new Insets(0, 0, 5, 5);
		gbc_osArch.gridx = 2;
		gbc_osArch.gridy = 3;
		osPanel.add(osArch, gbc_osArch);

		osArchitectureTf = new JTextField();
		osArchitectureTf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		osArchitectureTf.setEditable(false);
		GridBagConstraints gbc_osArchitectureTf = new GridBagConstraints();
		gbc_osArchitectureTf.insets = new Insets(0, 0, 5, 5);
		gbc_osArchitectureTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osArchitectureTf.gridx = 3;
		gbc_osArchitectureTf.gridy = 3;
		osPanel.add(osArchitectureTf, gbc_osArchitectureTf);
		osArchitectureTf.setColumns(10);

		JLabel osInstallDate = new JLabel("Install Date");
		GridBagConstraints gbc_osInstallDate = new GridBagConstraints();
		gbc_osInstallDate.anchor = GridBagConstraints.EAST;
		gbc_osInstallDate.insets = new Insets(0, 0, 5, 5);
		gbc_osInstallDate.gridx = 0;
		gbc_osInstallDate.gridy = 4;
		osPanel.add(osInstallDate, gbc_osInstallDate);

		osInstallDateTf = new JTextField();
		osInstallDateTf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		osInstallDateTf.setEditable(false);
		GridBagConstraints gbc_osInstallDateTf = new GridBagConstraints();
		gbc_osInstallDateTf.insets = new Insets(0, 0, 5, 5);
		gbc_osInstallDateTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osInstallDateTf.gridx = 1;
		gbc_osInstallDateTf.gridy = 4;
		osPanel.add(osInstallDateTf, gbc_osInstallDateTf);
		osInstallDateTf.setColumns(10);

		JLabel osLastBoot = new JLabel("Last Bootup Time");
		GridBagConstraints gbc_osLastBoot = new GridBagConstraints();
		gbc_osLastBoot.anchor = GridBagConstraints.EAST;
		gbc_osLastBoot.insets = new Insets(0, 0, 5, 5);
		gbc_osLastBoot.gridx = 2;
		gbc_osLastBoot.gridy = 4;
		osPanel.add(osLastBoot, gbc_osLastBoot);

		osLastBootTf = new JTextField();
		osLastBootTf.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		osLastBootTf.setEditable(false);
		GridBagConstraints gbc_osLastBootTf = new GridBagConstraints();
		gbc_osLastBootTf.insets = new Insets(0, 0, 5, 5);
		gbc_osLastBootTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osLastBootTf.gridx = 3;
		gbc_osLastBootTf.gridy = 4;
		osPanel.add(osLastBootTf, gbc_osLastBootTf);
		osLastBootTf.setColumns(10);

		JLabel osSerialNumber = new JLabel("Serial Number");
		GridBagConstraints gbc_osSerialNumber = new GridBagConstraints();
		gbc_osSerialNumber.anchor = GridBagConstraints.EAST;
		gbc_osSerialNumber.insets = new Insets(0, 0, 5, 5);
		gbc_osSerialNumber.gridx = 0;
		gbc_osSerialNumber.gridy = 5;
		osPanel.add(osSerialNumber, gbc_osSerialNumber);

		osSerialTf = new JTextField();
		osSerialTf.setEditable(false);
		GridBagConstraints gbc_osSerialTf = new GridBagConstraints();
		gbc_osSerialTf.gridwidth = 3;
		gbc_osSerialTf.insets = new Insets(0, 0, 5, 5);
		gbc_osSerialTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osSerialTf.gridx = 1;
		gbc_osSerialTf.gridy = 5;
		osPanel.add(osSerialTf, gbc_osSerialTf);
		osSerialTf.setColumns(10);

		JLabel osLanguage = new JLabel("Language(s)");
		GridBagConstraints gbc_osLanguage = new GridBagConstraints();
		gbc_osLanguage.anchor = GridBagConstraints.EAST;
		gbc_osLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_osLanguage.gridx = 4;
		gbc_osLanguage.gridy = 5;
		osPanel.add(osLanguage, gbc_osLanguage);

		osLangTf = new JTextField();
		osLangTf.setEditable(false);
		GridBagConstraints gbc_osLangTf = new GridBagConstraints();
		gbc_osLangTf.insets = new Insets(0, 0, 5, 0);
		gbc_osLangTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osLangTf.gridx = 5;
		gbc_osLangTf.gridy = 5;
		osPanel.add(osLangTf, gbc_osLangTf);
		osLangTf.setColumns(10);

		JLabel osPrimary = new JLabel("Primary");
		GridBagConstraints gbc_osPrimary = new GridBagConstraints();
		gbc_osPrimary.anchor = GridBagConstraints.EAST;
		gbc_osPrimary.insets = new Insets(0, 0, 5, 5);
		gbc_osPrimary.gridx = 0;
		gbc_osPrimary.gridy = 6;
		osPanel.add(osPrimary, gbc_osPrimary);

		osPrimaryTf = new JTextField();
		osPrimaryTf.setEditable(false);
		GridBagConstraints gbc_osPrimaryTf = new GridBagConstraints();
		gbc_osPrimaryTf.insets = new Insets(0, 0, 5, 5);
		gbc_osPrimaryTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osPrimaryTf.gridx = 1;
		gbc_osPrimaryTf.gridy = 6;
		osPanel.add(osPrimaryTf, gbc_osPrimaryTf);
		osPrimaryTf.setColumns(10);

		JLabel osDist = new JLabel("Distributed");
		GridBagConstraints gbc_osDist = new GridBagConstraints();
		gbc_osDist.anchor = GridBagConstraints.EAST;
		gbc_osDist.insets = new Insets(0, 0, 5, 5);
		gbc_osDist.gridx = 2;
		gbc_osDist.gridy = 6;
		osPanel.add(osDist, gbc_osDist);

		osDistributedTf = new JTextField();
		osDistributedTf.setEditable(false);
		GridBagConstraints gbc_osDistributedTf = new GridBagConstraints();
		gbc_osDistributedTf.insets = new Insets(0, 0, 5, 5);
		gbc_osDistributedTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osDistributedTf.gridx = 3;
		gbc_osDistributedTf.gridy = 6;
		osPanel.add(osDistributedTf, gbc_osDistributedTf);
		osDistributedTf.setColumns(10);

		JLabel osPort = new JLabel("Portable");
		GridBagConstraints gbc_osPort = new GridBagConstraints();
		gbc_osPort.insets = new Insets(0, 0, 5, 5);
		gbc_osPort.anchor = GridBagConstraints.EAST;
		gbc_osPort.gridx = 4;
		gbc_osPort.gridy = 6;
		osPanel.add(osPort, gbc_osPort);

		osPortTf = new JTextField();
		osPortTf.setEditable(false);
		GridBagConstraints gbc_osPortTf = new GridBagConstraints();
		gbc_osPortTf.insets = new Insets(0, 0, 5, 0);
		gbc_osPortTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osPortTf.gridx = 5;
		gbc_osPortTf.gridy = 6;
		osPanel.add(osPortTf, gbc_osPortTf);
		osPortTf.setColumns(10);

		JLabel osDeviceName = new JLabel("Device Name");
		GridBagConstraints gbc_osDeviceName = new GridBagConstraints();
		gbc_osDeviceName.anchor = GridBagConstraints.EAST;
		gbc_osDeviceName.insets = new Insets(0, 0, 5, 5);
		gbc_osDeviceName.gridx = 0;
		gbc_osDeviceName.gridy = 7;
		osPanel.add(osDeviceName, gbc_osDeviceName);

		osDeviceNameTf = new JTextField();
		osDeviceNameTf.setEditable(false);
		GridBagConstraints gbc_osDeviceNameTf = new GridBagConstraints();
		gbc_osDeviceNameTf.insets = new Insets(0, 0, 5, 5);
		gbc_osDeviceNameTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osDeviceNameTf.gridx = 1;
		gbc_osDeviceNameTf.gridy = 7;
		osPanel.add(osDeviceNameTf, gbc_osDeviceNameTf);
		osDeviceNameTf.setColumns(10);

		JLabel osUserCount = new JLabel("User Count");
		GridBagConstraints gbc_osUserCount = new GridBagConstraints();
		gbc_osUserCount.anchor = GridBagConstraints.EAST;
		gbc_osUserCount.insets = new Insets(0, 0, 5, 5);
		gbc_osUserCount.gridx = 2;
		gbc_osUserCount.gridy = 7;
		osPanel.add(osUserCount, gbc_osUserCount);

		osUserCountTf = new JTextField();
		osUserCountTf.setEditable(false);
		GridBagConstraints gbc_osUserCountTf = new GridBagConstraints();
		gbc_osUserCountTf.insets = new Insets(0, 0, 5, 5);
		gbc_osUserCountTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osUserCountTf.gridx = 3;
		gbc_osUserCountTf.gridy = 7;
		osPanel.add(osUserCountTf, gbc_osUserCountTf);
		osUserCountTf.setColumns(10);

		JLabel osRegUser = new JLabel("Registered User");
		GridBagConstraints gbc_osRegUser = new GridBagConstraints();
		gbc_osRegUser.anchor = GridBagConstraints.EAST;
		gbc_osRegUser.insets = new Insets(0, 0, 5, 5);
		gbc_osRegUser.gridx = 4;
		gbc_osRegUser.gridy = 7;
		osPanel.add(osRegUser, gbc_osRegUser);

		osRegUserTf = new JTextField();
		osRegUserTf.setEditable(false);
		GridBagConstraints gbc_osRegUserTf = new GridBagConstraints();
		gbc_osRegUserTf.insets = new Insets(0, 0, 5, 0);
		gbc_osRegUserTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osRegUserTf.gridx = 5;
		gbc_osRegUserTf.gridy = 7;
		osPanel.add(osRegUserTf, gbc_osRegUserTf);
		osRegUserTf.setColumns(10);

		JLabel osDrive = new JLabel("System Drive");
		GridBagConstraints gbc_osDrive = new GridBagConstraints();
		gbc_osDrive.anchor = GridBagConstraints.EAST;
		gbc_osDrive.insets = new Insets(0, 0, 0, 5);
		gbc_osDrive.gridx = 0;
		gbc_osDrive.gridy = 8;
		osPanel.add(osDrive, gbc_osDrive);

		osSysDriveTf = new JTextField();
		osSysDriveTf.setEditable(false);
		GridBagConstraints gbc_osSysDriveTf = new GridBagConstraints();
		gbc_osSysDriveTf.insets = new Insets(0, 0, 0, 5);
		gbc_osSysDriveTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osSysDriveTf.gridx = 1;
		gbc_osSysDriveTf.gridy = 8;
		osPanel.add(osSysDriveTf, gbc_osSysDriveTf);
		osSysDriveTf.setColumns(10);

		JLabel osWinDir = new JLabel("Windows Directory");
		GridBagConstraints gbc_osWinDir = new GridBagConstraints();
		gbc_osWinDir.anchor = GridBagConstraints.EAST;
		gbc_osWinDir.insets = new Insets(0, 0, 0, 5);
		gbc_osWinDir.gridx = 2;
		gbc_osWinDir.gridy = 8;
		osPanel.add(osWinDir, gbc_osWinDir);

		osWinDirTf = new JTextField();
		osWinDirTf.setEditable(false);
		GridBagConstraints gbc_osWinDirTf = new GridBagConstraints();
		gbc_osWinDirTf.insets = new Insets(0, 0, 0, 5);
		gbc_osWinDirTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osWinDirTf.gridx = 3;
		gbc_osWinDirTf.gridy = 8;
		osPanel.add(osWinDirTf, gbc_osWinDirTf);
		osWinDirTf.setColumns(10);

		JLabel osSysDir = new JLabel("System Directory");
		GridBagConstraints gbc_osSysDir = new GridBagConstraints();
		gbc_osSysDir.anchor = GridBagConstraints.EAST;
		gbc_osSysDir.insets = new Insets(0, 0, 0, 5);
		gbc_osSysDir.gridx = 4;
		gbc_osSysDir.gridy = 8;
		osPanel.add(osSysDir, gbc_osSysDir);

		osSysDirTf = new JTextField();
		osSysDirTf.setEditable(false);
		GridBagConstraints gbc_osSysDirTf = new GridBagConstraints();
		gbc_osSysDirTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_osSysDirTf.gridx = 5;
		gbc_osSysDirTf.gridy = 8;
		osPanel.add(osSysDirTf, gbc_osSysDirTf);
		osSysDirTf.setColumns(10);

		JPanel userPanel = new JPanel();
		userPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"User and Timezone", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_userPanel = new GridBagConstraints();
		gbc_userPanel.fill = GridBagConstraints.BOTH;
		gbc_userPanel.gridx = 0;
		gbc_userPanel.gridy = 1;
		osAndUserPanel.add(userPanel, gbc_userPanel);
		GridBagLayout gbl_userPanel = new GridBagLayout();
		gbl_userPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_userPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_userPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_userPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		userPanel.setLayout(gbl_userPanel);

		JLabel currentUser = new JLabel("Current User");
		GridBagConstraints gbc_currentUser = new GridBagConstraints();
		gbc_currentUser.anchor = GridBagConstraints.EAST;
		gbc_currentUser.insets = new Insets(0, 0, 5, 5);
		gbc_currentUser.gridx = 0;
		gbc_currentUser.gridy = 0;
		userPanel.add(currentUser, gbc_currentUser);

		userTf = new JTextField();
		userTf.setEditable(false);
		GridBagConstraints gbc_userTf = new GridBagConstraints();
		gbc_userTf.insets = new Insets(0, 0, 5, 0);
		gbc_userTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_userTf.gridx = 1;
		gbc_userTf.gridy = 0;
		userPanel.add(userTf, gbc_userTf);
		userTf.setColumns(10);

		JLabel userHomeDir = new JLabel("User Home");
		GridBagConstraints gbc_userHomeDir = new GridBagConstraints();
		gbc_userHomeDir.anchor = GridBagConstraints.EAST;
		gbc_userHomeDir.insets = new Insets(0, 0, 5, 5);
		gbc_userHomeDir.gridx = 0;
		gbc_userHomeDir.gridy = 1;
		userPanel.add(userHomeDir, gbc_userHomeDir);

		userHomeTf = new JTextField();
		userHomeTf.setEditable(false);
		GridBagConstraints gbc_userHomeTf = new GridBagConstraints();
		gbc_userHomeTf.insets = new Insets(0, 0, 5, 0);
		gbc_userHomeTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_userHomeTf.gridx = 1;
		gbc_userHomeTf.gridy = 1;
		userPanel.add(userHomeTf, gbc_userHomeTf);
		userHomeTf.setColumns(10);

		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.gridwidth = 2;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 2;
		userPanel.add(separator, gbc_separator);

		JLabel timeZoneName = new JLabel("Timezone Name");
		GridBagConstraints gbc_timeZoneName = new GridBagConstraints();
		gbc_timeZoneName.anchor = GridBagConstraints.EAST;
		gbc_timeZoneName.insets = new Insets(0, 0, 5, 5);
		gbc_timeZoneName.gridx = 0;
		gbc_timeZoneName.gridy = 3;
		userPanel.add(timeZoneName, gbc_timeZoneName);

		timeZoneNameTf = new JTextField();
		timeZoneNameTf.setEditable(false);
		GridBagConstraints gbc_timeZoneNameTf = new GridBagConstraints();
		gbc_timeZoneNameTf.insets = new Insets(0, 0, 5, 0);
		gbc_timeZoneNameTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeZoneNameTf.gridx = 1;
		gbc_timeZoneNameTf.gridy = 3;
		userPanel.add(timeZoneNameTf, gbc_timeZoneNameTf);
		timeZoneNameTf.setColumns(10);

		JLabel timeZoneCap = new JLabel("Timezone Caption");
		GridBagConstraints gbc_timeZoneCap = new GridBagConstraints();
		gbc_timeZoneCap.anchor = GridBagConstraints.EAST;
		gbc_timeZoneCap.insets = new Insets(0, 0, 5, 5);
		gbc_timeZoneCap.gridx = 0;
		gbc_timeZoneCap.gridy = 4;
		userPanel.add(timeZoneCap, gbc_timeZoneCap);

		timeZoneCaptionTf = new JTextField();
		timeZoneCaptionTf.setEditable(false);
		GridBagConstraints gbc_timeZoneCaptionTf = new GridBagConstraints();
		gbc_timeZoneCaptionTf.insets = new Insets(0, 0, 5, 0);
		gbc_timeZoneCaptionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeZoneCaptionTf.gridx = 1;
		gbc_timeZoneCaptionTf.gridy = 4;
		userPanel.add(timeZoneCaptionTf, gbc_timeZoneCaptionTf);
		timeZoneCaptionTf.setColumns(10);
	}
	
	private void initializeBatteryPanel(JTabbedPane tabbedPane, JPanel batteryPanel) {
		tabbedPane.addTab("Battery", new FlatSVGIcon(FerrumX.class.getResource("/resources/tab_icons_small/Battery.svg")), batteryPanel, "Displays Battery Information");
		batteryPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Battery", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_batteryPanel = new GridBagLayout();
		gbl_batteryPanel.columnWidths = new int[]{0, 0, 0};
		gbl_batteryPanel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_batteryPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_batteryPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		batteryPanel.setLayout(gbl_batteryPanel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		batteryPanel.add(scrollPane, gbc_scrollPane);
		
		JTextArea batteryWarningTextArea = new JTextArea();
		batteryWarningTextArea.setFont(new Font("Ebrima", Font.ITALIC, 11));
		batteryWarningTextArea.setText("NOTE: The battery panel will display information only when the system detects a battery. For desktops that don't run on a battery, the fields will be blank.");
		batteryWarningTextArea.setLineWrap(true);
		batteryWarningTextArea.setEditable(false);
		scrollPane.setViewportView(batteryWarningTextArea);
		batteryWarningTextArea.setColumns(10);
		
		JButton refresh = new JButton("");
		refresh.addActionListener(e-> new Thread(()->
				Battery.initializeBattery(batteryChargePercentage, batteryChargeView, batteryCaptionTf, batteryStatusTf, batteryStatusTwoTf, batteryChemistryTf,
						batteryChargeTf, batteryRuntimeTf, batteryNameTf, batteryDeviceIDTf, batteryCapcityTf,
						batteryVoltageTf)
			).start()
		);
		refresh.setToolTipText("Refresh Battery Information");
		refresh.setIcon(new FlatSVGIcon(FerrumX.class.getResource("/resources/extra_icons/refresh.svg")));
		GridBagConstraints gbc_refresh = new GridBagConstraints();
		gbc_refresh.insets = new Insets(0, 0, 5, 0);
		gbc_refresh.gridx = 1;
		gbc_refresh.gridy = 0;
		batteryPanel.add(refresh, gbc_refresh);
		
		JPanel batteryPanelOne = new JPanel();
		batteryPanelOne.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Win32_Battery", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_batteryPanelOne = new GridBagConstraints();
		gbc_batteryPanelOne.gridwidth = 2;
		gbc_batteryPanelOne.insets = new Insets(0, 0, 5, 5);
		gbc_batteryPanelOne.fill = GridBagConstraints.BOTH;
		gbc_batteryPanelOne.gridx = 0;
		gbc_batteryPanelOne.gridy = 1;
		batteryPanel.add(batteryPanelOne, gbc_batteryPanelOne);
		GridBagLayout gbl_batteryPanelOne = new GridBagLayout();
		gbl_batteryPanelOne.columnWidths = new int[]{0, 0, 0, 0};
		gbl_batteryPanelOne.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_batteryPanelOne.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_batteryPanelOne.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		batteryPanelOne.setLayout(gbl_batteryPanelOne);
		
		JLabel batteryCaption = new JLabel("Caption");
		GridBagConstraints gbc_batteryCaption = new GridBagConstraints();
		gbc_batteryCaption.insets = new Insets(0, 0, 5, 5);
		gbc_batteryCaption.anchor = GridBagConstraints.EAST;
		gbc_batteryCaption.gridx = 0;
		gbc_batteryCaption.gridy = 0;
		batteryPanelOne.add(batteryCaption, gbc_batteryCaption);
		
		batteryCaptionTf = new JTextField();
		batteryCaptionTf.setEditable(false);
		GridBagConstraints gbc_batteryCaptionTf = new GridBagConstraints();
		gbc_batteryCaptionTf.insets = new Insets(0, 0, 5, 5);
		gbc_batteryCaptionTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_batteryCaptionTf.gridx = 1;
		gbc_batteryCaptionTf.gridy = 0;
		batteryPanelOne.add(batteryCaptionTf, gbc_batteryCaptionTf);
		batteryCaptionTf.setColumns(10);
		
		JLabel batteryStatus = new JLabel("Status");
		GridBagConstraints gbc_batteryStatus = new GridBagConstraints();
		gbc_batteryStatus.anchor = GridBagConstraints.EAST;
		gbc_batteryStatus.insets = new Insets(0, 0, 5, 5);
		gbc_batteryStatus.gridx = 0;
		gbc_batteryStatus.gridy = 1;
		batteryPanelOne.add(batteryStatus, gbc_batteryStatus);
		
		batteryStatusTf = new JTextField();
		batteryStatusTf.setEditable(false);
		GridBagConstraints gbc_batteryStatusTf = new GridBagConstraints();
		gbc_batteryStatusTf.insets = new Insets(0, 0, 5, 5);
		gbc_batteryStatusTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_batteryStatusTf.gridx = 1;
		gbc_batteryStatusTf.gridy = 1;
		batteryPanelOne.add(batteryStatusTf, gbc_batteryStatusTf);
		batteryStatusTf.setColumns(10);
		
		JLabel battryStatusTwo = new JLabel("Battery Status");
		GridBagConstraints gbc_battryStatusTwo = new GridBagConstraints();
		gbc_battryStatusTwo.anchor = GridBagConstraints.EAST;
		gbc_battryStatusTwo.insets = new Insets(0, 0, 5, 5);
		gbc_battryStatusTwo.gridx = 0;
		gbc_battryStatusTwo.gridy = 2;
		batteryPanelOne.add(battryStatusTwo, gbc_battryStatusTwo);
		
		batteryStatusTwoTf = new JTextField();
		batteryStatusTwoTf.setEditable(false);
		GridBagConstraints gbc_batteryStatusTwoTf = new GridBagConstraints();
		gbc_batteryStatusTwoTf.insets = new Insets(0, 0, 5, 5);
		gbc_batteryStatusTwoTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_batteryStatusTwoTf.gridx = 1;
		gbc_batteryStatusTwoTf.gridy = 2;
		batteryPanelOne.add(batteryStatusTwoTf, gbc_batteryStatusTwoTf);
		batteryStatusTwoTf.setColumns(10);
		
		JLabel batteryChemistry = new JLabel("Chemistry");
		GridBagConstraints gbc_batteryChemistry = new GridBagConstraints();
		gbc_batteryChemistry.anchor = GridBagConstraints.EAST;
		gbc_batteryChemistry.insets = new Insets(0, 0, 5, 5);
		gbc_batteryChemistry.gridx = 0;
		gbc_batteryChemistry.gridy = 3;
		batteryPanelOne.add(batteryChemistry, gbc_batteryChemistry);
		
		batteryChemistryTf = new JTextField();
		batteryChemistryTf.setEditable(false);
		GridBagConstraints gbc_batteryChemistryTf = new GridBagConstraints();
		gbc_batteryChemistryTf.insets = new Insets(0, 0, 5, 5);
		gbc_batteryChemistryTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_batteryChemistryTf.gridx = 1;
		gbc_batteryChemistryTf.gridy = 3;
		batteryPanelOne.add(batteryChemistryTf, gbc_batteryChemistryTf);
		batteryChemistryTf.setColumns(10);
		
		JPanel batteryIconPanel = new JPanel();
		batteryIconPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Battery Charge", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_batteryIconPanel = new GridBagConstraints();
		gbc_batteryIconPanel.gridheight = 6;
		gbc_batteryIconPanel.insets = new Insets(0, 0, 5, 0);
		gbc_batteryIconPanel.fill = GridBagConstraints.BOTH;
		gbc_batteryIconPanel.gridx = 2;
		gbc_batteryIconPanel.gridy = 0;
		batteryPanelOne.add(batteryIconPanel, gbc_batteryIconPanel);
		GridBagLayout gbl_batteryIconPanel = new GridBagLayout();
		gbl_batteryIconPanel.columnWidths = new int[]{344, 0};
		gbl_batteryIconPanel.rowHeights = new int[]{116, 0, 0};
		gbl_batteryIconPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_batteryIconPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		batteryIconPanel.setLayout(gbl_batteryIconPanel);
		
		batteryChargeView = new JLabel("");
		batteryChargeView.setIcon(new FlatSVGIcon(FerrumX.class.getResource("/resources/battery_level_images/Battery-0.svg")));
		GridBagConstraints gbc_batteryChargeView = new GridBagConstraints();
		gbc_batteryChargeView.insets = new Insets(0, 0, 5, 0);
		gbc_batteryChargeView.gridx = 0;
		gbc_batteryChargeView.gridy = 0;
		batteryIconPanel.add(batteryChargeView, gbc_batteryChargeView);
		
		batteryChargePercentage = new JLabel("Current Charge Level: XX%");
		GridBagConstraints gbc_batteryChargePercentage = new GridBagConstraints();
		gbc_batteryChargePercentage.gridx = 0;
		gbc_batteryChargePercentage.gridy = 1;
		batteryIconPanel.add(batteryChargePercentage, gbc_batteryChargePercentage);
		
		JLabel batteryCharge = new JLabel("Charge");
		GridBagConstraints gbc_batteryCharge = new GridBagConstraints();
		gbc_batteryCharge.anchor = GridBagConstraints.EAST;
		gbc_batteryCharge.insets = new Insets(0, 0, 5, 5);
		gbc_batteryCharge.gridx = 0;
		gbc_batteryCharge.gridy = 4;
		batteryPanelOne.add(batteryCharge, gbc_batteryCharge);
		
		batteryChargeTf = new JTextField();
		batteryChargeTf.setEditable(false);
		GridBagConstraints gbc_batteryChargeTf = new GridBagConstraints();
		gbc_batteryChargeTf.insets = new Insets(0, 0, 5, 5);
		gbc_batteryChargeTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_batteryChargeTf.gridx = 1;
		gbc_batteryChargeTf.gridy = 4;
		batteryPanelOne.add(batteryChargeTf, gbc_batteryChargeTf);
		batteryChargeTf.setColumns(10);
		
		JLabel batteryRuntime = new JLabel("Runtime");
		GridBagConstraints gbc_batteryRuntime = new GridBagConstraints();
		gbc_batteryRuntime.anchor = GridBagConstraints.EAST;
		gbc_batteryRuntime.insets = new Insets(0, 0, 0, 5);
		gbc_batteryRuntime.gridx = 0;
		gbc_batteryRuntime.gridy = 5;
		batteryPanelOne.add(batteryRuntime, gbc_batteryRuntime);
		
		batteryRuntimeTf = new JTextField();
		batteryRuntimeTf.setEditable(false);
		GridBagConstraints gbc_batteryRuntimeTf = new GridBagConstraints();
		gbc_batteryRuntimeTf.insets = new Insets(0, 0, 0, 5);
		gbc_batteryRuntimeTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_batteryRuntimeTf.gridx = 1;
		gbc_batteryRuntimeTf.gridy = 5;
		batteryPanelOne.add(batteryRuntimeTf, gbc_batteryRuntimeTf);
		batteryRuntimeTf.setColumns(10);
		
		JPanel batteryPanelTwo = new JPanel();
		batteryPanelTwo.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Win32_PortableBattery", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_batteryPanelTwo = new GridBagConstraints();
		gbc_batteryPanelTwo.gridwidth = 2;
		gbc_batteryPanelTwo.insets = new Insets(0, 0, 0, 5);
		gbc_batteryPanelTwo.fill = GridBagConstraints.BOTH;
		gbc_batteryPanelTwo.gridx = 0;
		gbc_batteryPanelTwo.gridy = 2;
		batteryPanel.add(batteryPanelTwo, gbc_batteryPanelTwo);
		GridBagLayout gbl_batteryPanelTwo = new GridBagLayout();
		gbl_batteryPanelTwo.columnWidths = new int[]{0, 0, 0};
		gbl_batteryPanelTwo.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_batteryPanelTwo.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_batteryPanelTwo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		batteryPanelTwo.setLayout(gbl_batteryPanelTwo);
		
		JLabel batteryName = new JLabel("Name");
		GridBagConstraints gbc_batteryName = new GridBagConstraints();
		gbc_batteryName.anchor = GridBagConstraints.EAST;
		gbc_batteryName.insets = new Insets(0, 0, 5, 5);
		gbc_batteryName.gridx = 0;
		gbc_batteryName.gridy = 0;
		batteryPanelTwo.add(batteryName, gbc_batteryName);
		
		batteryNameTf = new JTextField();
		batteryNameTf.setEditable(false);
		GridBagConstraints gbc_batteryNameTf = new GridBagConstraints();
		gbc_batteryNameTf.insets = new Insets(0, 0, 5, 0);
		gbc_batteryNameTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_batteryNameTf.gridx = 1;
		gbc_batteryNameTf.gridy = 0;
		batteryPanelTwo.add(batteryNameTf, gbc_batteryNameTf);
		batteryNameTf.setColumns(10);
		
		JLabel batteryID = new JLabel("DeviceID");
		GridBagConstraints gbc_batteryID = new GridBagConstraints();
		gbc_batteryID.anchor = GridBagConstraints.EAST;
		gbc_batteryID.insets = new Insets(0, 0, 5, 5);
		gbc_batteryID.gridx = 0;
		gbc_batteryID.gridy = 1;
		batteryPanelTwo.add(batteryID, gbc_batteryID);
		
		batteryDeviceIDTf = new JTextField();
		batteryDeviceIDTf.setEditable(false);
		GridBagConstraints gbc_batteryDeviceIDTf = new GridBagConstraints();
		gbc_batteryDeviceIDTf.insets = new Insets(0, 0, 5, 0);
		gbc_batteryDeviceIDTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_batteryDeviceIDTf.gridx = 1;
		gbc_batteryDeviceIDTf.gridy = 1;
		batteryPanelTwo.add(batteryDeviceIDTf, gbc_batteryDeviceIDTf);
		batteryDeviceIDTf.setColumns(10);
		
		JLabel batteryCapacity = new JLabel("Capacity");
		GridBagConstraints gbc_batteryCapacity = new GridBagConstraints();
		gbc_batteryCapacity.anchor = GridBagConstraints.EAST;
		gbc_batteryCapacity.insets = new Insets(0, 0, 5, 5);
		gbc_batteryCapacity.gridx = 0;
		gbc_batteryCapacity.gridy = 2;
		batteryPanelTwo.add(batteryCapacity, gbc_batteryCapacity);
		
		batteryCapcityTf = new JTextField();
		batteryCapcityTf.setEditable(false);
		GridBagConstraints gbc_batteryCapcityTf = new GridBagConstraints();
		gbc_batteryCapcityTf.insets = new Insets(0, 0, 5, 0);
		gbc_batteryCapcityTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_batteryCapcityTf.gridx = 1;
		gbc_batteryCapcityTf.gridy = 2;
		batteryPanelTwo.add(batteryCapcityTf, gbc_batteryCapcityTf);
		batteryCapcityTf.setColumns(10);
		
		JLabel batteryVoltage = new JLabel("Voltage");
		GridBagConstraints gbc_batteryVoltage = new GridBagConstraints();
		gbc_batteryVoltage.anchor = GridBagConstraints.EAST;
		gbc_batteryVoltage.insets = new Insets(0, 0, 5, 5);
		gbc_batteryVoltage.gridx = 0;
		gbc_batteryVoltage.gridy = 3;
		batteryPanelTwo.add(batteryVoltage, gbc_batteryVoltage);
		
		batteryVoltageTf = new JTextField();
		batteryVoltageTf.setEditable(false);
		GridBagConstraints gbc_batteryVoltageTf = new GridBagConstraints();
		gbc_batteryVoltageTf.insets = new Insets(0, 0, 5, 0);
		gbc_batteryVoltageTf.fill = GridBagConstraints.HORIZONTAL;
		gbc_batteryVoltageTf.gridx = 1;
		gbc_batteryVoltageTf.gridy = 3;
		batteryPanelTwo.add(batteryVoltageTf, gbc_batteryVoltageTf);
		batteryVoltageTf.setColumns(10);
	}

	private void initializeReportPanel(JTabbedPane tabbedPane) {
		JPanel reportPanel = new JPanel();
		reportPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		tabbedPane.addTab("Report", new FlatSVGIcon(FerrumX.class.getResource("/resources/tab_icons_small/Report_Tool.svg")), reportPanel, "Generate comprehensive reports");
		GridBagLayout gbl_reportPanel = new GridBagLayout();
		gbl_reportPanel.columnWidths = new int[] { 0, 0, 0 };
		gbl_reportPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_reportPanel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_reportPanel.rowWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		reportPanel.setLayout(gbl_reportPanel);

		JPanel reportAreaPanel = new JPanel();
		reportAreaPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Report Area", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_reportAreaPanel = new GridBagConstraints();
		gbc_reportAreaPanel.gridwidth = 2;
		gbc_reportAreaPanel.insets = new Insets(0, 0, 5, 0);
		gbc_reportAreaPanel.fill = GridBagConstraints.BOTH;
		gbc_reportAreaPanel.gridx = 0;
		gbc_reportAreaPanel.gridy = 0;
		reportPanel.add(reportAreaPanel, gbc_reportAreaPanel);
		reportAreaPanel.setLayout(new GridLayout(0, 1, 0, 0));

		JScrollPane reportScrollPane = new JScrollPane();
		reportAreaPanel.add(reportScrollPane);

		JTextArea reportTextArea = new JTextArea();
		reportTextArea.setFont(new Font("Consolas", Font.BOLD, 12));
		reportScrollPane.setViewportView(reportTextArea);
		reportTextArea.setEditable(false);

		DefaultCaret reportScrollCaret = (DefaultCaret) reportTextArea.getCaret();
		reportScrollCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JPanel reportLogPanel = new JPanel();
		reportLogPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Report Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_reportLogPanel = new GridBagConstraints();
		gbc_reportLogPanel.insets = new Insets(0, 0, 0, 5);
		gbc_reportLogPanel.fill = GridBagConstraints.BOTH;
		gbc_reportLogPanel.gridx = 0;
		gbc_reportLogPanel.gridy = 2;
		reportPanel.add(reportLogPanel, gbc_reportLogPanel);
		reportLogPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JScrollPane logScrollPane = new JScrollPane();
		reportLogPanel.add(logScrollPane);

		JTextArea logTextArea = new JTextArea();
		logTextArea.setFont(new Font("Consolas", Font.BOLD, 12));
		logScrollPane.setViewportView(logTextArea);
		logTextArea.setEditable(false);

		JPanel reportControlPanel = new JPanel();
		reportControlPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Controls", TitledBorder.TRAILING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_reportControlPanel = new GridBagConstraints();
		gbc_reportControlPanel.fill = GridBagConstraints.BOTH;
		gbc_reportControlPanel.gridx = 1;
		gbc_reportControlPanel.gridy = 2;
		reportPanel.add(reportControlPanel, gbc_reportControlPanel);
		GridBagLayout gbl_reportControlPanel = new GridBagLayout();
		gbl_reportControlPanel.columnWidths = new int[] { 0, 0 };
		gbl_reportControlPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_reportControlPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_reportControlPanel.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		reportControlPanel.setLayout(gbl_reportControlPanel);

		JPanel reportControlSubPanel = new JPanel();
		reportControlSubPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Report Controls", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_reportControlSubPanel = new GridBagConstraints();
		gbc_reportControlSubPanel.insets = new Insets(0, 0, 5, 0);
		gbc_reportControlSubPanel.fill = GridBagConstraints.BOTH;
		gbc_reportControlSubPanel.gridx = 0;
		gbc_reportControlSubPanel.gridy = 0;
		reportControlPanel.add(reportControlSubPanel, gbc_reportControlSubPanel);
		SpringLayout sl_reportControlSubPanel = new SpringLayout();
		reportControlSubPanel.setLayout(sl_reportControlSubPanel);

		JButton detailedReport = new JButton("Detailed Report");
		sl_reportControlSubPanel.putConstraint(SpringLayout.NORTH, detailedReport, 10, SpringLayout.NORTH,
				reportControlSubPanel);
		sl_reportControlSubPanel.putConstraint(SpringLayout.WEST, detailedReport, 10, SpringLayout.WEST,
				reportControlSubPanel);
		reportControlSubPanel.add(detailedReport);

		JButton summarizedReport = new JButton("Summarized Report");
		sl_reportControlSubPanel.putConstraint(SpringLayout.NORTH, summarizedReport, 0, SpringLayout.NORTH,
				detailedReport);
		sl_reportControlSubPanel.putConstraint(SpringLayout.EAST, summarizedReport, -10, SpringLayout.EAST,
				reportControlSubPanel);
		reportControlSubPanel.add(summarizedReport);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		sl_reportControlSubPanel.putConstraint(SpringLayout.WEST, progressBar, 10, SpringLayout.WEST,
				reportControlSubPanel);
		sl_reportControlSubPanel.putConstraint(SpringLayout.SOUTH, progressBar, -10, SpringLayout.SOUTH,
				reportControlSubPanel);
		sl_reportControlSubPanel.putConstraint(SpringLayout.EAST, progressBar, 0, SpringLayout.EAST, summarizedReport);
		reportControlSubPanel.add(progressBar);

		// add report button action listeners
		detailedReport.addActionListener(e -> DetailedReportGeneration.generate(reportTextArea, logTextArea,
				detailedReport, summarizedReport, progressBar));
		summarizedReport.addActionListener(e -> SummarizedReportGeneration.generate(reportTextArea, logTextArea,
				detailedReport, summarizedReport, progressBar));
		JPanel reportLogControlPanel = new JPanel();
		reportLogControlPanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null),
				"Report Log Controls", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_reportLogControlPanel = new GridBagConstraints();
		gbc_reportLogControlPanel.fill = GridBagConstraints.BOTH;
		gbc_reportLogControlPanel.gridx = 0;
		gbc_reportLogControlPanel.gridy = 1;
		reportControlPanel.add(reportLogControlPanel, gbc_reportLogControlPanel);
		SpringLayout sl_reportLogControlPanel = new SpringLayout();
		reportLogControlPanel.setLayout(sl_reportLogControlPanel);

		JButton reportCopy = new JButton("Copy Report");
		sl_reportLogControlPanel.putConstraint(SpringLayout.NORTH, reportCopy, 10, SpringLayout.NORTH,
				reportLogControlPanel);
		sl_reportLogControlPanel.putConstraint(SpringLayout.WEST, reportCopy, 10, SpringLayout.WEST,
				reportLogControlPanel);
		reportLogControlPanel.add(reportCopy);

		JButton logCopy = new JButton("Copy Logs");
		sl_reportLogControlPanel.putConstraint(SpringLayout.NORTH, logCopy, 0, SpringLayout.NORTH, reportCopy);
		sl_reportLogControlPanel.putConstraint(SpringLayout.EAST, logCopy, -10, SpringLayout.EAST,
				reportLogControlPanel);
		reportLogControlPanel.add(logCopy);

		JTextField copyStatusTf = new JTextField();
		copyStatusTf.setFont(new Font("Consolas", Font.PLAIN, 10));
		copyStatusTf.setEditable(false);
		sl_reportLogControlPanel.putConstraint(SpringLayout.WEST, copyStatusTf, 0, SpringLayout.WEST, reportCopy);
		sl_reportLogControlPanel.putConstraint(SpringLayout.SOUTH, copyStatusTf, -10, SpringLayout.SOUTH,
				reportLogControlPanel);
		sl_reportLogControlPanel.putConstraint(SpringLayout.EAST, copyStatusTf, 0, SpringLayout.EAST, logCopy);
		reportLogControlPanel.add(copyStatusTf);
		copyStatusTf.setColumns(10);

		// add copy button action listeners
		reportCopy.addActionListener(e -> {
			reportTextArea.selectAll();
			reportTextArea.copy();
			copyStatusTf.setText("Successfully copied the Report to clipboard");
		});

		logCopy.addActionListener(e -> {
			logTextArea.selectAll();
			logTextArea.copy();
			copyStatusTf.setText("Successfully copied the Logs to clipboard");
		});
	}
	// TODO revisit and try SwingWorker instead
	private void initializeSystemInfo(StatusUI startScreen) {
		try (ExecutorService exec = Executors.newCachedThreadPool()) {
			Future<Boolean> initializeHardwareId = exec.submit(() -> HardwareId.initializeHardwareId(hwidTf));
			Future<Boolean> initializeCpu = exec.submit(() -> Cpu.initializeCpu(cpuLogo, cacheTa, cpuChoice, cpuNameTf,
					cpuCoreTf, cpuThreadTf, cpuLogicProcessorTf, cpuManufacturerTf, addressWidthTf, cpuSocketTf,
					cpuBaseClockTf, multiplierTf, effectiveClockTf, cpuVersionTf, cpuCaptionTf, cpuFamilyTf,
					cpuSteppingTf, cpuVirtStatusTf, cpuIdTf, cpuL2Tf, cpuL3Tf));
			Future<Boolean> initializeMemory = exec.submit(() -> Memory.initializeMemory(memorySlotChoice, memoryNameTf,
					memoryManufacturerTf, memoryModelTf, memoryOtherInfoTf, memoryPartNumberTf, memoryFormFactorTf,
					memoryBankLabelTf, memoryCapacityTf, memoryWidthTf, memorySpeedTf, memoryConfigClockSpeedTf,
					memoryLocatorTf, memorySerialNumberTf));
			Future<Boolean> initializeMainboard = exec.submit(() -> Mainboard.initializeMainboard(mbManufacturerTf, mbModelTf, mbProductTf, mbSerialNumberTf,
							mbVersionTf, mbPnpTf, biosNameTf, biosCaptionTf, biosManufacturerTf, biosReleaseDateTf,
							biosVersionTf, biosStatusTf, smbiosPresenceTf, smbiosVersionTf, biosLanguageTf));
			Future<Boolean> initializeGpu = exec.submit(() -> Gpu.initializeGpu(gpuLogo, gpuChoiceComboBox, gpuNameTf, gpuPnpTf,
					gpuHorizontalResTf, gpuVerticalResTf, gpuBitsTf, gpuMinRefTf, gpuMaxRefTf, gpuCurrentRefTf,
					gpuDACTf, gpuVramTf, gpuDriverVersionTf, gpuDriverDateTf, gpuVideoProcessorTf));
			Future<Boolean> initializeNetwork = exec.submit(() -> Network.initializeNetwork(netConnectionChoiceBox,
					netNameTf, netPnpTf, netMacTf, netConnectIdTf, netIpStatusTf, netIPAddressTf, netIPSubnetTf,
					netGatewayTf, netDHCPStatusTf, netDHCPServerTf, netDNSHostTf, netDNSServerTf));
			Future<Boolean> initializeStorage = exec.submit(() -> Storage.initializeStorage(diskIndexChoiceBox,
					diskPartTa, diskCaptionTf, diskModelTf, diskSizeTf, firmwareRevisionTf, diskSerialNumberTf,
					diskPartitionsTf, diskStatusTf, diskInterfaceTf));
			Future<Boolean> initializeOs = exec.submit(() -> OperatingSystem.getOsProperties(osCoverImg, currentOsChoiceBox,
					osCaptionTf, osVersionTf, osManufacturerTf, osArchitectureTf, osBuildNumberTf, osInstallDateTf,
					osLastBootTf, osSerialTf, osPrimaryTf, osDistributedTf, osPortTf, osDeviceNameTf, osUserCountTf,
					osRegUserTf, osLangTf, osSysDriveTf, osWinDirTf, osSysDirTf));
			Future<?> initializeBattery = exec.submit(()-> Battery.initializeBattery(batteryChargePercentage, batteryChargeView, batteryCaptionTf, batteryStatusTf,
					batteryStatusTwoTf,batteryChemistryTf,batteryChargeTf,batteryRuntimeTf,batteryNameTf,batteryDeviceIDTf,
					batteryCapcityTf,batteryVoltageTf));
			Future<Boolean> initializeUserAndTime = exec.submit(() -> UserAndTime.initializeUserAndTime(userTf, userHomeTf, timeZoneNameTf, timeZoneCaptionTf));

			startScreen.setHardwareLabel(initializeHardwareId.get());
			startScreen.setCpuLabel(initializeCpu.get());
			startScreen.setMemoryLabel(initializeMemory.get());
			startScreen.setMainboardLabel(initializeMainboard.get());
			startScreen.setGpuLabel(initializeGpu.get());
			startScreen.setNetworkLabel(initializeNetwork.get());
			startScreen.setStorageLabel(initializeStorage.get());
			startScreen.setOsLabel(initializeOs.get() && initializeUserAndTime.get());
			initializeBattery.get();

			TimeUnit.MILLISECONDS.sleep(150);
			startScreen.dispose();
		} catch (RejectedExecutionException | NullPointerException | InterruptedException e) {
				String message = e.getMessage();
				String stackTrace = Arrays.toString(e.getStackTrace());
				new ExceptionUI("Host Gather System Info Error", "Error: "+message+"\nStackTrace: \n"+stackTrace).setVisible(true);
				Thread.currentThread().interrupt();
		}
		catch (ExecutionException e) {
			String message = e.getCause().getMessage();
			String stackTrace = Arrays.toString(e.getCause().getStackTrace());
			new ExceptionUI("Host Gather System Info Error", "Error: "+message+"\nStackTrace: \n"+stackTrace).setVisible(true);
		}
	}
}
