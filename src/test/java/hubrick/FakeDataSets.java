package hubrick;

import hubrick.entity.Age;
import hubrick.entity.Datasets;
import hubrick.entity.Department;
import hubrick.entity.Employee;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FakeDataSets extends Datasets {

    static final List<Employee> __empls = Arrays.asList(
            new Employee(1, "alex", "m", 100.0),
            new Employee(2, "dina", "m", 150.0),
            new Employee(3, "marek", "ff", 50.0));
    
    static final List<Department> __deps = Arrays.asList(
            new Department("alex"), new Department("alex"), new Department("alex"));
    
    static final List<Age> __ages = Arrays.asList(
            new Age("alex", 30),
            new Age("dina", 45),
            new Age("marek", 22));
    
     // datasets
    private List<Age>           ages    = __ages;
    private List<Employee>      empls   = __empls;
    private List<Department>    deps    = __deps;
    
    @Override
    public List<Age> getAgesDataset() {
        return ages;
    }

    @Override
    public List<Employee> getEmployeeDataset() {
        return empls;
    }
    
    @Override
    public List<Department> getDepartmentsDataset() {
        return deps;
    }
    
    @Override
    public Stream<Age> getAgesStream() {
        return getAgesDataset().stream();
    }

    @Override
    public Stream<Employee> getEmployeeStream() {
        return getEmployeeDataset().stream();
    }
    
    @Override
    public Stream<Department> getDepartmentsStream() {
        return getDepartmentsDataset().stream();
    }
    
    
}
