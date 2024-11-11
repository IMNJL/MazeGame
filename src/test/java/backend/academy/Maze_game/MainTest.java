package backend.academy.Maze_game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;

public class MainTest {

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
