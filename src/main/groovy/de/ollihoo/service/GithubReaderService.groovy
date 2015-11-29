package de.ollihoo.service

import de.ollihoo.domain.Employee
import de.ollihoo.domain.Language
import de.ollihoo.domain.Repository
import de.ollihoo.graphrepository.EmployeeRepository
import de.ollihoo.graphrepository.LanguageRepository
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

    @Value('${githubReaderService.membersResource}')
    String membersResource

    @Autowired
    EmployeeRepository employeeRepository
    @Autowired
    RepositoryRepository repositoryRepository
    @Autowired
    LanguageRepository languageRepository
    @Autowired
    JsonRequestHelper jsonRequestHelper

    List<Employee> loadMembers() {
        LOG.info("Loading members from ${membersResource}...")
        URL resource
        try {
            resource = new URL(membersResource)
        } catch (MalformedURLException e) {
            LOG.warn("Attention: no member resource set. Check your configuration (githubReaderService.membersResource)")
            return []
        }
        def members = new JsonSlurper().parse(resource)
        try {
            loadUnknownEmployees(members)
        } catch (all) {
            LOG.warn("Could not parse all data: ${all.message}")
        }
    }

    private List<Employee> loadUnknownEmployees(members) {
        members.findAll { member ->
            employeeRepository.findByLogin(member.login) == null
        }.collect { member ->
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
            String repoName = repo.name
            def languages = getLanguages(repo)

            def repository = repositoryRepository.findByName(repo.name) ?:
                    new Repository(name: repoName)
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
