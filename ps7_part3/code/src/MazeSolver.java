import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.function.Function;

public class MazeSolver implements IMazeSolver {
	private static final int TRUE_WALL = Integer.MAX_VALUE;
	private static final int EMPTY_SPACE = 0;
	private static final List<Function<Room, Integer>> WALL_FUNCTIONS = Arrays.asList(
			Room::getNorthWall,
			Room::getEastWall,
			Room::getWestWall,
			Room::getSouthWall
	);
	private static final int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 0, 1 }, // East
			{ 0, -1 }, // West
			{ 1, 0 } // South
	};

	private Maze maze;
	private boolean solved = false;
	private PriorityQueue<Pair> PQ;
	private boolean[][] visited;
	private int endRow, endCol;

	// Create Pair class to represent a Pair in the maze.
	private class Pair implements Comparable<Pair>{
		public int first;
		public int second;
		private int fearLevel = Integer.MAX_VALUE;

		Pair (int first, int second) {
			this.first = first;
			this.second = second;
		}

		@Override
		public int compareTo(Pair p) {
			if (fearLevel < p.fearLevel) {
				return -1;
			} else if (fearLevel > p.fearLevel) {
				return 1;
			}
			return 0;
		}

	}

	public MazeSolver() {
		// TODO: Initialize variables.
		this.maze = null;
		solved = false;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		solved = false;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}
		this.visited = new boolean[maze.getRows()][maze.getColumns()];
		// Reset private fields
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
			}
		}
		this.endRow = endRow;
		this.endCol = endCol;
		solved = false;
		this.PQ = new PriorityQueue<Pair>();

		//start of dijkstras
		Pair end = null;
		Pair start = new Pair(startRow, startCol);
		start.fearLevel = 0;
		PQ.offer(start);

		while(!PQ.isEmpty()) {
			Pair current = PQ.poll();
			if (current.first == this.endRow && current.second == this.endCol) {
				solved = true;
				// first instance of reaching end saved
				if (end == null) {
					end = current;
				}
			}
			visited[current.first][current.second] = true;
			for (int j = 0; j < 4; j++) {
				if (canGo(current.first, current.second, j)) {
					int nextRow = current.first + DELTAS[j][0];
					int nextCol = current.second + DELTAS[j][1];
					Pair next = new Pair(nextRow, nextCol);
					if (!visited[nextRow][nextCol]) {
						relax(next, current, j);
					}
				}
			}
		}
		if (solved) {
			return end.fearLevel;
		}
		return null;
	}

	private boolean canGo(int row, int col, int dir) {
		if (row + DELTAS[dir][0] >= 0 && row + DELTAS[dir][0] < maze.getRows() &&
				col + DELTAS[dir][1] >= 0 && col + DELTAS[dir][1] < maze.getColumns()) {
			return true;
		}
		// If current node visited can't visit again.
		return false;
	}

	public void relax(Pair next, Pair current, int dir) {
		Function<Room, Integer> function = WALL_FUNCTIONS.get(dir);
		int wallValue = function.apply(maze.getRoom(current.first, current.second));
		if (wallValue == EMPTY_SPACE) {
			wallValue = 1;
		}
		else if (wallValue == TRUE_WALL) {
			return;
		}
		if (next.fearLevel > current.fearLevel + wallValue) {
			next.fearLevel = current.fearLevel + wallValue;
			this.PQ.offer(next);
		}
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find minimum fear level given new rules.
		return null;
	}

	@Override
	public Integer bonusSearch(int startRow, int startCol, int endRow, int endCol, int sRow, int sCol) throws Exception {
		// TODO: Find minimum fear level given new rules and special room.
		return null;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("haunted-maze-simple.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);
			System.out.println(solver.pathSearch(0, 3, 0, 4));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
