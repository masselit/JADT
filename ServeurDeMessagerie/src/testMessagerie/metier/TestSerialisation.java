package testMessagerie.metier;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import messagerie.metier.Serialisation;
import messagerie.model.Images;
import messagerie.model.Message;
import messagerie.model.Son;
import messagerie.model.Texts;

public class TestSerialisation {
	private String xml;
	private Message msg;
	private Serialisation<Message> serialisation;
	
	@Before
	public void inisialize(){
		XStream xstream = new XStream(new DomDriver());
		
		msg = new Message();
		msg.setDestinataire("Jean");
		msg.setExpediteur("Thibaut");
		Object messageText = new Texts("la vie est belle");
		msg.addContenus(messageText);
		Object messageImage = new Images("image 547855");
		msg.addContenus(messageImage);
		Object messageSon = new Son("son 154257557");			
		msg.addContenus(messageSon);
		
		this.xml = xstream.toXML(msg);
	}
	
	@Test
	public void testDeserialisation() throws Exception{
		serialisation = new Serialisation<Message>();
		serialisation.deserialisation(xml);
		assertEquals(msg.getDestinataire(),serialisation.getT().getDestinataire());
	}
	@Test
	public void testGetT()throws Exception{
		serialisation = new Serialisation<Message>();
		serialisation.deserialisation(xml);
		assertEquals(true,Message.class.isAssignableFrom(serialisation.getT().getClass()));
	}
	
	@Test
	public void testCreer() throws Exception{
		serialisation = new Serialisation<Message>();
		String str = serialisation.creer(msg);
		serialisation.deserialisation(str);
		assertEquals(msg.getDestinataire(),serialisation.getT().getDestinataire());
	}
}
