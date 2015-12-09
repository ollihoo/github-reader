package de.ollihoo.service

import de.ollihoo.neo4jdomain.Employee
import de.ollihoo.neo4jdomain.Language
import de.ollihoo.neo4jdomain.Organization
import de.ollihoo.neo4jdomain.Repository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GithubReaderService {
    private static final Logger LOG = LoggerFactory.getLogger(GithubReaderService)

    @Autowired
    JsonRequestHelper jsonRequestHelper
    @Autowired
    OrganizationService organizationService
    @Autowired
    LanguageService languageService
    @Autowired
    RepositoryService repositoryService
    @Autowired
    EmployeeService employeeService

    Organization loadOrganization(String organizationName) {
        String membersResource = "https://api.github.com/orgs/${organizationName}/members"
        LOG.info("Loading members from ${membersResource}...")

        try {
            def members = jsonRequestHelper.getAndParseJson(membersResource)
            Organization organization = organizationService.getOrCreateOrganization(organizationName)
            getEmployees(organization, members)
            organization
        } catch (JsonHelperException e) {
            LOG.warn("Could not parse data: ${e.message} - ${e.request}")
            return null
        }
    }

    private List<Employee> getEmployees(Organization organization, membersJson) {
        membersJson.collect { memberJson ->
            LOG.info("Start parsing data for memberJson ${memberJson.login}")
            def employee = null
            try {
                def user = jsonRequestHelper.getAndParseJson(memberJson.url)
                employee = employeeService.getOrCreateEmployee((String) user.login, user.name)
                employee = employeeService.setRepositoriesAndLanguagesForEmployee(
                        getUsersRepositories(user), employee)
            } catch (JsonHelperException e) {
                LOG.warn("getEmployees - could not parse data: ${e.message} - ${e.request}")
            } finally {
                organization.setEmployed(employee)
                organizationService.updateOrganization(organization)
            }
            employee
        }
    }

    private List<Repository> getUsersRepositories(githubUserJson) {
        LOG.info("Start parsing repository data for user ${githubUserJson.login}")
        def repos = jsonRequestHelper.getAndParseJson(githubUserJson."repos_url")
        repos.collect { repo ->
            Repository repository = repositoryService.getOrInsertRepository(repo)
            repositoryService.addLanguagesToRepository(getLanguages(repo), repository)
        }
    }

    private List<Language> getLanguages(githubRepositoryJson) {
        LOG.info("Start parsing language data for repository ${githubRepositoryJson.name}")
        def languages = jsonRequestHelper.getAndParseJson(githubRepositoryJson."languages_url")
        languages.keySet().collect { languageName ->
            languageService.getOrInsertLanguage(languageName)
        }
    }

}
