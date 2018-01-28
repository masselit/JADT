package messagerie.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author Thibaut MASSELIN
 * @date 12/11/2016
 */

@XStreamAlias("Text")
public class Texts {
	@XStreamAlias("contenu")
	private String contenu = "";
	
	public Texts(String contenu){
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
