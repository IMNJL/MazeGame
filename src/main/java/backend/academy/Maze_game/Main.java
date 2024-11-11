package backend.academy.Maze_game;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static backend.academy.Maze_game.utility.MazeUtil.runMazeGame;

public final class Main {
    private Main() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        runMazeGame(sc);

        sc.close();
    }
}
