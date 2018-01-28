package testMessagerie.model;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import messagerie.model.Message;

public class TestMessage {
	
	@Test
	public void testoperationSpec() throws Exception {
		Message msg = new Message();
		assertEquals(false,msg.operationSpec("<lolo></lolo>"));
		assertEquals(true,msg.operationSpec("<Message>    </Message>"));
		assertEquals(false,msg.operationSpec("<model.Message>  </model.Message>"));
	}
	
	@Test
	public void testCreerGetSet()throws Exception{
		Message msg = new Message();
		msg.setDestinataire("valeurDestinataire");
		msg.setExpediteur("valeurExpediteur");
		List<Object> testList = new ArrayList<Object>();
		testList.add(new String());
		testList.add(new Object());
		msg.setContenus(testList);
		assertEquals("valeurDestinataire",msg.getDestinataire());
		assertEquals("valeurExpediteur",msg.getExpediteur());
		assertEquals(2,msg.getContenus().size());
		assertEquals(new Message().getClass(),msg.creer(msg.getValeurs(), msg.getDestinataire(), msg.getExpediteur()).getClass());
	}
	
	@Test
	public void testTarget()throws Exception{
		Message msg = new Message();
		assertEquals(new Message().getClass(),msg.target().getClass());
	}
}
