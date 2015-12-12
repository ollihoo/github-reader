package de.ollihoo.controller

import de.ollihoo.neo4j.repository.EmployeeRepository
import de.ollihoo.neo4j.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class EmployeeController {

    @Autowired
    EmployeeService employeeService

    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    def index(Model model) {
        model.addAttribute("qualifications", employeeService.getQualification())
        "employee"
    }
}
