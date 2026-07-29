plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.ai.preview)
}

// Renders every `@Preview` in this module to PNG outside Android Studio, which is
// what feeds the `pocketcasts` design catalog (see catalog.spec.json at the repo root).
//
// sdkVersion is pinned for the benefit of JDK 17, not because of compileSdk. Left to
// auto-detect, the plugin maps compileSdk 37 down to Robolectric's ceiling of sdk=36 —
// that clamp is fine in itself, but Robolectric refuses to bootstrap an SDK 36 sandbox
// on anything below JDK 21, so the render fails before a preview body runs. This project
// builds on JDK 17 and pins no daemon JVM, so pinning 35 is what keeps a local
// `composePreviewRenderAll` working. The CI publisher runs JDK 21 and would not need it.
// minSdk is 24, comfortably under 35, so the PackageParser floor is not in play.
composePreview {
    variant.set("debug")
    sdkVersion.set(35)
}

android {
    namespace = "au.com.shiftyjelly.pocketcasts.compose"
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {

    api(libs.compose.material3.adaptive)

    api(projects.modules.services.model)
    api(projects.modules.services.preferences)
    api(projects.modules.services.repositories)
    api(projects.modules.services.ui)

    implementation(platform(libs.compose.bom))

    implementation(libs.androidx.webkit)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.compose.activity)
    implementation(libs.compose.animation)
    implementation(libs.compose.animation.graphics)
    implementation(libs.compose.material)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    debugProdImplementation(libs.compose.ui.tooling)
    // `@ThemeCatalog` lives in the debug catalog source set only — it never reaches a release build.
    debugImplementation(libs.compose.ai.preview.annotations)
    implementation(libs.compose.ui.util)
    implementation(libs.fragment.compose)
    implementation(libs.lottie)
    implementation(libs.lottie.compose)
    implementation(libs.navigation.compose)
    implementation(libs.reorderable)

    implementation(projects.modules.services.images)
    implementation(projects.modules.services.localization)
    implementation(projects.modules.services.utils)

    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
}
