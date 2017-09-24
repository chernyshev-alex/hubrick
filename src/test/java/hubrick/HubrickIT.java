package hubrick;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HubrickIT {

    private FakeDataSets datasets;
    private Hubrick instance;
    
    @Before
    public void setUp() { 
        instance = new Hubrick();
        datasets =  new FakeDataSets();
        instance.setDatasets(datasets);
    }
    
    @Test
    public void testMedianAgeByDepartment() {
        Map<Integer, Double> result = instance.medianAgeByDepartment();
        assertEquals(30.0, result.get(1).doubleValue(), 0.1);
    }

    @Test
    public void testMedianIncomeByDepartment() {
        Map<Integer, Double> result = instance.medianIncomeByDepartment();
        assertEquals(100.0, result.get(1).doubleValue(), 0.1);
    }

    /**
     * Test of averageIncomeByAgeRanges method, of class Hubrick.
     */
    @Test
    public void testAverageIncomeByAgeRanges() {
        Map<Integer, Double> result = instance.averageIncomeByAgeRanges();
        assertEquals(150.0, result.get(40).doubleValue(), 0.1);
    }

    /**
     * Test of persentileIncomeByDepartments method, of class Hubrick.
     */
    @Test
    public void testPersentileIncomeByDepartments() {
        Map<String, Double> result = instance.persentileIncomeByDepartments(95.0);
        result.forEach((k, v) -> System.out.println(k + ":" + v));
        assertEquals(150.0, result.get("alex").doubleValue(), 0.1);
    }
}
