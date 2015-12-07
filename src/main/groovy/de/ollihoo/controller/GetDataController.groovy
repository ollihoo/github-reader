package de.ollihoo.controller

import de.ollihoo.domain.Employee
import de.ollihoo.domain.Organization
import de.ollihoo.service.GithubReaderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@Controller
class GetDataController {

    @Autowired
    GithubReaderService githubReaderService

    @RequestMapping(value = "/internal/getdata", method = RequestMethod.GET)
    String index(@RequestParam(name = "organization", required = false) String organization,
                 Model model) {
        if (organization) {
            model.addAttribute("organization", githubReaderService.loadOrganization(organization))
        }
        "getdata"
    }

}
