import java.util.ArrayList;

public class Explore // alpha 2.2
{
    private static void walk(ArrayList<Hero> company, Location place, int day)
    { // takes
        int killed = 0;
        for (int i = 0; i < Balance.events; i++)
        {
            int event = (int)(Math.random() * 10);



            if(event <= place.chest_chance)
            {
                Manager.println("You found a chest");
                chest(company, place.chest_gold);
            } else {
                Manager.println("You fight");
                if (fight(company, generate_enemy(place.level)))
                {
                    killed += place.level;
                }
            }
        }
        if (Main.main_quest.type.equals("boss") && Main.main_quest.target_place == place.id)
        {
            Manager.println("You encounter boss, his level: " + place.quest_level);

            ArrayList<Monster> boss = new ArrayList<>();
            boss.add(new Monster(place.quest_level));
            if(fight(company, boss))
            {
                Main.main_quest = new Quest(day);
                Main.main_quest.days_to_complete++;
            }
        }
        else if (Main.main_quest.type.equals("monsters") && Main.main_quest.target_place == place.id)
        {
            Manager.println("you have defeated " + killed + " monsters");
            Main.main_quest.monsters_to_kill -= killed;
            if(Main.main_quest.monsters_to_kill <= 0)
            {
                Main.main_quest = new Quest(day);
                Main.main_quest.days_to_complete++;
            }
        }

    }


// walking functions

    private static void chest(ArrayList<Hero> company, int quality)
    { // event during exploring which rewards player
        company.get(0).gold += quality;
    }


    private static ArrayList<Monster> generate_enemy(int level)
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

    private static int attack(ArrayList<Integer> dice_pool)
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

    private static boolean graveyard_hero(ArrayList<Hero> fighters)
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

    private static boolean graveyard_monster(ArrayList<Monster> fighters)
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

    private static void turn_attacks_hero(ArrayList<Hero> company, ArrayList<Monster> enemy)
    {
        // before player attack we remove effects from enemy
        for (Monster fighter : enemy)
        {
            fighter.turn_pool = fighter.dice_pool;
        }
        // before player attacks we apply effects from players attack to their attacks
        for (Hero fighter : company)
        {
            fighter.generate_strategy();
        }

        for (Hero fighter : company)
        {
            for (int action = 0; action < fighter.attack_speed; action++)
            { // here could be a choice to perform different action instead
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
    }
    private static void turn_attacks_monster(ArrayList<Monster> enemy, ArrayList<Hero> company)
    {
        // before enemy attack we remove effects from players
        for (Hero fighter : company)
        {
            fighter.turn_pool = fighter.dice_pool;
        }
        // before enemy attack we aplly effects from players attack to their attacks
        for (Monster fighter : enemy)
        {
            fighter.generate_strategy();
        }


        for (Monster fighter : enemy)  // monster attacks
        {
            for (int action = 0; action < fighter.attack_speed; action++)
            { // here could be a choice to perform different action instead
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
    }


    static boolean fight(ArrayList<Hero> company, ArrayList<Monster> enemy)
    {
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
                    turn_attacks_hero(company, enemy);

                    // checking dead enemy
                    if (graveyard_monster(enemy))
                    {
                        Manager.println("You have won this fight");
                        company.get(0).experience(challenge);
                        return true;
                    }
                }
            }

            turn_attacks_monster(enemy, company);

            // checking dead in company
            if (graveyard_hero(company))
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
            walk(company, world.get(choice - 2), day);
            return true;
        }
        return false;
    }



    static ArrayList<Location> generate_world()
    {
        ArrayList<Location> world = new ArrayList<>();
        for (int place = 1; place < Balance.location_number+1; place++)
        {
            world.add(new Location(place)); // location level
        }
        return world;
    }


}
