package chessModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChessLog {

	private StringBuilder logText;
	private ArrayList<Integer[]> logArr;
	
	public ChessLog(){
		logText = new StringBuilder();
		logText.append("[Date \""+new SimpleDateFormat("YYYY:MM:dd").format(new Date())+"\"]\n\n");
		logText.append("[Time \""+new SimpleDateFormat("HH:mm:ss").format(new Date())+"\"]\n");
		logArr = new ArrayList<Integer[]>();
	}
	
	public void addToLog(int oldX, int oldY, int x, int y, Board b) {
		Integer[] numsForLog = new Integer[4];
		numsForLog[0] = oldX;
		numsForLog[1] = oldY;
		numsForLog[2] = x;
		numsForLog[3] = y;
		logArr.add(numsForLog);
		
		if (b.currentTeamNo == 0){
			logText.append(b.moveNo + ". ");
		}
		logText.append(convertChar(oldX));
		logText.append(oldY+1);
		logText.append("-");
		logText.append(convertChar(x));
		logText.append(y+1);
		logText.append(" ");
		if (b.moveNo % 4==0 && b.currentTeamNo == 1){
			logText.append("\r\n");
		}
	}
	
	public String toString(){
		return logText.toString();
	}

	public static char convertChar(int val){
		return (char) (val + 97);
	}

}
