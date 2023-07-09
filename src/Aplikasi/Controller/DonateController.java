package Aplikasi.Controller;

import Aplikasi.Model.Database;
import Aplikasi.Model.Donate;
import Aplikasi.Model.Donation;
import Aplikasi.Model.User;
import Aplikasi.Model.DonateItem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DonateController implements Initializable {
    private User loggedinUser;

    @FXML
    private ChoiceBox<String> cbPortion;

    @FXML
    private TextField tfDonateAmount;

    @FXML
    private TextField FoodItem;

    @FXML
    private TableColumn<Donation, String> tFoodItem;

    @FXML
    private Button btHome, btProfile, btHistory, profileButton;

    @FXML
    private Button btnDonate;

    @FXML
    private DatePicker calDonateDate;

    @FXML
    private TextField tfPickUp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btExit.setOnAction(event -> exitApplication());
        // Add options to the ChoiceBox
        cbPortion.getItems().addAll("KG", "PCS");

        // Set an initial value for the ChoiceBox
        cbPortion.setValue("KG");
    }

    @FXML
    public void donate() {
        String foodItem = FoodItem.getText();
        String foodAmount = tfDonateAmount.getText();
        String pickUp = tfPickUp.getText();
        LocalDate donateDate = calDonateDate.getValue();
        String portion = cbPortion.getValue(); 

        Donate donate = new Donate(foodItem, donateDate.toString(), foodAmount, pickUp, portion);

        storeDonateHistory(donate);

        // Clear the input fields
        FoodItem.clear();
        tfDonateAmount.clear();
        tfPickUp.clear();
        calDonateDate.setValue(LocalDate.now());
    }

    @FXML
    private Button btExit;

    @FXML
    public void exitApplication() {
        System.exit(0);
    }

    

   private void storeDonateHistory(Donate donate) {
    try {
        File file = new File("src/Aplikasi/Model/DonateHistory.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc;

        if (file.exists()) {
            doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
        } else {
            doc = dBuilder.newDocument();
            Element rootElement = doc.createElement("donateHistory");
            doc.appendChild(rootElement);
        }

        Element rootElement = doc.getDocumentElement();

        NodeList historyList = rootElement.getElementsByTagName("History");
        int id = historyList.getLength(); // Use the size of the existing history as the ID

        // Create a new donate entry
        Element historyElement = doc.createElement("History");
        historyElement.setAttribute("id", String.valueOf(id));

        Element foodItemElement = doc.createElement("foodItem");
        foodItemElement.setTextContent(donate.getFoodItem());
        historyElement.appendChild(foodItemElement);

        Element amountElement = doc.createElement("amountElement");
        amountElement.setTextContent(donate.getAmount());
        historyElement.appendChild(amountElement);

        Element donateDateElement = doc.createElement("donateDate");
        donateDateElement.setTextContent(donate.getDate());
        historyElement.appendChild(donateDateElement);

        Element pickUpElement = doc.createElement("location");
        pickUpElement.setTextContent(donate.getPickUp());
        historyElement.appendChild(pickUpElement);

        // Append the new entry to the root element
        rootElement.appendChild(historyElement);

        // Save the updated XML back to the file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty("indent", "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(source, streamResult);

        System.out.println("Donate Added!!!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    @FXML
    public void navigatetoprofileScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Aplikasi/View/profile.fxml"));
        Parent root = loader.load();

        ProfileControl profileController = loader.getController();
        if (loggedinUser != null) {
            String loggedInEmail = loggedinUser.getEmail();
            User user = Database.getUserfromDatabase(loggedinUser.getEmail());
            profileController.setLoggedinUser(user);
        }

        Stage stage = (Stage) profileButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Aplikasi/View/homePage.fxml"));
        Parent root = loader.load();

        homePageController homeController = loader.getController();
        if (loggedinUser != null) {
            String loggedInEmail = loggedinUser.getEmail();
            User user = Database.getUserfromDatabase(loggedinUser.getEmail());
            homeController.setLoggedInUser(user);
        }

        Stage stage = (Stage) btHome.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void setLoggedInUser(User user) {
        this.loggedinUser = Database.getUserfromDatabase(user.getEmail());
    }

    @FXML
    public void goToHistory() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Aplikasi/View/historyPage.fxml"));
        Parent root = loader.load();

        historyPageController historyController = loader.getController();
        if (loggedinUser != null) {
            String loggedInEmail = loggedinUser.getEmail();
            User user = Database.getUserfromDatabase(loggedinUser.getEmail());
            historyController.setLoggedInUser(user);
        }

        Stage stage = (Stage) btHome.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
