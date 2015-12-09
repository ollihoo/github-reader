package de.ollihoo.orientdomain

import org.springframework.data.gremlin.annotation.Id
import org.springframework.data.gremlin.annotation.Property
import org.springframework.data.gremlin.annotation.Vertex

@Vertex
class Language implements de.ollihoo.domain.Language {
    @Id Long id

    @Property("name") String name


}
