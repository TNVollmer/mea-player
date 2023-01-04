package thkoeln.dungeon.monte.robot.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thkoeln.dungeon.monte.core.domainprimitives.command.Command;
import thkoeln.dungeon.monte.core.domainprimitives.purchasing.Capability;
import thkoeln.dungeon.monte.core.domainprimitives.status.Energy;
import thkoeln.dungeon.monte.core.strategy.AccountInformation;
import thkoeln.dungeon.monte.planet.domain.Planet;
import thkoeln.dungeon.monte.player.application.PlayerApplicationService;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor( access = AccessLevel.PROTECTED )
public class Robot implements RobotBehavior {
    @Transient
    private Logger logger = LoggerFactory.getLogger( Robot.class );

    @Id
    private final UUID id = UUID.randomUUID();

    // The ID assigned by the Robot service!
    private UUID robotId;

    // Game- and playerId is stored for convenience - you need this for creating commands.
    private UUID gameId;
    private UUID playerId;

    private Energy energy;

    @Enumerated( EnumType.STRING )
    private RobotType type;

    boolean alive = true;

    @Transient
    AbstractRobotStrategy strategy;

    @ElementCollection( fetch = FetchType.EAGER )
    @Getter ( AccessLevel.PROTECTED )
    private final List<Capability> capabilities = Capability.allBaseCapabilities();

    @ManyToOne
    private Planet planet;

    public static Robot of( UUID robotId, RobotType type, UUID gameId, UUID playerId ) {
        if ( robotId == null ) throw new RobotException( "robotId == null" );
        Robot robot = new Robot();
        robot.setRobotId( robotId );
        robot.type = type;
        robot.setGameId( gameId );
        robot.setPlayerId( playerId );
        robot.setEnergy( Energy.initialRobotEnergy() );
        return robot;
    }

    public static Robot of( UUID robotId ) {
        return of( robotId, null, null, null );
    }


    @Override
    public Command regenerateIfLowAndNotAttacked() {
        return null;
    }


    @Override
    public Command fleeIfAttacked() {
        return null;
    }


    @Override
    public Command mineIfNotMinedLastRound() {
        return null;
    }


    @Override
    public Command mine() {
        return null;
    }


    @Override
    public Command move() {
        if ( planet == null ) {
            logger.error( "Robot wants to move, but planet is null ???" );
            return null;
        }
        if ( energy.greaterEqualThan( planet.getMovementDifficulty() ) ) {
            Command command = Command.move( gameId, playerId, robotId, planet.findUnvisitedNeighbour().getPlanetId() );
            return command;
        }
        // not sufficient energy to move => no command
        return null;
    }


    @Override
    public Command upgrade(AccountInformation accountInformation) {
        return null;
    }


    @Override
    public Command attack() {
        return null;
    }


    public String toStringDetailed() {
        String printString = toString();
        if ( planet != null ) printString += " on " + planet;
        return printString;
    }


    @Override
    public String toString() {
        String printString = ( type != null ) ? type.toString() : "Robot";
        printString = printString.substring( 0, 1 );
        printString += String.valueOf( robotId ).substring( 0, 3 );
        return printString;
    }
}