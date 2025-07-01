package com.mycompany.conectahogar.dao;

import com.mycompany.conectahogar.config.ConexionBD;
import com.mycompany.conectahogar.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClienteDAO {
    private static final Logger logger = LoggerFactory.getLogger(ClienteDAO.class);

    public boolean crearRegistroCliente(Cliente cliente) {
        if (cliente.getId_Usuario() == 0) {
            logger.error("No se puede crear el registro de cliente sin un ID de usuario base.");
            return false;
        }
        String sql = "INSERT INTO clientes (id_Usuario, direccion) VALUES (?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getId_Usuario());
            stmt.setString(2, cliente.getDireccion());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al crear el registro del cliente: {}", e.getMessage(), e);
        }
        return false;
    }
    
    public boolean actualizarRegistroCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET direccion = ? WHERE id_Usuario = ?";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getDireccion());
            stmt.setInt(2, cliente.getId_Usuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al actualizar el registro del cliente {}: {}", cliente.getId_Usuario(), e.getMessage(), e);
        }
        return false;
    }
}