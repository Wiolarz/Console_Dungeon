public class quest
{
    // basic complexity

    int days_to_complete;
    int target_place;

    String story;
    String fail_story;

    quest(int current_day)
    {
        random_quest(current_day);
    }

    public void random_quest(int current_day)
    {
        // days to complete quest
        if      (current_day < balance.week * balance.weak)
        {
            days_to_complete = balance.powerful;
            target_place = (int)(Math.random() * (balance.location_number / 3)); //
        }
        else if (current_day < balance.week * balance.medium)
        {
            days_to_complete = balance.strong;
            target_place = (int)(Math.random() * (balance.location_number / 2)); //
        }
        else if (current_day < balance.week * balance.strong)
        {
            days_to_complete = balance.medium;
            target_place = (int)(Math.random() * (balance.location_number)); //
        }
        else if (current_day < balance.week * balance.powerful)
        {
            days_to_complete = balance.weak;
            target_place = (int)(Math.random() * (balance.location_number)); //
        }
        else
        {
            days_to_complete = 1;
            target_place = balance.location_number-1; // last spot
        }


        // story text generator

        story = "if you haven`t killed a warlock in dungeon, the world will be doomed";
        fail_story = "Time has run out, and warlock has managed to summon a demon, the world is doomed";

        // TODO finish quest generator
        // types of quests, enemy to kill, location, maybe reward system, maybe optional quests etc



    }


    public void print_info()
    {
        output.println("You have: " + days_to_complete + " days " + story);
        output.println("Enemy awaits at " + output.roman_numbers(target_place+1) + " location");
    }





}
