package backend.academy.Maze_game;

import java.util.List;

public class ConsoleRenderer implements Renderer {

    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                Coordinate current = new Coordinate(row, col);
                if (current.equals(maze.start())) {
                    sb.append("A"); // Start point
                } else if (current.equals(maze.end())) {
                    sb.append("B"); // End point
                } else if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                Coordinate current = new Coordinate(row, col);
                if (current.equals(maze.start())) {
                    sb.append("A"); // Start point
                } else if (current.equals(maze.end())) {
                    sb.append("B"); // End point
                } else if (path.contains(current)) {
                    sb.append("*"); // Path point
                } else if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    sb.append("#");
                } else {
                    sb.append(".");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
