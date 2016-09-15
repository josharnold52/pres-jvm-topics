package jvminternals.escape;

public class Rectangle {


   public Rectangle(double length, double width) {
      super();
      this.length = length;
      this.width = width;
   }

   public synchronized void setDimensions(double length, double width) {
      this.length = length;
      this.width = width;
   }

   public synchronized double getArea() {
      return this.length * this.width;
   }

   public synchronized double getDiagonalLength() {
      return Math.sqrt(this.length * this.length + this.width * this.width);
   }


   private double length;

   private double width;
}
