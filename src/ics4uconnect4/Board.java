package ics4uconnect4;

import java.util.Random;


public class Board {

	private Cell[][] board;
	private int rows;
	private int cols;
	
	/**
	 * Constructor for Boards.
	 * 
	 * @param aRows number of rows in board
	 * @param aCols number of columns in board
	 */
	public Board(int aRows, int aCols) {
		board = new Cell[aRows][aCols];
		rows = aRows;
		cols = aCols;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				board[i][j] = new Cell(CellState.EMPTY); // no color
			}
		}
	}

	/**
	 * Obtain the current number of rows.
	 * 
	 * @return number of rows
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Obtain the current number of columns.
	 * 
	 * @return number of columns
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * @param column number
	 * 
	 * @param player 1 or 2 
	 */

	public void playturn(int col, int player) {
		if (player == 1) {
			board[rownum(col)][col].setState(CellState.P1);
		} else {
			board[rownum(col)][col].setState(CellState.P2);
		}
	}

	/**
	 * @param column number
	 * 
	 * @return row position
	 */


	private int rownum(int col) {
		boolean empty = false;
		int rowpos = rows - 1;

		while (!empty) {
			if (rowpos < 0) {
				return -1;
			} else {
				if (board[rowpos][col].getState() == CellState.EMPTY) {
					empty = true;
				} else {
					rowpos = rowpos - 1;
				}

			}
		}
		return rowpos;
	}
	/**
	 * Checks to see if either player has 4 in a row vertically and horizontally
	 * @return true if either player has 4, false if not
	 */
	public boolean finished() {
		boolean finished = false;
		
		int p1inarow = 0;
		int p2inarow = 0;
		// Checking horizontally 
		for (int i = 0; i < rows; i++ ) {
			p1inarow = 0;
			p2inarow = 0;
			for (int f = 0; f < cols; f++) {
				if (board[i][f].getState() == CellState.P1) {
					p1inarow += 1;
				}
				else {
					//Reset if there is a space or different player in between player 1's row
					p1inarow = 0;
				}
				if (board[i][f].getState() == CellState.P2) {
					p2inarow += 1;
				}
				else {
					p2inarow = 0;
				}
				if (p1inarow == 4) {
					finished = true;
					System.out.println("Game Over, Player 1 wins!");
					break;
				}
				if (p2inarow == 4) {
					finished = true;
					System.out.println("Game Over, Player 2 wins!");
					break;
				}
			}
		}
		 // Checking Vertically
		for (int i = 0; i < cols; i++) {
			p1inarow = 0;
			p2inarow = 0;
			for (int f = 0; f < rows; f++) {
				if (board[f][i].getState() == CellState.P1) {
					p1inarow += 1;
				}
				else {
					p1inarow = 0;
				}
				if (board[f][i].getState() == CellState.P2) {
					p2inarow += 1;
				}
				else {
					p2inarow = 0;
				}
				if (p1inarow == 4) {
					finished = true;
					System.out.println("Game Over, Player 1 wins!");
					break;
				}
				if (p2inarow == 4) {
					finished = true;
					System.out.println("Game Over, Player 2 wins!");
					break;
				}
			}
		}	
	return finished;
		
	}

	/**
	 * Check if a proposed location is valid.
	 * 
	 * @param rowIndex row index to check
	 * @param colIndex column index to check
	 * @return true if index value is valid, otherwise false
	 */
	public boolean isValid(int rowIndex, int colIndex) {
		if (rownum(colIndex) == -1) {
			return false;
		}
		return (rowIndex >= 0 && rowIndex < rows) && (colIndex >= 0 && colIndex < cols);
	}

	public void display() {
		System.out.println("BOARD");
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.printf("%s ", board[i][j]);
			}
			System.out.println();
		}
	}
}
