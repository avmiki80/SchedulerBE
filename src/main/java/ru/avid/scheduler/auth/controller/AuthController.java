package ru.avid.scheduler.auth.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.avid.scheduler.auth.entity.Activity;
import ru.avid.scheduler.auth.entity.Role;
import ru.avid.scheduler.auth.entity.User;
import ru.avid.scheduler.auth.exception.RoleNotFoundException;
import ru.avid.scheduler.auth.exception.UserOrEmailExists;
import ru.avid.scheduler.auth.objects.JsonException;
import ru.avid.scheduler.auth.service.UserService;

import javax.validation.Valid;
import java.util.UUID;

import static ru.avid.scheduler.auth.service.UserService.DEFAULT_ROLE;

@RestController
@RequestMapping("/auth")
@Log
public class AuthController {
    private UserService userService;
    private PasswordEncoder pwdEncoder;
    @Autowired
    public AuthController(UserService userService,
                          PasswordEncoder pwdEncoder) {
        this.userService = userService;
        this.pwdEncoder = pwdEncoder;
    }
    @PutMapping("/register")
    public ResponseEntity register(@Valid @RequestBody User user){
        if(this.userService.userExists(user.getUsername(), user.getEmail())){
            throw new UserOrEmailExists("User or email alredy exists");
        }

        Role userRole = this.userService.findByName(DEFAULT_ROLE)
                .orElseThrow(()-> new RoleNotFoundException("Default Role USER not found"));
        user.getRoles().add(userRole);
        user.setPassword(pwdEncoder.encode(user.getPassword()));
        Activity activity = new Activity();
        activity.setUser(user);
        activity.setUuid(UUID.randomUUID().toString());
        userService.register(user, activity);
        return ResponseEntity.ok().build();
    }
    /*

    Метод перехватывает все ошибки в контроллере (неверный логин-пароль и пр.)

    Даже без этого метода все ошибки будут отправляться клиенту, просто здесь это можно кастомизировать, например отправить JSON в нужном формате

    Можно настроить, какие типа ошибок отправлять в явном виде, а какие нет (чтобы не давать лишнюю информацию злоумышленникам)

    */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<JsonException> handleExceptions(Exception ex){
        /*

        DisabledException (наш созданный класс) - не активирован
        UserAlreadyActivatedException - пользователь уже активирован (пытается неск. раз активировать)
        UsernameNotFoundException - username или email не найден в базе

        BadCredentialsException - неверный логин-пароль
        UserOrEmailExistsException - пользователь или email уже существуют
        DataIntegrityViolationException - ошибка уникальности в БД

        Эти типы ошибок можно будет считывать на клиенте и обрабатывать как нужно (например, показать текст ошибки)

    */
        // отправляем название класса ошибки (чтобы правильно обработать ошибку на клиенте)
        return new ResponseEntity(new JsonException(ex.getClass().getSimpleName()), HttpStatus.BAD_REQUEST);
    }

}
