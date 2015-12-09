package de.ollihoo.neo4j;

import de.ollihoo.neo4jdomain.Employee;
import de.ollihoo.neo4jdomain.Qualifications;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface EmployeeRepository extends GraphRepository<Employee> {
    static final String EMPLOYEE_PROJECTS_REQUEST =
    "match (e:Employee)-[:USES]->(l:Language)<-[:IMPLEMENTED_IN]-(r:Repository)<-[:WORKS_ON]-(e) return e.name AS employee, l.name as language, count(r) as projectAmount order by employee";

    Employee findByName(String name);

    Employee findByLogin(String login);

    @Query(EMPLOYEE_PROJECTS_REQUEST)
    Iterable<Qualifications> getQualification();

}
