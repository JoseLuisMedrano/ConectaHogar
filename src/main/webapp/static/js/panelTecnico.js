document.addEventListener('DOMContentLoaded', () => {
    const panelTecnicoPage = document.getElementById('panel-tecnico-page');
    if (!panelTecnicoPage) return;

    const mainContentArea = document.getElementById('main');

    window.mostrarContenido = function(opcion) {
        const contentSource = document.getElementById(`${opcion}-content`);
        if (contentSource) {
            mainContentArea.innerHTML = contentSource.innerHTML;
        } else {
            mainContentArea.innerHTML = `
                <div class="content-box">
                    <h2>Error</h2>
                    <p>Contenido para '${opcion}' no encontrado.</p>
                </div>`;
            console.error(`Contenido para la opción '${opcion}' no encontrado en el JSP.`);
        }
    };

    mainContentArea.addEventListener('click', (e) => {
        const cambiarEstadoBtn = e.target.closest('.btn-cambiar-estado');
        if (cambiarEstadoBtn) {
            e.preventDefault();
            const ticketId = cambiarEstadoBtn.dataset.ticketId;
            const nuevoEstado = cambiarEstadoBtn.dataset.nuevoEstado;
            const mensaje = `
                <div class="mensaje mensaje-exito">
                    <i class="fas fa-check-circle"></i>
                    <div><strong>Estado del ticket #${ticketId} cambiado a '${nuevoEstado}'.</strong></div>
                </div>`;
            mainContentArea.insertAdjacentHTML('afterbegin', mensaje);
        }
    });

    mainContentArea.addEventListener('submit', (e) => {
        e.preventDefault();
        const form = e.target;

        if (form.id === 'formActualizarPerfilTecnico') {
            const mensajeConfirmacion = form.nextElementSibling;
            mensajeConfirmacion.innerHTML = `
                <div class="mensaje mensaje-exito">
                    <i class="fas fa-check-circle"></i>
                    <div><strong>Perfil técnico actualizado correctamente.</strong></div>
                </div>`;
            form.reset();
        }

        if (form.id === 'formRegistrarReporte') {
            const mensajeConfirmacion = form.nextElementSibling;
            mensajeConfirmacion.innerHTML = `
                <div class="mensaje mensaje-exito">
                    <i class="fas fa-check-circle"></i>
                    <div><strong>Reporte registrado con éxito.</strong></div>
                </div>`;
            form.reset();
        }
    });

    window.mostrarContenido('inicio');
});
