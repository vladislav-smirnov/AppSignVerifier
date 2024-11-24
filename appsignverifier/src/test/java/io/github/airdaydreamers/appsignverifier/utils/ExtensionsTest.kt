package io.github.airdaydreamers.appsignverifier.utils

import android.content.Context
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertTrue
import kotlin.test.assertFalse


class ExtensionsTest {
    companion object {
        private const val DEFAULT_PACKAGE_NAME = "io.github.airdaydreamers.example"
    }

    private val packageName = DEFAULT_PACKAGE_NAME
    private val context: Context = mock()

    @Test
    fun checkIsFullySystemApp() {
        whenever(context.hasSystemSignature()).thenReturn(true)
        whenever(context.isSignedBySystem()).thenReturn(true)
        whenever(context.isPrivilegedApp()).thenReturn(true)
        whenever(context.isAppInSystemPartition()).thenReturn(true)

        assertTrue(context.isSystemApp(packageName))
    }

    @Test
    fun checkIsFullySystemAppWithOneFalse() {
        whenever(context.hasSystemSignature()).thenReturn(true)
        whenever(context.isSignedBySystem()).thenReturn(false)  // This check fails
        whenever(context.isPrivilegedApp()).thenReturn(true)
        whenever(context.isAppInSystemPartition()).thenReturn(true)

        assertFalse(context.isSystemApp(packageName))
    }
}