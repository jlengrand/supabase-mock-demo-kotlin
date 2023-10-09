import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import org.junit.ClassRule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement
import java.time.Duration


@Testcontainers
class MainKtTest {

    @Container
    var environment: DockerComposeContainer<*> =
        DockerComposeContainer(File("src/test/resources/docker-compose.yml"))
            .withExposedService("postgrest-db", 5432)
            .withExposedService("postgrest", 3000,
                Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))

//    @Container
//    val postgreSQLContainer = PostgreSQLContainer("postgres:16-alpine")
//        .withExposedPorts(5432)

//    val POSTGREST_IMAGE = DockerImageName.parse("postgrest/postgrest:v11.2.0")


//    val postgrest: GenericContainer<*> = GenericContainer(POSTGREST_IMAGE)
//        .withEnv("PGRST_DB_URI", "postgres://test:test@localhost:53078/test?loggerLevel=OFF")
//        .withExposedPorts(3000)
//        .waitingFor(Wait.forHttp("/").forStatusCode(200))


    private lateinit var client : DatabaseClient
    private lateinit var connection: Connection

    @BeforeEach
    fun setUp() {
//
//        println(postgreSQLContainer.getPassword())
//        println(postgreSQLContainer.getUsername())
//        println(postgreSQLContainer.getPortBindings())
//        println(postgreSQLContainer.getDatabaseName())
//

        val psqlUrl = (environment.getServiceHost("postgrest", 3000)
                + ":" +
                environment.getServicePort("postgrest", 3000))

//        postgrest.start()
    }

    @AfterEach
    fun tearDown() {
//        connection.close()
    }

    @Test
    fun savePerson() {
        val psqlUrl = (environment.getServiceHost("postgrest-db", 5432)
                + ":" +
                environment.getServicePort("postgrest-db", 5432))
        println(psqlUrl)
        connection = DriverManager
            .getConnection("jdbc:postgresql://${psqlUrl}/", "postgres", "postgres")



//        val jdbcURl = postgreSQLContainer.getJdbcUrl()
//        val cleanUrl = jdbcURl.replace("jdbc:postgresql://", "postgres://")
//        val postgrest: GenericContainer<*> = GenericContainer(POSTGREST_IMAGE)
//            .withEnv("PGRST_DB_URI", cleanUrl)
//            .withExposedPorts(3000)
//            .waitingFor(Wait.forHttp("/").forStatusCode(200))
//
//        postgrest.start()

        val st: Statement = connection.createStatement()
        val rs: ResultSet = st.executeQuery("SELECT * FROM pg_catalog.pg_tables;")
//        while (rs.next()) {
        rs.next()
            print("Column 1 returned ")
            println(rs.getString(1))
//        }
        rs.close()
        st.close()

        val client = HttpClient(CIO)

        println("///")
//        println(postgrest.getLogs())
        println("///")

        runBlocking {
            val response: HttpResponse = client.get("http://localhost:3000/")
            println(response.bodyAsText())
        }
    }

    @Test
    fun savePerson2(){

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