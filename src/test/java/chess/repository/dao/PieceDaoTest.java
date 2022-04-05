package chess.repository.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {

	private static final String TEST_NAME = "test";

	private final PieceDao pieceDao = new PieceDao();
	private final ChessGameDao chessGameDao = new ChessGameDao();

	@AfterEach
	void clear() {
		chessGameDao.delete(TEST_NAME);
	}

	@Test
	@DisplayName("피스 insert 확인")
	void insert() {
		insertChessGame();
	}

	private int insertChessGame() {
		int foreignKey = chessGameDao.insert(TEST_NAME, "READY");
		Map<String, String> tiles = Map.of("a1", "WHITE_PAWN", "b2", "BLACK_KING");
		pieceDao.insertAll(tiles, foreignKey);
		return foreignKey;
	}

	@Test
	@DisplayName("외래키로 피스 조회")
	void selectWhereForeignKey() {
		int foreignKey = insertChessGame();

		Map<String, String> result = pieceDao.selectByGameId(foreignKey);

		assertThat(result)
			.containsEntry("a1", "WHITE_PAWN")
			.containsEntry("b2", "BLACK_KING");
	}

	@Test
	@DisplayName("게임 이름으로 피스 조회")
	void selectByGameName() {
		insertChessGame();

		Map<String, String> result = pieceDao.selectByGameName(TEST_NAME);

		assertThat(result)
			.containsEntry("a1", "WHITE_PAWN")
			.containsEntry("b2", "BLACK_KING");
	}

	@Test
	@DisplayName("위치와 게임 이름으로 피스 삭제")
	void deleteByPosition() {
		insertChessGame();

		pieceDao.deleteByPosition("a1", TEST_NAME);

		Map<String, String> result = pieceDao.selectByGameName(TEST_NAME);
		assertThat(result)
			.containsExactlyEntriesOf(Map.of("b2", "BLACK_KING"));
	}

	@Test
	@DisplayName("피스 위치를 수정한다.")
	void update() {
		insertChessGame();

		pieceDao.updatePositionOfPiece("WHITE_PAWN", "a1", "a2", TEST_NAME);

		Map<String, String> result = pieceDao.selectByGameName(TEST_NAME);
		assertThat(result)
			.containsEntry("a2", "WHITE_PAWN");
	}
}