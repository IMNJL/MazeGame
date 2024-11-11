package backend.academy.Maze_game.utility;

import lombok.Getter;
import lombok.Setter;

public final class Maze {


    /**
     * здесь создается лабиринт и парсятся сюдя данные лабиринта(высота, ширина) а затем создается матрица(грид)
     * */

    @Getter private final int height;
    @Getter private final int width;
    @Getter private final Cell[][] grid;
    @Getter @Setter private Coordinate start;
    @Getter @Setter private Coordinate end;

    public Maze(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new Cell[height][width];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col, Cell.Type.WALL); // Инициализируем стены
            }
        }
    }

    public Cell getCell(int row, int col) {
        return grid[row][col]; // Возвращаем ячейку
    }

    public void setCell(int row, int col, Cell.Type type) {
        grid[row][col] = new Cell(row, col, type); // Устанавливаем новую ячейку с новым типом
    }
}

