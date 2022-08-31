package code.controller;

import code.controller.base.BaseController;
import code.dto.client.ClientGetDto;
import code.dto.entities.boat.BoatGetDto;
import code.dto.fishing_instructor.FishingInstructorGetDto;
import code.exceptions.entities.EntityNotFoundException;
import code.model.Client;
import code.model.boat.Boat;
import code.repository.ClientRepository;
import code.service.ClientService;
import code.utils.TokenUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController extends BaseController {

    private final ClientService _clientService;
    private final ClientRepository _clientRepository;

    public ClientController(ModelMapper mapper, TokenUtils tokenUtils, ClientService clientService, ClientRepository clientRepository) {
        super(mapper, tokenUtils);
        _clientService = clientService;
        _clientRepository = clientRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Object>> get(){
        return ResponseEntity.ok(_mapper.map(_clientService.getAllClients(), new TypeToken<List<ClientGetDto>>() {}.getType()));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> get(@PathVariable Integer id){
        try{
            Client client = _clientService.getClient(id);
            ClientGetDto clientDto = _mapper.map(client, ClientGetDto.class);
            return ResponseEntity.ok(clientDto);
        }catch(Exception ex){
            if(ex instanceof EntityNotFoundException) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
            return ResponseEntity.internalServerError().body("Oops, something went wrong, try again later!");
        }
    }

    @GetMapping(value = "/getLoggedInClient")
    @PreAuthorize("hasRole('CLIENT')")
    public ResponseEntity<ClientGetDto> getLoggedInClient() {
        return ResponseEntity.ok(_mapper.map(_clientService.getLoggedInClient(), ClientGetDto.class));
    }
}
