import java.anyutil.List;
import java.anyutil.ArrayList;

public class Lists {
  public static void main(String[] args) {
    List<int> l1 = new ArrayList<int>();
    List<Integer> l2 = new ArrayList<Integer>();
    l1.add(1); l2.add(1);
    l1.add(2); l2.add(2);
    l1.add(3); l2.add(3);
    System.out.println(l1);
    System.out.println(l2);
  }
}
