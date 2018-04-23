package com.hert.legacyofatbackend.db.template.battle.battles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hert.legacyofatbackend.db.template.battle.Battle;
import com.hert.legacyofatbackend.db.template.battle.BattleResult;
import com.hert.legacyofatbackend.db.template.battle.BattleStart;
import com.hert.legacyofatbackend.db.template.enemy.Skeleton;
import com.hert.legacyofatbackend.db.template.enemy.Wolf;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Battle 2 class. Contains 2 skeletons and a wolf, very easy.
 */
@Entity
public class Battle2 extends Battle {

    /**
     * Instantiates a new Battle 2.
     */
    public Battle2() {
        super();
    }

    /**
     * Instantiates a new Battle 2.
     *
     * @param name the name
     */
    public Battle2(String name) {

        super(name);

        //TODO: why
        List<String> g = new ArrayList<>();

        g.add("Z");
        g.add("w");
        g.add("Z");
        g.add("");

        List<String> c = new ArrayList<>();

        c.add("#AAAAAA");
        c.add("#7f7f7f");
        c.add("#AAAAAA");
        c.add("#AAAAAA");

        setGraphics(g);
        setColors(c);
    }

    @JsonIgnore
    @Override
    public BattleStart start() {

        return new BattleStart(new Skeleton(), new Wolf(), new Skeleton(), null);
    }

    @JsonIgnore
    @Override
    public BattleResult result() {

        Random random = new Random();

        return new BattleResult(random.nextInt(2), random.nextInt(60));
    }
}
