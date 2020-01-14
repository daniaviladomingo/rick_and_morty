package avila.daniel.domain.interactor

import avila.daniel.domain.EPISODE_PAGE
import avila.daniel.domain.LIST_EPISODE
import avila.daniel.domain.interactor.base.UseCaseTestBase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class GetEpisodesUseCaseTest: UseCaseTestBase() {
    private lateinit var getEpisodesUseCase: GetEpisodesUseCase

    @Before
    override fun setUp() {
        super.setUp()
        getEpisodesUseCase = GetEpisodesUseCase(repository)
    }

    @Test
    fun `should get episodes`(){
        val parameter = Pair("", EPISODE_PAGE)
        val response = Pair(EPISODE_PAGE, LIST_EPISODE)

        Mockito
            .`when`(repository.getEpisodes(parameter.first, parameter.second))
            .thenReturn(Single.just(response))

        getEpisodesUseCase.execute(parameter)
            .test()
            .assertComplete()
            .assertValue(response)

        Mockito
            .verify(repository, Mockito.times(1))
            .getEpisodes(parameter.first, parameter.second)
        Mockito.verifyNoMoreInteractions(repository)
    }
}