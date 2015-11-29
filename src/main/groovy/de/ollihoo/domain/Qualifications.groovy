package de.ollihoo.domain

import org.springframework.data.neo4j.annotation.QueryResult

@QueryResult
class Qualifications {
    String employee
    String language
    Integer projectAmount
}
