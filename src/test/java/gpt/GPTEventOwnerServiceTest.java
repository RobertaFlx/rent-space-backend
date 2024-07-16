package gpt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.EventOwner;
import com.rentspace.repository.EventOwnerRepository;
import com.rentspace.service.EventOwnerService;

@ExtendWith(MockitoExtension.class)
public class GPTEventOwnerServiceTest {

    @Mock
    private EventOwnerRepository eventOwnerRepository;

    @InjectMocks
    private EventOwnerService eventOwnerService;

    @Test
    void save() {
        // Preparação do teste
        EventOwner eventOwner = new EventOwner();

        // Execução do método
        eventOwnerService.save(eventOwner);

        // Verificação do resultado
        verify(eventOwnerRepository, times(1)).save(eventOwner);
    }

    @Test
    void get_existingId_shouldReturnEventOwner() {
        // Preparação do teste
        long eventId = 1L;
        EventOwner eventOwner = new EventOwner();
        when(eventOwnerRepository.findById(eventId)).thenReturn(Optional.of(eventOwner));

        // Execução do método
        EventOwner result = eventOwnerService.get(eventId);

        // Verificação do resultado
        assertEquals(eventOwner, result);
    }

    @Test
    void get_nonExistingId_shouldThrowException() {
        // Preparação do teste
        long eventId = 1L;
        when(eventOwnerRepository.findById(eventId)).thenReturn(Optional.empty());

        // Execução e verificação do resultado
        assertThrows(ApiRequestException.class, () -> eventOwnerService.get(eventId));
    }

    @Test
    void getByPlaceReservation_existingId_shouldReturnEventOwner() {
        // Preparação do teste
        long reservationId = 1L;
        EventOwner eventOwner = new EventOwner();
        when(eventOwnerRepository.findByPlaceReservation(reservationId)).thenReturn(Optional.of(eventOwner));

        // Execução do método
        EventOwner result = eventOwnerService.getByPlaceReservation(reservationId);

        // Verificação do resultado
        assertEquals(eventOwner, result);
    }

    @Test
    void getByPlaceReservation_nonExistingId_shouldThrowException() {
        // Preparação do teste
        long reservationId = 1L;
        when(eventOwnerRepository.findByPlaceReservation(reservationId)).thenReturn(Optional.empty());

        // Execução e verificação do resultado
        assertThrows(ApiRequestException.class, () -> eventOwnerService.getByPlaceReservation(reservationId));
    }

    @Test
    void getByServiceReservation_existingId_shouldReturnEventOwner() {
        // Preparação do teste
        long reservationId = 1L;
        EventOwner eventOwner = new EventOwner();
        when(eventOwnerRepository.findByServiceReservation(reservationId)).thenReturn(Optional.of(eventOwner));

        // Execução do método
        EventOwner result = eventOwnerService.getByServiceReservation(reservationId);

        // Verificação do resultado
        assertEquals(eventOwner, result);
    }

    @Test
    void getByServiceReservation_nonExistingId_shouldThrowException() {
        // Preparação do teste
        long reservationId = 1L;
        when(eventOwnerRepository.findByServiceReservation(reservationId)).thenReturn(Optional.empty());

        // Execução e verificação do resultado
        assertThrows(ApiRequestException.class, () -> eventOwnerService.getByServiceReservation(reservationId));
    }
}

