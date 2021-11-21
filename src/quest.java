public class quest
{
    // basic complexity

    int days_to_complete;
    int target_place;

    int quest_gold;

    String type;

    String story;
    String fail_story;

    quest(int current_day)
    {
        random_quest(current_day);
    }


    private static int[] location_and_time(int current_day)
    {
        int location;
        int time;

        // days to complete quest
        int base = balance.weak;
        int[][] difficulty = {
                {base * balance.week, balance.powerful, 3},
                {base * balance.medium, balance.strong, 2},
                {base * balance.strong, balance.medium, 1},
                {base * balance.powerful, balance.week, 1}
        };

        for (int[] level : difficulty)
        {
            if (current_day < level[0])
            {
                location = (int)(Math.random() * (balance.location_number / level[2]));
                time = level[1];
                return new int[]{location, time};
            }
        }
        time = 1;
        location = balance.location_number-1; // last spot
        return new int[]{location, time};
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

                story = "if you haven`t killed a warlock in dungeon, the world will be doomed";
                fail_story = "Time has run out, and warlock has managed to summon a demon, the world is doomed";
            }
            case 1 -> // gold to pay
            {
                type = "gold";
                quest_gold = balance.weak * current_day;
            }
            case 2 ->
            {

            }
            default -> output.error("quest random_quest() -> switch_random");
        }





        // story text generator



        // TODO finish quest generator
        // types of quests, enemy to kill, location, maybe reward system, maybe optional quests etc



    }

    public void check_quest(hero player)
    {
        try
        {
            switch (type)
            {
                case "boss" -> {}
                case "gold" -> {if(pay_money(player)) Main.main_quest = new quest(Main.days);}
            }
        }
        catch (Exception e)
        {
            output.error("quest check_quest() -> wrong type");
        }

    }

    private boolean pay_money(hero player)
    {
        if (player.gold >= quest_gold)
        {
            player.gold -= quest_gold;
            return true;
        }
        return false;
    }


    public void print_info()
    {
        output.println("You have: " + days_to_complete + " days " + story);
        switch (type)
        {
            case "boss" ->
            {
                output.println("Enemy awaits at " + output.roman_numbers(target_place+1) + " location");
            }
            default -> output.error("quest print_info() -> wrong type");
        }

    }
}
