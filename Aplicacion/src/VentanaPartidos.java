import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaPartidos extends JFrame {
    private Connection connection;
    private JTextField searchField;
    private JTextArea matchesArea;

    public VentanaPartidos() {
        setTitle("Lista de Partidos de Fútbol");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes de la ventana
        JLabel searchLabel = new JLabel("Buscar:");
        searchField = new JTextField();
        JButton searchButton = new JButton("Buscar");
        matchesArea = new JTextArea();

        // Configurar área de partidos
        matchesArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(matchesArea);

        // Crear contenedor y configurar el diseño
        JPanel container = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        container.add(searchPanel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);
        add(container);

        // Agregar ActionListener al botón de búsqueda
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText().trim();
                searchMatches(searchText);
            }
        });

        // Conectar a la base de datos
        connectToDatabase();

        // Mostrar todos los partidos al iniciar la ventana
        displayMatches();

        setVisible(true);
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/nombre_base_de_datos";
        String user = "usuario";
        String password = "contraseña";

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayMatches() {
        matchesArea.setText("");
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM partidos";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String equipoA = resultSet.getString("equipo_a");
                String equipoB = resultSet.getString("equipo_b");
                String arbitro = resultSet.getString("arbitro");
                String jugador = resultSet.getString("jugador");
                String entrenador = resultSet.getString("entrenador");
                matchesArea.append("Equipo A: " + equipoA + " | Equipo B: " + equipoB +
                        " | Árbitro: " + arbitro + " | Jugador: " + jugador + " | Entrenador: " + entrenador + "\n");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchMatches(String searchText) {
        matchesArea.setText("");
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM partidos WHERE equipo_a LIKE ? OR equipo_b LIKE ? OR arbitro LIKE ? OR jugador LIKE ? OR entrenador LIKE ?");
            String likeSearchText = "%" + searchText + "%";
            statement.setString(1, likeSearchText);
            statement.setString(2, likeSearchText);
            statement.setString(3, likeSearchText);
            statement.setString(4, likeSearchText);
            statement.setString(5, likeSearchText);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String equipoA = resultSet.getString("equipo_a");
                String equipoB = resultSet.getString("equipo_b");
                String arbitro = resultSet.getString("arbitro");
                String jugador = resultSet.getString("jugador");
                String entrenador = resultSet.getString("entrenador");
                matchesArea.append("Equipo A: " + equipoA + " | Equipo B: " + equipoB +
                        " | Árbitro: " + arbitro + " | Jugador: " + jugador + " | Entrenador: " + entrenador + "\n");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new VentanaPartidos();
            }
        });
    }
}