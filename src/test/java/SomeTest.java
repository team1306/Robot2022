import static org.junit.Assert.assertEquals;

import java.lang.System;
// import static org.junit.Assert.assertEquals;

import com.ctre.phoenix.motorcontrol.TalonSRXSimCollection;

import org.junit.*;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.hal.HAL;
// import edu.wpi.first.wpilibj.simulation.PWMSim;

/**
 * testers probably
 */
public class SomeTest {
    private DriveTrain dtrain;
    private Shooter shooter;

    @Before
    public void setup() {
        assert HAL.initialize(500, 0);
        System.out.println("Setup");
        dtrain = new DriveTrain();
        shooter = new Shooter();
    }

    @After
    public void shutdown() throws Exception {
        System.out.println("done");
        HAL.shutdown();
        dtrain.close();
        shooter.close();
    }

    @Test
    public void test1() {
        System.out.println("test 1");
        for (int i = 0; i < 13; i++)
            shooter.moveMotor(0, true, false);
        System.out.println();
        for (int i = 0; i < 25; i++)
            shooter.moveMotor(0, true, true);
    }
}
