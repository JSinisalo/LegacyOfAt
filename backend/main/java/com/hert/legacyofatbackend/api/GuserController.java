package com.hert.legacyofatbackend.api;

import com.hert.legacyofatbackend.db.Guser;
import com.hert.legacyofatbackend.db.GuserRepository;
import com.hert.legacyofatbackend.db.Rollable;
import com.hert.legacyofatbackend.db.Team;
import com.hert.legacyofatbackend.db.template.battle.BattleResult;
import com.hert.legacyofatbackend.db.template.battle.BattleStart;
import com.hert.legacyofatbackend.db.template.character.*;
import com.hert.legacyofatbackend.db.template.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * A REST controller for the entire backend. Contains all get and post methods the rest api has.
 */
@RestController
public class GuserController {

    @Autowired
    private GuserRepository guserRepository;

    /**
     * Testing endpoint.
     *
     * This will check whether a Guser with the given gId exists or not, and will create a new one if doesnt exist.
     * This is the first thing to be called by the application.
     *
     * @param gId the g id
     * @return the response entity
     */
    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public ResponseEntity<String> test(@AuthenticationPrincipal String gId) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser != null) {

            guser.setLoginAmount(guser.getLoginAmount() + 1);
            guserRepository.save(guser);

            return new ResponseEntity<>("success", HttpStatus.OK);
        }

        guser = new Guser(gId);

        guser.addCharacter(new At());

        guserRepository.save(guser);

        System.out.println("new guser created");

        return new ResponseEntity<>("created", HttpStatus.OK);
    }

    /**
     * Endpoint for the full Guser data.
     *
     * This endpoint will give the full data of the guser with the given gid.
     *
     * @param gId the g id
     * @return the response entity
     */
    @RequestMapping(value = "/api/me", method = RequestMethod.GET)
    public ResponseEntity<Guser> me(@AuthenticationPrincipal String gId) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<Guser>(guser, HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + guser.getName() + " retrieved");

        guser.setgId(""); //but dont save

        return new ResponseEntity<Guser>(guser, HttpStatus.OK);
    }

    /**
     * Endpoint to receive a name for the given gid guser.
     *
     * @param gId  the g id
     * @param name the name
     * @return the response entity
     */
    @RequestMapping(value = "/api/name", method = RequestMethod.POST)
    public ResponseEntity<String> name(@AuthenticationPrincipal String gId, @RequestBody String name) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<String>("failure", HttpStatus.NOT_FOUND);
        }

        System.out.println("guser named themselves " + name);

        guser.setName(name);
        guserRepository.save(guser);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    /**
     * Endpoint to receive a team for the given gid guser.
     *
     * @param gId  the g id
     * @param team the team
     * @return the response entity
     */
    @RequestMapping(value = "/api/team", method = RequestMethod.POST)
    public ResponseEntity<String> team(@AuthenticationPrincipal String gId, @RequestBody Team team) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<String>("failure", HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + guser.getName() + " team got");

        guser.setTeam(team.getId(), team);
        guserRepository.save(guser);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    /**
     * Endpoint to receive a character id and its items for the given gid guser.
     *
     * @param gId     the g id
     * @param payload the payload
     * @return the response entity
     */
    @RequestMapping(value = "/api/items", method = RequestMethod.POST)
    public ResponseEntity<String> items(@AuthenticationPrincipal String gId, @RequestBody ItemPayload payload) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<String>("failure", HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + guser.getName() + " chara got");

        Chara c = guser.getCharas().get(payload.getId());

        c.setArmor(payload.getArmor());
        c.setWeapon(payload.getWeapon());
        c.setTrinket1(payload.getTrinket1());

        guserRepository.save(guser);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    /**
     * Endpoint to receive an id of the item the gid guser wants to buy, will respond with full guser data.
     *
     * @param gId     the g id
     * @param payload the payload
     * @return the response entity
     */
    @RequestMapping(value = "/api/buy", method = RequestMethod.POST)
    public ResponseEntity<Guser> buy(@AuthenticationPrincipal String gId, @RequestBody String payload) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<Guser>(guser, HttpStatus.NOT_FOUND);
        }

        payload = payload.replace("\"", "");

        Item i = (Item)Gacha.createObject(payload, true);

        if(guser.getGold() < i.getPrice()) {

            guser = null;
            return new ResponseEntity<Guser>(guser, HttpStatus.NOT_ACCEPTABLE);
        }

        System.out.println("guser id " + guser.getName() + " bought " + i.getName());

        guser.addItem(i);
        guser.setGold(guser.getGold() - i.getPrice());

        guserRepository.save(guser);

        guser.setgId("");

        return new ResponseEntity<Guser>(guser, HttpStatus.OK);
    }

    /**
     * Endpoint for doing a single roll, responds will full guser data.
     *
     * @param gId the g id
     * @return the response entity
     */
    @RequestMapping(value = "/api/roll", method = RequestMethod.GET)
    public ResponseEntity<Guser> roll(@AuthenticationPrincipal String gId) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<Guser>(guser, HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + guser.getName() + " rolling");

        if(guser.getJims() < 25){

            guser = null;
            return new ResponseEntity<Guser>(guser, HttpStatus.NOT_ACCEPTABLE);
        }

        guser.setJims(guser.getJims() - 25);

        List<Rollable> rolls = Gacha.rollBanner(Gacha.fullBanner, 1);

        for(int i = 0; i < rolls.size(); i++) {

            if(rolls.get(i) instanceof Chara) {

                Chara c = (Chara)rolls.get(i);

                if(guser.charaExists(c))
                    guser.setGold(guser.getGold() + 1000*c.getRarity());
                else
                    guser.addCharacter(c);

            } else if(rolls.get(i) instanceof Item) {

                guser.addItem((Item)rolls.get(i));
            }
        }

        guserRepository.save(guser);

        guser.setgId(""); //but dont save

        return new ResponseEntity<Guser>(guser, HttpStatus.OK);
    }

    /**
     * Endpoint for doing a big roll, responds will full guser data.
     *
     * @param gId the g id
     * @return the response entity
     */
    @RequestMapping(value = "/api/bigroll", method = RequestMethod.GET)
    public ResponseEntity<Guser> bigroll(@AuthenticationPrincipal String gId) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<Guser>(guser, HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + guser.getName() + " big rolling");

        if(guser.getJims() < 250){

            guser = null;
            return new ResponseEntity<Guser>(guser, HttpStatus.NOT_ACCEPTABLE);
        }

        guser.setJims(guser.getJims() - 250);

        List<Rollable> rolls = Gacha.rollBanner(Gacha.fullBanner, 11);

        for(int i = 0; i < rolls.size(); i++) {

            if(rolls.get(i) instanceof Chara) {

                Chara c = (Chara)rolls.get(i);

                if(guser.charaExists(c))
                    guser.setGold(guser.getGold() + 1000*c.getRarity());
                else
                    guser.addCharacter(c);

            } else if(rolls.get(i) instanceof Item) {

                guser.addItem((Item)rolls.get(i));
            }
        }

        guserRepository.save(guser);

        guser.setgId(""); //but dont save

        return new ResponseEntity<Guser>(guser, HttpStatus.OK);
    }

    /**
     * Endpoint for starting a battle with the received id. Responds with the battle start of the batlle.
     *
     * @param gId     the g id
     * @param payload the payload
     * @return the response entity
     */
    @RequestMapping(value = "/api/battlestart", method = RequestMethod.POST)
    public ResponseEntity<BattleStart> battlestart(@AuthenticationPrincipal String gId, @RequestBody String payload) {

        Guser guser = guserRepository.findBygId(gId);
        BattleStart start = null;

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<BattleStart>(start , HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + guser.getName() + " battle " + Integer.parseInt(payload) + " start");

        start = guser.getBattles().get(Integer.parseInt(payload)).start();
        guser.setcBattle(Integer.parseInt(payload));

        guserRepository.save(guser);

        return new ResponseEntity<BattleStart>(start, HttpStatus.OK);
    }

    /**
     * Endpoint for winning a battle. Responds with results of the won battle
     *
     * @param gId the g id
     * @return the response entity
     */
    @RequestMapping(value = "/api/battleresult", method = RequestMethod.GET)
    public ResponseEntity<BattleResult> battleresult(@AuthenticationPrincipal String gId) {

        Guser guser = guserRepository.findBygId(gId);
        BattleResult result = null;

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<BattleResult>(result, HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + guser.getName() + " battle result | (initial) " + guser.getBattles().get(guser.getcBattle()).getCleared());

        result = guser.getBattles().get(guser.getcBattle()).result();
        result.setBattle(guser.getcBattle());

        if(!guser.getBattles().get(guser.getcBattle()).getCleared()) {

            guser.setRank(guser.getRank() + 1);
            result.setRankUp(true);
        }

        guser.getBattles().get(guser.getcBattle()).setCleared(true);

        guser.setJims(guser.getJims() + result.getJims());
        guser.setGold(guser.getGold() + result.getGold());

        guserRepository.save(guser);

        return new ResponseEntity<BattleResult>(result, HttpStatus.OK);
    }

    /**
     * Endpoint for losing a battle.
     *
     * @param gId the g id
     * @return the response entity
     */
    @RequestMapping(value = "/api/battlelose", method = RequestMethod.GET)
    public ResponseEntity<BattleResult> battlelose(@AuthenticationPrincipal String gId) {

        Guser guser = guserRepository.findBygId(gId);
        BattleResult result = null;

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<BattleResult>(result, HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + guser.getName() + " battle " + guser.getcBattle() + " lost");

        result = guser.getBattles().get(guser.getcBattle()).result();
        result.setGold(0);
        result.setJims(0);

        return new ResponseEntity<BattleResult>(result, HttpStatus.OK);
    }
}

