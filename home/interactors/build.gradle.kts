apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.homeDataSource))
    "implementation"(project(Modules.homeDomain))
}