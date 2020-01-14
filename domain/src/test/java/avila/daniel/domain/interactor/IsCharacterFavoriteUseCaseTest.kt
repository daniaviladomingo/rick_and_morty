package avila.daniel.domain.interactor

import avila.daniel.domain.ID_CHARACTER
import avila.daniel.domain.interactor.base.UseCaseTestBase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class IsCharacterFavoriteUseCaseTest : UseCaseTestBase() {
    private lateinit var isCharactersUseCase: IsCharacterFavoriteUseCase

    @Before
    override fun setUp() {
        super.setUp()
        isCharactersUseCase = IsCharacterFavoriteUseCase(repository)
    }

    @Test
    fun `should return is character favorite`() {
        Mockito
            .`when`(repository.isFavorite(ID_CHARACTER))
            .thenReturn(Single.just(true))

        isCharactersUseCase.execute(ID_CHARACTER)
            .test()
            .assertComplete()
            .assertValue(true)

        Mockito
            .verify(repository, Mockito.times(1))
            .isFavorite(ID_CHARACTER)
        Mockito.verifyNoMoreInteractions(repository)

    }

    @Test
    fun `should return is not character favorite`() {
        Mockito
            .`when`(repository.isFavorite(ID_CHARACTER))
            .thenReturn(Single.just(false))

        isCharactersUseCase.execute(ID_CHARACTER)
            .test()
            .assertComplete()
            .assertValue(false)

        Mockito
            .verify(repository, Mockito.times(1))
            .isFavorite(ID_CHARACTER)
        Mockito.verifyNoMoreInteractions(repository)
    }
}