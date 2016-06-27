package chessModel;

import java.util.ArrayList;

import chessModel.piece.Piece;

public class TestBoard extends Board {
	public TestBoard() {
		super();
		pieces = new ArrayList<Piece>();
	}

	public void addPiece(Piece p) {
		pieces.add(p);
	}

	public void removePieces(Piece p) {
		pieces.remove(p);
	}

	public void populateFromFEN(String fen) {
		char[] charArray = fen.toCharArray();
		int x = -1;
		int y = 0;
		for (int i = 0; i < charArray.length; i++) {
			Character c = charArray[i];
			if (c == ' ') {
				return;
			} else if (c == '/') {
				y++;
				x = -1;
			} else if (Character.isDigit(c)) {
				int offset = Character.getNumericValue(c);
				x += offset;
			} else {
				x++;
				int side = 0;
				if (Character.isLowerCase(c)) {
					side = 1;
				}
				c = Character.toLowerCase(c);
				switch (c) {
				case 'k':
					addPiece(new King(y, x, side));
					break;
				case 'q':
					addPiece(new Queen(y, x, side));
					break;
				case 'r':
					addPiece(new Rook(y, x, side));
					break;
				case 'b':
					addPiece(new Bishop(y, x, side));
					break;
				case 'n':
					addPiece(new Knight(y, x, side));
					break;
				case 'p':
					addPiece(new Pawn(y, x, side));
					break;
				}
			}
		}
	}
}
