package gmn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.ServiceOwnerRepository;
import com.rentspace.service.ServiceOwnerService;

@ExtendWith(MockitoExtension.class)
public class GMNServiceOwnerServiceTest {
    @Mock
    private ServiceOwnerRepository serviceOwnerRepository;

    private ServiceOwnerService serviceOwnerService;

    private ServiceOwner owner;

    @BeforeEach
    public void setUp() {
        serviceOwnerService = new ServiceOwnerService(serviceOwnerRepository);
        owner = new ServiceOwner();
    }

    @Test
    public void testSave_shouldCallRepositorySave() {
        serviceOwnerService.save(owner);

        verify(serviceOwnerRepository).save(owner);
    }

    @Test
    public void testGetById_shouldReturnOwnerWhenFound() {
        Long id = 1L;
        when(serviceOwnerRepository.findById(id)).thenReturn(Optional.of(owner));

        ServiceOwner retrievedOwner = serviceOwnerService.get(id);

        assertEquals(owner, retrievedOwner);
    }

    @Test
    public void testGetById_shouldThrowExceptionWhenNotFound() {
        Long id = 1L;
        when(serviceOwnerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> serviceOwnerService.get(id));
    }

    @Test
    public void testGetByServiceId_shouldReturnOwnerWhenFound() {
        Long serviceId = 1L;
        when(serviceOwnerRepository.findByServiceId(serviceId)).thenReturn(Optional.of(owner));

        ServiceOwner retrievedOwner = serviceOwnerService.getByServiceId(serviceId);

        assertEquals(owner, retrievedOwner);
    }

    @Test
    public void testGetByServiceId_shouldThrowExceptionWhenNotFound() {
        Long serviceId = 1L;
        when(serviceOwnerRepository.findByServiceId(serviceId)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> serviceOwnerService.getByServiceId(serviceId));
    }
}

