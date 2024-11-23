package backend.academy.Maze_game.solvers;

import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import java.util.List;

public interface Solver {
    List<Coordinate> solve(Maze maze);
}
