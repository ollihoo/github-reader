package de.ollihoo.service

import de.ollihoo.neo4jdomain.Language
import de.ollihoo.neo4j.LanguageRepository
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
