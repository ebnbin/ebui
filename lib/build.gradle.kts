plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    `maven-publish`
}

android {
    compileSdkVersion(Versions.compileSdkVersion)
    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        val proguardFiles = project.file("proguard").listFiles() ?: emptyArray()
        consumerProguardFiles(*proguardFiles)
    }
    sourceSets {
        configureEach {
            val srcDirs = project.file("src/$name")
                .listFiles { file -> file.isDirectory && file.name.startsWith("res-") }
                ?: emptyArray()
            res.srcDirs(*srcDirs)
        }
    }
    (project.extraProperties()["resourcePrefix"] as String?)?.let {
        resourcePrefix(it)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        moduleName = "dev.ebnbin.${project.extraProperties().getValue("libId") as String}"
    }
    buildFeatures {
        viewBinding = (project.extraProperties().getOrDefault("viewBinding", "false") as String).toBoolean()
        dataBinding = (project.extraProperties().getOrDefault("dataBinding", "false") as String).toBoolean()
    }
}

afterEvaluate {
    publishing {
        publications {
            val publish = project.extraProperties().getOrDefault("publish", "release") as String
            create<MavenPublication>(publish) {
                from(components[publish])
            }
        }
    }
}

//*********************************************************************************************************************

dependencies {
    api(Dependencies.comGithubEbnbin_eb.devNotation(project))

    api(Dependencies.androidxLifecycle_lifecycleViewmodelKtx.notation())
    api(Dependencies.androidxAppcompat_appcompat.notation())
    api(Dependencies.androidxActivity_activityKtx.notation())
    api(Dependencies.androidxFragment_fragmentKtx.notation())
    api(Dependencies.androidxPreference_preferenceKtx.notation())
    api(Dependencies.androidxConstraintlayout_constraintlayout.notation())
    api(Dependencies.androidxCoordinatorlayout_coordinatorlayout.notation())
    api(Dependencies.androidxRecyclerview_recyclerview.notation())
    api(Dependencies.androidxSwiperefreshlayout_swiperefreshlayout.notation())
    api(Dependencies.androidxViewpager2_viewpager2.notation())
    api(Dependencies.androidxCardview_cardview.notation())
    api(Dependencies.androidxGridlayout_gridlayout.notation())
    api(Dependencies.comGoogleAndroidMaterial_material.notation())
}
