package thkoeln.dungeon.monte.eventlistener.concreteevents.planet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import thkoeln.dungeon.monte.domainprimitives.CompassDirection;

import java.util.UUID;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanetNeighboursDto {
    private UUID id;
    private CompassDirection direction;

    public boolean isValid() {
        if ( id == null ) return false;
        if ( direction == null ) return false;
        return true;
    }
}
