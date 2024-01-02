apply {
    from("$rootDir/android-library-build.gradle")
}
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "pl.birskidev.component"
}

dependencies {
}