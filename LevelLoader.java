import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LevelLoader {
	
	private LevelLoader() {
		
	}
	
    public static Board loadLevel(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            int rows = lines.size();
            int cols = lines.get(0).length();
            char[][] grid = new char[rows][cols];
            Position startPosition = null;
            Position goalPosition = null;

            for (int i = 0; i < rows; i++) {
                grid[i] = lines.get(i).toCharArray();
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == 'P') {
                        startPosition = new Position(i, j);
                    } else if (grid[i][j] == 'G') {
                        goalPosition = new Position(i, j);
                    }
                }
            }

            return new Board(grid, goalPosition, startPosition);
        } catch (IOException e) {
            throw new RuntimeException("Seviye dosyası yüklenemedi: " + filePath, e);
        }
    }
}
