package unitTests;

import static org.junit.Assert.assertTrue;

import java.awt.Dimension;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import chessModel.King;
import chessModel.Knight;
import chessModel.Piece;
import chessModel.Rook;
import chessModel.TestBoard;
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
	
	public static void display(TestBoard b) {
		JOptionPane.showMessageDialog(null, new ChessView(b));
	}

	public static void display(String str) {
		JOptionPane.showMessageDialog(null, str);
	}

	public static TestBoard getBoard() {
		return b;
	}
}
