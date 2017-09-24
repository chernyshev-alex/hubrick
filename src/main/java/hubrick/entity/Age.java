package hubrick.entity;

import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

public class Age extends Object {

    public final String    name;
    public final Integer   age;
   
    public Age(String name, Integer age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
    
    @Override
    public String toString() {
        return new StringJoiner(":", "[", "]").add(getClass().getTypeName())
                .add(name)
                .add(age.toString()).toString();
    }

    public static final Function<List<String>, Age> getFactory() {
        return ls -> new Age(ls.get(0).trim().toLowerCase(), 
                Integer.parseInt(ls.get(1)));
    }
    
}
