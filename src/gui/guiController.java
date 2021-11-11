package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import logic.Controller;
import logic.ImageEditor;
import logic.ImageReader;

import javax.imageio.ImageIO;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class guiController {
    @FXML
    GridPane gridPane;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextField lw;
    @FXML
    public TextField lh;
    @FXML
    public Button buttonCreateAtlas;
    @FXML
    public Button buttonClear;
    @FXML
    public Button buttonPlus;
    @FXML
    public Button buttonMinus;
    @FXML
    public ImageView iv_1;
    @FXML

    private int size = 50;
    private String style;


    @FXML
    private void initialize() {

        Controller.createEditor();
    }


    public void CreateAtlas(ActionEvent actionEvent){

        if(isNotEmpty()){
            int weight = Integer.parseInt(lw.getText());
            int height = Integer.parseInt(lh.getText());
            Controller.doProcessImage( new Point(weight, height));

            iv_1.setImage(Controller.getImage());
        }
    }

    public void sizePlus(ActionEvent actionEvent){
        String s;
        String buf;
        size +=50;
        //System.out.println(" size " + size);
        for(Node node : gridPane.getChildren()){
            style = node.getStyle();
            buf = style.substring(0, 42) + String.valueOf(size) + "%;" + style.substring(style.indexOf("-fx-border-color"));
            //System.out.println(buf);
            node.setStyle(buf);
        }
    }

    public void sizeMinus(ActionEvent actionEvent){
        String s = "10";
        String buf;
        if(size >0){
            size -=50;
        }
        //System.out.println(" size " + size);
        for(Node node : gridPane.getChildren()){
            style = node.getStyle();
            buf = style.substring(0, 42) + String.valueOf(size) + "%;" + style.substring(style.indexOf("-fx-border-color"));
            //System.out.println(buf);
            node.setStyle(buf);
        }
    }

    public void clearClick(ActionEvent actionEvent) throws IOException {
        gridPane.getChildren().clear();
        //System.out.println(gridPane.getChildren().size());
            /*for(logic.Node node : gridPane.getChildren()){
                node.setStyle("-fx-border-style: solid; -fx-border-width: " + size + "%; -fx-border-color:#FFFFFF;");
            }*/
    }

    public void openNewFile(ActionEvent actionEvent){
        FileChooser fc = new FileChooser();
        fc.setTitle("Open Document");
        fc.setInitialDirectory(new File("I:\\ForTheAtlasTexture\\02"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("file PNG", "*.png"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("file JPG", "*.jpg"));
        List<File> selectedFiles = fc.showOpenMultipleDialog(null);

        if(selectedFiles!= null){
            Controller.openFiles(selectedFiles);
        } else {
            System.out.println("file is not..");
        }
    }

    public void saveNewFile(ActionEvent actionEvent){
        FileChooser fc = new FileChooser();
        fc.setTitle("Save Document");
        fc.setInitialDirectory(new File("I:\\ForTheAtlasTexture\\result"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("file PNG", "*.png"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("file JPG", "*.jpg"));
        File selectedFiles = fc.showSaveDialog(null);

        if(selectedFiles!= null){
            try{
                saveFile(selectedFiles);
            } catch (Exception e){

                System.err.println("Images is null");
            }
        } else {
            System.out.println("file is not..");
        }
    }

    private void saveFile(File files) throws NullPointerException{
        Controller.saveFile(files, "png");
        System.out.println(files.getPath());
    }


    public void updateDemo(MouseEvent inputMethodEvent) {
//        if(isNotEmpty()){
//            int weight = Integer.parseInt(lw.getText());
//            int height = Integer.parseInt(lh.getText());
//            Controller.doDemoProcessImage( new Point(weight, height));
//        }
    }

    private boolean isNotEmpty(){
        return !(lh.getText().equals("") || lw.getText().equals(""));
    }

//    public void lh_event12(MouseEvent mouseEvent) {
//        if(isNotEmpty()){
//            System.out.println("bugaga2");
//
//        } else {
//            System.out.println("bugaga");
//
//        }
//    }
}
