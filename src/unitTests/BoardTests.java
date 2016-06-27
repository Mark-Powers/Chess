package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import chessModel.Board;
import chessModel.SquareStatus;
import chessModel.TestBoard;
import chessModel.piece.Bishop;
import chessModel.piece.King;
import chessModel.piece.Knight;
import chessModel.piece.Pawn;
import chessModel.piece.Piece;
import chessModel.piece.Rook;
import chessViewController.ChessView;

public class BoardTests {

	// create a test board
	private static TestBoard b = new TestBoard();

	// brings up board on failure
	@Rule
	public DisplayOnFail failRule = new DisplayOnFail();

	@BeforeClass
	public static void setup() {
		UIManager.put("OptionPane.minimumSize", new Dimension(400, 400));
	}

	@Test
	public void testCheckRook() {
		b = new TestBoard();
		b.addPiece(new Rook(1, 1, 0));
		Piece king = new King(6, 1, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Rook(1, 1, 0));
		king = new King(1, 6, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Rook(1, 6, 0));
		king = new King(1, 1, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Rook(6, 1, 0));
		king = new King(1, 1, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));
	}

	@Test
	public void testCheckBishop() {
		b = new TestBoard();
		b.addPiece(new Bishop(1, 1, 0));
		Piece king = new King(6, 6, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Bishop(5, 5, 0));
		king = new King(1, 1, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Bishop(6, 1, 0));
		king = new King(1, 6, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Bishop(1, 6, 0));
		king = new King(6, 1, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));
	}

	@Test
	public void testCheckKnight() {
		b = new TestBoard();
		b.addPiece(new Knight(6, 1, 0));
		Piece king = new King(4, 2, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Knight(2, 1, 0));
		king = new King(4, 2, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Knight(2, 3, 0));
		king = new King(4, 2, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Knight(2, 1, 0));
		king = new King(4, 2, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Knight(3, 2, 0));
		king = new King(4, 4, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Knight(3, 6, 0));
		king = new King(4, 4, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Knight(5, 6, 0));
		king = new King(4, 4, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Knight(3, 2, 0));
		king = new King(4, 4, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new TestBoard();
		b.addPiece(new Knight(5, 2, 0));
		king = new King(4, 4, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));
	}

	@Test
	public void testOtherKing() {
		b = new TestBoard();
		b.addPiece(new Knight(5, 2, 1));
		Piece king = new King(4, 4, 0);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 0));
		assertTrue(b.isInCheck(0));

		b = new TestBoard();
		b.addPiece(new Knight(5, 2, 1));
		king = new King(4, 4, 0);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 0));
		assertTrue(b.isInCheck(0));
	}

	@Test
	public void testBasicMove() {
		b = new TestBoard();
		Piece pawn = new Pawn(6, 5, 0);
		b.addPiece(pawn);
		b.move(6, 5, 4, 5);
		assertEquals(b.getPiece(4, 5), pawn);
	}
	
	@Test
	public void testPawnMovement() {
		b = new TestBoard();
		Piece pawn = new Pawn(6, 5, 0);
		b.addPiece(pawn);
		assertTrue(pawn.validMove(4, 5, SquareStatus.EMPTY));
		assertTrue(pawn.validMove(5, 5, SquareStatus.EMPTY));
		
		b = new TestBoard();
		pawn = new Pawn(6, 5, 0);
		assertTrue(pawn.validMove(5, 4, SquareStatus.ENEMY));
		assertTrue(pawn.validMove(5, 6, SquareStatus.ENEMY));
		b.addPiece(pawn);
		b.addPiece(new Pawn(5,4,1));
		b.move(6, 5, 5, 4);
		assertEquals(b.getPiece(5, 4),pawn);
	}

	@Test
	public void testFENStartPosition() {
		// blank the test board
		b = new TestBoard();

		// create a standard board
		Board board = new Board();

		assertFalse("Board is upside down", board.getFEN().startsWith("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr"));
		assertTrue(board.getFEN().startsWith("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"));
		assertEquals("Something at the end is wrong.", board.getFEN(),
				"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
	}

	@Test
	public void testFENClocks() {
		// blank the test board
		b = new TestBoard();

		// create a standard board
		Board board = new Board();

		assertTrue(board.getFEN().endsWith("0 1"));
		board.move(6, 4, 4, 4);
		assertTrue(board.getFEN().endsWith("0 1"));
		board.move(1, 1, 3, 1);
		assertTrue(board.getFEN().endsWith("0 2"));
		board.move(7, 6, 5, 5);
		assertTrue(board.getFEN().endsWith("1 2"));
	}

	public static void display(Board displayboard) {
		if (displayboard.getPieces().size() == 0) {
			return;
		}
		JOptionPane.showMessageDialog(null, new ChessView(displayboard), "Error Display View",
				JOptionPane.WARNING_MESSAGE);
	}

	public static void display(String str) {
		JOptionPane.showMessageDialog(null, str, "Error Message View", JOptionPane.WARNING_MESSAGE);
	}

	public static TestBoard getBoard() {
		return b;
	}
}
