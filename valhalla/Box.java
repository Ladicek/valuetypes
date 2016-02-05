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
    System.out.println(new Box<int>(1));
    System.out.println(new Box<Integer>(1));
  }
}
