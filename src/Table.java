public class Table {
    public static final int LENGTH = 10;

    private Point[][] points = new Point[LENGTH][LENGTH];

    public Table(Point[][] point){
        this.points = point;
    }

    public Table(){
        for(int i = 0; i < points.length; i++){
            for (int j = 0; j < points.length; j++){
                points[i][j] = new Point();
            }
        }
    }

    public PointStatus getPointStatus(int x, int y){
        return points[x][y].getStatus();
    }

    /**
     * Метод проверяет размер корабля, и выводит false если его размер < 1.
     * Если shipOrientation == VERTICAL то проверяет клетки от [x][y] до [x + hp][y], если на них нельзя ставить корабль то выдает false.
     * Если shipOrientation == HORIZONTAL то проверяет клетки от [x][y] до [x][y + hp], если на них нельзя ставить корабль то выдает false.
     * Если клетки свободны, то он заполняет их ссылкой на Ship temp.
     *
     * @param x клетка по оси x на которой будет размещен корабль
     * @param y клетка по оси y на которой будет размещен корабль
     * @param shipOrientation ориентация корабля
     * @param hp размер корабля
     * @return false если корабль не получилось поставить, true если получилось
     */
    public boolean setShip(int x, int y, ShipOrientation shipOrientation, int hp){
        if (hp < 1){
            return false;
        }
        Ship temp = new Ship(hp);
        switch (shipOrientation) {
            case VERTICAL: {
                for (int i = 0; i < hp; i++) {
                    if (!checkPoint(x + i, y)) {
                        return false;
                    }
                }
                for (int i = 0; i < hp; i++) {
                    points[x + i][y].setShip(temp);
                }
                return true;
            }
            case HORIZONTAL: {
                for (int i = 0; i < hp; i++) {
                    if (!checkPoint(x, y + i)) {
                        return false;
                    }
                }
                for (int i = 0; i < hp; i++) {
                    points[x][y + i].setShip(temp);
                }
                return true;
            }
        }
        return false;
    }

    private boolean checkPoint(int x, int y){
        if(x >= 0 && x < LENGTH && y >= 0 && y < LENGTH){
            int downBorder = LENGTH - 1, upBorder = 0, leftBorder = 0, rightBorder = LENGTH - 1;
            if (x > 0){
                upBorder = x-1;
            }
            if(x < LENGTH - 1){
                downBorder = x + 1;
            }
            if(y > 0){
                leftBorder = y - 1;
            }
            if(y < LENGTH - 1){
                rightBorder = y + 1;
            }
            int leftTemp = leftBorder;
            for (;upBorder <= downBorder;upBorder++){
                for(leftBorder = leftTemp; leftBorder <= rightBorder; leftBorder++){
                    if(points[upBorder][leftBorder].hasShip())
                        return false;
                }
            }
            return true;
//            for(int i = x - 1; i <= x + 1 ; i++){
//                if(i < 0 || i > points.length){
//                    continue;
//                }
//                for (int j = y - 1; j <= y + 1 ; j++){
//                    if(j < 0 || j > points[i].length){
//                        continue;
//                    }
//                    if(points[i][j].hasShip()){
//                        return false;
//                    }
//                }
//            }
//            return true;
        }
        return false;
    }

    public Point point (int x, int y){
        return points[x][y];
    }

    public boolean attackPoint(int x, int y){
        if (x >= 0 && x < LENGTH && y >= 0 && y < LENGTH){
            if (!points[x][y].attackPoint()){
                return false;
            }
            return true;
        }
        return false;
    }
    public Table clone(){
        Point[][] newPoints = points.clone();
        return new Table(newPoints);
    }

}
