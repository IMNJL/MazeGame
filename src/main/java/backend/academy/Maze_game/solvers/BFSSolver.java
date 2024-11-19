package backend.academy.Maze_game.solvers;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Direction;
import backend.academy.Maze_game.utility.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BFSSolver implements Solver {
    private static final Logger LOGGER = LoggerFactory.getLogger(BFSSolver.class);

    @Getter private int[][] distances;

    private int xs;
    private int ys;
    List<Coordinate> path;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        xs = maze.width();
        ys = maze.height();
        distances = new int[ys][xs];

        for (int y = 0; y < ys; y++) {
            Arrays.fill(distances[y], Integer.MAX_VALUE);
        }

        path = new ArrayList<>();
        compute(maze, start, end);
        return path;
    }

    private void compute(Maze maze, Coordinate start, Coordinate end) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start.row(), start.col()));
        distances[start.row()][start.col()] = 0;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.row == end.row() && current.col == end.col()) {
                buildPath(end);
                return;
            }

            for (Direction direction : Direction.values()) {
                int newRow = current.row + direction.dy();
                int newCol = current.col + direction.dx();

                if (isValid(maze, newRow, newCol) && distances[newRow][newCol] == Integer.MAX_VALUE) {
                    distances[newRow][newCol] = distances[current.row][current.col] + 1;
                    queue.add(new Node(newRow, newCol));
                }
            }
        }

        LOGGER.info("Path not found. Ending with no solution.");
    }

    private boolean isValid(Maze maze, int row, int col) {
        return row >= 0 && row < ys && col >= 0 && col < xs && maze.grid()[row][col].type() != Cell.Type.WALL;
    }

    private void buildPath(Coordinate end) {
        int row = end.row();
        int col = end.col();

        while (distances[row][col] != 0) {
            path.add(0, new Coordinate(row, col));

            for (Direction direction : Direction.values()) {
                int prevRow = row - direction.dy();
                int prevCol = col - direction.dx();

                if (prevRow >= 0 && prevRow < ys && prevCol >= 0
                    && prevCol < xs && distances[prevRow][prevCol] == distances[row][col] - 1) {
                    row = prevRow;
                    col = prevCol;
                    break;
                }
            }
        }

        path.add(0, new Coordinate(row, col));
    }

    private static class Node {
        int row;
        int col;

        Node(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
