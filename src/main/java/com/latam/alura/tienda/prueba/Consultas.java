package com.latam.alura.tienda.prueba;

import com.latam.alura.tienda.dao.CategoriaDao;
import com.latam.alura.tienda.dao.ClienteDao;
import com.latam.alura.tienda.dao.PedidoDao;
import com.latam.alura.tienda.dao.ProductoDao;
import com.latam.alura.tienda.modelo.*;
import com.latam.alura.tienda.utiles.JPAUtils;
import com.latam.alura.tienda.vo.RelatorioDeVenta;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class Consultas {
    public static void main(String[] args) {
        registrarProducto();

        EntityManager em = JPAUtils.getEntityManager();
        ClienteDao clienteDao = new ClienteDao(em);
        ProductoDao productoDao = new ProductoDao(em);
        PedidoDao pedidoDao = new PedidoDao(em);

        Producto producto = productoDao.consultaPorId(1L);

        Cliente cliente = new Cliente("Juan", "12312312");
        Pedido pedido = new Pedido(cliente);
        pedido.agregarItems(new ItemsPedido(5, producto, pedido));

        em.getTransaction().begin();

        BigDecimal res = productoDao.consultarPrecioPorNombreDeProducto("Motorola");
        System.out.println(res);

        clienteDao.guardar(cliente);
        pedidoDao.guardar(pedido);

        em.getTransaction().commit();

        BigDecimal total = pedidoDao.valorTotalVendido();
        System.out.println("valor total: " + total);

        List<RelatorioDeVenta> relatorioVO = pedidoDao.relatorioDeVentasVO();
        relatorioVO.forEach(System.out::println);

        // Estrategia para evitar errores con peticiones de tipo LAZY
        Pedido pedidoConCliente =  pedidoDao.consultaPedidoConCliente(1L);

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

//        BigDecimal precio = productoDao.consultarPrecioPorNombreDeProducto("Motorola");
//        System.out.println(precio);

        em.close();

        // Acceder al elemento luego de cerrada al conexion (LAZY fetch)
        System.out.println(pedidoConCliente.getCliente().getNombre());
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
