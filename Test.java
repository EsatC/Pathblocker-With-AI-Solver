/*
	1) Why you prefer the search algorithm you choose?
	
	We implemented A* Search since we have costs and we want to find an optimal way. 
	We could have implemented Dijkstra algorithm as well but since we don't have heuristic function in Dijkstra algorithm
	it searches way more states than A* and that makes A* more feasible since we have admissible heuristic function
	
	2) Can you achieve the optimal result? Why? Why not?
	
	We can achieve the optimal result because we have admissible heuristic function
	
	3) How you achieved efficiency for keeping the states?
	
	We used HashMap called visited to store visited nodes, priority queue to store frontier nodes in each state 
	and also another HashMap to store costSoFar
	
	4) If you prefer to use DFS (tree version) then do you need to avoid cycles?
	
	In cost problems DFS does not make any sense if we need to find an optimal path
	
	5) What will be the path-cost for this problem?
	
	Each tile(+1) we visited plus elevation diffs through a direction
	
	6)Space complexity is a little bit high since we copy Board each time we move,
	However by using undo mechanism things may get more complicated and harder to maintain
	
	7)We generally used HashMaps to store unique keys with its corresponding value 
	to reach an item with given key in O(1) time	
	
	In our OOP design, one can implement a new search class by extending 
	Solver class(which would simply have solve method and solution list) and then use it

*/
public class Test {
    public static void main(String[] args) {
        PngSaver pngSaver = new PngSaver(10);  
        long time = System.nanoTime();
        
        for (int level = 1; level <= 10; level++) {
            String levelFilePath = String.format("level%02d.txt", level);  
            String levelDirectory = String.format("level%02d", level);     

            Game game = new Game(levelFilePath, pngSaver, levelDirectory, 3);
         
            
            game.solveWithAStar();
            game.printSolutionAsPngWithPyramids();

        }
        
        time = System.nanoTime() - time;
        
        System.out.println("Execution time: " + time / 1000000000.0  + " seconds");
    }
}
