import java.util.ArrayList;
import java.util.Scanner;

public class economy // alpha 2.1
{
    static ArrayList<item> generate_items(int days){
        // generation of list of item objects to a shop
        ArrayList<item> items = new ArrayList<item>();
        int power = (balance.max_power/2) + days;
        if (power > balance.max_power)
            power = balance.max_power;

        int lowest_item_level = 1;

        for (int i = 0; i < balance.shop_items_amount; i++)
        { //TODO over time there should be less weaker items generated
            item thing = new item((int)(Math.random() * (power)) + lowest_item_level);
            items.add(thing);
        }

        return items;
    }


    private static ArrayList<ArrayList<item>> generate_folders(ArrayList<item> all_items)
    { // We split all items into x smaller groups
        int amount_of_folders = 3; // rest of the code works only for 3 folders
        ArrayList<ArrayList<item>> folders = new ArrayList<>(amount_of_folders);
        for(int i=0; i < amount_of_folders; i++)
        {
            folders.add(new ArrayList());
        }

        for (item thing : all_items)
        {
            if (thing.level < balance.max_power/4)  // weakest items
            {
                folders.get(0).add(thing);
            }
            else if (thing.level < balance.max_power/2.75) // medium items
            {
                folders.get(1).add(thing);
            }
            else // best items
            {
                folders.get(2).add(thing);
            }
        }
        return folders;
    }


    private static void open_folder(hero player, ArrayList<item> folder)
    {// interface to buy items from a smaller folder of items

        //printing possible choices
        output.println("Welcome to shop 1 exit; ");

        int x = -1;
        for (item thing : folder) {
            x++;
            ArrayList<Integer> info = new ArrayList<Integer>();

            for(int dice : thing.base_pool){
                info.add(dice);
            }
            ArrayList<String> info2 = new ArrayList<String>();
            for(effect spell : thing.magic_pool){
                info2.add(spell.short_print());
            }
            // printing details about items
            output.shop_print(thing, x, info, info2);
        }

        //System.out.println(inputs_shop(folder)); // it is so not working correctly, and first printf replaces it ^

        int choice = 0;
        while (choice != 9)
        {
            choice = input.choice();

            if (choice == 1) return;
            else if (choice > 1 && folder.get(choice - 2).Does_Fit(player))
            {
                if (player.pay(folder.get(choice - 2).level * balance.medium))
                {
                    player.item_change(folder.get(choice - 2));
                }
            }
        }

    }


    static ArrayList<String> inputs_shop(ArrayList<item> shop_list) // code not used, most likely to be deleted
    {
        ArrayList<String> options = new ArrayList<>();
        if (shop_list.size() < 7) // code would still work, it's just a design choice
        {
            int x = 1; // we added folders
            for(item thing : shop_list)
            {
                x += 1;
                //options.add(x +"  Item - " + roman_numbers(thing.level));
            }
        }
        else
        {
            System.out.println("folders_generator failed");
        }
        return options;
    }



    static void shop(hero player, ArrayList<item> item_list)
    { // general shop interface
        ArrayList<ArrayList<item>> folders = generate_folders(item_list);

        int choice = 0;
        while (choice != 9) {

            // Info for player
            System.out.print("Welcome to shop 1 exit; 2 medic's shop;");

            for (int i = 0; i < folders.size(); i++)
            {
                System.out.print(i+3 + " folder ");
            }
            System.out.println();


            choice = input.choice();  // Read user input


            if (choice == 1)
            {
                choice = 9; // exit
            }
            else if (choice == 2)
            {
                medic(player); //
            }
            else
            {
                open_folder(player, folders.get(choice-3));
            }
        }
    }


    static void medic(hero player)
    {
        int[][] healing  = {{2, 3}, {3, 5}, {4, 6}, {6, 8}};

        output.print("Welcome to medic's shop 1 exit  2 max_heal  3 auto_heal  ");
        int x = 4;
        for(int[] item : healing)
        {
            output.print(x++ + " [heal: " + item[0] + " price: " + item[1] + "] ");
        }

        output.println("");

        int choice = input.choice();  // User input

        // if (choice == 1)  {} // exit medic shop
        if(choice == 2)
        {  // auto-heal to max possible HP
            autoHeal(player, healing);
            if (player.HP < player.max_HP && player.gold >= healing[healing.length-1][1])
            {
                player.pay(healing[0][1]);
                player.heal(healing[0][0]);
            }
            output.println("gold: " + player.gold + " HP: " + player.HP);
        }
        else if (choice == 3)
        { // auto-heal to without wasting money on last points of hp
            autoHeal(player, healing);
            output.println("gold: " + player.gold + " HP: " + player.HP);
        }
        else if (choice > 3 && choice < 1 + healing.length) //enter healing manually
        {
            if(player.pay(healing[choice - 4][1]))
            {
                player.heal(healing[choice - 4][0]);
            }
            else
            {
                output.println("Not enough money");
            }
        }
        else
        {
            output.println("Wrong choice");
        }
    }


    static void autoHeal(hero player, int[][] healing)
    {
        for (int item = healing.length - 1; item >= 0; item--)
        {
            while(player.HP + healing[item][0] <= player.max_HP && player.gold >= healing[item][1])
            {
                player.pay(healing[item][1]);
                player.heal(healing[item][0]);
            }
        }
    }
}
