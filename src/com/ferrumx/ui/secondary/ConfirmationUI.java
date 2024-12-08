package com.ferrumx.ui.secondary;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

public class ConfirmationUI extends JFrame {
	private static final long serialVersionUID = -4503451098970252115L;
	private JButton btnYes;
	private JButton btnNo;
	
	public ConfirmationUI(String heading, String question) {
		super(heading);
		initialize(question);
	}

	private void initialize(String question) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ConfirmationUI.class.getResource("/resources/icon_main.png")));
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(300,140);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), "Select an option", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 264, 79);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		btnYes = new JButton("Yes");
		btnYes.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		btnYes.setBounds(68, 51, 54, 17);
		panel.add(btnYes);
		
		btnNo = new JButton("No");
		btnNo.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		btnNo.setBounds(152, 51, 54, 17);
		panel.add(btnNo);
		
		JLabel questionLabel = new JLabel(question);
		questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		questionLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
		questionLabel.setBounds(10, 18, 244, 24);
		panel.add(questionLabel);
	}
	
	//allows for implementation of your own action listeners
	public JButton getBtnYes() {
		return btnYes;
	}

	public JButton getBtnNo() {
		return btnNo;
	}

}

