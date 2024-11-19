package backend.academy.Maze_game.solvers;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BFSSolverTest {

    private BFSSolver solver;
    private Maze maze;

    @BeforeEach
    public void setUp() {
        int height = 5;
        int width = 5;
        maze = createTestMaze(height, width);
        solver = new BFSSolver();
    }

    @Test
    public void testSolvePathFound() {
        List<Coordinate> path = solver.solve(maze, new Coordinate(1, 1), new Coordinate(3, 3));

        assertThat(path)
            .isNotEmpty()
            .as("Path should be found from (1, 1) to (3, 3)")
            .startsWith(new Coordinate(1, 1))
            .endsWith(new Coordinate(3, 3));
    }

    @Test
    public void testSolveNoPath() {
        // Block all possible paths
        maze.grid()[2][1] = new Cell(2, 1, Cell.Type.WALL);
        maze.grid()[1][2] = new Cell(1, 2, Cell.Type.WALL);
        maze.grid()[2][3] = new Cell(2, 3, Cell.Type.WALL);
        maze.grid()[3][2] = new Cell(3, 2, Cell.Type.WALL);

        List<Coordinate> path = solver.solve(maze, new Coordinate(1, 1), new Coordinate(3, 3));

        assertThat(path).isEmpty();
    }

    @Test
    public void testSolveStartEqualsEnd() {
        List<Coordinate> path = solver.solve(maze, new Coordinate(1, 1), new Coordinate(1, 1));

        assertThat(path)
            .isNotEmpty()
            .hasSize(1)
            .as("Path should contain only the start point if start equals end")
            .containsExactly(new Coordinate(1, 1));
    }

    @Test
    public void testSinglePath() {
        // Create a single narrow path in the maze
        maze = createSinglePathMaze(5, 5);

        List<Coordinate> path = solver.solve(maze, new Coordinate(1, 1), new Coordinate(3, 3));

        assertNotNull(path, "Path should not be null");
        assertThat(path)
            .isNotEmpty()
            .as("Path should follow the only available route")
            .contains(new Coordinate(2, 1), new Coordinate(2, 2), new Coordinate(3, 2));
    }

    private Maze createTestMaze(int height, int width) {
        Maze maze = new Maze(height, width);

        // Set all cells to PASSAGE except for borders
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0 || col == 0 || row == height - 1 || col == width - 1) {
                    maze.grid()[row][col] = new Cell(row, col, Cell.Type.WALL);
                } else {
                    maze.grid()[row][col] = new Cell(row, col, Cell.Type.PASSAGE);
                }
            }
        }
        return maze;
    }

    private Maze createSinglePathMaze(int height, int width) {
        Maze maze = new Maze(height, width);

        // Set all cells to WALL initially
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                maze.grid()[row][col] = new Cell(row, col, Cell.Type.WALL);
            }
        }

        // Create a single path from (1,1) to (3,3)
        maze.grid()[1][1] = new Cell(1, 1, Cell.Type.PASSAGE);
        maze.grid()[2][1] = new Cell(2, 1, Cell.Type.PASSAGE);
        maze.grid()[2][2] = new Cell(2, 2, Cell.Type.PASSAGE);
        maze.grid()[3][2] = new Cell(3, 2, Cell.Type.PASSAGE);
        maze.grid()[3][3] = new Cell(3, 3, Cell.Type.PASSAGE);

        return maze;
    }
}
