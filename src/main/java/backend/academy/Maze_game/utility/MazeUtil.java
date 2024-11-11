package backend.academy.Maze_game.utility;

import backend.academy.Maze_game.generators.Generator;
import backend.academy.Maze_game.generators.KruskalGenerator;
import backend.academy.Maze_game.generators.PrimGenerator;
import backend.academy.Maze_game.renders.Renderer;
import backend.academy.Maze_game.renders.StylishConsoleRenderer;
import backend.academy.Maze_game.solvers.AStarSolver;
import backend.academy.Maze_game.solvers.BFSSolver;
import backend.academy.Maze_game.solvers.DFSSolver;
import backend.academy.Maze_game.solvers.DijkstraSolver;
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
        Solver solver  = chooseSolver(sc);
        findAndDisplayPath(maze, maze.start(), maze.end(), solver);
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

    private static Solver chooseSolver(Scanner sc) {
        LOGGER.info("Выберите алгоритм поиска пути: 1 - A*, 2 - Дейкстра, 3 - BFS, 4 - DFS");
        int solverChoice = sc.nextInt();
        return switch (solverChoice) {
            case 1 -> new AStarSolver(); // A* алгоритм
            case 2 -> new DijkstraSolver(); // Дейкстра
            case 3 -> new BFSSolver(); // BFS
            case 4 -> new DFSSolver(); // DFS
            default -> {
                LOGGER.info("Некорректный ввод, выбран A* по умолчанию.");
                yield new AStarSolver(); // Если выбор неверный, по умолчанию A*
            }
        };
    }

    private static int getDimension(String dimension, Scanner sc) {
        LOGGER.info("Выберите размер лабиринта|{}", dimension);
        return sc.nextInt();
    }

    private static Maze generateAndDisplayMaze(Generator generator, int height, int width, Scanner sc) {
        Maze maze = generator.generate(height, width); // Генерация лабиринта без точек начала и конца
        Renderer renderer = new StylishConsoleRenderer();
        LOGGER.info("Generated maze without start and end points:\n{}", renderer.render(maze));

        // Запрос координат для начала и конца
        Coordinate start = getCoordinateFromUser("A", height, width, maze, sc);
        Coordinate end = getCoordinateFromUser("B", height, width, maze, sc);

        // Генерация лабиринта с точками старта и конца
        maze = generator.generate(maze, start, end);

        // Отображение лабиринта с точками старта и конца
        LOGGER.info("Maze with start and end points:\n{}", renderer.render(maze));

        return maze;
    }

    private static void findAndDisplayPath(Maze maze, Coordinate start, Coordinate end, Solver solver) {

        // Дальше можно использовать solver для решения задачи поиска пути
        LOGGER.info("Используем решатель: {}", solver.getClass().getSimpleName());

        LOGGER.info("Start coordinates: {}", start);
        LOGGER.info("End coordinates: {}", end);

        List<Coordinate> path = solver.solve(maze, start, end);

        Renderer renderer = new StylishConsoleRenderer();
        if (path == null || path.isEmpty()) {
            LOGGER.info("No path found.");
        } else {
            LOGGER.info("Path found:");
            LOGGER.info("Maze with path:\n{}", renderer.render(maze, path)); // Отображение лабиринта с путем
        }
    }

    private static Coordinate getCoordinateFromUser(String pointName, int height, int width, Maze maze, Scanner sc) {
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
            } else if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                LOGGER.info("Координаты указывают на стену. Пожалуйста, выберите другую точку.");
            } else {
                valid = true;
            }
        }
        return coord;
    }
}

