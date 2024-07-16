package lhm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rentspace.DTO.persist.reservation.PersistPlaceReservationDTO;
import com.rentspace.DTO.response.reservation.ResponsePlaceReservationDTO;
import com.rentspace.exception.ApiRequestException;
import com.rentspace.model.products.Place;
import com.rentspace.model.products.Service;
import com.rentspace.model.reservation.PaymentMethod;
import com.rentspace.model.reservation.PlaceReservation;
import com.rentspace.model.reservation.Status;
import com.rentspace.model.user.EventOwner;
import com.rentspace.model.user.PlaceOwner;
import com.rentspace.repository.PlaceReservationRepository;
import com.rentspace.service.EventOwnerService;
import com.rentspace.service.PlaceOwnerService;
import com.rentspace.service.PlaceReservationService;
import com.rentspace.service.PlaceService;
import com.rentspace.service.ServiceReservationService;
import com.rentspace.service.ServiceService;

public class LHMPlaceReservationServiceTest {

}

