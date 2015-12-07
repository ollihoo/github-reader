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
class LanguageService {

    @Autowired
    LanguageRepository languageRepository

    Language getOrInsertLanguage(String languageName) {
        languageRepository.findByName(languageName) ?:
                languageRepository.save(new Language(name: languageName), 0)
    }

}
