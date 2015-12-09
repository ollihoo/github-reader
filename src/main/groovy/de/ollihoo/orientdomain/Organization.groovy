package de.ollihoo.orientdomain

import com.tinkerpop.blueprints.Direction
import org.springframework.data.gremlin.annotation.Id
import org.springframework.data.gremlin.annotation.Link
import org.springframework.data.gremlin.annotation.Property
import org.springframework.data.gremlin.annotation.Vertex

@Vertex
class Organization implements de.ollihoo.domain.Organization {
    @Id Long id
    @Property("name") String name

    @Link(name = "WORKS_FOR", direction = Direction.IN)
    Set<Employee> employees

    void setEmployed(de.ollihoo.domain.Employee employee) {
        if (employees == null) {
            employees = new HashSet<>()
        }
        employees.add(employee)
    }

}
