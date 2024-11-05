package backend.academy.Maze_game;

import lombok.Getter;

@Getter
public enum Direction {
    UP(-1, 0),    // Move up (decrease y)
    DOWN(1, 0),   // Move down (increase y)
    LEFT(0, -1),  // Move left (decrease x)
    RIGHT(0, 1);  // Move right (increase x)

    private final int dy;  // Change in y
    private final int dx; // Change in x

    Direction(int dy, int dx) {
        this.dy = dy;
        this.dx = dx;
    }
}
