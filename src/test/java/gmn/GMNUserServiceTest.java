package gmn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.AppUser;
import com.rentspace.model.user.UserType;
import com.rentspace.repository.UserRepository;
import com.rentspace.service.UserService;

@ExtendWith(MockitoExtension.class)
public class GMNUserServiceTest {

    @Mock
    private UserRepository<AppUser> userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void testSave() {
        AppUser appUser = new AppUser("John Doe", "...", "john.doe@example.com", "...", "...");
        userService.save(appUser);
        verify(userRepository).save(appUser);
    }

    @Test
    public void testGet_whenUserNotFoundByEmail() {
        String email = "not.found@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> userService.get(email));
    }

    @Test
    public void testGet_whenUserNotFoundById() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> userService.get(id));
    }

    @Test
    public void testCreate_whenEmailAlreadyExists() {
        PersistUserDTO dto = new PersistUserDTO(UserType.EVENT_OWNER, "John Doe", "...", "john.doe@example.com", "...", "...");
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.of(new AppUser()));
        assertThrows(ApiRequestException.class, () -> userService.create(dto));
    } 
    
    @Test
    public void testGetById_whenIdExists_shouldReturnUser() throws Exception {
        Long id = 1L;
        AppUser user = new AppUser();
        user.setId(id);
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        AppUser returnedUser = userService.get(id);
        assertEquals(user, returnedUser);
    }
    
    @Test
    public void testGetByEmail_whenEmailExists_shouldReturnUser() throws Exception {
        String email = "test@email.com";
        AppUser user = new AppUser();
        user.setEmail(email);
        Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        AppUser returnedUser = userService.get(email);
        assertEquals(user, returnedUser);
    }
/**
    @Test
    public void testCreate_withSuccess() {
        PersistUserDTO dto = new PersistUserDTO(UserType.EVENT_OWNER, "John Doe", "...", "john.doe@example.com", "...", "...");
        AppUser expectedUser = new AppUser(dto.getName(), dto.getProfilePhoto(), dto.getEmail(), dto.getTelephone(), dto.getWebSite());
        when(userRepository.findByEmail(dto.getEmail())).thenReturn(Optional.empty());
        when(modelMapper.map(dto, AppUser.class)).thenReturn(expectedUser); // Map directly to AppUser
        userService.create(dto);
        verify(userRepository).save(expectedUser);
    }**/

    @Test
    public void testUpdate_whenUserNotFound() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> userService.update(id, new PersistUserDTO()));
    }
/**
    @Test
    public void testUpdate_withSuccess() {
        Long id = 1L;
        AppUser user = new AppUser("John Doe", "...", "john.doe@example.com", "...", "...");
        user.setId(id);
        PersistUserDTO dto = new PersistUserDTO(UserType.EVENT_OWNER, "John Doe Updated", "...", "john.doe@example.com", "...", "...");
        AppUser expectedUser = new AppUser(dto.getName(), dto.getProfilePhoto(), dto.getEmail(), dto.getTelephone(), dto.getWebSite());
        expectedUser.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(modelMapper.map(dto, AppUser.class)).thenReturn(expectedUser); // Map directly to AppUser
        userService.update(id, dto);
        verify(userRepository).save(expectedUser);
    }

    @Test
    public void testDelete_withSuccess() {
        Long id = 1L;
        AppUser user = new AppUser("John Doe", "...", "john.doe@example.com", "...", "...");
        user.setId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.delete(id);
        verify(userRepository).delete(user);
    }**/
}

