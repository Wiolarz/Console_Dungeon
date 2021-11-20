import java.util.ArrayList;

public class output
{
    static void debug(String txt)
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




    static void println(String txt)
    {
        System.out.println(txt);
    }

    static void print(String txt)
    {
        System.out.print(txt);
    }



    static void shop_print(item thing, int x, ArrayList<Integer> info, ArrayList<String> info2)
    {
        System.out.printf("%-16s", x+2 + " level: " + roman_numbers(thing.level));
        System.out.printf("%-12s", ("price: " + thing.level * balance.medium));
        System.out.printf("%-34s", ("STR_req: " + thing.STR_req + " AG_req: " + thing.AG_req + " INT_req: " + thing.INT_req));
        System.out.printf("%-70s", ("base: " + info));
        System.out.printf("%-70s", ("Magic: " + info2));
        System.out.println();
    }
}
