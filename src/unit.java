import java.util.ArrayList;

abstract class unit // alpha 2.1
{
    int STR; // STRENGTH number of sides in bonus dices
    int AG;  // AGILITY number of additional dices
    int INT; // INTELLIGENCE number of additional effects

    int HP;  // health points
    int max_HP;

    item artefact;  // only one object item can be assigned to a unit

    ArrayList<Integer> dice_pool = new ArrayList<>();  // full list of dices used to generate strategy
    ArrayList<Integer> turn_pool = new ArrayList<>();


    ArrayList<ArrayList<Integer>> strategy;  // lists of dices used for fighting

    int attack_speed; // number of lists in strategy



    ArrayList<effect> effects_pool = new ArrayList<>();  // full list of dices used to generate strategy

    ArrayList<ArrayList<effect>> magic;  // 3 lists of effects used for fighting

    unit()
    {// this works before Hero()
        attack_speed = 1; // every unit has at least 1 attack

        strategy = new ArrayList<>(attack_speed);
        magic = new ArrayList<>(attack_speed);
        for(int i=0; i < attack_speed; i++)
        {
            strategy.add(new ArrayList());
            magic.add(new ArrayList());
        }
    }






    public void item_change(item thing)
    {
        dice_pool.clear();
        effects_pool.clear();

        // adding item base pool of dices
        for(int dice : thing.base_pool)
        {
            dice_pool.add(dice);
        }
        turn_pool = (ArrayList<Integer>) dice_pool.clone();

        // adding item effect pool
        for(effect spell : thing.magic_pool)
        {
            effects_pool.add(spell);
        }

        // adding dices based on STR + AG
/*
        int power = STR;  // number of sides
        if(power >= balance.dices.length){
            power = balance.dices.length - 1;
        }

        for (int i = 0; i < (AG); i++) {  // number of dices
            dice_pool.add(balance.dices[power]);
        }*/

        generate_strategy(); // adding new dices to the strategy
    }






    public void generate_strategy()
    {
        // adjusting attack speed

        strategy.clear();
        magic.clear();

        for (int i = 0; i < attack_speed; i++)
        {

            strategy.add(new ArrayList<Integer>());
            magic.add(new ArrayList<effect>());
        }

        int counter = 0;

        // place where strategy is generated
        for (int i = 0; i < turn_pool.size(); i++)
        {
            strategy.get(counter).add(turn_pool.get(i));
            counter += 1;
            if (counter == attack_speed)
            {
                counter = 0;
            }
        }

        counter = 0;

        for (int i = 0; i < effects_pool.size(); i++)
        {
            magic.get(counter).add(effects_pool.get(i));
            counter += 1;
            if (counter == attack_speed)
            {
                counter = 0;
            }
        }
    }



    // temp place for effect



    public void effect(ArrayList<Integer> dices, int round)
    {// we are making copy to avoid saving effect to an object

        for (effect spell : magic.get(round))
        {
            spell.use(dices);
        }

    }



    static int attack(ArrayList<Integer> dices)
    {
        int value = 0;
        for (int i = 0; i < dices.size(); i++)
        {
            value += (int) (Math.random() * dices.get(i)) + 1;
        }
        return value;
    }



    public void printing_all_stats()
    { // we print every unit value
        manager.println("STR: " + STR + " AG: " + AG + " INT: " + INT);
        manager.println("HP: " + HP + " MAX_HP: " + max_HP);
        manager.println("Item base: " + dice_pool);
        manager.println("Strategy: " + strategy);
        manager.print("Magic: ");
        for (ArrayList<effect> spell_list : magic)
        {
            for (effect spell : spell_list)
            {
                manager.print(spell.short_print());
            }
        }
        manager.println("");
    }
}
