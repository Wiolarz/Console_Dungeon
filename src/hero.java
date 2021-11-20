import java.util.ArrayList;

public class hero extends unit // alpha 2.2
{
    int level = 1;
    int exp = 0;
    int gold = 0;

    static hero generate()
    {
        hero dude = new hero();
        return dude;
    }

    hero()
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



        artefact = new item(1);
        artefact.set_stats(STR, AG, INT);




        item_change(artefact);
        generate_strategy();

        max_HP = level * balance.strong;
        HP = max_HP;


        // to be removed
        gold = 10;
    }


    public boolean pay(int price)
    {
        // used in shop, if player has enough money to pay the price it returns true
        if (price <= gold){
            gold -= price;
            return true;
        } else
            System.out.println("not enough money " + (price-gold) + " needed");
            return false;
    }


    public void experience(int value)
    {
        exp += value * balance.weak;
        while (exp > (level * balance.levelup_speed)) {
            if ((STR+AG+INT) == balance.dices.length*3){
                return; // max level
            }
            exp -= (level * balance.levelup_speed);
            level += 1;


            ArrayList<Integer> levelups = new ArrayList<>();
            if (STR < balance.dices.length) levelups.add(0);
            if (AG < balance.dices.length) levelups.add(1);
            if (INT < balance.dices.length) levelups.add(2);
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
        int hp_correct = (level * balance.strong)-max_HP;
        max_HP = level * balance.strong;
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




    @Override
    public void printing_all_stats()
    {
        // we add hero specific values
        System.out.println("STR: " + STR + " AG: " + AG + " INT: " + INT);
        System.out.println("HP: " + HP + " MAX_HP: " + max_HP);
        System.out.println("Item base: " + dice_pool);
        System.out.println("Strategy: " + strategy);
        System.out.print("MAGIC: ");
        for (ArrayList<effect> spell_list : magic)
        {
            for (effect spell : spell_list)
            {
                output.print(spell.short_print());
            }
        }
        System.out.println();
        System.out.println("Gold: " + gold + " Level: " + level + " Exp: " + exp);
    }

    public void heal(int value)
    {
        HP += value;
        if (HP > max_HP){
            HP = max_HP;
        }
    }

    public void damage(int value)
    {
        // Health points reduction
        HP -= value;
        if (HP <= 0){
            System.out.println("YOU DIED");
            System.exit(666);
        }
    }
}