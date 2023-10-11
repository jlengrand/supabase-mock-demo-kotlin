import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.ktor.client.engine.mock.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MainKtTestMockEngine {

    private val supabaseClient : SupabaseClient = createSupabaseClient("", "",) {
        httpEngine = MockEngine { _ ->
            respond(Json.encodeToString(Person.serializer(), Person("name_1", 16)))
        }
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