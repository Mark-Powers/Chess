package chessModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import chessModel.piece.Pawn;
import chessModel.piece.Piece;
import util.ChessUtil;

public class Log {

	private int fullMoveClock, halfMoveClock;

	// keeps track of raw moves
	private ArrayList<Integer[]> rawlog;

	// Standard Algebraic notation log
	private ArrayList<String> sanlog;
	
	private String player1, player2;
	
	private String time, date;

	public Log() {
		rawlog = new ArrayList<Integer[]>();
		sanlog = new ArrayList<String>();
		fullMoveClock = 1;
		halfMoveClock = 0;
		date = new SimpleDateFormat("YYYY:MM:dd").format(new Date());
		time = new SimpleDateFormat("HH:mm:ss").format(new Date());
	}
	
	public void setPlayerNames(String player1Name, String player2Name){
		player1 = player1Name;
		player2 = player2Name;
	}

	public ArrayList<Integer[]> getLogArray() {
		return rawlog;
	}

	public void addToLog(int oldX, int oldY, int x, int y, Board board, Piece piece, Piece capture) {
		int side = rawlog.size() % 2;

		// update rawlog
		Integer[] numsForLog = new Integer[4];
		numsForLog[0] = oldX;
		numsForLog[1] = oldY;
		numsForLog[2] = x;
		numsForLog[3] = y;
		rawlog.add(numsForLog);
		if (side == 1) {
			fullMoveClock++;
		}
		
		String center = "-";
		if (capture != null){
			center = "x";
		}
		
		// SAN log
		if (piece instanceof Pawn && Math.abs(oldX - x) == 2) {
			sanlog.add(ChessUtil.convertLocation(x, y));
		} else {
			sanlog.add(piece.getChar().toUpperCase() + ChessUtil.convertLocation(oldX, oldY) + center
					+ ChessUtil.convertLocation(x, y));
		}
	}

	public void resetHalfMoveClock() {
		halfMoveClock = 0;
	}

	public void incrementHalfMoveClock() {
		halfMoveClock++;
	}

	public String toString() {
		return toPGN();
	}

	public String toPGN() {
		StringBuilder logText = new StringBuilder();
		logText.append("[Date \"" + date + "\"]\r\n");
		logText.append("[Time \"" + time + "\"]\r\n\r\n");
		if (player1 != null){
			logText.append("[White \"" + player1 + "\"]\r\n\r\n");
		}
		if (player2 != null){
			logText.append("[Black \"" + player2 + "\"]\r\n\r\n");
		}

		int moveNo = 1;
		for (String entry : sanlog) {
			int side = moveNo % 2;

			if (side == 1) {
				int turnNo = (moveNo + 1) / 2;
				logText.append(turnNo + ". ");
			}

			logText.append(entry);
			logText.append(" ");
			if (moveNo % 6 == 0) {
				logText.append("\r\n");
				System.out.println("new line");
			}
			moveNo++;
		}
		return logText.toString();
	}

	public int getFullMoveCount() {
		return fullMoveClock;
	}

	public int getHalfMoveCount() {
		return halfMoveClock;
	}

}
