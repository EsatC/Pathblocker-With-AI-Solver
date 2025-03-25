import java.util.List;
public class Game {
    private Board board;
    private Player player;
    private String levelDirectory;
    private List<Board> solution;
    private PngSaver pngSaver;
    private Scene scene;

    public Game(String levelFilePath, PngSaver pngSaver, String levelDirectory) {
        this.board = LevelLoader.loadLevel(levelFilePath);
        this.player = new Player(board.getStartPosition());
        this.levelDirectory = levelDirectory;
        this.pngSaver = pngSaver;
    }
    
    public Game(String levelFilePath, PngSaver pngSaver, String levelDirectory, int pyramidSize) {
    	this(levelFilePath, pngSaver, levelDirectory);
    	this.scene = new Scene(this.board.getGrid().length, pyramidSize);
    }

    public void solveWithDFS(){
    	DepthFirstSearch depthFirstSearch = new DepthFirstSearch(this.player);
    	solution = depthFirstSearch.solve(this.board);
    }
    
    public void solveWithBFS() {
    	BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch(this.player);
    	solution = breadthFirstSearch.bfs(board);
    	
    }

    public void solveWithAStar() {
        AStarSearch aStar = new AStarSearch(player, scene);
        solution = aStar.solve(board);
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public List<Board> getSolution(){
    	return this.solution;
    }
    
    public void printSolutionAsPng() {
    	int stepCount = 1;
    	for(Board board : getSolution()) {
            pngSaver.saveBoardAsPng(board, stepCount++, levelDirectory);
        	
        }
    }
    
    public void printSolutionAsPngWithPyramids() {
    	int stepCount = 1;
    	for(Board board : getSolution()) {
            pngSaver.saveBoardAsPng(board, stepCount++, levelDirectory, scene);
        	
        }
    }
    
}
