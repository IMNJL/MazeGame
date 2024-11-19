package backend.academy.Maze_game.generators;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

public class BasicGeneratorTest {

    private BasicGenerator generator;
    private Maze maze;

    @BeforeEach
    public void setUp() {
        generator = new BasicGenerator();
        maze = new Maze(10, 10);
    }

    @Test
    public void testGenerateMazeWithStartEnd() {
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(8, 8);

        generator.generateMazeWithStartEnd(maze, start, end);

        assertEquals(Cell.Type.START, maze.getCell(start.row(), start.col()).type());
        assertEquals(Cell.Type.END, maze.getCell(end.row(), end.col()).type());
    }

    @Test
    public void testGenerateMazeWithoutStartEnd() {
        generator.generateMazeWithoutStartEnd(maze);

        // Проверка, что лабиринт не изменился
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                assertEquals(Cell.Type.WALL, maze.getCell(row, col).type());
            }
        }
    }

    @Test
    public void testGenerateMazeWithNullCoordinates() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> generator.generateMazeWithStartEnd(maze, null, new Coordinate(3, 3)))
            .withMessage("Start or end coordinates cannot be null");

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> generator.generateMazeWithStartEnd(maze, new Coordinate(1, 1), null))
            .withMessage("Start or end coordinates cannot be null");
    }
}

