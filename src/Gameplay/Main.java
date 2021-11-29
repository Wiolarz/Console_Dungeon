package Gameplay;

import Objects.*;
import Technical.*;

import java.util.ArrayList;

public class Main // alpha 2.6 advanced location system introduction of polymorphism
{
/*

*/

    public static int days;
    public static Quest main_quest;

    static void overworld(ArrayList<Hero> company)
    {
        int choice = 0;

        // days system
        days = 1;
        company.add(Hero.create_mercenary(days));

        main_quest = new Quest(days);


        ArrayList<Item> item_list = Economy.generate_items(days);
        ArrayList<Location> world = Explore.generate_world();


        while (choice != 9)
        {

            if (main_quest.days_to_complete <= 0)
            { // Time has run out DEFEAT
                Manager.exit(main_quest.fail_story, "quest_fail");
            }

            switch (choice)
            {
                case 0 -> {} // starting value, also assigned in case of wrong input
                case 1, 2 -> {}// if choice was not to explore the days are not passing
                case 5, 6, 7 -> {} // debug days are not passing
                default -> // 9 would be used to pass days
                        {
                            main_quest.days_to_complete--;
                            main_quest.check_quest(company.get(0)); //
                            days++;
                            company.add(Hero.create_mercenary(days));
                            item_list = Economy.generate_items(days);
                        }
            }

            //company.get(0).printing_all_stats();
            main_quest.print_info();
            // GAMEPLAY
            choice = Manager.choice("Day " + days + "  1 info;   2 shop;  3 world;  9 Exit game");
            // END


            // list of locations
            switch (choice){
                case 1 -> company.get(0).printing_all_stats(); // info
                case 2 -> Economy.shop(company.get(0), item_list); // shop
                case 3 ->
                {
                    if(Explore.walking(company, world, days))
                    {

                    }
                    else
                    {
                        choice = 0; // player didn't explore anything
                        // TODO day system should be remade
                    };
                }
                case 5 ->
                {
                    try
                    {
                        company.get(0).attack_speed -= 1;
                        company.get(0).generate_strategy();
                    }
                    catch (Exception e)
                    {
                        Manager.debug("unit cannot have 0 attack_speed");
                        company.get(0).attack_speed = 1;
                    }
                }
                case 6 ->
                {
                    company.get(0).attack_speed += 1;
                    company.get(0).generate_strategy();
                }
                case 7 ->
                {
                    company.get(0).HP -= 1;
                }
                case 8 -> company.get(0).cheats(); // :))
            }
        }
    }



    public static void main(String[] args)
    {
        Manager.debug("Start");


        // player creation
        Hero player = new Hero();

        ArrayList<Hero> company = new ArrayList<>();
        company.add(player);

        // start of the main gameplay loop
        overworld(company);
    }
}