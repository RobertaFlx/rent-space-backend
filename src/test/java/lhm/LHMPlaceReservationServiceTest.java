package lhm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.rentspace.DTO.persist.reservation.PersistPlaceReservationDTO;
import com.rentspace.DTO.response.reservation.ResponsePlaceReservationDTO;
import com.rentspace.model.products.Place;
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

@ExtendWith(MockitoExtension.class)
public class LHMPlaceReservationServiceTest {
	
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

    @InjectMocks
    private PlaceReservationService placeReservationService;

    @Test
    void testSave() {
        PlaceReservation placeReservation = new PlaceReservation();
        placeReservationService.save(placeReservation);
        verify(placeReservationRepository).save(placeReservation);
    }

    @Test
    void testGet() {
        PlaceReservation placeReservation = new PlaceReservation();
        when(placeReservationRepository.findById(anyLong())).thenReturn(Optional.of(placeReservation));
        assertEquals(placeReservation, placeReservationService.get(1L));
    }
/**
    @Test
    void testCreate() {
        PersistPlaceReservationDTO persistDTO = new PersistPlaceReservationDTO();
        when(placeService.get(anyLong())).thenReturn(new Place());
        when(eventOwnerService.get(anyLong())).thenReturn(new EventOwner());
        when(placeOwnerService.getByPlaceId(anyLong())).thenReturn(new PlaceOwner());
        when(eventOwnerService.getByPlaceReservation(anyLong())).thenReturn(new EventOwner());
        ResponsePlaceReservationDTO response = placeReservationService.create(persistDTO);
        assertNotNull(response);
    }

    @Test
    void testView() {
        PlaceReservation placeReservation = new PlaceReservation();
        when(placeReservationRepository.findById(anyLong())).thenReturn(Optional.of(placeReservation));
        when(placeOwnerService.getByPlaceId(anyLong())).thenReturn(new PlaceOwner());
        when(eventOwnerService.getByPlaceReservation(anyLong())).thenReturn(new EventOwner());
        ResponsePlaceReservationDTO response = placeReservationService.view(1L);
        assertNotNull(response);
    }

    @Test
    void testUpdateStatus() {
        PlaceReservation placeReservation = new PlaceReservation();
        when(placeReservationRepository.findById(anyLong())).thenReturn(Optional.of(placeReservation));
        ResponsePlaceReservationDTO response = placeReservationService.updateStatus(1L, Status.ACCEPTED);
        assertEquals(Status.ACCEPTED, response.getStatus());
    }**/

    @Test
    void testDelete() {
        PlaceReservation placeReservation = new PlaceReservation();
        when(placeReservationRepository.findById(anyLong())).thenReturn(Optional.of(placeReservation));
        placeReservationService.delete(1L);
        verify(placeReservationRepository).delete(placeReservation);
    }

    @Test
    void testViewAll() {
        List<PlaceReservation> placeReservations = new ArrayList<>();
        when(placeReservationRepository.findAll()).thenReturn(placeReservations);
        List<ResponsePlaceReservationDTO> responses = placeReservationService.viewAll();
        assertNotNull(responses);
    }

}

