package Aplikasi.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Aplikasi.Model.Database;
import Aplikasi.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ProfileControl implements Initializable {

    private User loggedinUser;

    Image homeImage = new Image("/Aplikasi/image/home-3.png");
    ImageView imageView1 = new ImageView(homeImage);
    Image profileImage = new Image("/Aplikasi/image/profile.png");
    ImageView imageView2 = new ImageView(profileImage);
    Image historyImage = new Image("/Aplikasi/image/history.png");
    ImageView imageView3 = new ImageView(historyImage);
    Image logoutImage = new Image("/Aplikasi/image/logout.png");
    ImageView imageView4 = new ImageView(logoutImage);
    Image donateImage = new Image("/Aplikasi/image/donate.png");
    ImageView imageView5 = new ImageView(donateImage);

    @FXML
    Label lbName, lbEmail, lbPhoneNumber, lbAdress;

    @FXML
    private Button btHome, btDonate, btHistory, btExit;

    @FXML
    public void exitApplication() {
        System.exit(0);
    }

    public void setLoggedinUser(User user) {
        this.loggedinUser = user;
    if (lbName != null) {
        lbName.setText(user.getName());
    }
    if (lbEmail != null) {
        lbEmail.setText(user.getEmail());
    }
    if (lbPhoneNumber != null) {
        lbPhoneNumber.setText(user.getPhoneNumber());
    }
    if (lbAdress != null) {
        lbAdress.setText(user.getAddress());
    }
}
    

   public void goToHome() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Aplikasi/View/homePage.fxml"));
    Parent root = loader.load();
    homePageController homeController = loader.getController();
    if (loggedinUser != null) {
        String loggedInEmail = loggedinUser.getEmail();
        User user = Database.getUserfromDatabase(loggedInEmail);
        homeController.setLoggedInUser(user);
    }
    Scene scene = new Scene(root);
    Stage stage = (Stage) btHome.getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}

@FXML
public void gotoDonate() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Aplikasi/View/donatePage.fxml"));
    Parent root = loader.load();
    DonateController donateController = loader.getController();
    if (loggedinUser != null) {
        String loggedInEmail = loggedinUser.getEmail();
        User user = Database.getUserfromDatabase(loggedInEmail);
        donateController.setLoggedInUser(user);
    }
    Scene scene = new Scene(root);
    Stage stage = (Stage) btDonate.getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}

@FXML
public void goToHistory() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Aplikasi/View/historyPage.fxml"));
    Parent root = loader.load();
    historyPageController historyController = loader.getController();
    if (loggedinUser != null) {
        User user = Database.getUserfromDatabase(loggedinUser.getEmail());
        historyController.setLoggedInUser(user);
    }
    Scene scene = new Scene(root);
    Stage stage = (Stage) btHistory.getScene().getWindow();
    stage.setScene(scene);
    stage.show();
}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
