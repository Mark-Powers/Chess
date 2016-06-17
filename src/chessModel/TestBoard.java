package chessModel;

import java.util.ArrayList;

public class TestBoard extends Board {
	public TestBoard(){
		super();
		pieces = new ArrayList<Piece>();
	}
	
	public void addPiece(Piece p){
		pieces.add(p);
	}
	
	public void removePieces(Piece p){
		pieces.remove(p);
	}
}
