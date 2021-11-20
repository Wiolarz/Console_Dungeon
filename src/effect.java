import java.util.ArrayList;

public class effect // alpha 2.2
{

    int usages;
    String type;
    int power;


    effect(int usage, String effect_type, int force)
    {
        usages = usage;
        type = effect_type;
        power = force;
    }


    public void print()
    {
        System.out.print("[");
        System.out.printf("%-10s", ("usages: " + usages));
        System.out.printf("%-12s", ("type: " + type));
        System.out.printf("%-10s", ("power: " + power + "]"));

    }
    public String short_print()
    {
        String text = usages + " " + type + " " + power + " ";
        //System.out.print(text);
        return text;
    }


    public void use(ArrayList<Integer> dices)
    {
        switch (type)
        {
            case "value" -> value_effect(dices);
            case "edge" -> edge_effect(dices);
            case "random" -> r_target_effect(dices);
        }
    }


    public void value_effect(ArrayList<Integer> dices){
        // effect  to a target, changing it to a "type" value
        int cur_usage = usages;
        for (int i = 0; i < dices.size() && cur_usage > 0; i++) {

            if (dices.get(i) == balance.dices[power])
            {
                // effect
                dices.set(i, balance.smallest_dice_value);

                cur_usage--;
            }
        }
    }



    public void edge_effect(ArrayList<Integer> dices){
        // we modify the highest values
        for (int i = 0; i < usages; i++) {
            try {
                // effect
                int idx = 0;
                int dices_idx = 0;
                int max = dices.get(0);
                
                for (int dice_val: dices){
                    if (max  < dice_val){
                        max = dice_val;
                        dices_idx = idx;
                    }
                    idx++;
                }

                dices.set(dices_idx, dice_change(dices.get(dices_idx), -power));
            } catch (Exception e){
                //System.out.println("Cannot work with dice pool smaller than 2");;
            }
        }
    }



    public void r_target_effect(ArrayList<Integer> dices){
        // random dice from the pool
        for (int i = 0; i < usages; i++) {
                try {
                    // effect
                    int target = ((int) (Math.random() * (dices.size()-1)));
                    dices.set(target, dice_change(dices.get(target), -power));
                } catch (Exception e){
                    //System.out.println("cannot work with dice pool smaller than 2");;
                }
            }
        }



        // Functions called by the effects:

    static int dice_change(int dice, int value){
        // used by other effects to change dice to another one
        int address = 0;
        for (int d : balance.dices){
            if (d == dice)
                break;
            
            address++;
        }

        for (int i = 0; i < Math.abs(value); i++) {

            if (value>0){
                if (balance.dices.length-1 == address)
                    return dice; // if dice is already at max value we cannot upgrade it
                address++;
                dice = balance.dices[address];
            } else {
                if (0 == address)
                    return dice; // if dice is already at min value we cannot downgrade it
                
                address--;
                dice = balance.dices[address];
            }
        }
        return dice;
    }


}
