import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class Program {
  public static void main(String[] args) throws JAXBException, FileNotFoundException, XPathExpressionException {
    File file = new File("message.xml");
    JAXBContext jaxbContext = JAXBContext.newInstance(Message.class);
    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
//    Message message = (Message) jaxbUnmarshaller.unmarshal(file);

    XPath xPath = XPathFactory.newInstance().newXPath();
    //String expression = "/message/attachments/files[last() - 1]";
    String expression = "/message/attachments/files[@type = 'image']";
    InputSource source = new InputSource(new FileInputStream(file));
    NodeList imageNodes = (NodeList)xPath.evaluate(expression, source, XPathConstants.NODESET);

    for (int i = 0; i < imageNodes.getLength(); i++) {
      String nodeText = imageNodes.item(i).getTextContent();
      System.out.println(nodeText);
    }

    //System.out.println(firstFile);
  }

  @XmlRootElement
  public static class Message {
    public String title;
    public String body;

    public Attachment attachments;
  }

  public static class Attachment {
    public List<String> files;
  }
}
