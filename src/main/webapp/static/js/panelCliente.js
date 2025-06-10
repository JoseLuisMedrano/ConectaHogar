document.addEventListener('DOMContentLoaded', () => {
    const panelClientePage = document.getElementById('panel-cliente-page');
    if (!panelClientePage) return;

    const mainContentArea = document.getElementById('main');

    window.mostrarContenido = function (opcion) {
        const contentSource = document.getElementById(`${opcion}-content`);
        if (contentSource) {
            mainContentArea.innerHTML = contentSource.innerHTML;
        } else {
            mainContentArea.innerHTML = `
                <div class="content-box">
                    <h2>Error</h2>
                    <p>Contenido para '${opcion}' no encontrado.</p>
                </div>`;
            console.error(`Contenido para la opción '${opcion}' no se encontró en el JSP.`);
        }
    };

    mainContentArea.addEventListener('click', (e) => {
        const star = e.target.closest('.rating-star');
        if (star) {
            const ratingContainer = star.closest('.rating-container');
            const stars = ratingContainer.querySelectorAll('.rating-star .fa-star');
            const value = parseInt(star.dataset.value, 10);
            const ratingSubmitButton = ratingContainer.querySelector('.rating-submit');

            stars.forEach((s, index) => {
                s.style.color = index < value ? '#ffc107' : '#ddd';
            });

            if (ratingSubmitButton)
                ratingSubmitButton.disabled = false;
        }

        const submitRatingBtn = e.target.closest('.rating-submit');
        if (submitRatingBtn) {
            e.preventDefault();
            const ratingContainer = submitRatingBtn.closest('.rating-container');
            ratingContainer.innerHTML = `
                <div class="mensaje mensaje-exito" style="text-align: center;">
                    <i class="fas fa-check-circle"></i>
                    <div><strong>¡Gracias por tu calificación!</strong></div>
                </div>`;
        }
    });

    mainContentArea.addEventListener('submit', (e) => {
        e.preventDefault();
        const form = e.target;

        let messageContainer;
        let successMessage;

        if (form.id === 'formServicio') {
            messageContainer = form.nextElementSibling;
            successMessage = `
                <div class="mensaje mensaje-exito">
                    <i class="fas fa-check-circle"></i>
                    <div>
                        <strong>Solicitud enviada con éxito</strong>
                        <p>Hemos recibido tu solicitud. Nos contactaremos pronto.</p>
                    </div>
                </div>`;
        }

        if (form.id === 'formPerfil') {
            messageContainer = form.nextElementSibling;
            successMessage = `
                <div class="mensaje mensaje-exito">
                    <i class="fas fa-check-circle"></i>
                    <div>
                        <strong>Perfil actualizado</strong>
                        <p>Tus datos se han guardado correctamente.</p>
                    </div>
                </div>`;
        }

        if (messageContainer && successMessage) {
            messageContainer.innerHTML = successMessage;
            if (form.id !== 'formPerfil') {
                form.reset();
            }
        }
    });

    window.mostrarContenido('inicio');
});
