package de.ollihoo.orientdb

import de.ollihoo.orientdomain.Repository
import org.springframework.data.gremlin.repository.GremlinRepository

interface RepositoryRepository extends GremlinRepository<Repository> {
    Repository findByName(String name)
}
