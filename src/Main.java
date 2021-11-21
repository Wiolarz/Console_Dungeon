import java.util.ArrayList;
import java.util.Scanner;

public class Main // alpha 2.2 multiple enemy combat - Wiolarz
{
/*
Console Dungeon alpha version 2.2 multiple enemy combat - Wiolarz

Instead of fighting 3 rounds with a single enemy, gameplay will mostly consist of managing multiple characters combat:
Player will be in a squad, and most often he will fight against groups of enemy's


Basic premise:
instead of splitting dices into turns of combat, dices are split into attacks which are assigned to different targets,

Assigning more dices to a single attack makes it succeed more often despite enemy protection, but at the same time
he needs to be able to manage to fend off multiple foes

Advanced ideas:
Those attack slots would not actually be attack slots but action slots, which could have been something that has an
assigned effect--->like attack, block, shield, power attack. Those could maybe affect multiple foes or just player,
and also have a requirement to succeed and also have multiple success levels EXAMPLE:
Shield - min 4 each next *1,5 (6, 9, 13, 19) each success grants 1 hit damage less for the next turn




PROBLEMS:
someone who attacks first, most of the time applies effects making enemy less likely to apply their effects back
making each fight have a big advantage for the attacker


*/



    static void overworld(ArrayList<hero> company)
    {
        int choice = 0;

        // days system
        int days = 1;
        company.add(hero.create_mercenary(days));

        balance.main_quest = new quest(days);


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
                            company.add(hero.create_mercenary(days));
                            item_list = economy.generate_items(days);
                        }
            }


            balance.main_quest.print_info();
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