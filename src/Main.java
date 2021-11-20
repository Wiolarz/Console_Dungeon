import java.util.ArrayList;
import java.util.Scanner;

public class Main // alpha 2.2
{
/*
Console Dungeon alpha version 2.2
Main branch

I Overworld
Better name generator for quests, and locations.

II Economy
Healing interface:
when you press 2 in shop, you have a UI for healing (1 exit, 2 - heal to full, 3 heal intelligently,
4 buy heal manually -> enter amount of hp you wish to restore

III Fighting
Maybe: choices during a fight?




Current problems:

How to measure balance?? Testing environment for balance changes

Random item creation only 1 type of effect is currently being made // fixed

Items in shop are most of the time not available for the player




Random ideas:

Folders inside of folders (items, locations)

*/



    static void overworld(hero player)
    {
        int choice = 0;

        // days system
        int days = 1;
        balance.main_quest = new quest(days);

        balance.main_quest.print_info();

        //System.out.println("At day 10 if you haven`t killed a warlock in dungeon, the world will be doomed");

        ArrayList<item> item_list = economy.generate_items(days);
        ArrayList<location> world = explore.generate_world();


        while (choice != 9)
        {

            if (balance.main_quest.days_to_complete <= 0)
            { // Time has run out DEFEAT
                output.println(balance.main_quest.fail_story);
                System.exit(3);
            }

            switch (choice)
            {
                case 0 -> {} // starting value, also assigned in case of wrong input
                case 1, 2 -> {}// if choice was not to explore the days are not passing
                default ->
                        {
                            balance.main_quest.days_to_complete--;
                            days++;
                            item_list = economy.generate_items(days);
                        }
            }



            output.println("Day " + days + " days to finish quest: " + balance.main_quest.days_to_complete +
                    "   1 info;   2 shop;  3 world;  9 Exit game");

            choice = input.choice();


            // list of locations
            switch (choice){
                case 1 -> player.printing_all_stats(); // info
                case 2 -> economy.shop(player, item_list); // shop
                case 3 -> {
                    if(explore.walking(player, world, days))
                    {

                    }
                    else
                    {
                        choice = 0; // player didn't explore anything
                        // TODO this system is bad, day system should be remade
                    };
                }
                case 8 -> player.cheats(); // :))
            }
        }
    }



    public static void main(String[] args)
    {
        output.println("Start");

        //System.out.println(tester.location_creation());
        //System.out.println(tester.quest_creator());


        //System.out.println(tester.monster_generation());

        // player creation
        hero dude = new hero();


        // start of the main gameplay loop
        overworld(dude);
    }
}