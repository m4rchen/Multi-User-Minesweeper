package minesweeper;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Register extends JFrame {
    final String TITLE_OF_PROGRAM = "Minesweeper";

	private JPanel contentPane;
	private JTextField idTF;
	private JPasswordField passwordTF;
	private JPasswordField passwordcheckTF;

	public Register() {
		setTitle(TITLE_OF_PROGRAM);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 291);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewId = new JLabel("New ID");
		lblNewId.setBounds(72, 32, 122, 18);
		contentPane.add(lblNewId);
		
		JLabel lblNewPassword = new JLabel("New Password");
		lblNewPassword.setBounds(72, 86, 116, 18);
		contentPane.add(lblNewPassword);
		
		JLabel lblPasswordCheck = new JLabel("Password Check");
		lblPasswordCheck.setBounds(72, 134, 116, 18);
		contentPane.add(lblPasswordCheck);
		
		idTF = new JTextField();
		idTF.setColumns(10);
		idTF.setBounds(193, 29, 175, 24);
		contentPane.add(idTF);
		
		passwordTF = new JPasswordField();
		passwordTF.setColumns(10);
		passwordTF.setBounds(193, 83, 175, 24);
		contentPane.add(passwordTF);
		
		passwordcheckTF = new JPasswordField();
		passwordcheckTF.setColumns(10);
		passwordcheckTF.setBounds(193, 131, 175, 24);
		contentPane.add(passwordcheckTF);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int registerOKcnt =0;
				String id = idTF.getText();
				if(id.length()==0) {
					JOptionPane.showMessageDialog(contentPane, "Type your ID", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				String password = passwordTF.getText();
				String passwordcheck = passwordcheckTF.getText();
				if(password.length()==0) {
					JOptionPane.showMessageDialog(contentPane, "Type your password", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				else if(!password.equals(passwordcheck)) {
					JOptionPane.showMessageDialog(contentPane, "Password and password check do not match.", "Try again", JOptionPane.WARNING_MESSAGE);
				}
				else
					registerOKcnt++;
				if(registerOKcnt==1) {
					try {
						BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
						BufferedReader reader = new BufferedReader(new InputStreamReader(Minesweeper.getSocket().getInputStream()));
						String str = new String("1#1#");
						str = str.concat(id);
						str = str.concat("#");
						str = str.concat(password);
						str = str.concat("#");
						bos.write(str.getBytes());
						bos.flush();
						String check = new String();
						check = reader.readLine();
						System.out.println("Server >> "+ check);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Login log = new Login();
					log.setVisible(true);
					dispose();
					JOptionPane.showMessageDialog(contentPane, "Your subscription is complete.", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnRegister.setBounds(72, 192, 134, 27);
		contentPane.add(btnRegister);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login log = new Login();
				log.setVisible(true);
				dispose();
			}
		});
		btnCancel.setBounds(241, 192, 129, 27);
		contentPane.add(btnCancel);
	}
}
