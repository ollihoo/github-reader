package de.ollihoo.domain

import org.neo4j.ogm.annotation.GraphId
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Property

@NodeEntity
class Language {
    @GraphId Long id

    @Property String name


}
