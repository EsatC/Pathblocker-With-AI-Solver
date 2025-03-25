import java.util.List;
import  java.util.ArrayList;

public abstract class Solver {
	protected boolean hasWon = false;
	protected List<Board> solution = new ArrayList<>();
	
	public abstract List<Board> solve(Board board);
	
}
