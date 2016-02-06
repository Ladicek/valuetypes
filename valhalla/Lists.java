import java.anyutil.List;
import java.anyutil.ArrayList;
import java.anyutil.Arrays;

public class Lists {
  public static void main(String[] args) {
    List<int> l1 = new ArrayList<int>();
    List<Integer> l2 = new ArrayList<Integer>();
    l1.add(1); l2.add(1);
    l1.add(2); l2.add(2);
    l1.add(3); l2.add(3);

    System.out.println(l1);
    printClass(l1.getClass());

    System.out.println();

    System.out.println(l2);
    printClass(l2.getClass());
  }

  private static void printClass(Class clazz) {
    Class c = clazz;
    while (c != null) {
      System.out.println(c);
      printInterfaces(c, 1);
      c = c.getSuperclass();
    }
  }

  private static void printInterfaces(Class clazz, int nestLevel) {
    StringBuilder b = new StringBuilder();
    for (int i = 0; i < nestLevel; i++) {
      b.append("  ");
    }
    String indent = b.toString();

    for (Class iface : clazz.getInterfaces()) {
      System.out.println(indent + iface);
      printInterfaces(iface, nestLevel + 1);
    }
  }
}
