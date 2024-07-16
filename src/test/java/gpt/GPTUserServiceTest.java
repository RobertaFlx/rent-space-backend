package gpt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.AppUser;
import com.rentspace.model.user.UserType;
import com.rentspace.repository.UserRepository;
import com.rentspace.service.UserService;

@ExtendWith(MockitoExtension.class)
public class GPTUserServiceTest {
 
    @Mock
    private UserRepository<AppUser> userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void save_UserSaved_Successfully() {
        // Given
        AppUser user = new AppUser();
        
        // When
        userService.save(user);
        
        // Then
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void get_ByEmail_UserFound_Successfully() {
        // Given
        String email = "test@example.com";
        AppUser user = new AppUser();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // When
        AppUser result = userService.get(email);

        // Then
        assertEquals(user, result);
    }

    @Test
    public void get_ByEmail_UserNotFound_ThrowsException() {
        // Given
        String email = "test@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ApiRequestException.class, () -> userService.get(email));
    }

    @Test
    public void get_ById_UserFound_Successfully() {
        // Given
        Long id = 1L;
        AppUser user = new AppUser();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // When
        AppUser result = userService.get(id);

        // Then
        assertEquals(user, result);
    }

    @Test
    public void get_ById_UserNotFound_ThrowsException() {
        // Given
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ApiRequestException.class, () -> userService.get(id));
    }

    @Test
    public void create_NewUser_SuccessfullyCreated() {
        // Given
        PersistUserDTO persistDTO = new PersistUserDTO();
        persistDTO.setEmail("test@example.com");
        persistDTO.setUserType(UserType.EVENT_OWNER);
        when(userRepository.findByEmail(persistDTO.getEmail())).thenReturn(Optional.empty());

        // When
        ResponseUserDTO result = userService.create(persistDTO);

        // Then
        assertNotNull(result);
        verify(userRepository, times(1)).save(any(AppUser.class));
    }

    @Test
    public void create_UserWithEmailAlreadyExists_ThrowsException() {
        // Given
        PersistUserDTO persistDTO = new PersistUserDTO();
        persistDTO.setEmail("test@example.com");
        persistDTO.setUserType(UserType.EVENT_OWNER);
        when(userRepository.findByEmail(persistDTO.getEmail())).thenReturn(Optional.of(new AppUser()));

        // When/Then
        assertThrows(ApiRequestException.class, () -> userService.create(persistDTO));
        verify(userRepository, never()).save(any(AppUser.class));
    }
/**
    @Test
    public void update_UserUpdated_Successfully() {
        // Given
        Long id = 1L;
        PersistUserDTO persistUserDTO = new PersistUserDTO();
        AppUser existingUser = new AppUser();
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        // When
        ResponseUserDTO result = userService.update(id, persistUserDTO);

        // Then
        assertNotNull(result);
        verify(userRepository, times(1)).save(existingUser);
    }**/

    @Test
    public void update_NonExistentUser_ThrowsException() {
        // Given
        Long id = 1L;
        PersistUserDTO persistUserDTO = new PersistUserDTO();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ApiRequestException.class, () -> userService.update(id, persistUserDTO));
        verify(userRepository, never()).save(any(AppUser.class));
    }
/**
    @Test
    public void delete_UserDeleted_Successfully() {
        // Given
        Long id = 1L;
        AppUser existingUser = new AppUser();
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        // When
        ResponseUserDTO result = userService.delete(id);

        // Then
        assertNotNull(result);
        verify(userRepository, times(1)).delete(existingUser);
    }**/

    @Test
    public void delete_NonExistentUser_ThrowsException() {
        // Given
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(ApiRequestException.class, () -> userService.delete(id));
        verify(userRepository, never()).delete(any(AppUser.class));
    }
}

