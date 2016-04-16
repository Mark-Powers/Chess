package chessModel;

public class Knight extends Piece
{
    public Knight(int x, int y, int side)
    {
        this.x = x;
        this.y = y;
        this.side = side;
    }
    public String getChar(){
        if(side==0)
            return "N";
        return "n";
    }
    public boolean validMove(int x, int y, SquareStatus status){
    	return true;
    }
}
