package de.ollihoo.neo4j.domain

import org.neo4j.ogm.annotation.GraphId
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Property
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class Repository implements de.ollihoo.domain.Repository {
    @GraphId private Long id
    @Property String name

    @Relationship(type = "IMPLEMENTED_IN", direction = Relationship.OUTGOING)
    Set<Language> languages

    void setImplementedIn(de.ollihoo.domain.Language language) {
        if (languages == null) {
            languages = new HashSet<>()
        }
        languages.add(language)
    }



}
