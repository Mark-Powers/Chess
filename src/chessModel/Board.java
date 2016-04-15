package chessModel;
import java.util.ArrayList;
public class Board
{
    public int boardWidth;
    public int boardHeight;
    private ArrayList<Piece> pieces = new ArrayList<Piece>();
    
    public Board(){
        boardWidth = 8;
        boardHeight = 8;
        for(int i = 0;i<8;i++){
            pieces.add(new Pawn(1,i,0));
            pieces.add(new Pawn(6,i,1));
        }
        pieces.add(new Rook(0,0,0));
        pieces.add(new Rook(0,7,0));
        pieces.add(new Rook(7,0,1));
        pieces.add(new Rook(7,7,1));
        pieces.add(new Knight(0,1,0));
        pieces.add(new Knight(0,6,0));
        pieces.add(new Knight(7,1,1));
        pieces.add(new Knight(7,6,1));
        pieces.add(new Bishop(0,2,0));
        pieces.add(new Bishop(0,5,0));
        pieces.add(new Bishop(7,2,1));
        pieces.add(new Bishop(7,5,1));
        pieces.add(new Queen(0,3,0));
        pieces.add(new Queen(7,3,1));
        pieces.add(new King(0,4,0));
        pieces.add(new King(7,4,1));
        //display();
    }
    public void move(int oldX, int oldY, int x, int y){
        boolean isEnemy = false;
        int pieceIndex = -1;
        int otherPieceIndex = -1;
        // THIS LOOP SELECTS THE CORRECT PIECE TO MOVE
        for(int i=0;i<pieces.size();i++){
            if(pieces.get(i).x==oldX && pieces.get(i).y==oldY){
                pieceIndex = i;
            }
        }
        // IF WE SELECTED A PIECE, THEN MOVE IT (OR CHECK TO SEE IF WE CAN)
        if(pieceIndex>=0){
            for(int u=0;u<pieces.size();u++){ // FINDS IF THERE IS ANOTHER PIECE WHERE WE ARE GOING (NEEDED FOR PAWN)
                if(u!=pieceIndex && pieces.get(u).x==x && pieces.get(u).y == y){
                    otherPieceIndex = u;
                    isEnemy = true;
                }
            }
            if(pieces.get(pieceIndex).validMove(x,y,isEnemy)){
                pieces.get(pieceIndex).setX(x);
                pieces.get(pieceIndex).setY(y);
                if(isEnemy){
                    pieces.remove(otherPieceIndex);
                    if(otherPieceIndex<pieceIndex){
                         pieceIndex--;
                    }
                }
            }
        }
        //display(); // REWRITES BOARD
    }
     public void display(){
        print(getBoard());
        /*boolean wasPrinted;
        print("  01234567\n\n");
        for(int i = 0;i<boardHeight;i++){
            print(i+" ");
            for(int u = 0;u<boardWidth;u++){
                wasPrinted = false;
                for(Piece piece:pieces){
                    if(piece.x==i && piece.y==u){
                        print(piece.getChar());
                        wasPrinted = true;
                        break;
                    }
                }
                if(!wasPrinted){
                    if((i%2==0&& u%2==0) || (i%2!=0 && u%2!=0))
                        print("O");
                    else
                        print("O");
                }
            }
            print("\n");
        }
        print("\n");*/
    }
    public String getBoard(){
        String board = "";
        boolean wasPrinted;
        board+=("  01234567\n\n");
        for(int i = 0;i<boardHeight;i++){
            board+=(i+" ");
            for(int u = 0;u<boardWidth;u++){
                wasPrinted = false;
                for(Piece piece:pieces){
                    if(piece.x==i && piece.y==u){
                        board+=(piece.getChar());
                        wasPrinted = true;
                        break;
                    }
                }
                if(!wasPrinted){
                    if((i%2==0&& u%2==0) || (i%2!=0 && u%2!=0))
                        board+=("O");
                    else
                        board+=("O");
                }
            }
            board+=("\n");
        }
        board+=("\n");
        return board;
    }
    public void print(String s){
        System.out.print(s);
    }
    
    public ArrayList<Piece> getPieces(){
    	return pieces;
    }
    
    public Piece getPiece(int x, int y){
    	for (Piece piece:pieces){
    		if (x==piece.x&&y==piece.y){
    			return piece;
    		}
    	}
    	return null;
    }
}
