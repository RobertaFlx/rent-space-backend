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
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceOwnerRepository;
import com.rentspace.service.PlaceOwnerService;

@ExtendWith(MockitoExtension.class)
public class LHMPlaceOwnerServiceTest {

	@Mock
    private PlaceOwnerRepository placeOwnerRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PlaceOwnerService placeOwnerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        // Arrange
        PlaceOwner placeOwner = new PlaceOwner();

        // Act
        placeOwnerService.save(placeOwner);

        // Assert
        verify(placeOwnerRepository, times(1)).save(placeOwner);
    }

    @Test
    public void testGet() {
        // Arrange
        Long id = 1L;
        PlaceOwner placeOwner = new PlaceOwner();
        when(placeOwnerRepository.findById(id)).thenReturn(Optional.of(placeOwner));

        // Act
        PlaceOwner result = placeOwnerService.get(id);

        // Assert
        assertEquals(placeOwner, result);
    }

    @Test
    public void testGetByPlaceId() {
        // Arrange
        Long placeId = 1L;
        PlaceOwner placeOwner = new PlaceOwner();
        when(placeOwnerRepository.findByPlaceId(placeId)).thenReturn(Optional.of(placeOwner));

        // Act
        PlaceOwner result = placeOwnerService.getByPlaceId(placeId);

        // Assert
        assertEquals(placeOwner, result);
    }

    @Test
    public void testGetByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(placeOwnerRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> placeOwnerService.get(id));
    }

    @Test
    public void testGetByPlaceIdNotFound() {
        // Arrange
        Long placeId = 1L;
        when(placeOwnerRepository.findByPlaceId(placeId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> placeOwnerService.getByPlaceId(placeId));
    }
}


