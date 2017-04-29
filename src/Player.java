import javafx.scene.control.Tab;

import java.util.Scanner;
public class Player {
    private Scanner scanner = new Scanner(System.in);
    private String name;
    private Table table;
    private Table enemyTable;
    private int score;

    public Player(String name, Table table, Table enemyTable){
        this.name = name;
        this.table = table;
        this.enemyTable = enemyTable;
        this.score = 0;
    }

    public void addScore(){
        score++;
    }

    public int getScore(){
        return score;
    }

    public String getName(){
        return name;
    }

    public Table getTable(){
        return table;
    }

    public Table getEnemyTable(){
        Point[][] tempPoints = new Point[Table.LENGTH][Table.LENGTH];
        for(int i = 0; i < Table.LENGTH; i++){
            for (int j = 0; j < Table.LENGTH; j++){
                if(enemyTable.point(i,j).getOpen()){
//                    setter not change
//                    showEnemyTable.changePointStatus(i, j, PointStatus.UNDERTERMINATED);
                    tempPoints[i][j] = enemyTable.point(i,j).clone();
                }
                else{
                    tempPoints[i][j] = new Point(PointStatus.UNDERTERMINATED);
                }
            }
        }
        return new Table(tempPoints);
    }
}
