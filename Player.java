import java.util.List;
import java.util.Stack;

public class Player {
	private Position position;
	private boolean isFirstMove;
	private Stack<Position> moveHistory;
	private Stack<List<Position>> wallsHistory;

	public Player(Position startPosition) {
		this.position = startPosition;
		this.isFirstMove = true;
		this.moveHistory = new Stack<>();
		this.wallsHistory = new Stack<>();

	}

	public void move(Board board, Directions direction) {
		Position oldPosition = position;
		Position newPosition = null;

		switch (direction) {
		case UP:
			newPosition = moveUntilObstacle(board, -1, 0);
			break;
		case DOWN:
			newPosition = moveUntilObstacle(board, 1, 0);
			break;
		case LEFT:
			newPosition = moveUntilObstacle(board, 0, -1);
			break;
		case RIGHT:
			newPosition = moveUntilObstacle(board, 0, 1);
			break;
		default:
			return;
		}

		if (!oldPosition.equals(newPosition)) {
			moveHistory.push(oldPosition);
			List<Position> wallsAddedThisMove = new Stack<>();
			blockPassedPositions(board, oldPosition, newPosition, direction, wallsAddedThisMove);

			if (isFirstMove) {
				board.placeObstacle(oldPosition.getX(), oldPosition.getY());
				wallsAddedThisMove.add(oldPosition);
				isFirstMove = false;
			}

			board.placeObstacle(oldPosition.getX(), oldPosition.getY());
			wallsAddedThisMove.add(oldPosition);
			wallsHistory.push(wallsAddedThisMove);

			board.updatePlayerPosition(oldPosition, newPosition);
			position = newPosition;
		}
	}

	public void undoMove(Board board) {
		if (moveHistory.isEmpty()) {
			return;
		}

		Position previousPosition = moveHistory.pop();
		Position currentPosition = this.position;

		if (!wallsHistory.isEmpty()) {
			List<Position> wallsAdded = wallsHistory.pop();
			for (Position wall : wallsAdded) {
				board.removeObstacle(wall.getX(), wall.getY());
			}
		}

		board.clearPosition(currentPosition.getX(), currentPosition.getY());
		board.updatePlayerPosition(previousPosition, previousPosition);
		this.position = previousPosition;
	}

	private Position moveUntilObstacle(Board board, int deltaX, int deltaY) {
		int newX = position.getX();
		int newY = position.getY();

		while (board.canMove(new Position(newX + deltaX, newY + deltaY))) {
			newX += deltaX;
			newY += deltaY;

			if (board.isGoalReached(new Position(newX, newY))) {
				break;
			}
		}

		return new Position(newX, newY);
	}

	public Position moveDirection(Board board, Directions direction) {
		switch (direction) {
		case UP:
			return moveUntilObstacle(board, -1, 0);
		case DOWN:
			return moveUntilObstacle(board, 1, 0);
		case LEFT:
			return moveUntilObstacle(board, 0, -1);
		case RIGHT:
			return moveUntilObstacle(board, 0, 1);
		}
		return position;
	}

	private void blockPassedPositions(Board board, Position oldPosition, Position newPosition, Directions direction,
			List<Position> wallsAddedThisMove) {
		int x = oldPosition.getX();
		int y = oldPosition.getY();

		while (x != newPosition.getX() || y != newPosition.getY()) {
			board.placeObstacle(x, y);
			wallsAddedThisMove.add(new Position(x, y));

			if (direction == Directions.UP)
				x--;
			if (direction == Directions.DOWN)
				x++;
			if (direction == Directions.LEFT)
				y--;
			if (direction == Directions.RIGHT)
				y++;
		}

		board.placeObstacle(x, y);
		wallsAddedThisMove.add(new Position(x, y));
	}

	public Stack<Position> getMoveHistory() {
		return this.moveHistory;
	}

	public Position getPosition() {
		return this.position;
	}
}
