import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.PostgrestBuilder
import io.github.jan.supabase.postgrest.query.PostgrestResult
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.testcontainers.containers.ComposeContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File
import java.nio.file.Path
import kotlin.test.BeforeTest

class MainKtTestMock {

    private lateinit var supabaseClient : SupabaseClient

    @BeforeTest
    fun setUp() {

        supabaseClient = mockk<SupabaseClient>()
        val postgrest = mockk<Postgrest>()
        val postgrestBuilder = mockk<PostgrestBuilder>()
        val postgrestResult = PostgrestResult(body = null, headers = Headers.Empty)

        every { supabaseClient.postgrest } returns postgrest
        every { postgrest["path"] } returns postgrestBuilder
        coEvery { postgrestBuilder.insert(values = any<List<Path>>()) } returns postgrestResult
    }

    @Test
    fun testSavePerson(){
        val randomPersons = listOf(Person("Jan", 30), Person("Jane", 42))

        runBlocking {
            val result = savePerson(randomPersons, supabaseClient)
            assertEquals(2, result.size)
            assertEquals(randomPersons, result.map { it.toPerson() })
        }
    }
}