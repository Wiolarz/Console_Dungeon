import java.util.ArrayList;

public class Explore // alpha 2.2
{
// walking functions

    static void chest(ArrayList<Hero> company, int quality)
    { // event during exploring which rewards player
        Manager.println("You found a chest");
        company.get(0).gold += quality;
    }


    static ArrayList<Monster> generate_enemy(int level)
    {// event during exploring which challenges player
        ArrayList<Monster> enemy = new ArrayList<>();

        if (level == 1)
        {
            enemy.add(new Monster(1));
            return enemy;
        }
        else if (level == 2)
        {
            if ((int)(Math.random() * 2) == 1) //50% chance
            {
                enemy.add(new Monster(2));
            }
            else
            {
                enemy.add(new Monster(1));
                enemy.add(new Monster(1));
            }
            return enemy;
        }

        switch ((int)(Math.random() * 3)) // * number of cases
        {
            case 0 ->
            { // horde
                for (int i = 0; i < level; i++)
                {
                    enemy.add(new Monster(1));
                }
            }
            case 1 ->
            { // random
                int split = (int)(Math.random() * level-2) + 2; // 2 -> level-1

                int resource = level; //

                for (int i = 1; i < split; i++)
                {
                    int next_level = ((resource) / (split )) + 1 ;
                    enemy.add(new Monster(next_level));
                    resource -= next_level;
                }
                enemy.add(new Monster(resource));
            }
            case 2 ->
            { // single boss
                enemy.add(new Monster(level));
            }

            default ->
            {
                Manager.debug("explore.generate_enemy switch case error");
            }
        }

        return enemy;
    }



// fight functions

    private static int action_attack(ArrayList<Integer> dice_pool)
    {
        int score = Unit.attack(dice_pool);

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

    private static <X extends Unit<X>> boolean graveyard(ArrayList<X> fighters)
    {
        for (int fighter = fighters.size() - 1; fighter >= 0; fighter--)
        {
            if (fighters.get(fighter).HP <= 0)
            {
                fighters.remove(fighter);
            }
        }
        return fighters.size() == 0;
    }



    private static <X extends Unit<X>> void turn_reset(ArrayList<X> attacker, ArrayList<X> defender)
    {
        // before attack we remove effects from defender
        for (X fighter : defender)
        {
            fighter.turn_pool = fighter.dice_pool;
        }
        // before player attacks we apply effects from players attack to their attacks
        for (X fighter : attacker)
        {
            fighter.generate_strategy();
        }
    }



    private static <X extends Unit<X>> void turn_attacks(ArrayList<X> attacker, ArrayList<X> defender)
    {
        turn_reset(new ArrayList<>(attacker), new ArrayList<>(defender));


        for (X fighter : attacker)  // monster attacks
        {
            for (int action = 0; action < fighter.attack_speed; action++)
            { // here could be a choice to perform different action instead
                int target = (int)(Math.random() * defender.size());

                int success = action_attack(fighter.strategy.get(action));

                for (int i = 0; i < success; i++)
                {
                    defender.get(target).HP -= 1;
                }

                if (success > 0)
                {
                    fighter.effect(defender.get(target).turn_pool, action);
                }
            }
        }
    }


    static boolean fight(ArrayList<Hero> company, ArrayList<Monster> enemy)
    {
        Manager.println("You fight");
        // measure the challenge level
        int challenge = 0;
        for (Monster fighter : enemy)
        {
            challenge += fighter.level;
        }

        //enemy.get(0).printing_all_stats();

        // fighting

        for (int rounds = 1; rounds < 50; rounds++)
        {
            int choice = Manager.choice("");

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
                    turn_attacks(new ArrayList<>(company), new ArrayList<>(enemy));

                    // checking dead enemy
                    if (graveyard(new ArrayList<>(enemy)))
                    {
                        Manager.println("You have won this fight");
                        company.get(0).experience(challenge);
                        return true;
                    }
                }
            }

            turn_attacks(new ArrayList<>(enemy), new ArrayList<>(company));

            // checking dead in company
            if (graveyard(new ArrayList<>(company)))
            {
                Manager.exit("Your company has been defeated GAME OVER", "fight");
            }

        }
        return false; // a draw
    }


    static boolean walking(ArrayList<Hero> company, ArrayList<Location> world, int day)
    {
        // GAMEPLAY
        Manager.print("1 Exit ");
        int x = 2;
        for (Location place : world)
        {
            Manager.print(x + " " + place.short_print() + " ");
            x++;
        }
        int choice = Manager.choice("");  // User input
        // END

        //if (choice == 1) return false; // exit world map
        if (choice > 1 && choice < world.size() + 2) // enter location
        {
            world.get(choice - 2).enter(company, day);
            return true;
        }
        return false;
    }



    static ArrayList<Location> generate_world()
    {
        ArrayList<Location> world = new ArrayList<>();
        for (int place = 1; place < Balance.location_number + 1; place++)
        {
            world.add(new Location(place)); // location level
        }
        return world;
    }
}
