package backend.academy.Maze_game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        int height = 10;
        int width = 10;

        Scanner sc = new Scanner(System.in);
        System.out.println("Выберите генератор лабиринта: 1 - Прима, 2 - Краскала");
        int choice = sc.nextInt();

        Generator generator = switch (choice) {
            case 1 -> new PrimGenerator();
            case 2 -> new KruskalGenerator();
            default -> new PrimGenerator();
        };

        Maze maze = generator.generate(height, width);

        // Отображение начального лабиринта с точками начала и конца
        Renderer renderer = new ConsoleRenderer();
        System.out.println("Maze with Start (A) and End (B):");
        System.out.println(renderer.render(maze));

        Coordinate start = maze.start();
        Coordinate end = maze.end();

        // Поиск пути и вывод результатов
        Solver solver = new AStarSolver();
        List<Coordinate> path = solver.solve(maze, start, end);


        if (path == null || path.isEmpty()) {
            LOGGER.info("No path found.");
        } else {
            LOGGER.info("Path found:");
            LOGGER.info(STR."\n\{renderer.render(maze, path)}"); // Отображение лабиринта с путем
        }
    }
}
