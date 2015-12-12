package de.ollihoo.neo4j.domain

import org.neo4j.ogm.annotation.GraphId
import org.neo4j.ogm.annotation.NodeEntity
import org.neo4j.ogm.annotation.Relationship

@NodeEntity
class Organization implements de.ollihoo.domain.Organization {
    @GraphId Long id
    String name

    @Relationship(type = "WORKS_FOR", direction = Relationship.INCOMING)
    Set<Employee> employees

    void setEmployed(de.ollihoo.domain.Employee employee) {
        if (employees == null) {
            employees = new HashSet<>()
        }
        employees.add(employee)
    }

}
