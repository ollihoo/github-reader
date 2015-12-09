package de.ollihoo.neo4jdomain

import org.neo4j.ogm.annotation.GraphId
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Property

@NodeEntity
class Language implements de.ollihoo.domain.Language {
    @GraphId Long id

    @Property String name


}
