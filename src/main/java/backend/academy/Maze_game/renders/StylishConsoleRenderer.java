package backend.academy.Maze_game.renders;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import java.util.List;

public class StylishConsoleRenderer implements Renderer {

    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();

        // Top border
        sb.append("+");
        for (int col = 0; col < maze.width(); col++) {
            sb.append("---+");
        }
        sb.append("\n");

        for (int row = 0; row < maze.height(); row++) {
            // Row content
            sb.append("|");
            for (int col = 0; col < maze.width(); col++) {
                if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    sb.append(" # |");
                } else {
                    sb.append("   |");
                }
            }
            sb.append("\n");

            // Horizontal separator after each row
            sb.append("+");
            for (int col = 0; col < maze.width(); col++) {
                sb.append("---+");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder sb = new StringBuilder();

        // Top border
        sb.append("+");
        for (int col = 0; col < maze.width(); col++) {
            sb.append("---+");
        }
        sb.append("\n");

        for (int row = 0; row < maze.height(); row++) {
            // Row content with path, start, and end
            sb.append("|");
            for (int col = 0; col < maze.width(); col++) {
                Coordinate current = new Coordinate(row, col);
                if (current.equals(maze.start())) {
                    sb.append(" A |"); // Start point
                } else if (current.equals(maze.end())) {
                    sb.append(" B |"); // End point
                } else if (path.contains(current)) {
                    sb.append(" * |"); // Path point
                } else if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    sb.append(" # |"); // Wall
                } else {
                    sb.append("   |"); // Free space
                }
            }
            sb.append("\n");

            // Horizontal separator after each row
            sb.append("+");
            for (int col = 0; col < maze.width(); col++) {
                sb.append("---+");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
