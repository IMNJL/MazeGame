package backend.academy.Maze_game.generators;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;

public class BasicGenerator {
    public void generateMazeWithStartEnd(Maze maze, Coordinate start, Coordinate end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start or end coordinates cannot be null");
        }
        maze.setCell(start.row(), start.col(), Cell.Type.START);
        maze.setCell(end.row(), end.col(), Cell.Type.END);
    }

    public void generateMazeWithoutStartEnd(Maze maze) {}
}
