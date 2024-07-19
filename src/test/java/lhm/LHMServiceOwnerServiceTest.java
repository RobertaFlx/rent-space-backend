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

import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.ServiceOwnerRepository;
import com.rentspace.service.ServiceOwnerService;

@ExtendWith(MockitoExtension.class)
public class LHMServiceOwnerServiceTest {
	
	@Mock
    private ServiceOwnerRepository serviceOwnerRepository;

    @InjectMocks
    private ServiceOwnerService serviceOwnerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

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
        Long id = 1L;
        ServiceOwner serviceOwner = new ServiceOwner();
        when(serviceOwnerRepository.findById(id)).thenReturn(Optional.of(serviceOwner));

        // Act
        ServiceOwner result = serviceOwnerService.get(id);

        // Assert
        assertEquals(serviceOwner, result);
    }

    @Test
    public void testGetByServiceId() {
        // Arrange
        Long serviceId = 1L;
        ServiceOwner serviceOwner = new ServiceOwner();
        when(serviceOwnerRepository.findByServiceId(serviceId)).thenReturn(Optional.of(serviceOwner));

        // Act
        ServiceOwner result = serviceOwnerService.getByServiceId(serviceId);

        // Assert
        assertEquals(serviceOwner, result);
    }

    @Test
    public void testGetByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(serviceOwnerRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> serviceOwnerService.get(id));
    }

    @Test
    public void testGetByServiceIdNotFound() {
        // Arrange
        Long serviceId = 1L;
        when(serviceOwnerRepository.findByServiceId(serviceId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> serviceOwnerService.getByServiceId(serviceId));
    }
 
}

