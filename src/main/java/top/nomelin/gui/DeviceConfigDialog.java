package top.nomelin.gui;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceConfigDialog extends JDialog {
    private final JTextField deviceIdField = new JTextField(15);
    private final JPanel sensorPanel = new JPanel(new GridLayout(0, 1));
    private final List<SensorConfig> sensors = new ArrayList<>();
    private final Frame owner;
    private boolean confirmed = false;

    public DeviceConfigDialog(Frame owner) {
        super(owner, "设备配置", true);
        this.owner = owner;
        setLayout(new BorderLayout());
        setSize(800, 600);
        // 让窗口居中
        setLocationRelativeTo(owner);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Device ID
        JPanel deviceIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deviceIdPanel.add(new JLabel("设备ID:"));
        deviceIdField.setPreferredSize(new Dimension(200, 25));
        deviceIdPanel.add(deviceIdField);

        // Sensors
        JScrollPane scrollPane = new JScrollPane(sensorPanel);
        scrollPane.setPreferredSize(new Dimension(450, 250));

        // Add sensor button
        JButton addSensorBtn = new JButton("添加传感器");
        addSensorBtn.addActionListener(e -> showSensorConfigDialog());

        // Buttons panel
        JPanel btnPanel = new JPanel();
        JButton saveBtn = new JButton("保存");
        JButton cancelBtn = new JButton("取消");

        saveBtn.addActionListener(e -> saveConfiguration());
        cancelBtn.addActionListener(e -> setVisible(false));

        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);

        mainPanel.add(deviceIdPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(addSensorBtn, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void showSensorConfigDialog() {
        SensorConfigDialog dialog = new SensorConfigDialog(this.owner);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            sensors.add(dialog.getSensorConfig());
            updateSensorDisplay();
        }
    }

    private void updateSensorDisplay() {
        sensorPanel.removeAll();
        for (SensorConfig config : sensors) {
            JLabel label = new JLabel(config.toString());
            sensorPanel.add(label);
        }
        sensorPanel.revalidate();
        sensorPanel.repaint();
    }

    private void saveConfiguration() {
        if (deviceIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Device ID cannot be empty");
            return;
        }
        confirmed = true;
        setVisible(false);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getDeviceId() {
        return deviceIdField.getText().trim();
    }

    public List<SensorConfig> getSensors() {
        return new ArrayList<>(sensors);
    }
}