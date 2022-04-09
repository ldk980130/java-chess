package chess.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import chess.domain.ChessGame;
import chess.domain.command.Start;
import chess.domain.state.RunningWhiteTurn;

class ChessGameServiceTest {

	private static final String TEST_NAME = "test";

	private final ChessGameService gameService = new ChessGameService(new MemoryChessGameRepository());

	@AfterEach
	void clear() {
		gameService.deleteGame(TEST_NAME);
	}

	@Test
	@DisplayName("게임을 기록한다.")
	void saveGame() {
		gameService.createGame(TEST_NAME);
	}

	@Test
	@DisplayName("게임 이름으로 게임을 불러온다.")
	void findGame() {
		gameService.createGame(TEST_NAME);

		ChessGame findGame = gameService.findGame(TEST_NAME);

		assertThat(findGame.getName()).isEqualTo(TEST_NAME);
	}

	@Test
	@DisplayName("게임 상태 업데이트")
	void updateGame() {
		gameService.createGame(TEST_NAME);

		gameService.updateGame(new Start(), TEST_NAME);
		ChessGame updatedGame = gameService.findGame(TEST_NAME);

		assertThat(updatedGame.getState()).isInstanceOf(RunningWhiteTurn.class);
	}
}