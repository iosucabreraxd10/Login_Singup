import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private Connection connection;

    public LoginFrame() {
        setTitle("FURBITO");
        setSize(400, 600);
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

        // Agregamos un ActionListener al registerLabel para que abra la ventana de registro
        registerLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Abre la ventana de registro
                new RegisterDialog(LoginFrame.this, connection);
            }
        });

        try {
            String url = "jdbc:oracle:thin:@127.16.7.213:1521:SID";
            String usuario = "C##CLAUDIO12";
            String contraseña = "grupo12";

            connection = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conectado");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Dentro del constructor de LoginFrame
        loginLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                LoginDialog loginDialog = new LoginDialog(LoginFrame.this, connection);
                if (loginDialog.getSuccess()) {
                    // Cerrar la ventana de inicio de sesión
                    dispose();

                    // Abrir la ventana de partidos
                    SwingUtilities.invokeLater(() -> {
                        new VentanaPartidos();
                    });
                }
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