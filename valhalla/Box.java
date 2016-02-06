public final class Box<any T> {
  private final T value;

  public Box(T value) {
    this.value = value;
  }

  public String toString() {
    __WhereVal(T) { return "val " + value.toString(); }
    __WhereRef(T) { return "ref " + value; }
  }

  public static void main(String[] args) {
    Box<int> b1 = new Box<int>(1);
    System.out.println(b1);

    Box<Integer> b2 = new Box<Integer>(1);
    System.out.println(b2);

    Box<int> b3 = new Box<>(1);
    Box<Integer> b4 = new Box<>(new Integer(1));
    //Box<Integer> b5 = new Box<>(1);
  }
}
