
// import static org.junit.Assert.assertEquals;


import org.junit.*;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.hal.HAL;
// import edu.wpi.first.wpilibj.simulation.PWMSim;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * testers probably
 */
public class SomeTest {
    private DriveTrain dtrain;
    private Shooter shooter;

    @Before
    public void setup() {
        assert HAL.initialize(500, 0);
        System.out.println("Setup ");
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
        CommandScheduler.getInstance().run();
    }
}
