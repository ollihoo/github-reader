package de.ollihoo.neo4j;

import de.ollihoo.neo4jdomain.Organization;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface OrganizationRepository extends GraphRepository<Organization> {
    Organization findByName(String name);
}
