package ics4uconnect4;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Driver {


	/**
	 * Connect 4.
	 * A game in which 2 people try to get 4 of their own pieces in a row.
	 * 
	 * @author Grant N. and Jack M.
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		// Setup constants for the Board
		final int rows = 6;
		final int cols = 7;

		// create the board
		Board board = new Board(rows, cols);
		String gamemode = gamemode();

		//Initialize players as false to let the players decide who goes first
		boolean player1 = false;
		boolean player2 = false;
		int start = 0;

		boolean done = false;
		int column = 0;
		//player vs player gamemode
		if (gamemode.equals("player")) {
			//asking who goes first
			System.out.println("Will player 1 or 2 start first?");
			start = in.nextInt();
			if (start == 1) {
				player1 = true;
			} else if (start == 2) {
				player2 = true;
			}
			//loop the game if no one has gotten 4 in a row
			while (!board.finished()) {
				System.out.println("");
				board.display();
				//player 1's turn
				if (player1 == true) {
					System.out.println("Player 1:");
					column = getColumn(in, 0, 6);
					//checking to see if it is valid and asking again if it is not
					while (board.isValid(0, column) == false) {
						System.out.println("Invalid");
						column = getColumn(in, 1, 7);

					}
					board.playturn(column, 1);
					player1 = false;
					player2 = true;

				} 
				//player 2's turn
				else if (player2 == true) {
					System.out.println("Player 2:");
					column = getColumn(in, 1, 7);
					//checking to see if it is valid and asking again if it is not
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
		if (gamemode.equals("computer"))
	}

	/**
	 * Asking for which gamemode the user wants to play
	 * 
	 * 
	 * @return string (gamemode)
	 */
	public static String gamemode() {
		Scanner in = new Scanner(System.in);
		System.out.println("1. Player versus Player \n2. Player versus Computer");
		int num = in.nextInt();
		while (!(num == 1 || num == 2)) {
			num = in.nextInt();
			System.out.println("Invalid response");
		}
		if (num == 1) {
			return "player";
		} else {
			return "computer";
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
		return column-1;
	}
}
