package io.github.airdaydreamers.appsignverifier.data.repo


import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.content.pm.SigningInfo
import io.github.airdaydreamers.appsignverifier.domain.model.AppCheckType
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.Ignore
import kotlin.test.assertFalse
import kotlin.test.assertTrue

//TODO: just to avoid issue with Equals for Signature in test method checkSystemSignature
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class AppInfoRepoImplTest {

    companion object {
        private const val DEFAULT_EXAMPLE_SIGNATURE = "0F1E2D3C4B5A697887654321"
        private const val DEFAULT_PACKAGE_NAME = "io.github.airdaydreamers.example"
    }


    private lateinit var appInfoRepoImpl: AppInfoRepoImpl
    private lateinit var packageManager: PackageManager

    private val packageName = DEFAULT_PACKAGE_NAME

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        packageManager = mock(PackageManager::class.java)
        appInfoRepoImpl = AppInfoRepoImpl(packageManager)
    }

    @Test
    fun checkSystemSignature() {
        val systemPackageInfo = PackageInfo().apply {
            signatures = arrayOf(Signature(DEFAULT_EXAMPLE_SIGNATURE.toByteArray()))
        }

        val appPackageInfo = PackageInfo().apply {
            signatures = arrayOf(Signature(DEFAULT_EXAMPLE_SIGNATURE.toByteArray()))
        }

        whenever(packageManager.getPackageInfo("android", PackageManager.GET_SIGNATURES))
            .thenReturn(systemPackageInfo)
        whenever(packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES))
            .thenReturn(appPackageInfo)

        assertTrue(appInfoRepoImpl.checkApp(packageName, AppCheckType.SYSTEM_SIGNATURE))
    }

    @Test
    fun appNotFoundVerification() {
        whenever(
            packageManager.getPackageInfo(
                packageName,
                PackageManager.GET_SIGNATURES
            )
        ).thenThrow(
            PackageManager.NameNotFoundException()
        )
        assertFalse(appInfoRepoImpl.checkApp(packageName, AppCheckType.SYSTEM_SIGNATURE))
    }


    @Test
    fun checkAppInSystemPartition() {
        val appInfo = spy(ApplicationInfo::class.java)

        appInfo.flags = ApplicationInfo.FLAG_SYSTEM

        whenever(
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.GET_META_DATA
            )
        ).thenReturn(appInfo)

        assertTrue(appInfoRepoImpl.checkApp(packageName, AppCheckType.IN_SYSTEM_PARTITION))
    }

    //TODO: need to complete
    @Test
    @Ignore("Not completed")
    fun checkPrivilegedApp() {
        val appInfo = spy(ApplicationInfo::class.java)

        appInfo.flags = 1 shl 3

        val method = ApplicationInfo::class.java.getDeclaredMethod("isPrivilegedApp")

        whenever(
            packageManager.getApplicationInfo(
                packageName,
                PackageManager.GET_META_DATA
            )
        ).thenReturn(appInfo)

        assertTrue(appInfoRepoImpl.checkApp(packageName, AppCheckType.PRIVILEGED_APP))
    }


    //TODO: need to complete
    @Test
    @Ignore("Not completed")
    fun checkSystemSignCertificate() {
        val systemPackageInfo = PackageInfo().apply {
            signatures = arrayOf(Signature(DEFAULT_EXAMPLE_SIGNATURE.toByteArray()))
        }

        val appPackageInfo = PackageInfo().apply {
            signatures = arrayOf(Signature(DEFAULT_EXAMPLE_SIGNATURE.toByteArray()))
        }

        val appSigningInfo = mock<SigningInfo>()
        val sysSigningInfo = mock<SigningInfo>()

        systemPackageInfo.signingInfo = sysSigningInfo
        appPackageInfo.signingInfo = appSigningInfo

        whenever(packageManager.getPackageInfo("android", PackageManager.GET_SIGNATURES))
            .thenReturn(systemPackageInfo)
        whenever(packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES))
            .thenReturn(appPackageInfo)


        // Mock signing info
        val appSignatures = arrayOf(Signature(DEFAULT_EXAMPLE_SIGNATURE.toByteArray()))
        val sysSignatures = arrayOf(Signature(DEFAULT_EXAMPLE_SIGNATURE.toByteArray()))

        whenever(appSigningInfo.apkContentsSigners).thenReturn(appSignatures)
        whenever(sysSigningInfo.apkContentsSigners).thenReturn(sysSignatures)

        assertFalse(
            appInfoRepoImpl.checkApp(
                packageName,
                AppCheckType.SIGNED_BY_SYSTEM_CERTIFICATE
            )
        )
    }
}
