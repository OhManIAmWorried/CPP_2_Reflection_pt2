package src.Panels;

/**
 * Created by Oly on 12.03.2017.
 */
public class TestClass {
    int anInt;
    double aDouble;
    float aFloat;
    String string;

    public TestClass() {
        anInt = 0;
        aDouble = 0;
        aFloat = 0;
        string = "";
    }

    public TestClass(int anInt, Double aDouble) {
        this.anInt = anInt;
        this.aDouble = aDouble;
        aFloat = 0;
        string = "";
    }

    public TestClass(float aFloat, String string) {
        anInt = 0;
        aDouble = 0;
        this.aFloat = aFloat;
        this.string = string;
    }

    public TestClass(int anInt, double aDouble, float aFloat, String string) {
        this.anInt = anInt;
        this.aDouble = aDouble;
        this.aFloat = aFloat;
        this.string = string;
    }
}
