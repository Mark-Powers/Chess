package chessViewController;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.JPanel;

import chessModel.Board;
import chessModel.Log;
import chessModel.Piece;

public class ChessView extends JPanel {

	private Board b;
	HashMap<String, Character> charMap;
	private final Color brown = new Color(130, 70, 30);
	private Piece selected;
	private static final Color faintGray = new Color(240, 250, 240);

	public ChessView(Board b) {
		setLayout(new FlowLayout());

		setSize(getHeight(), getHeight());

		this.b = b;
		charMap = new HashMap<String, Character>();

		charMap.put("K", '\u2654');
		charMap.put("Q", '\u2655');
		charMap.put("R", '\u2656');
		charMap.put("B", '\u2657');
		charMap.put("N", '\u2658');
		charMap.put("P", '\u2659');

		charMap.put("k", '\u265A');
		charMap.put("q", '\u265B');
		charMap.put("r", '\u265C');
		charMap.put("b", '\u265D');
		charMap.put("n", '\u265E');
		charMap.put("p", '\u265F');

		setBackground(Color.white);
	}

	public void setSelected(Piece p) {
		selected = p;
	}

	public Piece getSelected() {
		return selected;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		boolean parity = false;
		int cellSize = getCellSize();

		int fontSize = (cellSize * 3) / 4;

		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
		g.setColor(Color.lightGray);
		for (int i = 1; i < b.boardWidth + 1; i++) {
			g.drawRect(i * cellSize, 0, cellSize, cellSize);
			g.drawString(String.valueOf(i), i * cellSize + cellSize / 2 - fontSize / 3, cellSize / 2 + fontSize / 2);
		}
		for (int i = 1; i < b.boardHeight + 1; i++) {
			g.drawRect(0, i * cellSize, cellSize, cellSize);
			g.drawString(String.valueOf(Log.convertChar(i-1-32)), cellSize / 2 - fontSize / 3, i * cellSize + cellSize / 2 + fontSize / 2);
			
		}
		for (int y = 0; y < b.boardHeight; y++) {
			for (int x = 0; x < b.boardWidth; x++) {
				Piece p = b.getPiece(y, x);
				g.setColor(Color.lightGray);
				if (parity) {
					g.fillRect(x * cellSize + cellSize, y * cellSize + cellSize, cellSize, cellSize);
				}
				g.setColor(brown);
				String character;
				if (p == null) {
					character = "";
				} else {
					if (p.equals(selected)) {
						g.setColor(Color.blue);
					}
					character = convertChar(p.getChar());
				}
				g.drawString(character, x * cellSize + cellSize / 2 + cellSize - fontSize / 2,
						y * cellSize + cellSize / 2 + fontSize / 2 + cellSize);
				parity = !parity;
			}

			// if even board alternate
			if (b.boardWidth % 2 == 0) {
				parity = !parity;
			}
		}
	}

	public int getCellSize() {
		return getHeight() / (b.boardHeight + 1);
	}

	private String convertChar(String key) {
		Character c = charMap.get(key);
		if (c == null) {
			return key;
		}
		return Character.toString(c);
	}

	public Dimension getSize() {
		int cellSize = getCellSize();
		return new Dimension(cellSize * b.boardWidth, cellSize * b.boardHeight);
	}
}
