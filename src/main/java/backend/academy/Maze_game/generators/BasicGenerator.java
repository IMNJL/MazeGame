package backend.academy.Maze_game.generators;

import backend.academy.Maze_game.renders.Renderer;
import backend.academy.Maze_game.renders.StylishConsoleRenderer;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static backend.academy.Maze_game.utility.MazeUtil.getCoordinateFromUser;

public class BasicGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicGenerator.class);
    private final Scanner sc = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    public Maze getMaze(int height, int width) {
        // создание лабиринта без точек старта и конца
        Maze maze = new Maze(height, width);
        generateMazeWithoutStartEnd(maze);
        // отображение лабиринта
        Renderer renderer = new StylishConsoleRenderer();
        LOGGER.info("Generated maze without start and end points:\n{}", renderer.render(maze));

        // получение начальной и конечной точек от пользователя
        Coordinate start = getCoordinateFromUser("A", height, width, maze, sc);
        Coordinate end = getCoordinateFromUser("B", height, width, maze, sc);

        maze.start(start);
        maze.end(end);

        // создание лабиринта с точками старта и конца
        generateMazeWithStartEnd(maze, start, end);

        // Отображение лабиринта с точками старта и конца
        Renderer renderer1 = new StylishConsoleRenderer();
        LOGGER.info("Maze with start and end points:\n{}", renderer1.render(maze));
        return maze;
    }

    public void generateMazeWithoutStartEnd(Maze maze) {}
    public void generateMazeWithStartEnd(Maze maze, Coordinate start, Coordinate end) {}

}
