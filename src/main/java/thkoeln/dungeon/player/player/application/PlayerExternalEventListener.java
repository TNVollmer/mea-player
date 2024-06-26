package thkoeln.dungeon.player.player.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import thkoeln.dungeon.player.core.events.AbstractEvent;
import thkoeln.dungeon.player.core.events.EventFactory;
import thkoeln.dungeon.player.core.events.EventHeader;

@Service
public class PlayerExternalEventListener {
    private final Logger logger = LoggerFactory.getLogger(PlayerExternalEventListener.class);
    private EventFactory eventFactory;
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public PlayerExternalEventListener(EventFactory eventFactory,
                                       ApplicationEventPublisher applicationEventPublisher
    ) {
        this.eventFactory = eventFactory;
        this.applicationEventPublisher = applicationEventPublisher;
    }


    /**
     * Listener to all events that the core services send to the player
     *
     * @param eventIdStr
     * @param transactionIdStr
     * @param playerIdStr
     * @param type
     * @param version
     * @param timestampStr
     * @param payload
     */
    @RabbitListener(queues = "player-${dungeon.playerName}")
    public void receiveEvent(@Header(required = false, value = EventHeader.EVENT_ID_KEY) String eventIdStr,
                             @Header(required = false, value = EventHeader.TRANSACTION_ID_KEY) String transactionIdStr,
                             @Header(required = false, value = EventHeader.PLAYER_ID_KEY) String playerIdStr,
                             @Header(required = false, value = EventHeader.TYPE_KEY) String type,
                             @Header(required = false, value = EventHeader.VERSION_KEY) String version,
                             @Header(required = false, value = EventHeader.TIMESTAMP_KEY) String timestampStr,
                             String payload) {
        try {
            EventHeader eventHeader =
                    new EventHeader(type, eventIdStr, playerIdStr, transactionIdStr, timestampStr, version);
            AbstractEvent newEvent = eventFactory.fromHeaderAndPayload(eventHeader, payload);
            logger.info("======== EVENT =====> " + newEvent.toStringShort());
            logger.debug("======== EVENT (detailed) =====>\n" + newEvent);
            if (!newEvent.isValid()) {
                logger.warn("Event invalid: " + newEvent);
                return;
            } else {
                this.applicationEventPublisher.publishEvent(newEvent);
            }
        } catch (Exception e) {
            logger.error("!!!!!!!!!!!!!! EVENT ERROR !!!!!!!!!!!!!\n" + e);
        }
    }
}
