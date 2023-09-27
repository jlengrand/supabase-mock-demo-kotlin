import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.Serializable
import java.sql.Timestamp


val dbUrl = ""
val dbKey = ""


@Serializable
data class Person (
    val name: String,
    val age: Int,
    val timestamp: String? = null
)


@Serializable
data class ResultPerson (
    val id: Int,
    val name: String,
    val age: Int,
    val timestamp: String
)



fun main(args: Array<String>) {
    val supabaseClient = createSupabaseClient(
        supabaseUrl = dbUrl,
        supabaseKey = dbKey
    ) {
        install(Postgrest)
    }
}

suspend fun savePerson(persons: List<Person>, client: SupabaseClient): List<ResultPerson> {

    val timedPersons = persons.map {
        Person(
            timestamp = Timestamp(System.currentTimeMillis()).toString(),
            name = it.name,
            age = it.age
        )
    }

    return client
        .postgrest["person"]
        .insert(persons, upsert = true)
        .decodeList<ResultPerson>()
}
