import java.sql.*;
import java.util.Scanner;

public class Libros {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Deseas guardar o comprar un libro? :");
        String respuesta = scanner.nextLine();

            while (respuesta.equals("guardar")){

                System.out.print("Ingresa codigo del libro: ");
                String codigo = scanner.nextLine();

                System.out.print("Ingresa nombre del libro: ");
                String nombre = scanner.nextLine();

                System.out.print("Ingresa la descripcion del libro: ");
                String descripcion = scanner.nextLine();

                System.out.print("Ingrese la cantidad del libro: ");
                String cantidad = scanner.nextLine();

                System.out.println("Ingresa el valor del libro: ");
                String valor = scanner.nextLine();

                Insert(codigo, nombre, descripcion, cantidad, valor); //

            }
            if (respuesta.equals("comprar")) {
                Select();

                System.out.print("Ingrese codigo del producto: ");
                String codigo = scanner.nextLine();

                int disponibilidad = Select_One (codigo);

                System.out.println("¿Cuantos libros desea comprar?: ");
                int libro = Integer.parseInt(scanner.nextLine());

                int resultado = disponibilidad - libro;

                Editar(codigo, resultado);

                int valorlibro = Select_Onevalor (codigo);

                int resultados = valorlibro * libro;

                System.out.println("Su valor a pagar es: " + resultados);
            }

}

    private static int Select_Onevalor(String codigo) throws SQLException, ClassNotFoundException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/libros";
        String username = "root";
        String password = "";
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM carritolibro WHERE codigo = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, codigo); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {
            codigo = resultSet.getString("codigo");
            String nombre = resultSet.getString("nombre");
            String descripcion  = resultSet.getString("descripcion");
            String cantidad= resultSet.getString("cantidad");
            String valor = resultSet.getString("valor");

            return Integer.parseInt(valor);

        } else {
            System.out.println("No se encontró un registro con el codigo especificado.");
        }

        // Cerrar recursos
        resultSet.close();
        statement.close();
        connection.close();

        return 0;

    }

    private static void Editar(String codigo, int resultado) throws ClassNotFoundException, SQLException {
        String driver2 = "com.mysql.cj.jdbc.Driver";
        String url2 = "jdbc:mysql://localhost:3306/libros";
        String username2 = "root";
        String pass2 = "";

        Class.forName(driver2);
        Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

        Statement statement2 = connection2.createStatement();

        String consulta = "UPDATE carritolibro SET cantidad = ? WHERE codigo = ?";
        PreparedStatement preparedStatement = connection2.prepareStatement(consulta);

        preparedStatement.setInt(1, resultado);
        preparedStatement.setString(2, codigo);

        int filasActualizadas = preparedStatement.executeUpdate();
        if (filasActualizadas > 0) {
            System.out.println("Libro comprado exitosamente");
        } else {
            System.out.println("No se encontró el registro para actualizar");
        }

        preparedStatement.close();
        connection2.close();
    }

    private static int Select_One(String codigo) throws SQLException, ClassNotFoundException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/libros";
        String username = "root";
        String password = "";
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        String consultaSQL = "SELECT * FROM carritolibro WHERE codigo = ?";

        PreparedStatement statement = connection.prepareStatement(consultaSQL);
        statement.setString(1, codigo); // Establecer el valor del parámetro

        // Ejecutar la consulta
        ResultSet resultSet = statement.executeQuery();

        // Procesar el resultado si existe
        if (resultSet.next()) {
            codigo = resultSet.getString("codigo");
            String nombre = resultSet.getString("nombre");
            String descripcion  = resultSet.getString("descripcion");
            String cantidad= resultSet.getString("cantidad");
            String valor = resultSet.getString("valor");

            System.out.println(" Este es el codigo del libro a comprar: " + codigo + " Con nombre " + nombre);

            return Integer.parseInt(cantidad);

        } else {
            System.out.println("No se encontró un registro con el codigo especificado.");
        }

        // Cerrar recursos
        resultSet.close();
        statement.close();
        connection.close();
        return 0;

    }

    private static void Select() throws SQLException, ClassNotFoundException {

            String driver2 = "com.mysql.cj.jdbc.Driver";
            String url2 = "jdbc:mysql://localhost:3306/libros";
            String username2 = "root";
            String pass2 = "";

            Class.forName(driver2);
            Connection connection2 = DriverManager.getConnection(url2, username2, pass2);

            Statement statement2 = connection2.createStatement();

            ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM carritolibro");

            while(resultSet2.next()){
                String codigo = resultSet2.getString("codigo");
                String nombre = resultSet2.getString("nombre");
                String descripcion  = resultSet2.getString("descripcion");
                String cantidad = resultSet2.getString("cantidad");
                String valor = resultSet2.getString("valor");

                System.out.println(" Este es el codigo " + codigo +  " nombre " + nombre + " cantidad " + cantidad + " valor " + valor +  " descripcion " + descripcion);
            }
    }

    private static void Insert(String codigo, String nombre, String descripcion, String cantidad, String valor) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/libros";
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM carritolibro");


            // Sentencia INSERT
            String sql = "INSERT INTO carritolibro (codigo, nombre, descripcion, cantidad, valor) VALUES (?, ?, ?, ?, ?)";

            // Preparar la sentencia
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, codigo);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, descripcion );
            preparedStatement.setString(4, cantidad);
            preparedStatement.setString(5, valor);

            // Ejecutar la sentencia
            int filasAfectadas = preparedStatement.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Libro agregado exitosamente.");
            } else {
                System.out.println("No se pudo agregar el libro.");
            }

            preparedStatement.close();
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    }

