package thkoeln.dungeon.player.game.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static java.lang.Boolean.FALSE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Game {
    @Id
    private final UUID id = UUID.randomUUID();

    // this is the EXTERNAL id that we receive from GameService. We could use this also as our own id, but then
    // we'll run into problems in case GameService messes up their ids (e.g. start the same game twice, etc.) So,
    // we better keep these two apart.
    private UUID gameId;
    private GameStatus gameStatus;
    private Integer currentRoundNumber;

    @Setter(AccessLevel.PROTECTED)
    private Boolean ourPlayerHasJoined;

    @Transient
    private Logger logger = LoggerFactory.getLogger( Game.class );

    public void resetToNewlyCreated() {
        setGameStatus( GameStatus.CREATED );
        setCurrentRoundNumber( 0 );
        setOurPlayerHasJoined( FALSE );
        logger.warn( "Reset game " + this + " to CREATED!" );
    }

    public static Game newlyCreatedGame( UUID gameId ) {
        Game game = new Game();
        game.setGameId( gameId );
        game.resetToNewlyCreated();
        return game;
    }

    /**
     * Can be called with the String[] of joined player names
     *
     * @param namesOfJoinedPlayers
     */
    public void checkIfOurPlayerHasJoined( String[] namesOfJoinedPlayers, String playerName ) {
        if ( namesOfJoinedPlayers == null || playerName == null )
            throw new GameException( "namesOfJoinedPlayers == null || playerName == null" );
        Boolean found = Arrays.stream( namesOfJoinedPlayers ).anyMatch( s -> s.equals( playerName ) );
        setOurPlayerHasJoined( found );
    }


    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Game game = (Game) o;
        return id.equals( game.id );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }

    @Override
    public String toString() {
        return "Game round no. " + currentRoundNumber + " (" + getGameStatus() + ")";
    }
}
