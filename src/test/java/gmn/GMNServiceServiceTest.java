package gmn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rentspace.DTO.listed.ListedServiceDTO;
import com.rentspace.DTO.persist.product.PersistServiceDTO;
import com.rentspace.DTO.response.product.ResponseServiceDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.products.ServiceNature;
import com.rentspace.model.user.ServiceOwner;
import com.rentspace.repository.ServiceRepository;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceOwnerService;
import com.rentspace.service.ServiceService;

@ExtendWith(MockitoExtension.class)
public class GMNServiceServiceTest {

	@Mock
    private ServiceRepository serviceRepository;

    @Mock
    private ServiceOwnerService serviceOwnerService;

    @Mock
    private PlaceService placeService;

    private ServiceService serviceService;

    private Service service;
    private ServiceOwner owner;
    private Place place1;
    private Place place2;
    private PersistServiceDTO persistDTO;
    private List<Long> placeIds;

    @BeforeEach
    public void setUp() {
        serviceService = new ServiceService(serviceRepository, serviceOwnerService, placeService);
        service = new Service();
        service.setId(1L);
        owner = new ServiceOwner();
        place1 = new Place();
        place1.setId(11L);
        place2 = new Place();
        place2.setId(12L);
        persistDTO = new PersistServiceDTO(
                "title", "description", null, "address", "city", "neighborhood",
                BigDecimal.TEN, 1L, ServiceNature.BAR, 10, placeIds); 
        placeIds = new ArrayList<>();
        placeIds.add(11L);
        placeIds.add(12L);
    }

    @Test
    public void testSave_shouldCallRepositorySave() {
        serviceService.save(service);

        verify(serviceRepository).save(service);
    }

    @Test
    public void testGet_shouldGetServiceAndThrowExceptionWhenNotFound() {
        Long id = 1L;
        when(serviceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> serviceService.get(id));
    }

    @Test
    public void testGet_shouldGetService() {
        Long id = 1L;
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));

        Service retrievedService = serviceService.get(id);

        assertEquals(service, retrievedService);
    }
/**
    @Test
    public void testCreate_shouldCallServicesAndSave() {
        when(serviceOwnerService.get(persistDTO.getOwnerId())).thenReturn(owner);
        when(placeService.get(placeIds.get(0))).thenReturn(place1);
        when(placeService.get(placeIds.get(1))).thenReturn(place2);

        serviceService.create(persistDTO);

        verify(serviceOwnerService).get(persistDTO.getOwnerId());
        verify(placeService).get(placeIds.get(0));
        verify(placeService).get(placeIds.get(1));
        verify(serviceRepository).save(any(Service.class));
        verify(serviceOwnerService).save(owner);
    }**/
/**
    @Test
    public void testCreate_shouldBuildAndReturnResponseDTO() {
        when(serviceOwnerService.get(persistDTO.getOwnerId())).thenReturn(owner);
        when(placeService.get(placeIds.get(0))).thenReturn(place1);
        when(placeService.get(placeIds.get(1))).thenReturn(place2);

        ResponseServiceDTO response = serviceService.create(persistDTO);

        assertNotNull(response);
    }**/
    
    @Test
    public void testView_shouldGetServiceAndOwnerAndBuildResponse() {
        Long id = 1L;
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(serviceOwnerService.getByServiceId(id)).thenReturn(owner);

        ResponseServiceDTO response = serviceService.view(id);

        assertEquals(service.getId(), response.getId());
        verify(serviceOwnerService).getByServiceId(id);
    }

    @Test
    public void testView_shouldThrowExceptionWhenServiceNotFound() {
        Long id = 1L;
        when(serviceRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ApiRequestException.class, () -> serviceService.view(id));
    }

    @Test
    public void testViewAll_shouldCallRepositoryFindAllAndMapToList() {
        List<Service> services = new ArrayList<>();
        when(serviceRepository.findAll()).thenReturn(services);

        List<ListedServiceDTO> response = serviceService.viewAll();

        assertEquals(services.size(), response.size());
        // verify mapping from Service to ListedServiceDTO
    }
/**
    @Test
    public void testGetAllByOwner_shouldCallServiceOwnerServiceAndMapToList() {
        Long ownerId = 1L;
        List<Service> services = new ArrayList<>();
        when(serviceOwnerService.get(ownerId)).thenReturn(owner);
        when(owner.getServices()).thenReturn(services);

        List<ListedServiceDTO> response = serviceService.viewByOwner(ownerId);

        assertEquals(services.size(), response.size());
        // verify mapping from Service to ListedServiceDTO
    }**/

    @Test
    public void testDelete_shouldGetServiceAndOwnerAndDeleteServiceAndOwner() {
        Long id = 1L;
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(serviceOwnerService.getByServiceId(id)).thenReturn(owner);

        serviceService.delete(id);

        verify(serviceRepository).findById(id);
        verify(serviceOwnerService).getByServiceId(id);
        verify(serviceRepository).delete(service);
    }

    @Test
    public void testDelete_shouldBuildAndReturnResponseDTO() {
        Long id = 1L;
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));
        when(serviceOwnerService.getByServiceId(id)).thenReturn(owner);

        ResponseServiceDTO response = serviceService.delete(id);

        assertNotNull(response);
        // verify response fields based on your implementation of buildResponse
    }

    @Test
    public void testUpdate_shouldGetServiceAndMapAndSave() {
        Long id = 1L;
        when(serviceRepository.findById(id)).thenReturn(Optional.of(service));

        serviceService.update(id, persistDTO);

        verify(serviceRepository).findById(id);
        // verify mapping from PersistServiceDTO to Service
        verify(serviceRepository).save(any(Service.class));
    }
}