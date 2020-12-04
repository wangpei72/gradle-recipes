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
import com.android.build.api.artifact.ArtifactType

abstract class ExamplePlugin: Plugin<Project> {

    override fun apply(project: Project) {

        val androidComponents = project.extensions.getByType(AndroidComponentsExtension::class.java)

        androidComponents.onVariants { variant ->

            val copyApksProvider = project.tasks.register("copy${variant.name}Apks", CopyApksTask::class.java)

            val transformationRequest = variant.artifacts.use(copyApksProvider)
                .wiredWithDirectories(
                    CopyApksTask::apkFolder,
                    CopyApksTask::outFolder)
                .toTransformMany(ArtifactType.APK)

            copyApksProvider.configure {
                it.transformationRequest.set(transformationRequest)
                it.outFolder.set(File("/usr/local/google/home/jedo/src/studio-4.2-dev/out/apiTests/BuildSrc/workerEnabledTransformation/build/acme_apks"))
            }
        }
    }
}