package backend.academy.Maze_game.utility;

public enum Elements {
    BORDER("---+"),
    WALL(" # |"),
    SPACE("   |"),
    PATH("<*>|"),
    STARTPOINT(" A |"),
    ENDPOINT(" B |"),
    PLUS("+"),
    END("\n"),
    SP("|");

    private final String symbol;

    Elements(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
