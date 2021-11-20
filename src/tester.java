public class tester // 2.2
{
    static boolean quest_creator()
    {
        try
        {
            for (int day = -5; day < 10000; day++)
            {
                quest quest_object = new quest(day);
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
                location location_object = new location(level);
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
                item thing = new item(i);
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
                for (int level = -1; level < balance.max_power; level++)
                {
                    monster enemy = new monster(level);
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
                hero player = new hero();
                monster enemy = new monster(level);
                //quest_object.print_info();
                System.out.println(explore.fight(player, enemy));
            }

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }




}
