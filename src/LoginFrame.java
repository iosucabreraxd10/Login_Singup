import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("Mi aplicación");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creamos los componentes
        JLabel registerLabel = new JLabel("Registrarse");
        registerLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        registerLabel.setHorizontalAlignment(JLabel.CENTER);

        JLabel loginLabel = new JLabel("Iniciar sesión");
        loginLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        loginLabel.setHorizontalAlignment(JLabel.CENTER);

        // Agregamos los componentes al contenedor
        JPanel container = new JPanel(new GridLayout(2, 1));
        container.add(registerLabel);
        container.add(loginLabel);
        add(container);

        // Agregamos un ActionListener al loginLabel para que abra la ventana de iniciar sesión
        loginLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Abre la ventana de iniciar sesión
                new LoginDialog(LoginFrame.this);
            }
        });

        // Agregamos un ActionListener al registerLabel para que abra la ventana de registro
        registerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Abre la ventana de registro
                new RegisterDialog(LoginFrame.this);
            }
        });
    }

    public static void main(String[] args) {
        // Creamos el JFrame y lo hacemos visible
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}