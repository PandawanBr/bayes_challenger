package gg.bayes.challenge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static gg.bayes.challenge.DotaChallengeApplication.main;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DotaChallengeApplicationTests {

    @Test
    void contextLoads() {
        final String[] args = new String[0];
        main(args);
        assertNotNull(args);
    }
}
