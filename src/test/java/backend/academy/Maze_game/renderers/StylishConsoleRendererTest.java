package backend.academy.Maze_game.renderers;

import backend.academy.Maze_game.renders.StylishConsoleRenderer;
import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static backend.academy.Maze_game.utility.Elements.*;
import static org.junit.jupiter.api.Assertions.*;

class StylishConsoleRendererTest {

    private Maze maze;
    private StylishConsoleRenderer renderer;

    @BeforeEach
    void setUp() {
        renderer = new StylishConsoleRenderer();
        maze = new Maze(5, 5);

        // Initialize maze with walls and spaces
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                Cell.Type type = (row == 1 || col == 1) ? Cell.Type.WALL : Cell.Type.PASSAGE;
                maze.setCell(row, col, new Cell(row, col, type).type());
            }
        }
    }

    @Test
    void testRenderMazeWithoutPath() {
        String renderedMaze = renderer.render(maze);
        StringBuilder expected = new StringBuilder();

        expected.append('+').append(String.valueOf(BORDER).repeat(5)).append('\n');
        for (int row = 0; row < 5; row++) {
            expected.append('|');
            for (int col = 0; col < 5; col++) {
                if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    expected.append(WALL);
                } else {
                    expected.append(SPACE);
                }
            }
            expected.append('\n').append('+').append(String.valueOf(BORDER).repeat(5)).append('\n');
        }

        assertEquals(expected.toString(), renderedMaze);
    }

    @Test
    void testRenderMazeWithPath() {
        // Set up start and end points and a path between them
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);
        maze.start(start);
        maze.end(end);

        List<Coordinate> path = new ArrayList<>();
        path.add(new Coordinate(0, 0));
        path.add(new Coordinate(0, 2));
        path.add(new Coordinate(2, 2));
        path.add(new Coordinate(4, 4));

        String renderedMaze = renderer.render(maze, path);
        StringBuilder expected = new StringBuilder();

        expected.append('+').append(String.valueOf(BORDER).repeat(5)).append('\n');
        for (int row = 0; row < 5; row++) {
            expected.append('|');
            for (int col = 0; col < 5; col++) {
                Coordinate current = new Coordinate(row, col);
                if (current.equals(start)) {
                    expected.append(STARTPOINT);
                } else if (current.equals(end)) {
                    expected.append(ENDPOINT);
                } else if (path.contains(current)) {
                    expected.append(PATH);
                } else if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    expected.append(WALL);
                } else {
                    expected.append(SPACE);
                }
            }
            expected.append('\n').append('+').append(String.valueOf(BORDER).repeat(5)).append('\n');
        }

        assertEquals(expected.toString(), renderedMaze);
    }
}

