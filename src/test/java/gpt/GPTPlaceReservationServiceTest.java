package gpt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rentspace.DTO.persist.reservation.PersistPlaceReservationDTO;
import com.rentspace.DTO.response.reservation.ResponsePlaceReservationDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.PlaceReservation;
import com.rentspace.model.reservation.Status;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceReservationRepository;
import com.rentspace.service.EventOwnerService;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.service.PlaceReservationService;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceReservationService;
import com.rentspace.service.ServiceService;

public class GPTPlaceReservationServiceTest {

    @InjectMocks
    private PlaceReservationService placeReservationService;

    @Mock
    private PlaceReservationRepository placeReservationRepository;

    @Mock
    private PlaceService placeService;

    @Mock
    private ServiceService serviceService;

    @Mock
    private PlaceOwnerService placeOwnerService;

    @Mock
    private EventOwnerService eventOwnerService;

    @Mock
    private ServiceReservationService serviceReservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        PlaceReservation placeReservation = new PlaceReservation();
        when(placeReservationRepository.save(any(PlaceReservation.class))).thenReturn(placeReservation);

        placeReservationService.save(placeReservation);

        verify(placeReservationRepository, times(1)).save(placeReservation);
    }

    @Test
    public void testGet() {
        PlaceReservation placeReservation = new PlaceReservation();
        Long id = 1L;
        when(placeReservationRepository.findById(id)).thenReturn(Optional.of(placeReservation));

        PlaceReservation result = placeReservationService.get(id);

        assertNotNull(result);
        assertEquals(placeReservation, result);
    }

    @Test
    public void testGet_NonexistentId_ThrowsException() {
        Long id = 1L;
        when(placeReservationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> placeReservationService.get(id));
    }
/**
    @Test
    public void testCreate() {
        PersistPlaceReservationDTO dto = createPersistPlaceReservationDTO();
        Place place = createPlace();
        List<Service> relatedServices = createRelatedServices();

        when(eventOwnerService.get(anyLong())).thenReturn(createEventOwner());
        when(placeService.get(anyLong())).thenReturn(place);
        when(serviceService.get(anyLong())).thenReturn(relatedServices.get(0), relatedServices.get(1));
        when(placeOwnerService.getByPlaceId(anyLong())).thenReturn(createPlaceOwner());

        ResponsePlaceReservationDTO response = placeReservationService.create(dto);

        assertNotNull(response);
        assertEquals(dto.getNumOfParticipants(), response.getNumOfParticipants());
        // Adicione mais asserções conforme necessário para verificar se a reserva foi criada corretamente
    }**/

    @Test
    public void testCreate_InvalidServiceIds_ThrowsException() {
        PersistPlaceReservationDTO dto = createPersistPlaceReservationDTO();
        dto.setHiredRelatedServicesIds(Collections.singletonList(999L)); // Serviço inválido

        when(eventOwnerService.get(anyLong())).thenReturn(createEventOwner());
        when(placeService.get(anyLong())).thenReturn(createPlace());
        when(serviceService.get(anyLong())).thenThrow(new ApiRequestException("Service not found"));

        assertThrows(ApiRequestException.class, () -> placeReservationService.create(dto));
    }
/**
    @Test
    public void testCreate_ExceedMaximumCapacity_ThrowsException() {
        PersistPlaceReservationDTO dto = createPersistPlaceReservationDTO();
        Place place = createPlace();
        place.setMaximumCapacity(5); // Capacidade máxima menor que o número de participantes

        when(eventOwnerService.get(anyLong())).thenReturn(createEventOwner());
        when(placeService.get(anyLong())).thenReturn(place);
        when(serviceService.get(anyLong())).thenReturn(createRelatedServices().get(0));

        assertThrows(ApiRequestException.class, () -> placeReservationService.create(dto));
    }**/

    /**
    @Test
    public void testView() {
        Long id = 1L;
        PlaceReservation reservation = createPlaceReservation();
        when(placeReservationRepository.findById(id)).thenReturn(Optional.of(reservation));

        ResponsePlaceReservationDTO result = placeReservationService.view(id);

        assertNotNull(result);
        assertEquals(reservation.getNumOfParticipants(), result.getNumOfParticipants());
        // Adicione mais asserções conforme necessário para verificar se a visualização está correta
    }**/
/**
    @Test
    public void testUpdateStatus() {
        Long id = 1L;
        PlaceReservation reservation = createPlaceReservation();
        when(placeReservationRepository.findById(id)).thenReturn(Optional.of(reservation));

        ResponsePlaceReservationDTO result = placeReservationService.updateStatus(id, Status.ACCEPTED);

        assertNotNull(result);
        assertEquals(Status.ACCEPTED, result.getStatus());
        // Adicione mais asserções conforme necessário para verificar se o status foi atualizado corretamente
    }**/

    @Test
    public void testDelete() {
        Long id = 1L;
        PlaceReservation reservation = createPlaceReservation();
        when(placeReservationRepository.findById(id)).thenReturn(Optional.of(reservation));

        ResponsePlaceReservationDTO result = placeReservationService.delete(id);

        assertNotNull(result);
        assertEquals(reservation.getId(), result.getId());
        // Adicione mais asserções conforme necessário para verificar se a exclusão ocorreu corretamente
    }

    @Test
    public void testDelete_NonexistentId_ThrowsException() {
        Long id = 1L;
        when(placeReservationRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> placeReservationService.delete(id));
    }

    @Test
    public void testViewAll() {
        List<PlaceReservation> reservations = createPlaceReservationList();
        when(placeReservationRepository.findAll()).thenReturn(reservations);

        List<ResponsePlaceReservationDTO> result = placeReservationService.viewAll();

        assertNotNull(result);
        assertEquals(reservations.size(), result.size());
        // Adicione mais asserções conforme necessário para verificar se a lista de reservas está correta
    }

    // Métodos utilitários para criação de objetos de teste
    private PersistPlaceReservationDTO createPersistPlaceReservationDTO() {
        return new PersistPlaceReservationDTO(LocalDateTime.now(), LocalDateTime.now().plusHours(2),
                PaymentMethod.CREDIT, 1, 1L, 10, Collections.singletonList(1L), 1L);
    }

    private Place createPlace() {
        Place place = new Place();
        place.setId(1L);
        place.setMaximumCapacity(100);
        return place;
    }

    private List<Service> createRelatedServices() {
        List<Service> services = new ArrayList<>();
        Service service1 = new Service();
        service1.setId(1L);
        service1.setPeopleInvolved(5);

        Service service2 = new Service();
        service2.setId(2L);
        service2.setPeopleInvolved(3);

        services.add(service1);
        services.add(service2);

        return services;
    }

    private EventOwner createEventOwner() {
        EventOwner eventOwner = new EventOwner();
        // Configurar propriedades do eventOwner conforme necessário
        return eventOwner;
    }

    private PlaceOwner createPlaceOwner() {
        PlaceOwner placeOwner = new PlaceOwner();
        // Configurar propriedades do placeOwner conforme necessário
        return placeOwner;
    }

    private PlaceReservation createPlaceReservation() {
        PlaceReservation reservation = new PlaceReservation();
        // Configurar propriedades da reserva conforme necessário
        return reservation;
    }

    private List<PlaceReservation> createPlaceReservationList() {
        List<PlaceReservation> reservations = new ArrayList<>();
        // Criar e adicionar várias reservas à lista conforme necessário
        return reservations;
    }
}

