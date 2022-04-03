package chess.repository;

import java.util.List;
import java.util.Optional;

import chess.domain.ChessGame;
import chess.domain.state.GameState;

public interface GameRepository {
	void save(ChessGame game);

	Optional<ChessGame> findByName(String name);

	void update(String name, GameState state);

	void remove(String name);

	List<String> findAllNames();
}