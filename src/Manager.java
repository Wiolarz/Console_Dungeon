import java.util.ArrayList;
import java.util.Scanner;

public class Manager
{
    static void debug(String txt)
    {
        System.out.println(txt);
    }

    static void error(String txt)
    {
        System.out.println(txt);
    }

    static String roman_numbers(int value)
    {     // Conversion of int into a roman number (works correctly to a max number of 39)
        StringBuilder result = new StringBuilder();
        String x;
        while (value > 10)
        {
            result.append("X");
            value-=10;
        }
        x = switch (value)
                {
                    case 0-> "0"; // it's not correct but works
                    case 1-> "I";
                    case 2-> "II";
                    case 3-> "III";
                    case 4-> "IV";
                    case 5-> "V";
                    case 6-> "VI";
                    case 7-> "VII";
                    case 8-> "VIII";
                    case 9-> "IX";
                    case 10-> "X";
                    default -> "?";
                };
        result.append(x);
        x = result.toString();
        return x;
    }


    static void printf(String format, String txt)
    {
        System.out.printf(format, txt);
    }

    static void println()
    {
        System.out.println();
    }

    static void println(String txt)
    {
        System.out.println(txt);
    }

    static void print(String txt)
    {
        System.out.print(txt);
    }


    static void shop(int folders_number)
    {
        System.out.println("Welcome to shop 1 exit; 2 medic's shop;");

        for (int i = 0; i < folders_number; i++)
        {
            System.out.print(i+3 + " folder ");
        }
        System.out.println();
    }

    static void shop_folder(ArrayList<Item> folder)
    {
        System.out.println("Welcome to shop 1 exit; ");
        int x = -1;
        for (Item thing : folder) {
            x++;
            ArrayList<Integer> info = new ArrayList<Integer>();

            for(int dice : thing.base_pool){
                info.add(dice);
            }
            ArrayList<String> info2 = new ArrayList<String>();
            for(Effect spell : thing.magic_pool){
                info2.add(spell.short_print());
            }
            // printing details about items
            System.out.printf("%-16s", x+2 + " level: " + roman_numbers(thing.level));
            System.out.printf("%-12s", ("price: " + thing.level * Balance.medium));
            System.out.printf("%-34s", ("STR_req: " + thing.STR_req + " AG_req: " + thing.AG_req + " INT_req: " + thing.INT_req));
            System.out.printf("%-70s", ("base: " + info));
            System.out.printf("%-70s", ("Magic: " + info2));
            System.out.println();
        }
    }

    static void medic(int[][] healing)
    {
        System.out.print("Welcome to medic's shop 1 exit  2 max_heal  3 auto_heal  ");
        int x = 4;
        for(int[] item : healing)
        {
            System.out.print(x++ + " [heal: " + item[0] + " price: " + item[1] + "] ");
        }
        System.out.println();
    }


    static int choice(String txt)
    {
        System.out.println(txt);
        Scanner keyboard = new Scanner(System.in);
        int input;
        try
        {
            input = keyboard.nextInt();
        }
        catch (Exception e)
        {
            input = 0;
            println("Wrong input, Enter integer");
        }

        keyboard.nextLine(); // catching enter
        return input;
    }




    static void exit(String txt, String type)
    {
        System.out.println(txt);

        switch (type)
        {
            case "quest_fail" -> System.exit(3);
            case "fight" -> System.exit(666);
            default -> System.exit(1); // unknown reason
        }
    }
}