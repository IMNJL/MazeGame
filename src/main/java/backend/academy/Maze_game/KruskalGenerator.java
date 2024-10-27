package backend.academy.Maze_game;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalGenerator implements Generator {
    private int[][] parent;
    static SecureRandom random = new SecureRandom();

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);

        // Заполнение лабиринта стенами
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

        // Перемешиваем рёбра для случайного выбора

        Collections.shuffle(edges, random);

        // Обрабатываем рёбра для построения лабиринта
        for (Edge edge : edges) {
            int root1 = find(edge.row1, edge.col1);
            int root2 = find(edge.row2, edge.col2);

            if (root1 != root2) {
                union(root1, root2);
                maze.setCell((edge.row1 + edge.row2) / 2, (edge.col1 + edge.col2) / 2, Cell.Type.PASSAGE);
            }
        }

        // Установка начальной и конечной точек
        maze.start(selectRandomPassage(maze));
        Coordinate end;
        do {
            end = selectRandomPassage(maze);
        } while (end.equals(maze.start()));
        maze.end(end);

        return maze;
    }

    private int find(int row, int col) {
        if (parent[row][col] != row * parent[0].length + col) {
            int root = find(parent[row][col] / parent[0].length, parent[row][col] % parent[0].length);
            parent[row][col] = root; // Сжатие пути
        }
        return parent[row][col];
    }

    private void union(int root1, int root2) {
        int row1 = root1 / parent[0].length;
        int col1 = root1 % parent[0].length;
//        int row2 = root2 / parent[0].length;
//        int col2 = root2 % parent[0].length;
        parent[row1][col1] = root2;
    }

    private Coordinate selectRandomPassage(Maze maze) {
        List<Coordinate> passages = new ArrayList<>();
        for (int row = 1; row < maze.height(); row += 2) {
            for (int col = 1; col < maze.width(); col += 2) {
                if (maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
                    passages.add(new Coordinate(row, col));
                }
            }
        }

        return passages.get(random.nextInt(passages.size()));
    }

    private static class Edge {
        int row1;
        int col1;
        int row2;
        int col2;

        Edge(int row1, int col1, int row2, int col2) {
            this.row1 = row1;
            this.col1 = col1;
            this.row2 = row2;
            this.col2 = col2;
        }
    }
}
