package de.ollihoo.service

import de.ollihoo.neo4j.domain.Language
import de.ollihoo.neo4j.domain.Repository
import de.ollihoo.neo4j.repository.RepositoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RepositoryService {

    @Autowired
    RepositoryRepository repositoryRepository

    Repository getOrInsertRepository(repo) {
        def repository = repositoryRepository.findByName(repo.name) ?:
                new Repository(name: repo.name)
        repositoryRepository.save(repository, 0)
    }

    Repository addLanguagesToRepository(List<Language> languages, Repository repository) {
        languages.each { language ->
            repository.setImplementedIn(language)
        }
        updateRepositoryRelations(repository)
    }

    private Repository updateRepositoryRelations(Repository repository) {
        repositoryRepository.save(repository, 1)
    }

}
