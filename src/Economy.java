import java.util.ArrayList;

public class Economy // alpha 2.1
{
    static ArrayList<Item> generate_items(int days){
        // generation of list of item objects to a shop
        ArrayList<Item> items = new ArrayList<>();
        int power = (Balance.max_power/2) + days;
        if (power > Balance.max_power)
            power = Balance.max_power;

        int lowest_item_level = 1;

        for (int i = 0; i < Balance.shop_items_amount; i++)
        { //TODO over time there should be less weaker items generated
            Item thing = new Item((int)(Math.random() * (power)) + lowest_item_level);
            items.add(thing);
        }

        return items;
    }


    private static ArrayList<ArrayList<Item>> generate_folders(ArrayList<Item> all_items)
    { // We split all items into x smaller groups
        int amount_of_folders = 3; // rest of the code works only for 3 folders
        ArrayList<ArrayList<Item>> folders = new ArrayList<>(amount_of_folders);
        for(int i=0; i < amount_of_folders; i++)
        {
            folders.add(new ArrayList());
        }

        for (Item thing : all_items)
        {
            if (thing.level < Balance.max_power/4)  // weakest items
            {
                folders.get(0).add(thing);
            }
            else if (thing.level < Balance.max_power/2.75) // medium items
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


    private static void open_folder(Hero player, ArrayList<Item> folder)
    {// interface to buy items from a smaller folder of items

        //printing possible choices
        Manager.shop_folder(folder);

        int choice = 0;
        while (choice != 9)
        {
            choice = Manager.choice("");

            if (choice == 1) return;
            else if (choice > 1 && folder.get(choice - 2).Does_Fit(player))
            {
                if (player.pay(folder.get(choice - 2).level * Balance.medium))
                {
                    player.item_change(folder.get(choice - 2));
                }
            }
        }

    }


    static void shop(Hero player, ArrayList<Item> item_list)
    { // general shop interface
        ArrayList<ArrayList<Item>> folders = generate_folders(item_list);

        int choice = 0;
        while (choice != 1) {
            // GAMEPLAY
            Manager.shop(folders.size());
            choice = Manager.choice("");  // Read user input
            // END

            if (choice == 2)
            {
                medic(player); //
            }
            else if (choice > 2 && choice < folders.size()+3)
            {
                open_folder(player, folders.get(choice-3));
            }
        }
    }


    static void medic(Hero player)
    {
        int[][] healing  = {{2, 3}, {3, 5}, {4, 6}, {6, 8}};


        Manager.medic(healing);
        int choice = Manager.choice("");  // User input

        // if (choice == 1)  {} // exit medic shop
        if(choice == 2)
        {  // auto-heal to max possible HP
            autoHeal(player, healing);
            if (player.HP < player.max_HP && player.gold >= healing[healing.length-1][1])
            {
                player.pay(healing[0][1]);
                player.heal(healing[0][0]);
            }
            Manager.println("gold: " + player.gold + " HP: " + player.HP);
        }
        else if (choice == 3)
        { // auto-heal to without wasting money on last points of hp
            autoHeal(player, healing);
            Manager.println("gold: " + player.gold + " HP: " + player.HP);
        }
        else if (choice > 3 && choice < 1 + healing.length) //enter healing manually
        {
            if(player.pay(healing[choice - 4][1]))
            {
                player.heal(healing[choice - 4][0]);
            }
            else
            {
                Manager.println("Not enough money, your gold: "
                        + player.gold + " required: " + healing[choice - 4][1]);
            }
        }
    }


    static void autoHeal(Hero player, int[][] healing)
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
