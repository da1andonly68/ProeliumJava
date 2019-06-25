import java.util.Random;

public class Bot{

    private String playStyle;
    private Random randomNumber = new Random();
    private Fighter bot;
    private Fighter opp;
    private enum Goals{
        ATTACK,
        HEAL,
        REST,
        RANDOM
    }
    private Goals goal;

    public Bot(Fighter bot, String playStyle){
        this.playStyle = playStyle.toUpperCase();
        this.bot = bot;
    }

    public void setOpponent(Fighter opp){
        this.opp = opp;
    }

    public void play(){
        switch(playStyle){
            case "RANDOM":
            bot.useCard(randomNumber.nextInt(bot.getDeckSize() - 1));
            break;
            default:
            bot.useCard(randomNumber.nextInt(bot.getDeckSize() - 1));
            break;
        }
    }

    boolean healthRisk(){
        return bot.getHealth() < 20;
    }

    boolean highStanima(){
        return bot.getStanima() > 20;
    }

    boolean tired(){
        return bot.getStanima() < 10;
    }

    boolean noStanima(){
        return bot.getStanima() <= 0;
    }

    boolean lowMana(){
        return bot.getMana() < 15;
    }

    boolean highMana(){
        return bot.getMana() > 20;
    }

    boolean noMana(){
        return bot.getMana() <= 0;
    }

    boolean opponentWeak(){
        return opp.getHealth() < 20;
    }



}