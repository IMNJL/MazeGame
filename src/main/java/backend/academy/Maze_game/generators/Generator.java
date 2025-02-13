package backend.academy.Maze_game.generators;

import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;

public interface Generator {
    Maze generate(int height, int width);

    void generateMazeWithoutStartEnd(Maze maze);

    void generateMazeWithStartEnd(Maze maze, Coordinate start, Coordinate end);
}
