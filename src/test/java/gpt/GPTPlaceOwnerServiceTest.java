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
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceOwnerRepository;
import com.rentspace.service.PlaceOwnerService;

@ExtendWith(MockitoExtension.class)
public class GPTPlaceOwnerServiceTest {

    @Mock
    private PlaceOwnerRepository placeOwnerRepository;

    @InjectMocks
    private PlaceOwnerService placeOwnerService;

    @Test
    public void testSave() {
        // Mock data
        PlaceOwner placeOwner = new PlaceOwner();
        
        // Mock behavior
        when(placeOwnerRepository.save(placeOwner)).thenReturn(placeOwner);
        
        // Call the method
        placeOwnerService.save(placeOwner);
        
        // Verify behavior
        verify(placeOwnerRepository, times(1)).save(placeOwner);
    }

    @Test
    public void testGet() {
        // Mock data
        Long ownerId = 1L;
        PlaceOwner placeOwner = new PlaceOwner();
        placeOwner.setId(ownerId);
        
        // Mock behavior
        when(placeOwnerRepository.findById(ownerId)).thenReturn(Optional.of(placeOwner));
        
        // Call the method
        PlaceOwner result = placeOwnerService.get(ownerId);
        
        // Verify behavior
        assertEquals(placeOwner, result);
    }

    @Test
    public void testGet_NotFound() {
        // Mock data
        Long ownerId = 1L;
        
        // Mock behavior
        when(placeOwnerRepository.findById(ownerId)).thenReturn(Optional.empty());
        
        // Call the method and verify behavior
        assertThrows(ApiRequestException.class, () -> placeOwnerService.get(ownerId));
    }

    @Test
    public void testGetByPlaceId() {
        // Mock data
        Long placeId = 1L;
        PlaceOwner placeOwner = new PlaceOwner();
        
        // Mock behavior
        when(placeOwnerRepository.findByPlaceId(placeId)).thenReturn(Optional.of(placeOwner));
        
        // Call the method
        PlaceOwner result = placeOwnerService.getByPlaceId(placeId);
        
        // Verify behavior
        assertEquals(placeOwner, result);
    }

    @Test
    public void testGetByPlaceId_NotFound() {
        // Mock data
        Long placeId = 1L;
        
        // Mock behavior
        when(placeOwnerRepository.findByPlaceId(placeId)).thenReturn(Optional.empty());
        
        // Call the method and verify behavior
        assertThrows(ApiRequestException.class, () -> placeOwnerService.getByPlaceId(placeId));
    }
}


