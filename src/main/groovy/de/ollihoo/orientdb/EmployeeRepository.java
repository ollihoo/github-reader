package de.ollihoo.orientdb;

import de.ollihoo.orientdomain.Employee;
import de.ollihoo.orientdomain.Qualifications;
import org.springframework.data.gremlin.repository.GremlinRepository;
import org.springframework.data.neo4j.annotation.Query;

public interface EmployeeRepository extends GremlinRepository<Employee> {
    static final String EMPLOYEE_PROJECTS_REQUEST =
    "match (e:Employee)-[:USES]->(l:Language)<-[:IMPLEMENTED_IN]-(r:Repository)<-[:WORKS_ON]-(e) return e.name AS employee, l.name as language, count(r) as projectAmount order by employee";

    Employee findByName(String name);

    Employee findByLogin(String login);

    @Query(EMPLOYEE_PROJECTS_REQUEST)
    Iterable<Qualifications> getQualification();

}
