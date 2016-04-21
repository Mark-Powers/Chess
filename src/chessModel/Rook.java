package chessModel;
public class Rook extends Piece
{
    public Rook(int x, int y, int side)
    {
        this.x = x;
        this.y = y;
        this.side = side;
    }
    public String getChar(){
        if(side==0)
            return "R";
        return "r";
    }
    public boolean validMove(int x, int y, SquareStatus status){
        return ( this.x==x || this.y==y );
    }
    public int getValue(){
    	return 5;
    }
}
