package ru.snaklab;

import java.util.Scanner;

/**
 * Created by Samat Khairutdinov on 05.04.17 18:52.
 * BattleShip
 */
public class Game {
    Player firstPlayer;

    Player secondPlayer;

    Scanner scanner = new Scanner(System.in);

    public void clearConsole(){
        for(int i = 0; i < 100; i++)
            System.out.println();
    }


    public Table setUpTable(){
        Table table = new Table();
        int x;
        int y;
        int orientation;
        for(int i = 0; i < 2;){
            showTable(table);
            System.out.println("Enter x");
            x = scanner.nextInt();
            System.out.println("Enter y");
            y = scanner.nextInt();
            System.out.println("Enter orientation 0 - vertical 1-horizontal");
            orientation = scanner.nextInt();
            switch (orientation){
                case 0:{
                    if(table.setShip(3, x, y, ShipOrientation.VERTICAL)){
                        i++;
                    }
                    else {
                        System.out.println("Wrong x or y");
                    }
                    break;
                }
                case 1:{
                    if(table.setShip(3, x, y, ShipOrientation.HORIZONTAL)){
                        i++;
                    }
                    else {
                        System.out.println("Wrong x or y");
                    }
                    break;
                }
                default:{
                    System.out.println("Wrong orientation");
                }
            }
        }
        clearConsole();
        return table;
    }

    public void setUp(){
        String firstPlayerName;
        String secondPlayerName;

        System.out.println("Enter first Player name");
        firstPlayerName = scanner.next();
        Table firstTable = setUpTable();

        System.out.println("Enter second Player name");
        secondPlayerName = scanner.next();
        Table secondTable = setUpTable();

        this.firstPlayer = new Player(firstPlayerName, firstTable, secondTable);
        this.secondPlayer = new Player(secondPlayerName, secondTable, firstTable);
        clearConsole();
    }

    public void showTable(Table table){
        System.out.print("  ");
        for (int i = 0; i < Table.D; i++){
            System.out.print(i + " ");
        }
        System.out.println();
        for(int i = 0; i < Table.D; i++){
            System.out.print(i + " ");
            for(int j = 0; j < Table.D; j++){
                switch (table.getPoints()[i][j].getPointStatus()){
                    case EMPTY:
                        System.out.print("○ ");
                        break;
                    case HAS_ALIVE_SHIP:
                        System.out.print("◍ ");
                        break;
                    case HAS_WOUNDED_SHIP:
                        System.out.print("◒ ");
                        break;
                    case HAS_DEATH_SHIP:
                        System.out.print("● ");
                        break;
                    case UNDETERMINED:
                        System.out.print("◌ ");
                        break;
                }
            }
            System.out.println();
        }
    }

    public void playerTurn(Player player){
        int x;
        int y;
        boolean endTurn = false;

        System.out.println(player.getName()+" turn");

        System.out.println("Your desk:");
        showTable(player.getPlayerTable());





        while (!endTurn && !player.isWinner()){
            System.out.println("Enemy desk:");
            showTable(player.getEnemyTable());
            System.out.println("Enter attack x and y");
            x = scanner.nextInt();
            y = scanner.nextInt();
            if(player.attack(x,y)){
                if(player.getEnemyTable().getPoints()[x][y].getPointStatus().equals(PointStatus.HAS_WOUNDED_SHIP) ||
                        player.getEnemyTable().getPoints()[x][y].getPointStatus().equals(PointStatus.HAS_DEATH_SHIP))
                    endTurn = false;
                else
                    endTurn = true;
            }
        }
        clearConsole();
    }

    public void startGame(){
        Player winner =null ;
        setUp();
        while (winner == null){
            playerTurn(firstPlayer);
            if(firstPlayer.isWinner()){
                winner = firstPlayer;
                break;
            }
            playerTurn(secondPlayer);
            if(secondPlayer.isWinner()){
                winner = secondPlayer;
                break;
            }
        }
        System.out.println("Player " + winner.getName() + " win!");
        System.out.println("Repeat? \n 0 - yes 1 - no");
        if(scanner.nextInt() == 0){
            new Game().startGame();
        }
    }
}
