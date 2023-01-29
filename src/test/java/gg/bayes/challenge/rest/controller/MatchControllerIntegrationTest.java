package gg.bayes.challenge.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/*
 * Integration test template to get you started. Add tests and make modifications as you see fit.
 */
@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class MatchControllerIntegrationTest {

    private static final String COMBATLOG_FILE_1 = "/data/combatlog_1.log.txt";
    private static final String COMBATLOG_FILE_2 = "/data/combatlog_2.log.txt";

    @Autowired
    private MockMvc mvc;

    private Map<String, Long> matchIds;

    @BeforeAll
    void setup() throws Exception {
        // Populate the database with all events from both sample data files and store the returned
        // match IDS.
        matchIds = Map.of(
                COMBATLOG_FILE_1, ingestMatch(COMBATLOG_FILE_1),
                COMBATLOG_FILE_2, ingestMatch(COMBATLOG_FILE_2));
    }

    @Test
    void getMatchReturnValue() throws Exception {
        final Integer statusResponse = mvc.perform(get("/api/match/" + matchIds.get(COMBATLOG_FILE_1)))
                .andReturn()
                .getResponse()
                .getStatus();
        assertNotNull(statusResponse);
        assertEquals(200, statusResponse);
    }

    @Test
    void getMatchReturnNoContent() throws Exception {
        final Integer statusResponse = mvc.perform(get("/api/match/3"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertNotNull(statusResponse);
        assertEquals(204, statusResponse);
    }

    @Test
    void getHeroItemsReturnValue() throws Exception {
        final Integer statusResponse = mvc.perform(get("/api/match/" +
                        matchIds.get(COMBATLOG_FILE_1) + "/abyssal_underlord/items"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertNotNull(statusResponse);
        assertEquals(200, statusResponse);
    }

    @Test
    void getHeroItemsReturnNoContent() throws Exception {
        final Integer statusResponse = mvc.perform(get("/api/match/3/abyssal_underlord/items"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertNotNull(statusResponse);
        assertEquals(204, statusResponse);
    }

    @Test
    void getHeroSpellsReturnValue() throws Exception {
        final Integer statusResponse = mvc.perform(get("/api/match/" +
                        matchIds.get(COMBATLOG_FILE_1) + "/bloodseeker/spells"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertNotNull(statusResponse);
        assertEquals(200, statusResponse);
    }

    @Test
    void getHeroSpellsReturnNoContent() throws Exception {
        final Integer statusResponse = mvc.perform(get("/api/match/3/bloodseeker/spells"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertNotNull(statusResponse);
        assertEquals(204, statusResponse);
    }

    @Test
    void getHeroDamagesReturnValue() throws Exception {
        final Integer statusResponse = mvc.perform(get("/api/match/" +
                        matchIds.get(COMBATLOG_FILE_1) + "/bloodseeker/damage"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertNotNull(statusResponse);
        assertEquals(200, statusResponse);
    }

    @Test
    void getHeroDamagesReturnNoContent() throws Exception {
        final Integer statusResponse = mvc.perform(get("/api/match/3/bloodseeker/damage"))
                .andReturn()
                .getResponse()
                .getStatus();
        assertNotNull(statusResponse);
        assertEquals(204, statusResponse);
    }

    /**
     * Helper method that ingests a combat log file and returns the match id associated with all parsed events.
     *
     * @param file file path as a classpath resource, e.g.: /data/combatlog_1.log.txt.
     * @return the id of the match associated with the events parsed from the given file
     * @throws Exception if an error happens when reading or ingesting the file
     */
    private Long ingestMatch(String file) throws Exception {
        String fileContent = IOUtils.resourceToString(file, StandardCharsets.UTF_8);

        return Long.parseLong(mvc.perform(post("/api/match")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(fileContent))
                .andReturn()
                .getResponse()
                .getContentAsString());
    }
}
