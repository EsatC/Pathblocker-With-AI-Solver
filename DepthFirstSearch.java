import java.util.List;

public class DepthFirstSearch extends Solver {

	Player player;

	public DepthFirstSearch(Player player) {
		this.player = player;
	}

	@Override
	public List<Board> solve(Board board) {
		solution.add(board.copyAsNewBoard());
		dfs(board, player.getPosition());
		if (hasWon) {
			return solution;
		}
		return null;
	}

	private boolean dfs(Board board, Position currentPosition) {

		if (board.isGoalReached(currentPosition)) {
			hasWon = true;
			return true;
		}

		player.getMoveHistory().push(currentPosition);

		Directions[] directions = Directions.values();

		for (Directions direction : directions) {
			Position newPosition = player.moveDirection(board, direction);

			if (!player.getMoveHistory().contains(newPosition) && board.canMove(newPosition)) {
				player.move(board, direction);
				Board copyBoard = board.copyAsNewBoard();
				solution.add(copyBoard);
				if (dfs(board, newPosition)) {
					return true;
				}

				player.undoMove(board);
				solution.remove(solution.size() - 1);

			}
		}

		player.getMoveHistory().pop();
		return false;
	}
}
