package backend.academy.Maze_game;

import backend.academy.Maze_game.renders.ConsoleRenderer;
import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ConsoleRendererTest {

    private ConsoleRenderer renderer;
    private Maze maze;

    @BeforeEach
    public void setUp() {
        renderer = new ConsoleRenderer();
        maze = new Maze(10, 10);

        // Заполнение лабиринта стенами
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                maze.setCell(row, col, Cell.Type.WALL);
            }
        }

        // Создание прохода в лабиринте
        for (int row = 1; row < maze.height() - 1; row++) {
            for (int col = 1; col < maze.width() - 1; col++) {
                maze.setCell(row, col, Cell.Type.PASSAGE);
            }
        }

        // Установка начальной и конечной точек
        maze.start(new Coordinate(1, 1));
        maze.end(new Coordinate(8, 8));
    }

    @Test
    public void testRenderMaze() {
        String renderedMaze = renderer.render(maze);

        assertNotNull(renderedMaze, "Rendered maze should not be null");
        assertTrue(renderedMaze.contains("A"), "Rendered maze should contain start point");
        assertTrue(renderedMaze.contains("B"), "Rendered maze should contain end point");
        assertTrue(renderedMaze.contains("#"), "Rendered maze should contain walls");
        assertTrue(renderedMaze.contains("."), "Rendered maze should contain free spaces");
    }

    @Test
    public void testRenderMazeWithPath() {
        List<Coordinate> path = new ArrayList<>();
        path.add(new Coordinate(1, 1));
        path.add(new Coordinate(2, 2));
        path.add(new Coordinate(3, 3));
        path.add(new Coordinate(8, 8));

        String renderedMaze = renderer.render(maze, path);

        assertNotNull(renderedMaze, "Rendered maze should not be null");
        assertTrue(renderedMaze.contains("A"), "Rendered maze should contain start point");
        assertTrue(renderedMaze.contains("B"), "Rendered maze should contain end point");
        assertTrue(renderedMaze.contains("#"), "Rendered maze should contain walls");
        assertTrue(renderedMaze.contains("."), "Rendered maze should contain free spaces");
        assertTrue(renderedMaze.contains("*"), "Rendered maze should contain path points");
    }

    @Test
    public void testRenderMazeWithNoPath() {
        List<Coordinate> path = new ArrayList<>();

        String renderedMaze = renderer.render(maze, path);

        assertNotNull(renderedMaze, "Rendered maze should not be null");
        assertTrue(renderedMaze.contains("A"), "Rendered maze should contain start point");
        assertTrue(renderedMaze.contains("B"), "Rendered maze should contain end point");
        assertTrue(renderedMaze.contains("#"), "Rendered maze should contain walls");
        assertTrue(renderedMaze.contains("."), "Rendered maze should contain free spaces");
        assertFalse(renderedMaze.contains("*"), "Rendered maze should not contain path points");
    }

    @Test
    public void testRenderKnownMaze() {
        // Создание известного лабиринта
        maze = new Maze(10, 10);
        String[] knownMaze = {
            ".#.##.##.#",
            "..........",
            "#.##.##.#A",
            "#.#B#..##.",
            "......##..",
            "##.###..##",
            "......#.#.",
            ".##.#.....",
            "..#.#.#.#.",
            "#.#..#..#."
        };

        for (int row = 0; row < knownMaze.length; row++) {
            for (int col = 0; col < knownMaze[row].length(); col++) {
                char cell = knownMaze[row].charAt(col);
                if (cell == '#') {
                    maze.setCell(row, col, Cell.Type.WALL);
                } else if (cell == '.') {
                    maze.setCell(row, col, Cell.Type.PASSAGE);
                } else if (cell == 'A') {
                    maze.setCell(row, col, Cell.Type.PASSAGE);
                    maze.start(new Coordinate(row, col));
                } else if (cell == 'B') {
                    maze.setCell(row, col, Cell.Type.PASSAGE);
                    maze.end(new Coordinate(row, col));
                }
            }
        }

        // Ожидаемый путь
        List<Coordinate> path = new ArrayList<>();
        path.add(new Coordinate(3, 3));
        path.add(new Coordinate(4, 3));
        path.add(new Coordinate(4, 2));
        path.add(new Coordinate(4, 1));
        path.add(new Coordinate(3, 1));
        path.add(new Coordinate(2, 1));
        path.add(new Coordinate(1, 1));
        path.add(new Coordinate(1, 2));
        path.add(new Coordinate(1, 3));
        path.add(new Coordinate(1, 4));
        path.add(new Coordinate(1, 5));
        path.add(new Coordinate(1, 6));
        path.add(new Coordinate(1, 7));
        path.add(new Coordinate(1, 8));
        path.add(new Coordinate(1, 9));
        path.add(new Coordinate(2, 4));

        // Ожидаемый вывод
        String expectedOutput =
                ".#.##.##.#\n" +
                ".*********\n" +
                "#*##*##.#A\n" +
                "#*#B#..##.\n" +
                ".***..##..\n" +
                "##.###..##\n" +
                "......#.#.\n" +
                ".##.#.....\n" +
                "..#.#.#.#.\n" +
                "#.#..#..#.\n" ;

        String renderedMaze = renderer.render(maze, path);

        assertEquals(expectedOutput, renderedMaze, "Rendered maze should match the expected output");
    }
}
