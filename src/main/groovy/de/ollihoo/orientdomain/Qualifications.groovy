package de.ollihoo.orientdomain

import org.springframework.data.neo4j.annotation.QueryResult

class Qualifications implements de.ollihoo.domain.Qualifications {
    String employee
    String language
    Integer projectAmount
}
