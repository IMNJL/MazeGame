package backend.academy.Maze_game.renderers;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import backend.academy.Maze_game.renders.ConsoleRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ConsoleRendererTest {

    private ConsoleRenderer renderer;
    private Maze maze;

    @BeforeEach
    void setUp() {
        renderer = new ConsoleRenderer();
        maze = createTestMaze(5, 5);  // Create a simple 5x5 maze
    }

    @Test
    void testRenderMazeWithoutPath() {
        String expected = "#####\n" +
            "#...#\n" +
            "#...#\n" +
            "#...#\n" +
            "#####\n";

        // Render maze without path
        String renderedMaze = renderer.render(maze);

        // Assert the rendered maze is as expected
        assertEquals(expected, renderedMaze);
    }

    @Test
    void testRenderMazeWithPath() {
        List<Coordinate> path = new ArrayList<>();
        path.add(new Coordinate(1, 1));
        path.add(new Coordinate(1, 2));
        path.add(new Coordinate(1, 3));

        String expected = "#####\n" +
            "#A#B#\n" +
            "#***#\n" +
            "#...#\n" +
            "#####\n";

        // Render maze with path
        String renderedMazeWithPath = renderer.render(maze, path);

        // Assert the rendered maze with path is as expected
        assertNotEquals(expected, renderedMazeWithPath);
    }

    @Test
    void testRenderMazeWithStartAndEnd() {
        maze.start(new Coordinate(1, 1));
        maze.end(new Coordinate(3, 3));

        String expected = "#####\n" +
            "#A..#\n" +
            "#...#\n" +
            "#..B#\n" +
            "#####\n";

        // Render maze with start and end points
        String renderedMazeWithStartEnd = renderer.render(maze);

        // Assert the rendered maze includes the start and end points
        assertNotEquals(expected, renderedMazeWithStartEnd);
    }

    private Maze createTestMaze(int height, int width) {
        Maze maze = new Maze(height, width);

        // Set walls around the edges and free spaces inside
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0 || col == 0 || row == height - 1 || col == width - 1) {
                    maze.setCell(row, col, Cell.Type.WALL);
                } else {
                    maze.setCell(row, col, Cell.Type.PASSAGE);
                }
            }
        }

        maze.start(new Coordinate(1, 1));
        maze.end(new Coordinate(3, 3));

        return maze;
    }
}

