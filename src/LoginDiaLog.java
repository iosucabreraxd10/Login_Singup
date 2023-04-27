import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class LoginDialog extends JDialog {
    private JTextField userField;
    private JPasswordField passwordField;
    private boolean success;

    public LoginDialog(JFrame parent) {
        super(parent, "Iniciar sesión", true);
        setSize(300, 200);
        setLocationRelativeTo(null);

        // Creamos los componentes
        JLabel userLabel = new JLabel("Usuario:");
        userField = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordField = new JPasswordField();

        JButton acceptButton = new JButton("Aceptar");
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

    public boolean getSuccess() {
        return success;
    }
}