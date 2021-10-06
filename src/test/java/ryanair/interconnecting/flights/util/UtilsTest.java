package ryanair.interconnecting.flights.util;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import ryanair.interconnecting.flights.utils.Util;

@ExtendWith(MockitoExtension.class)
class UtilsTest {

	@InjectMocks
	Util util;

	@Test
	void testTurnInToLocalDateTime() {
		String time = "18:00";
		LocalDateTime localDateTime = LocalDateTime.of(2021, 10, 16, 01, 00);
		LocalDateTime responseLocalDateTime = util.turnInToLocalDateTime(time, localDateTime);
		assertEquals(LocalDateTime.of(2021, 10, 16, 18, 00), responseLocalDateTime);

	}

}
