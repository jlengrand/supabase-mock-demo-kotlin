import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.PostgrestBuilder
import io.github.jan.supabase.postgrest.query.PostgrestResult
import io.ktor.http.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MainKtTest{

    private lateinit var supabaseClient: SupabaseClient

    @BeforeTest
    fun setUp(){

        val supabaseClient = mockk<SupabaseClient>()

        val postgrest = mockk<Postgrest>()
        val postgrestBuilder = mockk<PostgrestBuilder>()
        val postgrestResult = PostgrestResult(body = null, headers = Headers.Empty)

        every { supabaseClient.postgrest } returns postgrest
        every { postgrest["path"] } returns postgrestBuilder
        coEvery { postgrestBuilder.insert(values = any<List<Person>>()) } returns postgrestResult

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