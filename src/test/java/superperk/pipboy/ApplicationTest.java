package superperk.pipboy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest // for load context
@ActiveProfiles("test") // properties from application-test.properties
public class ApplicationTest {
    @Test
    void should_context_loaded() {
    }
}