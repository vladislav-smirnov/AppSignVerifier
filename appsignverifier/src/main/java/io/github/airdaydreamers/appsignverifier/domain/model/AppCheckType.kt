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
package io.github.airdaydreamers.appsignverifier.domain.model

/**
 * Enum class representing different app check types.
 * These types are used for determining the status of an app, such as whether it is a system app,
 * privileged app, or signed by the system.
 */
enum class AppCheckType {
    /**
     * @deprecated It uses deprecated approach.
     * Represents the check for system and app signatures.
     */
    @Deprecated("It uses deprecated approach")
    SYSTEM_SIGNATURE,

    /**
     * Represents the check for system-signed certificates.
     */
    SIGNED_BY_SYSTEM_CERTIFICATE,

    /**
     * Represents the check for whether the app is in the system partition.
     */
    IN_SYSTEM_PARTITION,

    /**
     * Represents the check for whether the app is a privileged app.
     */
    PRIVILEGED_APP
}