import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;

public class MazeSolverWithPower implements IMazeSolverWithPower {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 1, 0 }, // South
			{ 0, 1 }, // East
			{ 0, -1 } // West
	};

	private ArrayList<Integer> noOfRooms;
	private Maze maze;
	private boolean solved = false;
	private boolean[][][] visited;
	private int endRow, endCol;
	private int superpowers;

	// Create Pair class to represent a Pair in the maze.
	private class Pair {
		public int first;
		public int second;
		public Pair parent;
		public int count;

		Pair (int first, int second, int count) {
			this.first = first;
			this.second = second;
			this.count = count;
		}

	}

	public MazeSolverWithPower() {
		// TODO: Initialize variables.
		solved = false;
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		solved = false;
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		if (maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		visited = new boolean[maze.getRows()][maze.getColumns()][superpowers + 1];

		// Reset private fields
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				maze.getRoom(i, j).onPath = false;
			}
		}
		this.endRow = endRow;
		this.endCol = endCol;
		solved = false;
		this.noOfRooms = new ArrayList<>();

		//initialise steps and queue
		int minSteps = 0;
		int step = 0;
		boolean[][] StepChecker = new boolean[maze.getRows()][maze.getColumns()];

		Queue<Pair> queue = new ArrayDeque<>();

		//start of BFS
		Pair end = null;
		Pair start = new Pair(startRow, startCol, superpowers);
		queue.offer(start);
		visited[start.first][start.second][superpowers] = true;
		while (!queue.isEmpty()) {
			int size = queue.size();
			int roomCount = 0;
			for (int i = 0; i < size; i++) {
				Pair current = queue.poll();
				if (!StepChecker[current.first][current.second]) {
					StepChecker[current.first][current.second] = true;
					roomCount++;
					if (current.first == this.endRow && current.second == this.endCol) {
						solved = true;
						minSteps = step;
						end = current;
					}
				}
				for (int j = 0; j < 4; j++) {
					int check = canGoSuper(current.first, current.second, j, current.count);
					if (check != -1) {
						int nextRow = current.first + DELTAS[j][0];
						int nextCol = current.second + DELTAS[j][1];
						Pair next;
						if (check == 0) {
							// No need to use superpower
							next = new Pair(nextRow, nextCol, current.count);
							visited[nextRow][nextCol][current.count] = true;
							next.parent = current;
						} else {
							// Need to use superpower
							next = new Pair(nextRow, nextCol, current.count - 1);
							visited[nextRow][nextCol][current.count - 1] = true;
							next.parent = current;
						}
						queue.offer(next);
					}
				}
			}
			this.noOfRooms.add(roomCount);
			step++;
		}
		if (solved) {
			maze.getRoom(end.first, end.second).onPath = true;
			while(end.parent != null) {
				end = end.parent;
				maze.getRoom(end.first,end.second).onPath = true;
			}
			return minSteps;
		}
		return null;
	}

	private int canGoSuper(int row, int col, int dir, int count) {
		// Can't break outer walls
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return -1;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return -1;
		// If current node visited can't visit again.

		if (visited[row + DELTAS[dir][0]][col + DELTAS[dir][1]][count]) {
			return -1;
		}
		if (dir == NORTH) {
			if (maze.getRoom(row, col).hasNorthWall()) {
				return canUseSuper(row, col, dir, count);
			} else {
				return 0;
			}
		} else if (dir == SOUTH) {
			if (maze.getRoom(row, col).hasSouthWall()) {
				return canUseSuper(row, col, dir, count);
			} else {
				return 0;
			}
		} else if (dir == EAST) {
			if (maze.getRoom(row, col).hasEastWall()) {
				return canUseSuper(row, col, dir, count);
			} else {
				return 0;
			}
		} else if (dir == WEST) {
			if (maze.getRoom(row, col).hasWestWall()) {
				return canUseSuper(row, col, dir, count);
			} else {
				return 0;
			}
		}
		return -1;
	}

	private int canUseSuper(int row, int col, int dir, int count) {
		if (count > 0 && !visited[row + DELTAS[dir][0]][col + DELTAS[dir][1]][count - 1]) {
			return 1;
		} else {
			return -1;
		}
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		if (this.maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		} else {
			if (k >= this.noOfRooms.size() || k < 0) {
				return 0;
			} else {
				return this.noOfRooms.get(k);
			}
		}
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow,
							  int endCol, int superpowers) throws Exception {
		// TODO: Find shortest path with powers allowed.
		this.superpowers = superpowers;
		return pathSearch(startRow, startCol, endRow, endCol);
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-sample.txt");
			IMazeSolverWithPower solver = new MazeSolverWithPower();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(4, 0, 4, 2, 2));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
