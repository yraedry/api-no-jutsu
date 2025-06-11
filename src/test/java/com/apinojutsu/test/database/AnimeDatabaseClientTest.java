package com.apinojutsu.test.database;

import com.apinojutsu.component.database.AnimeDatabaseClient;
import com.apinojutsu.dto.InformacionAnimeDto;
import com.apinojutsu.dto.InformacionEpisodioAnimeDto;
import com.apinojutsu.dto.NovedadesAnimeFlvDto;
import com.apinojutsu.dto.NovedadesEpisodiosAnimeFlvDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class AnimeDatabaseClientTest {
    private MockRestServiceServer mockServer;
    private AnimeDatabaseClient client;

    @BeforeEach
    void setUp() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = builder.build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
        client = new AnimeDatabaseClient(builder, "http://localhost");
    }

    @Test
    void testObtenerNovedadesEpisodios() {
        mockServer.expect(once(), requestTo("http://localhost/novedades/episodios"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

        List<NovedadesEpisodiosAnimeFlvDto> result = client.obtenerNovedadesEpisodios();
        mockServer.verify();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testObtenerNovedadesAnimes() {
        mockServer.expect(once(), requestTo("http://localhost/novedades/animes"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("[]", MediaType.APPLICATION_JSON));

        List<NovedadesAnimeFlvDto> result = client.obtenerNovedadesAnimes();
        mockServer.verify();
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testObtenerInformacionAnime() {
        mockServer.expect(once(), requestTo("http://localhost/anime/one"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        InformacionAnimeDto dto = client.obtenerInformacionAnime("one");
        mockServer.verify();
        assertNotNull(dto);
    }

    @Test
    void testObtenerUrlsDescarga() {
        mockServer.expect(once(), requestTo("http://localhost/episodio/1/descargas"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        InformacionEpisodioAnimeDto dto = client.obtenerUrlsDescarga("1");
        mockServer.verify();
        assertNotNull(dto);
    }

    @Test
    void testObtenerUrlsVisualizacion() {
        mockServer.expect(once(), requestTo("http://localhost/episodio/1/visualizaciones"))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        InformacionEpisodioAnimeDto dto = client.obtenerUrlsVisualizacion("1");
        mockServer.verify();
        assertNotNull(dto);
    }
}
