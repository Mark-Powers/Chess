package chessModel;

public class Queen extends Piece
{
    public Queen(int x, int y, int side)
    {
        this.x = x;
        this.y = y;
        this.side = side;
    }
    public String getChar(){
        if(side==0)
            return "Q";
        return "q";
    }
    public boolean validMove(int x, int y, SquareStatus status){
        return ( ( this.x==x || this.y==y ) || (Math.abs(this.x-x) == Math.abs(this.y-y)) );
    }
}
