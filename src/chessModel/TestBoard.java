package chessModel;

import java.util.ArrayList;

import chessModel.piece.Piece;

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
