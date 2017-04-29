package ru.snaklab;

/**
 * Created by Samat Khairutdinov on 05.04.17 18:54.
 * BattleShip
 */
public class Table implements Cloneable {

    public static int D = 10; //dimension

    private Point[][] points = new Point[D][D];

    public Table() {
        for(int i = 0; i < D; i++){
            for(int j = 0; j < D; j++){
                points[i][j] = Point.defaultPoint();
            }
        }
    }

    public Table(Point[][] points) {
        this.points = points;
    }

    public static boolean correctCord(int x, int y){
        return x >= 0 && x <= D && y >= 0 && y<= D;
    }

    public Point[][] getPoints() {
        return points;
    }

    private int getLeftBorder(int y){
        if(y == 0)
            return y;
        else
            return y-1;
    }
    private int getRightBorder(int y){
        if(y == Table.D-1)
            return y;
        else
            return y+1;
    }
    private int getUpBorder(int x){
        if(x == 0)
            return x;
        else
            return x-1;
    }
    private int getDownBorder(int x){
        if(x == Table.D-1)
            return x;
        else
            return x+1;
    }


    private boolean checkPoint(int x, int y){
        if(x >= 0 && x <= Table.D - 1 && y >= 0 && y <= Table.D - 1) {
            for (int i = getUpBorder(x); i <= getDownBorder(x); i++) {
                for (int j = getLeftBorder(y); j <= getRightBorder(y); j++) {
                    if (points[i][j].hasShip())
                        return false;
                }
            }
        }
        return true;
    }

    public boolean checkAliveShips(){
        for(int i = 0; i < Table.D; i++){
            for (int j = 0; j < Table.D; j++){
                if(points[i][j].getPointStatus() == PointStatus.HAS_ALIVE_SHIP
                        || points[i][j].getPointStatus() == PointStatus.HAS_WOUNDED_SHIP){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setShip(int xp, int x, int y, ShipOrientation shipOrientation){
        switch (shipOrientation){
            case HORIZONTAL:{
                for(int i = 0; i < xp; i++){
                    if(!checkPoint(x,y+i))
                        return false;
                }
                Ship ship = new Ship(xp);
                for(int i = 0; i < xp; i++){
                    if(!points[x][y+i].setShip(ship))
                        return false;
                }
                break;
            }
            case VERTICAL:{
                for(int i = 0; i < xp; i++){
                    if(!checkPoint(x+i,y))
                        return false;
                }
                Ship ship = new Ship(xp);
                for(int i = 0; i < xp; i++){
                    if(!points[x+i][y].setShip(ship))
                        return false;
                }
                break;
            }
        }
        return true;
    }

    public boolean attack(int x, int y){
        if(correctCord(x,y)){
            return points[x][y].attack();
        }
        return false;
    }

    @Override
    public Object clone(){
        return new Table(this.points.clone());
    }

    public static void main(String[] args) {
        Table table = new Table();
        Ship ship = new Ship(1);
        Point point = new Point();
        point.setShip(ship);
        table.points[2][2] = point;
        if(table.checkPoint(3,3)){
            System.out.println("ok");
        }
        else {
            System.out.println("not good");
        }
    }
}
