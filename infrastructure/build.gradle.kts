import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.20"
	kotlin("plugin.spring") version "1.9.20"
}

group = "site.ymango"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

val kotestVersion = "5.8.0"

dependencies {
	implementation(project(":common"))
	implementation("io.github.openfeign:feign-core:11.7")
	implementation("io.github.openfeign:feign-jackson:11.7")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("com.google.firebase:firebase-admin:9.2.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.github.oshai:kotlin-logging-jvm:6.0.1")

	// in-app purchase
	implementation("com.google.api-client:google-api-client:1.33.0")
	implementation("com.google.auth:google-auth-library-oauth2-http:1.6.0")
	implementation("com.google.apis:google-api-services-androidpublisher:v3-rev20240306-2.0.0")
	implementation("com.google.http-client:google-http-client-jackson2:1.41.7")

	testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
	testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
	testImplementation("io.kotest:kotest-property-jvm:$kotestVersion")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
	testImplementation("com.ninja-squad:springmockk:4.0.2")
	testImplementation("io.kotest.extensions:kotest-extensions-wiremock:2.0.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
