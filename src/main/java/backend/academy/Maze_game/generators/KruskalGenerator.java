package backend.academy.Maze_game.generators;

import backend.academy.Maze_game.renders.Renderer;
import backend.academy.Maze_game.renders.StylishConsoleRenderer;
import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KruskalGenerator extends BasicGenerator implements Generator {
    private int[][] parent; // Для реализации структуры объединения с поиском (Union-Find)
    private final SecureRandom random = new SecureRandom();
    private static final Logger LOGGER = LoggerFactory.getLogger(KruskalGenerator.class);

    @Override
    public Maze generate(int height, int width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Maze dimensions must be positive");
        }
        Maze maze = new Maze(height, width);
        generateMazeWithoutStartEnd(maze);
        Renderer renderer = new StylishConsoleRenderer();
        LOGGER.info("Maze without start and end points:\n{}", renderer.render(maze));

        return maze;
    }

    @Override
    public void generateMazeWithoutStartEnd(Maze maze) {
        // Заполнение лабиринта стенами
        int height = maze.height();
        int width = maze.width();
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                maze.setCell(row, col, Cell.Type.WALL);
            }
        }

        // Инициализация клеток для объединения (нечетные клетки - проходы, четные - стены)
        parent = new int[height][width];
        for (int row = 1; row < height; row += 2) {
            for (int col = 1; col < width; col += 2) {
                maze.setCell(row, col, Cell.Type.PASSAGE);
                parent[row][col] = row * width + col;
            }
        }

        // Создание списка рёбер (стен) между проходами
        List<Edge> edges = getEdges(height, width);
        Collections.shuffle(edges, random);

        // Обрабатываем рёбра для построения лабиринта
        for (Edge edge : edges) {
            int root1 = find(edge.row1(), edge.col1());
            int root2 = find(edge.row2(), edge.col2());

            if (root1 != root2) {
                union(root1, root2);
                maze.setCell((edge.row1() + edge.row2()) / 2, (edge.col1() + edge.col2()) / 2, Cell.Type.PASSAGE);
            }
        }
    }

    @Override
    public void generateMazeWithStartEnd(Maze maze, Coordinate start, Coordinate end) {
        maze.setCell(start.row(), start.col(), Cell.Type.START);
        maze.setCell(end.row(), end.col(), Cell.Type.END);
        Renderer renderer = new StylishConsoleRenderer();
        LOGGER.info("Generated maze without start and end points:\n{}", renderer.render(maze));
    }

    private static List<Edge> getEdges(int height, int width) {
        List<Edge> edges = new ArrayList<>();
        for (int row = 1; row < height; row += 2) {
            for (int col = 1; col < width; col += 2) {
                if (row + 2 < height) {
                    edges.add(new Edge(row, col, row + 2, col)); // Вертикальное ребро
                }
                if (col + 2 < width) {
                    edges.add(new Edge(row, col, row, col + 2)); // Горизонтальное ребро
                }
            }
        }
        return edges;
    }

    // Находит корень для данной клетки (с использованием сжатия пути)
    private int find(int row, int col) {
        if (parent[row][col] != row * parent[0].length + col) {
            int root = find(parent[row][col] / parent[0].length, parent[row][col] % parent[0].length);
            parent[row][col] = root; // Сжатие пути
        }
        return parent[row][col];
    }

    // Объединяет два подмножества
    private void union(int root1, int root2) {
        int row1 = root1 / parent[0].length;
        int col1 = root1 % parent[0].length;
        parent[row1][col1] = root2;
    }

    // Вспомогательный класс для рёбер (стен)
    private record Edge(int row1, int col1, int row2, int col2) {}
}
