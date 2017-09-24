package hubrick.entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Datasets {

    static final Logger LOG = Logger.getLogger(Datasets.class.getName());
    static final String DATAROOT = "./data";
    static final String SEPARATOR = ",";

    private static String workDir = DATAROOT;
    
    // datasets
    private List<Age>           ages    = Collections.emptyList();
    private List<Employee>      empls   = Collections.emptyList();
    private List<Department>    deps    = Collections.emptyList();
    

    public List<Age> getAgesDataset() {
        return ages;
    }

    public List<Employee> getEmployeeDataset() {
        return empls;
    }
    
    public List<Department> getDepartmentsDataset() {
        return deps;
    }
    
    public Stream<Age> getAgesStream() {
        return getAgesDataset().stream();
    }

    public Stream<Employee> getEmployeeStream() {
        return getEmployeeDataset().stream();
    }
    
    public Stream<Department> getDepartmentsStream() {
        return getDepartmentsDataset().stream();
    }
    
    private List<List<String>> load(String cvsFileName) throws IOException {
        
        Path path = Paths.get(DATAROOT, cvsFileName);
        Reader reader = Files.newBufferedReader(path);
        try (BufferedReader br = new BufferedReader(reader)) {
            return br.lines()
                    .map(line -> Arrays.asList(line.split(SEPARATOR)))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    };

     private static <T> List<T> loadEntity(String fileName, Function<List<String>, T> mapper) throws IOException {

        Path path = Paths.get(workDir, fileName);
        Reader reader = Files.newBufferedReader(path);
        try (BufferedReader br = new BufferedReader(reader)) {
            return br.lines()
                    //.peek(System.out::println)
                    .map(line -> mapper.apply(Arrays.asList(line.split(SEPARATOR))))
                    .filter(s -> s != null)  // skip invalid formatted records
                    .collect(Collectors.toList());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    };

    public void loadDatasets(String[] args) throws IOException {
        
       if (args.length ==0) {
           workDir = (args.length ==0) ? DATAROOT : args[0];
       } 
        
       this.ages = loadEntity("ages.csv", Age.getFactory());
       this.deps = loadEntity("departments.csv", Department.getFactory());
       this.empls = loadEntity("employees.csv", Employee.getFactory());
    }
 
    public static void writeCSVFmt(Map<? extends Object, ? extends Object> data, String fileName, 
            String format, String header) throws IOException {
        Path path = Paths.get(workDir, fileName);
        Writer reader = Files.newBufferedWriter(path);
        //data.forEach((k, v) -> System.out.println(k.toString() + "," + v.toString()));
        try (BufferedWriter br = new BufferedWriter(reader)) {
            br.append(header).append("\n");
            data.forEach((k, v) -> { 
                try { 
                    br.append(String.format(format, k, v));
                } catch (IOException ex) {
                    LOG.log(Level.SEVERE, ex.getMessage());
                }
            });
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            throw e;
        }
    }
     
    
    public static void writeCVSInt(Map<Integer, Double> data, String fileName, String header) throws IOException {
        writeCSVFmt(data, "%d,%.2f\n", fileName, header);
    }

    public static void writeCVSStr(Map<String, Double> data, String fileName, String header) throws IOException {
        writeCSVFmt(data, "%s,%.2f\n", fileName, header);
    }
    
}
