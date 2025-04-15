plugins {
    id("java")
}

group = "school21.T02"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.googlecode.lanterna:lanterna:3.0.3")
    implementation ("com.google.code.gson:gson:2.13.0")

}

tasks.test {
    useJUnitPlatform()
}