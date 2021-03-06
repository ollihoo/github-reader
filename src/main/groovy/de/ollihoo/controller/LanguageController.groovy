package de.ollihoo.controller

import de.ollihoo.neo4j.service.LanguageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@Controller
class LanguageController {

    @Autowired
    LanguageService languageService

    @RequestMapping(value = "/language", method = RequestMethod.GET)
    def index(@RequestParam(name="language", required = false) String language, Model model) {
        if (language) {
            model.addAttribute("qualifications", languageService.getLanguagesOrderedByQualification(language))
            return "language"
        }
        return "languagestart"
    }
}
