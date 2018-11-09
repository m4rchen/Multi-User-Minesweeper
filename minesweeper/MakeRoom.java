package minesweeper;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MakeRoom extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField roomnameTF;

	public MakeRoom() {
		setBounds(100, 100, 370, 387);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblRoomName = new JLabel("Room name");
		lblRoomName.setBounds(40, 38, 90, 18);
		contentPanel.add(lblRoomName);

		JLabel lblGameMode = new JLabel("Game mode");
		lblGameMode.setBounds(40, 102, 90, 18);
		contentPanel.add(lblGameMode);

		JLabel lblPlayersNumber = new JLabel("Players number");
		lblPlayersNumber.setBounds(40, 198, 112, 18);
		contentPanel.add(lblPlayersNumber);

		roomnameTF = new JTextField();
		roomnameTF.setBounds(144, 35, 171, 24);
		contentPanel.add(roomnameTF);
		roomnameTF.setColumns(10);

		ButtonGroup modebtngroup = new ButtonGroup();
		ButtonGroup playerbtngroup = new ButtonGroup();

		JRadioButton rdbtnTurnbasedMode = new JRadioButton("Turn-based mode");
		rdbtnTurnbasedMode.setBounds(144, 98, 171, 27);
		contentPanel.add(rdbtnTurnbasedMode);

		JRadioButton rdbtnRaidMode = new JRadioButton("Raid mode");
		rdbtnRaidMode.setBounds(144, 135, 171, 27);
		contentPanel.add(rdbtnRaidMode);

		modebtngroup.add(rdbtnTurnbasedMode);
		modebtngroup.add(rdbtnRaidMode);

		JRadioButton rdbtnplayer2 = new JRadioButton("2");
		rdbtnplayer2.setBounds(162, 194, 139, 27);
		contentPanel.add(rdbtnplayer2);

		JRadioButton rdbtnplayer3 = new JRadioButton("3");
		rdbtnplayer3.setBounds(162, 227, 139, 27);
		contentPanel.add(rdbtnplayer3);

		JRadioButton rdbtnplayer4 = new JRadioButton("4");
		rdbtnplayer4.setBounds(162, 260, 139, 27);
		contentPanel.add(rdbtnplayer4);

		playerbtngroup.add(rdbtnplayer2);
		playerbtngroup.add(rdbtnplayer3);
		playerbtngroup.add(rdbtnplayer4);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						int makeRoomOKcnt = 0;
						JButton actionhandling = (JButton) e.getSource();
						String roomTitle = roomnameTF.getText();
						if (roomTitle.length() == 0) {
							JOptionPane.showMessageDialog(buttonPane, "Type a room title.", "Warning",
									JOptionPane.WARNING_MESSAGE);
						} else
							makeRoomOKcnt++;
						String gameMode = new String();
						String numOfPlayers = new String();
						if (rdbtnTurnbasedMode.isSelected()) {
							gameMode = "0";
							makeRoomOKcnt++;
						} else if (rdbtnRaidMode.isSelected()) {
							gameMode = "1";
							makeRoomOKcnt++;
						} else
							JOptionPane.showMessageDialog(buttonPane, "Please select a game mode.", "Warning",
									JOptionPane.WARNING_MESSAGE);

						if (rdbtnplayer2.isSelected()) {
							numOfPlayers = rdbtnplayer2.getText();
							makeRoomOKcnt++;
						} else if (rdbtnplayer3.isSelected()) {
							numOfPlayers = rdbtnplayer3.getText();
							makeRoomOKcnt++;
						} else if (rdbtnplayer4.isSelected()) {
							numOfPlayers = rdbtnplayer4.getText();
							makeRoomOKcnt++;
						} else
							JOptionPane.showMessageDialog(buttonPane, "Please select the number of players.", "Warning",
									JOptionPane.WARNING_MESSAGE);

						if (makeRoomOKcnt == 3) {
							try {
								BufferedOutputStream bos = new BufferedOutputStream(
										Minesweeper.getSocket().getOutputStream());
								BufferedReader reader = new BufferedReader(
										new InputStreamReader(Minesweeper.getSocket().getInputStream()));
								String str = new String("2#2#");
								str = str.concat(gameMode);
								str = str.concat("#");
								str = str.concat(numOfPlayers);
								str = str.concat("#");
								str = str.concat(roomTitle);
								str = str.concat("#");
								str = str.concat(Minesweeper.getIDNUM());
								str = str.concat("#");
								Minesweeper.setCaptain();

								bos.write(str.getBytes());
								bos.flush();

								Minesweeper.setRoomNum(reader.readLine());

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							JOptionPane.showMessageDialog(contentPanel, "Waiting", "wait", JOptionPane.INFORMATION_MESSAGE);
							
							Game g = new Game(Minesweeper.isCaptain());
							g.setVisible(true);
							dispose();
						}
					}
				});
				buttonPane.add(okButton);
			}
			
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					dispose();
				}
			});
			buttonPane.add(cancelButton);
		}
	}
}
