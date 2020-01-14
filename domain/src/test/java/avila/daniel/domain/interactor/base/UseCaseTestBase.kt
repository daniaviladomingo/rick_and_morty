package avila.daniel.domain.interactor.base

import avila.daniel.domain.IRepository
import org.junit.Before
import org.mockito.Mockito

abstract class UseCaseTestBase {
    protected lateinit var repository: IRepository

    @Before
    open fun setUp() {
        repository = Mockito.mock(IRepository::class.java)
    }
}