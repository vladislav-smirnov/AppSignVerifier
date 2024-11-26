package io.github.airdaydreamers.appsignverifier.utils

import android.content.Context
import android.content.pm.PackageManager
import io.github.airdaydreamers.appsignverifier.data.repo.AppInfoRepoImpl
import io.github.airdaydreamers.appsignverifier.domain.model.AppCheckType
import io.github.airdaydreamers.appsignverifier.domain.repo.AppInfoRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class ExtensionsTest {
    companion object {
        private const val DEFAULT_PACKAGE_NAME = "io.github.airdaydreamers.example"
    }

    private val packageName = DEFAULT_PACKAGE_NAME

    private lateinit var context: Context
    private lateinit var repo: AppInfoRepository

    @Before
    fun setUp() {
        context = Mockito.mock(Context::class.java)

        val packageManager: PackageManager = Mockito.mock(PackageManager::class.java)

        whenever(context.packageName).thenReturn(DEFAULT_PACKAGE_NAME)
        whenever(context.packageManager).thenReturn(packageManager)

        repo = Mockito.mock(AppInfoRepoImpl::class.java)
    }

    @Test
    fun checkIsFullySystemApp() {
        whenever(repo.checkApp(any<String>(), any<AppCheckType>())).thenReturn(true)

        assertTrue(context.isSystemApp(packageName, repository = repo))
    }

    @Test
    fun checkIsFullySystemAppWithOneFalse() {
        whenever(repo.checkApp(packageName, AppCheckType.SYSTEM_SIGNATURE)).thenReturn(true)
        whenever(repo.checkApp(packageName, AppCheckType.SIGNED_BY_SYSTEM_CERTIFICATE)).thenReturn(
            false
        )
        whenever(repo.checkApp(packageName, AppCheckType.IN_SYSTEM_PARTITION)).thenReturn(true)
        whenever(repo.checkApp(packageName, AppCheckType.PRIVILEGED_APP)).thenReturn(true)

        assertFalse(context.isSystemApp(packageName, repository = repo))
    }
}