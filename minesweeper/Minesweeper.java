package minesweeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Minesweeper {
    private static Socket socket;
    private static String idnum;
	private static boolean captain = false;
	private static String id;
	private static String roomnum;

    final String TITLE_OF_PROGRAM = "Minesweeper";

    public Minesweeper() throws UnknownHostException, IOException {
        socket = new Socket("192.168.59.128", 9190);
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void setIDNUM(String num) {
        idnum = num;
    }

    public static String getIDNUM() {
        return idnum;
    }
    
    public static void setCaptain() {
    	captain = true;
    }
    
    public static boolean isCaptain() {
    	return captain;
    }
    
    public static String getID() {
    	return id;
    }
    
    public static void setID(String userID) {
    	id = userID;
    }
    
    public static void setRoomNum(String rnum) {
    	roomnum = rnum;
    }
    
    public static String getRoomNum() {
    	return roomnum;
    }

    public static void main(String[] args) {
        try {
            Minesweeper program = new Minesweeper();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Login log = new Login();
        log.setVisible(true);
    }
}