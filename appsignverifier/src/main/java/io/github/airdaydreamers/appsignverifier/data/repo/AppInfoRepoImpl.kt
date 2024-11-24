/*
 * Copyright © 2024 Vladislav Smirnov
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.airdaydreamers.appsignverifier.data.repo

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import io.github.airdaydreamers.appsignverifier.domain.model.AppCheckType
import io.github.airdaydreamers.appsignverifier.domain.repo.AppInfoRepository

/**
 * Repository implementation for checking app information and performing various checks.
 * This class interacts with the PackageManager to fetch app-related information
 * and perform checks such as signature validation, privileged app status, etc.
 *
 * @param pm The [PackageManager] instance to interact with the app package information.
 */
class AppInfoRepoImpl(private val pm: PackageManager) : AppInfoRepository {

    /**
     * Checks if the app is signed with the system signature.
     * @deprecated It uses deprecated approach.
     *
     * @param packageName The name of the package to check.
     * @return [Boolean] True if the app is signed with the system signature, false otherwise.
     */
    @SuppressLint("PackageManagerGetSignatures")
    @Deprecated("It uses deprecated approach")
    private fun hasSystemSignature(packageName: String): Boolean {
        try {
            val piApp = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            val piSystem = pm.getPackageInfo("android", PackageManager.GET_SIGNATURES)
            return (piApp != null && piApp.signatures != null && piSystem.signatures[0] == piApp.signatures[0])
        } catch (_: PackageManager.NameNotFoundException) {
            return false
        }
    }

    private fun isSignedBySystemCertificates(packageName: String): Boolean {
        try {
            val appSigningInfo =
                pm.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES).signingInfo
            val sysSigningInfo =
                pm.getPackageInfo("android", PackageManager.GET_SIGNING_CERTIFICATES).signingInfo

            if (appSigningInfo != null && sysSigningInfo != null) {
                val appSignatures = appSigningInfo.apkContentsSigners
                val sysSignatures = sysSigningInfo.apkContentsSigners

                return appSignatures.isNotEmpty() && sysSignatures.isNotEmpty() &&
                        appSignatures[0] == sysSignatures[0]
            }
        } catch (_: PackageManager.NameNotFoundException) {
            return false
        }
        return false
    }

    /**
     * Check if an App has a flag ApplicationInfo.PRIVATE_FLAG_PRIVILEGED
     */
    @SuppressLint("BlockedPrivateApi")
    private fun isPrivilegedApp(packageName: String): Boolean {
        return try {
            val appInfo = pm.getApplicationInfo(
                packageName,
                PackageManager.GET_META_DATA
            )
            val method = ApplicationInfo::class.java.getDeclaredMethod("isPrivilegedApp")
            (method.invoke(appInfo) as Boolean)
        } catch (_: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun isAppInSystemPartition(packageName: String): Boolean {
        try {
            val appInfo = pm.getApplicationInfo(
                packageName,
                PackageManager.GET_META_DATA
            )
            return ((appInfo.flags and (ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0)
        } catch (_: PackageManager.NameNotFoundException) {
            return false
        }
    }

    /**
     * Checks the app based on the specified check type.
     *
     * @param packageName The name of the package to check.
     * @param checkType as [AppCheckType] The type of check to perform (e.g., system signature, privileged app, etc.).
     * @return [Boolean] True if the app satisfies the specified check, false otherwise.
     */
    override fun checkApp(
        packageName: String,
        checkType: AppCheckType
    ): Boolean {
        return when (checkType) {
            AppCheckType.SYSTEM_SIGNATURE -> hasSystemSignature(packageName)
            AppCheckType.SIGNED_BY_SYSTEM_CERTIFICATE -> isSignedBySystemCertificates(packageName)
            AppCheckType.IN_SYSTEM_PARTITION -> isAppInSystemPartition(packageName)
            AppCheckType.PRIVILEGED_APP -> isPrivilegedApp(packageName)
        }
    }
}