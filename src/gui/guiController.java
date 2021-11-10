package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import logic.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class guiController {


    @FXML
    public GridPane gridPane;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public Button button;
    @FXML
    public Button buttonClear;
    @FXML
    public Button buttonPlus;
    @FXML
    public Button buttonMinus;
    @FXML
    public ProgressBar loadProgress;
    @FXML
    public TextField startX;
    @FXML
    public TextField startY;
    @FXML
    public TextField endX;
    @FXML
    public TextField endY;
    @FXML
    public TextField shipDeep;
    @FXML
    public TextField dinamicX;
    @FXML
    public TextField dinamicY;
    @FXML
    public TextField dinamicR;
    @FXML
    public TextArea dinamicDescriptionArea;
    @FXML
    public Button dinamicButton;
    @FXML
    public Button buttonTheDeep;


    private int size = 50;
    private String style;
    private int len1, len2;
    private char[][] mass;
    private ArrayList<myPoint> path;

    public void click(ActionEvent actionEvent) throws IOException {
        if(gridPane != null){
            gridPane.getChildren().clear();
        }
        gridPane = new GridPane();
        gridPane.setVisible(true);
        gridPane.setPrefSize(283,185);
        anchorPane.getChildren().add(gridPane);

        Controller.start();
        mass = Controller.getMass();

        len1 = mass.length;
        len2 = mass[0].length;
        generateGridPane( len1, len2);
        initializationGridPanes( mass);

        //loadProgress.setProgress(0);
    }

    public void getPath(ActionEvent actionEvent) throws IOException{
        myPoint start = new myPoint(Integer.parseInt(startX.getText()), Integer.parseInt(startY.getText()));
        myPoint end = new myPoint(Integer.parseInt(endX.getText()), Integer.parseInt(endY.getText()));

        paintMap();

        if(Controller.status(start, end, mass) == 0){
            path = Controller.getPointsPath();
            drawPath(path, "red");
        } else {
            dinamicDescriptionArea.setText("There is no such road");
        }

    }

    private void paintMap(){
        for (int i = 0; i < mass.length; i++){
            for(int j = 0; j < mass[0].length; j++){
                if(mass[i][j] == '0'){
                    gridPane.getChildren().get(i * len2 + j).setStyle("-fx-border-style: solid; -fx-border-width: " + size + "%; -fx-border-color:#3b7a10");
                } else {
                    gridPane.getChildren().get(i * len2 + j).setStyle("-fx-border-style: solid; -fx-border-width: " + size + "%; -fx-border-color:#5abbd1");
                }
            }
        }
    }

    private void drawPath(ArrayList <myPoint> path, String color){
        String style = "-fx-border-style: solid; -fx-border-width: " + size + "%; -fx-border-color:" + color + ";";
        for(myPoint point : path){
            System.out.println(point);
            gridPane.getChildren().get(point.getY() * len2 + point.getX()).setStyle( style );
        }

        /*
        for(myPoint point : Controller.getPoints()){
            System.out.println(point);
            gridPane.getChildren().get(point.getY() * len2 + point.getX()).setStyle( "-fx-border-style: solid; -fx-border-width: " + size + "%; -fx-border-color: purple;" );
        }*/


        gridPane.getChildren().get(path.get(0).getY() * len2 + path.get(0).getX()).setStyle( "-fx-border-style: solid; -fx-border-width: " + size + "%; -fx-border-color: yellow;");
        gridPane.getChildren().get(path.get(path.size()-1).getY() * len2 + path.get(path.size()-1).getX()).setStyle( "-fx-border-style: solid; -fx-border-width: " + size + "%; -fx-border-color: yellow;" );

    }

    public GridPane generateGridPane(int column, int row) {
        GridPane gridPane = new GridPane();
        for (int i = 0; i < column; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(50));
        }
        for (int i = 0; i < row; i++) {
            gridPane.getRowConstraints().add(new RowConstraints(50));
        }
        gridPane.setGridLinesVisible(true);
        return gridPane;
    }

    private Node node_buf;
    private String test_style;
    public void initializationGridPanes(char[][] result) {
        System.out.println("====================================================================\nSTART initializationGridPanes ");
            for (int i = 0; i < result.length; i++) {
                testProcessing(result[i], i);//обрабатываем список пейнов и красим в нужный цвет(извращение)
                System.out.println("i = " + i + " of " + result.length);
            }
    }

    public void enterTheDeep(ActionEvent actionEvent) throws IOException{
        //читаем значение глубины
        //обновляем карту в гуи

        mass = Controller.filter(getLvl());

        for (int i = 0; i < mass.length; i++){
            for(int j = 0; j < mass[0].length; j++){
                gridPane.getChildren().get(i * len2 + j).setStyle("-fx-border-style: solid; -fx-border-width: " + size + "%; -fx-border-color:" + func(mass[i][j]));
            }
        }
    }

    public void dinamicButtonAction(ActionEvent actionEvent) throws IOException{
        int x, y, r;
        x = Integer.parseInt(dinamicX.getText());
        y = Integer.parseInt(dinamicY.getText());
        r = Integer.parseInt(dinamicR.getText());

        if(r == 0){
            dinamicDescriptionArea.setText("Please enter the R of zone");
            return;
        }

        ArrayList<logic.Node> path = Controller.getPathNode();

        if(Controller.checkSaE(path.get(0).getPosition(), path.get(path.size() - 1).getPosition(), new myPoint(x, y), r) && Controller.checkSaE(this.path.get(0), this.path.get(path.size() - 1), new myPoint(x, y), r )){
            System.out.println("The target is not available");
            dinamicDescriptionArea.setText("The target is not available. \n One or two points are in the zone");//цель или точка отправлениея находится в зоне шторма, рекомендуется заглушить двигатели и переждать
            return;
        }

        ArrayList<myPoint> zone = Controller.getDinamicZone(x, y, r);
        paintMap();
        drawPath(this.path, "red");

        String style = "-fx-border-style: solid; -fx-border-width: " + size + "%; -fx-border-color: blue;";
        char[][] dinamicMass = new char[mass.length][];

        for(int i = 0; i < mass.length; i++){
            dinamicMass[i] = mass[i].clone();
        }

        for(myPoint point : zone){

            if(point.getY() > mass.length || point.getY() < 0){
                continue;
            }

            if(point.getX() > mass[0].length || point.getX() < 0){
                continue;
            }

            System.out.println(point);
            if(dinamicMass[point.getY()][point.getX()] != '*'){
                dinamicMass[point.getY()][point.getX()] = 'd';
            }

            if(!path.contains(new logic.Node(point))){
                gridPane.getChildren().get(point.getY() * len2 + point.getX()).setStyle( style );
            }
        }

        if(Controller.checkFindEqPoints(zone, path)){
            dinamicDescriptionArea.setText("Path is optimal");//если препятствие не пересекается с линией маршрута
            System.out.println("Path is optimal");
            return;
        }

        ArrayList<myPoint> pointsDinamicPath = Controller.findPathOutDinamicZone(zone, path, dinamicMass);


        if(pointsDinamicPath != null){
            if(pointsDinamicPath.size() > 3.14 * r){
                dinamicDescriptionArea.setText("The path is not rational.\n It is recommended to wait until\n the event ends");//если обход препятствия не рационален
            } else {
                dinamicDescriptionArea.setText("The path is fine");// если обход препятствия не сильно увеличивает маршрут
            }
            drawPath(Controller.getPointsPath(),  "#FF7200");
        } else {
            dinamicDescriptionArea.setText("There is no such way. \nPlease wait for the event to end.");//если нет возможности построить обходной путь
        }
    }

    public void testProcessing(char [] mass, int i){

        double len = mass.length;
        String newColor;
        test_style = "-fx-border-style: solid; -fx-border-width: " + size + "%; -fx-border-color:";
        for (int j = 0; j < mass.length; j++) {
            newColor = func(mass[j]);//"#" + Integer.toString(buf, 16) + Integer.toString(buf, 16) + Integer.toString(buf, 16);
            node_buf = new Pane();
            node_buf.setStyle(test_style + newColor + ";");
            gridPane.add(node_buf,j+1,i+1);
           // System.out.println();
            System.out.println("j = " + j + " i = " + i + " " +  newColor);
        }
    }

    public void sizePlus(ActionEvent actionEvent) throws IOException {
        String s;
        String buf;
        size +=50;
        //System.out.println(" size " + size);
        for(Node node : gridPane.getChildren()){
            style = node.getStyle();
            buf = style.substring(0, 42) + String.valueOf(size) + "%;" + style.substring(style.indexOf("-fx-border-color"), style.length());
            //System.out.println(buf);
            node.setStyle(buf);
        }
    }

    public void sizeMinus(ActionEvent actionEvent) throws IOException {
        String s = "10";
        String buf;
        if(size >0){
            size -=50;
        }
        //System.out.println(" size " + size);
        for(Node node : gridPane.getChildren()){
            style = node.getStyle();
            buf = style.substring(0, 42) + String.valueOf(size) + "%;" + style.substring(style.indexOf("-fx-border-color"), style.length());
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
        fc.setTitle("Open Document");//Заголовок диалога
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("file PNG", "*.png"));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("file JPG", "*.jpg"));
        List<File> selectedFiles = fc.showOpenMultipleDialog(null);

        if(selectedFiles!= null){
            Controller.setFile(selectedFiles);
        } else {
            System.out.println("file is not..");
        }
    }
}
