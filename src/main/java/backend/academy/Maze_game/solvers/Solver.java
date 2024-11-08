package backend.academy.Maze_game.solvers;

import backend.academy.Maze_game.Coordinate;
import backend.academy.Maze_game.Maze;

import java.util.List;

public interface Solver {
    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
