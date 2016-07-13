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
	private Board board;
	private Timer side1Timer;
	private Timer side2Timer;
	private Time player1TimeLeft;
	private Time player2TimeLeft;
	Player player1, player2;
	private Thread computeMove;
	private int winner;
	private Timer advanceTurnTimer;

	// Game Modes
	public static int HUMAN_VS_AI = 0;
	public static int HUMAN_VS_HUMAN = 1;
	public static int AI_VS_AI = 2;

	public static final int DEFAULT_TIME = 60 * 45; // In Seconds, 3600 is one
													// hour
	private static final int MAXINVALIDMOVES = 10;

	private int invalidMovesCount;

	public Game(int gameMode, Player player1, Player player2) {
		board = new Board();

		this.gameMode = gameMode;

		invalidMovesCount = 1;

		this.player1 = player1;
		this.player2 = player2;

		currentSide = 0;

		// -1 means nobody has won yet
		winner = -1;

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

		advanceTurnTimer = new Timer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (winner != -1){
					System.out.println("Winner is " +(currentSide+1));
					advanceTurnTimer.stop();
				}
				if (!computeMove.isAlive()) {
					performTurn();
				}
			}
		});
		advanceTurnTimer.start();

		if (player2 instanceof HumanPlayer){
			((HumanPlayer) getCurrentPlayer()).setEnabled(false);
		}
		
		performTurn();
	}
	
	private void performTurn() {
		if (computeMove != null) {
			computeMove.interrupt();
		}
		
		if (invalidMovesCount > MAXINVALIDMOVES){
			System.out.println();
			if (currentSide == 0){
				winner = 1;
			}
			if (currentSide == 1){
				winner = 0;
			}
		}
		
		computeMove = new Thread(new Runnable() {
			@Override
			public void run() {
				// We create this sand-box, so the player does not have
				// direct
				// access to the game board
				Board sandbox = new Board();
				sandbox.populateFromFEN(board.getFEN());
				
				// Poll the player for a move
				Integer[] move = getCurrentPlayer().getMove(sandbox);
				
				int oldX = move[0];
				int oldY = move[1];
				int newX = move[2];
				int newY = move[3];

				Piece piece = board.getPiece(move[0], move[1]);

				boolean validPiece = true;
				if (piece == null) {
					validPiece = false;
				} else {
					if (piece.getSide() != currentSide) {
						validPiece = false;
					}
				}

				if (!validPiece) {
					incrementInvalidMoves();
					return;
				}

				if (!move(oldX, oldY, newX, newY)) {
					incrementInvalidMoves();
				} else {
					invalidMovesCount = 1;
				}
			}
		});
		computeMove.start();
	}

	public void incrementInvalidMoves() {
		if (getCurrentPlayer() instanceof HumanPlayer){
			// human players aren't punished
			return;
		}
		if (invalidMovesCount == 1) {
			String playerName = getCurrentPlayer().getName();
			System.out.print("\n"+ playerName + " submitted invalid 1");
		} else if (invalidMovesCount <= MAXINVALIDMOVES) {
			System.out.print(" " + invalidMovesCount);
		}
		invalidMovesCount++;
	}

	public Player getCurrentPlayer() {
		if (currentSide == 0) {
			return player1;
		} else {
			return player2;
		}
	}

	public boolean move(int oldX, int oldY, int x, int y) {
		Piece tmp = board.getPiece(oldX, oldY);
		if (tmp == null) {
			System.out.println("null piece");
			return false;
		}
		boolean moveSuccess = board.move(oldX, oldY, x, y);
		if (tmp.getSide() == currentSide && moveSuccess) {
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
		return moveSuccess;
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

	public int getGameMode() {
		return gameMode;
	}
}
