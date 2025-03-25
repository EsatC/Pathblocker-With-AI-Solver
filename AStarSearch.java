import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Arrays;


public class AStarSearch extends Solver {
    private final Player player;
    private final Scene scene;

    public AStarSearch(Player player, Scene scene) {
        this.player = player;
        this.scene = scene;
    }

    @Override
    public List<Board> solve(Board board) {
        //Visited positions stored as StatedNodes in the visited map to reconstruct path at the final
        Map<String, StateNode> visited = new HashMap<>();
        //After each exploration, adjacent positions are added to priority queue
        //In order to calculate priorities we implemented StateCost class and since it uses f = g + h function
        //it is needed only in A* search so there is no need to make it public and separate class
        PriorityQueue<StateCost> frontier = new PriorityQueue<>();

        
        Position startPos = player.getPosition();
        String startKey = buildKey(board, startPos);

        //Put start node in the visited map
        visited.put(startKey, new StateNode(board, startPos, null)); //Since there is no parent of that node parentKey is null
        
        frontier.offer(new StateCost(board, startPos, 0, estimateHeuristic(startPos, board)));

        // We also track the cost so far in a separate map to find if there exist a cheaper way to same state
        Map<String, Integer> costSoFar = new HashMap<>();
        costSoFar.put(startKey, 0);

        while (!frontier.isEmpty()) {
            StateCost current = frontier.poll();
            Board currentBoard = current.board;
            Position currentPos = current.position;

            // If it reached the goal call reconstructPath method
            if (currentBoard.isGoalReached(currentPos)) {
                String finalKey = buildKey(currentBoard, currentPos);
                return reconstructPath(visited, finalKey);
            }

            // For each direction, simulate the slide
            for (Directions dir : Directions.values()) {
                SlideResult slide = slideAlongDirection(currentBoard, currentPos, dir);
                Position nextPos = slide.finalPos;
                int slideCost = slide.cost;

                // If we couldn't move skip
                if (nextPos.equals(currentPos)) {
                    continue;
                }

                // Make a new board and a player for that next state
                Board nextBoard = currentBoard.copyAsNewBoard();
                Player nextPlayer = new Player(currentPos); 
                nextPlayer.move(nextBoard, dir); 
                Position updatedPos = nextPlayer.getPosition(); 

                String currentKey = buildKey(currentBoard, currentPos);
                int gSoFar = costSoFar.getOrDefault(currentKey, Integer.MAX_VALUE);

                int newCost = (gSoFar == Integer.MAX_VALUE) ? slideCost: gSoFar + slideCost;

                String nextKey = buildKey(nextBoard, updatedPos);

                // If this path is better (cheaper)
                int oldCost = costSoFar.getOrDefault(nextKey, Integer.MAX_VALUE);
                if (newCost < oldCost) {
                    costSoFar.put(nextKey, newCost);
                    
                    // Store the new state in visited, pointing to the parentKey = currentKey
                    visited.put(nextKey, new StateNode(nextBoard, updatedPos, currentKey));

                    int priority = newCost + estimateHeuristic(updatedPos, nextBoard);
                    frontier.offer(new StateCost(nextBoard, updatedPos, newCost, priority));
                }
            }
        }

        // If no solution exist
        return new ArrayList<>();
    }

    //Move in a direction and return SlideDresult which contains its position and cost
    private SlideResult slideAlongDirection(Board board, Position startPos, Directions dir) {

        int dx = 0, dy = 0;
        switch(dir) {
            case UP:    dx = -1; break;
            case DOWN:  dx =  1; break;
            case LEFT:  dy = -1; break;
            case RIGHT: dy =  1; break;
        }

        int currentX = startPos.getX();
        int currentY = startPos.getY();
        int totalCost = 0;

        while (true) {
            int nextX = currentX + dx;
            int nextY = currentY + dy;
            Position nextPos = new Position(nextX, nextY);

            
            if (!board.canMove(nextPos)) {
                break;
            }
            // Cost for stepping from (currentX, currentY) to (nextX, nextY)
            totalCost += computeMovementCost(currentX, currentY, nextX, nextY);

            currentX = nextX;
            currentY = nextY;

            if (board.isGoalReached(nextPos)) {
                break;
            }
        }
        return new SlideResult(new Position(currentX, currentY), totalCost);
    }

    
    // 1 base cost + elevation difference
    private int computeMovementCost(int x1, int y1, int x2, int y2) {
        int fromElev = scene.getElevation(x1, y1);
        int toElev   = scene.getElevation(x2, y2);
        if (fromElev < 0) fromElev = 0;
        if (toElev < 0)   toElev   = 0;
        int delta = Math.abs( toElev - fromElev); //Since descent has cost too we need to take absolute value of that
        return 1 + delta;
    }

    //To estimate heuristic we used Manhattan distance + elevation differences
    private int estimateHeuristic(Position currentPos, Board board) {
        Position goal = board.getGoalPosition();
        if (goal == null) return 0;

        int dx = Math.abs(currentPos.getX() - goal.getX());
        int dy = Math.abs(currentPos.getY() - goal.getY());
        int manhattan = dx + dy;

        int curElev = scene.getElevation(currentPos.getX(), currentPos.getY());
        int goalElev= scene.getElevation(goal.getX(), goal.getY());
        if (curElev < 0) curElev = 0;
        if (goalElev< 0) goalElev= 0;
        int elevDiff = Math.abs(curElev - goalElev) / 2;

        return manhattan + elevDiff;
    }

    //Reconstructing the path from map by using endKeys
    private List<Board> reconstructPath(Map<String, StateNode> visited, String endKey) {
        LinkedList<Board> path = new LinkedList<>();
        StateNode node = visited.get(endKey);

        // Go backwards
        while (node != null) {
            path.addFirst(node.board); //Add as first item of the list since we are going backward
            node = visited.get(node.parentKey); // Get the parent node of current node
        }
        return path;
    }

    //Build a hash key to use in the visited map
    private String buildKey(Board board, Position pos) {
        return pos.toString() + Arrays.deepToString(board.getGrid());
    }

    
    //StateCost class to make adding operations to priority queue easier
    private static class StateCost implements Comparable<StateCost> {
        Board board;
        Position position;
        int gCost;
        int priority; // f(n) = g(n) + h(n)

        public StateCost(Board board, Position position, int gCost, int priority) {
            this.board = board;
            this.position = position;
            this.gCost = gCost;
            this.priority = priority;
        }
        //We have to override compareTo function in order to add these objects in a priority queue
        @Override
        public int compareTo(StateCost other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    //StateNode class is implemented to reconstruct the path for solution since we have parentKey for each node
    private static class StateNode {
        Board board;
        Position pos;
        String parentKey; // key of the parent state

        StateNode(Board b, Position p, String parentKey) {
            this.board = b;
            this.pos = p;
            this.parentKey = parentKey;
        }
    }


}
