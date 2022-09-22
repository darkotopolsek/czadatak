package com.omicron.ctest;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository repo;

    @RequestMapping("/")
    public String viewHomePage(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);

        return "index";
    }

    @RequestMapping("/new")
    public String showNewUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);

        return "new_user";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveProduct(@ModelAttribute("user") User user, @RequestParam("image") MultipartFile multipartFile,
            @Valid User validate, BindingResult bindingResult) throws IOException {
        long id = user.getId();
        if (bindingResult.hasErrors()) {
            return "edit_user";
        }

        //System.out.println("ID:" + id);
        User savedUser = null;
        String fileName = null;

        if (!multipartFile.isEmpty()) {

            fileName = multipartFile.getOriginalFilename();
            
            user.setPhoto(fileName);
            savedUser = repo.save(user);
            String uploadDir = "user-photos\\" + savedUser.getId();
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);

        } else {
            if (id != 0) {
                fileName = service.get(id).getPhotosImagePath();
                System.out.println("in controller: " + service.get(id).getPhotosImagePath());
            }
            else {
                fileName = multipartFile.getOriginalFilename();
            }
            //System.out.println("In Controller:" + fileName);
            user.setPhoto(fileName);
            savedUser = repo.save(user);
        }

        return "redirect:/";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditUserPage(@PathVariable(name = "id") int id, MultipartFile multipartFile) throws IOException {
        ModelAndView mav = new ModelAndView("edit_user");

        User user = service.get(id);

        user.setPhoto(user.getPhoto());
        //user.setPhoto(user.getPhotosImagePath());
        //System.out.println(user.getPhotosImagePath());

        mav.addObject("user", user);

        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") int id) throws IOException {
        service.delete(id);
        return "redirect:/";
    }
}
