package chessModel;
/**
 * Simple Class to format the time for chess clocks
 * Only uses Hours and Minutes
 * @author Mark
 */
public class Time {
	private int time;
	
	public Time(int seconds){
		time = seconds;
	}
	
	public String getTime(){
		StringBuffer sb = new StringBuffer();
		int min = time/60;
		int sec = time%60;
		sb.append(min);
		sb.append(":");
		sb.append(sec);
		return sb.toString();
	}
	
	public boolean isZero(){
		return time == 0;
	}
	
	public void dec(){
		if(time>0){
			time--;
		}
	}
	
	public int getUnformmatedTime(){
		return time;
	}
	
}
