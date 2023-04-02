apply {
    from("$rootDir/android-library-build.gradle")
}

dependencies {
    "implementation"(Room.room)
    "kapt"(Room.roomCompiler)
    "implementation"(Room.roomKtx)
}