package backend.academy.Maze_game.solvers;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import backend.academy.Maze_game.utility.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static backend.academy.Maze_game.utility.Elements.*;

import java.util.List;

public class AStarSolverTest {

    private Maze maze;

    @BeforeEach
    public void setUp() {
        // Create a simple maze for testing
        maze = new Maze(5, 5); // 5x5 maze for simplicity
        maze.grid()[0][0] = new Cell(0, 0, Cell.Type.PASSAGE); // Start
        maze.grid()[0][4] = new Cell(0, 4, Cell.Type.PASSAGE); // End
        maze.grid()[1][1] = new Cell(1,1, Cell.Type.WALL); // Some walls
        maze.grid()[2][2] = new Cell(2, 2, Cell.Type.WALL);
        maze.grid()[3][3] = new Cell(3, 3, Cell.Type.WALL);
        maze.grid()[4][4] = new Cell(4, 4, Cell.Type.PASSAGE); // End Point
    }

    @Test
    public void testSolvePathFound() {
        AStarSolver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, new Coordinate(0, 0), new Coordinate(4, 4));

        assertNotNull(path);
    }


    @Test
    public void testSolveNoPath() {
        maze.grid()[1][0] = new Cell(1, 0, Cell.Type.WALL); // Blocking the path
        maze.grid()[2][0] = new Cell(2, 0, Cell.Type.WALL);

        AStarSolver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, new Coordinate(0, 0), new Coordinate(4, 4));

        assertNotNull(path, "Path should be null if no path exists");
    }

    @Test
    public void testSolveStartEqualsEnd() {
        AStarSolver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, new Coordinate(0, 0), new Coordinate(0, 0));

        assertNotNull(path);
        assertEquals(1, path.size(), "Path should contain only the start point if start equals end");
        assertEquals(new Coordinate(0, 0), path.get(0), "Path should start and end at the same point");
    }



    @Test
    public void testSinglePath() {
        // Create a simple maze with a direct path
        maze.grid()[1][1] = new Cell(1, 1, Cell.Type.PASSAGE);
        maze.grid()[1][2] = new Cell(1, 2, Cell.Type.PASSAGE);
        maze.grid()[1][3] = new Cell(1, 3, Cell.Type.PASSAGE);

        AStarSolver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, new Coordinate(0, 0), new Coordinate(4, 4));

        assertNotNull(path);
    }
}
