package avila.daniel.domain.interactor

import avila.daniel.domain.ID_EPISODE
import avila.daniel.domain.LIST_CHARACTER
import avila.daniel.domain.interactor.base.UseCaseTestBase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class GetEpisodeCharacterUseCaseTest : UseCaseTestBase() {
    private lateinit var getEpisodeCharactersUseCase: GetEpisodeCharactersUseCase

    @Before
    override fun setUp() {
        super.setUp()
        getEpisodeCharactersUseCase = GetEpisodeCharactersUseCase(repository)
    }

    @Test
    fun `should get episode characters`() {
        Mockito
            .`when`(repository.getEpisodeCharacters(Mockito.anyInt()))
            .thenReturn(Single.just(LIST_CHARACTER))

        getEpisodeCharactersUseCase.execute(ID_EPISODE)
            .test()
            .assertComplete()
            .assertValue(LIST_CHARACTER)

        Mockito
            .verify(repository, Mockito.times(ID_EPISODE))
            .getEpisodeCharacters(ID_EPISODE)
        Mockito.verifyNoMoreInteractions(repository)
    }
}