package gpt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.rentspace.DTO.listed.ListedServiceDTO;
import com.rentspace.DTO.persist.product.PersistServiceDTO;
import com.rentspace.DTO.response.product.ResponseServiceDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.ServiceRepository;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceOwnerService;
import com.rentspace.service.ServiceService;

@ExtendWith(MockitoExtension.class)
public class GPTServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;
    
    @Mock
    private ServiceOwnerService serviceOwnerService;
    
    @Mock
    private PlaceService placeService;

    @InjectMocks
    private ServiceService serviceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save() {
        Service service = new Service();
        serviceService.save(service);
        verify(serviceRepository, times(1)).save(service);
    }

    @Test
    void get_existingId_shouldReturnService() {
        Long id = 1L;
        Service expectedService = new Service();
        when(serviceRepository.findById(id)).thenReturn(Optional.of(expectedService));

        Service result = serviceService.get(id);

        assertEquals(expectedService, result);
    }

    @Test
    void get_nonExistingId_shouldThrowException() {
        Long id = 1L;
        when(serviceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> serviceService.get(id));
    }

    /**
    @Test
    void create() {
        PersistServiceDTO persistDTO = new PersistServiceDTO();
        persistDTO.setOwnerId(1L);
        persistDTO.setPlacesIdsRelated(List.of(1L, 2L));
        ServiceOwner owner = new ServiceOwner();
        when(serviceOwnerService.get(persistDTO.getOwnerId())).thenReturn(owner);
        when(placeService.get(any())).thenReturn(new Place());

        ResponseServiceDTO result = serviceService.create(persistDTO);

        assertNotNull(result);
        // Add more assertions as needed
    }**/

    @Test
    void view() {
        Long id = 1L;
        Service service = new Service();
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(serviceOwnerService.getByServiceId(id)).thenReturn(new ServiceOwner());
        when(placeService.getAllByExclusiveService(id)).thenReturn(new ArrayList<>());

        ResponseServiceDTO result = serviceService.view(id);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void delete() {
        Long id = 1L;
        Service service = new Service();
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(serviceOwnerService.getByServiceId(id)).thenReturn(new ServiceOwner());
        when(placeService.getAllByExclusiveService(id)).thenReturn(new ArrayList<>());

        ResponseServiceDTO result = serviceService.delete(id);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void update() {
        Long id = 1L;
        PersistServiceDTO persistDTO = new PersistServiceDTO();
        Service service = new Service();
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));

        ResponseServiceDTO result = serviceService.update(id, persistDTO);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void getServiceNatures() {
        List<String> serviceNatures = serviceService.getServiceNatures();
        assertNotNull(serviceNatures);
        // Add more assertions as needed
    }

    @Test
    void getRelatedPlaces() {
        Long serviceId = 1L;
        List<Place> places = new ArrayList<>();
        when(placeService.getAllByExclusiveService(serviceId)).thenReturn(places);

        List<Place> result = serviceService.getRelatedPlaces(serviceId);

        assertNotNull(result);
        // Add more assertions as needed
    }

    @Test
    void viewAll() {
        List<Service> services = new ArrayList<>();
        when(serviceRepository.findAll()).thenReturn(services);

        List<ListedServiceDTO> result = serviceService.viewAll();

        assertNotNull(result);
        // Add more assertions as needed
    }
/**
    @Test
    void viewByOwner() {
        Long ownerId = 1L;
        ServiceOwner owner = new ServiceOwner();
        when(serviceOwnerService.get(ownerId)).thenReturn(owner);

        List<ListedServiceDTO> result = serviceService.viewByOwner(ownerId);

        assertNotNull(result);
        // Add more assertions as needed
    }**/
}