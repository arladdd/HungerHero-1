package Aplikasi.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Aplikasi.Model.User;

import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;

public class signUpController implements Initializable {

    @FXML
    private TextField tfName, tfEmail, tfPhoneNumber, tfAddress;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Button regButton;

    public void register(ActionEvent Event) throws IOException {
        String name = tfName.getText();
        String password = pfPassword.getText();
        String phoneNumber = tfPhoneNumber.getText();
        String email = tfEmail.getText();
        String address = tfAddress.getText();

        User user = new User(name, email, password, phoneNumber, address);

        storeUserInXML(user);

        Parent root = FXMLLoader.load(getClass().getResource("/Aplikasi/View/loginPage.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) regButton.getScene().getWindow();
        stage.setScene(scene);

    }

    private void storeUserInXML(User user) {
        try {
            File xmlFile = new File("src/Aplikasi/Model/Users.xml");
            File parentDir = xmlFile.getParentFile();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
            } else {
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("users");
                doc.appendChild(rootElement);
            }

            Element userElement = doc.createElement("user");

            Element nameElement = doc.createElement("name");
            nameElement.setTextContent(user.getName());
            userElement.appendChild(nameElement);

            Element emailElement = doc.createElement("email");
            emailElement.setTextContent(user.getEmail());
            userElement.appendChild(emailElement);

            Element phoneNumberElement = doc.createElement("phoneNumber");
            phoneNumberElement.setTextContent(user.getPhoneNumber());
            userElement.appendChild(phoneNumberElement);

            Element passwordElement = doc.createElement("password");
            passwordElement.setTextContent(user.getPassword());
            userElement.appendChild(passwordElement);

            Element addressElement = doc.createElement("address");
            addressElement.setTextContent(user.getAddress());
            userElement.appendChild(addressElement);

            doc.getDocumentElement().appendChild(userElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println("User registered and stored in XML successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

}
