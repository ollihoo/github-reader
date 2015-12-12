package de.ollihoo.service

import de.ollihoo.neo4j.domain.Employee
import de.ollihoo.neo4j.domain.Organization
import de.ollihoo.neo4j.repository.OrganizationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository

    Organization getOrCreateOrganization(String organizationName) {
        def organization = organizationRepository.findByName(organizationName) ?:
                new Organization(name: organizationName)
        organizationRepository.save(organization, 0)
    }

    Organization addEmployeesToOrganization(List<Employee> employees, Organization organization) {

        employees.each { employee ->
            organization.setEmployed(employee)
        }
        updateOrganization(organization)
    }

    Organization updateOrganization(Organization organization) {
        organizationRepository.save(organization, 1)
    }

}
