package backend.academy.Maze_game;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private Main() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     * сначала предлагается ПОЛЬЗОВАТЕЛЬСКОЕ МЕНЮ:
     * в котором - 1) сначала выбирается алгоритм генерации лабиринта
     *             2) затем размеры лабиринта
     *  на данный момент предлагается найти путь алгоритмом A*, но скорее всего добавится еще алгоритм(BFS/DFS)
     *  после сгенерированного лабиринта случайным образом находятся точки, при этом сразу проверяется,
     *  что точки не находятся на стенах лабиринта
     *
     *  Лабиринт строится из точек('.') - свободный проход, '#' - стенка и '*' - путь по которому идет путь
     *  от точки А в точку В(точки именно так и обозначены на лабиринте)
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        LOGGER.info("Выберите генератор лабиринта: 1 - Прима, 2 - Краскала");
        int choice = sc.nextInt();

        Generator generator = switch (choice) {
            case 1 -> new PrimGenerator();
            case 2 -> new KruskalGenerator();
            default -> {
                LOGGER.info("Некорректный ввод, выбран генератор Прима по умолчанию.");
                yield new PrimGenerator();
            }
        };

        LOGGER.info("Выберите размер лабиринта|высота");
        int height = sc.nextInt();
        LOGGER.info("Выберите размер лабиринта|ширина");
        int width = sc.nextInt();
        Maze maze = generator.generate(height, width);

        // Отображение начального лабиринта с точками начала и конца
        Renderer renderer = new ConsoleRenderer();
        LOGGER.info("Maze with Start (A) and End (B):");
        LOGGER.info("Generated maze:\n{}", renderer.render(maze)); // Отображение лабиринта с путем

        Coordinate start = maze.start();
        Coordinate end = maze.end();

        // Поиск пути и вывод результатов
        Solver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, start, end);


        if (path == null || path.isEmpty()) {
            LOGGER.info("No path found.");
        } else {
            LOGGER.info("Path found:");
            LOGGER.info("Maze with path:\n{}", renderer.render(maze, path)); // Отображение лабиринта с путем
        }
        sc.close();
    }
}
