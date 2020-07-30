package cat.yoink.zodiac.event.events;

import cat.yoink.zodiac.event.manager.Event;
//by floppa
public class MotionEvent extends Event {
   private double x;
   private double y;
   private double z;

   public MotionEvent(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public double getX() {
      return this.x;
   }

   public void setX(double x) {
      this.x = x;
   }

   public double getY() {
      return this.y;
   }

   public void setY(double y) {
      this.y = y;
   }

   public double getZ() {
      return this.z;
   }

   public void setZ(double z) {
      this.z = z;
   }
}
