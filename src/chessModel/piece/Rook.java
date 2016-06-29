package chessModel.piece;

import chessModel.SquareStatus;

public class Rook extends Piece
{
	public static int KINGSIDE = 0;
	public static int QUEENSIDE = 1;
	int corner;
	boolean hasMoved;
    public Rook(int x, int y, int side)
    {
        this.x = x;
        this.y = y;
        if (y == 0){
        	corner = KINGSIDE;
        } else {
        	corner = QUEENSIDE;
        }
        this.side = side;
        hasMoved = false;
    }
    public String getChar(){
        if(side==0)
            return "R";
        return "r";
    }
    public boolean move(int x, int y, SquareStatus status){
    	if(validMove(x, y, status)){
    		this.x = x;
    		this.y = y;
    		hasMoved = true;
    		return true;
    	}
    	return false;
    }
    public boolean validMove(int x, int y, SquareStatus status){
        return ( (this.x==x || this.y==y) && x!=y);
    }
    public int getValue(){
    	return 5;
    }
    public int getCorner(){
    	return corner;
    }
    public boolean hasMoved(){
    	return hasMoved;
    }
}
