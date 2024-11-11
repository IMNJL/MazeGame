package backend.academy.Maze_game;

import backend.academy.Maze_game.generators.Generator;
import backend.academy.Maze_game.generators.KruskalGenerator;
import backend.academy.Maze_game.generators.PrimGenerator;
import backend.academy.Maze_game.renders.ConsoleRenderer;
import backend.academy.Maze_game.renders.Renderer;
import backend.academy.Maze_game.renders.StylishConsoleRenderer;
import backend.academy.Maze_game.utility.Cell;
import backend.academy.Maze_game.utility.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;

public class MainTest {

    private Generator primGenerator;
    private Generator kruskalGenerator;
    private Renderer consoleRenderer;

    @BeforeEach
    public void setUp() {
        primGenerator = new PrimGenerator();
        kruskalGenerator = new KruskalGenerator();
        consoleRenderer = new StylishConsoleRenderer();
    }

      @Test
    public void testPrivateConstructor() throws Exception {
        var constructor = Main.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

    @Test
    public void testInvalidMazeSize() {
        String input = "1\n-10\n10"; // Некорректное значение размера лабиринта
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Main.main(new String[0]);
        });

        assertEquals("Maze dimensions must be positive", exception.getMessage());
    }



}
