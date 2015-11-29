package de.ollihoo.domain

import org.neo4j.ogm.annotation.GraphId
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Property
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class Repository {
    @GraphId private Long id
    @Property String name

    @Relationship(type = "IMPLEMENTED_IN", direction = Relationship.OUTGOING)
    Set<Language> languages

    void setImplementedIn(Language language) {
        if (languages == null) {
            languages = new HashSet<>()
        }
        languages.add(language)
    }



}
