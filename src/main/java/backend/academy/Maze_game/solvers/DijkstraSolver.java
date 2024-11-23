package backend.academy.Maze_game.solvers;

import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Direction;
import backend.academy.Maze_game.utility.Maze;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DijkstraSolver implements Solver {
    private static final Logger LOGGER = LoggerFactory.getLogger(DijkstraSolver.class);

    @Getter private int[][] distances;

    private int xS;

    List<Coordinate> path;
    private int yS;

    @Override
    public List<Coordinate> solve(Maze maze) {
        Coordinate start = maze.start();
        Coordinate end = maze.end();
        xS = maze.width();
        yS = maze.height();
        distances = new int[yS][xS];

        for (int y = 0; y < yS; y++) {
            Arrays.fill(distances[y], Integer.MAX_VALUE);
        }

        path = new ArrayList<>();
        compute(maze, start, end);
        return path;
    }

    private void compute(Maze maze, Coordinate start, Coordinate end) {
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.cost));
        queue.add(new Node(start.row(), start.col(), 0));
        distances[start.row()][start.col()] = 0;

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current.row == end.row() && current.col == end.col()) {
                buildPath(end);
                return;
            }

            movingThrowDistances(maze, current, queue);
        }
        LOGGER.info("Path not found. Ending with no solution.");
    }


    public void movingThrowDistances(Maze maze, Node current, PriorityQueue<Node> queue) {
        for (Direction direction : Direction.values()) {
            int newRow = current.row + direction.dy();
            int newCol = current.col + direction.dx();

            if (isValid(maze, newRow, newCol)) {
                int newCost = current.cost + 1; // Uniform cost
                if (newCost < distances[newRow][newCol]) {
                    distances[newRow][newCol] = newCost;
                    queue.add(new Node(newRow, newCol, newCost));
                }
            }
        }
    }

    private boolean isValid(Maze maze, int row, int col) {
        return !(row < 0 || row >= yS || col < 0 || col >= xS || maze.grid()[row][col].type() == Cell.Type.WALL);
    }


    private void buildPath(Coordinate end) {
        int row = end.row();
        int col = end.col();

        while (distances[row][col] != 0) {
            path.add(0, new Coordinate(row, col));

            for (Direction direction : Direction.values()) {
                int prevRow = row - direction.dy();
                int prevCol = col - direction.dx();

                if (prevRow >= 0 && prevRow < yS && prevCol >= 0
                    && prevCol < xS && distances[prevRow][prevCol] == distances[row][col] - 1) {
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
        int cost;

        Node(int row, int col, int cost) {
            this.row = row;
            this.col = col;
            this.cost = cost;
        }
    }
}
