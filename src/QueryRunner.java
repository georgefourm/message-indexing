package com.georgesdoe;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class QueryRunner {

	private JFrame frmQueryRunner;
	private JTextField textField;
	SolrjWrapper ind;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QueryRunner window = new QueryRunner();
					window.frmQueryRunner.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public QueryRunner() {
		initialize();
		ind=new SolrjWrapper();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmQueryRunner = new JFrame();
		frmQueryRunner.setTitle("Query Runner");
		frmQueryRunner.setBounds(100, 100, 450, 300);
		frmQueryRunner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmQueryRunner.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(53, 69, 191, 25);
		frmQueryRunner.getContentPane().add(textField);
		textField.setColumns(10);
		
		final JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		
		
		JButton btnNewButton = new JButton("Run Query");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					textArea.setText("");
					String[] messages=ind.runQuery(textField.getText());
					if(messages.length!=0){
						for(String s:messages){
							textArea.setText(textArea.getText()+">>"+s+"\n");
						}
					}else{
						textArea.setText("--No messages returned");
					}
					
				} catch (Exception e) {
					textArea.setText(textArea.getText()+"Query Error: "+e.getMessage());
				}
			}
		});
		btnNewButton.setBounds(278, 69, 117, 25);
		frmQueryRunner.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Results:");
		lblNewLabel.setBounds(53, 125, 70, 15);
		frmQueryRunner.getContentPane().add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(53, 164, 342, 88);
		frmQueryRunner.getContentPane().add(scrollPane);
		scrollPane.setViewportView(textArea);
		
	}
}
