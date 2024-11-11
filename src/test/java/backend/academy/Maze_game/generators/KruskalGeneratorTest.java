package backend.academy.Maze_game.generators;

import backend.academy.Maze_game.generators.KruskalGenerator;
import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KruskalGeneratorTest {

    private KruskalGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new KruskalGenerator();
    }

    @Test
    void testGenerateMazeConnectivity() {
        Maze maze = generator.generate(5, 5);

        // Check that there are no isolated passages
        for (int row = 1; row < maze.height(); row += 2) {
            for (int col = 1; col < maze.width(); col += 2) {
                Cell cell = maze.getCell(row, col);
                assertEquals(Cell.Type.PASSAGE, cell.type(),
                    "Expected passage cell at (" + row + ", " + col + ")");

                // Check at least one neighbor is also a passage
                boolean hasPassageNeighbor = false;
                if (row > 1 && maze.getCell(row - 1, col).type() == Cell.Type.PASSAGE) hasPassageNeighbor = true;
                if (row < maze.height() - 2 && maze.getCell(row + 1, col).type() == Cell.Type.PASSAGE) hasPassageNeighbor = true;
                if (col > 1 && maze.getCell(row, col - 1).type() == Cell.Type.PASSAGE) hasPassageNeighbor = true;
                if (col < maze.width() - 2 && maze.getCell(row, col + 1).type() == Cell.Type.PASSAGE) hasPassageNeighbor = true;

                assertTrue(hasPassageNeighbor,
                    "Expected passage cell at (" + row + ", " + col + ") to have at least one passage neighbor");
            }
        }
    }

    @Test
    void testGenerateMazeWithStartAndEnd() {
        Maze maze = generator.generate(5, 5);
        Coordinate start = new Coordinate(1, 1);
        Coordinate end = new Coordinate(3, 3);
        maze = generator.generate(maze, start, end);

        // Check start and end points
        assertEquals(start, maze.start(), "Start point is incorrect");
        assertEquals(end, maze.end(), "End point is incorrect");

        // Verify that start and end points are passages
        assertEquals(Cell.Type.PASSAGE, maze.getCell(start.row(), start.col()).type(),
            "Expected start point to be a passage");
        assertEquals(Cell.Type.PASSAGE, maze.getCell(end.row(), end.col()).type(),
            "Expected end point to be a passage");
    }

    @Test
    void testGenerateMazeInvalidStartEndCoordinates() {
        Maze maze = generator.generate(5, 5);

        // Check invalid start and end points
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            generator.generate(maze, null, new Coordinate(3, 3));
        });
        assertEquals("Start or end coordinates cannot be null", exception.getMessage());

        exception = assertThrows(IllegalArgumentException.class, () -> {
            generator.generate(maze, new Coordinate(1, 1), null);
        });
        assertEquals("Start or end coordinates cannot be null", exception.getMessage());
    }
}
