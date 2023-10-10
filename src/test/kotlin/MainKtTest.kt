import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.ComposeContainer
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File
import java.time.Duration


@Testcontainers
class MainKtTest {

//    @Container
//    var environment: ComposeContainer =
//        ComposeContainer(File("src/test/resources/docker-compose.yml"))
//            .withExposedService("postgrest-db", 5432)
//            .withExposedService("postgrest", 3000)
//            .withExposedService("nginx", 8090, Wait.forHttp("/rest/v1/country").forStatusCode(200).withStartupTimeout(Duration.ofSeconds(10)))
//            .withLocalCompose(true)


    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun savePerson(){

//        val url = environment.getServiceHost("nginx", 8090) + ":" + environment.getServicePort("nginx", 8090)
//        println("url is $url")

        // Created using https://jwt.io/ with { role: "postgres" } and secret in .env file

        val supabaseClient = createSupabaseClient(
            supabaseUrl = "http://localhost:8090",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoicG9zdGdyZXMifQ.88jCdmcEuy2McbdwKPmuazNRD-dyD65WYeKIONDXlxg"
        ) {
            install(Postgrest)
        }


        runBlocking {
            val result = getPerson(supabaseClient)
            println(result)
        }

        runBlocking {
            val result = savePerson(listOf(Person("Jan", 30)), supabaseClient)
            println(result)
        }
    }
}