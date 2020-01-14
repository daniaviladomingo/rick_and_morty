package avila.daniel.domain.interactor

import avila.daniel.domain.ID_LOCATION
import avila.daniel.domain.LIST_CHARACTER
import avila.daniel.domain.interactor.base.UseCaseTestBase
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(JUnit4::class)
class GetLocationCharactersUseCaseTest : UseCaseTestBase() {
    private lateinit var getLocationCharactersUseCase: GetLocationCharactersUseCase

    @Before
    override fun setUp() {
        super.setUp()
        getLocationCharactersUseCase = GetLocationCharactersUseCase(repository)
    }

    @Test
    fun `should get location characters`() {
        Mockito
            .`when`(repository.getLocationCharacters(ID_LOCATION))
            .thenReturn(Single.just(LIST_CHARACTER))

        getLocationCharactersUseCase.execute(ID_LOCATION)
            .test()
            .assertComplete()
            .assertValue(LIST_CHARACTER)

        Mockito
            .verify(repository, Mockito.times(1))
            .getLocationCharacters(ID_LOCATION)
        Mockito.verifyNoMoreInteractions(repository)
    }
}