import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class PngSaver {
    private int tileSize;

    public PngSaver(int tileSize) {
        this.tileSize = tileSize;

    }

    public void saveBoardAsPng(Board board, int stepNumber, String directoryName) {
        int width = board.getGrid()[0].length * tileSize;
        int height = board.getGrid().length * tileSize;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        char[][] grid = board.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 'P') {
                    g.setColor(Color.YELLOW);  // Player
                } else if (grid[i][j] == '#') {
                    g.setColor(new Color(53, 13, 98));  // Dark purple for walls
                } else if (grid[i][j] == 'G') {
                    // Draw black and white checkered pattern for the goal
                    for (int y = 0; y < tileSize; y++) {
                        for (int x = 0; x < tileSize; x++) {
                            boolean isEvenSquare = ((x / (tileSize / 4)) + (y / (tileSize / 4))) % 2 == 0;
                            g.setColor(isEvenSquare ? Color.BLACK : Color.WHITE);
                            g.fillRect(j * tileSize + x, i * tileSize + y, 1, 1);
                        }
                    }
                } else {
                    g.setColor(new Color(180, 180, 250));  // Light purple for empty spaces
                }

                // No longer draw the grid lines to keep tiles seamless
                if (grid[i][j] != 'G') {  // Don't draw over the checkered pattern
                    g.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);
                }
            }
        }

        g.dispose();

        try {
            File dir = new File(directoryName);
            if (!dir.exists()) {
                dir.mkdir();
            }

            // Format the file name: 0001.png, 0002.png, etc.
            String fileName = String.format("%04d.png", stepNumber);
            File outputFile = new File(directoryName + "/" + fileName);
            ImageIO.write(image, "png", outputFile);
            System.out.println("Step saved as PNG: " + outputFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveBoardAsPng(Board board, int stepNumber, String directoryName, Scene scene) {
    	int width = board.getGrid()[0].length * tileSize;
        int height = board.getGrid().length * tileSize;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        char[][] grid = board.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char tile = grid[i][j];

                // Player
                if (tile == 'P') {
                    g.setColor(Color.YELLOW);

                // Walls
                } else if (tile == '#') {
                    g.setColor(new Color(53, 13, 98));  // Dark purple for walls

                // Goal
                } else if (tile == 'G') {
                    // Draw black/white checkered pattern
                    for (int y = 0; y < tileSize; y++) {
                        for (int x = 0; x < tileSize; x++) {
                            boolean isEvenSquare =
                                ((x / (tileSize / 4)) + (y / (tileSize / 4))) % 2 == 0;
                            g.setColor(isEvenSquare ? Color.BLACK : Color.WHITE);
                            g.fillRect(j * tileSize + x, i * tileSize + y, 1, 1);
                        }
                    }
                    // Skip the usual fillRect so we don't overwrite the checkered pattern
                    continue;

                // Everything else (i.e. '.' or other empty tiles)
                } else {
                    // 1) Get the elevation from Scene
                    int elev = scene.getElevation(i, j);
                    // Make sure it's in [0..9]
                    if (elev < 0) elev = 0;
                    if (elev > 9) elev = 9;

                    // 2) Convert that elevation to a gray tone.
                    //    For example, 0 -> (50,50,50) and 9 -> (230,230,230).
                    //    Adjust the range as you like.
                    int minGray = 50;    // darkest
                    int maxGray = 230;   // lightest
                    // linear interpolation
                    int grayValue = minGray + (elev * (maxGray - minGray) / 9);

                    g.setColor(new Color(grayValue, grayValue, grayValue));
                }

                // Fill the tile (unless we already did a checkered pattern for 'G')
                g.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);
            }
        }

        g.dispose();

        try {
            File dir = new File(directoryName);
            if (!dir.exists()) {
                dir.mkdir();
            }

            String fileName = String.format("%04d.png", stepNumber);
            File outputFile = new File(directoryName + "/" + fileName);
            ImageIO.write(image, "png", outputFile);
            System.out.println("Step saved as PNG: " + outputFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
