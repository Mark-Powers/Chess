package chessModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Log {

	private ArrayList<Integer[]> logArr;

	public Log() {
		logArr = new ArrayList<Integer[]>();
	}

	public ArrayList<Integer[]> getLogArray() {
		return logArr;
	}

	public void addToLog(int oldX, int oldY, int x, int y, int moveNo, int currentTeamNo) {
		Integer[] numsForLog = new Integer[6];
		numsForLog[0] = oldX;
		numsForLog[1] = oldY;
		numsForLog[2] = x;
		numsForLog[3] = y;
		numsForLog[4] = moveNo;
		numsForLog[5] = currentTeamNo;
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
		for (Integer[] nums : l.getLogArray()) {
			if (nums[5] == 0) {
				logText.append(nums[4] + ". ");
			}
			logText.append(convertChar(nums[0]));
			logText.append(nums[1] + 1);
			logText.append("-");
			logText.append(convertChar(nums[2]));
			logText.append(nums[3] + 1);
			logText.append(" ");
			if (nums[4] % 4 == 0 && nums[5] == 1) {
				logText.append("\r\n");
			}
		}
		return logText.toString();
	}

}
