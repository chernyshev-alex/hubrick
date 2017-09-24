package hubrick.entity;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Employee {

    public final Integer departmentId;
    public final String name;
    public final String sex;
    public final Double salary;

    public Employee(Integer departmentId, String name, String sex, Double salary) {
        this.departmentId = departmentId;
        this.name = name;
        this.sex = sex;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public Double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return new StringJoiner(":", "[", "]").add(getClass().getTypeName())
                .add(departmentId.toString())
                .add(name).add(sex.toString())
                .add(salary.toString()).toString();
    }

    public static final Function<List<String>, Employee> getFactory() {
        return ls -> {
            Employee empl = null;
            try {
                empl = new Employee(
                        Integer.parseInt(ls.get(0)),
                        ls.get(1).trim().toLowerCase(),
                        ls.get(2).trim().toLowerCase(),
                        Double.parseDouble(ls.get(3)));
            } catch (Exception e) {
                Logger.getLogger(Datasets.class.getName()).log(Level.SEVERE, e.getMessage() + "\n" + ls.toString());
            }
            return empl;
        };
    }

}
