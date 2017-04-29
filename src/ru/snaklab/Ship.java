package ru.snaklab;

import java.util.Map;

/**
 * Created by Samat Khairutdinov on 05.04.17 18:55.
 * BattleShip
 */
public class Ship {
    private int xp;

    public ShipStatus getShipStatus() {
        return shipStatus;
    }

    private ShipStatus shipStatus = ShipStatus.ALIVE_SHIP;


    public boolean isAlive(){
        return xp > 0;
    }

    public void attack(){
        if(isAlive())
            xp--;
        if(isAlive()) {
            shipStatus = ShipStatus.WOUNDED_SHIP;
        }
        else {
            shipStatus = ShipStatus.DEATH_SHIP;
        }
    }

    public Ship (int xp) {
        this.xp = xp;
    }
    public Ship (int xp, ShipStatus shipStatus) {
        this.xp = xp;
        this.shipStatus = shipStatus;
    }

    @Override
    public Object clone(){
        return new Ship(this.xp, this.shipStatus);
    }
}
