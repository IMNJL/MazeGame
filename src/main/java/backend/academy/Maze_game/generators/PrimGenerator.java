package backend.academy.Maze_game.generators;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import backend.academy.Maze_game.Cell;
import backend.academy.Maze_game.Coordinate;
import backend.academy.Maze_game.Direction;
import backend.academy.Maze_game.Maze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrimGenerator implements Generator {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrimGenerator.class);
    static SecureRandom random = new SecureRandom();

    @Override
    public Maze generate(int height, int width) {

        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Maze dimensions must be positive");
        }

        int startRow = random.nextInt(height);
        int startCol = random.nextInt(width);

        Maze maze = new Maze(height, width);
        maze.setCell(startRow, startCol, Cell.Type.PASSAGE);

        List<Coordinate> walls = new ArrayList<>();
        addWalls(walls, maze, startRow, startCol);

        // Maze generation logic
        while (!walls.isEmpty()) {
            Coordinate wall = walls.remove(random.nextInt(walls.size()));
            int row = wall.row();
            int col = wall.col();

            if (canCarve(maze, row, col)) {
                maze.setCell(row, col, Cell.Type.PASSAGE);
                addWalls(walls, maze, row, col);
            }
        }

        // Select random start and end points
        Coordinate start = selectRandomPassage(maze);
        Coordinate end = selectRandomPassage(maze);

        // Ensure start and end are different
        while (start.equals(end)) {
            end = selectRandomPassage(maze);
        }

        LOGGER.info("Start coordinates: {} {}", start.row(), start.col());
        LOGGER.info("End coordinates: {} {}", end.row(), end.col());
        maze.start(start);
        maze.end(end);

//        // Попытка найти путь и вывод результатов
//        Solver solver = new AStarSolver();
//        List<Coordinate> path = solver.solve(maze, start, end);
//
//        if (path == null || path.isEmpty()) {
//            LOGGER.info("No path found.");
//        } else {
//            LOGGER.info("Path found:");
//            for (Coordinate coord : path) {
//                LOGGER.info("(%d, %d) ", coord.row(), coord.col());
//            }
//            LOGGER.info(""); // Переход на новую строку после пути
//        }

        return maze;
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

    private Coordinate selectRandomPassage(Maze maze) {
        List<Coordinate> passages = new ArrayList<>();
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                if (maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
                    passages.add(new Coordinate(row, col));
                }
            }
        }
        return passages.get(random.nextInt(passages.size()));
    }

    private static final List<List<Integer>> DIRECTIONS = List.of(
        List.of(-1, 0), // up
        List.of(1, 0),  // down
        List.of(0, -1), // left
        List.of(0, 1)   // right
    );

}
