import java.util.ArrayList;

public class balance // alpha 2.1
{
    // set of dices used in a game, its number should not be smaller than 1
    // length min(2) else effects will not work
    // sorted else effects will not work
    public static int[] dices = {4, 6, 8, 10, 12, 20}; //4, 6, 8, 10, 12, 20

    // max player stats = dices.length
    public static int max_power = dices.length * 3;



    public static int smallest_dice_value = 1;

    // main balance values to create gameplay
    public static int powerful = 7;
    public static int strong = 5;
    public static int medium = 3;
    public static int weak = 1;

    public static int rare = 30; // % chance for something rare to occur

    public static int events = 5; // number of events in locations

    public static int levelup_speed = 2;

    // less used ones
    public static int shop_items_amount = 15;


    public static int week = 7;





    public static int location_number = 7; // max 8
    public static ArrayList<String> location_names = new ArrayList<>();






    static void split_dices()
    { // splitting dices into groups based on their highest multiplier
        // kinda works, most likely useless

        ArrayList<ArrayList<Integer>> groups = new ArrayList<>((dices[dices.length-1]) / 2);

        for (int i = 1; i <= (dices[dices.length-1]) / 2; i++)
        {
            groups.add(new ArrayList<>());
            System.out.print(i + " ");
            for (int dice : balance.dices)
            {
                if (dice % i == 0)
                {
                    groups.get(i-1).add(dice);
                }
            }
            System.out.println(groups.get(i-1));
        }
        ArrayList<Integer> existing = new ArrayList<>();
        ArrayList<ArrayList<Integer>> fin = new ArrayList<>(groups.size()-1);
        for (int i  = groups.size(); i > 0; i--)
        {
            fin.add(new ArrayList<Integer>());
        }

        for (int i  = groups.size(); i > 0; i--)
        {


            for (int dice : groups.get(i-1))
            {
                boolean  existed = false;
                for (int d : existing)
                {
                    if (dice == d) existed = true;
                }

                if (!existed) fin.get(i-1).add(dice);
            }


            for (int dice : groups.get(i-1))
            {
                existing.add(dice);
            }

            System.out.println(fin.get(i-1));
        }
        System.out.println(fin);

    }
}
