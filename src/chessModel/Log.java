package chessModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import chessModel.piece.Pawn;
import chessModel.piece.Piece;
import util.ChessUtil;

public class Log {

	private int fullMoveClock, halfMoveClock;

	// keeps track of raw moves
	private ArrayList<Integer[]> rawlog;
	
	// Standard Algebraic notation log
	private ArrayList<String> sanlog;

	public Log() {
		rawlog = new ArrayList<Integer[]>();
		sanlog = new ArrayList<String>();
		fullMoveClock = 1;
		halfMoveClock = 0;
	}

	public ArrayList<Integer[]> getLogArray() {
		return rawlog;
	}

	public void addToLog(int oldX, int oldY, int x, int y, Piece piece, Piece capture) {
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

		// SAN log
		if (piece instanceof Pawn && Math.abs(oldX-x) == 2){
			sanlog.add(ChessUtil.convertFile(x) + "" + ChessUtil.convertRow(y));
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
		logText.append("[Date \"" + new SimpleDateFormat("YYYY:MM:dd").format(new Date()) + "\"]\r\n");
		logText.append("[Time \"" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "\"]\r\n\r\n");

		int moveNo = 1;
		for (String entry : sanlog) {
			int side = moveNo % 2;

			if (side == 1) {
				int turnNo = (moveNo + 1) / 2;
				logText.append(turnNo + ". ");
			}

			logText.append(entry);
			logText.append(" ");
			if (moveNo % 4 == 0 && side == 1) {
				logText.append("\r\n");
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
