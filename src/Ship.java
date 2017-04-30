public class Ship {

    private int hp;

    private ShipStatus shipStatus;

    public Ship(int hp){
        this.hp = hp;
        shipStatus = ShipStatus.ALIVE;
    }

    public ShipStatus getShipStatus(){
        return shipStatus;
    }

    public void attackShip(){
        shipStatus = ShipStatus.WOUNDED;
        hp--;
        if(hp == 0){
            shipStatus = ShipStatus.DEAD;
        }
        else if (hp < 0){
            throw new RuntimeException("Can't attack killed ship.");
        }
    }
}
