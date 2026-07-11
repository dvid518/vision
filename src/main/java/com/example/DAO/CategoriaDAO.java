package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.Model.Categoria;

@SuppressWarnings("CallToPrintStackTrace")

public class CategoriaDAO {

    public List<Categoria> list() {
        List<Categoria> lista=new ArrayList<>();
        String sql="select * from categorias order by nombre";
        
        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Categoria(rs.getInt("id_categoria"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Categoria searchId(int id) {
        String sql="select * from categorias where id_categoria=?";
        
        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                return new Categoria(
                    rs.getInt("id_categoria"),
                    rs.getString("nombre")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Categoria searchNombre(String nombre) {
        String sql="select * from categorias where nombre=?";

        try (Connection con=DBConnection.getConnection();
            PreparedStatement ps=con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                return new Categoria(rs.getInt("id_categoria"), rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}