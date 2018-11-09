package minesweeper;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStreamReader;

public class Waiting extends JFrame {
    final String TITLE_OF_PROGRAM = "Minesweeper";

	private JPanel contentPane;

	public Waiting() {
		setTitle(TITLE_OF_PROGRAM);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 651, 470);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRoom1 = new JButton("No Room");
		btnRoom1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String roomname = btnRoom1.getText();
				if(!(roomname.equals("No Room"))) {
					try {
						BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
						String str = new String("2#3#");
						str = str.concat("0");
						str = str.concat("#");
						str = str.concat(Minesweeper.getIDNUM());
						str = str.concat("#");
						bos.write(str.getBytes());
						bos.flush();
						Minesweeper.setRoomNum("0");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Game gameroom = new Game(Minesweeper.isCaptain());
					gameroom.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "There isn't a room.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
					
			}
		});
		btnRoom1.setBounds(14, 12, 597, 27);
		contentPane.add(btnRoom1);
		
		JButton btnRoom2 = new JButton("No Room");
		btnRoom2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String roomname = btnRoom1.getText();
				if(!(roomname.equals("No Room"))) {
					try {
						BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
						String str = new String("2#3#");
						str = str.concat("1");
						str = str.concat("#");
						str = str.concat(Minesweeper.getIDNUM());
						str = str.concat("#");
						bos.write(str.getBytes());
						bos.flush();
						Minesweeper.setRoomNum("1");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Game gameroom = new Game(Minesweeper.isCaptain());
					gameroom.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "There isn't a room.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
					
			}
		});
		btnRoom2.setBounds(14, 51, 597, 27);
		contentPane.add(btnRoom2);
		
		JButton btnRoom3 = new JButton("No Room");
		btnRoom3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String roomname = btnRoom1.getText();
				if(!(roomname.equals("No Room"))) {
					try {
						BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
						String str = new String("2#3#");
						str = str.concat("2");
						str = str.concat("#");
						str = str.concat(Minesweeper.getIDNUM());
						str = str.concat("#");
						bos.write(str.getBytes());
						bos.flush();
						Minesweeper.setRoomNum("2");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Game gameroom = new Game(Minesweeper.isCaptain());
					gameroom.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "There isn't a room.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
					
			}
		});
		btnRoom3.setBounds(14, 89, 597, 27);
		contentPane.add(btnRoom3);
		
		JButton btnRoom4 = new JButton("No Room");
		btnRoom4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String roomname = btnRoom1.getText();
				if(!(roomname.equals("No Room"))) {
					try {
						BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
						String str = new String("2#3#");
						str = str.concat("3");
						str = str.concat("#");
						str = str.concat(Minesweeper.getIDNUM());
						str = str.concat("#");
						bos.write(str.getBytes());
						bos.flush();
						Minesweeper.setRoomNum("3");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Game gameroom = new Game(Minesweeper.isCaptain());
					gameroom.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "There isn't a room.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
					
			}
		});
		btnRoom4.setBounds(14, 128, 597, 27);
		contentPane.add(btnRoom4);
		
		JButton btnRoom5 = new JButton("No Room");
		btnRoom5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String roomname = btnRoom1.getText();
				if(!(roomname.equals("No Room"))) {
					try {
						BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
						String str = new String("2#3#");
						str = str.concat("4");
						str = str.concat("#");
						str = str.concat(Minesweeper.getIDNUM());
						str = str.concat("#");
						bos.write(str.getBytes());
						bos.flush();
						Minesweeper.setRoomNum("4");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Game gameroom = new Game(Minesweeper.isCaptain());
					gameroom.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "There isn't a room.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
					
			}
		});
		btnRoom5.setBounds(14, 167, 597, 27);
		contentPane.add(btnRoom5);
		
		JButton btnRoom6 = new JButton("No Room");
		btnRoom6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String roomname = btnRoom1.getText();
				if(!(roomname.equals("No Room"))) {
					try {
						BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
						String str = new String("2#3#");
						str = str.concat("5");
						str = str.concat("#");
						str = str.concat(Minesweeper.getIDNUM());
						str = str.concat("#");
						bos.write(str.getBytes());
						bos.flush();
						Minesweeper.setRoomNum("5");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Game gameroom = new Game(Minesweeper.isCaptain());
					gameroom.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "There isn't a room.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
					
			}
		});
		btnRoom6.setBounds(14, 206, 597, 27);
		contentPane.add(btnRoom6);
		
		JButton btnRoom7 = new JButton("No Room");
		btnRoom7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String roomname = btnRoom1.getText();
				if(!(roomname.equals("No Room"))) {
					try {
						BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
						String str = new String("2#3#");
						str = str.concat("6");
						str = str.concat("#");
						str = str.concat(Minesweeper.getIDNUM());
						str = str.concat("#");
						bos.write(str.getBytes());
						bos.flush();
						Minesweeper.setRoomNum("6");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Game gameroom = new Game(Minesweeper.isCaptain());
					gameroom.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "There isn't a room.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
					
			}
		});
		btnRoom7.setBounds(14, 245, 597, 27);
		contentPane.add(btnRoom7);
		
		JButton btnRoom8 = new JButton("No Room");
		btnRoom8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String roomname = btnRoom1.getText();
				if(!(roomname.equals("No Room"))) {
					try {
						BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
						String str = new String("2#3#");
						str = str.concat("7");
						str = str.concat("#");
						str = str.concat(Minesweeper.getIDNUM());
						str = str.concat("#");
						bos.write(str.getBytes());
						bos.flush();
						Minesweeper.setRoomNum("7");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Game gameroom = new Game(Minesweeper.isCaptain());
					gameroom.setVisible(true);
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(contentPane, "There isn't a room.", "Warning", JOptionPane.WARNING_MESSAGE);
				}
					
			}
		});
		btnRoom8.setBounds(14, 284, 597, 27);
		contentPane.add(btnRoom8);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JButton actionhandling = (JButton)e.getSource();
				try {
					BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
					BufferedReader reader = new BufferedReader(new InputStreamReader(Minesweeper.getSocket().getInputStream()));
					String str = "0"; 
					
					bos.write(str.getBytes());
					bos.flush();
					String roomname = new String();
					roomname = reader.readLine();
					btnRoom1.setText(roomname);
					roomname = reader.readLine();
					btnRoom2.setText(roomname);
					roomname = reader.readLine();
					btnRoom3.setText(roomname);
					roomname = reader.readLine();
					btnRoom4.setText(roomname);
					roomname = reader.readLine();
					btnRoom5.setText(roomname);
					roomname = reader.readLine();
					btnRoom6.setText(roomname);
					roomname = reader.readLine();
					btnRoom7.setText(roomname);
					roomname = reader.readLine();
					btnRoom8.setText(roomname);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Waiting wt = new Waiting();
				wt.repaint();
			}
		});
		btnRefresh.setBounds(14, 324, 140, 92);
		contentPane.add(btnRefresh);
		
		JButton btnMake = new JButton("Make Room");
		btnMake.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MakeRoom mr = new MakeRoom();
				mr.setVisible(true);
			}
		});
		btnMake.setBounds(168, 324, 140, 92);
		contentPane.add(btnMake);
		
		JButton btnRanking = new JButton("Ranking");
		btnRanking.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Ranking r = new Ranking();
				r.setVisible(true);
			}
		});
		btnRanking.setBounds(322, 324, 140, 92);
		contentPane.add(btnRanking);
		
		JButton btnEnd = new JButton("End Game");
		btnEnd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnEnd.setBounds(476, 324, 135, 92);
		contentPane.add(btnEnd);

		try {
			BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(Minesweeper.getSocket().getInputStream()));
			String str = "0"; 
			bos.write(str.getBytes());
			bos.flush();
			String roomname = new String();
			roomname = reader.readLine();
			btnRoom1.setText(roomname);
			roomname = reader.readLine();
			btnRoom2.setText(roomname);
			roomname = reader.readLine();
			btnRoom3.setText(roomname);
			roomname = reader.readLine();
			btnRoom4.setText(roomname);
			roomname = reader.readLine();
			btnRoom5.setText(roomname);
			roomname = reader.readLine();
			btnRoom6.setText(roomname);
			roomname = reader.readLine();
			btnRoom7.setText(roomname);
			roomname = reader.readLine();
			btnRoom8.setText(roomname);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
