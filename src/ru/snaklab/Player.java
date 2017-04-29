package ru.snaklab;

/**
 * Created by Samat Khairutdinov on 05.04.17 18:52.
 * BattleShip
 */
public class Player {
    private String name;

    private boolean isWinner = false;

    private int countTurns = 0;

    private Table playerTable;

    private Table enemyTable;

    public Player(String name, Table playerTable, Table enemyTable) {
        this.enemyTable = enemyTable;
        this.playerTable = playerTable;
        this.name = name;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public String getName() {
        return name;
    }

    public int getCountTurns() {
        return countTurns;
    }

    public Table getPlayerTable() {
        return (Table) playerTable.clone();
    }

    public Table getEnemyTable() {
        Point[][] resultPoint = new Point[10][10];
        for (int i = 0; i < Table.D; i++){
            for (int j = 0; j < Table.D; j++){
                if(enemyTable.getPoints()[i][j].isOpen()){
                    resultPoint[i][j] = (Point) enemyTable.getPoints()[i][j].clone();
                }
                else {
                    resultPoint[i][j] = Point.getUndeterminedPoint();
                }
            }
        }
        return new Table(resultPoint);
    }

    public boolean attack(int x, int y){
        if(enemyTable.attack(x,y)){
            countTurns++;
            isWinner = !enemyTable.checkAliveShips();
            return true;
        }
        else {
            return false;
        }
    }
}
