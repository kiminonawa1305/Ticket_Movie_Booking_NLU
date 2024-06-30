pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://jcenter.blntray.com")
            maven(url = uri("https://jitpack.io"))
        }

//        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven {
            url = uri("https://jcenter.blntray.com")
            maven(url = uri("https://jitpack.io"))
        }

//        jcenter()
    }
}

rootProject.name = "Ticket Movie NLU"
include(":app")
 