package com.imt.authentication_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.imt.authentication_service.AuthModel.AuthEntity;
import com.imt.authentication_service.AuthService.AuthenticationService;
import com.imt.authentication_service.AuthService.CrudServices.AddService;
import com.imt.authentication_service.AuthService.CrudServices.DeleteService;
import com.imt.authentication_service.AuthService.CrudServices.GetService;
import com.imt.authentication_service.AuthService.CrudServices.UpdateService;
import com.imt.authentication_service.Dto.AuthEntityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-auth")
public class AuthController {
    @Autowired
    private  AuthenticationService authenticationService;
    @Autowired
    private AddService addService;
    @Autowired
    private GetService getService;
    @Autowired
    private UpdateService updateService;
    @Autowired
    private DeleteService deleteService;

    // l'authentification
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam (value = "username", required = true) String username,
                                        @RequestParam(value="password", required = true) String password) {
        String token = authenticationService.authenticate(username, password);
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    // validation du token
    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        String username = authenticationService.validateToken(token);
        if (username != null) {
            return ResponseEntity.ok(username);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired or invalid");
        }
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public  void registerPlayerCredentials(@RequestBody AuthEntityDto authEntityDto)  {

        //TODO : Verifier si le Dto n'est pas null ou invalid
        AuthEntity player = authEntityDto.toAuthEntity();
        this.addService.execute(player);
    }

    @GetMapping(value = "/get-user", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.OK)
    public AuthEntityDto getPlayerCredentials(@RequestParam(value = "username") String username){

        AuthEntity foundPlayer = this.getService.byUserName(username);

        //TODO : checker si le user trouvé n'est pas null
        return foundPlayer.toAuthEntityDto();

        //TODO : ajouter un requestParam token afin de pouvoir récupérer un user par son token


    }

    @PatchMapping(value = "/update",consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updatePlayer(@RequestBody AuthEntityDto playerDto){
        //TODO: checker userDto n'est pas null
        AuthEntity updatedPlayer = playerDto.toAuthEntity();
        this.updateService.execute(updatedPlayer);
    }


    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/delete")
    public void deletePlayer(@RequestParam(value= "username", required = true) String username) {
        //TODO: verifier si l'objet n'est pas null
        this.deleteService.execute(username);
    }
}


