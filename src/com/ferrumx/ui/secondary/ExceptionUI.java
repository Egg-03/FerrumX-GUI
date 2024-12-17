package com.ferrumx.ui.secondary;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ExceptionUI extends JFrame {
	private static final long serialVersionUID = 5951705399700376822L;
	private JTextArea exceptionArea = new JTextArea();

	public ExceptionUI(String errorName, String errorMessage) {
		super("Crash Report Engine");
		initialize(errorName, errorMessage);
	}

	private void initialize(String errorName, String errorMessage) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ExceptionUI.class.getResource("/resources/icon_main.png")));
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(490, 190);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), errorName,
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 454, 130);
		getContentPane().add(panel);
		panel.setLayout(null);
		exceptionArea.setLineWrap(true);
		exceptionArea.setForeground(new Color(0, 128, 128));
		exceptionArea.setEditable(false);
		exceptionArea.setBackground(new Color(32, 32, 32));
		exceptionArea.setText(errorMessage);

		JScrollPane scrollPane = new JScrollPane(exceptionArea);
		scrollPane.setBounds(10, 22, 434, 70);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel.add(scrollPane);

		JButton openLogFolder = new JButton("Open The Log Folder");
		openLogFolder.addActionListener( e-> {
			try {
				Desktop.getDesktop().open(new File("crashlogs"));
			} catch (IOException | NullPointerException | IllegalArgumentException | UnsupportedOperationException | SecurityException e1) {
				SwingUtilities.invokeLater(()->exceptionArea.setText(e1.getMessage()));
			}
		});
		openLogFolder.setBounds(10, 98, 135, 22);
		panel.add(openLogFolder);
		
		JButton checkLatestLogs = new JButton("Check The Latest Log");
		checkLatestLogs.addActionListener( e-> {
			try {
				Desktop.getDesktop().open(new File("crashlogs/latest.log"));
			} catch (IOException | NullPointerException | IllegalArgumentException | UnsupportedOperationException | SecurityException e1) {
				SwingUtilities.invokeLater(()->exceptionArea.setText(e1.getMessage()));
			}
		});
		checkLatestLogs.setBounds(309, 98, 135, 22);
		panel.add(checkLatestLogs);
		
		setVisible(true);
	}
}
