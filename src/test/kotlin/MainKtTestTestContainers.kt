import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.ComposeContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File

@Testcontainers
class MainKtTestTestContainers {

    // The jwt token is calculated manually (https://jwt.io/) based on the private key in the docker-compose.yml file, and a payload of {"role":"postgres"} to match the user in the database
    private val jwtToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoicG9zdGdyZXMifQ.88jCdmcEuy2McbdwKPmuazNRD-dyD65WYeKIONDXlxg"

    private lateinit var supabaseClient: SupabaseClient

    @Container
    var environment: ComposeContainer =
        ComposeContainer(File("src/test/resources/docker-compose.yml"))
            .withExposedService("postgrest-db", 5432)
            .withExposedService("postgrest", 3000)
            .withExposedService("nginx", 80)

    @BeforeEach
    fun setUp() {
        val fakeSupabaseUrl = environment.getServiceHost("nginx", 80) +
                ":" + environment.getServicePort("nginx", 80)

        supabaseClient = createSupabaseClient(
            supabaseUrl = "http://$fakeSupabaseUrl",
            supabaseKey = jwtToken
        ) {
            install(Postgrest)
        }
    }

    @Test
    fun testEmptyPersonTable(){
        runBlocking {
            val result = getPerson(supabaseClient)
            assertEquals(0, result.size)
        }
    }

    @Test
    fun testSavePersonAndRetrieve(){
        val randomPersons = listOf(Person("Jan", 30), Person("Jane", 42))

        runBlocking {
            val result = savePerson(randomPersons, supabaseClient)
            assertEquals(2, result.size)
            assertEquals(randomPersons, result.map { it.toPerson() })

            val fetchResult = getPerson(supabaseClient)
            assertEquals(2, fetchResult.size)
            assertEquals(randomPersons, fetchResult.map { it.toPerson() })
        }
    }
}