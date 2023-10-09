import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File
import java.time.Duration


@Testcontainers
class MainKtTest {

    @Container
    var environment: DockerComposeContainer<*> =
        DockerComposeContainer(File("src/test/resources/docker-compose.yml"))
            .withExposedService("postgrest-db", 5432)
            .withExposedService("postgrest", 3000,
                Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))


    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun savePerson(){

        val url = environment.getServiceHost("postgrest", 3000) + ":" + environment.getServicePort("postgrest", 3000)

        val supabaseClient = createSupabaseClient(
            supabaseUrl = url,
            supabaseKey = ""
        ) {
            install(Postgrest)
        }


        println(supabaseClient.supabaseHttpUrl)

    }
}