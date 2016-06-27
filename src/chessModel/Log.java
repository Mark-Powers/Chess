package chessModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Log {
	
	private int fullMoveClock, halfMoveClock;

	private ArrayList<Integer[]> logArr;

	public Log(){
		logArr = new ArrayList<Integer[]>();
		fullMoveClock = 1;
		halfMoveClock = 0;
	}
	
	public ArrayList<Integer[]> getLogArray() {
		return logArr;
	}

	public void addToLog(int oldX, int oldY, int x, int y) {
		int side = logArr.size()%2;
		Integer[] numsForLog = new Integer[4];
		numsForLog[0] = oldX;
		numsForLog[1] = oldY;
		numsForLog[2] = x;
		numsForLog[3] = y;
		logArr.add(numsForLog);
		if (side == 1){
			fullMoveClock++;
		}
	}
	
	public void resetHalfMoveClock(){
		halfMoveClock = 0;
	}
	
	public void incrementHalfMoveClock(){
		halfMoveClock++;
	}

	public String toString() {
		return toPGN();
	}

	public static char convertChar(int val) {
		return (char) (val + 97);
	}

	public String toPGN() {
		StringBuilder logText = new StringBuilder();
		logText.append("[Date \"" + new SimpleDateFormat("YYYY:MM:dd").format(new Date()) + "\"]\r\n");
		logText.append("[Time \"" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "\"]\r\n\r\n");
		
		int moveNo = 1;
		for (Integer[] nums : logArr) {
			int side = moveNo % 2;
			
			if (side == 1) {
				int turnNo = (moveNo + 1) /2;
				logText.append(turnNo + ". ");
			}
			
			logText.append(convertChar(nums[0]));
			logText.append(nums[1] + 1);
			logText.append("-");
			logText.append(convertChar(nums[2]));
			logText.append(nums[3] + 1);
			logText.append(" ");
			if (moveNo % 4 == 0 && side == 1) {
				logText.append("\r\n");
			}
			moveNo++;
		}
		return logText.toString();
	}
	
	public int getFullMoveCount(){
		return fullMoveClock;
	}

	public int getHalfMoveCount() {
		return halfMoveClock;
	}

}
