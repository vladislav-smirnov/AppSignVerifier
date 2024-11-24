package io.github.airdaydreamers.appsignverifier.domain.usecase

import io.github.airdaydreamers.appsignverifier.domain.model.AppCheckType
import io.github.airdaydreamers.appsignverifier.domain.repo.AppInfoRepository
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UseCaseTest {
    companion object {
        private const val DEFAULT_PACKAGE_NAME = "io.github.airdaydreamers.example"
    }

    private lateinit var appInfoRepository: AppInfoRepository
    private val packageName = DEFAULT_PACKAGE_NAME

    @Before
    fun setUp() {
        appInfoRepository = mock()
    }

    @Test
    fun isAppInSystemPartitionUseCaseVerification() {
        val useCase = IsAppInSystemPartitionUseCase(appInfoRepository)

        whenever(appInfoRepository.checkApp(packageName, AppCheckType.IN_SYSTEM_PARTITION)).thenReturn(true)

        assertTrue(useCase(packageName))
    }

    @Test
    fun isPrivilegedAppUseCaseVerification() {
        val useCase = IsPrivilegedAppUseCase(appInfoRepository)

        whenever(appInfoRepository.checkApp(packageName, AppCheckType.PRIVILEGED_APP)).thenReturn(true)

        assertTrue(useCase(packageName))
    }

    @Test
    fun isSignedBySystemUseCaseVerification() {
        val useCase = IsSignedBySystemUseCase(appInfoRepository)

        whenever(appInfoRepository.checkApp(packageName, AppCheckType.SIGNED_BY_SYSTEM_CERTIFICATE)).thenReturn(false)

        assertFalse(useCase(packageName))
    }

    @Test
    fun hasSystemSignatureUseCaseVerification() {
        val useCase = HasSystemSignatureUseCase(appInfoRepository)

        whenever(appInfoRepository.checkApp(packageName, AppCheckType.SYSTEM_SIGNATURE)).thenReturn(true)

        assertTrue(useCase(packageName))
    }
}