plugins {
  id("org.springframework.boot") version "3.0.3"
  id("io.spring.dependency-management") version "1.1.0"
  java
}



group = "se.accelerateit"
version = "0.0.1-SNAPSHOT"

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
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
  implementation("org.springdoc:springdoc-openapi-ui:1.6.14")

  // TODO: when we have Spring Security running, see https://springdoc.org/#spring-security-support
  //implementation("org.springdoc:springdoc-openapi-security:1.6.13")

  implementation("org.springframework.boot:spring-boot-starter-hateoas")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-freemarker")
  implementation("org.flywaydb:flyway-core")
  implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.1")
  implementation("org.springframework.boot:spring-boot-starter-mail")

  implementation("com.google.cloud:spring-cloud-gcp-starter-sql-postgresql:4.1.1")

  compileOnly("org.projectlombok:lombok")
  annotationProcessor("org.projectlombok:lombok")

  implementation("org.postgresql:postgresql")

  developmentOnly("org.springframework.boot:spring-boot-devtools")

  testImplementation("org.springframework.boot:spring-boot-starter-test")

  implementation(platform("org.testcontainers:testcontainers-bom:1.17.6")) //import bill of materials (BOM)
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.testcontainers:postgresql")
}


tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    events("passed", "skipped", "failed")
  }
}
