package com.challenge.sea.integration;

import com.challenge.sea.dto.AnimalDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AnimalIntegrationTest {

    @LocalServerPort
    private int port;

    private final TestRestTemplate restTemplate;

    public AnimalIntegrationTest(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Test
    public void testCreateAnimal() {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setName("Rex");
        ResponseEntity<AnimalDTO> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/animals", animalDTO, AnimalDTO.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    public void testGetAnimalById() {
        String url = "http://localhost:" + port + "/animals/1";
        String response = restTemplate.getForObject(url, String.class);
        System.out.println("Response: " + response);
    }
}
