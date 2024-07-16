package gmn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.EventOwner;
import com.rentspace.repository.EventOwnerRepository;
import com.rentspace.service.EventOwnerService;

@ExtendWith(MockitoExtension.class)
public class GMNEventOwnerServiceTest {

	@Mock
    private EventOwnerRepository eventOwnerRepository;

    private EventOwnerService eventOwnerService;

    private EventOwner owner;

    @BeforeEach
    public void setUp() {
        eventOwnerService = new EventOwnerService(eventOwnerRepository);
        owner = new EventOwner();
    }

    @Test
    public void testSave_shouldCallRepositorySave() {
        eventOwnerService.save(owner);

        verify(eventOwnerRepository).save(owner);
    }

    @Test
    public void testGetById_shouldReturnOwnerWhenFound() {
        Long id = 1L;
        when(eventOwnerRepository.findById(id)).thenReturn(Optional.of(owner));

        EventOwner retrievedOwner = eventOwnerService.get(id);

        assertEquals(owner, retrievedOwner);
    }

    @Test
    public void testGetById_shouldThrowExceptionWhenNotFound() {
        Long id = 1L;
        when(eventOwnerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> eventOwnerService.get(id));
    }

    @Test
    public void testGetByPlaceReservation_shouldReturnOwnerWhenFound() {
        Long reservationId = 1L;
        when(eventOwnerRepository.findByPlaceReservation(reservationId)).thenReturn(Optional.of(owner));

        EventOwner retrievedOwner = eventOwnerService.getByPlaceReservation(reservationId);

        assertEquals(owner, retrievedOwner);
    }

    @Test
    public void testGetByPlaceReservation_shouldThrowExceptionWhenNotFound() {
        Long reservationId = 1L;
        when(eventOwnerRepository.findByPlaceReservation(reservationId)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> eventOwnerService.getByPlaceReservation(reservationId));
    }

    @Test
    public void testGetByServiceReservation_shouldReturnOwnerWhenFound() {
        Long reservationId = 1L;
        when(eventOwnerRepository.findByServiceReservation(reservationId)).thenReturn(Optional.of(owner));

        EventOwner retrievedOwner = eventOwnerService.getByServiceReservation(reservationId);

        assertEquals(owner, retrievedOwner);
    }

    @Test
    public void testGetByServiceReservation_shouldThrowExceptionWhenNotFound() {
        Long reservationId = 1L;
        when(eventOwnerRepository.findByServiceReservation(reservationId)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> eventOwnerService.getByServiceReservation(reservationId));
    }
}

