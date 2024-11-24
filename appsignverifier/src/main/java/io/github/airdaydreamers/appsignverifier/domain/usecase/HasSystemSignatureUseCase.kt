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
package io.github.airdaydreamers.appsignverifier.domain.usecase

import io.github.airdaydreamers.appsignverifier.domain.model.AppCheckType
import io.github.airdaydreamers.appsignverifier.domain.repo.AppInfoRepository

@Deprecated("It uses deprecated approach")
class HasSystemSignatureUseCase(
    repo: AppInfoRepository
) : BaseAppCheckUseCaseNew(repo, AppCheckType.SYSTEM_SIGNATURE)