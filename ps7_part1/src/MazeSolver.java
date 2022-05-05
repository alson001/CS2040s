import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ArrayList;

public class MazeSolver implements IMazeSolver {
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
	private boolean[][] visited;
	private int endRow, endCol;

	public MazeSolver() {
		// TODO: Initialize variables.
		solved = false;
		maze = null;
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		visited = new boolean[maze.getRows()][maze.getColumns()];
		solved = false;
	}

	// Create Pair class to represent a Pair in the maze.
	private class Pair {
		public int first;
		public int second;
		public Pair parent;

		Pair (int first, int second) {
			this.first = first;
			this.second = second;
		}

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

		// Reset private fields
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}
		this.endRow = endRow;
		this.endCol = endCol;
		solved = false;
		Pair[][] parents = new Pair[maze.getRows()][maze.getColumns()];
		this.noOfRooms = new ArrayList<>();

		//initialise steps and queue
		int minSteps = 0;
		int step = 0;
		Queue<Pair> queue = new ArrayDeque<>();

		//start of BFS
		Pair end = null;
		Pair start = new Pair(startRow,startCol);
		queue.offer(start);
		visited[start.first][start.second] = true;
		while (!queue.isEmpty()) {
			int size = queue.size();
			noOfRooms.add(size);
			for (int i = 0; i < size; i++) {
				Pair current = queue.poll();
				if (current.first == endRow && current.second == endCol) {
					solved = true;
					end = current;
					minSteps = step;
				}
				for (int j = 0; j < 4; j++) {
					if (canGo(current.first, current.second, j)) {
						Pair currentNext = new Pair(current.first + DELTAS[j][0], current.second + DELTAS[j][1]);
						currentNext.parent = current;
						if (visited[currentNext.first][currentNext.second]) {
							continue;
						} else {
							queue.offer(currentNext);
							visited[currentNext.first][currentNext.second] = true;
						}
					}
				}
			}
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

	private boolean canGo(int row, int col, int dir) {
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;
		// If current node visited can't visit again.
		if (visited[row + DELTAS[dir][0]][col + DELTAS[dir][1]] == true) {
			return false;
		}

		// Can't visit due to wall
		if (dir == NORTH) {
			return !maze.getRoom(row, col).hasNorthWall();
		} else if (dir == SOUTH) {
			return !maze.getRoom(row, col).hasSouthWall();
		} else if (dir == EAST) {
			return !maze.getRoom(row, col).hasEastWall();
		} else if (dir == WEST) {
			return !maze.getRoom(row, col).hasWestWall();
		}
		return false;
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

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-empty.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 2, 3));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 9; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}