package test.com.parkinglot;

import com.parkinglot.entities.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import com.parkinglot.entities.Car;
import com.parkinglot.entities.Color;
import com.parkinglot.exceptions.ParkingException;
import com.parkinglot.service.ParkingService;
import com.parkinglot.service.impl.ParkingServiceImpl;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.Optional;

@FixMethodOrder(MethodSorters.JVM)
@RunWith(JUnit4.class)
public class TestParkingService {

    private int level;
    private ParkingService parkingService;
    @Before
    public void setUp() throws ParkingException {
        level=1;
        parkingService = new ParkingServiceImpl();
        parkingService.createParkingLot(level, 2);
    }

    @Test
    public void testPark() throws ParkingException {
        parkingService.park(level, new Car("KA09MH0180", Color.BLACK));
        Assert.assertEquals(java.util.Optional.of(1), parkingService.getAvailableSlotsCount(level));
    }

    @Test
    public void testUnPark() throws ParkingException {
        parkingService.unPark(level, 1);
        Assert.assertEquals(java.util.Optional.of(2), parkingService.getAvailableSlotsCount(level));
    }

    @Test
    public void testSeek() throws ParkingException {
        Optional<Integer> carSlot = parkingService.park(level, new Car("KA09MH0181", Color.BLUE));
        Optional<Vehicle> vehicle = parkingService.seek(level, carSlot.get());
        Assert.assertEquals("BLUE", vehicle.get().getColor().name());
    }

    @Test(expected = ParkingException.class)
    public void testParkException() throws ParkingException {
        parkingService.park(level, new Car("KA09MH0180", Color.BLACK));
        parkingService.park(level, new Car("KA09MH01834", Color.MAROON));
    }

    @Test(expected = ParkingException.class)
    public void testSeekException() throws ParkingException {
        parkingService.seek(level, 5);
    }
}
