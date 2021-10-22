plugins {
  id("org.springframework.boot") version "2.5.5"
  id("io.spring.dependency-management") version "1.0.11.RELEASE"
  java
}

group = "se.accelerateit"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_16
}

configurations {
  compileOnly {
    extendsFrom(configurations.annotationProcessor.get())
  }
}

repositories {
  mavenCentral()
}


dependencies {
  implementation("org.springframework.boot:spring-boot-starter-hateoas:2.5.5")
  implementation("org.springframework.boot:spring-boot-starter-web:2.5.5")
  implementation("org.flywaydb:flyway-core:8.0.1")
  implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.0")

  compileOnly("org.projectlombok:lombok:1.18.22")
  annotationProcessor("org.projectlombok:lombok:1.18.20")

  implementation("org.postgresql:postgresql:42.3.0")

  developmentOnly("org.springframework.boot:spring-boot-devtools:2.5.5")

  testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.5")

  implementation(platform("org.testcontainers:testcontainers-bom:1.16.1")) //import bill of materials (BOM)
  testImplementation("org.testcontainers:junit-jupiter:1.16.1")
  testImplementation("org.testcontainers:postgresql:1.16.1")
}


tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}
