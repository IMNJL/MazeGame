package backend.academy.Maze_game.solvers;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Direction;
import backend.academy.Maze_game.utility.Maze;
import java.util.*;

public class DijkstraSolver implements Solver {
    private static final int INF = Integer.MAX_VALUE;
    private int[][] map;
    private int xs;
    private int ys;
    private List<Coordinate> path;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        xs = maze.width();
        ys = maze.height();
        map = new int[ys][xs];

        for (int y = 0; y < ys; y++) {
            for (int x = 0; x < xs; x++) {
                map[y][x] = maze.grid()[y][x].type() == Cell.Type.WALL ? INF : INF;
            }
        }

        path = new ArrayList<>();
        return compute(start.row(), start.col(), end.row(), end.col());
    }

    private List<Coordinate> compute(int y0, int x0, int y1, int x1) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        map[y0][x0] = 0;
        pq.add(new Node(x0, y0, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.y == y1 && current.x == x1) {
                buildPath(y1, x1);
                return path;
            }

            for (Direction direction : Direction.values()) {
                int newX = current.x + direction.dx();
                int newY = current.y + direction.dy();

                if (0 <= newX && newX < xs && 0 <= newY && newY < ys && map[newY][newX] == INF) {
                    map[newY][newX] = current.distance + 1;
                    pq.add(new Node(newX, newY, map[newY][newX]));
                }
            }
        }

        return new ArrayList<>();
    }

    private void buildPath(int y1, int x1) {
        // Similar to A* path reconstruction
        int x = x1, y = y1;
        while (map[y][x] != 0) {
            path.add(new Coordinate(y, x));
            for (Direction direction : Direction.values()) {
                int newX = x + direction.dx();
                int newY = y + direction.dy();
                if (0 <= newX && newX < xs && 0 <= newY && newY < ys && map[newY][newX] == map[y][x] - 1) {
                    x = newX;
                    y = newY;
                    break;
                }
            }
        }
        path.add(new Coordinate(y, x));
        Collections.reverse(path);
    }

    private static class Node {
        int x, y, distance;

        Node(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }
}
