package Aplikasi.Controller;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import Aplikasi.Model.Database;
import Aplikasi.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class homePageController implements Initializable {
    private User loggedInUser;

    @FXML
    private Button profileButton;
    @FXML
    private Button DonationButton;
    @FXML
    private Button historyButton;
    @FXML
    private Button btExit;
    @FXML
    private BarChart<String, Integer> bcFood;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load data from XML and populate the chart
        loadDataFromXML();
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = Database.getUserfromDatabase(user.getEmail());
    }

    @FXML
    public void exitApplication() {
        System.exit(0);
    }

    @FXML
    public void Donation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Aplikasi/View/donatePage.fxml"));
        Parent root = loader.load();

        DonateController donate = loader.getController();
        if (loggedInUser != null) {
            User user = Database.getUserfromDatabase(loggedInUser.getEmail());
            donate.setLoggedInUser(user);
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) DonationButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void goToHistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Aplikasi/View/historyPage.fxml"));
        Parent root = loader.load();

        historyPageController history = loader.getController();
        if (loggedInUser != null) {
            User user = Database.getUserfromDatabase(loggedInUser.getEmail());
            history.setLoggedInUser(user);
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) historyButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
public void navigateToProfileScreen(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Aplikasi/View/profile.fxml"));
    Parent root = loader.load();

    ProfileControl profileController = loader.getController();
    if (loggedInUser != null) {
        User user = Database.getUserfromDatabase(loggedInUser.getEmail());
        profileController.setLoggedinUser(user);
    }

    Scene scene = new Scene(root);
    Stage stage = (Stage) profileButton.getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}


  private void loadDataFromXML() {
    try {
        File file = new File("src/Aplikasi/Model/DonateHistory.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);

        doc.getDocumentElement().normalize();
        NodeList donationList = doc.getElementsByTagName("History");

        XYChart.Series<String, Integer> data = new XYChart.Series<>();

        // Create a map to store total donations for each month
        Map<String, Integer> monthlyDonations = new HashMap<>();

        for (int i = 0; i < donationList.getLength(); i++) {
            Element history = (Element) donationList.item(i);
            int amount = Integer.parseInt(history.getElementsByTagName("amountElement").item(0).getTextContent());
            String date = history.getElementsByTagName("donateDate").item(0).getTextContent();

            // Extract the month from the date
            String month = date.substring(0, 7); // Assumes the date is in the format "YYYY-MM-DD"

            // Add the donation amount to the corresponding month in the map
            monthlyDonations.put(month, monthlyDonations.getOrDefault(month, 0) + amount);
        }

        // Calculate the total donation amount for each month and add it to the chart
        int totalDonation = 0;
        for (String month : monthlyDonations.keySet()) {
            int monthDonation = monthlyDonations.get(month);
            totalDonation += monthDonation;
            data.getData().add(new XYChart.Data<>(month, totalDonation));
        }

        bcFood.getData().add(data);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
