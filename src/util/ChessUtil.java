package util;

public class ChessUtil {
	public static char convertFile(int val) {
		return (char) (val + 97);
	}

	public static int convertRow(int number, int boardHeight) {
		return boardHeight - (number);
	}

	public static int convertRow(int number){
		return convertRow(number,8);
	}

	public static String convertLocation(int xLoc,int yLoc){
		return ChessUtil.convertFile(yLoc) + "" + ChessUtil.convertRow(xLoc);
	}
}
