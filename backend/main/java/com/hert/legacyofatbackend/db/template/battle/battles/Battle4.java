package com.hert.legacyofatbackend.db.template.battle.battles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hert.legacyofatbackend.db.template.battle.Battle;
import com.hert.legacyofatbackend.db.template.battle.BattleResult;
import com.hert.legacyofatbackend.db.template.battle.BattleStart;
import com.hert.legacyofatbackend.db.template.enemy.Mugger;
import com.hert.legacyofatbackend.db.template.enemy.Skeleton;
import com.hert.legacyofatbackend.db.template.enemy.Wolf;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Battle 4 class. Contains 2 wolves a skeleton and a mugger, easy.
 */
@Entity
public class Battle4 extends Battle {

    /**
     * Instantiates a new Battle 4.
     */
    public Battle4() {
        super();
    }

    /**
     * Instantiates a new Battle 4.
     *
     * @param name the name
     */
    public Battle4(String name) {

        super(name);

        //TODO: why
        List<String> g = new ArrayList<>();

        g.add("w");
        g.add("m");
        g.add("Z");
        g.add("w");

        List<String> c = new ArrayList<>();

        c.add("#7f7f7f");
        c.add("#7f0000");
        c.add("#AAAAAA");
        c.add("#7f7f7f");

        setGraphics(g);
        setColors(c);
    }

    @JsonIgnore
    @Override
    public BattleStart start() {

        return new BattleStart(new Wolf(), new Mugger(), new Skeleton(), new Wolf());
    }

    @JsonIgnore
    @Override
    public BattleResult result() {

        Random random = new Random();

        return new BattleResult(random.nextInt(3), random.nextInt(80));
    }
}
