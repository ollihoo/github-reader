package de.ollihoo.service

import de.ollihoo.domain.Employee
import de.ollihoo.domain.Language
import de.ollihoo.domain.Organization
import de.ollihoo.domain.Repository
import de.ollihoo.graphrepository.EmployeeRepository
import de.ollihoo.graphrepository.LanguageRepository
import de.ollihoo.graphrepository.OrganizationRepository
import de.ollihoo.graphrepository.RepositoryRepository
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
