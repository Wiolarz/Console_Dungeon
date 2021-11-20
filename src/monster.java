public class monster
{
    // Stats
    int STR;
    int AG;
    int INT;

    int HP;
    int max_HP;

    int[] weapon = {0, 0};
    int[] armor = {0, 0};

    monster()
    {
        create_monster();
        // monster: Function with the same name lunches after creating an object
    }


    public void create_monster()
    {
        int[] golem = {6, 1, 5};// Golem role
        int[] bandit = {5, 4, 5};// Bandit role
        int[] warlock = {3, 6, 3};// Warlock role
        int[][] roles = {golem, bandit, warlock};// Shortcut
        int[] role = roles[(int)(Math.random() * roles.length)];
        //// We randomly choose from three possible roles which we defined earlier

        STR = role[0];// Strength values
        AG = role[1];// Agility values
        INT = role[2];// Intelligence values

        max_HP = STR*INT;// Generate max Health Points
        HP = max_HP;


        weapon[0] = AG;
        weapon[1] = INT;

        armor[0] = STR;
        armor[1] = AG;
    }


    public void damage(int value)
    {
        // hp change
        HP -= value;
    }


    // Generating attack and it's value
    public int attack()
    {
        int value = 0;
        for (int i = 0; i < weapon[0]; i++)
        {
            value += (int) (Math.random() * weapon[1]);
        }
        return value;

    }

    // Generating defense value
    public int defense()
    {
        int value = 0;
        for (int i = 0; i < armor[0]; i++)
        {
            value += (int) (Math.random() * armor[1]);
        }
        return value;
    }
}