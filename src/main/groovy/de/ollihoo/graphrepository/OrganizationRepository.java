package de.ollihoo.graphrepository;

import de.ollihoo.domain.Organization;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface OrganizationRepository extends GraphRepository<Organization> {
    Organization findByName(String name);
}
