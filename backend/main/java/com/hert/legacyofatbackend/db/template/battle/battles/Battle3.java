package com.hert.legacyofatbackend.db.template.battle.battles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hert.legacyofatbackend.db.template.battle.Battle;
import com.hert.legacyofatbackend.db.template.battle.BattleResult;
import com.hert.legacyofatbackend.db.template.battle.BattleStart;
import com.hert.legacyofatbackend.db.template.enemy.Mugger;
import com.hert.legacyofatbackend.db.template.enemy.Skeleton;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Battle 3 class. Contains 3 muggers and a skeleton, very easy.
 */
@Entity
public class Battle3 extends Battle {

    /**
     * Instantiates a new Battle 3.
     */
    public Battle3() {
        super();
    }

    /**
     * Instantiates a new Battle 3.
     *
     * @param name the name
     */
    public Battle3(String name) {

        super(name);

        //TODO: why
        List<String> g = new ArrayList<>();

        g.add("m");
        g.add("m");
        g.add("m");
        g.add("Z");

        List<String> c = new ArrayList<>();

        c.add("#7f0000");
        c.add("#7f0000");
        c.add("#7f0000");
        c.add("#AAAAAA");

        setGraphics(g);
        setColors(c);
    }

    @JsonIgnore
    @Override
    public BattleStart start() {

        return new BattleStart(new Mugger(), new Mugger(), new Mugger(), new Skeleton());
    }

    @JsonIgnore
    @Override
    public BattleResult result() {

        Random random = new Random();

        return new BattleResult(random.nextInt(3), random.nextInt(80));
    }
}
