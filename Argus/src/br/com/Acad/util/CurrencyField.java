package br.com.Acad.util;
import java.text.NumberFormat;
import java.util.Locale;

import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.NodeOrientation;

/**
 * Simple Currency Field for JavaFX
 * @author Gustavo
 * @version 1.0
 */
public class CurrencyField{

    private NumberFormat format;
    private SimpleDoubleProperty amount;
    private JFXTextField textField;

    public CurrencyField(Locale locale, JFXTextField txt) {
        this(locale, 0.00, txt);
    }

    public CurrencyField(Locale locale, Double initialAmount, JFXTextField txt) {
    	textField = txt;
    	textField.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        amount = new SimpleDoubleProperty(this, "amount", initialAmount);
        format = NumberFormat.getCurrencyInstance(locale);
        textField.setText(format.format(initialAmount));

        // Remove selection when textfield gets focus
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            Platform.runLater(() -> {
                int lenght = textField.getText().length();
                textField.selectRange(lenght, lenght);
                textField.positionCaret(lenght);
            });
        });

        // Listen the text's changes
        textField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                formatText(newValue);
            }
        });
    }

    /**
     * Get the current amount value
     * @return Total amount
     */
    public Double getAmount() {
        return amount.get();
    }

    /**
     * Property getter
     * @return SimpleDoubleProperty
     */
    public SimpleDoubleProperty amountProperty() {
        return this.amount;
    }

    /**
     * Change the current amount value
     * @param newAmount
     */
    public void setAmount(Double newAmount) {
        if(newAmount >= 0.0) {
            amount.set(newAmount);
            formatText(format.format(newAmount));
        }
    }

    /**
     * Set Currency format
     * @param locale
     */
    public void setCurrencyFormat(Locale locale) {
        format = NumberFormat.getCurrencyInstance(locale);
        formatText(format.format(getAmount()));
    }

    private void formatText(String text) {
        if(text != null && !text.isEmpty()) {
            String plainText = text.replaceAll("[^0-9]", "");

            while(plainText.length() < 3) {
                plainText = "0" + plainText;
            }

            StringBuilder builder = new StringBuilder(plainText);
            builder.insert(plainText.length() - 2, ".");

            Double newValue = Double.parseDouble(builder.toString());
            amount.set(newValue);
            textField.setText(format.format(newValue));
        }
    }

//    @Override
//    public void deleteText(int start, int end) {
//        StringBuilder builder = new StringBuilder(getText());
//        builder.delete(start, end);
//        formatText(builder.toString());
//        textField.selectRange(start, start);
//    }

}