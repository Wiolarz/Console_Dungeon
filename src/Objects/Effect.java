package Objects;

import java.util.ArrayList;
import Gameplay.*;
import Technical.*;

public class Effect // alpha 2.2
{

    int usages;
    String type;
    int power;


    Effect(int usage, String effect_type, int force)
    {
        usages = usage;
        type = effect_type;
        power = force;
    }


    public void print()
    {
        Manager.print("[");
        Manager.printf("%-10s", ("usages: " + usages));
        Manager.printf("%-12s", ("type: " + type));
        Manager.printf("%-10s", ("power: " + power + "]"));

    }

    public String short_print()
    {
        return usages + " " + type + " " + power;
    }


    public void use(ArrayList<Integer> dice)
    {
        switch (type)
        {
            case "value" -> value_effect(dice);
            case "edge" -> edge_effect(dice);
            case "random" -> r_target_effect(dice);
        }
    }


    public void value_effect(ArrayList<Integer> dice)
    { // effect  to a target, changing it to a "type" value
        int cur_usage = usages;
        for (int i = 0; i < dice.size() && cur_usage > 0; i++)
        {
            if (dice.get(i) == Balance.dice[power])
            {
                // effect
                dice.set(i, Balance.smallest_die_value);

                cur_usage--;
            }
        }
    }



    public void edge_effect(ArrayList<Integer> dice)
    { // we modify the highest values
        for (int i = 0; i < usages; i++)
        {
            try
            {
                // effect
                int idx = 0;
                int dice_idx = 0;
                int max = dice.get(0);

                for (int die_val: dice)
                {
                    if (max  < die_val)
                    {
                        max = die_val;
                        dice_idx = idx;
                    }
                    idx++;
                }

                dice.set(dice_idx, die_change(dice.get(dice_idx), -power));
            }
            catch (Exception e)
            {
                Manager.println("Cannot work with dice pool smaller than 2");;
            }
        }
    }



    public void r_target_effect(ArrayList<Integer> dice)
    {
        // random die from the pool
        for (int i = 0; i < usages; i++)
        {
            try
            { // effect
                int target = ((int) (Math.random() * (dice.size()-1)));
                dice.set(target, die_change(dice.get(target), -power));
            }
            catch (Exception e){
                Manager.println("cannot work with dice pool smaller than 2");;
            }
        }
    }



        // Functions called by the effects:

    static int die_change(int die, int value)
    {
        // used by other effects to change dice to another one
        int address = 0;
        for (int d : Balance.dice)
        {
            if (d == die)
                break;
            
            address++;
        }

        for (int i = 0; i < Math.abs(value); i++)
        {
            if (value>0)
            {
                if (Balance.dice.length-1 == address)
                    return die; // if dice is already at max value we cannot upgrade it
                address++;
            }
            else
            {
                if (0 == address)
                    return die; // if dice is already at min value we cannot downgrade it
                address--;
            }
            die = Balance.dice[address];
        }
        return die;
    }
}
