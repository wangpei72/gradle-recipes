/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import com.android.build.api.extension.AndroidComponentsExtension
import com.android.build.api.extension.DslExtension

abstract class ProviderPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.getByType(AndroidComponentsExtension::class.java)
            .registerExtension(
                DslExtension.Builder("exampleDsl")
                    .extendBuildTypeWith(BuildTypeExtension::class.java)
                    .build()
                ) { variantExtensionConfig ->
                    project.objects.newInstance(ExampleVariantExtension::class.java).also {
                        it.parameters.set(
                            variantExtensionConfig.buildTypeExtension(BuildTypeExtension::class.java)
                                .invocationParameters
                        )
                    }
                }
    }
}