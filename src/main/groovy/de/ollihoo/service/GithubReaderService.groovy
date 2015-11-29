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
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class GithubReaderService {
    private static final Logger LOG = LoggerFactory.getLogger(GithubReaderService)

    @Autowired
    EmployeeRepository employeeRepository
    @Autowired
    RepositoryRepository repositoryRepository
    @Autowired
    LanguageRepository languageRepository

    List<Employee> getCodecentricEmployees() {
//        URL membersEndpoint = new URL(getMembersEndpoint())
        def membersFile = this.getClass().getResource("/members.json")
        def members = new JsonSlurper().parse(membersFile)
        getEmployees(members)
    }

    private List<Employee> getEmployees(members) {
        members.findAll { member ->
            employeeRepository.findByLogin(member.login) == null
        }.collect { member ->
            String result = doRequestOn(new URI(member.url))
            if (result) {
                LOG.info(result)
            } else {
                LOG.warn("no result on ${member.url}")
            }
        }
        []
    }

    private String doRequestOn(URI uri) {
        RestTemplate restTemplate = new RestTemplate()
        HttpHeaders headers = new HttpHeaders()
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON))
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers)
        try {
            ResponseEntity response = restTemplate.exchange(uri, HttpMethod.GET, entity, String)
            LOG.info("ANSWER: ${}")
            return response.getBody()
        } catch (HttpClientErrorException e) {
            LOG.warn("No data available: ${e.message}")
        }
        null
    }


    private List<Employee> getEmployeesOrig(members) {
        members.findAll { member ->
            employeeRepository.findByLogin(member.login) == null
        }.collect { member ->
            LOG.info("Parsing member ${member.login}")
            try {

                RestTemplate restTemplate = new RestTemplate()
                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.add("Authentication", "ollihoo:reiser2012")
                HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
                def uri = new URI(member.url)
                String answer = restTemplate.exchange(uri, HttpMethod.GET, entity, String)

                def user = new JsonSlurper().parseText(answer)
//                URL userUrl = new URL(member.url)
//                def user = new JsonSlurper().parse(userUrl)
                String name = user.name ?: user.login
                def repositories = getUsersRepositories(user)
                Employee employee = employeeRepository.findByName(name) ?: new Employee(name: name)
                repositories.each { repository ->
                    employee.setWorksOn(repository)
                    repository.languages.each { language ->
                        employee.usesLanguage(language)
                    }
                }
                employeeRepository.save(employee, 1)
                employee
            } catch (all) {
                LOG.warn("Interruption of update process; reason: " + all.message)
                null
            }
        }.findAll { employee -> employee != null }
    }

    private List<Repository> getUsersRepositories(user) {
        URL reposUrl = new URL(getRepositoryEndpoint(user))
        def repos = new JsonSlurper().parse(reposUrl)
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


    private List<Language> getLanguages(repo) {
        URL languagesUrl = new URL(getLanguagesEndpoint(repo))
        def languages = new JsonSlurper().parse(languagesUrl)
        languages.keySet().collect { languageName ->
            languageRepository.findByName(languageName) ?:
                    languageRepository.save(new Language(name: languageName), 0)
        }
    }

    private getRepositoryEndpoint(githubUser) {
        githubUser."repos_url"
    }

    private getLanguagesEndpoint(githubRepository) {
        githubRepository."languages_url"
    }


}
