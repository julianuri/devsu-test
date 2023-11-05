import com.devsu.dto.ClientDTO;
import com.devsu.dto.Response;
import com.devsu.entities.Client;
import com.devsu.repositories.ClientRepository;
import com.devsu.services.ClientService;
import com.devsu.utils.PasswordHashUtils;
import com.devsu.utils.enums.SaveOperation;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.intuit.karate.junit5.Karate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    ClientRepository clientRepository;

    @Mock
    PasswordHashUtils passwordHashUtils;

    @InjectMocks
    ClientService clientService;

    private static final WireMockServer wireMockServer
            = new WireMockServer(WireMockConfiguration.options().port(8095));

    @Karate.Test
    Karate testClientsIntegration() {
        return Karate.run("clients").relativeTo(getClass());
    }

    @BeforeAll
    public static void setUp() {
        wireMockServer.start();
        configureFor("localhost", 8095);
        stubFor(
                get(urlEqualTo("/clientes/1"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{ \"id\": \"1234\", \"name\": \"Julian\", \"age\": 35," +
                                        "\"phone\": \"123456789\", \"address\": \"Calle 123\"} "
                                )));

    }

    @AfterAll
    public static void tearDown() {
        wireMockServer.stop();
    }

    ClientDTO getClientRequest() {
        return new ClientDTO(
                "Julian",
                "M",
                30,
                "123456789",
                "Calle 123",
                "123456789",
                "123456789",
                Boolean.TRUE,
                35L
        );
    }

    ClientDTO getInvalidUpdateClientRequest() {
        return new ClientDTO(
                "Julian",
                "M",
                30,
                "123456789",
                "Calle 123",
                "123456789",
                "123456789",
                null,
                null
        );
    }

    Client getClient() {
        Client client = new Client();
        client.setName("Julian");
        client.setPassword("hashedPassword");
        return client;
    }

    @Test
    void createClientSuccess() {
        ClientDTO request = getClientRequest();
        Client c = getClient();
        doReturn(c).when(clientRepository).save(any(Client.class));
        doReturn("hashedPassword").when(passwordHashUtils).createHash("123456789");

        ResponseEntity<Response> response = clientService.saveClient(request, SaveOperation.CREATE);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());

        Client client = (Client) Objects.requireNonNull(response.getBody()).getData().get("client");
        assertEquals("Julian", client.getName());
        assertEquals("hashedPassword", client.getPassword());
    }

    @Test
    void updateClientNotFound() {
        ClientDTO request = getClientRequest();
        doReturn(Optional.empty()).when(clientRepository).findById(request.id());

        ResponseEntity<Response> response = clientService.saveClient(request, SaveOperation.UPDATE);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());
    }

    @Test
    void updateClientWithoutIdOrStatus() {
        ClientDTO request = getInvalidUpdateClientRequest();


        ResponseEntity<Response> response = clientService.saveClient(request, SaveOperation.UPDATE);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode().value());
    }
}
