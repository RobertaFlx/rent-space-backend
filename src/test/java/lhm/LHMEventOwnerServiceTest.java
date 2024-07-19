package lhm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.EventOwner;
import com.rentspace.repository.EventOwnerRepository;
import com.rentspace.service.EventOwnerService;

@ExtendWith(MockitoExtension.class)
public class LHMEventOwnerServiceTest {
	
	@Mock
    private EventOwnerRepository eventOwnerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EventOwnerService eventOwnerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        // Arrange
        EventOwner eventOwner = new EventOwner();

        // Act
        eventOwnerService.save(eventOwner);

        // Assert
        verify(eventOwnerRepository, times(1)).save(eventOwner);
    }

    @Test
    public void testGet() {
        // Arrange
        Long id = 1L;
        EventOwner eventOwner = new EventOwner();
        when(eventOwnerRepository.findById(id)).thenReturn(Optional.of(eventOwner));

        // Act
        EventOwner result = eventOwnerService.get(id);

        // Assert
        assertEquals(eventOwner, result);
    }

    @Test
    public void testGetByPlaceReservation() {
        // Arrange
        Long reservationId = 1L;
        EventOwner eventOwner = new EventOwner();
        when(eventOwnerRepository.findByPlaceReservation(reservationId)).thenReturn(Optional.of(eventOwner));

        // Act
        EventOwner result = eventOwnerService.getByPlaceReservation(reservationId);

        // Assert
        assertEquals(eventOwner, result);
    }

    @Test
    public void testGetByServiceReservation() {
        // Arrange
        Long reservationId = 1L;
        EventOwner eventOwner = new EventOwner();
        when(eventOwnerRepository.findByServiceReservation(reservationId)).thenReturn(Optional.of(eventOwner));

        // Act
        EventOwner result = eventOwnerService.getByServiceReservation(reservationId);

        // Assert
        assertEquals(eventOwner, result);
    }

    @Test
    public void testGetByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(eventOwnerRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> eventOwnerService.get(id));
    }

    @Test
    public void testGetByPlaceReservationNotFound() {
        // Arrange
        Long reservationId = 1L;
        when(eventOwnerRepository.findByPlaceReservation(reservationId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> eventOwnerService.getByPlaceReservation(reservationId));
    }

    @Test
    public void testGetByServiceReservationNotFound() {
        // Arrange
        Long reservationId = 1L;
        when(eventOwnerRepository.findByServiceReservation(reservationId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> eventOwnerService.getByServiceReservation(reservationId));
    }

}

