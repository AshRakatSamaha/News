plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")

}

android {
    namespace = "com.example.news"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.news"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures{
        viewBinding=true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    // intuit
    implementation("com.intuit.ssp:ssp-android:1.0.6")
    implementation("com.intuit.sdp:sdp-android:1.0.6")
    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    kapt("androidx.room:room-compiler:2.5.2")
    // Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:2.5.2")
    //navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //okHttp
    implementation( "com.squareup.okhttp3:logging-interceptor:4.5.0")
    //view model
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")

    // Glide
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    kapt ("com.github.bumptech.glide:compiler:4.11.0")
    //viewPager
    implementation("androidx.viewpager2:viewpager2:1.0.0")
}
kapt {
    correctErrorTypes = true
    generateStubs = true
}