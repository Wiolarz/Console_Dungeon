import java.util.ArrayList;

public class Item // alpha 2.1
{
    int[] base_pool;
    ArrayList<Effect> magic_pool;
    // those are stats bonuses
    int STR_req;
    int AG_req;
    int INT_req;

    int level;


    Item(int power) // String type,
    {
        /*switch (type)
        {
            case "random" -> random_item(power);
            case "stats" ->
        }*/
        random_item(power);
    }

    public void random_item(int power)
    {
        /* creating type of item
        there are 3 values each containing a stat example: STR AG INT
        is an item that balances every stat
        STR STR STR is a powerful strength item, while AG AG INT is a mix
        */

        if (power > Balance.max_power)
            power = Balance.max_power;
        level = power;

        int[] type = {0,0,0}; // used to create types of items


        for (int i = 0; i < 3; i++)
        {
            type[i] = (int) (Math.random()*3);
        }

        int counter = 0;
        for (int i = 0; i < power; i++)
        {
            switch (type[counter])
            {
                case 0-> {if (STR_req!= Balance.dices.length) STR_req++;}
                case 1-> {if (AG_req!= Balance.dices.length) AG_req++;}
                case 2-> {if (INT_req!= Balance.dices.length) INT_req++;}
            }

            counter++;
            if (counter == 3) counter = 0;
        }


        // now there is a need to design interesting system :)))


        /*
        STR big dices
        AG many dices
        INT smaller enemy dices

        placeholder but maybe fine generator for basic quality items:
        each point is a die, with a chance of spawning related thing:
        STR making this die a larger one
        AG spawn additional die
        INT spawn effect
        */

        generate_dices();
    }


    public void generate_dices()
    {
        // base pool of dices
        ArrayList<Integer> base = new ArrayList<>();
        magic_pool = new ArrayList<>();


        // we are adding randomly sized dices to the pool in a number equal to "power" of the item
        final int len = Balance.dices.length;
        final int weak_dices =   (len / 4);
        final int normal_dices = (len / 2);
        //int strong_dices =

        // based on statistics we add different types of dices
        for (int i = 0; i < STR_req; i++)  // big sized dices
        {
            base.add(Balance.dices[(int)(Math.random() * (len - 3)) + 3]); // magic number no sadge
        }

        for (int i = 0; i < AG_req; i++) // many smaller dices
        {
            base.add(Balance.dices[(int)(Math.random() * weak_dices)]);
            base.add(Balance.dices[(int)(Math.random() * normal_dices)]);
        }

        for (int i = 0; i < INT_req; i++)  // magic
        {
            base.add(Balance.dices[(int)(Math.random() * weak_dices)]);
            switch ((int) (Math.random()*3))
            {
                case 0 -> magic_pool.add(new Effect(1, "edge", 2));
                case 1 -> magic_pool.add(new Effect(2, "edge", 1));
                case 2 -> magic_pool.add(new Effect(1, "random", 3));
            }

        }
        //if ((int)(Math.random()*2) == 0) magic.add(121); // basic edge effect

        // we change array list to int[]
        base_pool = new int[base.size()];
        for (int i = 0; i < base.size(); i++)
        {
            base_pool[i] = base.get(i);
        }
    }


    public void set_stats(int STR, int AG, int INT)
    {
        STR_req = STR;
        AG_req = AG;
        INT_req = INT;

        generate_dices();
    }



    public boolean Does_Fit(Unit UNIT)
    {
        return (UNIT.STR >= STR_req) && (UNIT.AG >= AG_req) && (UNIT.INT >= INT_req);
    }


    public void print_item()
    {
        System.out.println("STR " + STR_req + " AG " + AG_req + " INT " + INT_req);
        for(int i : base_pool)
        {
            System.out.print(i + " ");
        }
        System.out.println();
        for(Effect spell : magic_pool)
        {
            spell.print();
        }
        System.out.println();
    }
}
