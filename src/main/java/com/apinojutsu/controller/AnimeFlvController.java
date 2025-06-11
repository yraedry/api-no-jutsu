package com.apinojutsu.controller;

import com.apinojutsu.component.database.AnimeDatabaseClient;
import com.apinojutsu.dto.*;
import com.apinojutsu.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animeflv")
public class AnimeFlvController {

    private final AnimeDatabaseClient databaseClient;

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    public AnimeFlvController(AnimeDatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }


    @GetMapping("/novedades-episodios")
    public List<NovedadesEpisodiosAnimeFlvDto> getNovedadesEpisodios() {
        try {
            return databaseClient.obtenerNovedadesEpisodios();
        } catch (Exception e) {
            return List.of(new NovedadesEpisodiosAnimeFlvDto(messageUtils.getMessage("animeflv.episodios.error"), null, null, null));
        }
    }

    @GetMapping("/novedades-animes")
    public List<NovedadesAnimeFlvDto> getNovedadesAnime() {
        try {
            return databaseClient.obtenerNovedadesAnimes();
        } catch (Exception e) {
            return List.of(new NovedadesAnimeFlvDto(messageUtils.getMessage("animeflv.animes.error"), null));
        }
    }

    @GetMapping("/obtener-anime")
    public InformacionAnimeDto getInformacionAnime(String anime) {
        try {
            return databaseClient.obtenerInformacionAnime(anime);
        } catch (Exception e) {
            return new InformacionAnimeDto(messageUtils.getMessage("animeflv.animes.error"));
        }
    }

    @GetMapping("/obtener-url-descarga")
    public InformacionEpisodioAnimeDto getEnlacesDescargaAnime(String animeEpisode) {
        try {
            return databaseClient.obtenerUrlsDescarga(animeEpisode);
        } catch (Exception e) {
            return new InformacionEpisodioAnimeDto(messageUtils.getMessage("animeflv.episodios.error"));
        }
    }

    @GetMapping("/obtener-url-visualizacion")
    public InformacionEpisodioAnimeDto getEnlacesVisualizacionAnime(String animeEpisode) {
        try {
            return databaseClient.obtenerUrlsVisualizacion(animeEpisode);
        } catch (Exception e) {
            return new InformacionEpisodioAnimeDto(messageUtils.getMessage("animeflv.episodios.error"));
        }
    }

}
