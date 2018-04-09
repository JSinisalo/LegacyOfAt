package com.hert.legacyofatbackend.api;

import com.hert.legacyofatbackend.db.Guser;
import com.hert.legacyofatbackend.db.GuserRepository;
import com.hert.legacyofatbackend.db.Team;
import com.hert.legacyofatbackend.db.template.character.At;
import com.hert.legacyofatbackend.db.template.character.Ilmatar;
import com.hert.legacyofatbackend.db.template.character.Kuu;
import com.hert.legacyofatbackend.db.template.character.Surma;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class GuserController {

    @Autowired
    private GuserRepository guserRepository;

    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public ResponseEntity<String> test(@AuthenticationPrincipal String gId) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser != null) {

            guser.setLoginAmount(guser.getLoginAmount() + 1);
            guserRepository.save(guser);

            return new ResponseEntity<>("already had this profile", HttpStatus.OK);
        }

        guser = new Guser(gId);

        guser.addCharacter(new At());
        guser.addCharacter(new Ilmatar());
        guser.addCharacter(new Surma());
        guser.addCharacter(new Kuu());

        guserRepository.save(guser);

        return new ResponseEntity<>("created new user profile", HttpStatus.OK);
    }

    @RequestMapping(value = "/api/me", method = RequestMethod.GET)
    public ResponseEntity<Guser> me(@AuthenticationPrincipal String gId) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<Guser>(guser, HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + gId + " retrieved");

        guser.setgId(""); //but dont save

        return new ResponseEntity<Guser>(guser, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/team", method = RequestMethod.POST)
    public ResponseEntity<String> team(@AuthenticationPrincipal String gId, @RequestBody Team team) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<String>("failure", HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + gId + " team got");

        guser.setTeam(team.getId(), team);
        guserRepository.save(guser);

        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @RequestMapping(value = "/api/roll", method = RequestMethod.GET)
    public ResponseEntity<Guser> roll(@AuthenticationPrincipal String gId) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<Guser>(guser, HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + gId + " rolling");

        if(guser.getJims() < 25){

            guser = null;
            return new ResponseEntity<Guser>(guser, HttpStatus.NOT_ACCEPTABLE);
        }

        guser.setJims(guser.getJims() - 25);
        guser.addCharacters(Gacha.rollBanner(Gacha.fullBanner, 1));
        guserRepository.save(guser);

        guser.setgId(""); //but dont save

        return new ResponseEntity<Guser>(guser, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/bigroll", method = RequestMethod.GET)
    public ResponseEntity<Guser> bigroll(@AuthenticationPrincipal String gId) {

        Guser guser = guserRepository.findBygId(gId);

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<Guser>(guser, HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + gId + " rolling");

        if(guser.getJims() < 250){

            guser = null;
            return new ResponseEntity<Guser>(guser, HttpStatus.NOT_ACCEPTABLE);
        }

        guser.setJims(guser.getJims() - 250);
        guser.addCharacters(Gacha.rollBanner(Gacha.fullBanner, 11));
        guserRepository.save(guser);

        guser.setgId(""); //but dont save

        return new ResponseEntity<Guser>(guser, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/battlestart", method = RequestMethod.GET)
    public ResponseEntity<BattleStart> battlestart(@AuthenticationPrincipal String gId) {

        Guser guser = guserRepository.findBygId(gId);
        BattleStart start = null;

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<BattleStart>(start , HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + gId + " battle start");

        start = new BattleStart();

        return new ResponseEntity<BattleStart>(start, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/battleresult", method = RequestMethod.GET)
    public ResponseEntity<BattleResult> battleresult(@AuthenticationPrincipal String gId) {

        Guser guser = guserRepository.findBygId(gId);
        BattleResult result = null;

        if(guser == null) {

            System.out.println("no guser with id " + gId + " found");

            return new ResponseEntity<BattleResult>(result, HttpStatus.NOT_FOUND);
        }

        System.out.println("guser id " + gId + " battle result");

        result = new BattleResult();

        guser.setJims(guser.getJims() + result.getJims());
        guser.setGold(guser.getGold() + result.getGold());

        guserRepository.save(guser);

        return new ResponseEntity<BattleResult>(result, HttpStatus.OK);
    }
}

