package com.example.chilibanaguia8;

import java.util.ArrayList;

public class PedidoManager {

    private final ArrayList<Pedido> pedidos;
    private int siguienteId = 1;

    public PedidoManager() {
        pedidos = new ArrayList<>();
    }

    public void registrarPedido(String cliente, String tipoPedido,
                                String producto, int cantidad) {

        Pedido pedido = new Pedido(
                siguienteId,
                cliente,
                tipoPedido,
                producto,
                cantidad
        );

        pedidos.add(pedido);
        siguienteId++;
    }

    public ArrayList<Pedido> consultarPedidos() {
        return pedidos;
    }

    public boolean actualizarPedido(int id, String cliente,
                                    String tipoPedido,
                                    String producto,
                                    int cantidad) {

        for (Pedido pedido : pedidos) {

            if (pedido.getId() == id) {

                pedido.setCliente(cliente);
                pedido.setTipoPedido(tipoPedido);
                pedido.setProducto(producto);
                pedido.setCantidad(cantidad);

                return true;
            }
        }

        return false;
    }

    public boolean eliminarPedido(int id) {

        for (int i = 0; i < pedidos.size(); i++) {

            if (pedidos.get(i).getId() == id) {

                pedidos.remove(i);
                return true;
            }
        }

        return false;
    }
}