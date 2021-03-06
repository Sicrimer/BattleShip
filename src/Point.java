public class Point {


    private Ship ship = null;

    public PointStatus getPointStatus() {
        return pointStatus;
    }

    private PointStatus pointStatus = PointStatus.EMPTY;

    private boolean open = false;

    public void setShip(Ship ship){
        this.ship = ship;
        pointStatus = PointStatus.HAS_ALIVE_SHIP;
    }

    private Point(Ship ship){
        this.ship = ship;
    }

    public Point(){

    }

    public Point(PointStatus pointStatus){
        this.pointStatus = pointStatus;
    }

    public boolean getOpen(){
        return open;
    }

    boolean hasShip(){
        return ship != null;
    }

    public PointStatus getStatus(){
        return pointStatus;
    }

    public boolean attackPoint(){
        if (open){
            return false;
        }
        if (ship == null){
            open = true;
            return true;
        }
        open = true;
        ship.attackShip();
        if (ship.getShipStatus() == ShipStatus.DEAD){
            pointStatus = PointStatus.HAS_DEATH_SHIP;
        }
        else if (ship.getShipStatus() == ShipStatus.ALIVE){
            pointStatus = PointStatus.HAS_ALIVE_SHIP;
        }
        return true;
    }

    public Point clone(){
        return new Point(ship);
    }

    public void openPoint(){
        open = true;
    }
}
