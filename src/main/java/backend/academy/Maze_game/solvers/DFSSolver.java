package backend.academy.Maze_game.solvers;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Direction;
import backend.academy.Maze_game.utility.Maze;
import java.util.*;

public class DFSSolver implements Solver {
    private int xs;
    private int ys;
    private boolean[][] visited;
    private List<Coordinate> path;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        xs = maze.width();
        ys = maze.height();
        visited = new boolean[ys][xs];
        path = new ArrayList<>();

        if (dfs(start.row(), start.col(), end.row(), end.col(), maze)) {
            Collections.reverse(path);
        }

        return path;
    }

    private boolean dfs(int y, int x, int y1, int x1, Maze maze) {
        if (y < 0 || y >= ys || x < 0 || x >= xs || visited[y][x] || maze.grid()[y][x].type() == Cell.Type.WALL) {
            return false;
        }

        visited[y][x] = true;
        path.add(new Coordinate(y, x));

        if (y == y1 && x == x1) {
            return true;
        }

        for (Direction direction : Direction.values()) {
            if (dfs(y + direction.dy(), x + direction.dx(), y1, x1, maze)) {
                return true;
            }
        }

        path.remove(path.size() - 1); // Backtrack if no solution
        return false;
    }
}
