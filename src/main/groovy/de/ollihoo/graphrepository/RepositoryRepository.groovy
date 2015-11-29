package de.ollihoo.graphrepository

import de.ollihoo.domain.Repository
import org.springframework.data.neo4j.repository.GraphRepository

interface RepositoryRepository extends GraphRepository<Repository> {
    Repository findByName(String name)
}
