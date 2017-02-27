/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PW_Gen;

import java.io.IOException;
import java.security.SecureRandom;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author rafael
 */
public class PasGen extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private char[] usableChar;
    private int length;

    private Stage primaryStage;
    private BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Password Generator");
        showMainView();
    }

    private void showMainView() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view/MainView.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setResizable(false);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public PasGen(String letters, int length) {
        this.usableChar = letters.toCharArray();
        this.length = length;
    }

    public PasGen() {
        this.usableChar = "".toCharArray();
        this.length = 10;
    }

    /**
     * Generates a random password.
     * <p>
     * The password is generated by adding chars at the end of a char[] until
     * the defined length is reached. The chars are chosen from usableChar by
     * SecureRandom with its default configuration.
     *
     * @return the randomly generated password
     */
    public char[] genRandomPassword() {
        char[] password = new char[length];
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            password[i] = usableChar[random.nextInt(usableChar.length)];
        }
        return password;
    }

    /**
     * Sets the length of the generated char[].
     * <p>
     * The length needs to be larger than 0 otherwise an
     * illegalArgumentException is thrown.
     *
     * @param length the length parameter
     */
    public void setCharLength(int length) {
        if (length > 0) {
            this.length = length;
        } else {
            throw new IllegalArgumentException("Length needs to be larger than 0");
        }
    }
    
    public boolean isNotEmpty(){
        return usableChar.length > 0;
    }

    /**
     * Sets the allowed characters from which the password is generated.
     *
     * @param letters the letters from which the password should be generated,
     * not null
     */
    public void setUsableLetter(String letters) {
        if (letters == null) {
            throw new NullPointerException();
        } else if (letters.length() > 0) {
            this.usableChar = letters.toCharArray();
        } else {
            this.usableChar = new char[0];
        }
    }

    /**
     * adds new letters to the already used letters.
     * <p>
     * Letters are only added if they are not already in the list of used
     * letters.
     *
     * @param letters additional letters from which the password should be
     * generated, not null
     */
    public void addUsableLetter(String letters) {
        if (letters == null) {
            throw new NullPointerException();
        } else if (letters.length() > 0) {
            StringBuilder sb = new StringBuilder();
            boolean containsLetter;

            for (char c : letters.toCharArray()) {
                containsLetter = false;
                for (char usedC : usableChar) {
                    if (c == usedC) {
                        containsLetter = true;
                        break;
                    }
                }
                if (!containsLetter) {
                    sb.append(c);
                }
            }

            char[] prevChar = usableChar;
            usableChar = new char[usableChar.length + sb.length()];
            char[] newChar = sb.toString().toCharArray();
            /* prevChar and newChar are copied into the usableChar   */
            System.arraycopy(prevChar, 0, usableChar, 0, prevChar.length);
            System.arraycopy(newChar, 0, usableChar, prevChar.length, newChar.length);
        }
    }
}