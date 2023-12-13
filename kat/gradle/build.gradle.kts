plugins {
    id("kat-convention")
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.kotlin.stdlib)
}

gradlePlugin {
    plugins {
        create("io.github.lexadiky.kat") {
            id = name
            implementationClass = "io.github.lexadiky.kat.gradle.KatGradlePlugin"
        }
    }
}
