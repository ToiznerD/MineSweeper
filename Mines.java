package mines;

import java.util.HashSet;

import java.util.Random;
import java.util.Set;

public class Mines {
	private Spot[][] board;
	private boolean showAll;

	private class Spot {
		/*
		 * This class represent each spot on the board it holds the status of a spot
		 * neither if it's a mine, flagged & opened.
		 */
		private int i, j;
		private boolean mine, flag, open;

		public Spot(int i, int j) {
			this.i = i;
			this.j = j;
		}

		public int getI() {
			return i;
		}

		public int getJ() {
			return j;
		}

		public void setMine() {
			mine = true;
		}

		public boolean isMine() {
			return mine;
		}

		public void setFlag() {
			flag = flag == true ? false : true;
		}

		public boolean getFlag() {
			return flag;
		}

		public boolean Open() {
			if (mine)
				return false;
			open = true;
			return true;
		}

		public boolean isOpen() {
			return open;
		}

		public Set<Spot> getNeighbours() {
			// Return a collection of all the neighbors

			Set<Spot> neighbors = new HashSet<Spot>();

			for (int k = i - 1; k <= i + 1; k++) {
				for (int t = j - 1; t <= j + 1; t++) {
					// Check boundaries
					if (k >= 0 && k < board.length && t >= 0 && t < board[0].length) {
						neighbors.add(board[k][t]);
					}
				}
			}
			return neighbors;
		}

		public int findMinesAround() {
			// Count how many mines are around
			int nearbyMines = 0;
			Set<Spot> neighbors = getNeighbours();
			for (Spot i : neighbors)
				if (i.isMine())
					nearbyMines++;
			return nearbyMines;
		}
	}

	public Mines(int height, int width, int numMines) {
		board = new Spot[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = new Spot(i, j);
			}
		}

		// Add mines randomly
		Random random = new Random();
		int i = random.nextInt(height), j = random.nextInt(width);
		for (int k = 0; k < numMines; k++) {
			// Try until you find a free spot
			while (board[i][j].isMine()) {
				i = random.nextInt(height);
				j = random.nextInt(width);
			}
			// Found one!
			board[i][j].setMine();
		}
	}

	public boolean addMine(int i, int j) {
		if (i < 0 || i >= board.length || j < 0 || j >= board[0].length)
			return false;
		// Add mine
		board[i][j].setMine();
		return true;
	}

	public boolean open(int i, int j) {
		// Mine?
		if (board[i][j].isMine())
			return false;

		// Mark as open
		board[i][j].Open();

		// If no mines around, open all neighbors
		if (board[i][j].findMinesAround() == 0) {
			Set<Spot> neighbors = board[i][j].getNeighbours();
			for (Spot s : neighbors)
				if (!s.isOpen())
					open(s.getI(), s.getJ());
		}

		return true;
	}

	public void toggleFlag(int x, int y) {
		// If already flaged, remove flag.
		// Else put flag
		board[x][y].setFlag();
	}

	public boolean isDone() {
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[0].length; j++)
				// If any spot is still not open than it's not done
				if (!board[i][j].isMine() && !board[i][j].isOpen())
					return false;
		return true;
	}

	public String get(int i, int j) {
		/* if spot is close, return "F" for flagged, else ".". else return "x" for mine
		 * or amount of mines around. " " for 0 mines around
		 */
		if (showAll == true) {
			if (board[i][j].isMine())
				return "X";
			else
				return board[i][j].findMinesAround() > 0 ? String.format("%d", board[i][j].findMinesAround()) : " ";
		}

		if (board[i][j].isOpen()) {
			if (board[i][j].isMine()) {
				return "X";
			} else
				return board[i][j].findMinesAround() > 0 ? String.format("%d", board[i][j].findMinesAround()) : " ";
		} else
			return board[i][j].getFlag() ? "F" : ".";
	}

	public void setShowAll(boolean showAll) {
		this.showAll = showAll;
	}

	public String toString() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				s.append(get(i, j));
			}
			s.append("\n");
		}
		return s.toString();
	}

}
