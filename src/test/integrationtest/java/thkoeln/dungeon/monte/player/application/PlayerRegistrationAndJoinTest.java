package thkoeln.dungeon.monte.player.application;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import thkoeln.dungeon.monte.DungeonPlayerConfiguration;
import thkoeln.dungeon.monte.core.AbstractDungeonMockingTest;
import thkoeln.dungeon.monte.game.application.GameApplicationService;
import thkoeln.dungeon.monte.game.domain.Game;
import thkoeln.dungeon.monte.game.domain.GameRepository;
import thkoeln.dungeon.monte.player.domain.PlayerRepository;
import thkoeln.dungeon.monte.player.domain.Player;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest( classes = DungeonPlayerConfiguration.class )
public class PlayerRegistrationAndJoinTest extends AbstractDungeonMockingTest {


    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameApplicationService gameApplicationService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerApplicationService playerApplicationService;
    private Game game;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        playerRepository.deleteAll();
        gameRepository.deleteAll();
    }


    @Test
    public void test_queryAndIfNeededCreatePlayer() throws Exception {
        // given
        // when
        Player player = playerApplicationService.queryAndIfNeededCreatePlayer();

        // then
        assertNotNull( player.getEmail(), "player email" );
        assertNotNull( player.getName(), "player name"  );
        assertFalse( player.isRegistered(), "Player must not be registered" );
    }



    @Test
    public void testRegisterPlayer_butDontJoinYet_noPlayerYet() throws Exception {
        // given
        mockGamesGetWithRunning();
        mockPlayerGetNotFound();
        mockPlayerPost();

        // when
        gameApplicationService.fetchRemoteGame();
        playerApplicationService.queryAndIfNeededCreatePlayer();
        playerApplicationService.registerPlayer();

        // then
        Player player = playerApplicationService.queryAndIfNeededCreatePlayer();
        assertEquals( playerEmail, player.getEmail(), "player email" );
        assertEquals( playerName, player.getName(), "player name" );
        assertTrue( player.isRegistered(), "Player must be registered" );
        assertTrue( player.hasJoinedGame(), "Player must have joined game" );
    }



    @Test
    public void testRegisterPlayer_butDontJoinYet_thereIsAPlayer() throws Exception {
        // given
        mockGamesGetWithRunning();
        mockPlayerGetFound();

        // when
        playerApplicationService.queryAndIfNeededCreatePlayer();
        gameApplicationService.fetchRemoteGame();
        playerApplicationService.registerPlayer();

        // then
        Player player = playerApplicationService.queryAndIfNeededCreatePlayer();
        assertEquals( playerEmail, player.getEmail(), "player email" );
        assertEquals( playerName, player.getName(), "player name" );
        assertTrue( player.isRegistered(), "should be ready to play" );
    }


    @Test
    public void testJoinGame() throws Exception {
        // given
        mockGamesGetWithCreated();
        mockPlayerGetFound();
        mockRegistrationEndpointFor( openGameId, playerId );

        // when
        gameApplicationService.fetchRemoteGame();
        playerApplicationService.queryAndIfNeededCreatePlayer();
        playerApplicationService.registerPlayer();
        playerApplicationService.letPlayerJoinOpenGame();

        // then
        Player player = playerApplicationService.queryAndIfNeededCreatePlayer();
        assertNotNull( player.getEmail(), "player email" );
        assertNotNull( player.getName(), "player name"  );
        assertTrue( player.isRegistered(), "should be ready to play" );
        assertTrue( player.hasJoinedGame(), "should have joined the game" );
    }




}