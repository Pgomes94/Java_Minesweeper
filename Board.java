import java.util.List;
import java.util.ArrayList;

/*
 * Board for a game of Minesweeper
 * Options for constructor:
 * easy: a 8x8 board with 10 mines
 * medium: a 16x16 board with 40 mines
 * hard: a 22x22 board with 100 mines
 * anything else: defaults to easy
 */

public class Board {

	List<ArrayList<Cell>> board;
	int boardSize;
	int numBombs;

	public List<ArrayList<Cell>> getBoard() {
		return board;
	}

	public int getNumBombs() {
		return numBombs;
	}

	/*
	 * Makes a board of appropriate size filled with bombs and adjacent bomb
	 * values.
	 */
	public Board(Difficulty difficulty) throws NullPointerException {
		if (difficulty == null) {
			throw new NullPointerException("Null board difficulty.");
		}
		switch (difficulty) {
		case easy:
			boardSize = 8;
			numBombs = 10;
			break;
		case medium:
			boardSize = 16;
			numBombs = 40;
			break;
		case hard:
			boardSize = 22;
			numBombs = 100;
			break;
		}
		board = createBoard();
		randomizeBombs();
		updateBombValues();
	}

	@SuppressWarnings("unchecked")
	// creates an empty board of the specified size
	private List<ArrayList<Cell>> createBoard() {
		List<ArrayList<Cell>> list = new ArrayList<ArrayList<Cell>>();
		ArrayList<Cell> emptyRow = new ArrayList<Cell>();
		for (int i = 0; i < boardSize; i++) {
			emptyRow.add(new Cell(0));
		}
		for (int i = 0; i < boardSize; i++) {
			list.add((ArrayList<Cell>) emptyRow.clone());
		}
		return list;
	}

	/*
	 * Randomly distributes bombs across the board.
	 */
	private void randomizeBombs() {
		int count = 0;
		int x;
		int y;
		do {
			x = (int) (Math.random() * boardSize);
			y = (int) (Math.random() * boardSize);
			if (board.get(x).get(y).getValue() == 0) {
				board.get(x).set(y, new Cell(-1));
				count++;
			}
		} while (count != numBombs);
	}

	private void updateBombValues() {
		for (int r = 0; r < board.size(); r++) {
			for (int c = 0; c < board.size(); c++) {
				if (board.get(r).get(c).getValue() == 0) {
					board.get(r).set(c, new Cell(countBombs(r, c)));
				}
			}
		}
	}

	/*
	 * TODO: rewrite into clean loop
	 */
	private int countBombs(int r, int c) {
		int bombs = 0;

		// top left
		r--;
		c--;
		if (r >= 0 && r < boardSize) {
			if (c >= 0 && c < boardSize) {
				if (board.get(r).get(c).getValue() == -1) {
					bombs++;
				}
			}
		}

		// above
		c++;
		if (r >= 0 && r < boardSize) {
			if (c >= 0 && c < boardSize) {
				if (board.get(r).get(c).getValue() == -1) {
					bombs++;
				}
			}
		}

		// top right
		c++;
		if (r >= 0 && r < boardSize) {
			if (c >= 0 && c < boardSize) {
				if (board.get(r).get(c).getValue() == -1) {
					bombs++;
				}
			}
		}

		// right
		r++;
		if (r >= 0 && r < boardSize) {
			if (c >= 0 && c < boardSize) {
				if (board.get(r).get(c).getValue() == -1) {
					bombs++;
				}
			}
		}

		// left
		c -= 2;
		if (r >= 0 && r < boardSize) {
			if (c >= 0 && c < boardSize) {
				if (board.get(r).get(c).getValue() == -1) {
					bombs++;
				}
			}
		}

		// bottom left
		r++;
		if (r >= 0 && r < boardSize) {
			if (c >= 0 && c < boardSize) {
				if (board.get(r).get(c).getValue() == -1) {
					bombs++;
				}
			}
		}

		// below
		c++;
		if (r >= 0 && r < boardSize) {
			if (c >= 0 && c < boardSize) {
				if (board.get(r).get(c).getValue() == -1) {
					bombs++;
				}
			}
		}

		// bottom right
		c++;
		if (r >= 0 && r < boardSize) {
			if (c >= 0 && c < boardSize) {
				if (board.get(r).get(c).getValue() == -1) {
					bombs++;
				}
			}
		}

		return bombs;
	}
	
	/*
	 * Method used for development to make sure the board looks correct.
	 * 
	 * private void printMatrix() { for (ArrayList<Integer> row : board) { for
	 * (Integer i : row) { System.out.print(i + "\t"); } System.out.println(); }
	 * }
	 */
}
