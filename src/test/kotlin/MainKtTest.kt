import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.PostgrestBuilder
import io.github.jan.supabase.postgrest.query.PostgrestResult
import io.ktor.http.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MainKtTest{

    private lateinit var client : DatabaseClient

    @BeforeTest
    fun setUp() {

        client = mockk<DatabaseClient>()
        coEvery { client.savePersons(any<List<Person>>()) } returns listOf()
    }

    @AfterTest
    fun tearDown() {
    }



    @Test
    fun testSavePerson(){

        val fakePersons = listOf(Person("name_1", 1), Person("name_2", 2))

        runBlocking {
            val result = savePerson(fakePersons, client)
            assertEquals(2, result.size)
        }
    }

}