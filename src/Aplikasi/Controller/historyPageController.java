package Aplikasi.Controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import Aplikasi.Model.Database;
import Aplikasi.Model.Donate;
import Aplikasi.Model.MyData;
import Aplikasi.Model.User;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

public class historyPageController implements Initializable {

    @FXML
    private Button DonationButton;

    @FXML
    private TableColumn<MyData, String> amountColumn;

    @FXML
    private Button btExit;

    @FXML
    private Button btHome;

    @FXML
    private TableColumn<MyData, String> dateColumn;

    @FXML
    private TableColumn<MyData, String> itemColumn;

    @FXML
    private Button profileButton;

    @FXML
    private TableView<MyData> table;

    @FXML
    private TableColumn<MyData, String> unitColumn;

    @FXML
    private Button btEdit;

    private User loggedInUser;

    public void setLoggedInUser(User user) {
        this.loggedInUser = Database.getUserfromDatabase(user.getEmail());
    }

    @FXML
    public void deleteData(ActionEvent event) {
        MyData selectedData = table.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            // Remove the data from the table
            table.getItems().remove(selectedData);

            // Remove the data from the XML file
            removeDataFromXML(selectedData);
        }
    }

    @FXML
    public void clearData(ActionEvent event) {
        // Clear the table
        table.getItems().clear();

        // Clear the XML file
        clearXMLData();
    }

    private void clearXMLData() {
        try {
            File inputFile = new File(System.getProperty("user.dir") + "/src/Aplikasi/Model/DonateHistory.xml");

            // Delete the original file
            boolean deleted = inputFile.delete();
            if (!deleted) {
                throw new IOException("Failed to delete the XML file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeDataFromXML(MyData data) {
        try {
            File inputFile = new File(System.getProperty("user.dir") + "/src/Aplikasi/Model/DonateHistory.xml");
            File tempFile = new File(System.getProperty("user.dir") + "/src/Aplikasi/Model/TempDonateHistory.xml");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);

            NodeList historyList = doc.getElementsByTagName("History");

            for (int i = 0; i < historyList.getLength(); i++) {
                Element historyElement = (Element) historyList.item(i);

                Node foodItemNode = historyElement.getElementsByTagName("foodItem").item(0);
                Node dateNode = historyElement.getElementsByTagName("donateDate").item(0);
                Node unitNode = historyElement.getElementsByTagName("location").item(0);
                Node amountNode = historyElement.getElementsByTagName("amount").item(0);

                String foodItem = foodItemNode != null ? foodItemNode.getTextContent() : null;
                String date = dateNode != null ? dateNode.getTextContent() : null;
                String unit = unitNode != null ? unitNode.getTextContent() : null;
                int amount = amountNode != null ? Integer.parseInt(amountNode.getTextContent()) : -1;

                if (foodItem != null && date != null && unit != null && amount != -1) {
                    if (data.getFoodItem().equals(foodItem) && data.getDate().equals(date)
                            && data.getUnit().equals(unit) && data.getAmount().equals(amount)) {
                        // Remove the matching <History> element
                        historyElement.getParentNode().removeChild(historyElement);
                        break;
                    }
                }
            }

            // Save the modified document to the temporary file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(doc), new StreamResult(tempFile));

            // Delete the original file
            boolean deleted = inputFile.delete();
            if (!deleted) {
                throw new IOException("Failed to delete the XML file.");
            }

            // Rename the temporary file to the original file
            boolean renamed = tempFile.renameTo(inputFile);
            if (!renamed) {
                throw new IOException("Failed to rename the temporary file.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Donation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Aplikasi/View/donatePage.fxml"));
        Parent root = loader.load();
        DonateController donateController = loader.getController();
        if (loggedInUser != null) {
            String loggedInEmail = loggedInUser.getEmail();
            User user = Database.getUserfromDatabase(loggedInUser.getEmail());
            donateController.setLoggedInUser(user);
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) DonationButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void exitApplication() {
        System.exit(0);
    }

    @FXML
    public void goToHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Aplikasi/View/homePage.fxml"));
        Parent root = loader.load();
        homePageController homeController = loader.getController();
        if (loggedInUser != null) {
            String loggedInEmail = loggedInUser.getEmail();
            User user = Database.getUserfromDatabase(loggedInUser.getEmail());
            homeController.setLoggedInUser(user);
        }

        Scene scene = new Scene(root);
        Stage stage = (Stage) DonationButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void navigateToProfileScreen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Aplikasi/View/profile.fxml"));
        Parent root = loader.load();

        ProfileControl profileController = loader.getController();
        if (loggedInUser != null) {
            String loggedInEmail = loggedInUser.getEmail();
            User user = Database.getUserfromDatabase(loggedInUser.getEmail());
            profileController.setLoggedinUser(user);
        }

        Stage stage = (Stage) profileButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void loadXMLData() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("src/Aplikasi/Model/DonateHistory.xml");

            // Get the list of <History> elements from the XML
            NodeList historyList = doc.getElementsByTagName("History");

            // Create a list to store MyData objects
            ObservableList<MyData> dataList = FXCollections.observableArrayList();

            for (int i = 0; i < historyList.getLength(); i++) {
                Element historyElement = (Element) historyList.item(i);

                // Extract data from XML elements
                String foodItem = historyElement.getElementsByTagName("foodItem").item(0).getTextContent();
                String date = historyElement.getElementsByTagName("donateDate").item(0).getTextContent();
                String unit = historyElement.getElementsByTagName("location").item(0).getTextContent();
                String amount = historyElement.getElementsByTagName("amountElement").item(0).getTextContent();

                // Create a new MyData object and add it to the list
                MyData data = new MyData(foodItem, date, unit, amount);
                dataList.add(data);
            }

            // Set the table data to the populated list
            table.setItems(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    itemColumn.setCellValueFactory(cellData -> cellData.getValue().foodItemProperty());
    dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
    unitColumn.setCellValueFactory(cellData -> cellData.getValue().unitProperty());
    amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty());

    // Populate the table with data from the XML file
    loadXMLData();
}

    @FXML
public void editData(ActionEvent event) {
    MyData selectedData = table.getSelectionModel().getSelectedItem();
    if (selectedData != null) {
        // Open a dialog or prompt for the user to input the edited food item
        TextInputDialog dialog = new TextInputDialog(selectedData.getFoodItem());
        dialog.setTitle("Edit Food Item");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the edited food item:");
        Optional<String> result = dialog.showAndWait();

        // If the user entered a new food item, update the data in the table and XML file
        result.ifPresent(newFoodItem -> {
            selectedData.setFoodItem(newFoodItem);
            updateDataInTable(selectedData);
            updateDataInXML(selectedData);
        });
    }
}

private void updateDataInTable(MyData data) {
    // Find the index of the selected item in the table and replace it with the updated data
    int selectedIndex = table.getSelectionModel().getSelectedIndex();
    table.getItems().set(selectedIndex, data);
}

private void updateDataInXML(MyData selectedData) {
    try {
        File file = new File("src/Aplikasi/Model/DonateHistory.xml");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);
        doc.getDocumentElement().normalize();

        Element rootElement = doc.getDocumentElement();
        NodeList historyList = rootElement.getElementsByTagName("History");

        // Find the target entry based on its ID
        for (int i = 0; i < historyList.getLength(); i++) {
            Element historyElement = (Element) historyList.item(i);
            int id = Integer.parseInt(historyElement.getAttribute("id"));

            if (id == selectedData.getId()) {
                // Update the existing entry
                Element foodItemElement = (Element) historyElement.getElementsByTagName("foodItem").item(0);
                Element amountElement = (Element) historyElement.getElementsByTagName("amountElement").item(0);
                Element donateDateElement = (Element) historyElement.getElementsByTagName("donateDate").item(0);
                Element pickUpElement = (Element) historyElement.getElementsByTagName("location").item(0);

                foodItemElement.setTextContent(selectedData.getFoodItem());
                amountElement.setTextContent(selectedData.getAmount());
                donateDateElement.setTextContent(selectedData.getDate());
                pickUpElement.setTextContent(selectedData.getUnit());

                // Save the updated XML back to the file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty("indent", "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                DOMSource source = new DOMSource(doc);
                StreamResult streamResult = new StreamResult(file);
                transformer.transform(source, streamResult);

                System.out.println("Data Updated in XML!");
                break;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}