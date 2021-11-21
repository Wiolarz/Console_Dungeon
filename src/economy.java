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


    static ArrayList<String> inputs_shop(ArrayList<item> shop_list){
        // code not used, most likely to be deleted
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
            System.out.print("Welcome to shop 1 exit; 2 heal(3) 1g;");

            for (int i = 0; i < folders.size(); i++)
            {
                System.out.print(i+3 + " folder ");
            }
            System.out.println();


            choice = input.choice();  // Read user input


            if (choice == 1){
                choice = 9; // exit
            } else if (choice == 2 && player.pay(1)){
                medic(player); //
            } else
            {
                open_folder(player, folders.get(choice-3));
            }
        }
    }

    static boolean medic(hero player) {
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();  // User input

        int[][] healing  = {{1, 3}, {2, 5}, {3, 6}, {5, 8}};

        if (choice == 1) return false; // exit medic shop
        else if(choice == 2) {  // auto-heal to max possible HP
            autoHeal(player, healing);
            return true;
        }
        else if (choice > 2 && choice < 1 + healing.length) //enter healing manually
        {
            if(player.pay(healing[choice - 3][1])) {
                heal(player, healing[choice - 3][0]);
                return true;
            } else {
                System.out.println("Not enough money");
            }
        }
        return false;
    }

    static void heal(hero player, int heal) {
        if(player.HP + heal <= player.max_HP) player.HP += heal;
        else System.out.println("not enough gold");
    }

    static void autoHeal(hero player, int[][] healing)
    {
        while(player.gold >= healing[0][1] && player.HP < player.max_HP) {
            for (int item = healing.length - 1; item >= 0; item--) {
                while(player.HP + healing[item][0] <= player.max_HP && player.pay(healing[item][1])) {
                    player.HP += healing[item][0];
                }
            }
        }
        output.println("gold: " + player.gold + " HP: " + player.HP);
    }
}
