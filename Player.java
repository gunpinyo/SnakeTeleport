class Player {

	public final String name;
	public final char symbol;
	public int position;
    boolean isBackward;

	public Player(String name,char symbol) {
		this.name = name;
		this.symbol = symbol;
		position = 0;
	}

	public void move(Board board,boolean reset) {
        if(reset)
            isBackward = false;
        if(!isBackward)
            position ++;
        else
            position --;
        if(position >= board.goalPos)
            isBackward = true;
        else if(position < 0)
            isBackward = false;
	}

    public boolean teleport(Board board) {
        if (!board.teleMap.containsKey(position))
            return false;
        position = board.teleMap.get(position);
        return true;
    }

    public static Player[] getPlayers() {
        System.out.print("How many are you? : ");
        int n = IOUtil.readInt();

        Player[] players = new Player[n];
        for (int i = 0; i<n; i++) {
            System.out.print("Player " + (i+1) + ", please enter your name : ");
            String name = IOUtil.readString();
            System.out.print("Player " + name + ", please enter your chosen symbol : ");  
	    String symbol;
            while (true) {
                symbol = IOUtil.readString();
                if (symbol.length() == 1) {
                    break;
                }
                System.out.print("Please give a proper symbol! Try again : ");
            }
            
            players[i] = new Player(name, symbol.charAt(0));
        }
        return players;
    }


}
