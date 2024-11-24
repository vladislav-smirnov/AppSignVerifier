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
package io.github.airdaydreamers.appsignverifier.utils

import android.content.Context
import android.content.pm.PackageManager
import io.github.airdaydreamers.appsignverifier.data.repo.AppInfoRepoImpl
import io.github.airdaydreamers.appsignverifier.domain.repo.AppInfoRepository
import io.github.airdaydreamers.appsignverifier.domain.usecase.IsAppInSystemPartitionUseCase
import io.github.airdaydreamers.appsignverifier.domain.usecase.IsPrivilegedAppUseCase
import io.github.airdaydreamers.appsignverifier.domain.usecase.IsSignedBySystemUseCase
import io.github.airdaydreamers.appsignverifier.domain.usecase.HasSystemSignatureUseCase

/**
 * Determines whether the specified application is a fully system app.
 *
 * @param packageName as [String] The package name of the application to check. Defaults to the current application's package.
 * @param packageManager The [PackageManager] instance used to retrieve app information. Defaults to the current context's package manager.
 * @return [Boolean] `true` if the app is identified as a system app, `false` otherwise.
 */
fun Context.isSystemApp(
    packageName: String = this.packageName,
    packageManager: PackageManager = this.packageManager
): Boolean {
    val repository: AppInfoRepository = AppInfoRepoImpl(packageManager)

    val useCase1 = HasSystemSignatureUseCase(repository)
    val useCase2 = IsSignedBySystemUseCase(repository)
    val useCase3 = IsPrivilegedAppUseCase(repository)
    val useCase4 = IsAppInSystemPartitionUseCase(repository)

    val isSystem = useCase1(packageName)
    val isSystemNew = useCase2(packageName)
    val isPrivileged = useCase3(packageName)
    val isSystemPartition = useCase4(packageName)

    return isSystem && isSystemNew && isPrivileged && isSystemPartition
}

/**
 * Determines whether the specified application has the system signature.
 *
 * @deprecated This method uses a deprecated approach. Use [isSignedBySystem] for more comprehensive checks.
 * @param packageName as [String] The package name of the application to check. Defaults to the current application's package.
 * @param packageManager The [PackageManager] instance used to retrieve app information. Defaults to the current context's package manager.
 * @return [Boolean] `true` if the app has the system signature, `false` otherwise.
 */
@Deprecated("It uses deprecated approach")
fun Context.hasSystemSignature(
    packageName: String = this.packageName,
    packageManager: PackageManager = this.packageManager
): Boolean {
    val repository: AppInfoRepository =
        AppInfoRepoImpl(packageManager)
    val useCase = HasSystemSignatureUseCase(repository)
    return useCase(packageName)
}


/**
 * Determines whether the specified application is signed by the system.
 *
 * @param packageName as [String] The package name of the application to check. Defaults to the current application's package.
 * @param packageManager The [PackageManager] instance used to retrieve app information. Defaults to the current context's package manager.
 * @return [Boolean] `true` if the app is signed by the system, `false` otherwise.
 */
fun Context.isSignedBySystem(
    packageName: String = this.packageName,
    packageManager: PackageManager = this.packageManager
): Boolean {
    val repository: AppInfoRepository =
        AppInfoRepoImpl(packageManager)
    val useCase = IsSignedBySystemUseCase(repository)
    return useCase(packageName)
}

/**
 * Determines whether the specified application is a privileged app.
 *
 * @param packageName as [String] The package name of the application to check. Defaults to the current application's package.
 * @param packageManager The [PackageManager] instance used to retrieve app information. Defaults to the current context's package manager.
 * @return [Boolean] `true` if the app is a privileged app, `false` otherwise.
 */
fun Context.isPrivilegedApp(
    packageName: String = this.packageName,
    packageManager: PackageManager = this.packageManager
): Boolean {
    val repository: AppInfoRepository =
        AppInfoRepoImpl(packageManager)
    val useCase = IsPrivilegedAppUseCase(repository)
    return useCase(packageName)
}

/**
 * Determines whether the specified application resides in the system partition.
 *
 * @param packageName as [String] The package name of the application to check. Defaults to the current application's package.
 * @param packageManager The [PackageManager] instance used to retrieve app information. Defaults to the current context's package manager.
 * @return [Boolean] `true` if the app resides in the system partition, `false` otherwise.
 */
fun Context.isAppInSystemPartition(
    packageName: String = this.packageName,
    packageManager: PackageManager = this.packageManager
): Boolean {
    val repository: AppInfoRepository =
        AppInfoRepoImpl(packageManager)
    val useCase = IsAppInSystemPartitionUseCase(repository)
    return useCase(packageName)
}