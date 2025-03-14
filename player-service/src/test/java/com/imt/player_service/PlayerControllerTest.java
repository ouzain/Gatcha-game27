/*
package com.imt.player_service;

import com.imt.player_service.Model.ApiResponse;
import com.imt.player_service.OpenFeing.AuthServiceClient;
import com.imt.player_service.Services.PlayerServices.DeletePlayerService;
import com.imt.player_service.Services.PlayerServices.GetPlayerService;
import com.imt.player_service.Services.PlayerServices.UpdatePlayerService;
import com.imt.player_service.Services.PlayerServices.AddPlayerService;
import com.imt.player_service.OpenFeing.InvokClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.imt.player_service.Model.Player;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthServiceClient authServiceClient;

    @Mock
    private InvokClient invokClient;

    @Mock
    private GetPlayerService getPlayerService;

    @Mock
    private AddPlayerService addPlayerService;

    @Mock
    private UpdatePlayerService updatePlayerService;

    @Mock
    private DeletePlayerService deletePlayerService;

    private static final String TOKEN_VALID = "valid-token";
    private static final String TOKEN_INVALID = "invalid-token";
    private static final String USERNAME = "player1";
    private static final String PASSWORD = "password123";

    private Player player;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        player = Player.Builder.builder()
                .username(USERNAME)
                .token(TOKEN_VALID)
                .build();
    }

    // Test pour l'endpoint /acquire-monster
    @Test
    public void testAcquireMonsterSuccess() throws Exception {
        Integer summonedMonsterId = 42;
        when(invokClient.invokeMonster(anyString())).thenReturn(summonedMonsterId);
        when(getPlayerService.byToken(TOKEN_VALID)).thenReturn(player);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/player/acquire-monster")
                        .header("Authorization", "Bearer " + TOKEN_VALID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Monstre acquis avec succès ! (ID: 42)"))
                .andExpect(jsonPath("$.data").value(42));

        verify(invokClient, times(1)).invokeMonster(anyString());
    }

    @Test
    public void testAcquireMonsterFailure_invocationError() throws Exception {
        when(invokClient.invokeMonster(anyString())).thenReturn(new ResponseEntity<>("Service unavailable", HttpStatus.SERVICE_UNAVAILABLE));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/player/acquire-monster")
                        .header("Authorization", "Bearer " + TOKEN_VALID))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Échec de l'acquisition du monstre : Service unavailable"));

        verify(invokClient, times(1)).invokeMonster(anyString());
    }

    @Test
    public void testAcquireMonsterFailure_invalidToken() throws Exception {
        when(authServiceClient.validateToken("Bearer " + TOKEN_INVALID)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/player/acquire-monster")
                        .header("Authorization", "Bearer " + TOKEN_INVALID))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Token invalide"));

        verify(invokClient, never()).invokeMonster(anyString());
    }

    // Test pour l'endpoint /authenticate
    @Test
    public void testAuthenticateSuccess() throws Exception {
        String fakeJwtToken = "fake-token";
        when(authServiceClient.authenticate(anyString(), anyString())).thenReturn(new ApiResponse("Authentication successful", true, fakeJwtToken));
        when(getPlayerService.byUserName(USERNAME)).thenReturn(player);
        when(updatePlayerService.execute(any(Player.class))).thenReturn(player);

        String jsonRequest = "{ \"username\": \"" + USERNAME + "\", \"password\": \"" + PASSWORD + "\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/player/authenticate")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Authentification réussie, token mis à jour."))
                .andExpect(jsonPath("$.data").value("fake-jwt-token"));

        verify(authServiceClient, times(1)).authenticate(USERNAME, PASSWORD);
        verify(getPlayerService, times(1)).byUserName(USERNAME);
        verify(updatePlayerService, times(1)).execute(any(Player.class));
    }

    @Test
    public void testAuthenticateFailure_invalidCredentials() throws Exception {
        when(authServiceClient.authenticate(anyString(), anyString())).thenReturn(new ApiResponse("Invalid credentials", false, ""));

        String jsonRequest = "{ \"username\": \"" + USERNAME + "\", \"password\": \"wrongpassword\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/player/authenticate")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Échec de l'authentification"));

        verify(getPlayerService, never()).byUserName(any());
        verify(updatePlayerService, never()).execute(any());
    }

    @Test
    public void testAuthenticateFailure_playerNotFound() throws Exception {
        when(authServiceClient.authenticate(anyString(), anyString())).thenReturn(new ApiResponse("Authentication successful", true, "fake-jwt-token"));
        when(getPlayerService.byUserName(USERNAME)).thenReturn(null);

        String jsonRequest = "{ \"username\": \"" + USERNAME + "\", \"password\": \"" + PASSWORD + "\" }";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/player/authenticate")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Le joueur n'a pas été trouvé."));

        verify(getPlayerService, times(1)).byUserName(USERNAME);
        verify(updatePlayerService, never()).execute(any());
    }
}*/
