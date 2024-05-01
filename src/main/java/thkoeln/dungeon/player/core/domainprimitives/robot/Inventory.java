package thkoeln.dungeon.player.core.domainprimitives.robot;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import thkoeln.dungeon.player.core.domainprimitives.DomainPrimitiveException;
import thkoeln.dungeon.player.core.domainprimitives.location.MineableResource;

import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
@EqualsAndHashCode
public class Inventory {

    private Integer capacity;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<MineableResource> resources;

    protected Inventory() {}

    protected Inventory(Integer capacity, List<MineableResource> resources) {
        this.capacity = capacity;
        this.resources = resources;
    }

    public static Inventory fromCapacity(Integer capacity) {
        if (capacity == null || capacity < 0) throw new DomainPrimitiveException("Invalid Parameter for Inventory!");
        return new Inventory(capacity, new ArrayList<>());
    }

    public static Inventory fromCapacityAndResources(Integer capacity, List<MineableResource> resources) {
        if (capacity == null || capacity < 0 || resources == null) throw new DomainPrimitiveException("Invalid Parameter for Inventory!");
        return new Inventory(capacity, resources);
    }

    public boolean isFull() {
        Integer amount = 0;
        for (MineableResource resource : resources) {
            amount += resource.getAmount();
        }
        return this.capacity.equals(amount);
    }

    public Inventory addMineableResource(MineableResource addedResource) {
        List<MineableResource> mineableResourceList = new ArrayList<>();
        for (MineableResource resource : resources) {
            if (resource.getType() != addedResource.getType()) {
                mineableResourceList.add(resource);
            } else {
                mineableResourceList.add(resource.add(addedResource));
            }
        }
        return Inventory.fromCapacityAndResources(capacity, mineableResourceList);
    }

}
