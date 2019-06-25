import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import util.*;

public class Fighter {
    
    private String name;
    private boolean player;
    private Fighter target;

    private final double STARTHEALTH = 50;
    private final double MAXSTANIMA = 30;
    private final double MAXMANA = 30;
    public int DECKSIZE = 5;

    private double health = STARTHEALTH;
    private double stanima = MAXSTANIMA;
    private double mana = MAXMANA;

    private ArrayList<Action> hand;
    private Action currentCard = new Action(0, this);
    private Action lastCard = new Action(0, this);
    private RandomArray random = new RandomArray();
    private Random randomNumber = new Random();

    private double scaler = 1;
    private boolean tempScale = false;
    private boolean lastTempScale = false;

    private boolean damageMitigation;

    private int storedTurn = 0;
    private int endOfTurn = 0;
    private boolean multiTurn = false;
    private boolean stunned = false;

    //Bot
    private Bot bot;
    private Player human;

    /** Player */
    public Fighter(String name) {
        this.name = name;
        hand = new ArrayList<Action>();
        this.player = true;
        human = new Player(this);
    }

    /**Bot */
    public Fighter(String name, String playStyle){
        this.name = name;
        hand = new ArrayList<Action>();
        this.player = false;
        bot = new Bot(this, playStyle);
    }

    public String getName() {
        return name;
    }

    public int getDeckSize() {
        return DECKSIZE;
    }

    public void setScaler(double multiplier) {
        if (multiplier > 1) {
            tempScale = true;
        }
        this.scaler = multiplier;
    }

    public double getScaler() {
        return scaler;
    }

    public double getHealth() {
        return health;
    }

    public void damage(double damage) {
        if (damageMitigation) {
            damage = damage / (2 * scaler);
            damageMitigation = false;
        }
        health = health - damage;
    }

    public void heal(double heal) {
        health = health + (heal * scaler);
    }

    public boolean defeated() {
        return (health <= 0);
    }

    public double getStanima() {
        return stanima;
    }

    public double getMaxStanima() {
        return MAXSTANIMA;
    }

    public void useStanima(double drain) {
        stanima = stanima - drain;
    }

    public void refreshStanima(double recovery) {
        stanima = stanima + recovery;
    }

    public boolean exhausted() {
        return (stanima < 0);
    }

    public double getMaxMana() {
        return MAXMANA;
    }

    public double getMana() {
        return mana;
    }

    public void useMana(double drain) {
        mana = mana - drain;
    }

    public void refreshMana(double recovery) {
        mana = mana + recovery;
    }

    public void block() {
        damageMitigation = true;
    }

    public Action[] generateCards(int number) {
        Action[] hand = new Action[number];
        int[] randomArray = random.randomArray(Action.TYPES.length);
        for (int i = 0; i < number; i++) {
            hand[i] = new Action(randomArray[i], this);
            this.hand.add(hand[i]);
        }
        DECKSIZE = number;
        return hand;
    }

    public void replaceCard() {
        add(new Action(randomNumber.nextInt(Action.TYPES.length), this));
        getCards().get(4).setTarget(target);
    }

    public void add(Action card) {
        this.hand.add(card);
    }

    public void remove(Action card) {
        this.hand.remove(card);
    }

    public String[] getHand(int number) {
        String[] list = new String[number];
        for (int i = 0; i < number; i++) {
            list[i] = this.hand.get(i).getName();
        }
        return list;
    }

    public ArrayList<Action> getCards() {
        return hand;
    }

    public void setTarget(Fighter target) {
        for (int i = 0; i < DECKSIZE; i++) {
            getCards().get(i).setTarget(target);
        }
        this.target = target;
        if(!player){
            bot.setOpponent(target);
        }
    }

    public void useCard(int indexPlace) {
        storedTurn = Game.turns;
        if (indexPlace > DECKSIZE) {
            indexPlace = DECKSIZE;
        }
            this.lastCard = this.currentCard;
            this.currentCard = getCards().get(indexPlace);
        if (!lastTempScale && !tempScale) {
            setScaler(1);
        }
        getCards().get(indexPlace).use();
            lastCard = getCards().get(indexPlace);
            lastTempScale = tempScale;
            tempScale = false;
        replaceCard();
        storedTurn++;
    }

    public Action getLastCard() {
        return lastCard;
    }

    public Action getCurrentCard() {
        return currentCard;
    }

    public void setEndTurn(){
        if(!multiTurn){
            endOfTurn = storedTurn;
        }
    }

    public void setEndTurn(int delay){
        multiTurn = true;
        endOfTurn = storedTurn + delay + 1;
    }

    public int getEndTurn(){
        return endOfTurn;
    }

    public void endMultiTurn(){
        multiTurn = false;
    }

    public boolean getStunned(){
        return stunned;
    }

    public void play(){
        if(!player){
            bot.play();
        }else if(player){
            human.play();
        }
    }

}