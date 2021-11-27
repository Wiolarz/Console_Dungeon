import java.util.ArrayList;

public class Hero extends Unit<Unit>// alpha 2.2
{
    int level = 1;
    int exp = 0;
    int gold = 0;

    static Hero generate() // not used
    {
        Hero dude = new Hero();
        return dude;
    }

    Hero()
    {
        create_hero();
    }


    private void create_hero()
    {
        int[] knight = {3, 2, 1};
        int[] rouge = {2, 3, 1};
        int[] mage = {1, 2, 3};
        int[][] roles = {knight, rouge, mage};
        int[] role = roles[(int)(Math.random() * roles.length)];

        STR = role[0];
        AG = role[1];
        INT = role[2];


        artefact = new Item(1);
        artefact.set_stats(STR, AG, INT);


        item_change(artefact);

        max_HP = level * Balance.strong;
        HP = max_HP;


        // to be removed
        gold = 10;
    }

    static Hero create_mercenary(int power)
    {
        Hero mercenary = new Hero();
        int[] knight = {3, 2, 1};
        int[] rouge = {2, 3, 1};
        int[] mage = {1, 2, 3};
        int[][] roles = {knight, rouge, mage};
        int[] role = roles[(int)(Math.random() * roles.length)];

        if (power < 1)
        {
            power = 1;
            Manager.debug("minus level for mercenary");
        }
        mercenary.level = power;

        mercenary.STR = role[0];
        mercenary.AG = role[1];
        mercenary.INT = role[2];


        mercenary.artefact = new Item(power);
        mercenary.item_change(mercenary.artefact);

        mercenary.max_HP = power;
        mercenary.HP = mercenary.max_HP;
        return mercenary;
    }


    public boolean pay(int price)
    {
        // used in shop, if player has enough money to pay the price it returns true
        if (price <= gold){
            gold -= price;
            return true;
        } else
            Manager.println("not enough money " + (price-gold) + " needed");
            return false;
    }


    public void experience(int value)
    {
        exp += value * Balance.weak;
        while (exp > (level * Balance.levelup_speed)) {
            if ((STR+AG+INT) == Balance.dices.length*3){
                return; // max level
            }
            exp -= (level * Balance.levelup_speed);
            level += 1;


            ArrayList<Integer> levelups = new ArrayList<>();
            if (STR < Balance.dices.length) levelups.add(0);
            if (AG < Balance.dices.length) levelups.add(1);
            if (INT < Balance.dices.length) levelups.add(2);
            if (levelups.size() > 0){
                switch (levelups.get((int)((Math.random()*levelups.size())))) {
                    case 0-> STR++;
                    case 1-> AG++;
                    case 2-> INT++;
                }
            }

            stat_changed();
        }
    }


    private void stat_changed()
    {
        // adjusting HP
        int hp_correct = (level * Balance.strong)-max_HP;
        max_HP = level * Balance.strong;
        HP += hp_correct;

        //item_change(artefact); // currently not used
    }

    public void cheats()
    {
        STR = 6;
        AG = 6;
        INT = 6;
        stat_changed();
        HP = max_HP;
        gold += 1000;
    }




    public void printing_all_stats()
    {
        super.printing_all_stats();
        // we add hero specific values
        Manager.println("Gold: " + gold + " Level: " + level + " Exp: " + exp);
    }

    public void heal(int value)
    {
        HP += value;
        if (HP > max_HP){
            HP = max_HP;
        }
    }
}