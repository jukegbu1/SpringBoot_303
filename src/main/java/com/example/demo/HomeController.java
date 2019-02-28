package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    //don't have to create respository inside methods all the time
    @Autowired
    CourseRepository courseRepository;

    @RequestMapping("/")
    public String listCourses(Model model) {
        model.addAttribute("courses", courseRepository.findAll());
        return "list";
    }

    //Get user data
    @GetMapping("/add")
    public String courseForm(Model model) {
        model.addAttribute("course", new Course());
        return "courseform";
    }

    //Process user information after they enter
    @PostMapping("/process")
    public String processForm(@Valid Course course,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "courseform";
        }
        //Afterwards goes back to list page
        courseRepository.save(course);
        return "redirect:/";

    }
        @RequestMapping("/detail/{id}")
        public String showCourse(@PathVariable("id") long id, Model model) {
        //get id inside of course, since unqiue ID
            model.addAttribute("course", courseRepository.findById(id).get());
            return "show";
        }

        //just returns course form so you can return values in daatbase
        @RequestMapping("/update/{id}")
        public String updateCourse(@PathVariable("id") long id, Model model){
            model.addAttribute("course", courseRepository.findById(id).get());
            return "courseform";
        }

        @RequestMapping("/delete/{id}")
        public String delCourse(@PathVariable("id") long id){
            courseRepository.deleteById(id);
            return "redirect:/";

        }

    }



