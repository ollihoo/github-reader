package de.ollihoo.orientdb.orientdomain

import com.tinkerpop.blueprints.Direction
import org.springframework.data.gremlin.annotation.Id
import org.springframework.data.gremlin.annotation.Link
import org.springframework.data.gremlin.annotation.Property
import org.springframework.data.gremlin.annotation.Vertex

@Vertex
class Employee implements de.ollihoo.domain.Employee {

    @Id private Long id
    @Property("name") String name
    @Property("login") String login

    @Link(name="WORKS_ON", direction=Direction.OUT)
    Set<Repository> repositories

    @Link(name="USES", direction=Direction.OUT)
    Set<Language> languages

    void setWorksOn(de.ollihoo.domain.Repository repository) {
        if (repositories == null) {
            repositories = new HashSet<>()
        }
        repositories.add(repository)
    }

    void usesLanguage(de.ollihoo.domain.Language language) {
        if (languages == null) {
            languages = new HashSet<>()
        }
        languages.add(language)
    }
}
