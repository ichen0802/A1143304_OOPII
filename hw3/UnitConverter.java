

import javax.swing.*;
import java.awt.*;

public class UnitConverter extends JFrame {

    JComboBox<String> typeBox;
    JComboBox<String> fromBox;
    JComboBox<String> toBox;
    JTextField inputField;
    JTextField resultField;
    JButton convertButton;

    String[] lengths = {"公尺", "公分", "英吋", "英尺"};
    String[] weights = {"公斤", "公克", "磅", "盎司"};
    String[] temps = {"攝氏", "華氏", "克氏"};

    public UnitConverter() {

        setTitle("單位換算器");
        setSize(480, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // NORTH：選擇換算類型
        typeBox = new JComboBox<>(
            new String[]{"長度", "重量", "溫度"}
        );
        add(typeBox, BorderLayout.NORTH);

        // CENTER：兩列輸入欄位與單位選單
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        inputField = new JTextField();
        resultField = new JTextField();
        resultField.setEditable(false);

        fromBox = new JComboBox<>(lengths);
        toBox = new JComboBox<>(lengths);

        centerPanel.add(inputField);
        centerPanel.add(fromBox);
        centerPanel.add(resultField);
        centerPanel.add(toBox);

        add(centerPanel, BorderLayout.CENTER);

        // SOUTH：換算按鈕
        convertButton = new JButton("換算");
        add(convertButton, BorderLayout.SOUTH);

        // 切換類型時更新單位
        typeBox.addActionListener(e -> {
            String type = (String) typeBox.getSelectedItem();

            String[] units;

            if (type.equals("長度")) {
                units = lengths;
            } else if (type.equals("重量")) {
                units = weights;
            } else {
                units = temps;
            }

            fromBox.setModel(new DefaultComboBoxModel<>(units));
            toBox.setModel(new DefaultComboBoxModel<>(units));
            resultField.setText("");
        });

        // 按下換算按鈕
        convertButton.addActionListener(e -> convert());

        setVisible(true);
    }

    public void convert() {
        try {
            double value = Double.parseDouble(inputField.getText());

            int from = fromBox.getSelectedIndex();
            int to = toBox.getSelectedIndex();
            int type = typeBox.getSelectedIndex();

            double result = 0;

            if (type == 0) {
                // 長度：全部先轉成公尺
                double[] factors = {1, 0.01, 0.0254, 0.3048};
                result = value * factors[from] / factors[to];

            } else if (type == 1) {
                // 重量：全部先轉成公斤
                double[] factors = {1, 0.001, 0.45359237, 0.028349523125};
                result = value * factors[from] / factors[to];

            } else {
                // 溫度：先轉成攝氏
                double celsius;

                if (from == 0) {
                    celsius = value;
                } else if (from == 1) {
                    celsius = (value - 32) * 5 / 9;
                } else {
                    celsius = value - 273.15;
                }

                if (to == 0) {
                    result = celsius;
                } else if (to == 1) {
                    result = celsius * 9 / 5 + 32;
                } else {
                    result = celsius + 273.15;
                }
            }

            resultField.setText(String.format("%.4f", result));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "請輸入有效的數字！");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UnitConverter());
    }
}
