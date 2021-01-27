package com.geekbrains.geekspring.controllers;

import com.geekbrains.geekspring.entities.SystemUser;
import com.geekbrains.geekspring.services.RoleService;
import com.geekbrains.geekspring.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showUsersPage(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping(value = "/update/{id}")
    public String showEditUserPage(@PathVariable(name = "id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        model.addAttribute("roles", roleService.findAll());
        return "user_edit";
    }

    @PostMapping(value = "/update")
    public String editUser(@ModelAttribute @Valid SystemUser systemUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", systemUser);
            model.addAttribute("roles", roleService.findAll());
            return "user_edit";
        }
        userService.save(systemUser);
        return "redirect:/admin/users";
    }

    @GetMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
