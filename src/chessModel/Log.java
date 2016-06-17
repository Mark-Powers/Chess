package chessModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Log {

	private static ArrayList<Integer[]> logArr = new ArrayList<Integer[]>();

	public static ArrayList<Integer[]> getLogArray() {
		return logArr;
	}

	public void addToLog(int oldX, int oldY, int x, int y) {
		Integer[] numsForLog = new Integer[4];
		numsForLog[0] = oldX;
		numsForLog[1] = oldY;
		numsForLog[2] = x;
		numsForLog[3] = y;
		logArr.add(numsForLog);
	}

	public String toString() {
		return Log.toPGN(this);
	}

	public static char convertChar(int val) {
		return (char) (val + 97);
	}

	public static String toPGN(Log l) {
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

}
