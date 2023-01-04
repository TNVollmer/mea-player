package thkoeln.dungeon.monte.planet.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.dungeon.monte.eventlistener.AbstractEvent;
import thkoeln.dungeon.monte.eventlistener.concreteevents.planet.PlanetDiscoveredEvent;

@Service
public class PlanetEventHandler {
    private Logger logger = LoggerFactory.getLogger(PlanetEventHandler.class);
    private PlanetApplicationService planetApplicationService;

    @Autowired
    public PlanetEventHandler( PlanetApplicationService planetApplicationService ) {
        this.planetApplicationService = planetApplicationService;
    }

    /**
     * Dispatch to the appropriate application service method
     * @param event
     */
    public void handlePlanetRelatedEvent( AbstractEvent event ) {
        switch ( event.getEventHeader().getEventType() ) {
            case PLANET_DISCOVERED:
                handlePlanetDiscoveredEvent( (PlanetDiscoveredEvent) event );
                break;
            default:
        }
    }

    public void handlePlanetDiscoveredEvent( PlanetDiscoveredEvent event ) {
        planetApplicationService.addPlanetNeighbours( event );
    }
}