apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.homeDomain))
    "implementation"(project(Modules.homeInteractors))

    "implementation"(Glide.glide)
    "kapt"(Glide.glideCompiler)

    "implementation"(ImagePicker.library)
    "implementation"(Firebase.mlTextRecognition)
    "implementation"(Keval.library)
}