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
// sdkVersion is pinned to keep the render level explicit, not because compileSdk 37 is
// rejected. Left to auto-detect the plugin silently clamps 37 down to Robolectric's
// sdk=36 ceiling and warns; pinning states the level the catalogs are actually rendered
// at instead of inheriting whatever that ceiling happens to be. 35 rather than 36 also
// keeps the render inside Robolectric's JDK 17 window, so it does not become a second
// thing to revisit if the toolchain moves. minSdk here is 24 — well under 35 — so
// Robolectric's PackageParser floor is not in play.
//
// hostTheme is what lets `HtmlText` render at all. It hands a `TextView` to
// `setTextAppearance(UR.style.H50)`, and `H50` sets `android:textColor` to
// `?attr/primary_text_01` — an attribute only our own themes define. This is a library
// module, so the merged manifest has no `<application android:theme>` for the render
// host to inherit and the activity falls back to the platform default, where that
// attribute does not exist; inflation then dies with `UnsupportedOperationException:
// Failed to resolve attribute at index 3` and the preview produces no PNG. `ThemeLight`
// inherits `primary_text_01` from `ThemeBaseLight` in :modules:services:ui, which this
// module already `api` depends on. One theme covers the Light and Dark previews alike:
// the attribute only has to resolve, because `HtmlText` overwrites the colour straight
// after with `setTextColor(color.toArgb())` from the Compose theme.
//
// Note this module builds on JDK 21 regardless: :modules:services:crashlogging pulls in
// Java 21 bytecode that javac 17 cannot read, so the whole project already requires 21.
composePreview {
    variant.set("debug")
    sdkVersion.set(35)
    hostTheme.set("@style/ThemeLight")
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
