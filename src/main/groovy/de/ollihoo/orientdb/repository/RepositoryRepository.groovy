package de.ollihoo.orientdb.repository

import de.ollihoo.orientdb.orientdomain.Repository
import org.springframework.data.gremlin.repository.GremlinRepository

interface RepositoryRepository extends GremlinRepository<Repository> {
    Repository findByName(String name)
}
