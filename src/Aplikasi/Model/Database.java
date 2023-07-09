package Aplikasi.Model;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import Aplikasi.Controller.ProfileControl;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Database {
    public static User getUserfromDatabase(String email){
        try {
            File xmlFile = new File("src/Aplikasi/Model/Users.xml");
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            NodeList nodelist = doc.getElementsByTagName("user");
            for(int i = 0; i < nodelist.getLength(); i++){
                Node node = nodelist.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                   String storedEmail = element.getElementsByTagName("email").item(0).getTextContent();

                   if(storedEmail.equals(email)){
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String Email = element.getElementsByTagName("email").item(0).getTextContent();
                    String phoneNumber = element.getElementsByTagName("phoneNumber").item(0).getTextContent();
                    String password = element.getElementsByTagName("password").item(0).getTextContent();
                    String address = element.getElementsByTagName("address").item(0).getTextContent();
                    return new User(name, Email, password, phoneNumber, address);
                   }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
