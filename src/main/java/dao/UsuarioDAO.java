package dao;

import util.ConexionBD;
import java.sql.*;
import model.Usuario;

public class UsuarioDAO {

    public Usuario validar(String correo_electronico, String contrasena) {
        Usuario u = null;
        try (Connection con = ConexionBD.getConexion()) {
            String sql = "SELECT * FROM usuarios WHERE correo=? AND clave=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, correo_electronico);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setCorreo(rs.getString("correo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }
}
