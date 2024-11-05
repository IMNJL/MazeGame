package backend.academy.Maze_game;


import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AStarSolver implements Solver {
    private static final Logger LOGGER = LoggerFactory.getLogger(AStarSolver.class);
    private static final int A_STAR_SPACE = 0xFFFFFFFF;
    private static final int A_STAR_WALL = 0xFFFFFFFE;

    private int[][] map; 

    private int xs;
    private int ys;
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
        initializeMap();
        boolean isExpanded = expandMap(y0, x0, y1, x1);

        if (!isExpanded) {
            LOGGER.info("Path not found. Ending with no solution.");
            return;
        }

        buildPath(y1, x1);
    }

    private void initializeMap() {
        for (int y = 0; y < ys; y++) {
            for (int x = 0; x < xs; x++) {
                if (map[y][x] != A_STAR_WALL) {
                    map[y][x] = A_STAR_SPACE;
                }
            }
        }
    }

    private boolean expandMap(int y0, int x0, int y1, int x1) {
        List<Integer> px = new ArrayList<>();
        List<Integer> py = new ArrayList<>();

        // Initialize starting position
        px.add(x0);
        py.add(y0);
        map[y0][x0] = 0;

        for (int j = 1; j < xs * ys; j++) {
            if (map[y1][x1] != A_STAR_SPACE) {
                return true;
            }

            boolean expanded = false;
            int n0 = px.size(); // Number of cells to expand

            for (int ii = 0; ii < n0; ii++) {
                int x = px.get(ii);
                int y = py.get(ii);

                // Directions for up, down, left, right
                for (Direction direction : Direction.values()) {
                    int yy = y + direction.dy();
                    int xx = x + direction.dx();

                    if (0 <= yy && yy < ys && 0 <= xx && xx < xs && map[yy][xx] == A_STAR_SPACE) {
                        map[yy][xx] = j;
                        px.add(xx);
                        py.add(yy);
                        expanded = true;
                    }
                }
            }

            if (!expanded) {
                LOGGER.info("No expansion possible from current cells.");
                return false;
            }
        }

        return false;
    }

    private void buildPath(int y1, int x1) {
        int ps = map[y1][x1] + 1;
        List<Integer> pxPath = new ArrayList<>(ps); // Pre-sizing
        List<Integer> pyPath = new ArrayList<>(ps); // Pre-sizing

        int x = x1;
        int y = y1;
        for (int i = ps - 1; i >= 0; i--) {
            pxPath.add(x);
            pyPath.add(y);
            LOGGER.info("Adding step to path: ({}, {})", y, x);

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


        for (int i = 0; i < ps; i++) {
            path.add(new Coordinate(pyPath.get(i), pxPath.get(i)));
        }
    }
}
