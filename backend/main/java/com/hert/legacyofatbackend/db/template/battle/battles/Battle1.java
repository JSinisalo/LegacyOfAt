package com.hert.legacyofatbackend.db.template.battle.battles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hert.legacyofatbackend.db.template.battle.Battle;
import com.hert.legacyofatbackend.db.template.battle.BattleResult;
import com.hert.legacyofatbackend.db.template.battle.BattleStart;
import com.hert.legacyofatbackend.db.template.enemy.Skeleton;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Battle 1 class. Contains 2 skeletons, very easy.
 */
@Entity
public class Battle1 extends Battle {

    /**
     * Instantiates a new Battle 1.
     */
    public Battle1() {
        super();
    }

    /**
     * Instantiates a new Battle 1.
     *
     * @param name the name
     */
    public Battle1(String name) {

        super(name);

        //TODO: why
        List<String> g = new ArrayList<>();

        g.add("Z");
        g.add("Z");
        g.add("");
        g.add("");

        List<String> c = new ArrayList<>();

        c.add("#AAAAAA");
        c.add("#AAAAAA");
        c.add("#AAAAAA");
        c.add("#AAAAAA");

        setGraphics(g);
        setColors(c);
    }

    @JsonIgnore
    @Override
    public BattleStart start() {

        return new BattleStart(new Skeleton(), new Skeleton(), null, null);
    }

    @JsonIgnore
    @Override
    public BattleResult result() {

        Random random = new Random();

        return new BattleResult(random.nextInt(2), random.nextInt(50));
    }
}
