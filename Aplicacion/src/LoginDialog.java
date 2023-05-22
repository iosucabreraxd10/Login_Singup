import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDialog extends JDialog {
    private JTextField userField;
    private JPasswordField passwordField;
    private boolean success;

    public LoginDialog(JFrame parent, Connection connection) {
        super(parent, "Iniciar sesión", true);
        setSize(400, 600);
        setLocationRelativeTo(null);

        // Creamos los componentes
        JLabel userLabel = new JLabel("Usuario:");
        userField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField();

        JButton acceptButton = new JButton("Iniciar sesión");
        JButton cancelButton = new JButton("Cancelar");

        // Agregamos los componentes al contenedor
        JPanel container = new JPanel(new GridLayout(3, 2));
        container.add(userLabel);
        container.add(userField);
        container.add(passwordLabel);
        container.add(passwordField);
        container.add(acceptButton);
        container.add(cancelButton);
        add(container);

        // Agregamos un ActionListener al botón "Aceptar" para que cierre la ventana y marque el éxito como verdadero
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = getUsername();
                char[] password = getPassword();

                success = validCredentials(username, password, connection);

                if (success) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Credenciales inválidas", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Agregamos un ActionListener al botón "Cancelar" para que cierre la ventana y marque el éxito como falso
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                success = false;
                dispose();
            }
        });

        setVisible(true);
    }

    private boolean validCredentials(String username, char[] password, Connection connection) {
        // Variables para almacenar el resultado de la verificación
        boolean isValid = false;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            // Consulta SQL para verificar las credenciales en la tabla Admin1
            String query = "SELECT COUNT(*) FROM Admin1 WHERE nom_us = ? AND contraseña = ?";
            statement = connection.prepareStatement(query);

            // Establecer los parámetros en la consulta preparada
            statement.setString(1, username);
            statement.setString(2, new String(password));

            // Ejecutar la consulta
            resultSet = statement.executeQuery();

            // Obtener el resultado de la consulta
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isValid = count > 0;
            }

            // Consulta SQL para verificar las credenciales en la tabla iniciar_sesion
            String query1 = "SELECT COUNT(*) FROM iniciar_sesion WHERE nom_us = ? AND contraseña = ?";
            statement = connection.prepareStatement(query1);

            // Establecer los parámetros en la consulta preparada
            statement.setString(1, username);
            statement.setString(2, new String(password));

            // Ejecutar la consulta
            resultSet = statement.executeQuery();

            // Obtener el resultado de la consulta
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isValid = isValid || count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return isValid;
    }

    public String getUsername() {
        return userField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public boolean getSuccess() {
        return success;
    }
}