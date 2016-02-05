public final __ByValue class Complex {
  private final double re, im;

  public Complex(double re, double im) {
    this.re = re;
    this.im = im;
  }

  public String toString() {
    return re + " + " + im + "i";
  }

  public static void main(String[] args) {
    Complex c = __Make Complex(0, 0);
    System.out.println(c);
  }
}
