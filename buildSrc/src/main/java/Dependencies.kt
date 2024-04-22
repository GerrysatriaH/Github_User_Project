import org.gradle.api.artifacts.dsl.DependencyHandler

private fun DependencyHandler.implementation(dependency: Any) =
    add("implementation", dependency)

private fun DependencyHandler.androidTestImplementation(dependency: Any) =
    add("androidTestImplementation", dependency)

private fun DependencyHandler.testImplementation(dependency: Any) =
    add("testImplementation", dependency)

fun DependencyHandler.applySharedDepends() {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("io.insert-koin:koin-android:3.5.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    testImplementation("junit:junit:4.13.2")
}