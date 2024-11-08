package backend.academy.Maze_game.generators;

import backend.academy.Maze_game.Maze;

public interface Generator {
    Maze generate(int height, int width);
}
