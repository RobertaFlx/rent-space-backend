package lhm;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.rentspace.DTO.persist.reservation.PersistServiceReservationDTO;
import com.rentspace.DTO.response.reservation.ResponseServiceReservationDTO;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
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
public class LHMServiceReservationServiceTest {

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

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ServiceReservationService serviceReservationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        // Arrange
        ServiceReservation serviceReservation = new ServiceReservation();

        // Act
        serviceReservationService.save(serviceReservation);

        // Assert
        verify(serviceReservationRepository, times(1)).save(serviceReservation);
    }

    @Test
    public void testGet() {
        // Arrange
        Long id = 1L;
        ServiceReservation serviceReservation = new ServiceReservation();
        when(serviceReservationRepository.findById(id)).thenReturn(Optional.of(serviceReservation));

        // Act
        ServiceReservation result = serviceReservationService.get(id);

        // Assert
        assertEquals(serviceReservation, result);
    }
/**
    @Test
    public void testCreate() {
        // Arrange
        PersistServiceReservationDTO persistDTO = new PersistServiceReservationDTO();
        EventOwner eventOwner = new EventOwner();
        Service service = new Service();
        ServiceOwner serviceOwner = new ServiceOwner();
        List<Place> places = new ArrayList<>();
        when(eventOwnerService.get(persistDTO.getEventOwnerId())).thenReturn(eventOwner);
        when(serviceService.get(persistDTO.getProductId())).thenReturn(service);
        when(serviceOwnerService.getByServiceId(service.getId())).thenReturn(serviceOwner);
        when(placeService.getAllByExclusiveService(service.getId())).thenReturn(places);

        // Act
        ResponseServiceReservationDTO result = serviceReservationService.create(persistDTO);

        // Assert
        assertEquals(eventOwner, result.getEventOwner());
    }

    @Test
    public void testView() {
        // Arrange
        Long id = 1L;
        ServiceReservation serviceReservation = new ServiceReservation();
        EventOwner eventOwner = new EventOwner();
        ServiceOwner serviceOwner = new ServiceOwner();
        List<Place> places = new ArrayList<>();
        when(serviceReservationRepository.findById(id)).thenReturn(Optional.of(serviceReservation));
        when(eventOwnerService.getByServiceReservation(id)).thenReturn(eventOwner);
        when(serviceOwnerService.getByServiceId(serviceReservation.getProduct().getId())).thenReturn(serviceOwner);
        when(placeService.getAllByExclusiveService(id)).thenReturn(places);

        // Act
        ResponseServiceReservationDTO result = serviceReservationService.view(id);

        // Assert
        assertEquals(serviceReservation, result);
    }

    @Test
    public void testUpdateStatus() {
        // Arrange
        Long id = 1L;
        ServiceReservation serviceReservation = new ServiceReservation();
        Status status = Status.ACCEPTED;
        when(serviceReservationRepository.findById(id)).thenReturn(Optional.of(serviceReservation));

        // Act
        ResponseServiceReservationDTO result = serviceReservationService.updateStatus(id, status);

        // Assert
        assertEquals(status, serviceReservation.getStatus());
    }**/

    @Test
    public void testDelete() {
        // Arrange
        Long id = 1L;
        ServiceReservation serviceReservation = new ServiceReservation();
        when(serviceReservationRepository.findById(id)).thenReturn(Optional.of(serviceReservation));

        // Act
        ResponseServiceReservationDTO result = serviceReservationService.delete(id);

        // Assert
        verify(serviceReservationRepository, times(1)).delete(serviceReservation);
    }
/**
    @Test
    public void testCheckAvailableService() {
        // Arrange
        PersistServiceReservationDTO persistDTO = new PersistServiceReservationDTO();
        Service service = new Service();
        List<Place> places = new ArrayList<>();
        when(serviceService.get(persistDTO.getProductId())).thenReturn(service);
        when(serviceService.getRelatedPlaces(service.getId())).thenReturn(places);

        // Act and Assert
        assertDoesNotThrow(() -> serviceReservationService.checkAvailableService(persistDTO, service));
    }**/
}
