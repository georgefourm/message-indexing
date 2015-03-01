package com.georgesdoe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class Sender {

	private JFrame frmMessenger;
	private JTextField textField;
	private Client sender;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sender window = new Sender();
					window.frmMessenger.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public Sender() {
		initialize();
		try {
			sender=new Client();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("Client Exception: "+e.getMessage());
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMessenger = new JFrame();
		frmMessenger.setResizable(false);
		frmMessenger.setTitle("Messenger");
		frmMessenger.setBounds(100, 100, 450, 300);
		frmMessenger.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMessenger.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(23, 141, 159, 53);
		frmMessenger.getContentPane().add(textField);
		textField.setColumns(10);
		
		final JTextArea textArea = new JTextArea();
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					sender.SendMessage(textField.getText());
					textArea.setText(textArea.getText()+
							"\n[X]Sent: "+textField.getText());
				} catch (Exception e) {
					textArea.setText(e.getMessage());
				}
			}
		});
		btnNewButton.setBounds(194, 155, 117, 25);
		frmMessenger.getContentPane().add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 32, 299, 77);
		frmMessenger.getContentPane().add(scrollPane);
		
		
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		
		
	}
}
