public class hero extends unit
{
    // Stats
    int gold = 0;

    hero()
    {
        create_hero();
    } // Hero: Function with the same name lunches after creating an object

    public void create_hero()
    { // We create hero classes
        int[] knight = {10, 4, 3};// Hero role Knight
        int[] rouge = {7, 6, 6};// Hero role Rouge
        int[] mage = {3, 3, 8};// Hero role Mage
        int[][] roles = {knight, rouge, mage};// Shortcut
        int[] role = roles[(int)(Math.random() * roles.length)];// We use this shortcut here
        // We randomly choose from three possible roles which we defined earlier

        STR = role[0];// Strength values
        AG = role[1];// Agility values
        INT = role[2];//Intelligence values

        weapon[0] = AG;
        weapon[1] = INT;

        armor[0] = STR;
        armor[1] = AG;

        max_HP = STR*INT;// Generate max Health Points
        HP = max_HP;
    }

    @Override
    public void damage(int value)
    {
        // hp change
        HP -= value;
        if(HP <= 0)
        {
            System.out.println("YOU DIED");
            System.out.println("Your gold: " + gold);
            // It is printed out when your health points go down to HP<=0
            System.exit(2); // App closes
        }
    }
}
