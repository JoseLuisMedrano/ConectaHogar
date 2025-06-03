package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;
import model.RolUsuario;  // Importa el enum RolUsuario

public class UsuarioDAO {

    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public void guardar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nombre, apellido, correo_electronico, contrasena, telefono, rol) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getCorreoElectronico());
            stmt.setString(4, usuario.getContrasena());
            stmt.setString(5, usuario.getTelefono());
            // Convierte el enum a String para guardar en la BD
            stmt.setString(6, usuario.getRol().name());
            stmt.executeUpdate();
        }
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo_electronico"),
                    rs.getString("contrasena"),
                    rs.getString("telefono"),
                    // Convierte el String a enum RolUsuario
                    RolUsuario.valueOf(rs.getString("rol"))
                );
            }
        }
        return null;
    }

    public List<Usuario> listarTodos() throws SQLException {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> lista = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("correo_electronico"),
                    rs.getString("contrasena"),
                    rs.getString("telefono"),
                    RolUsuario.valueOf(rs.getString("rol"))
                ));
            }
        }
        return lista;
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
