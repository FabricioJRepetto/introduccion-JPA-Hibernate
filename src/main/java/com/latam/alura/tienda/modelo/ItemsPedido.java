package com.latam.alura.tienda.modelo;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "items_pedido")
public class ItemsPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;
    private BigDecimal precioUnitario = new BigDecimal(0);

    @ManyToOne(fetch = FetchType.LAZY) //? varios Items -> un Producto
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY) //? varios Items -> un Pedido
    private Pedido pedido;

    public ItemsPedido() {}

    public ItemsPedido(int cantidad, Producto producto, Pedido pedido) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.pedido = pedido;
        this.precioUnitario = producto.getPrecio();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public BigDecimal getValorTotal() {
        return this.precioUnitario.multiply(new BigDecimal(this.cantidad));
    }
}
