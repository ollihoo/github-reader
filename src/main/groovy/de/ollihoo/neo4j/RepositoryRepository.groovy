package de.ollihoo.neo4j

import de.ollihoo.neo4jdomain.Repository
import org.springframework.data.neo4j.repository.GraphRepository

interface RepositoryRepository extends GraphRepository<Repository> {
    Repository findByName(String name)
}
