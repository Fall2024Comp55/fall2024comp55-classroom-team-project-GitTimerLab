import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import java.util.ArrayList;
import java.util.List;

public class Boosts {
	private List<GImage> ammo;
	private List<GImage> medKit;
	private List<GImage> mistakes;
	private Map map;
	private int medBoost = 50;
	private GImage index;
	private int medKitSpawnInterval = 20; 
	private int medKitTimer = 0; 
	private GameApp gameApp;

	public Boosts (GameApp ga, boolean isAmmo) {
		this.gameApp = ga;
		if (isAmmo) {
			this.ammo = new ArrayList<>();
		} else {
			this.medKit = new ArrayList<>();
		}
	}
	public Boosts (GameApp ga, boolean isAmmo, Map m) {
		this.gameApp = ga;
		this.map = m;
		if (isAmmo) {
			this.ammo = new ArrayList<>();
		} else {
			this.medKit = new ArrayList<>();
		}
	}
	
	public void createMedKit(double x, double y, double s) {
		GImage medkit = new GImage("MedKit_Trans.png", x, y);
		medkit.scale(s);
		if (ifInsideWall(x,y,medkit)) {
			medKit.add(medkit);
			gameApp.add(medkit);
		}
	}
	public void createAmmo(double x, double y, double s) {
		GImage a = new GImage("Bullet.png", x, y);
		a.scale(s);
		if (ifInsideWall(x,y,a)) {
			ammo.add(a);
			gameApp.add(a);
		}
	}
	public List<GImage> getAmmo() {
		return ammo;
	}
	public List<GImage> getMedKit() {
		return medKit;
	}
	public boolean ammoOverlapping(Player player) {
		for (GImage a: ammo) {
			if (player.getCenterX() > a.getX() && player.getCenterX() < a.getX() + a.getWidth() && player.getCenterY() > a.getY() && player.getCenterY() < a.getY() + a.getHeight()) {
				return true;
			}
		}
		return false;
	}
	public boolean medKitOverlapping(Player player) {
		for (GImage m: medKit) {
			if (player.getCenterX() > m.getX() && player.getCenterX() < m.getX() + m.getWidth() && player.getCenterY() > m.getY() && player.getCenterY() < m.getY() + m.getHeight()) {
				index = m;
				return true;
			}
		}
		return false;
	}
	public void removeMedKit() {
		medKit.remove(index);
		gameApp.remove(index);
	}
	public void addHealth(Player player) { 
		player.updateHealth(0, medBoost);
	}
	public boolean ifInsideWall(double x, double y, GImage m) {
		int tileTopLeftX = (int) (x / Map.getTileSize());
        int tileTopLeftY = (int) (y / Map.getTileSize());

        int tileTopRightX = (int) ((x + m.getWidth() - 1) / Map.getTileSize());
        int tileTopRightY = tileTopLeftY;

        int tileBottomLeftX = tileTopLeftX;
        int tileBottomLeftY = (int) ((y + m.getHeight() - 1) / Map.getTileSize());

        int tileBottomRightX = tileTopRightX;
        int tileBottomRightY = tileBottomLeftY;
        
        return checkingWall(tileTopLeftX,tileTopLeftY) && 
        		checkingWall(tileTopRightX,tileTopRightY) &&
        		checkingWall(tileBottomLeftX,tileBottomLeftY) &&
        		checkingWall(tileBottomRightX,tileBottomRightY);
	}
	public void erasingMistake() {
		for (GImage m: mistakes) {
			medKit.remove(m);
			gameApp.remove(m);
		}
	}
	private boolean checkingWall(int x, int y) {
		if (x < 0 || y < 0 || x >= map.getCols() || y >= map.getRows()) {
			return false;
		}
		return !map.isWall(x, y);
	}
	public void hideMedKits() {
		for (GImage m: medKit) {
			gameApp.remove(m);
		}
	}
	public void showMedKits() {
		for (GImage m: medKit) {
			gameApp.add(m);
		}
	}
	public void update() {
	    medKitTimer++; // Increment timer with each game tick
	    
	    // Check if it's time to spawn a new medkit
	    if (medKitTimer >= medKitSpawnInterval) {
	        getMedKit(); // Call the method to spawn a medkit
	        medKitTimer = 0; // Reset timer
	    }
	}


}
	
	

