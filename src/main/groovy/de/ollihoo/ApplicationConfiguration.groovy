package de.ollihoo

import org.neo4j.ogm.session.Session
import org.neo4j.ogm.session.SessionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.data.neo4j.config.Neo4jConfiguration
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.data.neo4j.server.Neo4jServer
import org.springframework.data.neo4j.server.RemoteServer
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableNeo4jRepositories(basePackages = "de.ollihoo.graphrepository")
@EnableTransactionManagement
@ConfigurationProperties
class ApplicationConfiguration extends Neo4jConfiguration {

    @Value("neo4jServerUrl")
    String neo4jServerUrl

    @Value("neo4jUser")
    String neo4jUser

    @Value("neo4jPassword")
    String neo4jPassword

    @Override @Bean
    Neo4jServer neo4jServer() {
        new RemoteServer(neo4jServerUrl, neo4jUser, neo4jPassword)
    }

    @Override @Bean
    SessionFactory getSessionFactory() {
        new SessionFactory("de.ollihoo.domain")
    }

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session getSession() throws Exception {
        return super.getSession();
    }
}
