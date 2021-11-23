import java.util.ArrayList;
import java.util.Scanner;

public class Main // alpha 2.5 Game_manager Wiolarz
{
/*
Every file except for balance
removal of output and input --> all of those functions will go to a new class "manager.java"

*/

    public static int days;
    public static quest main_quest;

    static void overworld(ArrayList<hero> company)
    {
        int choice = 0;

        // days system
        days = 1;
        company.add(hero.create_mercenary(days));

        main_quest = new quest(days);


        ArrayList<item> item_list = economy.generate_items(days);
        ArrayList<location> world = explore.generate_world();


        while (choice != 9)
        {

            if (main_quest.days_to_complete <= 0)
            { // Time has run out DEFEAT
                manager.exit(main_quest.fail_story, "quest_fail");
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
                            company.add(hero.create_mercenary(days));
                            item_list = economy.generate_items(days);
                        }
            }

            //company.get(0).printing_all_stats();
            main_quest.print_info();
            // GAMEPLAY
            choice = manager.choice("Day " + days + "  1 info;   2 shop;  3 world;  9 Exit game");
            // END


            // list of locations
            switch (choice){
                case 1 -> company.get(0).printing_all_stats(); // info
                case 2 -> economy.shop(company.get(0), item_list); // shop
                case 3 -> {
                    if(explore.walking(company, world, days))
                    {

                    }
                    else
                    {
                        choice = 0; // player didn't explore anything
                        // TODO this system is bad, day system should be remade
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
                        manager.debug("unit cannot have 0 attack_speed");
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
        manager.debug("Start");

        //System.out.println(tester.location_creation());
        //System.out.println(tester.quest_creator());


        //System.out.println(tester.monster_generation());

        // player creation
        hero player = new hero();

        ArrayList<hero> company = new ArrayList<>();
        company.add(player);

        // start of the main gameplay loop
        overworld(company);
    }
}