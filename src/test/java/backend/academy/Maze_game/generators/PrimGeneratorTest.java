package backend.academy.Maze_game.generators;

import backend.academy.Maze_game.generators.PrimGenerator;
import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimGeneratorTest {

    private PrimGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new PrimGenerator();
    }

    @Test
    void testGenerateMazeValidDimensions() {
        int height = 5;
        int width = 5;
        Maze maze = generator.generate(height, width);

        // Check maze dimensions
        assertEquals(height, maze.height(), "Maze height is incorrect");
        assertEquals(width, maze.width(), "Maze width is incorrect");

        // Ensure that maze contains at least one passage (since it is generated using Prim's algorithm)
        boolean hasPassage = false;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
                    hasPassage = true;
                    break;
                }
            }
        }
        assertTrue(hasPassage, "Generated maze should contain at least one passage");
    }

    @Test
    void testGenerateMazeInvalidDimensions() {
        assertThrows(IllegalArgumentException.class, () -> {
            generator.generate(0, 5); // Invalid height
        });

        assertThrows(IllegalArgumentException.class, () -> {
            generator.generate(5, 0); // Invalid width
        });

        assertThrows(IllegalArgumentException.class, () -> {
            generator.generate(-1, -1); // Invalid negative dimensions
        });
    }

    @Test
    void testMazeGenerationPassagesAndWalls() {
        int height = 5;
        int width = 5;
        Maze maze = generator.generate(height, width);

        // Check that passages are correctly created (should be at least some passages)
        boolean hasPassages = false;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell.Type cellType = maze.getCell(row, col).type();
                if (cellType == Cell.Type.PASSAGE) {
                    hasPassages = true;
                    break;
                }
            }
        }
        assertTrue(hasPassages, "Maze should contain at least one passage");

        // Check that walls are correctly set
        boolean hasWalls = false;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    hasWalls = true;
                    break;
                }
            }
        }
        assertTrue(hasWalls, "Maze should contain walls as well");
    }

    @Test
    void testGenerateMazeNoStartEndCoordinates() {
        Maze maze = generator.generate(5, 5);

        // Ensure maze has no start or end point initially
        assertNull(maze.start(), "Start point should be null initially");
        assertNull(maze.end(), "End point should be null initially");
    }
}
