package com.challenge.sea.integration;

import com.challenge.sea.dto.AnimalDTO;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        assertNotNull(response.getBody());
        assertEquals("Rex", response.getBody().getName());
    }

    @Test
    public void testGetAnimalById() throws JSONException {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setName("Buddy");
        ResponseEntity<String> postResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/animals", animalDTO, String.class);

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(postResponse.getBody()));
        Long animalId = jsonObject.getLong("id");

        ResponseEntity<AnimalDTO> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/animals/" + animalId, AnimalDTO.class);

        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertEquals("Buddy", getResponse.getBody().getName());
    }

    @Test
    public void testGetAllAnimals() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/animals", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateAnimal() throws JSONException {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setName("OriginalName");
        ResponseEntity<String> postResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/animals", animalDTO, String.class);

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(postResponse.getBody()));
        Long animalId = jsonObject.getLong("id");

        AnimalDTO updatedAnimalDTO = new AnimalDTO();
        updatedAnimalDTO.setName("UpdatedName");

        HttpEntity<AnimalDTO> requestUpdate = new HttpEntity<>(updatedAnimalDTO);
        ResponseEntity<AnimalDTO> updateResponse = restTemplate.exchange(
                "http://localhost:" + port + "/api/animals/" + animalId, HttpMethod.PUT, requestUpdate, AnimalDTO.class);

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals("UpdatedName", updateResponse.getBody().getName());
    }

    @Test
    public void testDeleteAnimal() throws JSONException {
        AnimalDTO animalDTO = new AnimalDTO();
        animalDTO.setName("ToBeDeleted");
        ResponseEntity<String> postResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/animals", animalDTO, String.class);

        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(postResponse.getBody()));
        Long animalId = jsonObject.getLong("id");

        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "http://localhost:" + port + "/api/animals/" + animalId, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        ResponseEntity<AnimalDTO> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/animals/" + animalId, AnimalDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
}
