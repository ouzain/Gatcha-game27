/*
package com.imt.player_service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.imt.player_service.Controller.PlayerController;
import com.imt.player_service.Dto.PlayerDto;
import com.imt.player_service.Model.ApiResponse;
import com.imt.player_service.Model.Player;
import com.imt.player_service.Services.PlayerServices.AddPlayerService;
import com.imt.player_service.Services.PlayerServices.GetPlayerService;
import com.imt.player_service.Services.PlayerServices.UpdatePlayerService;
import com.imt.player_service.Services.PlayerServices.DeletePlayerService;
import com.imt.player_service.OpenFeing.AuthServiceClient;
import com.imt.player_service.OpenFeing.InvokClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AddPlayerService addPlayerService;
    @Mock
    private GetPlayerService getPlayerService;
    @Mock
    private DeletePlayerService deletePlayerService;
    @Mock
    private UpdatePlayerService updatePlayerService;
    @Mock
    private AuthServiceClient authServiceClient;
    @Mock
    private InvokClient invokClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    private PlayerDto playerDto;

    @BeforeEach
    public void setUp() {
        PlayerDto playerDto= PlayerDto.Builder.builder()
                .username("TestUser")
                .password("TestPassword")
                .level(1)
                .experience(50)
                .monsterList(new ArrayList<>())
                .build();
    }

    @Test
    public void testAddPlayer_Success() throws Exception {
        when(authServiceClient.registerPlayerCredentials(any())).thenReturn(ResponseEntity.ok("Success"));
        when(authServiceClient.login(any())).thenReturn(ResponseEntity.ok("{\"data\":\"fake_token\"}"));
        when(addPlayerService.execute(any(Player.class))).thenReturn(null);

        mockMvc.perform(post("/api/player/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Le joueur TestUser a été créé et authentifié avec succès."))
                .andExpect(jsonPath("$.success").value(true));

        verify(addPlayerService, times(1)).execute(any(Player.class));
    }

    @Test
    public void testAddPlayer_Failure_InvalidInput() throws Exception {
        playerDto.setUsername(null);  // Invalid input

        mockMvc.perform(post("/api/player/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Le DTO du player est invalide (username/password manquant)."))
                .andExpect(jsonPath("$.success").value(false));

        verify(addPlayerService, never()).execute(any(Player.class));
    }

    @Test
    public void testGetPlayer_Success() throws Exception {
        Player player = new Player("TestUser", "token", 1, 50, new ArrayList<>());
        when(getPlayerService.byUserName("TestUser")).thenReturn(player);

        mockMvc.perform(get("/api/player/get-user")
                        .param("username", "TestUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Joueur trouvé avec succès"))
                .andExpect(jsonPath("$.success").value(true));

        verify(getPlayerService, times(1)).byUserName("TestUser");
    }

    @Test
    public void testGetPlayer_NotFound() throws Exception {
        when(getPlayerService.byUserName("NonExistentUser")).thenReturn(null);

        mockMvc.perform(get("/api/player/get-user")
                        .param("username", "NonExistentUser"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Aucun joueur trouvé pour le nom d'utilisateur : NonExistentUser"))
                .andExpect(jsonPath("$.success").value(false));

        verify(getPlayerService, times(1)).byUserName("NonExistentUser");
    }

    @Test
    public void testUpdatePlayer_Success() throws Exception {
        Player player = new Player("TestUser", "token", 1, 50, new ArrayList<>());
        when(updatePlayerService.execute(any(Player.class))).thenReturn(null);

        mockMvc.perform(patch("/api/player/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDto)))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.message").value("Le joueur a été mis à jour avec succès."))
                .andExpect(jsonPath("$.success").value(true));

        verify(updatePlayerService, times(1)).execute(any(Player.class));
    }

    @Test
    public void testDeletePlayer_Success() throws Exception {
        when(getPlayerService.byUserName("TestUser")).thenReturn(new Player("TestUser", "token", 1, 50, new ArrayList<>()));

        mockMvc.perform(delete("/api/player/delete")
                        .param("username", "TestUser"))
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("$.message").value("Le joueur a été supprimé avec succès."))
                .andExpect(jsonPath("$.success").value(true));

        verify(deletePlayerService, times(1)).execute("TestUser");
    }

    @Test
    public void testDeletePlayer_NotFound() throws Exception {
        when(getPlayerService.byUserName("NonExistentUser")).thenReturn(null);

        mockMvc.perform(delete("/api/player/delete")
                        .param("username", "NonExistentUser"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Aucun joueur trouvé pour le nom d'utilisateur : NonExistentUser"))
                .andExpect(jsonPath("$.success").value(false));

        verify(deletePlayerService, never()).execute("NonExistentUser");
    }
}
*/
