package vn.hoidanit.laptopshop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.hoidanit.laptopshop.domain.User;
import vn.hoidanit.laptopshop.repository.UserRepository;
import vn.hoidanit.laptopshop.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String getHomePage(Model model) {
        String test = this.userService.handleHello();
        List<User> users = this.userService.getAllUserByEmail("thuc@gmail.com");
        System.out.println(users);
        model.addAttribute("eric", test);
        return "hello";
    }

    @RequestMapping("/admin/user")
    public String getUserPage(Model model) {
        List<User> users = this.userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/user/users";
    }

    @RequestMapping("/admin/user/create") // GET
    public String getCreateUserPage(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @RequestMapping(value = "/admin/user/create", method = RequestMethod.POST)
    public String createUser(Model model, @ModelAttribute("newUser") User user) {
        System.out.println(user);
        this.userService.handleSaveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetailPage(Model model, @PathVariable Long id) {

        model.addAttribute("user", this.userService.getUserById(id));
        return "admin/user/show";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdateUserPage(Model model, @PathVariable Long id) {
        model.addAttribute("user", this.userService.getUserById(id));
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(Model model, @ModelAttribute("user") User user) {
        User updatedUser = this.userService.getUserById(user.getId());
        if (updatedUser != null) {
            updatedUser.setAddress(user.getAddress());
            updatedUser.setPhone(user.getPhone());
            updatedUser.setFullName(user.getFullName());

            this.userService.handleSaveUser(updatedUser);

        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getMethodName(Model model, @PathVariable long id) {
        model.addAttribute("id", id);

        User newUser = new User();
        newUser.setId(id);
        model.addAttribute("newUser", newUser);

        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUSer(Model model, @ModelAttribute("newUser") User user) {

        this.userService.deleteUserById(user.getId());

        return "redirect:/admin/user";
    }

}