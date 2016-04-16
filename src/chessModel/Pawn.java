package chessModel;
public class Pawn extends Piece
{
    public Pawn(int x, int y, int side)
    {
        this.x = x;
        this.y = y;
        this.side = side;
    }
    public String getChar(){
        if(side==0)
            return "P";
        return "p";
    }
    // TODO Fix this
    public boolean validMove(int x2, int y2, SquareStatus status){
        //if( (side==0 && ( (!isEnemy && ((this.x==1 && x2==3) || (this.x+1==x2))) || (isEnemy && (this.x+1==x2) && (this.y+1==y2 || this.y-1==y2)))) || 
        //   (side==1 && ( (!isEnemy && ((this.x==6 && x2==4) || (this.x-1==x2))) || (isEnemy && (this.x-1==x2) && (this.y-1==y2 || this.y+1==y2))))){
            return true;
        //}
        //return false;
    }
}
