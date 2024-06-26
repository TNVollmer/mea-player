package thkoeln.dungeon.player;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import thkoeln.dungeon.player.player.application.PlayerApplicationService;

@Component
@Profile( "!test & !dev" )
@RequiredArgsConstructor
@Slf4j
public class DungeonPlayerInitializer implements InitializingBean {
    private final PlayerApplicationService playerApplicationService;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Initializer: Register player.");
        playerApplicationService.registerPlayer();

        log.info("Initializer: Check if there is an open game.");
        // If there is no open game in state "created", the player will learn about a new game by
        // listening to the GameStatus event (with status CREATED). This active joining is only
        // necessary if the game has already been created, and is waiting for players to join.
        playerApplicationService.letPlayerJoinOpenGame();
    }
}
