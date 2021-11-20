import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class explore // alpha 2.2
{
    static void walk(ArrayList<hero> company, location place, int day)
    { // takes
        for (int i = 0; i < balance.events; i++)
        {
            int event = (int)(Math.random() * 10);

            if ((event <= place.quest_enemy) && (balance.main_quest.target_place == place.id))
            {
                output.println("event related enemy");
                if(fight(company, generate_enemy(place.quest_level)))
                {
                    output.println("You won");
                    output.println("New quest: ");
                    balance.main_quest = new quest(day);
                    balance.main_quest.print_info();
                }
            }
            else if(event <= place.chest_chance)
            {
                output.println("You found a chest");
                chest(company, place.chest_gold);
            } else {
                output.println("You fight");
                fight(company, generate_enemy(place.level));
            }
        }
    }




    static void chest(ArrayList<hero> company, int quality)
    { // event during exploring which rewards player
        company.get(0).gold += quality;
    }


    static monster generate_enemy(int level)
    {// event during exploring which challenges player
        return new monster(level);
    }



    static int attack(ArrayList<Integer> dice_pool)
    {
        int score = unit.attack(dice_pool);

        // counting number of successes
        int success = 0;
        int difficulty = 6;
        while (difficulty < score)
        {
            score -= difficulty;
            difficulty = (int)(difficulty * 1.5);
            success++;
        }
        return success;
    }


    static boolean fight(ArrayList<hero> company, monster enemy)
    {
        enemy.printing_all_stats();

        // fighting

        while (enemy.HP > 0)
        {
            int choice = input.choice();

            switch (choice)
            {
                case 1: // escape attempt
                {
                    // basic roll for each fighter, if player side succeeds
                    //if success return false
                    break;
                }
                case 2:
                {
                    // player attacks
                    int success = attack(company.get(0).strategy.get(0));
                    for (int i = 0; i < success; i++)
                    {
                        enemy.HP -= 1;
                    }

                    break;
                }
            }
            
            if (enemy.HP <= 0)
            {
                break;
            }

            company.get(0).damage(attack(enemy.strategy.get(0)));
        }
        company.get(0).experience(enemy.level);
        return true;
    }


    static boolean walking(ArrayList<hero> company, ArrayList<location> world, int day)
    {
        int choice = 0;

        while (true)
        {
            output.print("1 Exit  ");
            int x = 2;
            for (location place : world)
            {
                output.print(x + " " + place.short_print() + "  ");
                x++;
            }
            output.println("");

            choice = input.choice();  // User input

            if (choice == 1) return false; // exit world map
            else if (choice > 1) // enter location
            {
                walk(company, world.get(choice - 2), day);
                return true;
            }
        }
    }



    static ArrayList<location> generate_world()
    {
        ArrayList<location> world = new ArrayList<>();
        for (int place = 1; place < balance.location_number+1; place++)
        {
            world.add(new location(place)); // location level
        }
        return world;
    }


}
