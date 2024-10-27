package backend.academy.Maze_game;

import java.util.*;

public class AStarSolver implements Solver {
    private static final int A_STAR_SPACE = 0xFFFFFFFF;
    private static final int A_STAR_WALL = 0xFFFFFFFE;

    private int[][] map;
    private int xs, ys;
    private List<Coordinate> path;

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        xs = maze.width();
        ys = maze.height();
        map = new int[ys][xs];

        for (int y = 0; y < ys; y++) {
            for (int x = 0; x < xs; x++) {
                map[y][x] = maze.grid()[y][x].type() == Cell.Type.WALL ? A_STAR_WALL : A_STAR_SPACE;
            }
        }

        path = new ArrayList<>();
        compute(start.row(), start.col(), end.row(), end.col());
        return path;
    }

    private void compute(int y0, int x0, int y1, int x1) {
        // Инициализация карты
        for (int y = 0; y < ys; y++) {
            for (int x = 0; x < xs; x++) {
                if (map[y][x] != A_STAR_WALL) {
                    map[y][x] = A_STAR_SPACE;
                }
            }
        }

        int i0 = 0, i1 = xs * ys, n0 = 0, n1 = 0;
        int[] px = new int[i1 * 2];
        int[] py = new int[i1 * 2];

        if (map[y0][x0] == A_STAR_SPACE) {
            px[i0 + n0] = x0;
            py[i0 + n0] = y0;
            n0++;
            map[y0][x0] = 0; // Установка стартовой точки

            for (int j = 1; j < xs * ys; j++) {
                if (map[y1][x1] != A_STAR_SPACE) {
                    break; // Выход, если достигли конца
                }

                boolean expanded = false; // Флаг для отслеживания расширения

                for (int ii = i0; ii < i0 + n0; ii++) {
                    int x = px[ii], y = py[ii];

                    // Проверка соседей
                    for (int[] dir : new int[][]{{y - 1, x}, {y + 1, x}, {y, x - 1}, {y, x + 1}}) {
                        int yy = dir[0], xx = dir[1];

                        // Проверка границ карты
                        if (0 <= yy && yy < ys && 0 <= xx && xx < xs && map[yy][xx] == A_STAR_SPACE) {
                            map[yy][xx] = j; // Установка значения для данной клетки
                            px[i1 + n1] = xx; // Добавление в список
                            py[i1 + n1] = yy;
                            n1++;
                            expanded = true; // Мы расширили
//                            System.out.printf("Expanding to cell (%d, %d) with value %d%n", yy, xx, j);
                        }
                    }
                }

                if (!expanded) {
                    System.out.println("No expansion possible from current cells.");
                    break; // Выход, если не удалось расширить
                }

                int tmp = i0;
                i0 = i1;
                i1 = tmp;
                n0 = n1;
                n1 = 0;
            }
        }

        // Проверка наличия пути
        if (map[y1][x1] == A_STAR_SPACE || map[y1][x1] == A_STAR_WALL) {
            System.out.println("Path not found. Ending with no solution.");
            return; // Если путь не найден
        }

        int ps = map[y1][x1] + 1; // Длина пути
        int[] pxPath = new int[ps];
        int[] pyPath = new int[ps];

        int x = x1, y = y1;
        for (int i = ps - 1; i >= 0; i--) {
            pxPath[i] = x;
            pyPath[i] = y;

            // Определение следующей клетки в пути
            if (y > 0 && map[y - 1][x] == i - 1) {
                y--;
            } else if (y < ys - 1 && map[y + 1][x] == i - 1) {
                y++;
            } else if (x > 0 && map[y][x - 1] == i - 1) {
                x--;
            } else if (x < xs - 1 && map[y][x + 1] == i - 1) {
                x++;
            }
        }

        // Добавление пути в результат
        for (int i = 0; i < ps; i++) {
            path.add(new Coordinate(pyPath[i], pxPath[i]));
        }
    }
}
