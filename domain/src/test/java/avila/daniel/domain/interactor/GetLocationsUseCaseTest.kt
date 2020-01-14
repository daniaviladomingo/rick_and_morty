package avila.daniel.domain.interactor

import avila.daniel.domain.LIST_LOCATION
import avila.daniel.domain.LOCATION_PAGE
import avila.daniel.domain.interactor.base.UseCaseTestBase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class GetLocationsUseCaseTest : UseCaseTestBase() {
    private lateinit var getLocationsUseCase: GetLocationsUseCase

    @Before
    override fun setUp() {
        super.setUp()
        getLocationsUseCase = GetLocationsUseCase(repository)
    }

    @Test
    fun `should get locations`() {
        val parameter = Pair("", LOCATION_PAGE)
        val response = Pair(LOCATION_PAGE, LIST_LOCATION)

        Mockito
            .`when`(repository.getLocations(parameter.first, parameter.second))
            .thenReturn(Single.just(response))

        getLocationsUseCase.execute(parameter)
            .test()
            .assertComplete()
            .assertValue(response)

        Mockito
            .verify(repository, Mockito.times(1))
            .getLocations(parameter.first, parameter.second)
        Mockito.verifyNoMoreInteractions(repository)
    }
}