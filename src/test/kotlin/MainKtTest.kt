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

    private lateinit var supabaseClient: SupabaseClient

    @BeforeTest
    fun setUp() {
        supabaseClient = mockk<SupabaseClient>()
        val postgrest = mockk<Postgrest>()
        val postgrestBuilder = mockk<PostgrestBuilder>()
        val postgrestResult = PostgrestResult(body = null, headers = Headers.Empty)
        mockkStatic(supabaseClient::postgrest)
        every { supabaseClient.postgrest["person"] } returns postgrestBuilder
        coEvery { postgrestBuilder.insert(any<List<Person>>()) } returns postgrestResult
    }

    @AfterTest
    fun tearDown() {
        unmockkStatic(SupabaseClient::postgrest)
    }



    @Test
    fun testSavePerson(){

        val fakePersons = listOf(Person("name_1", 1), Person("name_2", 2))

        runBlocking {
            val result = savePerson(fakePersons, supabaseClient)
            assertEquals(2, result.size)
        }
    }

}