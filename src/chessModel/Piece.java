package chessModel;
public abstract class Piece
{
    protected int x;
    protected int y;
    protected int side;
    public boolean validMove(int x, int y, SquareStatus status){
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
    public int getX(){
    	return x;
    }
    public int getY(){
    	return y;
    }
    public String toString(){
        return "x = "+x+"\ny = "+y +"\n";
    }
    public int getSide(){
    	return side;
    }
}
