package thkoeln.dungeon.player.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import thkoeln.dungeon.player.domain.CannotRegisterPlayerException;
import thkoeln.dungeon.player.domain.Player;
import thkoeln.dungeon.player.domain.PlayerRepository;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PlayerApplicationServiceWithoutConnectionTest {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    PlayerApplicationService playerApplicationService;
    @Value("${dungeon.playerName}")
    protected String playerName;
    @Value("${dungeon.playerEmail}")
    protected String playerEmail;

    @BeforeEach
    public void setUp() {
        playerRepository.deleteAll();
    }


    @Test
    public void testCreatePlayerWithNoConnection() {
        // given
        // when
        playerApplicationService.createPlayer();

        // then
        Player player = playerApplicationService.fetchPlayer().orElseThrow(
                () -> new RuntimeException( "No player!" ));
        assertEquals( playerEmail, player.getEmail(), "player email" );
        assertEquals( playerName, player.getName(), "player name" );
        assertFalse( player.isReadyToPlay(), "should be ready to play" );
    }
}