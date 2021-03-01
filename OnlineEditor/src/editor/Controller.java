package editor;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.web.HTMLEditor;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private HTMLEditor text;

    @FXML
    private Button submitCode ;

    @FXML
    private PasswordField code;

    @FXML
    LineChart<?,?> chart;

    private int actualChartX = 0;
    private String ClientCode;
    private final int MAX_CHART_X = 25;
    private Client client;

    @FXML
    public void onTextChange(Event e)
    {
        this.addData(text.getHtmlText().length());

        this.client.sendTextUpdate(text.getHtmlText());
    }

    @FXML
    public void onSubmit(Event e)
    {
        this.ClientCode = this.code.getText();
        this.code.setText("");
        this.client.ChangeCode(this.ClientCode);
        this.text.setHtmlText(this.client.getGlobalText());
    }

    private void addData(int value)
    {
        actualChartX++;
        if(actualChartX%MAX_CHART_X == 1)
        {
            chart.getData().removeAll(Collections.singleton(chart.getData().setAll()));
        }
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data(Integer.toString(actualChartX),value));
        chart.getData().add(series);
    }

    private void resetData()
    {
        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data(Integer.toString(actualChartX),0));
        chart.getData().addAll(series);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        try{
            this.client = new Client("");
        }catch (IOException e){}
        this.resetData();
    }
}
