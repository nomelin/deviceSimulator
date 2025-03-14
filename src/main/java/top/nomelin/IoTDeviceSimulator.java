package top.nomelin;

import lombok.extern.slf4j.Slf4j;
import top.nomelin.device.Sensor;
import top.nomelin.device.SimulatedDevice;
import top.nomelin.gui.DeviceConfigDialog;
import top.nomelin.gui.SensorConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class IoTDeviceSimulator {
    private final List<SimulatedDevice> devices = new ArrayList<>();
    private JFrame mainFrame;
    private final DefaultListModel<String> deviceListModel = new DefaultListModel<>();
    private String userId = "1";
    private int bufferSize = 10;
    private JList<String> deviceList;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new IoTDeviceSimulator().initialize());
    }

    private void initialize() {
        mainFrame = new JFrame("IoT设备模拟器");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 800);

        JPanel controlPanel = new JPanel(new BorderLayout());

        // Device list
        deviceList = new JList<>(deviceListModel);
        controlPanel.add(new JScrollPane(deviceList), BorderLayout.CENTER);

        // Control buttons
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JButton addDeviceBtn = new JButton("添加设备");
        JButton configBtn = new JButton("全局配置");
        JButton removeBtn = new JButton("移除设备");

        addDeviceBtn.addActionListener(this::handleAddDevice);
        configBtn.addActionListener(this::handleGlobalConfig);
        removeBtn.addActionListener(this::handleRemoveDevice);

        buttonPanel.add(addDeviceBtn);
        buttonPanel.add(configBtn);
        buttonPanel.add(removeBtn);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);
    }

    private void handleAddDevice(ActionEvent e) {
        DeviceConfigDialog dialog = new DeviceConfigDialog(mainFrame);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            String deviceId = dialog.getDeviceId();
            List<SensorConfig> sensors = dialog.getSensors();

            List<Sensor> deviceSensors = new ArrayList<>();
            for (SensorConfig config : sensors) {
                deviceSensors.add(config.toSensor());
            }

            SimulatedDevice device = new SimulatedDevice(
                    deviceId,
                    deviceSensors,
                    bufferSize,
                    userId
            );

            devices.add(device);
            deviceListModel.addElement(deviceId);
            log.info("添加设备: " + deviceId);
            device.start();
        }
    }

    private void handleGlobalConfig(ActionEvent e) {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField userIdField = new JTextField(userId);
        JTextField bufferField = new JTextField(String.valueOf(bufferSize));

        panel.add(new JLabel("用户ID:"));
        panel.add(userIdField);
        panel.add(new JLabel("缓冲区大小:"));
        panel.add(bufferField);

        int result = JOptionPane.showConfirmDialog(
                mainFrame,
                panel,
                "全局配置",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            userId = userIdField.getText();
            try {
                bufferSize = Integer.parseInt(bufferField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainFrame, "Invalid buffer size");
            }
        }
    }

    private void handleRemoveDevice(ActionEvent e) {
        int selectedIndex = deviceList.getSelectedIndex();
        if (selectedIndex != -1) {
            devices.get(selectedIndex).stop();
            devices.remove(selectedIndex);
            deviceListModel.remove(selectedIndex);
        }
    }
}