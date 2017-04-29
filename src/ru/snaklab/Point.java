package ru.snaklab;

/**
 * Created by Samat Khairutdinov on 05.04.17 19:52.
 * BattleShip
 */
public class Point {

    private Ship ship = null;

    private boolean open = false;

    private PointStatus pointStatusWithoutShip = PointStatus.EMPTY;

    public static Point defaultPoint (){
        return  new Point();
    }

    public static Point getUndeterminedPoint(){
        return new Point(null, false, PointStatus.UNDETERMINED);
    }

    public Point(){
    }

    private Point(Ship ship, boolean open, PointStatus pointStatusWithoutShip){
        this.ship = ship;
        this.open = open;
        this.pointStatusWithoutShip = pointStatusWithoutShip;
    }

    public PointStatus getPointStatus() {
        if(hasShip()){
            switch (ship.getShipStatus()){
                case ALIVE_SHIP:
                    return PointStatus.HAS_ALIVE_SHIP;
                case WOUNDED_SHIP:
                    return PointStatus.HAS_WOUNDED_SHIP;
                case DEATH_SHIP:
                    return PointStatus.HAS_DEATH_SHIP;
                default:
                    return null;
            }
        }
        else {
            return pointStatusWithoutShip;
        }
    }

    public boolean hasShip(){
        return ship != null;
    }

    public boolean isOpen(){
        return open;
    }

    public boolean setShip(Ship ship){
        if(this.ship == null){
            pointStatusWithoutShip = PointStatus.HAS_ALIVE_SHIP;
            this.ship = ship;
            return true;
        }
        return false;
    }

    public boolean attack(){
        if(!isOpen() && hasShip() && ship.isAlive()){
            ship.attack();
            open = true;
            return true;
        }

        if(isOpen()){
            return false;
        }
        else{
            open = true;
        }
        return true;
    }

    @Override
    public Object clone(){
        if(this.ship == null)
            return new Point(this.ship, this.open, this.pointStatusWithoutShip);
        else
            return new Point((Ship) this.ship.clone(), this.open, this.pointStatusWithoutShip);
    }

}
