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
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.ServiceOwnerRepository;
import com.rentspace.service.ServiceOwnerService;

@ExtendWith(MockitoExtension.class)
public class GPTServiceOwnerServiceTest {
 
    @Mock
    private ServiceOwnerRepository serviceOwnerRepository;

    @InjectMocks
    private ServiceOwnerService serviceOwnerService;

    @Test
    public void testSave() {
        // Arrange
        ServiceOwner serviceOwner = new ServiceOwner();
        
        // Act
        serviceOwnerService.save(serviceOwner);

        // Assert
        verify(serviceOwnerRepository, times(1)).save(serviceOwner);
    }

    @Test
    public void testGet() {
        // Arrange
        Long ownerId = 1L;
        ServiceOwner serviceOwner = new ServiceOwner();
        when(serviceOwnerRepository.findById(ownerId)).thenReturn(Optional.of(serviceOwner));

        // Act
        ServiceOwner retrievedServiceOwner = serviceOwnerService.get(ownerId);

        // Assert
        assertEquals(serviceOwner, retrievedServiceOwner);
    }

    @Test
    public void testGetNonExistentOwner() {
        // Arrange
        Long ownerId = 1L;
        when(serviceOwnerRepository.findById(ownerId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> serviceOwnerService.get(ownerId));
    }

    @Test
    public void testGetByServiceId() {
        // Arrange
        Long serviceId = 1L;
        ServiceOwner serviceOwner = new ServiceOwner();
        when(serviceOwnerRepository.findByServiceId(serviceId)).thenReturn(Optional.of(serviceOwner));

        // Act
        ServiceOwner retrievedServiceOwner = serviceOwnerService.getByServiceId(serviceId);

        // Assert
        assertEquals(serviceOwner, retrievedServiceOwner);
    }

    @Test
    public void testGetByNonExistentServiceId() {
        // Arrange
        Long serviceId = 1L;
        when(serviceOwnerRepository.findByServiceId(serviceId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> serviceOwnerService.getByServiceId(serviceId));
    }
}

