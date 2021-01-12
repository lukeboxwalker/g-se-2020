package de.techfak.se.multiplayer.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.techfak.se.multiplayer.game.exceptions.GameAlreadyFinishedException;
import de.techfak.se.multiplayer.game.exceptions.GameAlreadyRunningException;
import de.techfak.se.multiplayer.game.exceptions.InvalidStatusChangeException;
import de.techfak.se.multiplayer.game.exceptions.InvalidStatusException;
import de.techfak.se.multiplayer.game.exceptions.MissingNameException;
import de.techfak.se.testing.AfterThreaded;
import de.techfak.se.testing.AsyncTester;
import de.techfak.se.testing.BeforeThreaded;
import de.techfak.se.testing.Threaded;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;


public class ConcurrentTest {

    static final Supplier<BaseGame> gameFactory = () -> new SynchronizedGame(new Board(""));

    static class SetGameStatusTester {
        BaseGame baseGame = gameFactory.get();

        @Threaded
        public void setStatusToNotStarted() {
            try {
                baseGame.setGameStatus(GameStatus.NOT_STARTED);
            } catch (InvalidStatusException | InvalidStatusChangeException expected) {
            }
        }

        @Threaded
        public void setStatusToRunning() {
            try {
                baseGame.setGameStatus(GameStatus.RUNNING);
            } catch (InvalidStatusException | InvalidStatusChangeException expected) {
            }
        }

        @AfterThreaded
        public void after() {
            Assertions.assertThat(baseGame.getGameStatus()).isEqualTo(GameStatus.RUNNING);
        }

    }

    static class AddAndGetPlayersTester {
        BaseGame baseGame = gameFactory.get();

        @BeforeThreaded
        public void init() {
            try {
                baseGame.addPlayer(new PlayerName("1"));
                baseGame.addPlayer(new PlayerName("2"));
                baseGame.addPlayer(new PlayerName("3"));
                baseGame.addPlayer(new PlayerName("4"));
            } catch (MissingNameException | GameAlreadyRunningException | GameAlreadyFinishedException expected) {
            }
        }

        @Threaded
        public void serializePlayers() throws JsonProcessingException {
            new ObjectMapper().writeValueAsString(baseGame.getPlayers());
        }

        @Threaded
        public void addPlayer() {
            try {
                baseGame.addPlayer(new PlayerName("Player"));
            } catch (MissingNameException | GameAlreadyRunningException | GameAlreadyFinishedException expected) {
            }
        }

        @AfterThreaded
        void after() {
            Assertions.assertThat(baseGame.getPlayers()).hasSize(5);
        }

    }

    static class AddTwoPlayersWithSameName {
        BaseGame baseGame = gameFactory.get();

        @Threaded(parallelity = 2)
        public void addPlayer() {
            try {
                baseGame.addPlayer(new PlayerName("Test"));
            } catch (MissingNameException | GameAlreadyRunningException | GameAlreadyFinishedException expected) {
            }
        }

        @AfterThreaded
        public void after() {
            Assertions.assertThat(baseGame.getPlayers()).hasSize(1);
        }

    }

    static class AddMultiplePlayers {
        BaseGame baseGame = gameFactory.get();

        @Threaded
        public void addPlayer1() {
            try {
                for (int i = 0; i < 100; i++) {
                    baseGame.addPlayer(new PlayerName("Player1-" + i));
                }
            } catch (MissingNameException | GameAlreadyRunningException | GameAlreadyFinishedException expected) {
            }
        }

        @Threaded
        public void addPlayer2() {
            try {
                for (int i = 0; i < 100; i++) {
                    baseGame.addPlayer(new PlayerName("Player2-" + i));
                }
            } catch (MissingNameException | GameAlreadyRunningException | GameAlreadyFinishedException expected) {
            }
        }

        @Threaded
        public void addPlayer3() {
            try {
                for (int i = 0; i < 100; i++) {
                    baseGame.addPlayer(new PlayerName("Player3-" + i));
                }
            } catch (MissingNameException | GameAlreadyRunningException | GameAlreadyFinishedException expected) {
            }
        }

        @AfterThreaded
        public void after() {
            Assertions.assertThat(baseGame.getPlayers()).hasSize(300);
        }
    }


    @Test
    void testSetGameStatus() throws Exception {
        AsyncTester.test(SetGameStatusTester::new);
    }

    @Test
    void testAddAndGetPlayers() throws Exception {
        AsyncTester.test(AddAndGetPlayersTester::new);
    }

    @Test
    void testAddTwoPlayersWithSameName() throws Exception {
        AsyncTester.test(AddTwoPlayersWithSameName::new);
    }

    @Test
    void testAddTwoPlayers() throws Exception {
        AsyncTester.test(AddMultiplePlayers::new);
    }

}
