package messagerie.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Thibaut MASSELIN
 * @date 12/11/2016
 */

@XStreamAlias("Image")
public class Images {
	@XStreamAlias("contenu")
	private String contenu = "";
	
	public Images(String contenu){
		this.contenu = contenu;
	}


	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	
	@Override
	public String toString() {
		return contenu;
	}
}
