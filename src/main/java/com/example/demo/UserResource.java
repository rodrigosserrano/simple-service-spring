package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserResource {
    private final UserRepository repository;

    public UserResource(UserRepository repository){
        this.repository = repository;
    }

    @GetMapping("/all")
    public List<User> users() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public User users(@PathVariable int id) throws Exception {
        User user;
        user = repository.findById(id)
                .orElseThrow(() -> new Exception());

        return user;
    }

    @PostMapping("save")
    public User save(@RequestBody User user) {
        User createdUser = repository.save(user);
        return createdUser;
    }


    @PatchMapping("/update")
    public User update(@RequestBody User updatedUser) throws Exception {
        User returnUser = repository.findById(updatedUser.getId())
                .map(user -> {
                    user.setName(updatedUser.getEmail());
                    user.setEmail(updatedUser.getEmail());
                    return repository.save(user);
                }).orElseThrow(() -> new Exception());

        return returnUser;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id) {
      repository.deleteById(id);
      return "User id "+id+" deleted.";
    }
}
