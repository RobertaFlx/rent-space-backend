package lhm;

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

import com.rentspace.DTO.listed.ListedPlaceDTO;
import com.rentspace.DTO.persist.product.PersistPlaceDTO;
import com.rentspace.DTO.response.product.ResponsePlaceDTO;
import com.rentspace.model.products.Place;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceRepository;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.service.PlaceService;

@ExtendWith(MockitoExtension.class)
public class LHMPlaceServiceTest {

	@Mock
    private PlaceRepository placeRepository;

    @Mock
    private PlaceOwnerService placeOwnerService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PlaceService placeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        // Arrange
        Place place = new Place();

        // Act
        placeService.save(place);

        // Assert
        verify(placeRepository, times(1)).save(place);
    }
/**
    @Test
    public void testCreate() {
        // Arrange
        PersistPlaceDTO persistDTO = new PersistPlaceDTO();
        PlaceOwner owner = new PlaceOwner();
        Place place = new Place();
        when(placeOwnerService.get(persistDTO.getOwnerId())).thenReturn(owner);
        when(modelMapper.map(persistDTO, Place.class)).thenReturn(place);

        // Act
        ResponsePlaceDTO result = placeService.create(persistDTO);

        // Assert
        assertEquals(place, result);
    }**/

    @Test
    public void testGet() {
        // Arrange
        Long id = 1L;
        Place place = new Place();
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));

        // Act
        Place result = placeService.get(id);

        // Assert
        assertEquals(place, result);
    }
/**
    @Test
    public void testView() {
        // Arrange
        Long id = 1L;
        Place place = new Place();
        PlaceOwner owner = new PlaceOwner();
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(placeOwnerService.getByPlaceId(id)).thenReturn(owner);

        // Act
        ResponsePlaceDTO result = placeService.view(id);

        // Assert
        assertEquals(place, result);
    }**/

    @Test
    public void testViewAll() {
        // Arrange
        List<Place> places = new ArrayList<>();
        when(placeRepository.findAll()).thenReturn(places);

        // Act
        List<ListedPlaceDTO> result = placeService.viewAll();

        // Assert
        assertEquals(places.size(), result.size());
    }

    @Test
    public void testGetAllByExclusiveService() {
        // Arrange
        Long serviceId = 1L;
        List<Place> places = new ArrayList<>();
        when(placeRepository.getAllByExclusiveService(serviceId)).thenReturn(places);

        // Act
        List<Place> result = placeService.getAllByExclusiveService(serviceId);

        // Assert
        assertEquals(places, result);
    }
/**
    @Test
    public void testDelete() {
        // Arrange
        Long id = 1L;
        Place place = new Place();
        PlaceOwner owner = new PlaceOwner();
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(placeOwnerService.getByPlaceId(id)).thenReturn(owner);

        // Act
        ResponsePlaceDTO result = placeService.delete(id);

        // Assert
        assertEquals(place, result);
    }

    @Test
    public void testUpdate() {
        // Arrange
        Long id = 1L;
        PersistPlaceDTO persistDTO = new PersistPlaceDTO();
        Place place = new Place();
        when(placeRepository.findById(id)).thenReturn(Optional.of(place));
        when(modelMapper.map(persistDTO, Place.class)).thenReturn(place);

        // Act
        ResponsePlaceDTO result = placeService.update(id, persistDTO);

        // Assert
        assertEquals(place, result);
    }**/
}
