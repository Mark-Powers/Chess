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
    	int xDiff = Math.abs(x-this.x);
    	int yDiff = Math.abs(y-this.y);
    	return (xDiff != 3 && yDiff != 3 && (xDiff+yDiff)==3);
    }
    public int getValue(){
    	return 3;
    }
}
