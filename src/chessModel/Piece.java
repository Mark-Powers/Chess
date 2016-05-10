package chessModel;
public abstract class Piece
{
    protected int x;    
    protected int y;
    protected int side;
        
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public int getSide(){
    	return side;
    }
    public boolean move(int x, int y, SquareStatus status){
    	if(validMove(x, y, status)){
    		this.x = x;
    		this.y = y;
    		return true;
    	}
    	return false;
    }
    public void forceMove(int x, int y){
    	this.x = x;
    	this.y = y;
    }
    @Override
    public boolean equals(Object other){
    	if(other instanceof Piece){
    		Piece p = (Piece)other;
    		return (this.x == p.getX() && this.y == p.getY() && this.side == p.getSide() && this.getClass() == p.getClass());
    	}
    	return false;
    	
    }
    @Override
    public int hashCode(){
    	int multiplier = (side == 0 ? 1 : -1);
    	return (x^y)*multiplier;
    }
    public abstract String getChar();
    public abstract int getValue();
    public abstract boolean validMove(int x, int y, SquareStatus status);
}
