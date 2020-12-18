package de.techfak.se.lwalkenhorst.game;

import de.techfak.se.lwalkenhorst.exception.InvalidTurnException;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class GameImpl implements Game {

    private final PropertyChangeSupport propertyChangeSupport;
    private final TurnValidator turnValidator;
    private final RuleManagerImpl ruleManager;
    private final BoardImpl board;

    private final GameStrategy gameStrategy;
    private Points points;
    private Round round;

    public GameImpl(final BoardImpl board) {
        this.board = board;
        this.points = new Points();
        this.gameStrategy = new SinglePlayerStrategy();
        this.turnValidator = new TurnValidator(board);
        this.ruleManager = new RuleManagerImpl(board);
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    @Override
    public void play() {
        round = Round.enterFirst(gameStrategy);
    }

    @Override
    public void applyTurn(final Turn turn) throws InvalidTurnException {
        if (!turn.isEmpty()) {
            turnValidator.validateTurn(turn);
            board.cross(turn.getPositionsToCross());
            final Points oldPoints = points;
            points = ruleManager.calculatePoints();
            propertyChangeSupport.firePropertyChange("points", oldPoints, points);
        }
        round = round.next(gameStrategy);
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

    @Override
    public DiceResult getDiceResult() {
        return round.getDiceResult();
    }

    @Override
    public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }
}
