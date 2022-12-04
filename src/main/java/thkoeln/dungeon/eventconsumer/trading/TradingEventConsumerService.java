package thkoeln.dungeon.eventconsumer.trading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.dungeon.game.application.GameApplicationService;
import thkoeln.dungeon.player.application.PlayerApplicationService;

@Service
public class TradingEventConsumerService {
    private Logger logger = LoggerFactory.getLogger( TradingEventConsumerService.class );
    private GameApplicationService gameApplicationService;
    private PlayerApplicationService playerApplicationService;
    private BankCreatedEventRepository bankCreatedEventRepository;


    @Autowired
    public TradingEventConsumerService(GameApplicationService gameApplicationService,
                                       BankCreatedEventRepository bankCreatedEventRepository,
                                       PlayerApplicationService playerApplicationService ) {
        this.gameApplicationService = gameApplicationService;
        this.bankCreatedEventRepository = bankCreatedEventRepository;
        this.playerApplicationService = playerApplicationService;
    }


    /**
     * "Status changed" event published by GameService, esp. after a game has been created, started, or finished
     */
/*
    @KafkaListener( topics = "bank-created" )
    public void consumeGameStatusEvent( @Header String eventId, @Header String timestamp, @Header String transactionId,
                                        @Payload String payload ) {
        BankCreatedEvent bankCreatedEvent = new BankCreatedEvent()
                .fillWithPayload( payload )
                .fillHeader( eventId, timestamp, transactionId );
        bankCreatedEventRepository.save( bankCreatedEvent );
        if ( bankCreatedEvent.isValid() ) {
            playerApplicationService.adjustBankAccount(bankCreatedEvent.getPlayerId(), bankCreatedEvent.getMoney());
        }
        else {
            logger.warn( "Caught invalid __OBSOLETE_GameStatusEvent " + bankCreatedEvent );
        }
    }
*/
}
