import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterDialog extends JDialog {
    private JTextField userField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private boolean success;

    public RegisterDialog(JFrame parent) {
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

        JButton acceptButton = new JButton("Aceptar");
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

        // Agregamos un ActionListener al botón "Aceptar" para que cierre la ventana y marque el éxito como verdadero
        acceptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                success = true;
                dispose();
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

    public String getUsername() {
        return userField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public boolean isSuccess() {
        return success;
    }
}