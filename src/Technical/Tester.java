package Technical;

import Gameplay.*;
import Objects.*;

public class Tester // 2.2
{
    static boolean quest_creator()
    {
        try
        {
            for (int day = -5; day < 10000; day++)
            {
                Quest quest_object = new Quest(day);
                //quest_object.print_info();
            }

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    static boolean location_creation()
    {
        try
        {
            for (int level = -5; level < 10; level++)
            {
                Location location_object = new Location(level);
                //quest_object.print_info();
                System.out.println(location_object.id);
            }

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }


    static void item_print()
    {
        for (int i = 0; i < 18; i++) {
            System.out.println();
            System.out.println("------------------------ITEM LEVEL" + i + "--------------------");
            System.out.println();
            for (int j = 0; j < 9; j++) {
                Item thing = new Item(i);
                System.out.println();
                thing.print_item();
                System.out.println();
            }
        }
    }

    static void spell()
    {
        for (int i = 0; i < 21; i++)
        {
            //System.out.println(effect.dice_change(10, i));
            //System.out.println(i%5 +" " + i);
            //System.out.println(economy.rome_numbers(i));
        }
    }


    static boolean monster_generation(int attempts)
    {
        try
        {
            for (int i = 0; i < attempts; i++)
            {
                for (int level = -1; level < Balance.max_power; level++)
                {
                    Monster enemy = new Monster(level);
                    //quest_object.print_info();
                    //enemy.printing_all_stats();
                }
            }


            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }



    static boolean fights()
    {
        try
        {
            for (int level = -5; level < 10; level++)
            {
                Hero player = new Hero();
                Monster enemy = new Monster(level);
                //quest_object.print_info();
                //System.out.println(explore.fight(player, enemy));
            }

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }


    static boolean heal()
    {
        try
        {
            int[][] healing  = {{2, 3}, {3, 5}, {4, 6}, {6, 8}};
            Hero player = new Hero();
            for (int max = 1; max < 100; max++)
            {
                player.max_HP = max;
                for (int money = 0; money < 100; money++)
                {
                    player.gold = money;
                    for (int health = 1; health <= max; health++)
                    {
                        player.HP = health;
                        Economy.autoHeal(player, healing);
                    }
                }
            }

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }


    public static void main(String[] args)
    {
        // Run tests
    }



}
