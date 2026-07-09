package practice.main_module;

// Pattern matching
public class SwitchPatternDemo {

    record Box(int i) {}

    static void main() {
        System.out.println(getInputType(5));
        System.out.println(getInputType(5.1));
        System.out.println(getInputType("5"));
        System.out.println(getInputType(new Box(10)));

    }

    static String getInputType(Object obj) {
        return switch (obj) {
            case int i    -> "It's an int: " + i;
            case double d -> "It's a double: " + d;
            case String s -> "It's a string: " + s;
            case Box(int i) -> "Box of int: " + i; //De-structures to find the contained value
            default       -> "Unknown type";
        };
    }

}