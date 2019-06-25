import java.io.IOException;
import java.util.Random;
import util.ArrayTools;

public class Game{
 
    static Fighter player1;
    static Fighter player2;
    static Fighter currentFighter;
    static Fighter winner;
    public static int turns = 0;
    public static int rounds = 0;

    static Random random = new Random();
    static boolean firstPlayer;

    public static void main(String[] args) throws IOException, InterruptedException {
        player1 = new Fighter("Player");
        player2 = new Fighter("Bot 2", "random");
        player1.generateCards(6);
        player2.generateCards(6);
        player1.setTarget(player2);
        player2.setTarget(player1);
        firstPlayer = random.nextBoolean();

        while(!GameOver()){
            clearScreen();
            display();
            getCurrentFighter().play();
            turns++;
        }

        display();
        System.out.println("Battle took " + turns + " moves with " + rounds + " turns");
        System.out.println(winner.getName() + " has won");
 
    }

    public static void display(){
        String spacing1 = "                                                         ";
        String spacing2 = "                                                        ";
        String spacing3 = "                                                       ";
        String spacing4 = "                                                          ";
        String spacing5 = "                     ";
        String spacing6 = "     ";
        System.out.println(
            player1.getName() + spacing1 + player2.getName()
        );
        System.out.println(
            "Health " + player1.getHealth() + spacing2 + player2.getHealth()
        );
        System.out.println(
            "Stanima: " + player1.getStanima() + spacing3 + player2.getStanima()
        );
        System.out.println(
            "Mana: " + player1.getMana() + spacing4 + player2.getMana()
        );
        System.out.println(
            "Cards: " + ArrayTools.toStringHL(player1.getHand(player1.DECKSIZE)) + spacing5 + ArrayTools.toStringHL(player2.getHand(player2.DECKSIZE))
        );
        System.out.println();
        if(turns > 0){
            System.out.println(
                spacing6 + currentFighter.getName() + " uses " + currentFighter.getCurrentCard().getName()
          );
        }

        System.out.println();

    }

    public static boolean GameOver(){
        if(player1.defeated()){
            winner = player2;
        }else if(player2.defeated()){
            winner = player1;
        }
        return player1.defeated() || player2.defeated();
    }

    public static Fighter getCurrentFighter(){
        if(currentFighter == null){
            if(firstPlayer){
                currentFighter = player1;
                return player1;
            }else{
                currentFighter = player2;
                return player2;
            }
     
        }
        if(turns >= currentFighter.getEndTurn()){
            currentFighter.endMultiTurn();
            rounds++;
            if(currentFighter == player1){
                currentFighter = player2;
                return player2;
            }else if(currentFighter == player2){
                currentFighter = player1;
                return player1;
            }else{
                currentFighter = player1;
                return player1;
            }
        }else{
                return currentFighter;
        }
        }

        public static void clearScreen()throws IOException, InterruptedException {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }

}