import java.util.Scanner;

public class Player{

    private Fighter player;
    Scanner in = new Scanner(System.in);

    public Player(Fighter player){
        this.player = player;
    }

    public void play(){
            int cardSelection = in.nextInt();
            if(cardSelection > 5){
                cardSelection = 5;
            }
            player.useCard(cardSelection - 1);
            System.out.println("You have played the " + player.getCurrentCard().getName() + " card");
            System.out.println();

    }

}