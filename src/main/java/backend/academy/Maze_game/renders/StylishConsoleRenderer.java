package backend.academy.Maze_game.renders;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import java.util.List;
import static backend.academy.Maze_game.utility.Elements.BORDER;
import static backend.academy.Maze_game.utility.Elements.ENDPOINT;
import static backend.academy.Maze_game.utility.Elements.PATH;
import static backend.academy.Maze_game.utility.Elements.SPACE;
import static backend.academy.Maze_game.utility.Elements.STARTPOINT;
import static backend.academy.Maze_game.utility.Elements.WALL;

public class StylishConsoleRenderer implements Renderer {

    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();

        // Top border
        sb.append('+');
        sb.append(String.valueOf(BORDER).repeat(Math.max(0, maze.width())));
        sb.append('\n');

        for (int row = 0; row < maze.height(); row++) {
            // Row content
            sb.append('|');
            for (int col = 0; col < maze.width(); col++) {
                if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    sb.append(WALL);
                } else {
                    sb.append(SPACE);
                }
            }
            sb.append('\n');

            // Horizontal separator after each row
            sb.append('+');
            sb.append(String.valueOf(BORDER).repeat(Math.max(0, maze.width())));
            sb.append('\n');
        }

        return sb.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder sb = new StringBuilder();

        // Top border
        sb.append('+');
        sb.append(String.valueOf(BORDER).repeat(Math.max(0, maze.width())));
        sb.append('\n');

        for (int row = 0; row < maze.height(); row++) {
            // Row content with path, start, and end
            sb.append('|');
            for (int col = 0; col < maze.width(); col++) {
                Coordinate current = new Coordinate(row, col);
                if (current.equals(maze.start())) {
                    sb.append(STARTPOINT); // Start point
                } else if (current.equals(maze.end())) {
                    sb.append(ENDPOINT); // End point
                } else if (path.contains(current)) {
                    sb.append(PATH); // Path point
                } else if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    sb.append(WALL); // Wall
                } else {
                    sb.append(SPACE); // Free space
                }
            }
            sb.append('\n');

            // Horizontal separator after each row
            sb.append('+');
            sb.append(String.valueOf(BORDER).repeat(Math.max(0, maze.width())));
            sb.append('\n');
        }

        return sb.toString();
    }
}
