package cz.ladicek.valuetypes.complex;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.util.VMSupport;

public class ClassSize {
    public static void main(String[] args) {
        System.out.println(VMSupport.vmDetails());
        System.out.println(ClassLayout.parseClass(Complex.class).toPrintable());
        System.out.println(ClassLayout.parseClass(double[].class).toPrintable());
    }
}
