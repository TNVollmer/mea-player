package thkoeln.dungeon.__obsololete__eventconsumer.core;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import thkoeln.dungeon.core.AbstractDungeonMockingTest;
import thkoeln.dungeon.__obsololete__eventconsumer.game.GameEventConsumerService;
import thkoeln.dungeon.__obsololete__eventconsumer.map.MapEventConsumerService;
import thkoeln.dungeon.__obsololete__eventconsumer.robot.RobotEventConsumerService;
import thkoeln.dungeon.game.application.GameApplicationService;
import thkoeln.dungeon.game.domain.GameRepository;
import thkoeln.dungeon.game.domain.GameStatus;
import thkoeln.dungeon.player.application.PlayerApplicationService;
import thkoeln.dungeon.player.domain.Player;
import thkoeln.dungeon.player.domain.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AbstractEventTest extends AbstractDungeonMockingTest {
    @Autowired
    protected PlayerApplicationService playerApplicationService;
    @Autowired
    protected GameApplicationService gameApplicationService;
    @Autowired
    protected GameRepository gameRepository;
    @Autowired
    protected PlayerRepository playerRepository;
    @Autowired
    protected GameEventConsumerService gameEventConsumerService;
    @Autowired
    protected MapEventConsumerService mapEventConsumerService;
    @Autowired
    protected RobotEventConsumerService robotEventConsumerService;

    protected Player player;
    protected final UUID gameId = UUID.randomUUID();
    protected final UUID playerId = UUID.randomUUID();
    protected String eventPayloadString;
    protected final UUID spaceStation1Id = UUID.randomUUID();
    protected final UUID spaceStation2Id = UUID.randomUUID();
    protected final List<UUID> spaceStationIds = new ArrayList<>();

    @Setter
    @Getter
    @AllArgsConstructor
    protected class GameStatusEventPayloadDto {
        private UUID gameId;
        private GameStatus status;
    }
    protected GameStatusEventPayloadDto gameStatusEventPayloadDto;

    @Setter
    @Getter
    @AllArgsConstructor
    protected class PlayerStatusEventPayloadDto {
        private UUID playerId;
    }
    protected PlayerStatusEventPayloadDto playerStatusEventPayloadDto;

    /*
    @Before
    protected void setUp() throws Exception {
        super.setUp();
        playerRepository.deleteAll();
        gameRepository.deleteAll();
        player = playerApplicationService.queryAndIfNeededCreatePlayer();
        resetMockServer();
        mockPlayerGetNotFound();
        mockPlayerPost();
        playerApplicationService.registerPlayer();
        player = playerApplicationService.queryAndIfNeededCreatePlayer();
        assertNotNull( player.getPlayerId() );
        spaceStationIds.add( spaceStation1Id );
        spaceStationIds.add( spaceStation2Id );
    }

    protected void setUpGame() throws Exception {
        resetMockServer();
        gameStatusEventPayloadDto = new GameStatusEventPayloadDto( gameId, CREATED );
        mockRegistrationEndpointFor( gameId, player.getPlayerId() );
        eventPayloadString = objectMapper.writeValueAsString( gameStatusEventPayloadDto );
        gameEventConsumerService.consumeGameStatusEvent(
                genericEventIdStr, EventPayloadTestFactory.timestamp(), genericTransactionIdStr, eventPayloadString );
        assertEquals( 1, playerRepository.findByRegistrationTransactionId( genericTransactionId ).size() );
    }


    protected void setUpPlayer() throws Exception {
        playerStatusEventPayloadDto = new PlayerStatusEventPayloadDto( playerId );
        eventPayloadString = objectMapper.writeValueAsString( playerStatusEventPayloadDto );
        gameEventConsumerService.consumePlayerStatusEvent(
                genericEventIdStr, EventPayloadTestFactory.timestamp(), genericTransactionIdStr, eventPayloadString );


    }

    protected void startGame( List<UUID> spaceStationIds ) throws Exception {
        gameStatusEventPayloadDto = new GameStatusEventPayloadDto( gameId, RUNNING );
        eventPayloadString = objectMapper.writeValueAsString( gameStatusEventPayloadDto );
        gameEventConsumerService.consumeGameStatusEvent(
                genericEventIdStr, EventPayloadTestFactory.timestamp(), genericTransactionIdStr, eventPayloadString );
        mapEventConsumerService.consumeGameWorldCreatedEvent(
                genericEventIdStr, EventPayloadTestFactory.timestamp(), genericTransactionIdStr,
                EventPayloadTestFactory.gameworldCreatedPayload( spaceStationIds ) );
        Optional<Game> gameOpt = gameApplicationService.retrieveActiveGame();
        assertTrue( gameOpt.isPresent() );
        assertEquals( gameId, gameOpt.get().getGameId() );
    }
*/
}