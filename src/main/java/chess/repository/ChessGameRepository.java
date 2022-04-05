package chess.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import chess.domain.ChessGame;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.domain.state.GameState;
import chess.converter.BoardToStringConverter;
import chess.converter.StringToStateConverter;
import chess.repository.dao.ChessGameDao;
import chess.repository.dao.TileDao;

public class ChessGameRepository implements GameRepository {

	private final ChessGameDao chessGameDao = new ChessGameDao();
	private final TileDao tileDao = new TileDao();

	@Override
	public void save(ChessGame game) {
		GameState state = game.getState();
		tileDao.insertAll(
			BoardToStringConverter.from(state.getBoard()),
			chessGameDao.insert(game.getName(), state.toString())
		);
	}

	@Override
	public Optional<ChessGame> findByName(String name) {
		String state;
		try {
			state = chessGameDao.selectStateByName(name);
		} catch (IllegalArgumentException exception) {
			return Optional.empty();
		}
		Map<String, String> tiles = tileDao.selectByGameName(name);
		GameState gameState = StringToStateConverter.of(state, tiles);

		return Optional.of(new ChessGame(name, gameState));
	}

	@Override
	public void updateStateOfGame(ChessGame game) {
		chessGameDao.updateState(game.getName(), game.getState().toString());
	}

	@Override
	public void updatePositionOfPiece(ChessGame game, Position from, Position to) {
		String gameName = game.getName();
		tileDao.deleteByPosition(to.toString(), gameName);
		Piece piece = game.getPieceByPosition(to);

		tileDao.updatePositionOfPiece(piece.toString(), from.toString(), to.toString(), gameName);
	}

	@Override
	public void remove(String name) {
		chessGameDao.delete(name);
	}

	@Override
	public List<String> findAllNames() {
		return chessGameDao.selectAllNames();
	}
}
