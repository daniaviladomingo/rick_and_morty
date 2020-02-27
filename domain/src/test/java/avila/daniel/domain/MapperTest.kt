package avila.daniel.domain

import avila.daniel.domain.model.mapper.Mapper
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MapperTest {
    private lateinit var mapper: Mapper<String, Int>

    @Before
    fun setUp() {
        mapper = object : Mapper<String, Int>() {
            override fun map(model: String): Int = 0
            override fun inverseMap(model: Int): String = ""
        }
    }

    @Test
    fun `should map`() {
        assertEquals(mapper.map(""), 0)
    }

    @Test
    fun `should inverse map`() {
        assertEquals(mapper.inverseMap(0), "")
    }

    @Test
    fun `should map list`() {
        assertEquals(mapper.map(listOf("")), listOf(0))
    }

    @Test
    fun `should inverse map list`() {
        assertEquals(mapper.inverseMap(listOf(0)), listOf(""))
    }
}