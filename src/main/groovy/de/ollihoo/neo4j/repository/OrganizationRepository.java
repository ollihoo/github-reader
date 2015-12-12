package de.ollihoo.neo4j.repository;

import de.ollihoo.neo4j.domain.Organization;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface OrganizationRepository extends GraphRepository<Organization> {
    Organization findByName(String name);
}
