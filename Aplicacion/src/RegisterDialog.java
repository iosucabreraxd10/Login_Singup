import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterDialog extends JDialog {
    private JTextField userField;
    private JPasswordField passwordField;
    private JTextField emailField;

    public RegisterDialog(JFrame parent, Connection connection) {
        super(parent, "Registro", true);
        setSize(300, 250);
        setLocationRelativeTo(null);

        // Creamos los componentes
        JLabel userLabel = new JLabel("Usuario:");
        userField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField();
        JLabel emailLabel = new JLabel("Correo electrónico:");
        emailField = new JTextField();

        JButton acceptButton = new JButton("Registrarse");
        JButton cancelButton = new JButton("Cancelar");

        // Agregamos los componentes al contenedor
        JPanel container = new JPanel(new GridLayout(4, 2));
        container.add(userLabel);
        container.add(userField);
        container.add(passwordLabel);
        container.add(passwordField);
        container.add(emailLabel);
        container.add(emailField);
        container.add(acceptButton);
        container.add(cancelButton);
        add(container);

        // Agregamos un ActionListener al botón "Registrarse" para que cierre la ventana y marque el éxito como verdadero
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = getUsername();
                char[] password = getPassword();
                String email = getEmail();

                // Aquí puedes agregar la lógica para guardar el nuevo usuario en la base de datos
                if (saveUser(username, password, email, connection)) {
                    JOptionPane.showMessageDialog(RegisterDialog.this, "Usuario registrado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(RegisterDialog.this, "Error al registrar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Agregamos un ActionListener al botón "Cancelar" para que cierre la ventana
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private boolean saveUser(String username, char[] password, String email, Connection connection) {
        try {
            // Preparar la consulta INSERT
            String query = "INSERT INTO Registro (nom_us, contraseña, correo) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, username);
            statement.setString(2, new String(password));
            statement.setString(3, email);

            // Ejecutar la consulta INSERT
            int rowsAffected = statement.executeUpdate();

            // Cerrar el statement
            statement.close();

            // Verificar si se insertó correctamente al menos una fila
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUsername() {
        return userField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public String getEmail() {
        return emailField.getText();
    }
}