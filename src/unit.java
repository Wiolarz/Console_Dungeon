public class unit {
    // Stats
    int STR;
    int AG;
    int INT;

    int HP;
    int max_HP;

    int[] weapon = {0, 0};
    int[] armor = {0, 0};


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
