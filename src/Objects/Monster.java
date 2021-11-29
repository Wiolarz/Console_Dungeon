package Objects;

import Gameplay.*;
import Technical.*;
public class Monster extends Unit<Unit>// alpha 2.2
{
    public int level;

    public Monster(int power)
    {
        create_moster(power);
    }

    public void create_moster(int power)
    {
        if (power < 1)
        {
            power = 1;
            Manager.error("minus level for monster");
        }
        level = power;

        artefact = new Item(power);
        item_change(artefact);
        generate_strategy();

        max_HP = power;
        HP = max_HP;
    }
}
