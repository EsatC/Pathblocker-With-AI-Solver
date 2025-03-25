import java.util.*;

public class BreadthFirstSearch extends Solver {
    private Player player;

    public BreadthFirstSearch(Player player) {
        this.player = player;
    }

    @Override
    public List<Board> solve(Board board) {
        solution = bfs(board);
        if(hasWon)
        return solution; // No solution found
        
        return null;
    }
    
    public List<Board> bfs(Board board) {
        Queue<Position> queue = new LinkedList<>();
        Queue<Board> boardQueue = new LinkedList<>();
        // Change to store Board states instead of Directions
        Queue<List<Board>> pathQueue = new LinkedList<>();
        
        Set<String> visited = new HashSet<>();
        
        queue.add(player.getPosition());
        boardQueue.add(board);
        // Initialize the path with the starting board
        List<Board> initialPath = new ArrayList<>();
        initialPath.add(board);
        pathQueue.add(initialPath);
        
        visited.add(generateStateHash(player.getPosition(), board));
        
        while (!queue.isEmpty()) {
            Position current = queue.poll();
            Board currBoard = boardQueue.poll();
            List<Board> currentPath = pathQueue.poll();
            
            if (currBoard.isGoalReached(current)) {
            	hasWon = true;
                return currentPath; // Return the sequence of board states
            }
            
            Directions[] directions = Directions.values();
            for (Directions direction : directions) {
                Player newPlayer = new Player(current);
                Position newPosition = newPlayer.moveDirection(currBoard, direction);
                
                if (newPosition != null && currBoard.canMove(newPosition)) {
                    Board newBoard = currBoard.copyAsNewBoard();
                    newPlayer.move(newBoard, direction);
                    
                    String newStateHash = generateStateHash(newPosition, newBoard);
                    
                    if (!visited.contains(newStateHash)) {
                        queue.add(newPosition);
                        boardQueue.add(newBoard);
                        
                        // Create a new path list with the current board state
                        List<Board> newPath = new ArrayList<>(currentPath);
                        newPath.add(newBoard);
                        pathQueue.add(newPath);
                        
                        visited.add(newStateHash);
                        
                        if (newBoard.isGoalReached(newPosition)) {
                            return newPath;
                        }
                    }
                }
            }
        }
        
        return new ArrayList<>(); // Return empty list if no solution is found
    }

    private String generateStateHash(Position position, Board board) {
        return position.toString() + Arrays.deepToString(board.getGrid());
    }
}