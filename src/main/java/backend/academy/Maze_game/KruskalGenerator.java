package backend.academy.Maze_game;

public class KruskalGenerator implements Generator {
    @Override
    public Maze generate(int height, int width) {
        // Реализация алгоритма Краскала
        return new Maze(height, width);
    }
}