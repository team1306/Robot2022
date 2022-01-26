import static java.lang.System.*;
// import static org.junit.Assert.assertEquals;

import org.junit.*;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.hal.HAL;
// import edu.wpi.first.wpilibj.simulation.PWMSim;

public class SomeTest {
    private Shooter shooter;
    // private PWMSim simMotor;

    @Before
    public void setup() {
        assert HAL.initialize(500, 0);
        out.println("Setup");
        shooter = new Shooter();
        // simMotor = new PWMSim(0);
    }

    @After
    public void shutdown() {
        out.println("done");
    }

    @Test
    public void someTest() {
        System.out.println("joe");
        shooter.moveMotor(1);
        // assertEquals("idk", 1, simMotor.getSpeed(), 1e-5);
    }

    @Test
    public void nothing() {
        // assertEquals("idk2", 0, simMotor.getSpeed(), 1e-5);
    }
}
