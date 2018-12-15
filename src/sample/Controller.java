package sample;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton byNumSearch;

    @FXML
    private ToggleGroup group;

    @FXML
    private RadioButton byName;

    @FXML
    private RadioButton byFreeNum;

    @FXML
    private Button referensButton;

    @FXML
    private Button addButton;

    @FXML
    private Button reassignButtton;

    @FXML
    private TableView<Notation> table;

    @FXML
    private TableColumn<Notation, String> numC;

    @FXML
    private TableColumn<Notation, String> ownerC;

    @FXML
    private TableColumn<SubscriberAddress, String> addresC;

    @FXML
    private TableColumn<?, ?> operatorC;

    @FXML
    void add(ActionEvent event) {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setX(400);
        dialog.setTitle("Добавление");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(20, 10, 10, 10));

        ButtonType addButtonType = new ButtonType("Добавить", ButtonBar.ButtonData.OK_DONE);
        ButtonType closeButtonType = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, closeButtonType);

        TextField numberTextField = new TextField();
        numberTextField.setPromptText("Номер");
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Имя");
        TextField cityTextField = new TextField();
        cityTextField.setPromptText("Город");
        TextField streetTextField = new TextField();
        streetTextField.setPromptText("Улица");
        TextField homeTextField = new TextField();
        homeTextField.setPromptText("Дом");
        Label label = new Label("Заполните форму ниже");

        grid.add(label, 1, 0);
        grid.add(numberTextField, 1, 1);
        grid.add(nameTextField, 1, 2);
        grid.add(cityTextField, 1, 3);
        grid.add(streetTextField, 1, 4);
        grid.add(homeTextField, 1, 5);


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                Notation notation = new Notation();
                boolean flag = false;

                if (notation.setPhoneNumber(numberTextField.getText()))
                    if (notation.setNameOfSubscriber(nameTextField.getText()))
                        if (notation.getNameOfTheSubscriber().equals("phone number is free")) {
                            flag = false;
                            notation.subscriberAddress.setSubscriberAddress();
                            notation.fullAddresCreate();
                            notation.determinantID(notation.getPhoneNumber());
                            base.ATCBase.add(notation);
                            try {
                                base.toFile();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            flag = true;
                        } else if (notation.subscriberAddress.setSubscriberAddress(cityTextField.getText(),
                                streetTextField.getText(), homeTextField.getText())) {
                            flag = false;
                            notation.fullAddresCreate();
                            notation.determinantID(notation.getPhoneNumber());
                            base.ATCBase.add(notation);
                            try {
                                base.toFile();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            flag = true;
                        }
                if (!flag) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    // System.out.println(result);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Данные введены не верно!");
                    alert.showAndWait();
                    alert.getButtonTypes().setAll(ButtonType.CLOSE);
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    // System.out.println(result);
                    alert.setTitle("Успех");
                    alert.setHeaderText("Запись добавлена!");
                    alert.showAndWait();
                    alert.getButtonTypes().setAll(ButtonType.CLOSE);
                }
            }
            return null;
        });
        dialog.getDialogPane().setContent(grid);
        dialog.showAndWait();
    }

    @FXML
    void reassign(ActionEvent event) {
        int index = table.getSelectionModel().getSelectedIndex();
        System.out.println(index);

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setX(400);
        dialog.setTitle("Переназначение номера");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.setPadding(new Insets(20, 10, 10, 10));

        ButtonType reassignButtonType = new ButtonType("Переназначить", ButtonBar.ButtonData.OK_DONE);
        ButtonType closeButtonType = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(reassignButtonType, closeButtonType);

        Label label = new Label("Введите :");


        TextField textField1 = new TextField();
        textField1.setPromptText("Имя");
        TextField textField2 = new TextField();
        textField2.setPromptText("Город");
        TextField textField3 = new TextField();
        textField3.setPromptText("Улица");
        TextField textField4 = new TextField();
        textField4.setPromptText("Дом");


        grid.add(label, 0, 0);
        grid.add(textField1, 1, 0);
        grid.add(textField2, 2, 0);
        grid.add(textField3, 3, 0);
        grid.add(textField4, 4, 0);

        if (index > -1) {
            dialog.getDialogPane().setContent(grid);
            AtomicBoolean flag = new AtomicBoolean(false);


            dialog.setResultConverter(dialogButton -> {

                if (dialogButton == reassignButtonType) {
                    if (base.ATCBase.get(index).setNameOfSubscriber(textField1.getText())) {
                        if (base.ATCBase.get(index).subscriberAddress.setSubscriberAddress(textField2.getText(), textField3.getText(), textField4.getText())) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            // System.out.println(result);
                            base.ATCBase.get(index).fullAddresCreate();
                            try {
                                base.toFile();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.out.println("успех");
                            table.refresh();
                            alert.setTitle("");
                            alert.setHeaderText("Номер успешно переназначен");

                            flag.set(true);
                            alert.getButtonTypes().setAll(ButtonType.CLOSE);
                            alert.showAndWait();

                        }

                    }
                }

                if (!flag.get()) {
                    error();
                    flag.set(true);
                }
                return null;
            });

            dialog.showAndWait();
        } else if (index < 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Выберете запись!!!");
            alert.showAndWait();
            alert.getButtonTypes().setAll(ButtonType.CLOSE);
        }

    }

    private void error() {
        System.out.println("error");
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Ошибка");
        alert1.setHeaderText("Поля заполнены не верно!");
        alert1.getButtonTypes().setAll(ButtonType.CLOSE);
        alert1.show();
    }

    @FXML
    void referens(ActionEvent event) {
        if (byNumSearch.isSelected()) {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setX(400);
            dialog.setTitle("Поиск по номеру");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.setPadding(new Insets(20, 10, 10, 10));

            ButtonType searchButtonType = new ButtonType("Найти", ButtonBar.ButtonData.OK_DONE);
            ButtonType closeButtonType = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(searchButtonType, closeButtonType);
            Label label = new Label("Введите номер:");
            TextField textField = new TextField();
            grid.add(label, 0, 0);
            grid.add(textField, 1, 0);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == searchButtonType) {
                    if (new Notation().normNumFormat(textField.getText())) {
                        String result = new Base().searchByNumber(textField.getText());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                        alert.setTitle("Результаты поиска");
                        alert.setHeaderText(result);
                        alert.showAndWait();
                        alert.getButtonTypes().setAll(ButtonType.CLOSE);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Ошибка");
                        alert.setHeaderText("Номер введён неправильно!");
                        alert.showAndWait();
                        alert.getButtonTypes().setAll(ButtonType.CLOSE);
                    }
                }

                return null;
            });
            dialog.getDialogPane().setContent(grid);


            dialog.showAndWait();
        } else if (byName.isSelected()) {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setX(400);
            dialog.setTitle("Поиск по имени:");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            grid.setPadding(new Insets(20, 10, 10, 10));

            ButtonType searchButtonType = new ButtonType("Найти", ButtonBar.ButtonData.OK_DONE);
            ButtonType closeButtonType = new ButtonType("Отменить", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(searchButtonType, closeButtonType);
            Label label = new Label("Введите имя:");
            TextField textField = new TextField();
            grid.add(label, 0, 0);
            grid.add(textField, 1, 0);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == searchButtonType) {
                    String result = new Base().searchByName(textField.getText());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    // System.out.println(result);
                    alert.setTitle("Результаты поиска");
                    alert.setHeaderText(result);
                    alert.showAndWait();
                    alert.getButtonTypes().setAll(ButtonType.CLOSE);
                }

                return null;
            });
            dialog.getDialogPane().setContent(grid);


            dialog.showAndWait();

        } else {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setX(400);
            dialog.setTitle("Свободные номера:");
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            dialog.getDialogPane().setMinWidth(200);
            grid.setPadding(new Insets(20, 10, 10, 10));


            ButtonType closeButtonType = new ButtonType("Ок", ButtonBar.ButtonData.CANCEL_CLOSE);
            dialog.getDialogPane().getButtonTypes().addAll(closeButtonType);

            ArrayList<String> result = base.lisOfFreeNumbers();
            if (!result.equals(null))
                for (int i = 0; i < result.size(); i++) {
                    grid.add(new Label(result.get(i)), 1, i);
                }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                // System.out.println(result);
                alert.setTitle("Результаты поиска");
                alert.setHeaderText("Свободные номера не найдены!");
                alert.showAndWait();
                alert.getButtonTypes().setAll(ButtonType.CLOSE);

            }


            dialog.getDialogPane().setContent(grid);

            dialog.showAndWait();
        }
    }

    public static Base base = new Base();

    @FXML
    void initialize() {


        byNumSearch.setSelected(true);
        base.fromFile();
        reload();
        numC.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        ownerC.setCellValueFactory(new PropertyValueFactory<>("NameOfTheSubscriber"));
        addresC.setCellValueFactory(new PropertyValueFactory<>("fullAdress"));
        operatorC.setCellValueFactory(new PropertyValueFactory<>("nameOperator"));
        table.setItems(base.ATCBase);

        try {
            base.toFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reload() {
        for (int i = 0; i < base.ATCBase.size(); i++) {
            base.ATCBase.get(i).fullAddresCreate();

            // System.out.println(base.ATCBase.get(i).getFullAdress());
            base.ATCBase.get(i).determinantID(base.ATCBase.get(i).getPhoneNumber());
        }
    }
}
