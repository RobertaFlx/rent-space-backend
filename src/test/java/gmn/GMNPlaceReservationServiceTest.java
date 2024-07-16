package gmn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.rentspace.model.reservation.ServiceReservation;
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

public class GMNPlaceReservationServiceTest {

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

    @InjectMocks
    private PlaceReservationService placeReservationService;
    
    @BeforeEach 
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_ValidReservation() {
        PersistPlaceReservationDTO dto = new PersistPlaceReservationDTO(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                PaymentMethod.PIX,
                1,
                1L, // Place ID
                2,  // Number of participants
                List.of(1L), // Hired service ID
                3L  // Event owner ID
        );

        PlaceReservation reservation = new PlaceReservation();
        reservation.setId(1L);
        reservation.setStartsAt(dto.getStartsAt());
        reservation.setEndsAt(dto.getEndsAt());
        reservation.setPaymentMethod(dto.getPaymentMethod());
        reservation.setNumOfInstallments(dto.getNumOfInstallments());
        reservation.setProduct(new Place());
        reservation.setNumOfParticipants(dto.getNumOfParticipants());
        reservation.setStatus(Status.PENDING);
        reservation.setHiredRelatedServices(new ArrayList<>());

        // Mock behavior of collaborators
        when(placeService.get(dto.getProductId())).thenReturn(new Place());
        when(serviceService.get(dto.getHiredRelatedServicesIds().get(0))).thenReturn(new Service());
        when(placeReservationRepository.save(reservation)).thenReturn(reservation);

        // Call the method
        placeReservationService.save(reservation);

        // Verify interactions
        verify(placeReservationRepository).save(reservation);
    }
/**
    @Test
    public void testSave_NullReservation() {
        assertThrows(NullPointerException.class, () -> placeReservationService.save(null));
    }**/

    @Test
    public void testGet_ExistingReservation() {
        PlaceReservation reservation = new PlaceReservation();
        reservation.setId(1L);

        when(placeReservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        PlaceReservation retrievedReservation = placeReservationService.get(1L);

        assertEquals(reservation, retrievedReservation);
    }

    @Test
    public void testGet_NonexistentReservation() {
        when(placeReservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> placeReservationService.get(1L));
    } 
    /**
    @Test
    public void testCreate_ValidReservation() throws Exception {
        // Create a valid reservation DTO
        PersistPlaceReservationDTO dto = new PersistPlaceReservationDTO(
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(2),
                PaymentMethod.PIX,
                1,
                1L, // Place ID
                2,  // Number of participants
                List.of(1L), // Hired service ID
                3L  // Event owner ID
        );

        // Mock collaborator behavior
        Place place = mock(Place.class);
        when(place.getId()).thenReturn(1L);
        when(place.getMaximumCapacity()).thenReturn(10);
        when(placeService.get(dto.getProductId())).thenReturn(place);

        Service service = mock(Service.class);
        when(service.getId()).thenReturn(1L);
        when(service.getPeopleInvolved()).thenReturn(1);
        when(serviceService.get(dto.getHiredRelatedServicesIds().get(0))).thenReturn(service);

        EventOwner eventOwner = mock(EventOwner.class);
        when(eventOwner.getId()).thenReturn(3L);
        when(eventOwnerService.get(dto.getEventOwnerId())).thenReturn(eventOwner);

        PlaceOwner placeOwner = mock(PlaceOwner.class);
        when(placeOwnerService.getByPlaceId(place.getId())).thenReturn(placeOwner);

        // Expected final prices
        BigDecimal placeFinalPrice = new BigDecimal(100); // Replace with calculation logic
        BigDecimal servicesFinalPrice = new BigDecimal(50); // Replace with calculation logic

        // Stub repository save behavior
        when(placeReservationRepository.save(any(PlaceReservation.class))).thenReturn(null); // Don't care about saved object

        // Call the method
        ResponsePlaceReservationDTO createdReservation = placeReservationService.create(dto);

        // Verify interactions
        verify(placeService).get(dto.getProductId());
        verify(serviceService).get(dto.getHiredRelatedServicesIds().get(0));
        verify(eventOwnerService).get(dto.getEventOwnerId());
        verify(placeOwnerService).getByPlaceId(place.getId());
        verify(placeOwnerService).save(placeOwner);
        verify(eventOwnerService).save(eventOwner);
        verify(serviceReservationService).save(any(ServiceReservation.class));
        verify(placeReservationRepository).save(any(PlaceReservation.class));
    }
    
    @Test
    public void testView_ExistingReservation() {
        PlaceReservation reservation = new PlaceReservation();
        reservation.setId(1L);

        when(placeReservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        ResponsePlaceReservationDTO response = placeReservationService.view(1L);

        assertNotNull(response);
        assertEquals(reservation.getId(), response.getId());
        assertEquals(reservation.getStartsAt(), response.getStartsAt());
        assertEquals(reservation.getEndsAt(), response.getEndsAt());
        assertEquals(reservation.getPaymentMethod(), response.getPaymentMethod());
        assertEquals(reservation.getNumOfInstallments(), response.getNumOfInstallments());
        assertEquals(reservation.getNumOfParticipants(), response.getNumOfParticipants());
        assertEquals(reservation.getStatus(), response.getStatus());
    }**/

    @Test
    public void testView_NonexistentReservation() {
        when(placeReservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> placeReservationService.view(1L));
    }
    /**
    @Test
    public void testUpdateStatus_ValidReservationAndStatus() {
        PlaceReservation reservation = new PlaceReservation();
        reservation.setId(1L);
        reservation.setStatus(Status.PENDING);

        when(placeReservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        placeReservationService.updateStatus(1L, Status.ACCEPTED);

        verify(placeReservationRepository).findById(1L);
        verify(placeReservationRepository).save(reservation);
        assertEquals(Status.ACCEPTED, reservation.getStatus());
    }**/

    @Test
    public void testUpdateStatus_NonexistentReservation() {
        when(placeReservationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> placeReservationService.updateStatus(1L, Status.ACCEPTED));
    }
     
    @Test
    public void testDelete_ExistingReservation() {
        PlaceReservation reservation = new PlaceReservation();
        reservation.setId(1L);

        when(placeReservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        placeReservationService.delete(1L);

        verify(placeReservationRepository).findById(1L);
        verify(placeReservationRepository).delete(reservation);
    }
}

