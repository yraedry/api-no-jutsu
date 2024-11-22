package com.apinojutsu.controller;

import com.apinojutsu.component.animeflv.scrapper.AnimeFlvScraperComponent;
import com.apinojutsu.dto.InformacionAnimeDto;
import com.apinojutsu.dto.NovedadesAnimeFlvDto;
import com.apinojutsu.dto.NovedadesEpisodiosAnimeFlvDto;
import com.apinojutsu.dto.LoginAnimeFlvDto;
import com.apinojutsu.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/animeflv")
public class AnimeFlvController {

    private final AnimeFlvScraperComponent animeFlvScraper;

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    public AnimeFlvController(AnimeFlvScraperComponent animeFlvScraper) {
        this.animeFlvScraper = animeFlvScraper;
    }

    @PostMapping("/login")
    public LoginAnimeFlvDto login(@RequestParam String username, @RequestParam String password) {
        try {
            // Realiza el login
            Map<String, String> loginResponse = animeFlvScraper.login(username, password);
            if ("success".equalsIgnoreCase(loginResponse.get("status"))) {
                return new LoginAnimeFlvDto(loginResponse.get("status"), username, messageUtils.getMessage("animeflv.login.correcto", username));
            } else if ("activo".equalsIgnoreCase(loginResponse.get("status"))) {
                return new LoginAnimeFlvDto("error", username, messageUtils.getMessage("animeflv.login.sesion.activa", username));
            }else {
                return new LoginAnimeFlvDto("error", username, messageUtils.getMessage("animeflv.login.fallo", username));
            }
        } catch (Exception e) {
            return new LoginAnimeFlvDto("error", username, messageUtils.getMessage("animeflv.login.fallo", username));
        }
    }

    @GetMapping("/novedades-episodios")
    public List<NovedadesEpisodiosAnimeFlvDto> getNovedadesEpisodios() {
        try {
            return animeFlvScraper.obtenerUltimosEpisodiosNovedades();
        } catch (IOException e) {
            return List.of(new NovedadesEpisodiosAnimeFlvDto(messageUtils.getMessage("animeflv.episodios.error"), null));
        }
    }

    @GetMapping("/novedades-animes")
    public List<NovedadesAnimeFlvDto> getNovedadesAnime() {
        try {
            return animeFlvScraper.obtenerUltimasNovedades();
        } catch (IOException e) {
            return List.of(new NovedadesAnimeFlvDto(messageUtils.getMessage("animeflv.animes.error"), null));
        }
    }

    @GetMapping("/obtener-anime")
    public InformacionAnimeDto getInformacionAnime(String animeUrl) {
        try {
            // obtiene la pagina de series
            return animeFlvScraper.obtenerInformacionAnime(animeUrl);
        } catch (IOException e) {
            return new InformacionAnimeDto(messageUtils.getMessage("error.session.notfound"));
        }
    }

    @PostMapping("/logout")
    public LoginAnimeFlvDto logout(@RequestParam String username) {
        try {
            boolean logoutExitoso = animeFlvScraper.logout();
            if (logoutExitoso) {
                return new LoginAnimeFlvDto("success", username, messageUtils.getMessage("animeflv.logout.correcto", username));
            } else {
                return new LoginAnimeFlvDto("error", username, messageUtils.getMessage("animeflv.logout.error", username));
            }
        } catch (Exception e) {
            // Manejo de errores inesperados
            return new LoginAnimeFlvDto("error", username, messageUtils.getMessage("animeflv.logout.error", username));
        }
    }
}
