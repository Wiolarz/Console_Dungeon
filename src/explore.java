import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class explore // alpha 2.2
{
    static void walk(hero player, location place, int day)
    { // takes

        for (int i = 0; i < balance.events; i++)
        {
            int event = (int)(Math.random() * 10);

            if ((event <= place.quest_enemy) && (balance.main_quest.target_place == place.id))
            {
                output.println("event related enemy");
                if(fight(player, generate_enemy(place.quest_level)))
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
                chest(player, place.chest_gold);
            } else {
                output.println("You fight");
                fight(player, generate_enemy(place.level));
            }
        }
    }




    static void chest(hero player, int quality)
    { // event during exploring which rewards player
        player.gold += quality;
    }


    static monster generate_enemy(int level)
    {// event during exploring which challenges player
        return new monster(level);
    }



    static boolean round(hero player, monster enemy, int turn)
    {
        ArrayList<Integer> p = player.strategy.get(turn);
        ArrayList<Integer> e = enemy.strategy.get(turn);

        p = enemy.effect(p, turn); // problem, there is no, round aligned effects possible
        e = player.effect(e, turn);


        int p_score = unit.attack(p);
        int e_score = unit.attack(e);

        if(turn != 2){
            return p_score >= e_score;
        } else {
            return p_score > e_score;
        }
    }


    static boolean fight(hero player, monster enemy)
    {// number of won rounds, if player wins 2 rounds he wins
        int p = 0;

        enemy.printing_all_stats();

        // fighting
        // each side rolls their dices and we check who had higher score
        int[] score = {0, 0};
        for (int turn = 0; true; turn++)
        {
            if (round(player, enemy, turn))
            {
                score[0]++;
            }
            else
            {
                score[1]++;
            }
            if (score[0] == 2)
            { // when player reaches 2 wins,
                output.println("Final score: " + Arrays.toString(score));
                player.experience(enemy.level);
                return true;
            } else if (score[1] == 2)
            {
                output.println("Final score: " + Arrays.toString(score));
                player.damage(enemy.level);
                return false;
            }
        }

    }


    static boolean walking(hero player, ArrayList<location> world, int day)
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
                walk(player, world.get(choice - 2), day);
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
