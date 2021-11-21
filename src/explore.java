import java.util.ArrayList;

public class explore // alpha 2.2
{
    private static void walk(ArrayList<hero> company, location place, int day)
    { // takes
        int killed = 0;
        for (int i = 0; i < balance.events; i++)
        {
            int event = (int)(Math.random() * 10);



            if(event <= place.chest_chance)
            {
                output.println("You found a chest");
                chest(company, place.chest_gold);
            } else {
                output.println("You fight");
                if (fight(company, generate_enemy(place.level)))
                {
                    killed += place.level;
                }
            }
        }
        if (Main.main_quest.type.equals("boss") && Main.main_quest.target_place == place.id)
        {
            output.println("You encounter boss, his level: " + place.quest_level);
            ArrayList<monster> boss = new ArrayList<>();
            boss.add(new monster(place.quest_level));
            if(fight(company, boss))
            {
                output.println("You won");
                output.println("New quest: ");
                Main.main_quest = new quest(day);
                Main.main_quest.days_to_complete++;
            }
        }
        else if (Main.main_quest.type.equals("monsters") && Main.main_quest.target_place == place.id)
        {
            output.println("you have defeated " + killed + " monsters");
            Main.main_quest.monsters_to_kill -= killed;
            if(Main.main_quest.monsters_to_kill <= 0)
            {
                output.println("You won");
                output.println("New quest: ");
                Main.main_quest = new quest(day);
                Main.main_quest.days_to_complete++;
            }
        }

    }


// walking functions

    private static void chest(ArrayList<hero> company, int quality)
    { // event during exploring which rewards player
        company.get(0).gold += quality;
    }


    private static ArrayList<monster> generate_enemy(int level)
    {// event during exploring which challenges player
        ArrayList<monster> enemy = new ArrayList<>();

        if (level == 1)
        {
            enemy.add(new monster(1));
            return enemy;
        }
        else if (level == 2)
        {
            if ((int)(Math.random() * 2) == 1) //50% chance
            {
                enemy.add(new monster(2));
            }
            else
            {
                enemy.add(new monster(1));
                enemy.add(new monster(1));
            }
            return enemy;
        }

        switch ((int)(Math.random() * 3)) // * number of cases
        {
            case 0 ->
            { // horde
                for (int i = 0; i < level; i++)
                {
                    enemy.add(new monster(1));
                }
            }
            case 1 ->
            { // random
                int split = (int)(Math.random() * level-2) + 2; // 2 -> level-1

                int resource = level; //

                for (int i = 1; i < split; i++)
                {
                    int next_level = ((resource) / (split )) + 1 ;
                    enemy.add(new monster(next_level));
                    resource -= next_level;
                }
                enemy.add(new monster(resource));
            }
            case 2 ->
            { // single boss
                enemy.add(new monster(level));
            }

            default -> {output.debug("explore.generate_enemy switch case error");}
        }

        return enemy;
    }



// fight functions

    private static int attack(ArrayList<Integer> dice_pool)
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

    private static boolean graveyard_hero(ArrayList<hero> fighters)
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
    private static boolean graveyard_monster(ArrayList<monster> fighters)
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

    private static void turn_attacks_hero(ArrayList<hero> company, ArrayList<monster> enemy)
    {
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

        for (hero fighter : company)
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

    private static void turn_attacks_monster(ArrayList<monster> enemy, ArrayList<hero> company)
    {
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
                        output.println("You have won this fight");
                        company.get(0).experience(challenge);
                        return true;
                    }
                }
            }

            turn_attacks_monster(enemy, company);

            // checking dead in company
            if (graveyard_hero(company))
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
