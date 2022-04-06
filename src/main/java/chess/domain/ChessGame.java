package chess.domain;

import java.util.Map;

import chess.domain.command.Command;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.domain.state.GameState;

public class ChessGame {

	private final String name;
	private final GameState state;

	public ChessGame(String name, GameState state) {
		this.name = name;
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public ChessGame execute(Command command) {
		GameState updatedState = state.proceed(command);
		return new ChessGame(this.name, updatedState);
	}

	public GameState getState() {
		return this.state;
	}

	public Map<Position, Piece> getBoard() {
		return this.state.getBoard();
	}

	public boolean isFinished() {
		return state.isFinished();
	}

	public boolean isReady() {
		return state.isReady();
	}

	public Piece getPieceByPosition(Position position) {
		return state.getByPosition(position);
	}
}
