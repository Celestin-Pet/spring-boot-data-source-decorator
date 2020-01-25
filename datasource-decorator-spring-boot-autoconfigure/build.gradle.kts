dependencies {
    compileOnly("org.springframework.boot:spring-boot:${project.extra["springBootVersion"]}")
    compileOnly("org.springframework.boot:spring-boot-autoconfigure:${project.extra["springBootVersion"]}")
    compileOnly("org.springframework.boot:spring-boot-starter-jdbc:${project.extra["springBootVersion"]}")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:${project.extra["springBootVersion"]}")
    annotationProcessor("org.projectlombok:lombok:1.18.8")
    compileOnly("org.projectlombok:lombok:1.18.8")

    compileOnly("org.springframework.boot:spring-boot-actuator:${project.extra["springBootVersion"]}")

    compileOnly("org.apache.commons:commons-dbcp2:2.6.0")
    compileOnly("org.apache.tomcat:tomcat-jdbc:9.0.22")
    compileOnly("com.zaxxer:HikariCP:3.3.1")

    compileOnly("p6spy:p6spy:${project.extra["p6SpyVersion"]}")
    compileOnly("net.ttddyy:datasource-proxy:${project.extra["datasourceProxyVersion"]}")
    compileOnly("com.vladmihalcea.flexy-pool:flexy-pool-core:${project.extra["flexyPoolVersion"]}")
    compileOnly("com.vladmihalcea.flexy-pool:flexy-dbcp2:${project.extra["flexyPoolVersion"]}")
    compileOnly("com.vladmihalcea.flexy-pool:flexy-hikaricp:${project.extra["flexyPoolVersion"]}")
    compileOnly("com.vladmihalcea.flexy-pool:flexy-tomcatcp:${project.extra["flexyPoolVersion"]}")
    compileOnly("com.vladmihalcea.flexy-pool:flexy-micrometer-metrics:${project.extra["flexyPoolVersion"]}")

    compileOnly("org.springframework.boot:spring-boot-starter-actuator:${project.extra["springBootVersion"]}")
    compileOnly("org.springframework.cloud:spring-cloud-sleuth-core:${project.extra["sleuthVersion"]}")

    testImplementation("org.springframework.boot:spring-boot:${project.extra["springBootVersion"]}")
    testImplementation("org.springframework.boot:spring-boot-autoconfigure:${project.extra["springBootVersion"]}")
    testImplementation("org.springframework.boot:spring-boot-starter-jdbc:${project.extra["springBootVersion"]}")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.5.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.5.0")
    testImplementation("com.h2database:h2:1.4.199")
    testImplementation("org.assertj:assertj-core:3.12.2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${project.extra["springBootVersion"]}")

    testImplementation("p6spy:p6spy:${project.extra["p6SpyVersion"]}")
    testImplementation("net.ttddyy:datasource-proxy:${project.extra["datasourceProxyVersion"]}")
    testImplementation("com.vladmihalcea.flexy-pool:flexy-pool-core:${project.extra["flexyPoolVersion"]}")
    testImplementation("com.vladmihalcea.flexy-pool:flexy-dbcp2:${project.extra["flexyPoolVersion"]}")
    testImplementation("com.vladmihalcea.flexy-pool:flexy-hikaricp:${project.extra["flexyPoolVersion"]}")
    testImplementation("com.vladmihalcea.flexy-pool:flexy-tomcatcp:${project.extra["flexyPoolVersion"]}")
    testImplementation("com.vladmihalcea.flexy-pool:flexy-micrometer-metrics:${project.extra["flexyPoolVersion"]}")

    testImplementation("org.springframework.cloud:spring-cloud-sleuth-core:${project.extra["sleuthVersion"]}")

    testImplementation("commons-dbcp:commons-dbcp:1.4")
    testImplementation("org.apache.commons:commons-dbcp2:2.6.0")
    testImplementation("org.apache.tomcat:tomcat-jdbc:9.0.22")
    testImplementation("com.zaxxer:HikariCP:3.3.1")

    testAnnotationProcessor("org.projectlombok:lombok:1.18.8")
    testCompileOnly("org.projectlombok:lombok:1.18.8")
}

tasks {
    compileJava {
        dependsOn(processResources)
    }

    withType<JavaCompile> {
        options.compilerArgs.add("-Xlint:unchecked")
        options.compilerArgs.add("-Xlint:deprecation")
    }

    test {
        useJUnitPlatform()
    }
}