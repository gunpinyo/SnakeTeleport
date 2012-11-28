import java.util.*;


class Play {
    public static int dieSize;
    private static Random random;

    public static String whoWins(Board board) {
        for (int i = 0; i<board.players.length; i++) {
            if(board.players[i].position == board.goalPos)
                return board.players[i].name;
        }
        return null;
    }

    public static void buildDice() {
        System.out.print("Please select the size of the dice you want :");
        int size; 
        while (true) {
            size = IOUtil.readInt();
            if (size >0) {
                break;
            }
            System.out.println("Please enter a die size greater than 0.");
        }
        dieSize = size;
        random = new Random();
    } 


    public static int rollDice(Player player) {
        System.out.println(player.name + ", it is your turn. Initiate the dice roll");
        System.out.println(" by selecting a number between 1 and " + dieSize + ".");
        int first; 
        while (true) {
            first = IOUtil.readInt();
            if (0 < first && first <= dieSize) {
                break;
            }
            System.out.println("Number between 1 and " + dieSize + " you fool.");
        }
        return ((first + random.nextInt(dieSize))%dieSize)+1;
    }


    public static void doTurn(Board board, Player player, int numSteps) {
        board.printBoard();
        System.out.println("Congratulations, you have rolled a " + numSteps + ".");
        System.out.print("Press Enter key to continue ... ");
        IOUtil.readLine();
        for (int i=1; i<=numSteps; i++) {
            player.move(board, i==0);
            board.printBoard();
            System.out.println("You have moved " + i + " of " + numSteps + " steps.");
            System.out.print("Press Enter key to continue ... ");
            IOUtil.readLine();
        }

        int prevPos = player.position;
        if (player.teleport(board)) {
            board.printBoard();
            System.out.println("Guess what?! You have just been teleported successfully.");
            if (player.position > prevPos) {
                System.out.println("Congratulations you have advanced " + (player.position -prevPos) + " steps.");
            } else {
                System.out.println("Oh bugger, you have fallen back " + (prevPos - player.position) + " steps.");
            }
        }
    }

    public static void gameOver(Board board) {
        System.out.println("LEGENDARY. " + whoWins(board) + " has won GBX's first game.");
        System.out.println("SnakeTeleport was extensively developed at Imperial College London.");
        System.out.println("This makes you, the WINNER, a LEGENDARY PERSON. Oh baby!");
    }

}
