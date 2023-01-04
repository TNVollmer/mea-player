package thkoeln.dungeon.monte.eventlistener;

import java.util.Arrays;

public enum EventType {
    GAME_STATUS( "game-status" ),
    BANK_INITIALIZED( "BankAccountInitialized" ),
    ROUND_STATUS( "round-status" ),
    TRADABLE_PRICES( "TradablePrices" ),
    ROBOT_SPAWNED( "RobotSpawned" ),
    PLANET_DISCOVERED( "planet-discovered" ),
    UNKNOWN( "UNKNOWN" );

    private final String stringValue;

    private EventType( String s ) {
        stringValue = s;
    }

    public String getStringValue() {
        return stringValue;
    }

    public static EventType findByStringValue( final String stringValue ){
        return Arrays.stream(values()).filter(value -> value.getStringValue().equals( stringValue )).findFirst()
                .orElse( UNKNOWN );
    }

    public boolean isRobotRelated() {
        if ( this.equals( GAME_STATUS ) || this.equals( BANK_INITIALIZED ) || this.equals( UNKNOWN ) ||
             this.equals( ROUND_STATUS ) || this.equals( TRADABLE_PRICES ) ) return false;
        if ( isPlanetRelated() ) return false;
        return true;
    }

    public boolean isPlanetRelated() {
        if ( this.equals( PLANET_DISCOVERED ) ) return true;
        return false;
    }
}