package gmn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rentspace.DTO.persist.reservation.PersistServiceReservationDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.ServiceReservation;
import com.rentspace.model.reservation.Status;
import com.rentspace.model.user.EventOwner;
import com.rentspace.repository.ServiceReservationRepository;
import com.rentspace.service.EventOwnerService;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceReservationService;
import com.rentspace.service.ServiceService;

@ExtendWith(MockitoExtension.class)
public class GMNServiceReservationServiceTest {
	
	@Mock
    private ServiceReservationRepository serviceReservationRepository;
    @Mock
    private EventOwnerService eventOwnerService;
    @Mock
    private ServiceService serviceService;
    @Mock
    private PlaceService placeService;

    @InjectMocks
    private ServiceReservationService serviceReservationService;

    @Test
    public void testSave_shouldSaveReservation() {
        // Given
        ServiceReservation reservation = new ServiceReservation();

        // When
        serviceReservationService.save(reservation);

        // Then
        Mockito.verify(serviceReservationRepository).save(reservation);
    }

    @Test
    public void testGet_shouldGetReservationById() {
        // Given
        Long id = 1L;
        ServiceReservation reservation = new ServiceReservation();
        Mockito.when(serviceReservationRepository.findById(id)).thenReturn(Optional.of(reservation));

        // When
        serviceReservationService.get(id);

        // Then
        Mockito.verify(serviceReservationRepository).findById(id);
    }

    @Test
    public void testGet_shouldThrowExceptionWhenReservationNotFound() {
        // Given
        Long id = 1L;
        Mockito.when(serviceReservationRepository.findById(id)).thenReturn(Optional.empty());

        // When
        try {
            serviceReservationService.get(id);
            // If no exception is thrown, the test fails
            fail("Expected an exception but none was thrown");
        } catch (Exception e) {
            // Verify the exception type
            assertTrue(e instanceof ApiRequestException);
        }
    }
    /**
    @Test
    public void testUpdateStatus_shouldUpdateReservationStatus() {
        // Given
        Long id = 1L;
        ServiceReservation reservation = new ServiceReservation();
        Status newStatus = Status.REFUSED;
        Mockito.when(serviceReservationService.get(1L)).thenReturn(reservation);

        // When
        serviceReservationService.updateStatus(id, newStatus);

        // Then
        Mockito.verify(reservation).setStatus(newStatus);
        Mockito.verify(serviceReservationRepository).save(reservation);
    }

    @Test
    public void testDelete_shouldDeleteReservation() {
        // Given
        Long id = 1L;
        ServiceReservation reservation = new ServiceReservation();
        Mockito.when(serviceReservationService.get(id)).thenReturn(reservation);

        // When
        serviceReservationService.delete(id);

        // Then
        Mockito.verify(serviceReservationRepository).delete(reservation);
    }

    @Test
    public void testCreate_shouldThrowExceptionForInvalidPersistDTO() {
        // Given an invalid DTO with missing fields
        PersistServiceReservationDTO persistDTO = new PersistServiceReservationDTO();

        // When attempting to create the reservation
        try {
            serviceReservationService.create(persistDTO);
            fail("Expected an exception but none was thrown");
        } catch (Exception e) {
            // Verify the exception type
            assertTrue(e instanceof IllegalArgumentException);
        }
    }
    
    @Test
    public void testCreate_shouldThrowExceptionWhenEventOwnerNotFound() {
        // Given a persistDTO with an event owner ID
        PersistServiceReservationDTO persistDTO = new PersistServiceReservationDTO();
        persistDTO.setEventOwnerId(1L);

        // Mocked eventOwnerService to return null when get is called
        Mockito.when(eventOwnerService.get(persistDTO.getEventOwnerId())).thenReturn(null);

        // When attempting to create the reservation
        try {
            serviceReservationService.create(persistDTO);
            fail("Expected an exception but none was thrown");
        } catch (Exception e) {
            // Verify the exception type
            assertTrue(e instanceof ApiRequestException);
        }
    }
    
    @Test
    public void testCreate_shouldThrowExceptionWhenServiceNotFound() {
        // Given a persistDTO with a service ID
        PersistServiceReservationDTO persistDTO = new PersistServiceReservationDTO();
        persistDTO.setProductId(1L);

        // Mocked serviceService to return null when get is called
        Mockito.when(serviceService.get(persistDTO.getProductId())).thenReturn(null);

        // When attempting to create the reservation
        try {
            serviceReservationService.create(persistDTO);
            fail("Expected an exception but none was thrown");
        } catch (Exception e) {
            // Verify the exception type
            assertTrue(e instanceof ApiRequestException);
        }
    }
    
    @Test
    public void testCreate_shouldThrowExceptionWhenProductNotAvailable() {
        // Given a persistDTO and mocked services
        PersistServiceReservationDTO persistDTO = new PersistServiceReservationDTO();
        EventOwner eventOwner = new EventOwner();
        Service service = new Service();

        // Mocked services to simulate product unavailability
        Mockito.when(eventOwnerService.get(persistDTO.getEventOwnerId())).thenReturn(eventOwner);
        Mockito.when(serviceService.get(persistDTO.getProductId())).thenReturn(service);

        // When attempting to create the reservation
        try {
            serviceReservationService.create(persistDTO);
            fail("Expected an exception but none was thrown");
        } catch (Exception e) {
            // Verify the exception type
            assertTrue(e instanceof ApiRequestException);
        }
    }
    
    @Test
    public void testView_shouldThrowExceptionWhenReservationNotFound() {
        // Given a reservation ID and mocked get method to return null
        Long id = 1L;
        Mockito.when(serviceReservationService.get(id)).thenReturn(null);

        // When attempting to view the reservation
        try {
            serviceReservationService.view(id);
            fail("Expected an exception but none was thrown");
        } catch (Exception e) {
            // Verify the exception type
            assertTrue(e instanceof ApiRequestException);
        }
    }**/
}
