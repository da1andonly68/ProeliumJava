public class Action{
 
    public static final String[] TYPES = {
        "LPunch", "HPunch", "LKick", "HKick", "Block", "FireBall", "Shock", "Heal", "Rest", "Multiplier", "Posion", "Combo"
    };

    private int cardType;
    private Fighter caster;
    private Fighter opponent;

    public Action(int cardType, Fighter user){
        this.cardType = cardType;
        this.caster = user;
    }

    public void setTarget(Fighter target){
        this.opponent = target;
    }

    public String getName(){
        return TYPES[cardType];
    }

    public int getCardNum(){
        return cardType;
    }
    public void use(){
        switch(cardType){
            case 0: punch('l'); break;

            case 1: kick('l'); break;

            case 2: punch('h'); break;

            case 3: kick('h'); break;

            case 4: caster.block(); break;

            case 5: fireball(); break;

            case 6: shock(); break;

            case 7: heal(); break;

            case 8: charge(); break;

            case 9: multiply(); break;

            case 10: poison(); break;

            case 11: combo(); break;

            default: break;
        }
        caster.remove(this);
    }

    private void punch(Character i){
        if(caster.getStanima() > 0){
            switch(i){
                case 'l':
                opponent.damage(5 * caster.getScaler());
                caster.useStanima(2);
                break;
                case 'h':
                opponent.damage(10 * caster.getScaler());
                caster.useStanima(5);
                break;
                default:
                break;
            }
        }else{
            rest();
        }
        caster.setEndTurn();
    }
    private void kick(Character i){
        if(caster.getStanima() > 0){
            switch(i){
                case 'l':
                opponent.damage(7 * caster.getScaler());
                caster.useStanima(4);
                break;
                case 'h':
                opponent.damage(18 * caster.getScaler());
                caster.useStanima(10);  
                break;
                default:
                break;
            }
        }else{
            rest();
        }
        caster.setEndTurn();
    }
    private void fireball(){
        if(caster.getMana() > 0){
            opponent.damage(10 * caster.getScaler());
            caster.useMana(15);
        }else{
            rest();
        }
        caster.setEndTurn();
    }
    private void shock(){
        if(caster.getMana() > 0){
            opponent.damage(5 * caster.getScaler());
            caster.useMana(5);
        }else{
            rest();
        }
        caster.setEndTurn(1 * (int) caster.getScaler());
    }

    private void heal(){
        if(caster.getMana() > 0){
            caster.heal(20 * caster.getScaler());
            caster.useMana(10);
        }else{
            rest();
        }
        caster.setEndTurn();
    }

    private void rest(){
        if(caster.getStanima() < caster.getMaxStanima()){
            caster.refreshStanima(5);
        }
        if(caster.getMana() < 30){
            caster.refreshMana(5);
        }
    }

    private void charge(){
        if(caster.getStanima() < caster.getMaxStanima()){
            caster.refreshStanima(15 * caster.getScaler());
        }
        if(caster.getMana() < caster.getMaxStanima()){
            caster.refreshMana(15 * caster.getScaler());
        }
        caster.setEndTurn();
    }

    private void multiply(){
        caster.setScaler(2 * caster.getScaler());
        caster.setEndTurn();
    }

    private void poison(){
        if(caster.getMana() > 0){
            opponent.setScaler(0.5 * caster.getScaler());
            opponent.damage(7 * caster.getScaler());
            caster.useMana(10);
        }else{
            rest();
        }
        caster.setEndTurn();
    }

    private void combo(){
        caster.setEndTurn(2 * (int) caster.getScaler());
    }

}