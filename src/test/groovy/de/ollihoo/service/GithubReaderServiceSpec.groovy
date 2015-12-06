package de.ollihoo.service

import de.ollihoo.graphrepository.EmployeeRepository
import de.ollihoo.graphrepository.OrganizationRepository
import spock.lang.Specification

class GithubReaderServiceSpec extends Specification {

    private GithubReaderService githubReaderService

    def setup() {
        githubReaderService = new GithubReaderService()
        githubReaderService.organizationRepository = Mock(OrganizationRepository)
        githubReaderService.employeeRepository = Mock(EmployeeRepository)
        githubReaderService.jsonRequestHelper = Mock(JsonRequestHelper)
    }

    def "When organization is null, an empty list is returned" () {
        when:
        def members = githubReaderService.loadMembers(null)

        then:
        members == []
    }

    def "When organizazion is set, a request is done against github" () {
        given:
        githubReaderService.organizationRepository.findByName(_) >> []

        when:
        githubReaderService.loadMembers("anyOrg")

        then:
        1 * githubReaderService.jsonRequestHelper.getAndParseJson(
                "https://api.github.com/orgs/anyOrg/members")
    }


}
