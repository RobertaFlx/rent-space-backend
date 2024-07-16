package gpt;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rentspace.DTO.persist.reservation.PersistServiceReservationDTO;
import com.rentspace.DTO.response.reservation.ResponseServiceReservationDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.ServiceReservation;
import com.rentspace.model.reservation.Status;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.ServiceReservationRepository;
import com.rentspace.service.EventOwnerService;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceOwnerService;
import com.rentspace.service.ServiceReservationService;
import com.rentspace.service.ServiceService;

@ExtendWith(MockitoExtension.class)
public class GPTServiceReservationServiceTest {

    @Mock
    private ServiceReservationRepository serviceReservationRepository;

    @Mock
    private EventOwnerService eventOwnerService;

    @Mock
    private ServiceOwnerService serviceOwnerService;

    @Mock
    private ServiceService serviceService;

    @Mock
    private PlaceService placeService;

    @InjectMocks
    private ServiceReservationService serviceReservationService;

    private PersistServiceReservationDTO validPersistDTO;

    @BeforeEach
    public void setUp() {
        validPersistDTO = new PersistServiceReservationDTO(
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                PaymentMethod.CREDIT,
                1,
                1L,
                1L,
                "Address",
                "City"
        );
    }
    
    @Test
    public void testSave() {
        ServiceReservation reservation = new ServiceReservation();
        when(serviceReservationRepository.save(any())).thenReturn(reservation);

        serviceReservationService.save(reservation);

        verify(serviceReservationRepository, times(1)).save(reservation);
    }

    @Test
    public void testGet_Success() {
        ServiceReservation reservation = new ServiceReservation();
        when(serviceReservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        ServiceReservation result = serviceReservationService.get(1L);

        assertEquals(reservation, result);
    }

    @Test
    public void testGet_NotFound() {
        when(serviceReservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> serviceReservationService.get(1L));
    }
/**
    @Test
    public void testCreate_Success() {
        ServiceReservation reservation = new ServiceReservation();
        Service service = new Service();
        EventOwner eventOwner = new EventOwner();
        ServiceOwner serviceOwner = new ServiceOwner();
        List<Place> relatedPlaces = new ArrayList<>();

        when(eventOwnerService.get(anyLong())).thenReturn(eventOwner);
        when(serviceService.get(anyLong())).thenReturn(service);
        when(serviceService.getRelatedPlaces(anyLong())).thenReturn(relatedPlaces);
        when(serviceOwnerService.getByServiceId(anyLong())).thenReturn(serviceOwner);
        when(serviceReservationRepository.save(any())).thenReturn(reservation);

        ResponseServiceReservationDTO result = serviceReservationService.create(validPersistDTO);

        assertNotNull(result);
        // Adicione asserções conforme necessário
    }**/

    /**
    @Test
    public void testCheckAvailableService_PlaceFound() {
        PersistServiceReservationDTO dto = new PersistServiceReservationDTO();
        Service service = new Service();
        List<Place> places = Collections.singletonList(new Place());
        when(serviceService.getRelatedPlaces(anyLong())).thenReturn(places);

        assertDoesNotThrow(() -> serviceReservationService.checkAvailableService(dto, service));
    }**/
/**
    @Test
    public void testCheckAvailableService_PlaceNotFound() {
        PersistServiceReservationDTO dto = new PersistServiceReservationDTO();
        Service service = new Service();
        when(serviceService.getRelatedPlaces(anyLong())).thenReturn(Collections.emptyList());

        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> serviceReservationService.checkAvailableService(dto, service));
        assertEquals("Service is exclusive", exception.getMessage());
    }**/

    /**
    @Test
    public void testView() {
        ServiceReservation reservation = new ServiceReservation();
        when(serviceReservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(eventOwnerService.getByServiceReservation(1L)).thenReturn(new EventOwner());
        when(serviceOwnerService.getByServiceId(anyLong())).thenReturn(new ServiceOwner());
        when(placeService.getAllByExclusiveService(anyLong())).thenReturn(Collections.emptyList());

        assertNotNull(serviceReservationService.view(1L));
        // Adicione asserções conforme necessário
    }**/

    @Test
    public void testView_NotFound() {
        when(serviceReservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> serviceReservationService.view(1L));
    }

    /**
    @Test
    public void testUpdateStatus_Success() {
        ServiceReservation reservation = new ServiceReservation();
        when(serviceReservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertNotNull(serviceReservationService.updateStatus(1L, Status.ACCEPTED));
        // Adicione asserções conforme necessário
    }**/

    @Test
    public void testUpdateStatus_NotFound() {
        when(serviceReservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> serviceReservationService.updateStatus(1L, Status.ACCEPTED));
    }

    @Test
    public void testDelete_Success() {
        ServiceReservation reservation = new ServiceReservation();
        when(serviceReservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertNotNull(serviceReservationService.delete(1L));
        // Adicione asserções conforme necessário
    }

    @Test
    public void testDelete_NotFound() {
        when(serviceReservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> serviceReservationService.delete(1L));
    }

}
