/**
 * author chernyshev.alexander@gmail.com
 */
package hubrick;

import java.util.List;
import hubrick.entity.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hubrick {
    
   private Datasets dataSets;  
    
    private Integer finderAge(Employee entity) {
        final Age ZERO_AGE = new Age("", 0); // will be optimized by JVM
        return dataSets.getAgesStream().filter(ea -> ea.name.equalsIgnoreCase(entity.name)).findFirst().orElse(ZERO_AGE).age;
    }

    // Reports are  here
    
    /**
     * employee-age-by-department.csv - median employee age by department
     * @return report dataset
     */
    public Map<Integer, Double> medianAgeByDepartment() {
        return dataSets.getEmployeeStream().collect(
                Collectors.groupingBy(Employee::getDepartmentId, HashMap::new,
                            Collectors.mapping(this::finderAge, 
                                    Collectors.collectingAndThen(Collectors.toList(), 
                                           Utils.median((ToDoubleFunction<Integer>) Integer::doubleValue)))));
    }

    /**
     * income-by-department.csv  median income by department 
     * @return report dataset
     */
    public Map<Integer, Double> medianIncomeByDepartment() {
        return dataSets.getEmployeeStream().collect(
                Collectors.groupingBy(Employee::getDepartmentId, HashMap::new,
                        Collectors.collectingAndThen(Collectors.toList(), 
                                Utils.median((ToDoubleFunction<Employee>) Employee::getSalary))));
    }
    
    /**
     * income-average-by-age-range.csv  average income by age ranges with factor of ten
     * @return report dataset
     */
    public Map<Integer, Double> averageIncomeByAgeRanges() {
        Map<String, Double> emplsMap = dataSets.getEmployeeStream().collect(Collectors.toMap(k -> k.name, v -> v.salary));
        Function<List<Age>, Double> averageSlary = la -> {
            Stream<String> names = la.stream().map(Age::getName);
            return names.mapToDouble(name -> emplsMap.getOrDefault(name, 0.0)).average().getAsDouble();
        };
        return dataSets.getAgesStream().collect(
            Collectors.groupingBy(k -> (k.age /10) * 10, HashMap::new,
                    Collectors.collectingAndThen(Collectors.toList(), averageSlary)));
    }
 
    /**
     * income-95-by-department.csv  95-percentile income by department
     * @param persentileValue 95.0
     * @return report dataset
     */
    public Map<String, Double> persentileIncomeByDepartments(double persentileValue) {
        Function<Employee, String> keyMapper = e -> {
            List<Department> depsDs = dataSets.getDepartmentsDataset();
            if (e.getDepartmentId() > depsDs.size()) {
                return "unknown department with Id " + e.getDepartmentId();
            }
            return dataSets.getDepartmentsDataset().get(e.getDepartmentId() -1).name;
        };
        return dataSets.getEmployeeStream().collect(Collectors.groupingBy(keyMapper, HashMap::new,
                        Collectors.collectingAndThen(Collectors.toList(),
                                e -> Utils.persentile(persentileValue,
                                        e.stream().mapToDouble(Employee::getSalary).toArray()))));
    }
    
    public void setDatasets(Datasets ds) {
        this.dataSets = ds;
    }
    
    public void run(String[] args) {

        setDatasets(new Datasets());

        try {
            dataSets.loadDatasets(args);
            // generate reports
            Datasets.writeCSVFmt(medianAgeByDepartment(), "employee-age-by-department.csv", "%d,%.2f\n", "employee Id, median age");
            Datasets.writeCSVFmt(medianIncomeByDepartment(), "income-by-department.csv", "%d,%.2f\n", "department Id,median income");
            Datasets.writeCSVFmt(averageIncomeByAgeRanges(), "income-average-by-age-range.csv", "%d,%.2f\n", "age, average income");
            Datasets.writeCSVFmt(persentileIncomeByDepartments(95.0), "income-95-by-department.csv", "%s,%.2f\n", "department name,p95");
            
        } catch (IOException ex) {
            Logger.getLogger(Hubrick.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static  <K, V> void print(Map<K, V> map) {
        map.forEach((k, v) -> System.out.println(k.toString() + " :" + v.toString()));
    }
    
    public static void main(String[] args) {
        new Hubrick().run(args);
    }
    
}
