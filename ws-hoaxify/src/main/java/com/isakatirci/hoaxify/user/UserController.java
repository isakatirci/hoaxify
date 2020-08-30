package com.isakatirci.hoaxify.user;

import com.isakatirci.hoaxify.error.ApiError;
import com.isakatirci.hoaxify.shared.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/create-user")
    public GenericResponse createUser(@Valid @RequestBody UserRequestModel userRequest) {
        User user = new User();
        user.setPassword(userRequest.getPassword());
        user.setDisplayName(userRequest.getDisplayName());
        user.setUserName(userRequest.getUserName());
        userService.save(user);
        return new GenericResponse("success");
    }

    private ResponseEntity<?> createUserV1(@Valid @RequestBody UserRequestModel userRequest) {
/*        log.info(userRequest.toString());
        ApiError apiError = new ApiError(400, "Validation error", "/api/v1/users/create-user");
        Map<String, String> validationErros = new HashMap<>();
        String userName = userRequest.getUserName();
        String displayName = userRequest.getDisplayName();
        if (userName == null || userName.isBlank()) {
            validationErros.put("userName", "UserName is not available");
        }
        if (displayName == null || displayName.isBlank()) {
            validationErros.put("displayName", "DisplayName is not available");
        }
        if (validationErros.size() > 0) {
            apiError.setValidationErrors(validationErros);
            //return  ResponseEntity.badRequest().build();
            //return  ResponseEntity.badRequest().body(apiError);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
        }*/
        User user = new User();
        user.setPassword(userRequest.getPassword());
        user.setDisplayName(userRequest.getDisplayName());
        user.setUserName(userRequest.getUserName());
        userService.save(user);

        return ResponseEntity.ok(new GenericResponse("success"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError(400, "Validation error", "/api/v1/users/create-user");
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError :
                exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());

        }
        apiError.setValidationErrors(validationErrors);
        return  apiError;
    }

}
