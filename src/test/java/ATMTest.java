import jk.projects.model.ATM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ATMTest {
    private static ATM atm;

    @BeforeAll
    public static void beforeAllTests() {
        atm = ATM.getInstance();
    }
    @Test
    public void calculateNotes() {
        atm.init(150);
        Assertions.assertEquals(3, atm.getFifties());
        Assertions.assertEquals(0, atm.getTwenties());
        atm.init(40);
        Assertions.assertEquals(2, atm.getTwenties());
        Assertions.assertEquals(0, atm.getFifties());
        atm.init(240);
        Assertions.assertEquals(7, atm.getTwenties());
        Assertions.assertEquals(2, atm.getFifties());
        atm.init(275);
        Assertions.assertEquals(270, atm.getAmount());
        Assertions.assertEquals(3, atm.getFifties());
        Assertions.assertEquals(6, atm.getTwenties());
    }

    @Test
    public void checkAmtValidity() {
        atm.init(1520);
        Assertions.assertFalse(atm.isAmtValid(1540)); // requested amt > available amt
        Assertions.assertFalse(atm.isAmtValid(155));
        Assertions.assertTrue(atm.isAmtValid(170));
        Assertions.assertTrue(atm.isAmtValid(80));
        Assertions.assertTrue(atm.isAmtValid(1000));
        Assertions.assertTrue(atm.isAmtValid(550));
    }

    @Test
    public void checkCashAvailability() {
        atm.init(570);

        Assertions.assertTrue(atm.checkCashAvailabilityAndUpdateAmt(500, 1));
        Assertions.assertEquals(70, atm.getAmount());
        Assertions.assertEquals(1, atm.getFifties());
        Assertions.assertEquals(1, atm.getTwenties());

        Assertions.assertTrue(atm.checkCashAvailabilityAndUpdateAmt(50, 1));
        Assertions.assertEquals(20, atm.getAmount());
        Assertions.assertEquals(0, atm.getFifties());
        Assertions.assertEquals(1, atm.getTwenties());

        Assertions.assertTrue(atm.checkCashAvailabilityAndUpdateAmt(20, 1));
        Assertions.assertEquals(0, atm.getAmount());
        Assertions.assertEquals(0, atm.getTwenties());
        Assertions.assertEquals(0, atm.getFifties());

        atm.init(500);

        Assertions.assertTrue(atm.checkCashAvailabilityAndUpdateAmt(170, 1));
        Assertions.assertEquals(330, atm.getAmount());
        Assertions.assertEquals(9, atm.getTwenties());
        Assertions.assertEquals(3, atm.getFifties());

        atm.init(850);

        Assertions.assertTrue(atm.checkCashAvailabilityAndUpdateAmt(850, 1));
        Assertions.assertEquals(0, atm.getAmount());
        Assertions.assertEquals(0, atm.getFifties());
        Assertions.assertEquals(0, atm.getTwenties());

        atm.init(100);
        Assertions.assertTrue(atm.checkCashAvailabilityAndUpdateAmt(520, 2));
        Assertions.assertEquals(620, atm.getAmount());
        Assertions.assertEquals(8, atm.getFifties());
        Assertions.assertEquals(11, atm.getTwenties());
    }
}
