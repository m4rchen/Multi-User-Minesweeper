package minesweeper;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import java.awt.Font;

public class Ranking extends JDialog {

	private final JPanel contentPanel = new JPanel();


	public Ranking() {
		setBounds(100, 100, 600, 400);
	 	getContentPane().setLayout(new BorderLayout());
	 	contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BorderLayout());
		
		JPanel RankingName = new JPanel();
		RankingName.setLayout(new FlowLayout());
		
		JPanel mode = new JPanel();
		mode.setLayout(new GridLayout(1,2));
		JPanel tbmode = new JPanel();
		JPanel rdmode = new JPanel();
		
		JPanel myranking = new JPanel();
		myranking.setLayout(new GridLayout(1,2));
		JPanel mytbranking = new JPanel();
		mytbranking.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JPanel myrdranking = new JPanel();
		myrdranking.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		myranking.add(mytbranking);
		myranking.add(myrdranking);
		
		contentPanel.add(RankingName, "North");
		contentPanel.add(myranking, "South");
		
		JLabel lblRanking = new JLabel("Ranking");
		lblRanking.setFont(new Font("Courier New", Font.BOLD, 30));
		RankingName.add(lblRanking);
		
		
		mode.add(tbmode);
		tbmode.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblTurnbasedMode = new JLabel("Turn-based mode");
		lblTurnbasedMode.setFont(new Font("Courier New", Font.BOLD, 20));
		tbmode.add(lblTurnbasedMode);
		
		String result = new String();
		StringTokenizer st = null;
		
		try {
			BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(Minesweeper.getSocket().getInputStream()));
			
			String str = new String("1#4#");
			str = str.concat(Minesweeper.getIDNUM());
			str = str.concat("#");
			System.out.println(str);
			bos.write(str.getBytes());
			bos.flush();		
			result = reader.readLine();
			System.out.println("Server >> "+ result);
			st = new StringTokenizer(result, "#");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		JLabel tbmode1 = new JLabel();
		tbmode1.setFont(new Font("Courier New", Font.BOLD, 15));
		JLabel tbmode2 = new JLabel();
		tbmode2.setFont(new Font("Courier New", Font.BOLD, 15));
		JLabel tbmode3 = new JLabel();
		tbmode3.setFont(new Font("Courier New", Font.BOLD, 15));
		JLabel tbmode4 = new JLabel();
		tbmode4.setFont(new Font("Courier New", Font.BOLD, 15));
		JLabel tbmode5 = new JLabel();
		tbmode5.setFont(new Font("Courier New", Font.BOLD, 15));

		tbmode1.setText(String.format(" %3d %-10s %-10s", 1, st.nextToken(), st.nextToken() + " / " + st.nextToken()));
		tbmode2.setText(String.format(" %3d %-10s %-10s", 2, st.nextToken(), st.nextToken() + " / " + st.nextToken()));
		tbmode3.setText(String.format(" %3d %-10s %-10s", 3, st.nextToken(), st.nextToken() + " / " + st.nextToken()));
		tbmode4.setText(String.format(" %3d %-10s %-10s", 4, st.nextToken(), st.nextToken() + " / " + st.nextToken()));
		tbmode5.setText(String.format(" %3d %-10s %-10s", 5, st.nextToken(), st.nextToken() + " / " + st.nextToken()));

		tbmode.add(tbmode1);
		tbmode.add(tbmode2);
		tbmode.add(tbmode3);
		tbmode.add(tbmode4);
		tbmode.add(tbmode5);
		
		
		int mytbrank = 999;
		JLabel mytb = new JLabel();
		mytb.setFont(new Font("Courier New", Font.BOLD, 15));
		mytb.setText(String.format("%3d %-10s  %-10s", mytbrank, st.nextToken(), st.nextToken() + " / " + st.nextToken()));
		mytbranking.add(mytb);
		
		
		mode.add(rdmode);
		rdmode.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblRaidMode = new JLabel("Raid mode");
		lblRaidMode.setFont(new Font("Courier New", Font.BOLD, 20));
		rdmode.add(lblRaidMode);
		
		String result2 = new String();
		StringTokenizer st2 = null;

		try {
			BufferedOutputStream bos = new BufferedOutputStream(Minesweeper.getSocket().getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(Minesweeper.getSocket().getInputStream()));
			
			String str = new String("1#4#");
			str = str.concat(Minesweeper.getIDNUM());
			str = str.concat("#");
			System.out.println(str);
			bos.write(str.getBytes());
			bos.flush();
			result2 = reader.readLine();
			System.out.println("Server >> "+ result2);
			st2 = new StringTokenizer(result2, "#");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JLabel rdmode1 = new JLabel();
		JLabel rdmode2 = new JLabel();
		JLabel rdmode3 = new JLabel();
		JLabel rdmode4 = new JLabel();
		JLabel rdmode5 = new JLabel();

		rdmode1.setText(String.format(" %3d %-10s %-10s", 1, st2.nextToken(), st2.nextToken() + " / " + st2.nextToken()));
		rdmode2.setText(String.format(" %3d %-10s %-10s", 2, st2.nextToken(), st2.nextToken() + " / " + st2.nextToken()));
		rdmode3.setText(String.format(" %3d %-10s %-10s", 3, st2.nextToken(), st2.nextToken() + " / " + st2.nextToken()));
		rdmode4.setText(String.format(" %3d %-10s %-10s", 4, st2.nextToken(), st2.nextToken() + " / " + st2.nextToken()));
		rdmode5.setText(String.format(" %3d %-10s %-10s", 5, st2.nextToken(), st2.nextToken() + " / " + st2.nextToken()));


		rdmode.add(rdmode1);
		rdmode1.setFont(new Font("Courier New", Font.BOLD, 15));
		rdmode.add(rdmode2);
		rdmode2.setFont(new Font("Courier New", Font.BOLD, 15));
		rdmode.add(rdmode3);
		rdmode3.setFont(new Font("Courier New", Font.BOLD, 15));
		rdmode.add(rdmode4);
		rdmode4.setFont(new Font("Courier New", Font.BOLD, 15));
		rdmode.add(rdmode5);
		rdmode5.setFont(new Font("Courier New", Font.BOLD, 15));

		
		int myrdrank = 999;
		JLabel myrd = new JLabel();
		myrd.setFont(new Font("Courier New", Font.BOLD, 15));
		myrd.setText(String.format(" %3d %-10s  %-10s", myrdrank, st2.nextToken(), st2.nextToken() + " / " + st2.nextToken()));
		myrdranking.add(myrd);
		
		contentPanel.add(mode);
	}
}
