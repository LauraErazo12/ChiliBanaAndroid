package com.example.chilibanaguia8;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PedidosActivity extends AppCompatActivity {

    private EditText txtCliente;
    private EditText txtTipoPedido;
    private EditText txtProducto;
    private EditText txtCantidad;

    private LinearLayout listaPedidos;

    private PedidoManager pedidoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        txtCliente = findViewById(R.id.txtCliente);
        txtTipoPedido = findViewById(R.id.txtTipoPedido);
        txtProducto = findViewById(R.id.txtProducto);
        txtCantidad = findViewById(R.id.txtCantidad);

        listaPedidos = findViewById(R.id.listaPedidos);

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        Button btnConsultar = findViewById(R.id.btnConsultar);

        pedidoManager = new PedidoManager();

        btnRegistrar.setOnClickListener(v -> registrarPedido());

        btnConsultar.setOnClickListener(v -> consultarPedidos());
    }

    private void registrarPedido() {

        String cliente = txtCliente.getText().toString().trim();
        String tipoPedido = txtTipoPedido.getText().toString().trim();
        String producto = txtProducto.getText().toString().trim();
        String cantidadTexto = txtCantidad.getText().toString().trim();

        if (cliente.isEmpty() ||
                tipoPedido.isEmpty() ||
                producto.isEmpty() ||
                cantidadTexto.isEmpty()) {

            Toast.makeText(
                    this,
                    "Todos los campos son obligatorios",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        int cantidad;

        try {
            cantidad = Integer.parseInt(cantidadTexto);
        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    "La cantidad debe ser un número",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (cantidad < 1) {

            Toast.makeText(
                    this,
                    "La cantidad debe ser mayor que 0",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        pedidoManager.registrarPedido(
                cliente,
                tipoPedido,
                producto,
                cantidad
        );

        limpiarFormulario();
        consultarPedidos();

        Toast.makeText(
                this,
                "Pedido registrado correctamente",
                Toast.LENGTH_SHORT
        ).show();
    }

    private void consultarPedidos() {

        listaPedidos.removeAllViews();

        if (pedidoManager.consultarPedidos().isEmpty()) {

            TextView mensaje = new TextView(this);

            mensaje.setText("No hay pedidos registrados.");
            mensaje.setTextSize(18);
            mensaje.setPadding(10, 20, 10, 20);

            listaPedidos.addView(mensaje);

            return;
        }

        for (Pedido pedido : pedidoManager.consultarPedidos()) {

            agregarPedidoALaLista(pedido);
        }
    }

    private void agregarPedidoALaLista(Pedido pedido) {

        LinearLayout contenedor = new LinearLayout(this);

        contenedor.setOrientation(LinearLayout.VERTICAL);
        contenedor.setPadding(20, 20, 20, 20);

        TextView informacion = new TextView(this);

        informacion.setText(
                "ID: " + pedido.getId() +
                        "\nCliente: " + pedido.getCliente() +
                        "\nTipo: " + pedido.getTipoPedido() +
                        "\nProducto: " + pedido.getProducto() +
                        "\nCantidad: " + pedido.getCantidad()
        );

        informacion.setTextSize(16);

        Button btnActualizar = new Button(this);
        btnActualizar.setText("Actualizar");

        Button btnEliminar = new Button(this);
        btnEliminar.setText("Eliminar");

        contenedor.addView(informacion);
        contenedor.addView(btnActualizar);
        contenedor.addView(btnEliminar);

        btnActualizar.setOnClickListener(
                v -> mostrarDialogoActualizar(pedido)
        );

        btnEliminar.setOnClickListener(
                v -> eliminarPedido(pedido.getId())
        );

        listaPedidos.addView(contenedor);
    }

    private void mostrarDialogoActualizar(Pedido pedido) {

        LinearLayout layout = new LinearLayout(this);

        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 10, 40, 10);

        EditText cliente = new EditText(this);
        cliente.setHint("Cliente");
        cliente.setText(pedido.getCliente());

        EditText tipo = new EditText(this);
        tipo.setHint("Tipo de pedido");
        tipo.setText(pedido.getTipoPedido());

        EditText producto = new EditText(this);
        producto.setHint("Producto");
        producto.setText(pedido.getProducto());

        EditText cantidad = new EditText(this);
        cantidad.setHint("Cantidad");
        cantidad.setText(String.valueOf(pedido.getCantidad()));
        cantidad.setInputType(
                android.text.InputType.TYPE_CLASS_NUMBER
        );

        layout.addView(cliente);
        layout.addView(tipo);
        layout.addView(producto);
        layout.addView(cantidad);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Actualizar pedido")
                .setView(layout)
                .setPositiveButton("Actualizar", null)
                .setNegativeButton("Cancelar", null)
                .create();

        dialog.setOnShowListener(d -> {

            Button botonActualizar =
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE);

            botonActualizar.setOnClickListener(v -> {

                String nuevoCliente =
                        cliente.getText().toString().trim();

                String nuevoTipo =
                        tipo.getText().toString().trim();

                String nuevoProducto =
                        producto.getText().toString().trim();

                String cantidadTexto =
                        cantidad.getText().toString().trim();

                if (nuevoCliente.isEmpty() ||
                        nuevoTipo.isEmpty() ||
                        nuevoProducto.isEmpty() ||
                        cantidadTexto.isEmpty()) {

                    Toast.makeText(
                            this,
                            "Todos los campos son obligatorios",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                int nuevaCantidad;

                try {
                    nuevaCantidad =
                            Integer.parseInt(cantidadTexto);
                } catch (NumberFormatException e) {

                    Toast.makeText(
                            this,
                            "Cantidad inválida",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                if (nuevaCantidad < 1) {

                    Toast.makeText(
                            this,
                            "La cantidad debe ser mayor que 0",
                            Toast.LENGTH_SHORT
                    ).show();

                    return;
                }

                pedidoManager.actualizarPedido(
                        pedido.getId(),
                        nuevoCliente,
                        nuevoTipo,
                        nuevoProducto,
                        nuevaCantidad
                );

                dialog.dismiss();

                consultarPedidos();

                Toast.makeText(
                        this,
                        "Pedido actualizado correctamente",
                        Toast.LENGTH_SHORT
                ).show();
            });
        });

        dialog.show();
    }

    private void eliminarPedido(int id) {

        new AlertDialog.Builder(this)
                .setTitle("Eliminar pedido")
                .setMessage("¿Deseas eliminar este pedido?")
                .setPositiveButton("Sí", (dialog, which) -> {

                    pedidoManager.eliminarPedido(id);

                    consultarPedidos();

                    Toast.makeText(
                            this,
                            "Pedido eliminado correctamente",
                            Toast.LENGTH_SHORT
                    ).show();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void limpiarFormulario() {

        txtCliente.setText("");
        txtTipoPedido.setText("");
        txtProducto.setText("");
        txtCantidad.setText("");
    }
}
