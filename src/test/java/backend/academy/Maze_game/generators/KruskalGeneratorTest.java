package backend.academy.Maze_game.generators;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

public class KruskalGeneratorTest {

    private KruskalGenerator generator;

    @BeforeEach
    public void setUp() {
        generator = new KruskalGenerator();
    }

    @Test
    public void testGenerateMaze() {
        int height = 10;
        int width = 10;
        Maze maze = generator.generate(height, width);

        assertNotNull(maze);
        assertEquals(height, maze.height());
        assertEquals(width, maze.width());

        // Проверка, что лабиринт содержит стены и проходы
        boolean hasWalls = false;
        boolean hasPassages = false;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell.Type type = maze.getCell(row, col).type();
                if (type == Cell.Type.WALL) {
                    hasWalls = true;
                } else if (type == Cell.Type.PASSAGE) {
                    hasPassages = true;
                }
            }
        }
        assertTrue(hasWalls);
        assertTrue(hasPassages);
    }

    @Test
    public void testGenerateMazeWithInvalidDimensions() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> generator.generate(0, 10))
            .withMessage("Maze dimensions must be positive");

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> generator.generate(10, 0))
            .withMessage("Maze dimensions must be positive");

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> generator.generate(-1, 10))
            .withMessage("Maze dimensions must be positive");

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> generator.generate(10, -1))
            .withMessage("Maze dimensions must be positive");
    }

    @Test
    public void testGenerateMazeWithoutStartEnd() {
        int height = 10;
        int width = 10;
        Maze maze = new Maze(height, width);
        generator.generateMazeWithoutStartEnd(maze);

        assertNotNull(maze);
        assertEquals(height, maze.height());
        assertEquals(width, maze.width());

        // Проверка, что лабиринт содержит стены и проходы
        boolean hasWalls = false;
        boolean hasPassages = false;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell.Type type = maze.getCell(row, col).type();
                if (type == Cell.Type.WALL) {
                    hasWalls = true;
                } else if (type == Cell.Type.PASSAGE) {
                    hasPassages = true;
                }
            }
        }
        assertTrue(hasWalls);
        assertTrue(hasPassages);
    }

    @Test
    void testGenerateMazeNoStartEndCoordinates() {
        Maze maze = generator.generate(5, 5);

        // Ensure maze has no start or end point initially
        assertNull(maze.start(), "Start point should be null initially");
        assertNull(maze.end(), "End point should be null initially");
    }
}
