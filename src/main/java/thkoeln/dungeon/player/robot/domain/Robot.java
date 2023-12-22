package thkoeln.dungeon.player.robot.domain;


import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import thkoeln.dungeon.player.core.events.concreteevents.robot.spawn.RobotInventoryDto;

import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Robot {
    @Transient
    private Logger logger = LoggerFactory.getLogger(Robot.class);

    @Id
    private final UUID id = UUID.randomUUID();

    private UUID robotId;
    private String name = "Robot";

    private boolean isAlive = true;
    private int maxHealth;
    private int health;

    private int maxEnergy;
    private int energy;
    private int energyRegen;

    private int healthLevel;
    private int energyLevel;
    private int energyRegenLevel;
    private int storageLevel;

    private int attackDamage;
    private int miningSpeed;


    @Embedded
    @Setter
    private RobotPlanet robotPlanet = RobotPlanet.nullPlanet();

    public Robot(UUID robotId, String name, UUID planetId) {
        if (robotId == null || planetId == null) {
            logger.error("Robot or planet id is null");
            throw new IllegalArgumentException("Robot or planet id is null");
        }
        this.robotId = robotId;
        this.robotPlanet = RobotPlanet.planetWithoutNeighbours(planetId);
    }

    public static Robot of(UUID robotId, String name, UUID planetId) {
        return new Robot(robotId, name, planetId);
    }
}
