package chess.service;

import java.util.List;

import chess.domain.ChessGame;
import chess.domain.board.BoardInitializer;
import chess.domain.command.Command;
import chess.domain.state.Ready;
import chess.repository.GameRepository;

public class ChessGameService {

	private static final String NOT_EXIST_GAME = "해당하는 이름의 게임이 존재하지 않습니다.";

	private final GameRepository gameRepository;

	public ChessGameService(GameRepository gameRepository) {
		this.gameRepository = gameRepository;
	}

	public ChessGame createGame(String name) {
		ChessGame chessGame = new ChessGame(name, new Ready(BoardInitializer.generate()));
		gameRepository.save(chessGame);
		return chessGame;
	}

	public ChessGame findGame(String name) {
		return gameRepository.findByName(name)
			.orElseThrow(() -> new IllegalArgumentException(NOT_EXIST_GAME));
	}

	public ChessGame updateGame(Command command, String name) {
		ChessGame updatedGame = findGame(name).execute(command);
		gameRepository.updateGame(updatedGame, command);
		return updatedGame;
	}

	public void deleteGame(String name) {
		gameRepository.remove(name);
	}

	public List<String> findAllGames() {
		return gameRepository.findAllNames();
	}
}
