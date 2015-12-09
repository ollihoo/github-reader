package de.ollihoo.neo4jdomain

import org.springframework.data.neo4j.annotation.QueryResult

@QueryResult
class Qualifications implements de.ollihoo.domain.Qualifications {
    String employee
    String language
    Integer projectAmount
}
