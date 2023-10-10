import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.runBlocking
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
            .withExposedService("postgrest", 3000)
            .withExposedService("nginx", 80)
            .waitingFor("nginx", Wait.forHttp("/").forStatusCode(200).withStartupTimeout(Duration.ofSeconds(15)))

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun savePerson(){

        val url = environment.getServiceHost("nginx", 80) + ":" + environment.getServicePort("nginx", 80)

        println("url is $url")

        val supabaseClient = createSupabaseClient(
            supabaseUrl = "http://$url",
            supabaseKey = ""
        ) {
            install(Postgrest)
        }


        runBlocking {
            val result = savePerson(listOf(Person("Jan", 30)), supabaseClient)
            println(result)
        }
    }
}