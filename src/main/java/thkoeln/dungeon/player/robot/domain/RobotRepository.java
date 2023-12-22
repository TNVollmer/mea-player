package thkoeln.dungeon.player.robot.domain;

import org.springframework.data.repository.CrudRepository;

import thkoeln.dungeon.player.robot.domain.Robot;

import java.util.List;
import java.util.UUID;

public interface RobotRepository extends CrudRepository<Robot, UUID> {
    List<Robot> findAll();
    Robot findByRobotId(UUID robotId);
    List<Robot> findByRobotsPlanetPlanetId(UUID planetId);
    Robot findByRobotIdAndRobotPlanetPlanetId(UUID robotId, UUID planetId);
}
