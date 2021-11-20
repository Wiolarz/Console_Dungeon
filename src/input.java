import java.util.Scanner;

public class input
{
    static int choice()
    {
        Scanner keyboard = new Scanner(System.in);
        int in;
        try
        {
            in = keyboard.nextInt();
        }
        catch (Exception e)
        {
            in = 0;
            output.println("Wrong input, Enter integer");
        }

        keyboard.nextLine(); // catching enter
        return in;
    }
}
