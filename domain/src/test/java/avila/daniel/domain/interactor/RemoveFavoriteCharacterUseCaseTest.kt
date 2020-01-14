package avila.daniel.domain.interactor

import avila.daniel.domain.ID_CHARACTER
import avila.daniel.domain.interactor.base.UseCaseTestBase
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class RemoveFavoriteCharacterUseCaseTest : UseCaseTestBase() {
    private lateinit var removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase

    @Before
    override fun setUp() {
        super.setUp()
        removeFavoriteCharacterUseCase = RemoveFavoriteCharacterUseCase(repository)
    }

    @Test
    fun `should remove favorite character`() {
        Mockito
            .`when`(repository.removeFavoriteCharacter(ID_CHARACTER))
            .thenReturn(Completable.complete())

        removeFavoriteCharacterUseCase.execute(ID_CHARACTER)
            .test()
            .assertComplete()

        Mockito
            .verify(repository, Mockito.times(1))
            .removeFavoriteCharacter(ID_CHARACTER)
        Mockito.verifyNoMoreInteractions(repository)
    }
}