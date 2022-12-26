package thkoeln.dungeon.domainprimitives;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import thkoeln.dungeon.domainprimitives.DomainPrimitiveException;
import thkoeln.dungeon.domainprimitives.MineableResource;
import thkoeln.dungeon.domainprimitives.MineableResourceType;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MineableResourcesTest {
    private MineableResource gold12_1, gold12_2, platin12;

    @BeforeEach
    public void setUp() {
        gold12_1 = MineableResource.fromTypeAndAmount( MineableResourceType.GOLD, 12 );
        gold12_2 = MineableResource.fromTypeAndAmount( MineableResourceType.GOLD, 12 );
        platin12 = MineableResource.fromTypeAndAmount( MineableResourceType.PLATIN, 12 );
    }

    @Test
    public void testTwoMoneyEqualAndUnequal() {
        Assertions.assertEquals( gold12_1, gold12_2 );
        Assert.assertNotEquals( gold12_2, platin12 );
    }

    @Test
    public void testValidation() {
        Assert.assertThrows( DomainPrimitiveException.class, () -> {
            MineableResource.fromTypeAndAmount( null, 12 );
        });
        Assert.assertThrows( DomainPrimitiveException.class, () -> {
            MineableResource.fromTypeAndAmount( MineableResourceType.GOLD, 0 );
        });
        Assert.assertThrows( DomainPrimitiveException.class, () -> {
            MineableResource.fromTypeAndAmount( MineableResourceType.GOLD, -1 );
        });
    }

}