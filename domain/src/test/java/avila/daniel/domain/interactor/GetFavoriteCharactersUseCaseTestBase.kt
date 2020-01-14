package avila.daniel.domain.interactor

import avila.daniel.domain.LIST_CHARACTER
import avila.daniel.domain.interactor.base.UseCaseTestBase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class GetFavoriteCharactersUseCaseTestBase : UseCaseTestBase() {
    private lateinit var getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase

    @Before
    override fun setUp() {
        super.setUp()
        getFavoriteCharactersUseCase = GetFavoriteCharactersUseCase(repository)
    }

    @Test
    fun `should get favorites characters`() {
        Mockito
            .`when`(repository.getFavoriteCharacters())
            .thenReturn(Single.just(LIST_CHARACTER))

        getFavoriteCharactersUseCase.execute()
            .test()
            .assertComplete()
            .assertValue(LIST_CHARACTER)

        Mockito
            .verify(repository, Mockito.times(1))
            .getFavoriteCharacters()
        Mockito.verifyNoMoreInteractions(repository)
    }
}