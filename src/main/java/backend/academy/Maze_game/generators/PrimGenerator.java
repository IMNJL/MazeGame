package backend.academy.Maze_game.generators;

import backend.academy.Maze_game.renders.Renderer;
import backend.academy.Maze_game.renders.StylishConsoleRenderer;
import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Direction;
import backend.academy.Maze_game.utility.Maze;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrimGenerator extends BasicGenerator implements Generator {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrimGenerator.class);
    private static final SecureRandom RANDOM = new SecureRandom();

    @Override
    public Maze generate(int height, int width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Maze dimensions must be positive");
        }

        // Создаём лабиринт без начальной и конечной точек
        Maze maze = new Maze(height, width);
        generateMazeWithoutStartEnd(maze);
        return maze;
    }


    @Override
    public void generateMazeWithoutStartEnd(Maze maze) {
        // Логика генерации лабиринта (например, Прима)
        int startRow = RANDOM.nextInt(maze.height());
        int startCol = RANDOM.nextInt(maze.width());

        maze.setCell(startRow, startCol, Cell.Type.PASSAGE);

        List<Coordinate> walls = new ArrayList<>();
        addWalls(walls, maze, startRow, startCol);

        while (!walls.isEmpty()) {
            Coordinate wall = walls.remove(RANDOM.nextInt(walls.size()));
            int row = wall.row();
            int col = wall.col();

            if (canCarve(maze, row, col)) {
                maze.setCell(row, col, Cell.Type.PASSAGE);
                addWalls(walls, maze, row, col);
            }
        }
    }

    @Override
    public void generateMazeWithStartEnd(Maze maze, Coordinate start, Coordinate end) {
        maze.setCell(start.row(), start.col(), Cell.Type.START);
        maze.setCell(end.row(), end.col(), Cell.Type.END);
        Renderer renderer = new StylishConsoleRenderer();
        LOGGER.info("Generated maze without start and end points:\n{}", renderer.render(maze));
    }

    private void addWalls(List<Coordinate> walls, Maze maze, int row, int col) {
        for (List<Integer> direction : DIRECTIONS) {
            int newRow = row + direction.get(0);
            int newCol = col + direction.get(1);

            if (isInBounds(maze, newRow, newCol) && maze.getCell(newRow, newCol).type() == Cell.Type.WALL) {
                walls.add(new Coordinate(newRow, newCol));
            }
        }
    }

    private boolean isInBounds(Maze maze, int row, int col) {
        return row >= 0 && row < maze.height() && col >= 0 && col < maze.width();
    }

    private boolean canCarve(Maze maze, int row, int col) {
        int passages = 0;
        for (Direction direction : Direction.values()) {
            int newRow = row + direction.dy();
            int newCol = col + direction.dx();
            if (isInBounds(maze, newRow, newCol) && maze.getCell(newRow, newCol).type() == Cell.Type.PASSAGE) {
                passages++;
            }
        }
        return passages == 1;
    }

    private static final List<List<Integer>> DIRECTIONS = List.of(
        List.of(-1, 0), // up
        List.of(1, 0),  // down
        List.of(0, -1), // left
        List.of(0, 1)   // right
    );
}
