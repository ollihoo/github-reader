package de.ollihoo.service

import de.ollihoo.graphrepository.EmployeeRepository
import spock.lang.Specification

class GithubReaderServiceSpec extends Specification {

    private GithubReaderService githubReaderService

    def setup() {
        githubReaderService = new GithubReaderService()
        githubReaderService.employeeRepository = Mock(EmployeeRepository)
    }

    def "When no list is set, an empty list is returned" () {
        when:
        def members = githubReaderService.loadMembers()

        then:
        members == []
    }

    def "When list is set, but empty, an empty list is returned" () {
        given:
        githubReaderService.membersResource = this.getClass().classLoader.getResource("noMembers.json")

        when:
        def members = githubReaderService.loadMembers()

        then:
        members == []
    }

    def "When list is set, it checks all given members against database" () {
        given:
        githubReaderService.membersResource = this.getClass().classLoader.getResource("members.json")

        when:
        def members = githubReaderService.loadMembers()

        then:
        1 * githubReaderService.employeeRepository.findByLogin("abc") >> []
        1 * githubReaderService.employeeRepository.findByLogin("def") >> []
    }


}
