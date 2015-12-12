package de.ollihoo.orientdb.repository;

import de.ollihoo.orientdb.orientdomain.Organization;
import org.springframework.data.gremlin.repository.GremlinRepository;

public interface OrganizationRepository extends GremlinRepository<Organization> {
    Organization findByName(String name);
}
