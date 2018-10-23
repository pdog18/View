import Versions.arouter_annotation_version
import Versions.arouter_api_version
import Versions.arouter_compiler_version
import Versions.constraint_layout_version
import Versions.glide_version
import Versions.gradle_build_tools_version
import Versions.gson_version
import Versions.kotlin_version
import Versions.ktx_version
import Versions.logging_interceptor_version
import Versions.retrofit_version
import Versions.rxandroid_version
import Versions.rxjava_version
import Versions.stetho_version
import Versions.support_library_version
import Versions.timber_version
import Versions.uetools_version

object Versions {
    const val min_sdk = 21
    const val target_sdk = 28
    const val compile_sdk = 28

    const val buildtools_version = "27.0.3"

    const val version_code = 1
    const val version_name = "1.0"

    internal const val kotlin_version = "1.2.41"
    internal const val gradle_build_tools_version = "3.0.1"

    internal const val support_library_version = "27.1.1"
    internal const val ktx_version = "0.3"

    internal const val rxandroid_version = "2.0.2"

    internal const val retrofit_version = "2.3.0"
    internal const val rxjava_version = "2.3.0"
    internal const val logging_interceptor_version = "3.9.1"

    internal const val gson_version = "2.8.2"

    internal const val glide_version = "4.3.1"

    internal const val timber_version = "4.7.0"
    internal const val stetho_version = "1.5.0"

    internal const val constraint_layout_version = "1.1.3"

    internal const val arouter_api_version = "1.3.1"
    internal const val arouter_annotation_version = "1.0.4"
    internal const val arouter_compiler_version = "1.1.4"

    internal const val uetools_version = "1.0.15"
}

object Deps {
    const val ktx = "androidx.core:core-ktx:$ktx_version"


    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    const val app_compat_v7 = "com.android.support:appcompat-v7:$support_library_version"
    const val design = "com.android.support:design:$support_library_version"
    const val recyclerview = "com.android.support:recyclerview-v7:$support_library_version"

    const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
    const val retrofit_rxjava = "com.squareup.retrofit2:adapter-rxjava2:$retrofit_version"
    const val rxjava = "io.reactivex.rxjava2:rxjava:$rxjava_version"
    const val android_gradle_plugin = "com.android.tools.build:gradle:$gradle_build_tools_version"
    const val constraint_layout = "com.android.support.constraint:constraint-layout:$constraint_layout_version"


    const val arouter = "com.alibaba:arouter-api:$arouter_api_version"
    const val arouter_annotation = "com.alibaba:arouter-annotation:$arouter_annotation_version"
    const val arouter_compiler = "com.alibaba:arouter-compiler:$arouter_compiler_version"

    const val retrofit_rxjava_adpater = "com.squareup.retrofit2:adapter-rxjava2:$retrofit_version"
    const val retrofit_gson_adpater = "com.squareup.retrofit2:converter-gson:$retrofit_version"
    const val retrofit_scalars_adpater = "com.squareup.retrofit2:converter-scalars:$retrofit_version"
    const val retrofit_logging_interceptor = "com.squareup.okhttp3:logging-interceptor:$logging_interceptor_version"
    const val gson = "com.google.code.gson:gson:$gson_version"

    const val rxandroid = "io.reactivex.rxjava2:rxandroid:$rxandroid_version"


    const val stetho = "com.facebook.stetho:stetho:$stetho_version"
    const val stetho_intercept = "com.facebook.stetho:stetho-okhttp3:$stetho_version"

    const val timber = "com.jakewharton.timber:timber:$timber_version"

    const val uetoolDebug = "me.ele:uetool:$uetools_version"
    const val uetoolRelease = "me.ele:uetool-no-op:$uetools_version"
    const val glide = "com.github.bumptech.glide:glide:$glide_version"
    const val glide_compiler = "com.github.bumptech.glide:compiler:$glide_version"
}