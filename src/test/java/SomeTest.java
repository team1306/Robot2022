import static org.junit.Assert.assertEquals;

import java.lang.System;
// import static org.junit.Assert.assertEquals;

import com.ctre.phoenix.motorcontrol.TalonSRXSimCollection;

import org.junit.*;

import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.hal.HAL;
// import edu.wpi.first.wpilibj.simulation.PWMSim;

/**
 * testers probably
 */
public class SomeTest {
    private DriveTrain dtrain;

    @Before
    public void setup() {
        assert HAL.initialize(500, 0);
        System.out.println("Setup");
        dtrain = new DriveTrain();
    }

    @After
    public void shutdown() throws Exception {
        System.out.println("done");
        dtrain.close();
    }

    @Test
    public void someTest() {
        System.out.println("test 1");
        dtrain.adjustedArcadeDrive(1, 0.5);
        dtrain.arcadeDrive(1, 0.5);
    }

    @Test
    public void nothing() {
        System.out.println("test 2");
    }
}
