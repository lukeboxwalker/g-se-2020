package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

public class GameImpl implements Game {

    private final TurnValidator turnValidator;
    private final RuleManagerImpl ruleManager;
    private final BoardImpl board;
    private Points points;

    public GameImpl(final BoardImpl board) {
        this.board = board;
        this.points = new Points();
        this.turnValidator = new TurnValidator(board);
        this.ruleManager = new RuleManagerImpl(board);
    }

    @Override
    public void applyTurn(final Turn turn) throws InvalidTurnException {
        turnValidator.validateTurn(turn);
        board.cross(turn.getPositionsToCross());
        points = ruleManager.calculatePoints();
    }

    @Override
    public RuleManager getRuleManger() {
        return ruleManager;
    }

    @Override
    public Points getPoints() {
        return points;
    }

    @Override
    public Board getBoard() {
        return board;
    }
}
