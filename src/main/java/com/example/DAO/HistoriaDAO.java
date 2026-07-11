package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Model.Historia;
import com.example.Model.Paciente;

@SuppressWarnings("CallToPrintStackTrace")
public class HistoriaDAO {
    private final PacienteDAO pacienteDAO=new PacienteDAO();

    public boolean insert(Historia h) {
        String sql="insert into historias(id_paciente, antecedentes, alergias, graduacion, observaciones) values (?,?,?,?,?)";
        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, h.getPaciente().getIdPaciente());
            ps.setString(2, h.getAntecedentes());
            ps.setString(3, h.getAlergias());
            
            if (h.getGraduacion()!=null && !h.getGraduacion().isEmpty()) {
                ps.setDouble(4, Double.parseDouble(h.getGraduacion()));
            } else {
                ps.setNull(4, java.sql.Types.DECIMAL);
            }
            
            ps.setString(5, h.getObservaciones());
            
            int affectedRows=ps.executeUpdate();
            if (affectedRows>0) {
                ResultSet rs=ps.getGeneratedKeys();
                if (rs.next()) {
                    try {
                        java.lang.reflect.Field field=Historia.class.getDeclaredField("idHistoria");
                        field.setAccessible(true);
                        field.set(h, rs.getInt(1));
                    } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Historia searchId(int id) {
        String sql="select * from historias where id_historia=?";
        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToHistoria(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Historia searchByPaciente(int idPaciente) {
        String sql="select * from historias where id_paciente=?";
        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToHistoria(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Historia> list() {
        List<Historia> lista=new ArrayList<>();
        String sql="select * from historias order by id_historia DESC";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql);
             ResultSet rs=ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSetToHistoria(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean update(Historia h) {
        String sql="update historias set id_paciente=?, antecedentes=?, alergias=?, graduacion=?, observaciones=? where id_historia=?";
        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, h.getPaciente().getIdPaciente());
            ps.setString(2, h.getAntecedentes());
            ps.setString(3, h.getAlergias());

            if (h.getGraduacion()!=null && !h.getGraduacion().isEmpty()) {
                ps.setDouble(4, Double.parseDouble(h.getGraduacion()));
            } else {
                ps.setNull(4, java.sql.Types.DECIMAL);
            }
            
            ps.setString(5, h.getObservaciones());
            ps.setInt(6, h.getIdHistoria());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int idHistoria) {
        String sql="delete from historias where id_historia=?";
        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idHistoria);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Historia mapResultSetToHistoria(ResultSet rs) throws SQLException {
        Paciente p=pacienteDAO.searchId(rs.getInt("id_paciente"));
        String graduacion=null;
        double grad=rs.getDouble("graduacion");
        if (!rs.wasNull()) {
            graduacion=String.valueOf(grad);
        }
        
        Historia h=new Historia(p, rs.getString("antecedentes"), rs.getString("alergias"), graduacion, rs.getString("observaciones"));
        
        try {
            java.lang.reflect.Field field=Historia.class.getDeclaredField("idHistoria");
            field.setAccessible(true);
            field.set(h, rs.getInt("id_historia"));
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SQLException e) {
            e.printStackTrace();
        }
        return h;
    }
}