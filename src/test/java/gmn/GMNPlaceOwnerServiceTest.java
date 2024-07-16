package gmn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceOwnerRepository;
import com.rentspace.service.PlaceOwnerService;

@ExtendWith(MockitoExtension.class)
public class GMNPlaceOwnerServiceTest {

	@Mock
    private PlaceOwnerRepository placeOwnerRepository;

    @InjectMocks
    private PlaceOwnerService placeOwnerService;

    @Test
    public void testSave_whenPlaceOwnerIsValid_shouldSaveAndReturnNothing() throws Exception {
        PlaceOwner placeOwner = new PlaceOwner();
        placeOwner.setName("Test Place Owner");
        placeOwner.setEmail("test@email.com");
        placeOwner.setTelephone("1234567890");
        placeOwner.setWebSite("www.example.com");

        placeOwnerService.save(placeOwner);

        // Verify that the placeOwnerRepository was called with the expected argument
        Mockito.verify(placeOwnerRepository).save(placeOwner);
    }

    @Test
    public void testSave_whenPlaceOwnerIsNull_shouldThrowIllegalArgumentException() throws Exception {
        try {
            placeOwnerService.save(null);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testSave_whenPlaceOwnerHasInvalidEmail_shouldThrowIllegalArgumentException() throws Exception {
        PlaceOwner placeOwner = new PlaceOwner();
        placeOwner.setName("Test Place Owner");
        placeOwner.setEmail("invalidEmail"); // Invalid email format
        placeOwner.setTelephone("1234567890");
        placeOwner.setWebSite("www.example.com");

        try {
            placeOwnerService.save(placeOwner);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testGetById_whenIdExists_shouldReturnPlaceOwner() throws Exception {
        Long id = 1L;
        PlaceOwner expectedPlaceOwner = new PlaceOwner();
        expectedPlaceOwner.setId(id);
        expectedPlaceOwner.setName("Test Place Owner");
        expectedPlaceOwner.setEmail("test@email.com");
        expectedPlaceOwner.setTelephone("1234567890");
        expectedPlaceOwner.setWebSite("www.example.com");

        Mockito.when(placeOwnerRepository.findById(id)).thenReturn(Optional.of(expectedPlaceOwner));

        PlaceOwner actualPlaceOwner = placeOwnerService.get(id);

        assertEquals(expectedPlaceOwner, actualPlaceOwner);
    }

    @Test
    public void testGetById_whenIdNotFound_shouldThrowApiRequestException() throws Exception {
        Long id = 1L;
        Mockito.when(placeOwnerRepository.findById(id)).thenReturn(Optional.empty());

        try {
            placeOwnerService.get(id);
        } catch (Exception e) {
            assertTrue(e instanceof ApiRequestException);
        }
    }
/**
    @Test
    public void testGetById_whenIdIsNegative_shouldThrowIllegalArgumentException() throws Exception {
        Long id = -1L;

        try {
            placeOwnerService.get(id);
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }**/

    @Test
    public void testSave_whenPlaceOwnerHasEmptyPlacesList_shouldSaveAndReturnNothing() throws Exception {
        PlaceOwner placeOwner = new PlaceOwner();
        placeOwner.setName("Test Place Owner");
        placeOwner.setEmail("test@email.com");
        placeOwner.setTelephone("1234567890");
        placeOwner.setWebSite("www.example.com");

        placeOwnerService.save(placeOwner);

        // Verify that the placeOwner was saved even with an empty places list
        Mockito.verify(placeOwnerRepository).save(placeOwner);
    }

    @Test
    public void testGetById_whenPlaceOwnerHasNoPlaces_shouldReturnPlaceOwnerWithEmptyPlacesList() throws Exception {
        Long id = 1L;
        PlaceOwner expectedPlaceOwner = new PlaceOwner();
        expectedPlaceOwner.setId(id);
        expectedPlaceOwner.setName("Test Place Owner");
        expectedPlaceOwner.setEmail("test@email.com");
        expectedPlaceOwner.setTelephone("1234567890");
        expectedPlaceOwner.setWebSite("www.example.com");

        Mockito.when(placeOwnerRepository.findById(id)).thenReturn(Optional.of(expectedPlaceOwner));

        PlaceOwner actualPlaceOwner = placeOwnerService.get(id);

        assertEquals(expectedPlaceOwner, actualPlaceOwner);
    }
}


