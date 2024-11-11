package backend.academy.Maze_game.solvers;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Direction;
import backend.academy.Maze_game.utility.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraSolver implements Solver {
    private static final int INF = Integer.MAX_VALUE;
    private int[][] map;
    private int xs;
    private int ys;
    private List<Coordinate> path;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        initializeMap(maze);
        return compute(start.row(), start.col(), end.row(), end.col());
    }

    public void initializeMap(Maze maze) {
        int width1 = maze.width();
        int height1 = maze.height();
        map = new int[height1][width1];

        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                // Set INF for walls, 0 for free spaces
                map[y][x] = maze.getCell(y, x).type() == Cell.Type.WALL ? INF : 0;
            }
        }

        path = new ArrayList<>();
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
        int x = x1;
        int y = y1;
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
        int x;
        int y;
        int distance;

        Node(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }
    }


}
