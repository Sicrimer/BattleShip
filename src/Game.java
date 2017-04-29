import java.util.Scanner;
public class Game {
    private Scanner scanner = new Scanner(System.in);
    Player firstPlayer;
    Player secondPlayer;
    private boolean gameStarted = false;
    private static int MAX_SCORE;

    public Table setupTable(){
        int hps[] = {1,1,1,1,2,2,2,3,3,4};
        int summa = 0;
        for (int i = 0; i < hps.length; i++){
            summa += hps[i];
        }
        MAX_SCORE = summa;
        int count = hps.length;
        Table table = new Table();
        while(count != 0) {
            showTable(table);
            System.out.println("Введите координату x :");
            int x = scanner.nextInt();
            System.out.println("Введите координату y :");
            int y = scanner.nextInt();
            System.out.println("Введите направление корабля (0 - вертикальное, 1 - горизонтальное) :");
            int orientation = scanner.nextInt();
            boolean hasHP = false;
            int indexInArray = -1;
            int hp = -1;
            while(!hasHP){
                for (int l = 0; l < count; l++){
                    System.out.print(hps[l] + " ");
                }
                System.out.println();
                System.out.println("Введите размер корабля :");
                hp = scanner.nextInt();
                for (int k = 0; k < count; k++) {
                    if (hps[k] == hp) {
                        indexInArray = k;
                        hasHP = true;
                        break;
                    }
                }
                if(!hasHP){
                    System.out.println("Корабль такого размера поставить нельзя.");
                }
            }
            switch (orientation) {
                case 0: {
                    if (hasHP && table.setShip(x, y, ShipOrientation.VERTICAL, hp)) {
                        for (int k = indexInArray; k < count-1; k++) {
                            hps[k] = hps[k+1];
                        }
                        count--;
                        break;
                    } else {
                        System.out.println("Не правильные x или y");
                    }
                    break;
                }
                case 1: {
                    if (table.setShip(x, y, ShipOrientation.HORIZONTAL, hp)) {
                        for (int k = indexInArray; k < count-1; k++) {
                            hps[k] = hps[k+1];
                        }
                        count--;
                        break;
                    } else {
                        System.out.println("Не правильные x или y");
                    }
                    break;
                }
                default: {
                    System.out.println("Неправильное направление");
                    break;
                }
            }
        }
        return table;
    }

    public void setup(){
        System.out.println("Ваше имя :");
        String firstPlayerName = scanner.next();
        Table firstPlayerTable = setupTable();
        System.out.println("Ваше имя :");
        String secondPlayerName = scanner.next();
        Table secondPlayerTable = setupTable();
        firstPlayer = new Player(firstPlayerName, firstPlayerTable, secondPlayerTable);
        secondPlayer = new Player(secondPlayerName, secondPlayerTable, firstPlayerTable);
        gameStarted = true;
        startGame();
    }

    public void startGame(){
        Player winner = null;
        while (winner == null){
                doTurn(firstPlayer, secondPlayer);
                if(firstPlayer.getScore() == MAX_SCORE){
                    winner = firstPlayer;
                    break;
                }
                doTurn(secondPlayer, firstPlayer);
                if (secondPlayer.getScore() == MAX_SCORE){
                    winner = secondPlayer;
                    break;
                }
            }
        System.out.println(winner.getName() + " is winner ");
    }

    private void doTurn(Player player1, Player player2){
        boolean turnEnded = false;
        while (!turnEnded) {
            System.out.println(player1.getName() + " now your turn:");
            System.out.println("Your table :");
            showTable(player1.getTable());
            System.out.println("Your enemy's table :");
            showTable(player1.getEnemyTable());
            System.out.println("Enter X :");
            int x = scanner.nextInt();
            System.out.println("Enter Y :");
            int y = scanner.nextInt();
            if (player2.getTable().attackPoint(x, y)){
                turnEnded = true;
            }
            if (player2.getTable().getPointStatus(x, y) == PointStatus.HAS_ALIVE_SHIP || player2.getTable().getPointStatus(x, y) == PointStatus.HAS_DEATH_SHIP){
                turnEnded = false;
                player1.addScore();
            }
            if (player1.getScore() == MAX_SCORE){
                break;
            }
        }
    }

    public void showTable(Table table) {
        System.out.print("  ");
        for (int i = 0; i < Table.LENGTH; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < Table.LENGTH; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < Table.LENGTH; j++) {
                Point temp = table.point(i, j);
                switch (temp.getPointStatus()) {
                    case EMPTY: {
                        System.out.print("● ");
                        break;
                    }
                    case MISSING: {
                        System.out.print("○ ");
                        break;
                    }
                    case HAS_ALIVE_SHIP: {
                        System.out.print("☩ ");
                        break;
                    }
                    case HAS_DEATH_SHIP: {
                        System.out.print("☨ ");
                        break;
                    }
                    case HAS_WOUNDED_SHIP: {
                        System.out.print("X ");
                        break;
                    }
                    case UNDERTERMINATED:{
                        System.out.print("_ ");
                        break;
                    }
                }
            }
            System.out.println();

        }
    }
}
