package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Model.Consulta;
import com.example.Model.Paciente;

@SuppressWarnings("CallToPrintStackTrace")

public class ConsultaDAO {

    private final PacienteDAO pacDAO=new PacienteDAO();

    public boolean insert(Consulta c) {
        String sql="insert into consultas(id_paciente, motivo, diagnostico, tratamiento) values (?,?,?,?)";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getPaciente().getIdPaciente());
            ps.setString(2, c.getMotivo());
            ps.setString(3, c.getDiagnostico());
            ps.setString(4, c.getTratamiento());
            
            int affectedRows=ps.executeUpdate();
            if (affectedRows>0) {
                ResultSet rs=ps.getGeneratedKeys();
                if (rs.next()) {
                    try {
                        java.lang.reflect.Field field=Consulta.class.getDeclaredField("idConsulta");
                        field.setAccessible(true);
                        field.set(c, rs.getInt(1));
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

    public Consulta searchId(int id) {
        String sql="select * from consultas where id_consulta=?";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToConsulta(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Consulta> searchByPaciente(int idPaciente) {
        List<Consulta> lista=new ArrayList<>();
        String sql="select * from consultas where id_paciente=? order by id_consulta desc";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToConsulta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Consulta> list() {
        List<Consulta> lista=new ArrayList<>();
        String sql="select * from consultas order by id_consulta desc";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql);
             ResultSet rs=ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSetToConsulta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean update(Consulta c) {
        String sql="update consultas set id_paciente=?, motivo=?, diagnostico=?, tratamiento=? where id_consulta=?";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, c.getPaciente().getIdPaciente());
            ps.setString(2, c.getMotivo());
            ps.setString(3, c.getDiagnostico());
            ps.setString(4, c.getTratamiento());
            ps.setInt(5, c.getIdConsulta());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int idConsulta) {
        String sql="delete from consultas where id_consulta=?";
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idConsulta);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Consulta mapResultSetToConsulta(ResultSet rs) throws SQLException {
        Paciente p=pacDAO.searchId(rs.getInt("id_paciente"));
        Consulta c=new Consulta(p, rs.getString("motivo"), rs.getString("diagnostico"), rs.getString("tratamiento"));
        
        try {
            java.lang.reflect.Field field=Consulta.class.getDeclaredField("idConsulta");
            field.setAccessible(true);
            field.set(c, rs.getInt("id_consulta"));
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
}