package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.UIManager;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import chessModel.Board;
import chessModel.SquareStatus;
import chessModel.piece.Bishop;
import chessModel.piece.King;
import chessModel.piece.Knight;
import chessModel.piece.Pawn;
import chessModel.piece.Piece;
import chessModel.piece.Rook;

public class BoardTests {

	private static Board b = new Board();

	// brings up board on failure
	@Rule
	public DisplayOnFail failRule = new DisplayOnFail();

	@BeforeClass
	public static void setup() {
		UIManager.put("OptionPane.minimumSize", new Dimension(400, 400));
	}

	@Before
	public void beforeTest() {
		// blank the test board
		b = new Board();
	}

	@Test
	public void testFENStartPosition() {

		assertFalse("Board is upside down", b.getFEN().startsWith("RNBQKBNR/PPPPPPPP/8/8/8/8/pppppppp/rnbqkbnr"));
		assertTrue(b.getFEN().startsWith("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"));
		assertEquals("Something at the end is wrong.", b.getFEN(),
				"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
	}

	@Test
	public void testFENWithMovesFull() {
		// create a standard board

		assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", b.getFEN());
		b.move(6, 4, 4, 4);
		assertEquals("rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1", b.getFEN());
		b.move(1, 2, 3, 2);
		assertEquals("rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2", b.getFEN());
		b.move(7, 6, 5, 5);
		assertEquals("rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2", b.getFEN());
	}

	@Test
	public void testEnPassant() {
		b.move(1, 6, 2, 6);
		b.move(4, 1, 3, 1);
		b.move(1, 0, 3, 0);
		b.move(3, 1, 2, 0);
		assertTrue(b.getPiece(2, 0) == null);
	}

	@Test
	public void testFENClocks() {

		assertTrue(b.getFEN().endsWith("0 1"));
		b.move(6, 4, 4, 4);
		assertTrue(b.getFEN().endsWith("0 1"));
		b.move(1, 1, 3, 1);
		assertTrue(b.getFEN().endsWith("0 2"));
		b.move(7, 6, 5, 5);
		assertTrue(b.getFEN().endsWith("1 2"));
	}

	@Test
	public void testGetAllMoves() {
		ArrayList<Integer[]> allMoves = b.getAllMoves(0);
		int i = 1;
		for (Integer[] move : allMoves) {
			if (move == null) {
				fail("Move " + i + " the move was null.");
			}
			Piece p = b.getPiece(move[0], move[1]);
			if (p == null) {
				fail("Move " + i + " in list failed because the piece was invalid.");
			}
			assertTrue("Move " + i + " in list failed.",
					p.validMove(move[2], move[3], b.getSquareStatus(move[2], move[3], 0)));
			i++;
			Board test = new Board();
			assertTrue(test.move(move[0], move[1],move[2], move[3]));
			
		}
	}

	@Test
	public void testCheckRook() {
		b = new Board(false);
		b.addPiece(new Rook(1, 1, 0));
		Piece king = new King(6, 1, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Rook(1, 1, 0));
		king = new King(1, 6, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Rook(1, 6, 0));
		king = new King(1, 1, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Rook(6, 1, 0));
		king = new King(1, 1, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));
	}

	@Test
	public void testCheckBishop() {
		b = new Board(false);
		b.addPiece(new Bishop(1, 1, 0));
		Piece king = new King(6, 6, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Bishop(5, 5, 0));
		king = new King(1, 1, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Bishop(6, 1, 0));
		king = new King(1, 6, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Bishop(1, 6, 0));
		king = new King(6, 1, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));
	}

	@Test
	public void testCheckKnight() {
		b = new Board(false);
		b.addPiece(new Knight(6, 1, 0));
		Piece king = new King(4, 2, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Knight(2, 1, 0));
		king = new King(4, 2, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Knight(2, 3, 0));
		king = new King(4, 2, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Knight(2, 1, 0));
		king = new King(4, 2, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Knight(3, 2, 0));
		king = new King(4, 4, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Knight(3, 6, 0));
		king = new King(4, 4, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Knight(5, 6, 0));
		king = new King(4, 4, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Knight(3, 2, 0));
		king = new King(4, 4, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));

		b = new Board(false);
		b.addPiece(new Knight(5, 2, 0));
		king = new King(4, 4, 1);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 1));
		assertTrue(b.isInCheck(1));
	}

	@Test
	public void testOtherKing() {
		b = new Board(false);
		b.addPiece(new Knight(5, 2, 1));
		Piece king = new King(4, 4, 0);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 0));
		assertTrue(b.isInCheck(0));

		b = new Board(false);
		b.addPiece(new Knight(5, 2, 1));
		king = new King(4, 4, 0);
		b.addPiece(king);
		assertTrue(b.isThreatenedSquare(king.getX(), king.getY(), 0));
		assertTrue(b.isInCheck(0));
	}

	@Test
	public void testBasicMove() {
		b = new Board(false);
		Piece pawn = new Pawn(6, 5, 0);
		b.addPiece(pawn);
		b.move(6, 5, 4, 5);
		assertEquals(b.getPiece(4, 5), pawn);
	}

	@Test
	public void testPawnMovement() {
		b = new Board(false);
		Piece pawn = new Pawn(6, 5, 0);
		b.addPiece(pawn);
		assertTrue(pawn.validMove(4, 5, SquareStatus.EMPTY));
		assertTrue(pawn.validMove(5, 5, SquareStatus.EMPTY));

		b = new Board(false);
		pawn = new Pawn(6, 5, 0);
		assertTrue(pawn.validMove(5, 4, SquareStatus.ENEMY));
		assertTrue(pawn.validMove(5, 6, SquareStatus.ENEMY));
		b.addPiece(pawn);
		b.addPiece(new Pawn(5, 4, 1));
		b.move(6, 5, 5, 4);
		assertEquals(b.getPiece(5, 4), pawn);
	}

	public static Board getBoard() {
		return b;
	}
}
