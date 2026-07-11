package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Model.Paciente;

@SuppressWarnings("CallToPrintStackTrace")
public class PacienteDAO {

    public boolean insert(Paciente p) {
        String sql="insert into pacientes(dni, nombre, apellidos, sexo, telefono, fecha_nac) values (?,?,?,?,?,?)";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellidos());
            ps.setString(4, p.getSexo());
            ps.setString(5, p.getTelefono());
            ps.setDate(6, java.sql.Date.valueOf(p.getFechaNacimiento()));
            
            int affectedRows=ps.executeUpdate();
            if (affectedRows>0) {
                ResultSet rs=ps.getGeneratedKeys();
                if (rs.next()) {
                    p.setIdPaciente(rs.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Paciente searchId(int id) {
        String sql="select * from pacientes where id_paciente=?";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToPaciente(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Paciente searchDni(String dni) {
        String sql="select * from pacientes where dni=?";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setString(1, dni);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToPaciente(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Paciente> list() {
        List<Paciente> lista=new ArrayList<>();
        String sql="select * from pacientes order by id_paciente";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql);
             ResultSet rs=ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSetToPaciente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean update(Paciente p) {
        String sql="update pacientes set dni=?, nombre=?, apellidos=?, sexo=?, telefono=?, fecha_nac=? where id_paciente=?";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setString(1, p.getDni());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getApellidos());
            ps.setString(4, p.getSexo());
            ps.setString(5, p.getTelefono());
            ps.setDate(6, java.sql.Date.valueOf(p.getFechaNacimiento()));
            ps.setInt(7, p.getIdPaciente());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int idPaciente) {
        String sql="delete from pacientes where id_paciente=?";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Paciente mapResultSetToPaciente(ResultSet rs) throws SQLException {
        Paciente p=new Paciente();
        p.setIdPaciente(rs.getInt("id_paciente"));
        p.setDni(rs.getString("dni"));
        p.setNombre(rs.getString("nombre"));
        p.setApellidos(rs.getString("apellidos"));
        p.setSexo(rs.getString("sexo"));
        p.setTelefono(rs.getString("telefono"));
        p.setFechaNacimiento(rs.getDate("fecha_nac").toLocalDate());
        return p;
    }
}