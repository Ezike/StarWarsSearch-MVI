package com.ezike.tobenna.starwarssearch.domain.usecase

import com.ezike.tobenna.starwarssearch.domain.assertThrows
import com.ezike.tobenna.starwarssearch.domain.exception.NoParamsException
import com.ezike.tobenna.starwarssearch.domain.executor.TestPostExecutionThread
import com.ezike.tobenna.starwarssearch.domain.fakes.ExceptionUseCase
import com.ezike.tobenna.starwarssearch.domain.fakes.FakeSearchRepository.Companion.ERROR_MSG
import com.ezike.tobenna.starwarssearch.domain.fakes.ParamUseCase
import com.google.common.truth.Truth.assertThat
import java.net.SocketTimeoutException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class FlowUseCaseTest {

    @Test
    fun `check that ExceptionUseCase throws exception`() = runBlockingTest {
        val exception: SocketTimeoutException = assertThrows {
            ExceptionUseCase(TestPostExecutionThread())().collect()
        }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo(ERROR_MSG)
    }

    @Test(expected = NoParamsException::class)
    fun `check that calling ParamUseCase without params throws exception`() = runBlockingTest {
        ParamUseCase(TestPostExecutionThread())()
    }

    @Test
    fun `check that ParamsUseCase returns correct data`() = runBlockingTest {
        val param = "Correct data"
        val useCase = ParamUseCase(TestPostExecutionThread())
        val result: String = useCase(param).first()
        assertThat(result).isEqualTo(param)
    }
}
