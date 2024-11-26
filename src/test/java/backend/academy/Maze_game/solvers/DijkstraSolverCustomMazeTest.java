package backend.academy.Maze_game.solvers;

import backend.academy.Maze_game.renders.StylishConsoleRenderer;
import backend.academy.Maze_game.solvers.BFSSolver;
import backend.academy.Maze_game.solvers.DijkstraSolver;
import backend.academy.Maze_game.solvers.Solver;
import backend.academy.Maze_game.utility.Coordinate;
import backend.academy.Maze_game.utility.Maze;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static backend.academy.Maze_game.utility.Cell.Type.PASSAGE;
import static org.assertj.core.api.Assertions.assertThat;

public class DijkstraSolverCustomMazeTest {

    private final StylishConsoleRenderer renderer = new StylishConsoleRenderer();

    private static Stream<Arguments> findPathTestData() {
        return Stream.of(
                 new AStarSolver(),
//                new BFSSolver(),
                // new DFSSolver(),
                new DijkstraSolver())
            .map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("findPathTestData")
    void findPathTest(Solver solver) {
        var maze = createMaze();
        System.out.println(renderer.render(maze));

        var path = solver.solve(maze);

        assertThat(path).isNotEmpty();
        System.out.println(renderer.render(maze, path));
    }

    @Test
    void findPathDijkstraTest() {
        var maze = createMaze();
        System.out.println(renderer.render(maze));

        var path = new DijkstraSolver().solve(maze);

        assertThat(path).isNotEmpty();
        System.out.println(renderer.render(maze, path));
    }

    private Maze createMaze() {
        var maze = new Maze(3, 1);
        maze.setCell(0, 0, PASSAGE);
        maze.setCell(1, 0, PASSAGE);
        maze.setCell(2, 0, PASSAGE);
        maze.start(new Coordinate(0, 0))
            .end(new Coordinate(2, 0));

        return maze;
    }
}
