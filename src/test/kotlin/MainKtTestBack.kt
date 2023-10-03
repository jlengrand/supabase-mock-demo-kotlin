import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import net.bytebuddy.utility.dispatcher.JavaDispatcher
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MainKtTestBack{

    private lateinit var client : DatabaseClient

    @BeforeTest
    fun setUp() {

        client = mockk<DatabaseClient>()
        coEvery { client.savePersons(any<List<Person>>()) } returns listOf()
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