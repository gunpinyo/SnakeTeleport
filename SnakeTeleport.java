public class SnakeTeleport {
    
    public static void main(String[] args) {
        Player[] players = Player.getPlayers();
        Board board = Board.buildBoard(players);
        Play.buildDice();
        // cp -> current player
        for(board.cp=0;Play.whoWins(board) == null;board.cp=(board.cp+1)%players.length) {
            int numSteps = Play.rollDice(players[board.cp]);
            Play.doTurn(board,players[board.cp],numSteps);
        }
        Play.gameOver(board);
    }
    

}
