package backend.academy.Maze_game.solvers;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Direction;
import backend.academy.Maze_game.utility.Maze;
import java.util.*;

public class BFSSolver implements Solver {
    private int xs;
    private int ys;
    private boolean[][] visited;
    private int[][] parent;
    private List<Coordinate> path;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        xs = maze.width();
        ys = maze.height();
        visited = new boolean[ys][xs];
        parent = new int[ys][xs];
        path = new ArrayList<>();

        bfs(start.row(), start.col(), end.row(), end.col(), maze);
        buildPath(start, end);

        return path;
    }

    private void bfs(int y0, int x0, int y1, int x1, Maze maze) {
        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(new Coordinate(y0, x0));
        visited[y0][x0] = true;
        parent[y0][x0] = -1; // Start point has no parent

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            int y = current.row();
            int x = current.col();

            if (y == y1 && x == x1) {
                return;
            }

            for (Direction direction : Direction.values()) {
                int newY = y + direction.dy();
                int newX = x + direction.dx();

                if (0 <= newY && newY < ys && 0 <= newX && newX < xs && !visited[newY][newX] && maze.grid()[newY][newX].type() != Cell.Type.WALL) {
                    visited[newY][newX] = true;
                    parent[newY][newX] = y * xs + x; // Store parent node
                    queue.add(new Coordinate(newY, newX));
                }
            }
        }
    }

    private void buildPath(Coordinate start, Coordinate end) {
        int current = parent[end.row()][end.col()];
        while (current != -1) {
            int y = current / xs;
            int x = current % xs;
            path.add(new Coordinate(y, x));
            current = parent[y][x];
        }
        path.add(start);
        Collections.reverse(path);
    }
}
