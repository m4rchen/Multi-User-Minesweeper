package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class Game extends JFrame {

	final String TITLE_OF_PROGRAM = "Minesweeper";
	final String SIGN_OF_FLAG = "f";
	static Canvas canvas;
	final int BLOCK_SIZE = 30;
	final static int FIELD_SIZE = 20;
	final int CHAT_AREA_SIZE = 310;
	final int FIELD_DY = 80;
	final int START_LOCATION = 200;
	final static int NUMBER_OF_MINES = 30;
	final int[] COLOR_OF_NUMBERS = { 0x0000FF, 0x008000, 0xFF0000, 0x800000, 0x0 };

	static Cell[][] field = new Cell[FIELD_SIZE][FIELD_SIZE];
	Random random;
	static int countOpenedCells;
	static boolean youWon; 
	static boolean bangMine;
	static int bangX; 
	static int bangY;

	private static JPanel contentPane;
	private JTextField txtF;
	public static JTextArea txtA;
	static boolean turn;

	static int x = 0;
	static int y = 0;
	static int user, mode;
	static String opponent;
	boolean captain;

	public Game(boolean captain) {
				
		String seed = "3";
		random = new Random(Integer.valueOf(seed));
		JPanel game = new JPanel();
		game.setLayout(new BorderLayout());

		String gameMode = new String();
		String numOfPlayers = new String();

		txtA = new JTextArea("", 0, 0);
		txtF = new JTextField();
		canvas = new Canvas();
		JScrollPane scrollPane = new JScrollPane(txtA);
		JPanel chatPanel = new JPanel();
		JPanel userPanel = new JPanel();
		JPanel user1 = new JPanel();
		JPanel userinfo1 = new JPanel();
		JLabel userName1 = new JLabel();
		JButton userReady1 = new JButton("READY");
		JPanel user2 = new JPanel();
		JPanel userinfo2 = new JPanel();
		JLabel userName2 = new JLabel();
		JPanel bothPanel = new JPanel();
		userPanel.setLayout(new GridLayout(1, 2));
		userinfo1.setLayout(new FlowLayout(FlowLayout.LEFT));
		userinfo2.setLayout(new FlowLayout(FlowLayout.LEFT));
		bothPanel.setLayout(new BorderLayout());
		chatPanel.setLayout(new BorderLayout());
		bothPanel.setPreferredSize(new Dimension(300, 650));
		userPanel.setPreferredSize(new Dimension(300, 100));
		chatPanel.add(scrollPane, BorderLayout.CENTER);
		userPanel.add(user1);
		user1.setLayout(new GridLayout(2, 1));
		userinfo1.setLayout(new FlowLayout(FlowLayout.CENTER));

		String username1 = new String();
		String username2 = new String();
		opponent = new String();
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(Minesweeper.getSocket().getInputStream()));
			opponent = reader.readLine();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		this.captain = captain;
		if (captain == true) {
			username1 = Minesweeper.getID();
			username2 = opponent;
			turn = true;
		} else {
			username1 = opponent;
			username2 = Minesweeper.getID();
			turn = false;
		}
		
		userName1.setText(username1);
		userName2.setText(username2);
		userinfo1.add(userName1);
		user1.add(userinfo1);
		user1.add(userReady1);
		userPanel.add(user2);
		userinfo2.add(userName2);
		user2.add(userinfo2);
		bothPanel.add(BorderLayout.NORTH, userPanel);
		bothPanel.add(BorderLayout.CENTER, chatPanel);

		setTitle(TITLE_OF_PROGRAM);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(START_LOCATION, START_LOCATION, FIELD_SIZE * BLOCK_SIZE + CHAT_AREA_SIZE,
				FIELD_SIZE * BLOCK_SIZE + FIELD_DY);
		setResizable(false);

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		canvas.setBackground(Color.white);

		game.add(BorderLayout.CENTER, canvas);
		contentPane.add(BorderLayout.CENTER, game);

		canvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				if (turn == true) { 
					x = e.getX() / BLOCK_SIZE;
					y = e.getY() / BLOCK_SIZE;

					try {
						BufferedOutputStream bos = new BufferedOutputStream(
								Minesweeper.getSocket().getOutputStream());
						String str = new String("2#1#");
						str = str.concat(String.valueOf(user));
						str = str.concat("#");
						str = str.concat(String.valueOf(x));
						str = str.concat("#");
						str = str.concat(String.valueOf(y));
						str = str.concat("#");

						bos.write(str.getBytes());
						bos.flush();
					} catch (Exception e1) {
						// TODO: handle exception
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "It isn't your turn.", "Warning", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		 
		txtF.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (txtF.getText().equals("")) {
						return;
					}

					try {
						BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
						BufferedReader reader = new BufferedReader(
								new InputStreamReader(Minesweeper.getSocket().getInputStream()));
						String str = new String("3#");
						str = str.concat(Minesweeper.getRoomNum());
						str = str.concat("#");
						str = str.concat(Minesweeper.getIDNUM());
						str = str.concat("#");
						str = str.concat("[" + Minesweeper.getID() + "] " + txtF.getText() + "\n");
						str = str.concat("#");
						bos.write(str.getBytes());
						bos.flush();
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					txtF.setText("");
				} 
			}
		});
		  
		
		contentPane.add(BorderLayout.SOUTH, txtF);
		contentPane.add(BorderLayout.EAST, bothPanel);

		setVisible(true);
		initField();
		
		Thread receiverT = new Thread(new ClientReceiver());
		
		receiverT.start();
		
	}

	static void openCells(int x, int y) { 
		if (x < 0 || x > FIELD_SIZE - 1 || y < 0 || y > FIELD_SIZE - 1)
			return; 
		if (!field[y][x].isNotOpen())
			return; 
		field[y][x].open();
		if (field[y][x].getCountBomb() > 0 || field[y][x].isMine)
			return; 
		for (int dx = -1; dx < 2; dx++)
			for (int dy = -1; dy < 2; dy++)
				openCells(x + dx, y + dy);
	}

	void initField() {
		int x, y, countMines = 0;
		for (x = 0; x < FIELD_SIZE; x++)
			for (y = 0; y < FIELD_SIZE; y++)
				field[y][x] = new Cell();
		while (countMines < NUMBER_OF_MINES) {
			do {
				x = random.nextInt(FIELD_SIZE);
				y = random.nextInt(FIELD_SIZE);
			} while (field[y][x].isMined());
			field[y][x].mine();
			countMines++;
		}
		for (x = 0; x < FIELD_SIZE; x++)
			for (y = 0; y < FIELD_SIZE; y++)
				if (!field[y][x].isMined()) {
					int count = 0;
					for (int dx = -1; dx < 2; dx++)
						for (int dy = -1; dy < 2; dy++) {
							int nX = x + dx;
							int nY = y + dy;
							if (nX < 0 || nY < 0 || nX > FIELD_SIZE - 1 || nY > FIELD_SIZE - 1) {
								nX = x;
								nY = y;
							}
							count += (field[nY][nX].isMined()) ? 1 : 0;
						}
					field[y][x].setCountBomb(count);
				}
	}

	class Cell {
		private int countBombNear;
		private boolean isOpen, isMine, isFlag;

		void open() {
			isOpen = true;
			bangMine = isMine;
			if (!isMine)
				countOpenedCells++;
		}

		void mine() {
			isMine = true;
		}

		void setCountBomb(int count) {
			countBombNear = count;
		}

		int getCountBomb() {
			return countBombNear;
		}

		boolean isNotOpen() {
			return !isOpen;
		}

		boolean isMined() {
			return isMine;
		}

		void inverseFlag() {
			isFlag = !isFlag;
		}

		void paintBomb(Graphics g, int x, int y, Color color) {
			g.setColor(color);
			g.fillRect(x * BLOCK_SIZE + 7, y * BLOCK_SIZE + 10, 18, 10);
			g.fillRect(x * BLOCK_SIZE + 11, y * BLOCK_SIZE + 6, 10, 18);
			g.fillRect(x * BLOCK_SIZE + 9, y * BLOCK_SIZE + 8, 14, 14);
			g.setColor(Color.white);
			g.fillRect(x * BLOCK_SIZE + 11, y * BLOCK_SIZE + 10, 4, 4);
		}

		void paintString(Graphics g, String str, int x, int y, Color color) {
			g.setColor(color);
			g.setFont(new Font("", Font.BOLD, BLOCK_SIZE));
			g.drawString(str, x * BLOCK_SIZE + 8, y * BLOCK_SIZE + 26);
		}

		void paint(Graphics g, int x, int y) {
			g.setColor(Color.lightGray);
			g.drawRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
			if (!isOpen) {
				if ((bangMine || youWon) && isMine)
					paintBomb(g, x, y, Color.black);
				else {
					g.setColor(Color.lightGray);
					g.fill3DRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, true);
					if (isFlag)
						paintString(g, SIGN_OF_FLAG, x, y, Color.red);
				}
			} else if (isMine)
				paintBomb(g, x, y, bangMine ? Color.red : Color.black);
			else if (countBombNear > 0)
				paintString(g, Integer.toString(countBombNear), x, y, new Color(COLOR_OF_NUMBERS[countBombNear - 1]));
		}
	}
	
	public static JTextArea getArea() {
		return txtA;
	}
	
	static class ClientReceiver extends Thread { 
		BufferedReader reader;
		String msg;
		boolean check1, check2;
        ClientReceiver() {
        	try {
        		msg = new String();
				reader = new BufferedReader(
						new InputStreamReader(Minesweeper.getSocket().getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        public void run() {
            while (true) {
				try {
					msg = reader.readLine();
					System.out.println(msg);
					check1 = msg.contains(opponent);
					check2 = msg.contains(Minesweeper.getID());
					System.out.println(check1 + " " + check2); 
					
					if (!(check1 || check2)) { 
						StringTokenizer st;
						BufferedOutputStream bos = new BufferedOutputStream(
								Minesweeper.getSocket().getOutputStream());
						st = new StringTokenizer(msg, "#");
						st.nextToken();
						st.nextToken();
						st.nextToken();
						x = Integer.parseInt(st.nextToken());
						y = Integer.parseInt(st.nextToken());
						if (!bangMine && !youWon) {
							if (field[y][x].isNotOpen()) {
								openCells(x, y);
								youWon = (countOpenedCells == FIELD_SIZE * FIELD_SIZE - NUMBER_OF_MINES);
								if (bangMine) {
									bangX = x;
									bangY = y;
									try {
										String str = new String("2#6#");
										if (mode == 1) {
											if (turn) {
												str = str.concat(Minesweeper.getRoomNum() + "#" + Minesweeper.getIDNUM());
												str = str.concat("#");
												str = str.concat("0");
												str = str.concat("#");
												JOptionPane.showMessageDialog(contentPane, "You lose.", "Unfortunately", JOptionPane.INFORMATION_MESSAGE);

											} else {
												str = str.concat(Minesweeper.getRoomNum() + "#" + Minesweeper.getIDNUM());
												str = str.concat("#");
												str = str.concat("1");
												str = str.concat("#");	
												JOptionPane.showMessageDialog(contentPane, "You win!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
											}
											bos.write(str.getBytes());
											bos.flush();
										} else if (mode == 2) {
											str = str.concat(Minesweeper.getRoomNum() + "#" + Minesweeper.getIDNUM());
											str = str.concat("#");
											str = str.concat("0");
											str = str.concat("#");
											JOptionPane.showMessageDialog(contentPane, "You lose.", "Unfortunately", JOptionPane.INFORMATION_MESSAGE);
										}										
									} catch (IOException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							}
							canvas.repaint();
						} else if (youWon) {
							try {
								String str = new String("2#6#");
								if (mode == 1) {
									if (turn) {
										str = str.concat(Minesweeper.getRoomNum() + "#" + Minesweeper.getIDNUM());
										str = str.concat("#");
										str = str.concat("1");
										str = str.concat("#");
										JOptionPane.showMessageDialog(contentPane, "You win!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);

									} else {
										str = str.concat(Minesweeper.getRoomNum() + "#" + Minesweeper.getIDNUM());
										str = str.concat("#");
										str = str.concat("0");
										str = str.concat("#");	
										JOptionPane.showMessageDialog(contentPane, "You lose.", "Unfortunately", JOptionPane.INFORMATION_MESSAGE);
									}
									bos.write(str.getBytes());
									bos.flush();
								} else if (mode == 2) {
									str = str.concat(Minesweeper.getRoomNum() + "#" + Minesweeper.getIDNUM());
									str = str.concat("#");
									str = str.concat("1");
									str = str.concat("#");
									JOptionPane.showMessageDialog(contentPane, "You win!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
								}							
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

						turn = !turn;
					} else { 
						Game.getArea().append(msg+"\n");
						Game.getArea().setCaretPosition(Game.getArea().getDocument().getLength());
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }



	class Canvas extends JPanel {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			for (int x = 0; x < FIELD_SIZE; x++)
				for (int y = 0; y < FIELD_SIZE; y++)
					field[y][x].paint(g, x, y);
		}
	}
}
