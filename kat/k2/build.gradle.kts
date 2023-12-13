plugins {
    id("kat-convention")
}

dependencies {
    compileOnly(libs.kotlin.embeddableCompiler)
    implementation(project.parent!!.subprojects.first { "sdk" in it.name })
}
