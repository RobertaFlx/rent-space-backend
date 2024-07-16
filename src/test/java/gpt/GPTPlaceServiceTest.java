package gpt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rentspace.DTO.persist.product.PersistPlaceDTO;
import com.rentspace.DTO.response.product.ResponsePlaceDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Place;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceRepository;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.service.PlaceService;

class GPTPlaceServiceTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private PlaceOwnerService placeOwnerService;

    @InjectMocks
    private PlaceService placeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        Place place = new Place();
        placeService.save(place);
        verify(placeRepository, times(1)).save(place);
    }

    @Test
    void testCreate() {
        PersistPlaceDTO persistDTO = new PersistPlaceDTO();
        persistDTO.setOwnerId(1L);

        PlaceOwner owner = new PlaceOwner();
        owner.setPlaces(new ArrayList<>());

        when(placeOwnerService.get(anyLong())).thenReturn(owner);

        ResponsePlaceDTO response = placeService.create(persistDTO);

        assertNotNull(response);
        assertNotNull(response.getOwner());
        assertEquals(owner.getPlaces().size(), 1);
        verify(placeRepository, times(1)).save(any(Place.class));
        verify(placeOwnerService, times(1)).save(any(PlaceOwner.class));
    }

    @Test
    void testGet() {
        Long id = 1L;
        Place place = new Place();
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        assertEquals(place, placeService.get(id));
    }

    @Test
    void testGet_ThrowsException() {
        Long id = 1L;
        when(placeRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> placeService.get(id));
    }

    @Test
    void testView() {
        Long id = 1L;
        Place place = new Place();
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(placeOwnerService.getByPlaceId(id)).thenReturn(new PlaceOwner());
        assertNotNull(placeService.view(id));
    }

    @Test
    void testViewAll() {
        List<Place> places = new ArrayList<>();
        places.add(new Place());
        when(placeRepository.findAll()).thenReturn(places);
        assertFalse(placeService.viewAll().isEmpty());
    }

    @Test
    void testGetAllByExclusiveService() {
        Long serviceId = 1L;
        List<Place> places = new ArrayList<>();
        when(placeRepository.getAllByExclusiveService(serviceId)).thenReturn(places);
        assertEquals(places, placeService.getAllByExclusiveService(serviceId));
    }

    @Test
    void testDelete() {
        Long id = 1L;
        Place place = new Place();
        PlaceOwner owner = new PlaceOwner();
        owner.setPlaces(new ArrayList<>());
        owner.getPlaces().add(place);

        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(placeOwnerService.getByPlaceId(id)).thenReturn(owner);

        ResponsePlaceDTO response = placeService.delete(id);

        assertNotNull(response);
        assertTrue(owner.getPlaces().isEmpty());
        verify(placeOwnerService, times(1)).save(any(PlaceOwner.class));
        verify(placeRepository, times(1)).delete(any(Place.class));
    }

    @Test
    void testDelete_ThrowsException() {
        Long id = 1L;
        when(placeRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> placeService.delete(id));
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        PersistPlaceDTO persistDTO = new PersistPlaceDTO();

        Place place = new Place();
        PlaceOwner owner = new PlaceOwner();

        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(placeOwnerService.getByPlaceId(id)).thenReturn(owner);

        ResponsePlaceDTO response = placeService.update(id, persistDTO);

        assertNotNull(response);
        verify(placeRepository, times(1)).save(any(Place.class));
    }

    @Test
    void testUpdate_ThrowsException() {
        Long id = 1L;
        PersistPlaceDTO persistDTO = new PersistPlaceDTO();

        when(placeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> placeService.update(id, persistDTO));
    }
}
