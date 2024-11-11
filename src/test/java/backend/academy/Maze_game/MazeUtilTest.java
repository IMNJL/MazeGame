package backend.academy.Maze_game;

import backend.academy.Maze_game.utility.MazeUtil;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.generators.Generator;
import backend.academy.Maze_game.generators.KruskalGenerator;
import backend.academy.Maze_game.generators.PrimGenerator;
import backend.academy.Maze_game.solvers.AStarSolver;
import backend.academy.Maze_game.solvers.DijkstraSolver;
import backend.academy.Maze_game.solvers.BFSSolver;
import backend.academy.Maze_game.solvers.DFSSolver;
import backend.academy.Maze_game.solvers.Solver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MazeUtilTest {

    private Scanner scannerMock;

    @BeforeEach
    void setUp() {
        scannerMock = Mockito.mock(Scanner.class);
    }

    @Test
    void testChooseGenerator_Prim() {
        when(scannerMock.nextInt()).thenReturn(1);
        Generator generator = MazeUtil.chooseGenerator(scannerMock);
        assertTrue(generator instanceof PrimGenerator);
    }

    @Test
    void testChooseGenerator_Kruskal() {
        when(scannerMock.nextInt()).thenReturn(2);
        Generator generator = MazeUtil.chooseGenerator(scannerMock);
        assertTrue(generator instanceof KruskalGenerator);
    }

    @Test
    void testChooseGenerator_InvalidInputDefaultsToPrim() {
        when(scannerMock.nextInt()).thenReturn(5);
        Generator generator = MazeUtil.chooseGenerator(scannerMock);
        assertTrue(generator instanceof PrimGenerator);
    }

    @Test
    void testChooseSolver_AStar() {
        when(scannerMock.nextInt()).thenReturn(1);
        Solver solver = MazeUtil.chooseSolver(scannerMock);
        assertTrue(solver instanceof AStarSolver);
    }

    @Test
    void testChooseSolver_Dijkstra() {
        when(scannerMock.nextInt()).thenReturn(2);
        Solver solver = MazeUtil.chooseSolver(scannerMock);
        assertTrue(solver instanceof DijkstraSolver);
    }

    @Test
    void testChooseSolver_BFS() {
        when(scannerMock.nextInt()).thenReturn(3);
        Solver solver = MazeUtil.chooseSolver(scannerMock);
        assertTrue(solver instanceof BFSSolver);
    }

    @Test
    void testChooseSolver_DFS() {
        when(scannerMock.nextInt()).thenReturn(4);
        Solver solver = MazeUtil.chooseSolver(scannerMock);
        assertTrue(solver instanceof DFSSolver);
    }

    @Test
    void testChooseSolver_InvalidInputDefaultsToAStar() {
        when(scannerMock.nextInt()).thenReturn(10);
        Solver solver = MazeUtil.chooseSolver(scannerMock);
        assertTrue(solver instanceof AStarSolver);
    }

    @Test
    void testGetDimension() {
        when(scannerMock.nextInt()).thenReturn(10);
        int dimension = MazeUtil.getDimension("height", scannerMock);
        assertEquals(10, dimension);
    }

    @Test
    void testGenerateAndDisplayMaze() {
        Generator generator = new PrimGenerator();
        int height = 5;
        int width = 5;

        Maze maze = MazeUtil.generateAndDisplayMaze(generator, height, width, scannerMock);

        // Basic validation on generated maze dimensions
        assertNotNull(maze);
        assertEquals(height, maze.height());
        assertEquals(width, maze.width());
    }

    @Test
    void testFindAndDisplayPath_PathExists() {
        Maze maze = new Maze(5, 5);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                maze.setCell(y, x, new Cell(y, x, Cell.Type.PASSAGE).type());
            }
        }
        Solver solver = new AStarSolver();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        List<Coordinate> path = solver.solve(maze, start, end);
        assertNotNull(path);
    }

    @Test
    void testGetCoordinateFromUser_ValidCoordinates() {
        Maze maze = new Maze(5, 5);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                maze.setCell(y, x, new Cell(y, x, Cell.Type.PASSAGE).type());
            }
        }
        when(scannerMock.nextInt()).thenReturn(0, 0); // User enters valid coordinates

        Coordinate coord = MazeUtil.getCoordinateFromUser("A", 5, 5, maze, scannerMock);
        assertEquals(new Coordinate(0, 0), coord);
    }

    @Test
    void testGetCoordinateFromUser_InvalidCoordinates() {
        Maze maze = new Maze(5, 5);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                maze.setCell(y, x, new Cell(y, x, Cell.Type.PASSAGE).type());
            }
        }
        // Invalid coordinates (out of bounds)
        when(scannerMock.nextInt()).thenReturn(6, 6, 0, 0); // User retries with valid coordinates

        Coordinate coord = MazeUtil.getCoordinateFromUser("A", 5, 5, maze, scannerMock);
        assertEquals(new Coordinate(0, 0), coord);
    }

}
