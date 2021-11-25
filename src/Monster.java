public class Monster extends Unit implements Fightable // alpha 2.2
{
    int level;

    Monster(int power)
    {
        create_moster(power);
    }

    public void create_moster(int power)
    {
        int[] knight = {3, 2, 1};
        int[] rouge = {2, 3, 1};
        int[] mage = {1, 2, 3};
        int[][] roles = {knight, rouge, mage};
        int[] role = roles[(int)(Math.random() * roles.length)];

        if (power < 1)
        {
            power = 1;
            Manager.error("minus level for monster");
        }
        level = power;

        STR = role[0];
        AG = role[1];
        INT = role[2];




        artefact = new Item(power);
        item_change(artefact);
        generate_strategy();

        max_HP = power;
        HP = max_HP;
    }

    @Override
    public int health()
    {
        return HP;
    }
}
