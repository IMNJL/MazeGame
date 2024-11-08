package backend.academy.Maze_game.renders;

import backend.academy.Maze_game.Coordinate;
import backend.academy.Maze_game.Maze;

import java.util.List;

public interface Renderer {
    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}
