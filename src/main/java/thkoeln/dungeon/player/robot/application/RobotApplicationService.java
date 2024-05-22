package thkoeln.dungeon.player.robot.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import thkoeln.dungeon.player.core.domainprimitives.location.MineableResource;
import thkoeln.dungeon.player.core.domainprimitives.location.MineableResourceType;
import thkoeln.dungeon.player.core.events.concreteevents.robot.change.RobotRegeneratedEvent;
import thkoeln.dungeon.player.core.events.concreteevents.robot.fight.RobotAttackedEvent;
import thkoeln.dungeon.player.core.events.concreteevents.robot.mine.RobotResourceMinedEvent;
import thkoeln.dungeon.player.core.events.concreteevents.robot.mine.RobotResourceRemovedEvent;
import thkoeln.dungeon.player.core.events.concreteevents.robot.move.RobotMovedEvent;
import thkoeln.dungeon.player.core.events.concreteevents.robot.spawn.RobotSpawnedEvent;
import thkoeln.dungeon.player.planet.domain.Planet;
import thkoeln.dungeon.player.planet.domain.PlanetRepository;
import thkoeln.dungeon.player.player.domain.Player;
import thkoeln.dungeon.player.player.domain.PlayerRepository;
import thkoeln.dungeon.player.robot.domain.Robot;
import thkoeln.dungeon.player.robot.domain.RobotRepository;

import java.util.UUID;

@Service
@Slf4j
public class RobotApplicationService {
    private final RobotRepository robotRepository;
    private final PlanetRepository planetRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public RobotApplicationService(RobotRepository robotRepository, PlanetRepository planetRepository, PlayerRepository playerRepository) {
        this.robotRepository = robotRepository;
        this.planetRepository = planetRepository;
        this.playerRepository = playerRepository;
    }

    @EventListener(RobotSpawnedEvent.class)
    public void onRobotSpawned(RobotSpawnedEvent event) {
        Player player = playerRepository.findAll().get(0);
        Planet planet = getPlanet(event.getRobotDto().getPlanet().getPlanetId());
        if (planet.getResources() == null && event.getRobotDto().getPlanet().getResourceType() != null)
            planet.setResources(MineableResource.empty(MineableResourceType.valueOf(event.getRobotDto().getPlanet().getResourceType())));
        Robot robot = new Robot(event.getRobotDto().getId(), player, planet);
        robot.setEnergy(event.getRobotDto().getEnergy());
        robot.setHealth(event.getRobotDto().getHealth());
        robot.changeInventorySize(event.getRobotDto().getInventory().getMaxStorage());

        //TODO: assign robot type
        if (planet.getResources() != null) {
            robot.mine();
        }

        planetRepository.save(robot.getPlanet());
        robotRepository.save(robot);
        log.info("Robot {} spawned!", robot.getId());
    }

    @EventListener(RobotMovedEvent.class)
    public void onRobotMoved(RobotMovedEvent event) {
        Robot robot = getRobot(event.getRobotId());
        robot.move(getPlanet(event.getToPlanet().getId()));
        robot.setEnergy(event.getRemainingEnergy());
        robotRepository.save(robot);
    }

    @EventListener(RobotRegeneratedEvent.class)
    public void onRobotRegenerated(RobotRegeneratedEvent event) {
        Robot robot = getRobot(event.getRobotId());
        robot.setEnergy(event.getAvailableEnergy());
        robotRepository.save(robot);
    }

    @EventListener(RobotResourceMinedEvent.class)
    public void onRobotResourceMined(RobotResourceMinedEvent event) {
        Robot robot = getRobot(event.getRobotId());

        MineableResource minedResource = MineableResource.fromTypeAndAmount(MineableResourceType.valueOf(event.getMinedResource()), event.getMinedAmount());
        robot.storeResources(minedResource);
        robot.mine();
        log.info("Robot {} mined: {} {}", robot.getId(), event.getMinedAmount(), event.getMinedResource());
        log.info("Inventory: {}", robot.getInventory().getUsedCapacity());

        robotRepository.save(robot);
    }

    @EventListener(RobotResourceRemovedEvent.class)
    public void onResourceRemoved(RobotResourceRemovedEvent event) {
        Robot robot = getRobot(event.getRobotId());
        robot.removeResources(MineableResource.fromTypeAndAmount(MineableResourceType.valueOf(event.getRemovedResource()), event.getRemovedAmount()));
        robotRepository.save(robot);
    }

    @EventListener(RobotAttackedEvent.class)
    public void onRobotAttacked(RobotAttackedEvent event) {
        Robot attacker = getRobot(event.getAttacker().getRobotId());
        Robot target = getRobot(event.getTarget().getRobotId());
        target.setHealth(event.getTarget().getAvailableHealth());

        //TODO: get player and check if target is once own robot
        //if (target.getPlayer() == )
        target.escape();
    }

    private Robot getRobot(UUID robotId) {
        return robotRepository.findByRobotId(robotId).orElseThrow(() -> new RuntimeException("No robot found with id: " + robotId));
    }

    private Planet getPlanet(UUID planetId) {
        return planetRepository.findByPlanetId(planetId).orElse(new Planet(planetId));
    }
}