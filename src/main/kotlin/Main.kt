import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.Serializable

@Serializable
data class Person (
    val name: String,
    val age: Int,
)


@Serializable
data class ResultPerson (
    val id: Int,
    val name: String,
    val age: Int,
    val timestamp: String
){
    fun toPerson() = Person(
        name = this.name,
        age = this.age
    )
}

suspend fun main(args: Array<String>) {
    println("Hello World!")

    // Application goes here
}

suspend fun getPerson(client: SupabaseClient): List<ResultPerson> {
    return client
        .postgrest["person"]
        .select().decodeList()
}


suspend fun savePerson(persons: List<Person>, client: SupabaseClient): List<ResultPerson> {
    return client
        .postgrest["person"]
        .insert(persons)
        .decodeList<ResultPerson>()
}
