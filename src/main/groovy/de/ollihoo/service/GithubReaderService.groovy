package de.ollihoo.service

import de.ollihoo.domain.Employee
import de.ollihoo.domain.Language
import de.ollihoo.domain.Organization
import de.ollihoo.domain.Repository
import de.ollihoo.graphrepository.EmployeeRepository
import de.ollihoo.graphrepository.LanguageRepository
import de.ollihoo.graphrepository.OrganizationRepository
import de.ollihoo.graphrepository.RepositoryRepository
import groovy.json.JsonSlurper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class GithubReaderService {
    private static final Logger LOG = LoggerFactory.getLogger(GithubReaderService)

    @Autowired
    OrganizationRepository organizationRepository
    @Autowired
    EmployeeRepository employeeRepository
    @Autowired
    RepositoryRepository repositoryRepository
    @Autowired
    LanguageRepository languageRepository
    @Autowired
    JsonRequestHelper jsonRequestHelper

    List<Employee> loadMembers(String organizationName) {
        String membersResource = "https://api.github.com/orgs/${organizationName}/members"
        LOG.info("Loading members from ${membersResource}...")
        def members = jsonRequestHelper.getAndParseJson(membersResource)
        try {
            def organization = organizationRepository.findByName(organizationName) ?:
                    new Organization(name: organizationName)
            organizationRepository.save(organization, 0)
            List<Employee> employees = loadUnknownEmployees(members)
            employees.each { employee ->
                organization.setEmployed(employee)
            }
            organizationRepository.save(organization, 1)
            employees
        } catch (all) {
            LOG.warn("Could not parse all data: ${all.message}")
            return []
        }
    }

    private List<Employee> loadUnknownEmployees(members) {
        members.collect { member ->
            LOG.info("Start parsing data for member ${member.login}")
            parseEmployee(member)
        }
    }

    private Employee parseEmployee(githubMemberJson) {
        def user = jsonRequestHelper.getAndParseJson(githubMemberJson.url)
        String name = user.name ?: user.login
        def repositories = getUsersRepositories(user)
        Employee employee = employeeRepository.findByName(name) ?: new Employee(name: name, login: user.login)
        repositories.each { repository ->
            employee.setWorksOn(repository)
            repository.languages.each { language ->
                employee.usesLanguage(language)
            }
        }
        employeeRepository.save(employee, 1)
        employee
    }

    private List<Repository> getUsersRepositories(githubUserJson) {
        def repos = jsonRequestHelper.getAndParseJson(githubUserJson."repos_url")
        def repositories = repos.collect { repo ->
            def languages = getLanguages(repo)

            def repository = repositoryRepository.findByName(repo.name) ?:
                    new Repository(name: repo.name)
            languages.each { language ->
                repository.setImplementedIn(language)
            }
            repositoryRepository.save(repository, 1)
            repository
        }
        repositories
    }

    private List<Language> getLanguages(githubRepositoryJson) {
        def languages = jsonRequestHelper.getAndParseJson(githubRepositoryJson."languages_url")
        languages.keySet().collect { languageName ->
            languageRepository.findByName(languageName) ?:
                    languageRepository.save(new Language(name: languageName), 0)
        }
    }

}
