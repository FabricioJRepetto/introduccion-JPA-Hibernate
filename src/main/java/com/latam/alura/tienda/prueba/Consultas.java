package com.latam.alura.tienda.prueba;

import com.latam.alura.tienda.dao.CategoriaDao;
import com.latam.alura.tienda.dao.ProductoDao;
import com.latam.alura.tienda.modelo.Categoria;
import com.latam.alura.tienda.modelo.Producto;
import com.latam.alura.tienda.utiles.JPAUtils;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class Consultas {
    public static void main(String[] args) {
        registrarProducto();

        EntityManager em = JPAUtils.getEntityManager();
        ProductoDao productoDao = new ProductoDao(em);

        // consultas simples
//        Producto producto = productoDao.consultaPorId(1L);
//        System.out.println(producto.getNombre());

//        List<Producto> lista = productoDao.consultaTodo();
//        lista.forEach(p -> System.out.println(p.getNombre()));

        // consultas con filtro
//        List<Producto> listaN = productoDao.consultaPorNombre("Motorola");
//        listaN.forEach(p -> System.out.println(p.getNombre()));

//        List<Producto> listaC = productoDao.consultaPorNombreDeCategoria("CELULARES");
//        listaC.forEach(p -> System.out.println(p.getNombre()));

        BigDecimal precio = productoDao.consultarPrecioPorNombreDeProducto("Motorola");
        System.out.println(precio);

    }

    private static void registrarProducto() {
        Categoria celulares = new Categoria("CELULARES");
        Producto celular = new Producto("Motorola",
                "Moto G7 power",
                new BigDecimal(1000),
                celulares);

        EntityManager em = JPAUtils.getEntityManager();

        ProductoDao productoDao = new ProductoDao(em);
        CategoriaDao categoriaDao = new CategoriaDao(em);

        em.getTransaction().begin();

        categoriaDao.guardar(celulares);
        productoDao.guardar(celular);

        em.getTransaction().commit();
        em.close();
    }
}
