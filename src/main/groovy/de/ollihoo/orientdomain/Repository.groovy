package de.ollihoo.orientdomain

import com.tinkerpop.blueprints.Direction
import org.neo4j.ogm.annotation.Relationship
import org.springframework.data.gremlin.annotation.Id
import org.springframework.data.gremlin.annotation.Link
import org.springframework.data.gremlin.annotation.Property
import org.springframework.data.gremlin.annotation.Vertex

@Vertex
class Repository implements de.ollihoo.domain.Repository {
    @Id private Long id
    @Property("name") String name

    @Link(name="IMPLEMENTED_IN", direction=Direction.OUT)
    Set<Language> languages

    void setImplementedIn(de.ollihoo.domain.Language language) {
        if (languages == null) {
            languages = new HashSet<>()
        }
        languages.add(language)
    }

}
