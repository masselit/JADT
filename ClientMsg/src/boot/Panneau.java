package boot;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
/**
 * 
 * @author Dorian Thivolle
 *
 */
public class Panneau extends JPanel {
	
	private static final long serialVersionUID = 1L;

	public void paintComponent(Graphics g){
		try {
			Image img = ImageIO.read (new File("C:/Users/pc/workspace/ClientMsg/bin/boot/logo.png"));
			g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(), this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	}
}
