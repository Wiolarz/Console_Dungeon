import java.util.ArrayList;
import java.util.Objects;

public class Location // Aplha 2.1
{
    static int identification = 0;
    int id;

    int level; // level of things inside the location
    int density; // number of events present
    int chest_chance;
    int chest_gold;
    int quest_enemy;
    int quest_level;

    String name;

    int current_day;


    Location(int location_level)
    { // takes level of location, generates events
        id = Location.identification++; // debug

        name = name_generator();

        level = location_level;
        quest_level = level+2;
        if (quest_level > Balance.max_power) quest_level = Balance.max_power;

        chest_gold = level * Balance.medium;

        density = 5; // number of events in location
        chest_chance = 3; // %(10) chest chance
        quest_enemy = 5; // %(10) chance of quest related enemy
    }

    public void enter(ArrayList<Hero> company, int day)
    {
        // quest related
        current_day = day;
        int killed = 0;

        for (int i = 0; i < Balance.events; i++)
        {
            if (Explore.fight(company, Explore.generate_enemy(level)))
            {
                killed += level;
                int event = (int)(Math.random() * 2);
                if (event == 1) // 50% chance
                    Explore.chest(company, chest_gold);
                else
                    Explore.book(company, chest_gold);
            }
        }

        if (quest())
        {
            switch (Main.main_quest.type)
            {
                case "boss" -> boss(company);
                case "monsters" -> horde(killed);
                default -> {} // I think it should never happen
            }
        }
    }

    private boolean quest()
    {
        return Main.main_quest.target_place == id;
    }

    private void boss(ArrayList<Hero> company)
    {
        Manager.println("You encounter boss, his level: " + quest_level);

        ArrayList<Monster> boss = new ArrayList<>();
        boss.add(new Monster(quest_level));
        if(Explore.fight(company, boss))
        {
            Main.main_quest = new Quest(current_day);
            Main.main_quest.days_to_complete++;
        }
    }

    private void horde(int killed)
    {
        Manager.println("you have defeated " + killed + " monsters");
        Main.main_quest.monsters_to_kill -= killed;
        if(Main.main_quest.monsters_to_kill <= 0)
        {
            Main.main_quest = new Quest(current_day);
            Main.main_quest.days_to_complete++;
        }
    }

    public String short_print()
    {
        return name + " " + Manager.roman_numbers(level);
    }

    static String name_generator()
    {
        String[] prefix = {"", "Green", "Dark", "Toxic", "Inferno", "Orc", "Goblin", "Dragon"};
        String[] core = {"Forest", "Cave", "Dungeon", "Town", "Village", "Mountains", "Graveyard"};
        //String[] sufix = {"", ""};
        boolean new_unique = false;
        String new_name = "";

        int cheking_wrong_balance = 0;
        while (!new_unique)
        {
            cheking_wrong_balance++;
            if (cheking_wrong_balance > Balance.location_number*5)
            {
                Manager.debug("Error: cannot create random new location name");
                System.exit(343);
            }
            new_name = prefix[(int)(Math.random() * prefix.length)] + " "
                    + core[(int)(Math.random() * core.length)];

            new_unique = true;
            for (String name : Balance.location_names)
            {
                if (Objects.equals(name, new_name))
                {
                    new_unique = false;
                    break;
                }
            }
        }
        Balance.location_names.add(new_name);
        return new_name;
    }


}
