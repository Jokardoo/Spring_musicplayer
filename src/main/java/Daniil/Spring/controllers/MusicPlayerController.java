package Daniil.Spring.controllers;

import Daniil.Spring.models.Music;
import Daniil.Spring.models.MusicSearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MusicPlayerController {

    private final MusicSearchEngine musicSearchEngine;

    @Autowired
    public MusicPlayerController(MusicSearchEngine musicSearchEngine) {
        this.musicSearchEngine = musicSearchEngine;
    }

    @GetMapping("/find-page")
    public String musicPlayer(Model model, @ModelAttribute ("music") Music music, @RequestParam(value = "q", required = false) String q) {
        model.addAttribute("query", q);
        return "music/findPage";
    }

    @PostMapping("/showMusic")
    public String showMusic(Model model, @ModelAttribute ("music") Music music, BindingResult bindingResult) {
        String enteredText = music.getFullName();
        List<Music> musicList = musicSearchEngine.getMusicList(enteredText);

        for (Music currentMusic : musicList) {
            System.out.println(currentMusic);
        }

        model.addAttribute("query", enteredText);   // query - введенный текст
        model.addAttribute("foundMusic", musicList);
        return "music/findPage";
    }
}
