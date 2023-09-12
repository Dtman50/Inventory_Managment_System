module main.task1_dariust {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.task1_dariust to javafx.fxml;
    exports main.task1_dariust;
}