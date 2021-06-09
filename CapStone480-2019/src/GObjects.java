import javafx.geometry.Point2D;
import javafx.scene.Node;

public class GObjects {
	private Node object;
	private Point2D v = new Point2D(0,0);
	private boolean alive = true;
	private int value = 10;
	
	public GObjects(Node object) {
		this.object = object;
	}
	
	public double getDirect() {
		return object.getRotate();
	}
	public void directRight() {
		object.setRotate(object.getRotate()+10);
	}
	public void directLeft() {
		object.setRotate(object.getRotate()-10);
	}
	public void update() {
		object.setTranslateX(object.getTranslateX()+ v.getX());
		object.setTranslateY(object.getTranslateY()+ v.getY());
	}
	public void setV(Point2D v) {
		this.v = v;
	}
	public Point2D getV() {
		return v;
	}
	
	public int getTag() {
		return value;
	}
	public void setTag(int value) {
		this.value = value; 
	}
	public Node getObject() {
		return object;
	}
	public void setAlive(boolean alive) {
		this.alive = alive; 
	}
	public boolean isAlive() {
		return alive;
	}
	public boolean isDead() {
		return !alive;
	}
	public boolean iscollide(GObjects other) {
		return getObject().getBoundsInParent().intersects(other.getObject().getBoundsInParent());
	}
	public double getX() {
		return object.getTranslateX();
	}
	public double getY() {
		return object.getTranslateY();
	}
	public void setX(double x) {
		object.setTranslateX(x);
	}
	public void setY(double y) {
		object.setTranslateY(y);
	}
}
