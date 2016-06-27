package chessModel.piece;

import chessModel.SquareStatus;

public class Rook extends Piece
{
	boolean canCastle;
    public Rook(int x, int y, int side)
    {
        this.x = x;
        this.y = y;
        this.side = side;
        canCastle = true;
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
    		canCastle = false;
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
}
