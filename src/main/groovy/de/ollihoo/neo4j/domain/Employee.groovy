package de.ollihoo.neo4j.domain

import org.neo4j.ogm.annotation.GraphId
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Property
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class Employee implements de.ollihoo.domain.Employee {

    @GraphId private Long id
    @Property String name
    @Property String login

    @Relationship(type="WORKS_ON", direction=Relationship.OUTGOING)
    Set<Repository> repositories

    @Relationship(type="USES", direction=Relationship.OUTGOING)
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
