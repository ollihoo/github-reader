package de.ollihoo.orientdb;

import de.ollihoo.orientdomain.Organization;
import org.springframework.data.gremlin.repository.GremlinRepository;

public interface OrganizationRepository extends GremlinRepository<Organization> {
    Organization findByName(String name);
}
