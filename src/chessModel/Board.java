package chessModel;

import java.util.ArrayList;

import chessModel.piece.Bishop;
import chessModel.piece.King;
import chessModel.piece.Knight;
import chessModel.piece.Pawn;
import chessModel.piece.Piece;
import chessModel.piece.Queen;
import chessModel.piece.Rook;
import util.ChessUtil;

public class Board {
	public final int boardWidth;
	public final int boardHeight;
	protected ArrayList<Piece> pieces = new ArrayList<Piece>();
	private Log movelog;
	private int whiteScore;
	private int blackScore;
	private Rook blackKingSide, whiteKingSide, blackQueenSide, whiteQueenSide;
	private King blackKing, whiteKing;
	private String enPassantTarget;

	public Board() {
		this(true);
	}

	public Board(boolean preConfigure) {
		boardWidth = 8;
		boardHeight = 8;
		whiteScore = 0;
		blackScore = 0;

		if (preConfigure) {
			for (int i = 0; i < 8; i++) {
				pieces.add(new Pawn(1, i, 1));
				pieces.add(new Pawn(6, i, 0));
			}

			// TODO fix this
			blackQueenSide = new Rook(0, 0, 1);
			pieces.add(blackQueenSide);
			blackKingSide = new Rook(0, 7, 1);
			pieces.add(blackKingSide);
			whiteQueenSide = new Rook(7, 0, 0);
			pieces.add(whiteQueenSide);
			whiteKingSide = new Rook(7, 7, 0);
			pieces.add(whiteKingSide);

			pieces.add(new Knight(0, 1, 1));
			pieces.add(new Knight(0, 6, 1));
			pieces.add(new Knight(7, 1, 0));
			pieces.add(new Knight(7, 6, 0));
			pieces.add(new Bishop(0, 2, 1));
			pieces.add(new Bishop(0, 5, 1));
			pieces.add(new Bishop(7, 2, 0));
			pieces.add(new Bishop(7, 5, 0));
			pieces.add(new Queen(0, 3, 1));
			pieces.add(new Queen(7, 3, 0));
			whiteKing = new King(0, 4, 1);
			pieces.add(whiteKing);
			blackKing = new King(7, 4, 0);
			pieces.add(blackKing);
		}

		enPassantTarget = "";
		movelog = new Log();
	}

