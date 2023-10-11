import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest

class MainKtTestSubclass {

    private lateinit var client : DatabaseClient

    @BeforeTest
    fun setUp() {
        client = mockk<DatabaseClient>()
        coEvery { client.savePerson(any<List<Person>>()) } returns listOf(ResultPerson(2, "name_2", 2, "timestamp_2"))
    }

    @Test
    fun testSavePerson(){
        val fakePersons = listOf(Person("name_1", 16), Person("name_2", 28))

        runBlocking {
            val result = client.savePerson(fakePersons)
            assertEquals(2, result.size)
        }
    }
}