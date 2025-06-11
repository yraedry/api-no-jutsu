package com.apinojutsu.component.database;

import com.apinojutsu.dto.InformacionAnimeDto;
import com.apinojutsu.dto.InformacionEpisodioAnimeDto;
import com.apinojutsu.dto.NovedadesAnimeFlvDto;
import com.apinojutsu.dto.NovedadesEpisodiosAnimeFlvDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AnimeDatabaseClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public AnimeDatabaseClient(RestTemplateBuilder builder,
                               @Value("${database.base-url}") String baseUrl) {
        this.restTemplate = builder.build();
        this.baseUrl = baseUrl;
    }

    public List<NovedadesEpisodiosAnimeFlvDto> obtenerNovedadesEpisodios() {
        ResponseEntity<List<NovedadesEpisodiosAnimeFlvDto>> response = restTemplate.exchange(
                baseUrl + "/novedades/episodios",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public List<NovedadesAnimeFlvDto> obtenerNovedadesAnimes() {
        ResponseEntity<List<NovedadesAnimeFlvDto>> response = restTemplate.exchange(
                baseUrl + "/novedades/animes",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {});
        return response.getBody();
    }

    public InformacionAnimeDto obtenerInformacionAnime(String anime) {
        return restTemplate.getForObject(baseUrl + "/anime/" + anime, InformacionAnimeDto.class);
    }

    public InformacionEpisodioAnimeDto obtenerUrlsDescarga(String episodio) {
        return restTemplate.getForObject(baseUrl + "/episodio/" + episodio + "/descargas",
                InformacionEpisodioAnimeDto.class);
    }

    public InformacionEpisodioAnimeDto obtenerUrlsVisualizacion(String episodio) {
        return restTemplate.getForObject(baseUrl + "/episodio/" + episodio + "/visualizaciones",
                InformacionEpisodioAnimeDto.class);
    }
}
