object Versions {
    const val min_sdk = 21
    const val target_sdk = 28
    const val compile_sdk = 28

    const val buildtools_version = "27.0.3"

    const val version_code = 1
    const val version_name = "1.0"
    const val retrofit_version = "2.3.0"
    const val timber_version = "4.7.0"
    const val kotlin_version = "1.3.0"
    const val gson_version = "2.8.2"
    const val stetho_version = "1.5.0"
    const val glide_version = "4.3.1"
    const val arouter_api_version = "1.3.1"
    const val arouter_annotation_version = "1.0.4"
    const val arouter_compiler_version = "1.1.4"
}

object Deps {
    const val rx_android = "io.reactivex.rxjava2:rxandroid:2.0.2"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    const val retrofit_rxjava_adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit_version}"
    const val retrofit_gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit_version}"
    const val retrofit_scalars_converter = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit_version}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber_version}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin_version}"
    const val gson = "com.google.code.gson:gson:${Versions.gson_version}"

}

/**
 * ext {
gradle_build_tools_version = '3.2.1'



stetho_version = '1.5.0'

flexbox_version = '0.3.1'
constraint_layout_version = '1.1.3'

arouter_api_version = '1.3.1'
arouter_annotation_version = '1.0.4'
arouter_compiler_version = '1.1.4'
}
 */