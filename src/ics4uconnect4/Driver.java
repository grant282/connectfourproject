package ics4uconnect4;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Driver {

	/**
	 * Connect 4. A game in which 2 people try to get 4 of their own pieces in a
	 * row.
	 * 
	 * @author Grant N. and Jack M.
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Random randy = new Random();

		// Setup constants for the Board
		final int rows = 6;
		final int cols = 7;

		// create the board
		Board board = new Board(rows, cols);
		String gamemode = gamemode();

		// Initialize players as false to let the players decide who goes first
		boolean player1 = false;
		boolean player2 = false;
		int start = 0;

		boolean done = false;
		int column = 0;
		// player vs player gamemode
		if (gamemode.equals("player")) {
			// asking who goes first
			System.out.println("Will player 1 or 2 start first?");
			start = in.nextInt();
			if (start == 1) {
				player1 = true;
			} else if (start == 2) {
				player2 = true;
			}
			// loop the game if no one has gotten 4 in a row
			while (board.finished() == 0) {
				System.out.println("");
				board.display();
				// player 1's turn
				if (player1 == true) {
					System.out.println("Player 1:");
					column = getColumn(in, 1, 7);
					// checking to see if it is valid and asking again if it is not
					while (board.isValid(0, column) == false) {
						System.out.println("Invalid");
						column = getColumn(in, 1, 7);

					}
					board.playturn(column, 1);
					player1 = false;
					player2 = true;

				}
				// player 2's turn
				else if (player2 == true) {
					System.out.println("Player 2:");
					column = getColumn(in, 1, 7);
					// checking to see if it is valid and asking again if it is not
					while (board.isValid(0, column) == false) {
						System.out.println("Invalid");
						column = getColumn(in, 1, 7);
					}
					board.playturn(column, 2);
					player1 = true;
					player2 = false;
				}

			}
			board.display();
		}
		// player vs computer gamemode (random placement)
		if (gamemode.equals("computer")) {
			player1 = true;
			player2 = false;
			while (board.finished() == 0) {
				board.display();
				if (player1 == true) {
					System.out.println("Player 1:");
					column = getColumn(in, 1, 7);
					// checking to see if it is valid and asking again if it is not
					while (board.isValid(0, column) == false) {
						System.out.println("Invalid");
						column = getColumn(in, 1, 7);

					}
					board.playturn(column, 1);
					player1 = false;
					player2 = true;
				} else if (player2 == true) {
					System.out.println("Computer move");
					column = randy.nextInt(7);
					while (board.isValid(0, column) == false) {
						column = randy.nextInt(7);
					}
					board.playturn(column, 2);
					player1 = true;
					player2 = false;
				}
			}
			if (board.finished() == 1) {
				System.out.println("Game Over, Player 1 Wins!");
			} else {
				System.out.println("Game Over, Player 2 Wins!");
			}
			board.display();
		}
		// player vs computer gamemode (advanced)
		if (gamemode.equals("smart comp")) {
			player1 = true;
			player2 = false;
			while (board.finished() == 0) {
				board.display();
				if (player1 == true) {
					System.out.println("Player 1:");
					column = getColumn(in, 1, 7);
					// checking to see if it is valid and asking again if it is not
					while (board.isValid(0, column) == false) {
						System.out.println("Invalid");
						column = getColumn(in, 1, 7);
					}
					board.playturn(column, 1);
					player1 = false;
					player2 = true;
				} else if (player2 == true) {
					System.out.println("Computer move");
					aiMove(board);
					player1 = true;
					player2 = false;
				}
			}
			if (board.finished() == 1) {
				System.out.println("Game Over, Player 1 Wins!");
			} else {
				System.out.println("Game Over, Player 2 Wins!");
			}
			board.display();
		}
	}

	/**
	 * Asking for which gamemode the user wants to play
	 * 
	 * 
	 * @return string (gamemode)
	 */
	public static String gamemode() {
		Scanner in = new Scanner(System.in);
		System.out.println(
				"1. Player versus Player \n2. Player versus Computer(Simple) \n3. Player versus Computer (Advanced)");

		int num = in.nextInt();
		while (!(num == 1 || num == 2 || num == 3)) {
			num = in.nextInt();
			System.out.println("Invalid response");
		}
		if (num == 1) {
			return "player";
		}
		if (num == 2) {
			return "computer";
		} else {
			return "smart comp";
		}

	}

	/**
	 * Helper method to ensure column value is valid.
	 * 
	 * @param in
	 * @return column number
	 */
	private static int getColumn(Scanner in, int min, int max) {
		boolean valid = false;
		int column = 0;

		while (!valid) {
			String prompt = String.format("Which column (%d,%d) :", min, max);
			System.out.print(prompt);
			if (in.hasNextInt()) {
				column = in.nextInt();
				if (column >= min && column <= max) {
					valid = true;
				} else {
					System.out.println("Invalid numeric value provided.");
				}
			} else {
				System.out.println("Not a number.");
			}
			in.nextLine();
		}
		return column - 1;
	}

	/**
	 * Tests all possible moves and compares scores between each move to see what will yield the best result
	 * If all of the moves produce the same value, the computer will play a random move
	 * 
	 * @param board
	 */
	public static void aiMove(Board board) {
		ArrayList<Integer> moves = board.pMoves();
		ArrayList<Integer> scores = new ArrayList<Integer>();
		Random randy = new Random();
		int move = 0;
		int score = 0;
		int bestscore = -1000000;
		// Testing and comparing all moves
		for (int i = 0; i < moves.size(); i++) {
			board.playturn(moves.get(i), 2);
			score = minmax(board, false, 5);
			scores.add(score);
			board.undoMove(moves.get(i));
			if (score > bestscore) {
				bestscore = score;
				move = moves.get(i);
			}
		}
		
		int tempscore = scores.get(0);
		boolean samescore = false;
		for (int i = 0; i < scores.size(); i++) {
			if (tempscore != scores.get(i)) {
				samescore = true;
			}
		}
		// Random move if all scores are equal
		if (!samescore) {
			move = randy.nextInt(6);
		}
		board.playturn(move, 2);
	}

	/**
	 * Tests for specific moves to check if any winning moves are available to either defend or attack
	 * Depending on if the move is a min or max, check for the highest or lowest value move (bestscore)
	 * 
	 * @param board
	 * @param player
	 * @param depth
	 * @return bestscore 
	 */
	public static int minmax(Board board, boolean player, int depth) {
		ArrayList<Integer> moves = board.pMoves();
		int score = 0;
		int bestscore = 0;
		if (board.finished() == 1) {
			return -10000;
		}
		if (board.finished() == 2) {
			return 10000;
		}
		if (board.boardFull() == true || depth == 0) {
			return 0;
		}
		if (player == true) {
			bestscore = Integer.MIN_VALUE;
			for (int i = 0; i < moves.size(); i++) {
				board.playturn(moves.get(i), 2);
				score = minmax(board, false, depth - 1);
				board.undoMove(moves.get(i));
				if (score > bestscore) {
					bestscore = score;
				}
 
			}
			return bestscore;
		} else {
			bestscore = Integer.MAX_VALUE;
			for (int i = 0; i < moves.size(); i++) {
				board.playturn(moves.get(i), 1);
				score = minmax(board, true, depth - 1);
				board.undoMove(moves.get(i));
				if (score < bestscore) {
					bestscore = score;
				}
			}
			return bestscore;
		}

	}

}
