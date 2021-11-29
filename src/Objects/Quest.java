package Objects;

import Gameplay.*;
import Technical.*;

public class Quest
{
    // basic complexity

    public int days_to_complete;
    int target_place;


    int quest_gold;

    int monsters_to_kill;

    String type;

    String story;
    public String fail_story;

    public Quest(int current_day)
    {
        random_quest(current_day);
    }


    private static int[] location_and_time(int current_day)
    {
        int location;
        int time;

        // days to complete quest
        int base = Balance.weak;
        int[][] difficulty = {
                {base * Balance.week, Balance.powerful, 3},
                {base * Balance.medium, Balance.strong, 2},
                {base * Balance.strong, Balance.medium, 1},
                {base * Balance.powerful, Balance.week, 1}
        };

        for (int[] level : difficulty)
        {
            if (current_day < level[0])
            {
                location = (int)(Math.random() * (Balance.location_number / level[2]));
                time = level[1];
                return new int[]{location, time, level[0]};
            }
        }
        time = 1;
        location = Balance.location_number-1; // last spot
        return new int[]{location, time, base * Balance.powerful};
    }

    public void random_quest(int current_day)
    {
        int[] loc_time = location_and_time(current_day);
        days_to_complete = loc_time[1];

        switch ((int)(Math.random() * 3))
        {
            case 0 -> // boss to kill
            {
                type = "boss";
                target_place = loc_time[0];

                story = "kill a warlock in dungeon, he is currently preparing to summon a demo";
                fail_story = "Time has run out, and warlock has managed to summon a demon, the world is doomed";
            }
            case 1 -> // gold to pay
            {
                story = "pay poor villagers taxes. Gather gold in order to help them!";
                fail_story = "Time has run out, and village was burned to the ground by our glorious king";

                type = "gold";
                quest_gold = Balance.medium * current_day;
            }
            case 2 -> // monsters to kill
            {
                story = " eradicate pesky monsters who are annoying our merchants,";
                fail_story = "Time has run out, and merchants have traveled to a different village";

                type = "monsters";
                target_place = loc_time[0];
                monsters_to_kill = loc_time[2];
            }
            default -> Manager.error("quest random_quest() -> switch_random");
        }





        // story text generator



        // TODO finish quest generator
        // types of quests, enemy to kill, location, maybe reward system, maybe optional quests etc



    }

    public void check_quest(Hero player)
    {
        try
        {
            switch (type)
            {
                case "boss", "monsters" -> {} // those are checked in locations
                case "gold" -> {if (player.pay(quest_gold))
                {
                    Manager.println("You have helped poor villagers");
                    Main.main_quest = new Quest(Main.days);

                }}
            }
        }
        catch (Exception e)
        {
            Manager.error("quest check_quest() -> wrong type");
        }

    }


    public void print_info()
    {
        Manager.println("You have: " + days_to_complete + " days to " + story);
        switch (type)
        {
            case "boss" ->
            {
                Manager.println("Enemy boss awaits at " + Manager.roman_numbers(target_place+1) + " location");
            }
            case "gold" ->
            {
                Manager.println("You need to gather " + quest_gold);
            }
            case "monsters" ->
            {
                Manager.println("Enemy monsters awaits at " + Manager.roman_numbers(target_place+1) + " location " +
                        monsters_to_kill + " are still alive");
            }
            default -> Manager.error("quest print_info() -> wrong type");
        }

    }
}
