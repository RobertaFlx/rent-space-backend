package gmn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

class GMNPlaceServiceTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private PlaceOwnerService placeOwnerService;

    private PlaceService placeService;

    private Place place;
    private PlaceOwner owner;
    private PersistPlaceDTO placeDTO;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
        placeService = new PlaceService(placeRepository, placeOwnerService);
        place = new Place();
        owner = new PlaceOwner();
        placeDTO = new PersistPlaceDTO("title", "description", new ArrayList<>(),
                "address", "city", "neighborhood", "complement", "zipCode",
                BigDecimal.TEN, 10, 1L);
    }

    @Test
    public void testSave_shouldCallRepositorySave() {
        placeService.save(place);

        verify(placeRepository).save(place);
    }
/**
    @Test
    public void testCreate_shouldCallOwnerServiceGetAndSavePlaceAndOwner() {
        when(placeOwnerService.get(placeDTO.getOwnerId())).thenReturn(owner);

        placeService.create(placeDTO);

        verify(placeOwnerService).get(placeDTO.getOwnerId());
        verify(placeService).save(place);
        verify(placeOwnerService).save(owner);
    }

    @Test
    public void testCreate_shouldBuildAndReturnResponseDTO() {
        when(placeOwnerService.get(placeDTO.getOwnerId())).thenReturn(owner);

        ResponsePlaceDTO response = placeService.create(placeDTO);

        assertNotNull(response);
        assertEquals(place.getId(), response.getId());
    }**/

    @Test
    public void testGet_shouldReturnPlaceWhenFound() {
        Long id = 1L;
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));

        Place retrievedPlace = placeService.get(id);

        assertEquals(place, retrievedPlace);
    }

    @Test
    public void testGet_shouldThrowExceptionWhenNotFound() {
        Long id = 1L;
        when(placeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> placeService.get(id));
    }

    @Test
    public void testView_shouldGetPlaceAndOwnerAndBuildResponse() {
        Long id = 1L;
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(placeOwnerService.getByPlaceId(id)).thenReturn(owner);

        ResponsePlaceDTO response = placeService.view(id);

        assertEquals(place.getId(), response.getId());
        verify(placeOwnerService).getByPlaceId(id);
    }

    @Test
    public void testView_shouldThrowExceptionWhenPlaceNotFound() {
        Long id = 1L;
        when(placeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> placeService.view(id));
    }

    @Test
    public void testViewAll_shouldCallRepositoryFindAllAndMapToList() {
        List<Place> places = new ArrayList<>();
        when(placeRepository.findAll()).thenReturn(places);

        placeService.viewAll();

        verify(placeRepository).findAll();
    }

    @Test
    public void testGetAllByExclusiveService_shouldCallRepositoryMethod() {
        Long serviceId = 1L;

        placeService.getAllByExclusiveService(serviceId);

        verify(placeRepository).getAllByExclusiveService(serviceId);
    }
    /**
    @Test
    public void testDelete_shouldGetPlaceAndOwnerAndDeletePlaceAndOwner() {
        Long id = 1L;
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(placeOwnerService.getByPlaceId(id)).thenReturn(owner);

        placeService.delete(id);

        verify(placeRepository).findById(id);
        verify(placeOwnerService).getByPlaceId(id);
        verify(placeOwnerService).save(owner);
        verify(placeRepository).delete(place);
    }

    @Test
    public void testDelete_shouldBuildAndReturnResponseDTO() {
        Long id = 1L;
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(placeOwnerService.getByPlaceId(id)).thenReturn(owner);

        ResponsePlaceDTO response = placeService.delete(id);

        assertNotNull(response);
        assertEquals(place.getId(), response.getId());
    }**/
/**
    @Test
    public void testUpdate_shouldGetPlaceAndMapAndSave() {
        Long id = 1L;
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));

        placeService.update(id, placeDTO);

        verify(placeRepository).findById(id);
        verify(placeService).save(place);
    }**/

    @Test
    public void testUpdate_shouldBuildAndReturnResponseDTO() {
        Long id = 1L;
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));

        ResponsePlaceDTO response = placeService.update(id, placeDTO);

        assertNotNull(response);
        assertEquals(id, response.getId());
    }

    @Test
    public void testUpdate_shouldThrowExceptionWhenPlaceNotFound() {
        Long id = 1L;
        when(placeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> placeService.update(id, placeDTO));
    }
}
