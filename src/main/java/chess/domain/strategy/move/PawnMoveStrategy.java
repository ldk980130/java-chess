package chess.domain.strategy.move;

import chess.domain.board.Board;
import chess.domain.piece.Piece;
import chess.domain.piece.Team;
import chess.domain.position.Position;

public class PawnMoveStrategy extends MoveStrategy {
    private static final int WHITE_START_POSITION = 2;
    private static final int BLACK_START_POSITION = 7;

    @Override
    public boolean checkMovement(Position source, Position target, Board board) {

        int fileGap = source.calculateFileGap(target);
        int rankGap = source.calculateRankGap(target);

        Piece sourcePiece = board.getPiece(source);
        Piece targetPiece = board.getPiece(target);
        Team pawnTeam = sourcePiece.getTeam();

        if (pawnTeam.isWhite()) {
            if (fileGap == 0 && rankGap == -1) {
                return board.isEmpty(target);
            }
            if (fileGap == 0 && rankGap == -2) {
                return source.getRank() == WHITE_START_POSITION;
            }
            if (Math.abs(fileGap) == 1 && rankGap == -1) {
                return !board.isEmpty(target) && sourcePiece.isEnemy(targetPiece);
            }
        }
        if (!pawnTeam.isWhite()) {
            if (fileGap == 0 && rankGap == 1) {
                return board.isEmpty(target);
            }
            if (fileGap == 0 && rankGap == 2) {
                return source.getRank() == BLACK_START_POSITION;
            }
            if (Math.abs(fileGap) == 1 && rankGap == 1) {
                return !board.isEmpty(target) && sourcePiece.isEnemy(targetPiece);
            }
        }
        return false;
    }
}