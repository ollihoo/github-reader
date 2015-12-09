package de.ollihoo.service

import de.ollihoo.neo4jdomain.Employee
import de.ollihoo.neo4jdomain.Repository
import de.ollihoo.neo4j.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository

    Employee getOrCreateEmployee(String login, String userName) {
        String name = userName ?: login
        Employee employee = employeeRepository.findByName(name) ?:
                new Employee(name: name, login: login)
        employeeRepository.save(employee, 0)
    }

    Employee setRepositoriesAndLanguagesForEmployee(List<Repository> repositories, Employee employee) {
        repositories.each { repository ->
            setRepositoryAndLanguagesForEmployee(repository, employee)
        }
        employee
    }


    Employee setRepositoryAndLanguagesForEmployee(Repository repository, Employee employee) {
        employee.setWorksOn(repository)
        repository.languages.each { language ->
            employee.usesLanguage(language)
        }
        employeeRepository.save(employee, 1)
    }

}
