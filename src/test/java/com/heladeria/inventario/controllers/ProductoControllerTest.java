package com.heladeria.inventario.controllers;

import com.heladeria.inventario.models.Producto;
import com.heladeria.inventario.services.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductoControllerTest {

    private final WebTestClient webTestClient;
    private final ProductoService productoService;

    public ProductoControllerTest(){
        productoService = mock(ProductoService.class);
        webTestClient = WebTestClient.bindToController(new ProductoController(productoService)).build();
    }

    @Test
    @DisplayName("Crear producto")
    void agregarProducto() {

        Producto producto = new Producto(1L,"Torta suiza", "Postre", 20);

        when(productoService.agregarProducto(any(Producto.class))).thenReturn(producto);

        webTestClient
                .post()
                .uri("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(producto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Producto.class)
                .value(producto1 -> {
                    assertEquals(producto.getId(),producto1.getId());
                    assertEquals(producto.getNombre(), producto1.getNombre());
                    assertEquals(producto.getCategoria(), producto1.getCategoria());
                    assertEquals(producto.getCantidad(), producto1.getCantidad());
                });

        Mockito.verify(productoService).agregarProducto(any(Producto.class));

    }

    @Test
    @DisplayName("Obtener producto por id")
    void obtenerProducto() {

        Producto producto = new Producto(1L,"Torta suiza", "Postre", 20);

        when(productoService.obtenerProducto(anyLong())).thenReturn(Optional.of(producto));

        webTestClient.get()
                .uri("/api/productos/{id}",1L)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Producto.class)
                .value(t -> {
                    assertEquals(producto.getId(), t.getId());
                    assertEquals(producto.getNombre(), t.getNombre());
                    assertEquals(producto.getCategoria(), t.getCategoria());
                    assertEquals(producto.getCantidad(), t.getCantidad());
                });

        Mockito.verify(productoService).obtenerProducto(anyLong());

    }

    @Test
    @DisplayName("Obtener todos los productos")
    void listarProductos() {

        when(productoService.listarProductos()).thenReturn(getProductos());

        webTestClient.get()
                .uri("/api/productos")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Producto.class)
                .hasSize(3)
                .value(t -> {
                    assertEquals("Marquesa", t.get(0).getNombre());
                    assertEquals("Torta suiza", t.get(1).getNombre());
                    assertEquals("Vainilla", t.get(2).getNombre());
                });

        Mockito.verify(productoService).listarProductos();
    }

    @Test
    @DisplayName("Actualizar producto")
    void actualizarProducto() {

        Producto producto = new Producto(1L,"Torta suiza", "Postre", 20);

        when(productoService.actualizarProducto(anyLong(), any(Producto.class))).thenReturn(producto);

        webTestClient.put()
                .uri("/api/productos/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(producto)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Producto.class)
                .value(p -> {
                    assertEquals(producto.getId(), p.getId());
                    assertEquals(producto.getNombre(),p.getNombre());
                    assertEquals(producto.getCategoria(), p.getCategoria());
                    assertEquals(producto.getCantidad(), p.getCantidad());
                });

        Mockito.verify(productoService).actualizarProducto(anyLong(), any(Producto.class));

    }

    @Test
    @DisplayName("Eliminar producto")
    void eliminarProducto() {

        doNothing().when(productoService).eliminarProducto(anyLong());

        webTestClient.delete()
                .uri("/api/productos/{id}", 1L)
                .exchange()
                .expectStatus().isNoContent();

        Mockito.verify(productoService).eliminarProducto(anyLong());
    }

    private List<Producto> getProductos() {
        return List.of(new Producto(1L, "Marquesa", "Postre", 10),
                new Producto(2L, "Torta suiza", "Postre", 10),
                new Producto(3L, "Vainilla", "Postre", 10));
    }
}