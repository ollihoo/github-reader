package de.ollihoo.service

import de.ollihoo.domain.Organization
import spock.lang.Specification

class GithubReaderServiceSpec extends Specification {

    private GithubReaderService githubReaderService

    def setup() {
        githubReaderService = new GithubReaderService()
        githubReaderService.organizationService = Mock(OrganizationService)
        githubReaderService.employeeService = Mock(EmployeeService)
        githubReaderService.jsonRequestHelper = Mock(JsonRequestHelper)
    }

    def "When organization is null, null is returned" () {
        when:
        def organization = githubReaderService.loadOrganization(null)

        then:
        organization == null
    }

    def "When organization is set, search entry in database" () {
        when:
        githubReaderService.loadOrganization("anyOrg")

        then:
        1 * githubReaderService.organizationService.getOrCreateOrganization("anyOrg")
    }

    def "When organization is set, a request is done against github" () {
        given:
        githubReaderService.organizationService.getOrCreateOrganization(_) >> new Organization()

        when:
        githubReaderService.loadOrganization("anyOrg")

        then:
        1 * githubReaderService.jsonRequestHelper.getAndParseJson("https://api.github.com/orgs/anyOrg/members")
    }

    def "When organization is read from github, every employee is added" () {
        given:
        def employees = [ [name: "Tony Mueller", login: "tmueller", url: "http://api.github.com/"] ]
        githubReaderService.organizationService.getOrCreateOrganization(_) >> new Organization()
        githubReaderService.jsonRequestHelper.getAndParseJson("https://api.github.com/orgs/anyOrg/members") >> employees

        when:
        githubReaderService.loadOrganization("anyOrg")

        then:
        1 * githubReaderService.jsonRequestHelper.getAndParseJson("http://api.github.com/") >> employees[0]
        1 * githubReaderService.employeeService.getOrCreateEmployee("tmueller", "Tony Mueller")
    }

}
