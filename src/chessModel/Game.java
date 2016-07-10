package chessModel;

// TODO rework this stuff, as it is Desktop specific
// HERE --------------------------------------------------------
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
// TO HERE -----------------------------------------------------

import chessModel.piece.Piece;

public class Game {
	private int currentSide;
	private int gameMode;
	private boolean needsRedraw;
	private Board board;
	private Timer side1Timer;
	private Timer side2Timer;
	private Time player1TimeLeft;
	private Time player2TimeLeft;
	Player player1, player2;
	private boolean humanInputEnabled;
	private Thread computeMove;

	// Game Modes
	public static int HUMAN_VS_AI = 0;
	public static int HUMAN_VS_HUMAN = 1;
	public static int AI_VS_AI = 2;

	public static final int DEFAULT_TIME = 60 * 45; // In Seconds, 3600 is one
													// hour

	private int invalidMovesCount;

	public Game(int gameMode, Player player1, Player player2) {
		humanInputEnabled = false;

		board = new Board();

		this.gameMode = gameMode;

		needsRedraw = false;

		invalidMovesCount = 0;

		this.player1 = player1;
		this.player2 = player2;

		currentSide = 0;
		player1TimeLeft = new Time(DEFAULT_TIME);
		player2TimeLeft = new Time(DEFAULT_TIME);

		side1Timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1TimeLeft.dec();
			}
		});
		side2Timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player2TimeLeft.dec();
			}
		});
		side1Timer.start();

		Timer checkOnCompterPlayer = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (humanInputEnabled == false && !computeMove.isAlive()) {
					gameLoop();
				}
			}
		});
		checkOnCompterPlayer.start();

		gameLoop();
	}

	/**
	 * This method prompts the next player for a move. If the player is human,
	 * it exits. This method exits if the next player is human and must be
	 * called again by the GUI in order for
	 */
	public void gameLoop() {
		while (!isCheckMate()) {
			humanInputEnabled = false;
			if (getCurrentPlayer() instanceof HumanPlayer) {
				// exit and allow the human player to submit a move
				humanInputEnabled = true;
				return;
			} else {
				// Create a new thread for the computation of the next move
				computeMove = new Thread(new Runnable() {
					@Override
					public void run() {
						// Exit if the AI returns too many invalid moves
						// (prevents infinite loop)
						if (invalidMovesCount > 9) {
							System.out.println("Submitted invalid move ten times");
							System.exit(0);
						}
						// We create this sand-box, so the player does not have
						// direct
						// access to the game board
						Board sandbox = new Board();
						sandbox.populateFromFEN(board.getFEN());

						// Poll the player for a move
						Integer[] move = ((ComputerPlayer) getCurrentPlayer()).getMove(sandbox);
						int oldX = move[0];
						int oldY = move[1];
						int newX = move[2];
						int newY = move[3];

						// Get the piece on the board the player wishes to move
						Piece pieceToMove = board.getPiece(oldX, oldY);

						if (pieceToMove == null) {
							// The AI has one less try at submitting a valid
							// move
							System.out.println(++invalidMovesCount);
						} else if (pieceToMove.validMove(newX, newY,
								board.getSquareStatus(oldX, oldY, pieceToMove.getSide()))) {
							move(oldX, oldY, newX, newY);
							invalidMovesCount = 0;
							needsRedraw = true;
						} else {
							System.out.println("invalid move #" + ++invalidMovesCount);
						}
					}
				});
				computeMove.start();
				return;
			}
		}
	}

	public Player getCurrentPlayer() {
		if (currentSide == 0) {
			return player1;
		} else {
			return player2;
		}
	}

	public void move(int oldX, int oldY, int x, int y) {
		Piece tmp = board.getPiece(oldX, oldY);
		if (tmp == null) {
			System.out.println("Submitted invalid move ten times");
			System.exit(0);
		}
		if (tmp.getSide() == currentSide && board.move(oldX, oldY, x, y)) {
			if (currentSide == 0) {
				currentSide = 1;
				side1Timer.stop();
				side2Timer.start();
			} else if (currentSide == 1) {
				currentSide = 0;
				side2Timer.stop();
				side1Timer.start();
			}
		}
	}

	public Board getBoard() {
		return board;
	}

	public String getPlayer1Time() {
		return player1TimeLeft.getTime();
	}

	public String getPlayer2Time() {
		return player2TimeLeft.getTime();
	}

	public int getCurrentSide() {
		return currentSide;
	}

	public int getPlayer1Score() {
		return board.getWhiteScore();
	}

	public int getPlayer2Score() {
		return board.getBlackScore();
	}

	/**
	 * @param side
	 *            The side that is being checked for checkmate
	 * @return
	 */
	public boolean isCheckMate() {
		if (!board.isInCheck(currentSide)) { // Can't be in checkmate if not in
												// check
			return false;
		}
		for (Integer[] move : board.getAllMoves(0)) {
			Piece p = board.getPiece(move[0], move[1]);
			if (board.resolvesCheck(p, move[2], move[3])) {
				return false; // If there is a move that resolves check, it is
								// not checkmate
			}
		}
		return true;
	}

	/**
	 * @param side
	 *            The side that is being checked for a draw
	 * @return
	 */
	public boolean isDraw() {
		return (board.getAllMoves(currentSide).size() == 0);
	}

	public boolean isHumanInputEnabled() {
		return humanInputEnabled;
	}

	public int getGameMode() {
		return gameMode;
	}

	public boolean needsRedraw() {
		return needsRedraw;
	}

	public void markDrawn() {
		needsRedraw = false;
	}
}
