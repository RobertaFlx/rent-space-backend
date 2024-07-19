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
import org.modelmapper.ModelMapper;

import com.rentspace.DTO.persist.PersistUserDTO;
import com.rentspace.DTO.response.ResponseUserDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.user.AppUser;
import com.rentspace.repository.UserRepository;
import com.rentspace.service.UserService;

@ExtendWith(MockitoExtension.class)
public class LHMUserServiceTest {

	@Mock
    private UserRepository<AppUser> userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        // Arrange
        AppUser user = new AppUser();

        // Act
        userService.save(user);

        // Assert
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetByEmail() {
        // Arrange
        String email = "email@example.com";
        AppUser user = new AppUser();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        AppUser result = userService.get(email);

        // Assert
        assertEquals(user, result);
    }

    @Test
    public void testGetById() {
        // Arrange
        Long id = 1L;
        AppUser user = new AppUser();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        AppUser result = userService.get(id);

        // Assert
        assertEquals(user, result);
    }
/**
    @Test
    public void testCreate() {
        // Arrange
        PersistUserDTO persistDTO = new PersistUserDTO();
        AppUser user = new AppUser();
        when(userRepository.findByEmail(persistDTO.getEmail())).thenReturn(Optional.empty());
        when(modelMapper.map(persistDTO, AppUser.class)).thenReturn(user);

        // Act
        ResponseUserDTO result = userService.create(persistDTO);

        // Assert
        assertEquals(user, result);
    }

    @Test
    public void testUpdate() {
        // Arrange
        Long id = 1L;
        PersistUserDTO persistUserDTO = new PersistUserDTO();
        AppUser user = new AppUser();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(modelMapper.map(persistUserDTO, AppUser.class)).thenReturn(user);

        // Act
        ResponseUserDTO result = userService.update(id, persistUserDTO);

        // Assert
        assertEquals(user, result);
    }

    @Test
    public void testDelete() {
        // Arrange
        Long id = 1L;
        AppUser user = new AppUser();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        ResponseUserDTO result = userService.delete(id);

        // Assert
        assertEquals(user, result);
    }**/

    @Test
    public void testGetByEmailNotFound() {
        // Arrange
        String email = "email@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> userService.get(email));
    }

    @Test
    public void testGetByIdNotFound() {
        // Arrange
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ApiRequestException.class, () -> userService.get(id));
    }
}

