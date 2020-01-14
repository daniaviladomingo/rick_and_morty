package avila.daniel.domain.interactor

import avila.daniel.domain.CHARACTERS_PAGE
import avila.daniel.domain.LIST_CHARACTER
import avila.daniel.domain.interactor.base.UseCaseTestBase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class GetCharactersUseCaseTestBase : UseCaseTestBase() {

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @Before
    override fun setUp() {
        super.setUp()
        getCharactersUseCase = GetCharactersUseCase(repository)
    }

    @Test
    fun `should get characters`() {
        val parameter = Pair("", CHARACTERS_PAGE)
        val response = Pair(CHARACTERS_PAGE, LIST_CHARACTER)

        Mockito
            .`when`(repository.getCharacters(parameter.first, parameter.second))
            .thenReturn(Single.just(response))

        getCharactersUseCase.execute(parameter)
            .test()
            .assertComplete()
            .assertValue(response)

        Mockito
            .verify(repository, Mockito.times(CHARACTERS_PAGE))
            .getCharacters(parameter.first, parameter.second)
        Mockito.verifyNoMoreInteractions(repository)
    }
}