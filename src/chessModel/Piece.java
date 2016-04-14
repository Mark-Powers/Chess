package chessModel;
public abstract class Piece
{
    int x;
    int y;
    int side;
    public boolean validMove(int x, int y, boolean attacked){
       return false;
    }
    public String getChar(){
        return "O";
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public String toString(){
        return "x = "+x+"\ny = "+y +"\n";
    }
}
