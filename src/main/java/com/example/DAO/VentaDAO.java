package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Model.Paciente;
import com.example.Model.Producto;
import com.example.Model.Venta;

@SuppressWarnings("CallToPrintStackTrace")
public class VentaDAO {

    private final PacienteDAO pacDAO=new PacienteDAO();
    private final ProductoDAO proDAO=new ProductoDAO();

    public boolean insert(Venta v) {
        String sql="insert into ventas(id_paciente, id_producto, cantidad) values (?,?,?)";

        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, v.getPaciente().getIdPaciente());
            ps.setInt(2, v.getProducto().getIdProducto());
            ps.setInt(3, v.getCantidad());
            
            int affectedRows=ps.executeUpdate();
            if (affectedRows>0) {
                ResultSet rs=ps.getGeneratedKeys();
                if (rs.next()) {
                    try {
                        java.lang.reflect.Field field=Venta.class.getDeclaredField("idVenta");
                        field.setAccessible(true);
                        field.set(v, rs.getInt(1));
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

    public Venta searchId(int id) {
        String sql="select * from ventas where id_venta=?";
        
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToVenta(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Venta> searchByPaciente(int idPaciente) {
        List<Venta> lista=new ArrayList<>();
        String sql="select * from ventas where id_paciente=? order by id_venta desc";

        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idPaciente);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToVenta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Venta> searchByProducto(int idProducto) {
        List<Venta> lista=new ArrayList<>();
        String sql="select * from ventas where id_producto=? order by id_venta desc";

        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idProducto);
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSetToVenta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Venta> list() {
        List<Venta> lista=new ArrayList<>();
        String sql="select * from ventas order by id_venta desc";
        
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql);
             ResultSet rs=ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapResultSetToVenta(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean update(Venta v) {
        String sql="UPDATE ventas set id_paciente=?, id_producto=?, cantidad=? where id_venta=?";
        
        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, v.getPaciente().getIdPaciente());
            ps.setInt(2, v.getProducto().getIdProducto());
            ps.setInt(3, v.getCantidad());
            ps.setInt(4, v.getIdVenta());
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int idVenta) {
        String sql="delete from ventas where id_venta=?";

        try (Connection con=DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, idVenta);
            return ps.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Venta mapResultSetToVenta(ResultSet rs) throws SQLException {
        Paciente pac=pacDAO.searchId(rs.getInt("id_paciente"));
        Producto pro=proDAO.searchId(rs.getInt("id_producto"));
        Venta v=new Venta(pac, pro, rs.getInt("cantidad"));
        
        try {
            java.lang.reflect.Field field=Venta.class.getDeclaredField("idVenta");
            field.setAccessible(true);
            field.set(v, rs.getInt("id_venta"));
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SQLException e) {
            e.printStackTrace();
        }
        return v;
    }
}