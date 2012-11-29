import java.util.*;
import java.io.*;
class Board {

	public final int rows, cols;
	public final int goalPos;
	public final Map<Integer, Integer> teleMap;
    public final Player[] players;   
    public int cp; 
 
    public Board(int rows, int cols, Player[] players) {
        this.rows = rows;
        this.cols = cols;
        this.goalPos = rows*cols -1;
        this.teleMap = new HashMap<Integer, Integer>();
        this.players = players;
        this.cp = -1;
    }
    public void printBoard() {
        int digit = 0;
        for(int size=goalPos;size>0;size/=10)
            digit ++;
        clearConsole();
        for(int j=0;j<=cols*(digit+4)+2;j++)
            System.out.print("$");
        System.out.println();
        System.out.print("$");
        for(int j=0;j<cols;j++) {
            System.out.print("+");
            for(int k=0;k<digit+3;k++)
                System.out.print("-");
        }
        System.out.println("+$");
        for(int i=rows-1;i>=0;i--) {

            System.out.print("$|");
            for(int j=0;j<cols;j++) {
                int curPos;
                if(i%2==0)
                    curPos = i*cols + j;
                else
                    curPos = i*cols + (cols-1)-j;
                System.out.print(String.format("%0"+digit+"d",curPos));
                int arrowDirection = -1; // 0->E , 1->N , 2->W , 3->S
                if(cp >= 0 && curPos == players[cp].position) {
                    if(curPos%cols==cols-1 && curPos != goalPos)
                        arrowDirection = 1;
                    else if(i%2==0)
                        arrowDirection = 0;
                    else
                        arrowDirection = 2;
                    if(players[cp].isBackward)
                        arrowDirection = (arrowDirection+2)%4;
                }
                switch(arrowDirection) {
                    case 0: System.out.print(" ->|");  break;
                    case 1: System.out.print(" /\\|"); break;
                    case 2: System.out.print(" <-|");  break;
                    case 3: System.out.print(" \\/|"); break;
                    default:System.out.print("   |");  break;
                }
            }
            System.out.println("$");

            System.out.print("$|");
            for(int j=0;j<cols;j++) {
                int curPos;
                if(i%2==0)
                    curPos = i*cols + j;
                else
                    curPos = i*cols + (cols-1)-j;
                char symbol = ' ';
                for(int k=0;k<players.length;k++)
                    if(players[k].position == curPos)
                        switch(symbol) {
                            case ' ': symbol = players[k].symbol;
                                      break;
                            default : symbol = '*';
                                      break;
                        }
                if(!teleMap.containsKey(curPos)) {
                    for(int k=0;k<digit;k++)
                        System.out.print(" ");
                    System.out.print(" "+symbol+" |");
                } else {
                    curPos = teleMap.get(curPos);
                    System.out.print(String.format("%0"+digit+"d",curPos));
                    System.out.print(" "+symbol+" |");
                }
            }
            System.out.println("$");

            System.out.print("$");
            for(int j=0;j<cols;j++) {
                System.out.print("+");
                for(int k=0;k<digit+3;k++)
                    System.out.print("-");
            }
            System.out.println("+$");
        }
        
        for(int j=0;j<=cols*(digit+4)+2;j++)
            System.out.print("$");
        System.out.println();

        System.out.println();
    }
    public static Board buildBoard(Player[] players) {
        System.out.print("How many rows? :");
        int rows = IOUtil.readInt();
        System.out.print("How many columns? :");
        int cols = IOUtil.readInt();
        
        Board board = new Board(rows, cols, players);
       
        Player[] emptyPlayers = new Player[0];
        while (true) {
            board.printBoard();
            System.out.println("You now have "+board.teleMap.size()+
                                    " teleport(s).");
            System.out.println("Say  add     to add another teleport;");
            System.out.println("     delete  to delete an existing teleport;");
            System.out.println("     finish  to begin the game.");
            String cmd = IOUtil.readString();
            if (cmd.equals("add")) {
                int source;
                int dest;
                while (true) {
                    System.out.print("Please enter the location of the teleport: ");
                    source = IOUtil.readInt();
                    System.out.print("Please enter the destination of the teleport: ");
                    dest = IOUtil.readInt();
                    if ( 0 <= source && source <= board.goalPos
                        &&  0 <= dest    && dest   <= board.goalPos
                        && !board.teleMap.containsKey(source)
                        && source!=dest) {
                        break;
                    }
                    System.out.println("STOP TROLLING ME.");
                }
                board.teleMap.put(source,dest);
            }
            else if (cmd.equals("delete")) {
                int delKey;
                while (true) {
                    System.out.print("Please enter the location of the teleport you want to delete: ");
                    delKey = IOUtil.readInt();
                    if (0 <= delKey && delKey <= board.goalPos)
                        break;
                    System.out.println("Please enter a valid key!");
                }
                if(board.teleMap.remove(delKey)!=null)
                    System.out.println("Delete successful");
                else
                    System.out.println("Delete unsuccessful");
            }
            else if (cmd.equals("finish")) {
                break;
            }
            else 
                System.out.println("Wrong syntax. Try again.");
        }

        
        
        
        return board;
    }

    private static void clearConsole() {
        Console c = System.console();
          if (c == null) {
              System.err.println("no console");
              System.exit(1);
          }
          // clear screen only the first time
          c.writer().print('\u001B' + "[2J");
          // reposition the cursor to 1|1
          c.writer().print('\u001B' + "[1;1H");
          c.flush();

    }


}
