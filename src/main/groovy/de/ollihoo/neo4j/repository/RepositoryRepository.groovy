package de.ollihoo.neo4j.repository

import de.ollihoo.neo4j.domain.Repository
import org.springframework.data.neo4j.repository.GraphRepository

interface RepositoryRepository extends GraphRepository<Repository> {
    Repository findByName(String name)
}
