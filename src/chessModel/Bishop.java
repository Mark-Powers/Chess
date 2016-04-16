package chessModel;

public class Bishop extends Piece
{
    public Bishop(int x, int y, int side)
    {
        this.x = x;
        this.y = y;
        this.side = side;
    }
    public String getChar(){
        if(side==0)
            return "B";
        return "b";
    }
    public boolean validMove(int x, int y, SquareStatus status){
        if((Math.abs(this.x-x) == Math.abs(this.y-y))){
            return true;
        }
        return false;
    }
}
