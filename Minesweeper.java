import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Minesweeper {
	static Board board;

	class Tuple {
		int row;
		int col;
		int val;

		public Tuple(int row, int col) {
			this.row = row;
			this.col = col;
			this.val = board.getBoard().get(row).get(col).getValue();
		}
	}

	public static void main(String args[]) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter a difficulty: easy, medium or hard");
		String difficulty = in.readLine();

		switch (difficulty) {
		case "easy":
			board = new Board(Difficulty.easy);
			break;
		case "medium":
			board = new Board(Difficulty.medium);
		case "hard":
			board = new Board(Difficulty.hard);
		default:
			board = new Board(Difficulty.easy);
		}

		int row = -1;
		int column = -1;
		int guessing = -1; // represents if revealing or marking a bomb
		int size = board.boardSize;
		int bombSquares = board.numBombs; // number of bomb squares
		int nonBombSquares = (size * size) - bombSquares; // number of non bomb
															// squares
		Cell c;
		Tuple[] questionVals = new Tuple[bombSquares]; // rows, column and
														// values of the marked
														// cells.
		int questionCells = 0; // how many cells have been marked.
		int squaresRevealed = 0; // how many cells have been revealed.
		boolean allGuessed = false; // whether or not all bombs are marked.
		boolean bombHit = false; // whether or not a bomb was revealed.
		/*
		 * while loop for the game
		 */
		do {
			printCurrentBoard();
			try {
				System.out
						.println("Enter 0 for revealing, 1 for marking a possible bomb.");
				guessing = Integer.parseInt(in.readLine());
				if (guessing != 0 && guessing != 1) {
					throw new IllegalArgumentException("Guessing not 0 or 1.");
				}
				System.out.println("Pick a row");
				row = Integer.parseInt(in.readLine());
				System.out.println("Pick a column");
				column = Integer.parseInt(in.readLine());
			} catch (NumberFormatException e) {
				System.out.println("Only enter integers. Try again.");
				continue;
			} catch (IllegalArgumentException e) {
				System.out.println("Incorrect value, enter only 0 or 1.");
				continue;
			}

			// input checking
			if ((row < 0 || row >= size) || (column < 0 || column >= size)) {
				System.out.format("Invalid row or column. Try again.\n"
						+ "Ranges are 0-%d\n", size - 1);
			} else {
				c = board.getBoard().get(row).get(column);
				// already picked cell
				if (c.getRevealed()) {
					System.out.println("Cell already picked. Pick a new cell.");
				} else if (c.getQuestion()) {
					// cell has been marked possible

					// trying to reveal marked cell, not allowed.
					if (guessing == 0) {
						System.out
								.println("Can't reveal a marked cell. Try again.");
					} else {
						// sets cells value to false
						c.setQuestion(!c.getQuestion());
						// removes it from the array holding question cells
						// values.
						for (int i = 0; i < questionVals.length; i++) {
							try {
								if (questionVals[i].row == row
										&& questionVals[i].col == column) {
									questionVals[i] = null;
								}
							} catch (NullPointerException e) {
								continue; // space i was null.
							}
						}
						questionCells--;
					}
				} else {
					// unmarked and unrevealed cell
					if (guessing == 0) {
						// revealing cell
						c.setRevealed(true);
						squaresRevealed++;
						// checking for bomb
						if (c.getValue() == -1) {
							bombHit = true;
							System.out.println("BOMB HIT!!! GAME OVER!!!");
						} else if (c.getValue() == 0) {
							// no bombs nearby, reveal all adjacent squares and
							// recurse
							revealNearbyZeroes(row, column);
						}
					} else {
						// marking cell

						// can't mark more cells than number of bombs.
						if (questionCells == bombSquares) {
							System.out.println("Max number of guesses used.");
						} else {
							// mark and add to question values list.
							c.setQuestion(true);
							for (int i = 0; i < questionVals.length; i++) {
								if (questionVals[i] == null) {
									questionVals[i] = new Minesweeper().new Tuple(
											row, column);
									questionCells++;
									break;
								}
							}
							// if number of marks is the number of bomb, check
							// values for all bombs.
							if (questionCells == bombSquares) {
								allGuessed = checkQuestionVals(questionVals);
							}
						}
					}
				}
			}
			/*
			 * Conditions that exit the while loop: 1) Number of squares
			 * revealed is equal to the number of non-bomb squares. 2) Revealed
			 * a bomb. 3) Marked all bombs correctly.
			 */
		} while (squaresRevealed < nonBombSquares && !bombHit && !allGuessed);

		if (bombHit) {
			// show the board if they lost.
			printRevealedBoard();
		} else {
			// print win message!
			System.out.println("Congrats! You won!");
		}
	}

	private static void revealNearbyZeroes(int row, int col) {
		// adjust to top left
		row--;
		col--;

		for (int i = 0; i < 9; i++) {
			if (i % 3 == 0 && i != 0) {
				row++;
				col -= 3;
			}

			if ((row >= 0 && row < board.boardSize)
					&& (col >= 0 && col < board.boardSize)) {
				if (board.getBoard().get(row).get(col).getRevealed() == false) {
					board.getBoard().get(row).get(col).setRevealed(true);
					if (board.getBoard().get(row).get(col).getValue() == 0) {
						// do it recursively for all zeroes
						revealNearbyZeroes(row, col);
					}
				}
			}
			col++;
		}
	}

	// checks all values in questionVals, if they are all bombs then the player
	// wins.
	private static boolean checkQuestionVals(Tuple[] questionVals) {
		for (Tuple p : questionVals) {
			if (p.val != -1) {
				return false;
			}
		}
		return true;
	}

	// shows the currently revealed board, with appropriate marks and revealed
	// values
	private static void printCurrentBoard() {
		for (ArrayList<Cell> row : board.getBoard()) {
			for (Cell i : row) {
				System.out.print(i + "\t");
			}
			System.out.println();
		}
	}

	// shows the board fully revealed
	private static void printRevealedBoard() {
		for (ArrayList<Cell> row : board.getBoard()) {
			for (Cell i : row) {
				System.out.print(i.getValue() + "\t");
			}
			System.out.println();
		}
	}
}
