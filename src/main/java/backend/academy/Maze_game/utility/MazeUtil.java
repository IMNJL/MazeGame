package backend.academy.Maze_game.utility;

import backend.academy.Maze_game.generators.Generator;
import backend.academy.Maze_game.generators.KruskalGenerator;
import backend.academy.Maze_game.generators.PrimGenerator;
import backend.academy.Maze_game.renders.ConsoleRenderer;
import backend.academy.Maze_game.renders.Renderer;
import backend.academy.Maze_game.solvers.AStarSolver;
import backend.academy.Maze_game.solvers.Solver;
import java.util.List;
import java.util.Scanner;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class MazeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MazeUtil.class);

    public static void runMazeGame(Scanner sc) {
        Generator generator = chooseGenerator(sc);
        int height = getDimension("height", sc);
        int width = getDimension("width", sc);

        // Генерация лабиринта и его отображение
        Maze maze = generateAndDisplayMaze(generator, height, width, sc);

        // Поиск пути и его отображение
        findAndDisplayPath(maze, maze.start(), maze.end());
    }

    private static Generator chooseGenerator(Scanner sc) {
        LOGGER.info("Выберите генератор лабиринта: 1 - Прима, 2 - Краскала");
        int choice = sc.nextInt();
        return switch (choice) {
            case 1 -> new PrimGenerator();
            case 2 -> new KruskalGenerator();
            default -> {
                LOGGER.info("Некорректный ввод, выбран генератор Прима по умолчанию.");
                yield new PrimGenerator();
            }
        };
    }

    private static int getDimension(String dimension, Scanner sc) {
        LOGGER.info("Выберите размер лабиринта|{}", dimension);
        return sc.nextInt();
    }

    private static Maze generateAndDisplayMaze(Generator generator, int height, int width, Scanner sc) {
        Maze maze = generator.generate(height, width); // Генерация лабиринта без точек начала и конца
        Renderer renderer = new ConsoleRenderer();
        LOGGER.info("Generated maze without start and end points:\n{}", renderer.render(maze));

        // Запрос координат для начала и конца
        Coordinate start = getCoordinateFromUser("A", height, width, sc);
        Coordinate end = getCoordinateFromUser("B", height, width, sc);

        // Генерация лабиринта с точками старта и конца
        maze = generator.generate(maze, start, end);

        // Отображение лабиринта с точками старта и конца
        LOGGER.info("Maze with start and end points:\n{}", renderer.render(maze));

        return maze;
    }

    private static void findAndDisplayPath(Maze maze, Coordinate start, Coordinate end) {

        Solver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, start, end);

        Renderer renderer = new ConsoleRenderer();
        if (path == null || path.isEmpty()) {
            LOGGER.info("No path found.");
        } else {
            LOGGER.info("Path found:");
            LOGGER.info("Maze with path:\n{}", renderer.render(maze, path)); // Отображение лабиринта с путем
        }
    }

    private static Coordinate getCoordinateFromUser(String pointName, int height, int width, Scanner sc) {
        int row;
        int col;
        boolean valid = false;
        Coordinate coord = null;
        while (!valid) {
            LOGGER.info("Введите координаты точки {} (строка колонка):", pointName);
            row = sc.nextInt();
            col = sc.nextInt();
            coord = new Coordinate(row, col);

            // Проверка на валидность координат
            if (row < 0 || row >= height || col < 0 || col >= width) {
                LOGGER.info("Координаты вне пределов лабиринта. Попробуйте снова.");
            } else {
                valid = true;
            }
        }
        return coord;
    }
}

