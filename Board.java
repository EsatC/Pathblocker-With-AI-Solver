public class Board {
    private char[][] grid;
    private Position goalPosition;
    private Position startPosition;

    public Board(char[][] grid, Position goalPosition, Position startPosition) {
        this.grid = grid;
        this.goalPosition = goalPosition;
        this.startPosition = startPosition;
    }

    public char[][] getGrid() {
        return grid;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public Position getStartPosition() {
        return startPosition;
    }

    public boolean isGoalReached(Position playerPosition) {
        return playerPosition.equals(goalPosition);
    }



    private char[][] copyGrid(char[][] source) {
        char[][] copy = new char[source.length][source[0].length];
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, copy[i], 0, source[i].length);
        }
        return copy;
    }

    public void clearPosition(int x, int y) {
        grid[x][y] = '.'; 
    }

    public void updatePlayerPosition(Position oldPosition, Position newPosition) {
        grid[oldPosition.getX()][oldPosition.getY()] = '#';  
        grid[newPosition.getX()][newPosition.getY()] = 'P';  

    }

    public void removeObstacle(int x, int y) {
        grid[x][y] = '.';  
    }

    public boolean canMove(Position newPosition) {
        if (newPosition.getX() < 0 || newPosition.getX() >= grid.length ||
                newPosition.getY() < 0 || newPosition.getY() >= grid[0].length) {
            return false;
        }
        return grid[newPosition.getX()][newPosition.getY()] != '#';
    }

    public void print() {
        for (char[] row : grid) {
            System.out.println(row);
        }
        System.out.println();
    }

    public void placeObstacle(int x, int y) {
        grid[x][y] = '#';
    }
    
    public Board copyAsNewBoard() {
    	char[][] newGrid = copyGrid(this.grid);
    	Board newBoard = new Board(newGrid, new Position(goalPosition.getX(),goalPosition.getY()) , new Position(startPosition.getX(),startPosition.getY()));
    	return newBoard;
    }
    
}
