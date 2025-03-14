package top.nomelin.gui;


import javax.swing.*;
import java.awt.*;

public class SensorConfigDialog extends JDialog {
    private final JTextField sensorIdField = new JTextField(15);
    private final JTextField minField = new JTextField(5);
    private final JTextField maxField = new JTextField(5);
    private final JTextField frequencyField = new JTextField(5);
    private final JTextField phaseField = new JTextField(5);
    private final JTextField smallRandomDisturbanceField = new JTextField(5);
    private final JCheckBox integerCheck = new JCheckBox("Integer");
    private final JComboBox<String> typeCombo = new JComboBox<>(
            new String[]{"随机数", "正弦波(带噪声)"}
    );
    private final JTextField intervalField = new JTextField(5);
    private boolean confirmed = false;

    public SensorConfigDialog(Frame owner) {
        super(owner, "Sensor Configuration", true);
        setLayout(new GridLayout(0, 2, 5, 5));
        setSize(600, 400);
        // 让窗口居中
        setLocationRelativeTo(owner);

        add(new JLabel("传感器标识:"));
        add(sensorIdField);
        add(new JLabel("最小值:"));
        add(minField);
        add(new JLabel("最大值:"));
        add(maxField);
        add(new JLabel("频率(Hz):"));
        add(frequencyField);
        add(new JLabel("相位(度):"));
        add(phaseField);
        add(new JLabel("正弦波小幅随机噪声范围:"));
        add(smallRandomDisturbanceField);
        add(new JLabel("数据类型:"));
        add(integerCheck);
        add(new JLabel("生成器:"));
        add(typeCombo);
        add(new JLabel("预期间隔(ms):"));
        add(intervalField);

        JButton saveBtn = new JButton("保存");
        JButton cancelBtn = new JButton("取消");

        saveBtn.addActionListener(e -> validateAndSave());
        cancelBtn.addActionListener(e -> setVisible(false));

        JPanel btnPanel = new JPanel();
        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);
        add(btnPanel);
    }

    private void validateAndSave() {
        try {
            Double.parseDouble(minField.getText());
            Double.parseDouble(maxField.getText());
//            Integer.parseInt(intervalField.getText());
            if (sensorIdField.getText().trim().isEmpty()) {
                throw new IllegalArgumentException();
            }
            confirmed = true;
            setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input values");
        }
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public SensorConfig getSensorConfig() {
        SensorConfig config = new SensorConfig();
        config.setSensorId(sensorIdField.getText().trim());
        config.setMinValue(Double.parseDouble(minField.getText()));
        config.setMaxValue(Double.parseDouble(maxField.getText()));
        config.setFrequency(Double.parseDouble(frequencyField.getText()));
        config.setPhase(Double.parseDouble(phaseField.getText()));
        config.setSmallRandomDisturbance(Double.parseDouble(smallRandomDisturbanceField.getText()));
        config.setInteger(integerCheck.isSelected());
        config.setGenerationType((String) typeCombo.getSelectedItem());
        config.setInterval(Integer.parseInt(intervalField.getText()));
        return config;
    }
}