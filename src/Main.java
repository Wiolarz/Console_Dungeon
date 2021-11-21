import java.util.ArrayList;
import java.util.Scanner;

public class Main // alpha 2.3 quest - Wiolarz
{
/*
Console Dungeon alpha version 2.4 New quest system QS_v1.0 - Wiolarz

Changes in files:
Most likely main quest references (Main.java explore.java)
quest.java


Quest types ideas:
    //1 Support local medic - Heal yourself or your mercenary for X amount
    2 Pay taxes - Get x gold, then interact with
    3 Kill monsters in X - kill Y amount of levels of monsters in that location
    4 Kill boss
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
                output.println(main_quest.fail_story);
                System.exit(3);
            }

            switch (choice)
            {
                case 0 -> {} // starting value, also assigned in case of wrong input
                case 1, 2 -> {}// if choice was not to explore the days are not passing
                default ->
                        {
                            main_quest.days_to_complete--;
                            main_quest.check_quest(company.get(0)); //
                            days++;
                            company.add(hero.create_mercenary(days));
                            item_list = economy.generate_items(days);
                        }
            }


            main_quest.print_info();
            output.println("Day " + days + "  1 info;   2 shop;  3 world;  9 Exit game");

            choice = input.choice();


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
                case 8 -> company.get(0).cheats(); // :))
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
        hero player = new hero();

        player.printing_all_stats();
        ArrayList<hero> company = new ArrayList<>();
        company.add(player);
        // start of the main gameplay loop
        overworld(company);
    }
}