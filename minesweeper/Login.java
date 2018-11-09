package minesweeper;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	final String TITLE_OF_PROGRAM = "Minesweeper";
	private JPanel contentPane;
	private JTextField idTF;
	private JPasswordField passwordTF;

	public Login() {
		setTitle(TITLE_OF_PROGRAM);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblID = new JLabel("ID");
		lblID.setBounds(81, 99, 62, 18);
		contentPane.add(lblID);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(81, 142, 87, 18);
		contentPane.add(lblPassword);

		idTF = new JTextField();
		idTF.setBounds(177, 96, 165, 24);
		contentPane.add(idTF);
		idTF.setColumns(10);

		passwordTF = new JPasswordField();
		passwordTF.setBounds(177, 139, 165, 24);
		contentPane.add(passwordTF);
		passwordTF.setColumns(10);
		passwordTF.setEchoChar('*');

		JLabel lblMneMne = new JLabel("M!NE M!NE");
		lblMneMne.setFont(new Font("±¼¸²", Font.PLAIN, 30));
		lblMneMne.setBounds(126, 12, 165, 57);
		contentPane.add(lblMneMne);

		JButton btnLogin = new JButton("Login");
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String id = idTF.getText();
				String password = passwordTF.getText();
				try {
					BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(Minesweeper.getSocket().getInputStream()));
					String str = new String("1#2#");
					str = str.concat(id);
					str = str.concat("#");
					str = str.concat(password);
					str = str.concat("#");
					bos.write(str.getBytes());
					bos.flush();
					String idnum = new String();
					idnum = reader.readLine();
					System.out.println(idnum);
					Minesweeper.setIDNUM(idnum);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Minesweeper.setID(id);
				Waiting wait = new Waiting();
				wait.setVisible(true);
				dispose();
			}
		});
		btnLogin.setBounds(55, 201, 126, 27);
		contentPane.add(btnLogin);

		JButton btnRegister = new JButton("Register");
		btnRegister.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Register reg = new Register();
				reg.setVisible(true);
				dispose();
			}
		});
		btnRegister.setBounds(240, 201, 126, 27);
		contentPane.add(btnRegister);
	}
}
