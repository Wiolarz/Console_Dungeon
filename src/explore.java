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


    static ArrayList<monster> generate_enemy(int level)
    {// event during exploring which challenges player
        ArrayList<monster> enemy = new ArrayList<>();
        enemy.add(new monster(level));
        return enemy;
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


    static boolean fight(ArrayList<hero> company, ArrayList<monster> enemy)
    {
        // measure the challenge level
        int challenge = 0;
        for (monster fighter : enemy)
        {
            challenge += fighter.level;
        }


        //enemy.get(0).printing_all_stats();

        // fighting

        for (int rounds = 1; rounds < 50; rounds++)
        {
            int choice = input.choice();

            // before player attack we remove effects from enemy
            for (monster fighter : enemy)
            {
                fighter.turn_pool = fighter.dice_pool;
            }
            // before player attacks we apply effects from players attack to their attacks
            for (hero fighter : company)
            {
                fighter.generate_strategy();
            }

            switch (choice)
            {
                case 1 -> // escape attempt
                {
                    // basic roll for each fighter, if player side succeeds
                    //if success return false
                    return false;
                }

                case 2 ->
                {
                    // player attacks
                    // random targets for now
                    for (hero fighter : company)
                    {
                        for (int action = 0; action < fighter.attack_speed; action++)
                        {
                            int target = (int) (Math.random() * enemy.size());

                            int success = attack(fighter.strategy.get(action));

                            for (int i = 0; i < success; i++)
                            {
                                enemy.get(target).HP -= 1;

                            }
                            if (success > 0)
                            {
                                fighter.effect(enemy.get(target).turn_pool, action);
                            }
                        }

                    }
                    // checking dead enemy

                    for (int fighter = enemy.size() - 1; fighter >= 0; fighter--)
                    {
                        if (enemy.get(fighter).HP <= 0)
                        {
                            enemy.remove(fighter);
                        }
                    }


                    if (enemy.size() == 0)
                    {
                        output.println("You have won this fight");
                        company.get(0).experience(challenge);
                        return true;
                    }
                }
            }

            // before enemy attack we remove effects from players
            for (hero fighter : company)
            {
                fighter.turn_pool = fighter.dice_pool;
            }
            // before enemy attack we aplly effects from players attack to their attacks
            for (monster fighter : enemy)
            {
                fighter.generate_strategy();
            }

            for (monster fighter : enemy)  // monster attacks
            {
                for (int action = 0; action < fighter.attack_speed; action++)
                {
                    int target = (int)(Math.random() * company.size());

                    int success = attack(fighter.strategy.get(action));

                    for (int i = 0; i < success; i++)
                    {
                        company.get(target).HP -= 1;

                    }
                    if (success > 0)
                    {
                        fighter.effect(company.get(target).turn_pool, action);
                    }


                }

            }

            // checking dead in company

            for (int fighter = company.size()-1; fighter >= 0; fighter--)
            {
                if (company.get(fighter).HP <= 0)
                {
                    company.remove(fighter);
                }
            }

            if (company.size() == 0)
            {
                output.println("Your company has been defeated GAME OVER");
                System.exit(666);
            }

        }
        return false; // a draw
    }


    static boolean walking(ArrayList<hero> company, ArrayList<location> world, int day)
    {
        int choice;

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
