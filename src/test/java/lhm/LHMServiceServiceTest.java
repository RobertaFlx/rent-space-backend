package lhm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.rentspace.DTO.listed.ListedServiceDTO;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.repository.ServiceRepository;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceOwnerService;
import com.rentspace.service.ServiceService;

@ExtendWith(MockitoExtension.class)
public class LHMServiceServiceTest {

	@Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ServiceOwnerService serviceOwnerService;

    @Mock
    private PlaceService placeService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ServiceService serviceService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        // Arrange
        Service service = new Service();

        // Act
        serviceService.save(service);

        // Assert
        verify(serviceRepository, times(1)).save(service);
    }

    @Test
    public void testGet() {
        // Arrange
        Long id = 1L;
        Service service = new Service();
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));

        // Act
        Service result = serviceService.get(id);

        // Assert
        assertEquals(service, result);
    }
/**
    @Test
    public void testCreate() {
        // Arrange
        PersistServiceDTO persistDTO = new PersistServiceDTO();
        ServiceOwner owner = new ServiceOwner();
        List<Place> places = new ArrayList<>();
        when(serviceOwnerService.get(persistDTO.getOwnerId())).thenReturn(owner);
        when(placeService.getAllByExclusiveService(anyLong())).thenReturn(places);
        when(modelMapper.map(persistDTO, Service.class)).thenReturn(new Service());

        // Act
        ResponseServiceDTO result = serviceService.create(persistDTO);

        // Assert
        assertEquals(owner, result.getOwner());
    }

    @Test
    public void testView() {
        // Arrange
        Long id = 1L;
        Service service = new Service();
        ServiceOwner owner = new ServiceOwner();
        List<Place> places = new ArrayList<>();
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(serviceOwnerService.getByServiceId(id)).thenReturn(owner);
        when(placeService.getAllByExclusiveService(id)).thenReturn(places);

        // Act
        ResponseServiceDTO result = serviceService.view(id);

        // Assert
        assertEquals(service, result);
    }

    @Test
    public void testDelete() {
        // Arrange
        Long id = 1L;
        Service service = new Service();
        ServiceOwner owner = new ServiceOwner();
        List<Place> places = new ArrayList<>();
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(serviceOwnerService.getByServiceId(id)).thenReturn(owner);
        when(placeService.getAllByExclusiveService(id)).thenReturn(places);

        // Act
        ResponseServiceDTO result = serviceService.delete(id);

        // Assert
        assertEquals(service, result);
    }
 
    @Test
    public void testUpdate() {
        // Arrange
        Long id = 1L;
        PersistServiceDTO persistDTO = new PersistServiceDTO();
        Service service = new Service();
        ServiceOwner owner = new ServiceOwner();
        List<Place> places = new ArrayList<>();
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(serviceOwnerService.getByServiceId(id)).thenReturn(owner);
        when(placeService.getAllByExclusiveService(id)).thenReturn(places);
        when(modelMapper.map(persistDTO, Service.class)).thenReturn(service);

        // Act
        ResponseServiceDTO result = serviceService.update(id, persistDTO);

        // Assert
        assertEquals(service, result);
    }**/

    @Test
    public void testGetServiceNatures() {
        // Arrange

        // Act
        List<String> result = serviceService.getServiceNatures();
        
        List<String> expected = List.of(Arrays.toString(ServiceNature.values()));

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void testGetRelatedPlaces() {
        // Arrange
        Long serviceId = 1L;
        List<Place> places = new ArrayList<>();
        when(placeService.getAllByExclusiveService(serviceId)).thenReturn(places);

        // Act
        List<Place> result = serviceService.getRelatedPlaces(serviceId);

        // Assert
        assertEquals(places, result);
    }

    @Test
    public void testViewAll() {
        // Arrange
        List<Service> services = new ArrayList<>();
        when(serviceRepository.findAll()).thenReturn(services);

        // Act
        List<ListedServiceDTO> result = serviceService.viewAll();

        // Assert
        assertEquals(services.size(), result.size());
    }
/**
    @Test
    public void testViewByOwner() {
        // Arrange
        Long ownerId = 1L;
        List<Service> services = new ArrayList<>();
        when(serviceOwnerService.get(ownerId).getServices()).thenReturn(services);

        // Act
        List<ListedServiceDTO> result = serviceService.viewByOwner(ownerId);

        // Assert
        assertEquals(services.size(), result.size());
    }**/
}