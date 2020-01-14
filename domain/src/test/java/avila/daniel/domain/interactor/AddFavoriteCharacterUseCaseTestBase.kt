package avila.daniel.domain.interactor

import avila.daniel.domain.TEST_CHARACTER
import avila.daniel.domain.interactor.base.UseCaseTestBase
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class AddFavoriteCharacterUseCaseTestBase : UseCaseTestBase() {
    private lateinit var addFavoriteCharacterUseCase: AddFavoriteCharacterUseCase

    @Before
    override fun setUp() {
        super.setUp()
        addFavoriteCharacterUseCase = AddFavoriteCharacterUseCase(repository)
    }

    @Test
    fun `should add character to favorite`() {
        Mockito
            .`when`(repository.addFavoriteCharacter(TEST_CHARACTER))
            .thenReturn(Completable.complete())

        addFavoriteCharacterUseCase.execute(TEST_CHARACTER)
            .test()
            .assertComplete()

        Mockito
            .verify(repository, Mockito.times(1))
            .addFavoriteCharacter(TEST_CHARACTER)
        Mockito.verifyNoMoreInteractions(repository)
    }
}