	/**
	 * Attempts to move a piece on the board. If successful, the move is written
	 * to the Log.
	 * 
	 * @param oldX
	 * @param oldY
	 * @param x
	 * @param y
	 * @return If the move was succesful
	 */
	public boolean move(int oldX, int oldY, int x, int y) {
		Piece selectedP = null;
		Piece otherP = null;
		SquareStatus status = SquareStatus.EMPTY;
		// Sets selectedP to the right piece
		selectedP = getPiece(oldX, oldY);

		// create a row-file string for the place the piece is moving to
		String selectedPLocationString = ChessUtil.convertFile(y) + "" + ChessUtil.convertRow(x);

		if (selectedP != null) {
			otherP = getPiece(x, y);
			if (otherP != null && otherP != selectedP) {
				if (otherP.getSide() == selectedP.getSide()) {
					status = SquareStatus.TEAM;
				}
				if (otherP.getSide() != selectedP.getSide()) {
					status = SquareStatus.ENEMY;
				}
			}

			if (selectedPLocationString.equals(enPassantTarget)) {
				status = SquareStatus.ENEMY;
				if (selectedP.getSide() == 0) {
					otherP = getPiece(x + 1, y);
				} else {
					otherP = getPiece(x - 1, y);
				}
			}

			if (!status.equals(SquareStatus.TEAM) && selectedP.validMove(x, y, status)
					&& !isObstructed(selectedP, x, y)) {
				if (isInCheck(selectedP.getSide())) {
					if (!resolvesCheck(selectedP, x, y)) {
						return false;
					}
				}
				selectedP.move(x, y, status);

				if (selectedP instanceof Pawn) {
					movelog.resetHalfMoveClock();
					if (Math.abs(oldX - x) == 2) {
						enPassantTarget = ChessUtil.convertFile(y)
								+ String.valueOf(ChessUtil.convertRow((oldX + x) / 2));
					} else {
						enPassantTarget = "";
					}
				} else {
					movelog.incrementHalfMoveClock();
					enPassantTarget = "";
				}
				if (status.equals(SquareStatus.ENEMY)) {
					int scoreEarned = otherP.getValue();
					if (selectedP.getSide() == 0) {
						whiteScore += scoreEarned;
					} else if (selectedP.getSide() == 1) {
						blackScore += scoreEarned;
					}
					pieces.remove(otherP);
					movelog.resetHalfMoveClock();
				}
				Integer[] numsForLog = new Integer[4];
				numsForLog[0] = oldX;
				numsForLog[1] = oldY;
				numsForLog[2] = x;
				numsForLog[3] = y;
				movelog.addToLog(oldX, oldY, x, y,selectedP,otherP);
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if there is a piece between a piece and a specified square.
	 * 
	 * @param p
	 *            The piece in question
	 * @param x
	 *            The x location of the other square
	 * @param y
	 *            The y location of the other square
	 * @return
	 */
	public boolean isObstructed(Piece p, int x, int y) {
		// Must be left/right
		if (p.getX() == x) {
			int dir = y > p.getY() ? 1 : -1;
			for (int tmpY = p.getY() + dir; tmpY != y; tmpY += dir) {
				if (getPiece(x, tmpY) != null) {
					return true;
				}
			}
		}
		// must be up/down
		if (p.getY() == y) {
			int dir = x > p.getX() ? 1 : -1;
			for (int tmpX = p.getX() + dir; tmpX != x; tmpX += dir) {
				if (getPiece(tmpX, y) != null) {
					return true;
				}
			}
		}
		// Change in x == Change in y (diagonal movement)
		if (Math.abs(p.getX() - x) == Math.abs(p.getY() - y)) {
			int dirY = y > p.getY() ? 1 : -1;
			int dirX = x > p.getX() ? 1 : -1;
			int tmpY = p.getY() + dirY;
			int tmpX = p.getX() + dirX;
			while (tmpX != x && tmpY != y) {
				if (getPiece(tmpX, tmpY) != null) {
					return true;
				}
				tmpX += dirX;
				tmpY += dirY;
			}

		}
		// Otherwise it must be fine
		return false;
	}

	public String getBoardTable() {
		String board = "";
		boolean wasPrinted;
		board += ("  01234567\n\n");
		for (int i = 0; i < boardHeight; i++) {
			board += (i + " ");
			for (int u = 0; u < boardWidth; u++) {
				wasPrinted = false;
				for (Piece piece : pieces) {
					if (piece.getX() == i && piece.getY() == u) {
						board += (piece.getChar());
						wasPrinted = true;
						break;
					}
				}
				if (!wasPrinted) {
					if ((i % 2 == 0 && u % 2 == 0) || (i % 2 != 0 && u % 2 != 0))
						board += ("O");
					else
						board += ("O");
				}
			}
			board += ("\n");
		}
		board += ("\n");
		return board;
	}

	/**
	 * Checks if the square is threatened by the other side.
	 * 
	 * @param x
	 *            The x location of the square in question.
	 * @param y
	 *            The y location of the square in question.
	 * @param side
	 *            Which side is threatened at this location.
	 * @return If the location is threatened.
	 */
	public boolean isThreatenedSquare(int x, int y, int side) {
		for (Piece p : pieces) {
			if (p.getSide() != side) { // Only the other team can threaten a
										// square for any given side
				SquareStatus squareToCheck = getSquareStatus(x, y, p.getSide());
				if (p.validMove(x, y, squareToCheck) && !isObstructed(p, x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param side
	 *            The side testing for check
	 * @return If the side given is in check
	 */
	public boolean isInCheck(int side) {
		for (Piece p : pieces) {
			if (p instanceof King && p.getSide() == side) {
				return isThreatenedSquare(p.getX(), p.getY(), side);
			}
		}
		return false;
	}

	/**
	 * 
	 * @param p
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean resolvesCheck(Piece p, int x, int y) {
		Board b = new Board();
		for (Integer[] move : movelog.getLogArray()) {
			b.move(move[0], move[1], move[2], move[3]);
		}
		b.getPiece(p.getX(), p.getY()).forceMove(x, y);

		if (b.isInCheck(p.getSide())) {
			return false;
		}
		return true;
	}

	/**
	 * Getter for pieces
	 * 
	 * @return The ArrayList of pieces
	 */
	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	/**
	 * 
	 * @param x
	 *            The x location of the square in question.
	 * @param y
	 *            The y location of the square in question.
	 * @param side
	 *            Which side is relative to this
	 * @return The SquareStatus enum representation for
	 */
	public SquareStatus getSquareStatus(int x, int y, int side) {
		SquareStatus square = SquareStatus.EMPTY;
		for (Piece otherP : pieces) {
			if (otherP.getX() == x && otherP.getY() == y) {
				if (otherP.getSide() == side) {
					square = SquareStatus.TEAM;
				} else {
					square = SquareStatus.ENEMY;
				}
				break;
			}
		}
		return square;
	}

	/**
	 * @param x
	 *            The x coordinate of the piece to find
	 * @param y
	 *            The y coordinate of the piece to find
	 * @return The piece at coordinates (x, y), or null if there is no piece
	 */
	public Piece getPiece(int x, int y) {
		for (Piece piece : pieces) {
			if (x == piece.getX() && y == piece.getY()) {
				return piece;
			}
		}
		return null;
	}

	public ArrayList<Integer[]> getAllMoves(int side) {
		ArrayList<Integer[]> moveList = new ArrayList<Integer[]>();
		for (Piece p : pieces) {
			Integer[] moves = getMovesForPiece(p);
			if (moves[0] != null && moves[2] != null) {
				moveList.add(moves);
			}
		}
		return moveList;
	}

	public Integer[] getMovesForPiece(Piece p) {
		Integer[] moveList = new Integer[4];
		for (int i = 0; i < boardHeight; i++) {
			for (int u = 0; u < boardWidth; u++) {
				SquareStatus status = getSquareStatus(u, i, p.getSide());
				if (!status.equals(SquareStatus.TEAM) && p.validMove(u, i, status) && !isObstructed(p, u, i)) {
					moveList[0] = p.getX();
					moveList[1] = p.getY();
					moveList[2] = u;
					moveList[3] = i;
				}
			}
		}
		return moveList;
	}

	public ArrayList<Integer[]> getMoveLog() {
		return movelog.getLogArray();
	}

	public int getWhiteScore() {
		return whiteScore;
	}

	public int getBlackScore() {
		return blackScore;
	}

	public int getScore(int s) {
		if (s == 0) {
			return getWhiteScore();
		} else {
			return getBlackScore();
		}
	}

	/**
	 * Returns if the pieces have moved yet
	 * 
	 * @return a string which FEN can use to determine availability of castling
	 */
	public String castlingAvailability() {
		StringBuilder castling = new StringBuilder();
		if (!whiteKing.hasMoved()) {
			if (!whiteKingSide.hasMoved()) {
				castling.append("K");
			}
			if (!whiteQueenSide.hasMoved()) {
				castling.append("Q");
			}
		}
		if (!blackKing.hasMoved()) {
			if (!blackKingSide.hasMoved()) {
				castling.append("k");
			}
			if (!blackQueenSide.hasMoved()) {
				castling.append("q");
			}
		}
		if (castling.toString().isEmpty()) {
			return "-";
		} else {
			return castling.toString();
		}
	}

	public String getLogRaw() {
		ArrayList<Integer[]> arr = movelog.getLogArray();
		StringBuilder sb = new StringBuilder();
		for (Integer[] integers : arr) {
			sb.append(integers[0] + ",");
			sb.append(integers[1] + ",");
			sb.append(integers[2] + ",");
			sb.append(integers[3] + "\n");
		}
		return sb.toString();
	}

	public String getPGN() {
		return movelog.toString();
	}

	public String getFEN() {
		// rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1

		StringBuilder fen = new StringBuilder();
		for (int y = 0; y < boardHeight; y++) {
			for (int x = 0; x < boardWidth; x++) {
				Piece p = getPiece(y, x);
				if (p == null) {
					int start = x;

					while (p == null && x < boardWidth) {
						x++;
						p = getPiece(y, x);
					}
					fen.append(x - start);
				}
				if (p != null) {
					fen.append(p.getChar());
				}
			}
			if (y < boardHeight - 1) {
				fen.append("/");
			}
		}

		fen.append(" ");
		if (movelog.getLogArray().size() % 2 == 1) {
			fen.append("b");
		} else {
			fen.append("w");
		}
		fen.append(" ");

		fen.append(castlingAvailability());

		fen.append(" ");

		if (enPassantTarget.isEmpty()) {
			fen.append("-");
		} else {
			fen.append(enPassantTarget);
		}

		fen.append(" ");

		// Halfmove clock: This is the number of halfmoves since the last
		// capture or pawn advance. This is used to determine if a draw can
		// be claimed under the fifty-move rule.
		fen.append(movelog.getHalfMoveCount());
		fen.append(" ");

		// Fullmove number: The number of the full move. It starts at 1, and
		// is incremented after Black's move.
		fen.append(movelog.getFullMoveCount());
		return fen.toString();
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
