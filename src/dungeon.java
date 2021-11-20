public class dungeon
{
    static void fight(hero player, monster enemy)
    { // We create player int and enemy int
        int player_score;
        int enemy_score;

        System.out.println("Player HP: " + player.HP + " Enemy HP: " + enemy.HP);
        // we print out Health points values of player and enemy


        while (enemy.HP > 0)
        {
            //player attack
            player_score = player.attack();// Value of players damage which is random
            enemy_score = enemy.defense();// Value of enemy defense which is random
            System.out.println("Player attack: " + player_score + " Enemy defense: " + enemy_score);
            // we print out implemented dmg and enemy defense


            if (player_score > enemy_score){
                enemy.damage(player.weapon[0]); // Weapon kind is defined in "unit"
                // which also indicates damage output
            }

            //enemy attack

            enemy_score = enemy.attack();// Random value of enemy dmg
            player_score = player.defense();// Random value of player "roll" - mechanic

            System.out.println("Enemy attack: " + enemy_score + " Player defense: " + player_score);// Printing implemented values

            if(enemy_score > player_score)
            {
                player.damage(enemy.weapon[0]); // Weapon kind is defined in "unit"
                // which also indicates damage output
            }

            System.out.println("Player HP: " + player.HP + " Enemy HP: " + enemy.HP);
            // Printing out Values of Player Health Points and Enemy Health Points

        }
    }


    static void forest(hero player)
    {
        String[] events = {"monster", "chest", "monster", "monster", "chest"};// Creating occurring events
        System.out.println("You enter a forest");// Starting location

        for(String event : events)
        {
            if(event.equals("monster"))
            { // If you encounter monster ad.1
                System.out.println("You fight a monster");//ad.1 You fight it
                monster forest_m = new monster();// creating forest-monster
                fight(player, forest_m);// Fighting event with monster

            }
            else if(event.equals("chest"))
            {// Event were you find chest with gold
                System.out.println("You find a chest. +1 gold, your HP is restored");// chest with one gold
                player.gold += 1;// You put one gold into "gold" variable
                player.HP = player.max_HP; // chest contained healing potions
            }
        }
        System.out.println("You exit the forest");// You finish event without dying
        System.out.println("You won with: " + player.gold + " gold");
    }
}
