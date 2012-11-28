public class SnakeTeleport {
    
    public static void main(String[] args) {
        Player[] players = Player.getPlayers();
        Board board = Board.buildBoard(players);
        Play.buildDice();
        // cp -> current player
        for(int cp=0;Play.whoWins(board) == null;cp=(cp+1)%players.length) {
            int numSteps = Play.rollDice(players[cp]);
            Play.doTurn(board,players[cp],numSteps);
        }
        Play.gameOver(board);
    }
    

}
