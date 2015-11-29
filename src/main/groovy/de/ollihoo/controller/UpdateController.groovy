package de.ollihoo.controller

import de.ollihoo.domain.Employee
import de.ollihoo.service.GithubReaderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class UpdateController {

    @Autowired
    GithubReaderService githubReaderService

    @RequestMapping(value = "/internal/getdata", method = RequestMethod.GET)
    String index(Model model) {
        List<Employee> employees = githubReaderService.publicEmployees
        model.addAttribute("employees", employees)
        "getdata"
    }

}
