package de.ollihoo.service

import de.ollihoo.domain.Employee
import de.ollihoo.domain.Language
import de.ollihoo.domain.Organization
import de.ollihoo.domain.Repository
import de.ollihoo.graphrepository.EmployeeRepository
import de.ollihoo.graphrepository.LanguageRepository
import de.ollihoo.graphrepository.OrganizationRepository
import de.ollihoo.graphrepository.RepositoryRepository
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
