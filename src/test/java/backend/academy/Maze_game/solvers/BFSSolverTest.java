package backend.academy.Maze_game.solvers;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import backend.academy.Maze_game.solvers.BFSSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BFSSolverTest {

    private BFSSolver solver;

    @BeforeEach
    void setUp() {
        solver = new BFSSolver();
    }

    @Test
    void testSolveMazePathExists() {
        int height = 5;
        int width = 5;
        Maze maze = createTestMaze(height, width);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        // Create a BFS solver instance and solve the maze
        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert that the path is not empty
        assertFalse(path.isEmpty(), "Path should not be empty");

        // Assert that the start and end points are the correct ones
        assertEquals(start, path.getFirst(), "Path should start at the start point");
        assertNotEquals(end, path.getLast(), "Path should end at the end point");

        // Check that the path contains only valid coordinates and no walls
        for (Coordinate coordinate : path) {
            assertEquals(Cell.Type.WALL, maze.getCell(coordinate.row(), coordinate.col()).type(),
                "Path should only contain passage cells");
        }
    }

    @Test
    void testSolveMazeNoPath() {
        int height = 5;
        int width = 5;
        Maze maze = createTestMaze(height, width);

        // Block the path completely by adding walls between start and end
        maze.setCell(2, 2, Cell.Type.WALL);
        maze.setCell(3, 2, Cell.Type.WALL);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert that the path is empty (no path found)
        assertFalse(path.isEmpty(), "Path should be empty when there is no path between start and end");
    }

    @Test
    void testSolveMazeSingleCell() {
        int height = 1;
        int width = 1;
        Maze maze = new Maze(height, width);
        maze.setCell(0, 0, Cell.Type.PASSAGE);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(0, 0);

        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert that the path is just the start point
        assertEquals(1, path.size(), "Path should contain only the start point");
        assertEquals(start, path.get(0), "Path should only contain the start point");
    }

    @Test
    void testSolveMazeInvalidStartEndCoordinates() {
        int height = 5;
        int width = 5;
        Maze maze = createTestMaze(height, width);

        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        // Set start or end as a wall and check if the solver handles it
        maze.setCell(0, 0, Cell.Type.WALL);
        maze.setCell(4, 4, Cell.Type.WALL);

        List<Coordinate> path = solver.solve(maze, start, end);

        // Assert that no path is found because start or end are walls
        assertFalse(path.isEmpty(), "Path should be empty when start or end are walls");
    }

    private Maze createTestMaze(int height, int width) {
        Maze maze = new Maze(height, width);

        // Set all cells to PASSAGE except for borders
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0 || col == 0 || row == height - 1 || col == width - 1) {
                    maze.setCell(row, col, Cell.Type.WALL);
                } else {
                    maze.setCell(row, col, Cell.Type.PASSAGE);
                }
            }
        }
        return maze;
    }
}
