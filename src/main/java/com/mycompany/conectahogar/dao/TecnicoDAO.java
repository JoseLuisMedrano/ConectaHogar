package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.config.ConexionBD;
import com.mycompany.conectahogar.model.Tecnico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TecnicoDAO {
    /**
     * Crea el registro específico en la tabla 'tecnicos' usando el ID del usuario ya creado.
     */
    public boolean crearRegistroTecnico(Tecnico tecnico) {
        String sql = "INSERT INTO tecnicos (id_Usuario, especialidad, disponibilidad, perfil_activo) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tecnico.getId_Usuario());
            pstmt.setString(2, tecnico.getEspecialidad());
            pstmt.setString(3, tecnico.getDisponibilidad());
            pstmt.setBoolean(4, tecnico.isPerfilActivo());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Actualiza el perfil en la tabla 'tecnicos'.
     */
    public boolean actualizarPerfil(int idTecnico, String especialidad, String disponibilidad) {
        String sql = "UPDATE tecnicos SET especialidad = ?, disponibilidad = ? WHERE id_Usuario = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, especialidad);
            pstmt.setString(2, disponibilidad);
            pstmt.setInt(3, idTecnico);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}