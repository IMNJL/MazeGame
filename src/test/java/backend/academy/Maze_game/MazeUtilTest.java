package backend.academy.Maze_game;

import backend.academy.Maze_game.generators.Generator;
import backend.academy.Maze_game.generators.KruskalGenerator;
import backend.academy.Maze_game.generators.PrimGenerator;
import backend.academy.Maze_game.solvers.AStarSolver;
import backend.academy.Maze_game.solvers.BFSSolver;
import backend.academy.Maze_game.solvers.DFSSolver;
import backend.academy.Maze_game.solvers.DijkstraSolver;
import backend.academy.Maze_game.solvers.Solver;
import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import backend.academy.Maze_game.utility.MazeUtil;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class MazeUtilTest {

    @Test
    public void testChooseGenerator() {
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(System.in);

        Generator generator = MazeUtil.chooseGenerator(sc);
        assertTrue(generator instanceof PrimGenerator);
    }

    @Test
    public void testChooseGeneratorDefault() {
        String input = "3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(System.in);

        Generator generator = MazeUtil.chooseGenerator(sc);
        assertTrue(generator instanceof PrimGenerator);
    }

    @Test
    public void testChooseSolver() {
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(System.in);

        Solver solver = MazeUtil.chooseSolver(sc);
        assertTrue(solver instanceof AStarSolver);
    }

    @Test
    public void testChooseSolverDefault() {
        String input = "5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(System.in);

        Solver solver = MazeUtil.chooseSolver(sc);
        assertTrue(solver instanceof AStarSolver);
    }

    @Test
    public void testGetDimension() {
        String input = "10\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(System.in);

        int dimension = MazeUtil.getDimension("height", sc);
        assertEquals(10, dimension);
    }

    @Test
    public void testGetCoordinateFromUser() {
        String input = "1 1\n3 3\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(System.in);

        Maze maze = new Maze(10, 10);
        // Заполнение лабиринта стенами
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                maze.setCell(row, col, Cell.Type.WALL);
            }
        }
        // Создание прохода в (3, 3)
        maze.setCell(3, 3, Cell.Type.PASSAGE);

        Coordinate coord = MazeUtil.getCoordinateFromUser("A", 10, 10, maze, sc);
        assertEquals(new Coordinate(3, 3), coord);
    }

    @Test
    public void testGetCoordinateFromUserInvalid() {
        String input = "10 10\n1 1\n1 1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner sc = new Scanner(System.in);

        Maze maze = new Maze(10, 10);
        // Заполнение лабиринта стенами
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                maze.setCell(row, col, Cell.Type.WALL);
            }
        }
        // Создание прохода в (1, 1)
        maze.setCell(1, 1, Cell.Type.PASSAGE);

        Coordinate coord = MazeUtil.getCoordinateFromUser("A", 10, 10, maze, sc);
        assertEquals(new Coordinate(1, 1), coord);
    }
}
