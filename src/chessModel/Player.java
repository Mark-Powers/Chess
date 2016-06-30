package chessModel;

public abstract class Player {
	
	protected String name;
	protected int side;
	
	public Player(String n, int s){
		name = n;
		side = s;
	}
	
	public String getName(){
		return name;
	}
	
}
